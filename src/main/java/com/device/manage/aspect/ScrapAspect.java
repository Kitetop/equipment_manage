package com.device.manage.aspect;

import com.device.manage.model.ScrapModel;
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
public class ScrapAspect {
    @Autowired
    private UserService userService;

    @Pointcut("execution(public * com.device.manage.action.ScrapAction.*(..))")
    private void checkAdmin() {
    }

    @Around("checkAdmin()")
    private Object beforeAdd(ProceedingJoinPoint point) throws Throwable {
        Integer userId = (Integer) point.getArgs()[0];
        if (userService.checkType(userId)) {
            return point.proceed();
        }
        throw new SelfExcUtils(400, "没有操作权限");
    }

    /**
     * 设置报废时间
     *
     * @param point
     */
    @Before("execution(public * com.device.manage.action.ScrapAction.addScrap(..))")
    private void initTime(JoinPoint point) {
        ScrapModel scrapModel = (ScrapModel) point.getArgs()[1];
        scrapModel.setTime();
    }
}
