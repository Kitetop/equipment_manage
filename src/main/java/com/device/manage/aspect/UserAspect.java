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
    protected void beforeUserList(JoinPoint point) {
        Integer userId = (Integer)point.getArgs()[0];
        this.checkAdmin(userId);
    }

    @Before("execution(public * com.device.manage.action.UserAction.delete(..))")
    protected void beforeUserDelete(JoinPoint point) {
        Integer userId = (Integer)point.getArgs()[0];
        this.checkAdmin(userId);
    }

    @Before("execution(public * com.device.manage.action.UserAction.addUser(..))")
    protected void beforeUserAdd(JoinPoint point) {
        Integer userId = (Integer)point.getArgs()[0];
        this.checkAdmin(userId);
    }

    /**
     * 检测是否是管理员的功能函数
     * @param userId
     */
    private void checkAdmin(Integer userId) {
        if(!userService.checkType(userId)) {
            throw new SelfExcUtils(400, "没有操作权限");
        }
    }
}
