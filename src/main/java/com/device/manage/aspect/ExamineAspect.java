package com.device.manage.aspect;

import com.device.manage.model.ExamineModel;
import com.device.manage.repository.UserRepository;
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
 * Date: 2019/04/18
 */
@Aspect
@Component
public class ExamineAspect {
    @Autowired
    private UserRepository userRepository;

    @Pointcut("execution(public * com.device.manage.action.ExamineAction.*(..))")
    protected void initExamine(){}

    @Around("initExamine()")
    protected Object init(ProceedingJoinPoint point) throws Throwable
    {
        ExamineModel model = (ExamineModel)point.getArgs()[0];
        if(model.getState() == null) {
            model.setState(ExamineModel.getNORMAl());
        }
        model.setCheckTime();
        if(userRepository.existsById(model.getChecker())) {
            return point.proceed();
        }
        return ResponseUtils.error(400, "非法用户，检修记录提交失败");
    }
}
