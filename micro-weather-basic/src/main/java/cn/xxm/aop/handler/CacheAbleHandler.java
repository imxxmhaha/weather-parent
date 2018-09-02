package cn.xxm.aop.handler;

import cn.xxm.aop.exception.CacheAbleAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CacheAbleHandler {

    @ExceptionHandler(value = CacheAbleAdvice.class)
    @ResponseBody
    public Object handlerSellerException(CacheAbleAdvice e) {
        Object data = e.getData();
        return data;
    }

}