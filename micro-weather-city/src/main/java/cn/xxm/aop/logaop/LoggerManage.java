package cn.xxm.aop.logaop;

import java.lang.annotation.*;

/**
 * @author Leo Wu
 * @version 1.0
 * @Description: 日志注解
 * @date 2016年7月7日  上午11:34:57
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggerManage {

    public String description();
}
