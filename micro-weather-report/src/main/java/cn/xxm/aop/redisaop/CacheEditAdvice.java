package cn.xxm.aop.redisaop;

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
public class CacheEditAdvice {


    @Autowired
    private RedisTemplate redisTemplate;

    // Controller层切点
    @Pointcut("within(cn.xxm..*)")
    public void pointcut() {
    }


    @Before("pointcut() && @annotation(cacheEdit)")
    public void addBeforeLogger(JoinPoint joinPoint, CacheEdit cacheEdit) {
        log.info("cacheEdit 注解,不管缓存中有没有数据都会经过目标方法");


    }

    @AfterReturning(returning = "rvt", pointcut = "pointcut() && @annotation(cacheEdit)")
    public void addAfterReturningLogger(JoinPoint joinPoint, CacheEdit cacheEdit, Object rvt) {
        log.info("CacheEdit注解,对方法返回值进行缓存");
        log.info("方法返回值:" + rvt);
        String keyInKey = "";
        if (!StringUtil.isEmpty(cacheEdit.keyInKey()) && "HASH".equals(cacheEdit.optType().toString())) {
            keyInKey = getParam(cacheEdit.keyInKey(), joinPoint);
        }

        String key = cacheEdit.key();
        String type = cacheEdit.optType().toString();

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

    @AfterThrowing(pointcut = "pointcut() && @annotation(cacheEdit)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, CacheEdit cacheEdit, Exception ex) {
        log.error("执行 " + cacheEdit.description() + " 异常", ex);
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