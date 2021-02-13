package com.cyb.proname.exception;

import com.cyb.common.tips.Tips;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author 陈迎博
 * @Description 全局异常拦截处理
 * @Date 2021/2/13
 */

@Slf4j
@RestControllerAdvice(basePackages = {"com.cyb.proname.business.controller"})
public class GlobalExceptionHandler {

    /**
     * @Author 陈迎博
     * @Title 校验异常
     * @Description 校验异常
     * @Date 2021/2/13
     */
    @ExceptionHandler(BindException.class)
    public Tips methodArgNotValidHandler(BindException e) {
        return getTipsWithBindingResult(e.getBindingResult());
    }

    /**
     * @Author 陈迎博
     * @Title 校验异常
     * @Description 校验异常
     * @Date 2021/2/13
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Tips serviceHandle(MethodArgumentNotValidException e) {
        return getTipsWithBindingResult(e.getBindingResult());
    }

    /**
     * 全局异常处理
     * @param
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Tips exceptionHandler(Exception e) {
        log.error("全局异常捕捉异常", e);
        return new Tips("系统异常", true, false);
    }

    private Tips getTipsWithBindingResult (BindingResult result){
        String msg = result.getFieldErrors().get(0).getDefaultMessage();
        log.error("校验异常捕捉异常: " + msg);
        return new Tips(msg, true, false);
    }
}
