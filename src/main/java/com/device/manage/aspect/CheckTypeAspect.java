package com.device.manage.aspect;

import com.device.manage.model.ClassModel;
import com.device.manage.services.ClassService;
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
 * Date: 2019/04/15
 * 用于检查是否有操作权限
 */
@Aspect
@Component
public class CheckTypeAspect {
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;

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
        return  ResponseUtils.error(1,"没有操作权限");
    }

    /**
     * 验证该设备类是否存在
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("execution(public * com.device.manage.action.ClassAction.updateClass(..))")
    protected Object checkClassExists(ProceedingJoinPoint point) throws Throwable
    {
        ClassModel model = (ClassModel)point.getArgs()[1];
        if(classService.existClass(model.getId())) {
            return point.proceed();
        }
        return ResponseUtils.error(403,"不存在此类，无法修改");
    }
}
