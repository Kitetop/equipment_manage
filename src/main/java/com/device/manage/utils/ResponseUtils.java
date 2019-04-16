package com.device.manage.utils;

import com.device.manage.aspect.ResponseAspect;
import org.springframework.stereotype.Component;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/16
 */
@Component
public class ResponseUtils {

    /**
     * 无异常的返回
     * @param message
     * @param object
     * @return
     */
    public static ResponseAspect success(String message, Object object)
    {
        ResponseAspect aspect = new ResponseAspect();
        aspect.setCode(0);
        aspect.setMessage(message);
        aspect.setData(object);
        return aspect;
    }

    public static ResponseAspect error(Integer code, String message)
    {
        ResponseAspect aspect = new ResponseAspect();
        aspect.setCode(code);
        aspect.setMessage(message);
        aspect.setData(null);
        return aspect;
    }
}
