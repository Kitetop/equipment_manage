package com.device.manage.aspect;

import com.device.manage.services.UserService;
import com.device.manage.utils.SelfExcUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/23
 */
@Aspect
@Component
public class UserAspect {
    @Autowired
    private UserService userService;

    @Before("execution(public * com.device.manage.action.UserAction.userList(..))")
    protected void checkAdmin(JoinPoint point) {
        Integer userId = (Integer)point.getArgs()[0];
        if(!userService.checkType(userId)) {
            throw new SelfExcUtils(400, "没有操作权限");
        }
    }
}
