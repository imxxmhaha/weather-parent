package cn.xxm.aop.redisaop;

import java.lang.annotation.*;

/**
 * @author xxm
 * @version 1.0
 * @Description: redis存入缓存注解
 * @date 2018年5月7日  上午11:34:57
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheClear {

    /**
     * 方法描述
     */
    public String description() default "";

    /**
     * 类型枚举
     */
    public enum Type {
        STRING, HASH, LIST, SET, ZSET
    }

    ;

    /**
     * 类型属性
     *
     * @return
     */
    public Type optType() default Type.STRING;

    /**
     * 存储的key的名称
     *
     * @return
     */
    public String key();

    /**
     * Hash类型的小key
     */
    public String keyInKey() default "";
}
