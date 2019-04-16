package com.device.manage.utils;

import com.device.manage.aspect.ResponseAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/16
 * 用于捕获异常
 */
@Component
@RestController
@ControllerAdvice
public class HandleExcUtils {

    private final static Logger logger = LoggerFactory.getLogger(HandleExcUtils.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseAspect handle(Exception e) {
        if (e instanceof SelfExcUtils) {
            SelfExcUtils excUtils = (SelfExcUtils) e;
            return ResponseUtils.error(excUtils.getCode(), e.getMessage());
        } else {
            logger.error("系统异常{}",e);
            return ResponseUtils.error(1, "UN KNOW ERROR");
        }
    }
}
