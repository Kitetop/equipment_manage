package com.device.manage.utils;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/16
 * 封装自己的异常类,便于捕获状态码
 */
public class SelfExcUtils extends RuntimeException {
    private Integer code;

    public SelfExcUtils(Integer code, String message)
    {
        super(message);
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
