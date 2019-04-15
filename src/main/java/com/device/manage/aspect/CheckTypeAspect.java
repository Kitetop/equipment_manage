package com.device.manage.aspect;

import com.device.manage.services.ClassService;
import com.device.manage.services.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/15
 * 用于检查是否有操作权限
 */
@Aspect
@Component
public class CheckTypeAspect {
    @Autowired
    private ClassService classService;
    @Autowired
    private UserService userService;

    /**
     * 验证是否是管理员的公共切点
     */
    @Pointcut("execution(public * com.device.manage.action.ClassAction.*(..))")
    public void checkAdmin(){}

    @Around("checkAdmin()")
    protected Object checkType(ProceedingJoinPoint point) throws Throwable {
        if(userService.checkType((Integer)point.getArgs()[0])) {
            return point.proceed();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message","Permission denied");
        return map;
    }
}
