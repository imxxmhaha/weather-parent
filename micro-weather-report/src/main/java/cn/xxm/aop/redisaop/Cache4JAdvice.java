package cn.xxm.aop.redisaop;

import cn.xxm.aop.exception.CacheAbleAdvice;
import cn.xxm.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class Cache4JAdvice {


    @Autowired
    private RedisTemplate redisTemplate;

    // Controller层切点
    @Pointcut("within(cn.xxm..*)")
    public void pointcut() {
    }

    //前置通知在进入方法体之前,去redis中查找需要查找的数据如果存在就直接返回,不走方法体;如果不存在,进入方法体查询数据库
    @Before("pointcut() && @annotation(cache4J)")
    public Object addBeforeLogger(JoinPoint joinPoint, Cache4J cache4J) {
        log.info("==========操作缓存==========");
        log.info("需要存储的key：" + cache4J.key());
        log.info("存储的key的类型:" + cache4J.optType());
        String s = cache4J.keyInKey();

        String keyInKey = "";
        if (!StringUtil.isEmpty(cache4J.keyInKey()) && "HASH".equals(cache4J.optType().toString())) {
            keyInKey = getParam(cache4J.keyInKey(), joinPoint);
            log.info("存储的Hash类型的keyInkey--{}:{}", cache4J.keyInKey(), keyInKey);
        }


        String key = cache4J.key();
        String type = cache4J.optType().toString();
        switch (type) {
            case "STRING":
                String value = (String) redisTemplate.boundValueOps(key).get();
                if (!StringUtil.isEmpty(value)) {
                    throw new CacheAbleAdvice(1, value);
                }
                break;
            case "HASH":
                Object obj = redisTemplate.boundHashOps(key).get(keyInKey);
                if (!StringUtil.isEmpty(obj)) {
                    throw new CacheAbleAdvice(2, obj);
                }

        }

        return "";
    }

    //在redis中需要查找的数据不存在时,进入方法体查询到数据 返回客户端,并在之前进入后置通知,将数据库查询到的数据存入redis
    //下次如果重复此请求,就直接在前置通知中 在redis取出数据 返回客户端  不会进入方法体
    @AfterReturning(returning = "rvt", pointcut = "pointcut() && @annotation(cache4J)")
    public void addAfterReturningLogger(JoinPoint joinPoint, Cache4J cache4J, Object rvt) {
        log.info("方法返回值:" + rvt);
        String keyInKey = "";
        if (!StringUtil.isEmpty(cache4J.keyInKey()) && "HASH".equals(cache4J.optType().toString())) {
            keyInKey = getParam(cache4J.keyInKey(), joinPoint);

        }

        String key = cache4J.key();
        String type = cache4J.optType().toString();

        switch (type) {
            case "STRING":
                redisTemplate.boundValueOps(key).set(rvt);
                log.info("已将{}:{},以{}方式存储在缓存中", key, rvt, type);
                break;
            case "HASH":
                redisTemplate.boundHashOps(key).put(keyInKey, rvt);
                log.info("已将{}:{}--{},以{}方式存储在缓存中", key, keyInKey, rvt, type);
        }
        log.info("==========存储缓存日志结束==========");
    }

    @AfterThrowing(pointcut = "pointcut() && @annotation(cache4J)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, Cache4J cache4J, Exception ex) {
        log.error("执行 " + cache4J.description() + " 异常", ex);
    }

    private String parseParames(JoinPoint joinPoint) {
        Object[] parames = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();

        if (null == parames || parames.length <= 0) {
            return "";
        }
        StringBuffer param = new StringBuffer("传入参数: ");
        for (int i = 0; i < parameterNames.length; i++) {
            if (!"model".equals(parameterNames[i])) {
                param.append(parameterNames[i]).append("=").append(ToStringBuilder.reflectionToString(parames[i], ToStringStyle.SHORT_PREFIX_STYLE)).append(" ");
            }
        }

//		for (Object obj : parames) {
//			param.append(ToStringBuilder.reflectionToString(obj,ToStringStyle.DEFAULT_STYLE)).append("  ");
//		}
        return param.toString();
    }


    private String getParam(String paramName, JoinPoint joinPoint) {
        String paramValue = "";
        Object[] parames = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        if (null == parames || parames.length <= 0) {
            return "";
        }

        for (int i = 0; i < parameterNames.length; i++) {
            if (paramName.equals(parameterNames[i])) {
                Object parame = parames[i];
                paramValue = parame.toString();
            }
        }
        return paramValue;
    }
}