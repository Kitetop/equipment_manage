package com.device.manage.aspect;

import com.device.manage.services.UserService;
import com.device.manage.utils.SelfExcUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/19
 * 对于设备管理的前置操作类
 */
@Aspect
@Component
public class EquipAspect {
    @Autowired
    private UserService service;

    @Pointcut("execution(public * com.device.manage.action.EquipAction.*(..))")
    protected void checkAdmin() {
    }

    @Around("checkAdmin()")
    protected Object before(ProceedingJoinPoint point) throws Throwable {
        Integer userId = (Integer) point.getArgs()[0];
        if (service.checkType(userId)) {
            return point.proceed();
        }
        throw new SelfExcUtils(400, "没有操作权限");
    }
}
