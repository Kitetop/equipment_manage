package com.device.manage.action;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.ExamineModel;
import com.device.manage.repository.ExamineRepository;
import com.device.manage.services.ExamineService;
import com.device.manage.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/18
 */
@RestController
@RequestMapping("/examine")
public class ExamineAction {
    @Autowired
    private ExamineService service;
    @PostMapping("/add")
    public ResponseAspect addCheck(@Valid ExamineModel model, BindingResult result)
    {
        if(result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return ResponseUtils.error(400, message);
        }
        service.addExamine(model);
        return ResponseUtils.success("检修记录添加成功", null);
    }
}
