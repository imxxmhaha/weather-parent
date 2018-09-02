package cn.xxm.aop.logaop;

import cn.xxm.utils.DateUtil;
import cn.xxm.utils.StringUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;


@Aspect
@Component
public class LoggerAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Controller层切点
    @Pointcut("within(cn.xxm..*)")
    public void pointcut() {
    }


    @Before("pointcut() && @annotation(loggerManage)")
    public void addBeforeLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
        logger.info("==========方法执行日志开始==========");
        logger.info("功能名称：" + loggerManage.description());
        logger.info("开始时间：" + DateUtil.dateToStringYMDHMS(new Date()));
        logger.info("请求方法：" + joinPoint.getSignature().getName());
        logger.info("方法类名: " + joinPoint.getSignature().getDeclaringTypeName());
        logger.info(StringUtil.isEmpty(parseParames(joinPoint)) ? "传入参数: 无" : parseParames(joinPoint));
    }

    @AfterReturning(returning = "rvt", pointcut = "pointcut() && @annotation(loggerManage)")
    public void addAfterReturningLogger(JoinPoint joinPoint, LoggerManage loggerManage,Object rvt) {
        logger.info("返回内容: "+rvt);
        logger.info("功能名称： " + loggerManage.description() + " 执行结束");
        logger.info("==========方法执行日志结束==========");
    }

    @AfterThrowing(pointcut = "pointcut() && @annotation(loggerManage)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, LoggerManage loggerManage, Exception ex) {
        logger.error("执行 " + loggerManage.description() + " 异常", ex);
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

}