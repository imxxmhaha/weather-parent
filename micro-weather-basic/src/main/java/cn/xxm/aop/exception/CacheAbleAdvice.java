package cn.xxm.aop.exception;

import lombok.Data;

/**
 * 检验用户登陆验证的异常类
 */
@Data
public class CacheAbleAdvice extends RuntimeException {
    private Integer code;
    private Object data;


    public CacheAbleAdvice(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }
}
