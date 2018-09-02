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
public class CacheClearAdvice {


    @Autowired
    private RedisTemplate redisTemplate;

    // Controller层切点
    @Pointcut("within(cn.xxm..*)")
    public void pointcut() {
    }


    @Before("pointcut() && @annotation(cacheClear)")
    public void addBeforeLogger(JoinPoint joinPoint, CacheClear cacheClear) {

    }

    @AfterReturning(returning = "rvt", pointcut = "pointcut() && @annotation(cacheClear)")
    public void addAfterReturningLogger(JoinPoint joinPoint, CacheClear cacheClear, Object rvt) {
        log.info("CacheClear清除方法返回值:" + rvt);
        String keyInKey = "";
        if (!StringUtil.isEmpty(cacheClear.keyInKey()) && "HASH".equals(cacheClear.optType().toString())) {
            keyInKey = getParam(cacheClear.keyInKey(), joinPoint);
        }

        String key = cacheClear.key();
        String type = cacheClear.optType().toString();

        switch (type) {
            case "STRING":
                redisTemplate.delete(key);
                log.info("已将{}:{},在缓存中清除", key, rvt);
                break;
            case "HASH":
                if (!StringUtil.isEmpty(cacheClear.keyInKey())) {
                    redisTemplate.boundHashOps(key).delete(keyInKey);
                    log.info("已将{}:{}--{}清除", key, keyInKey, rvt);
                } else {
                    redisTemplate.delete(key);
                    log.info("已将{}清除", key);
                }
        }
        log.info("==========存储清除操作结束==========");
    }

    @AfterThrowing(pointcut = "pointcut() && @annotation(cacheClear)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, CacheClear cacheClear, Exception ex) {
        log.error("执行 " + cacheClear.description() + " 异常", ex);
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