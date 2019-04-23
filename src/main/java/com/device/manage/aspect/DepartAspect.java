package com.device.manage.aspect;

import com.device.manage.services.UserService;
import com.device.manage.utils.ResponseUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/16
 * 部门相关操作的切面
 */
@Aspect
@Component
public class DepartAspect {
    @Autowired
    private UserService userService;

    @Pointcut("execution(public * com.device.manage.action.DepartAction.*(..))")
    public void checkAdmin() {
    }

    @Around("checkAdmin()")
    public Object beforeAdd(ProceedingJoinPoint point) throws Throwable {
        if (userService.checkType((Integer) point.getArgs()[0])) {
            return point.proceed();
        }
        return ResponseUtils.error(400, "没有操作权限");
    }
}
