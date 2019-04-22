package com.device.manage.aspect;

import com.device.manage.model.RepairModel;
import com.device.manage.services.UserService;
import com.device.manage.utils.SelfExcUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/22
 */
@Aspect
@Component
public class RepairAspect {
    @Autowired
    private UserService userService;

    @Pointcut("execution(public * com.device.manage.action.RepairAction.*(..))")
    private void checkAdmin(){}

    @Around("checkAdmin()")
    private Object beforeAdd(ProceedingJoinPoint point) throws Throwable{
        Integer userId = (Integer) point.getArgs()[0];
        if (userService.checkType(userId)) {
            return point.proceed();
        }
        throw new SelfExcUtils(400, "没有操作权限");
    }
    @Before("execution(public * com.device.manage.action.RepairAction.addRepair(..))")
    private void initModel(JoinPoint point)
    {
        RepairModel model = (RepairModel) point.getArgs()[1];
        model.setTime();
    }
}
