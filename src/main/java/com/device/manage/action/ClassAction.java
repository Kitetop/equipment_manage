package com.device.manage.action;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.ClassModel;
import com.device.manage.services.ClassService;
import com.device.manage.utils.ResponseUtils;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release:v1.0
 * Date: 2019/04/15
 */
@RestController
@RequestMapping("/class")
public class ClassAction {
    @Autowired
    private ClassService classService;
    @Autowired
    private ResponseAspect responseAspect;


    /**
     * 添加设备种类
     * @param id 添加种类的管理员的id
     * @param classModel
     * @param result
     * @return
     */
    @PostMapping("/add")
    public ResponseAspect addClass(@RequestParam ("userId") Integer id, @Valid ClassModel classModel, BindingResult result)
    {
        if(result.hasErrors()) {
            String message =  result.getFieldError().getDefaultMessage();
            return ResponseUtils.error(400, message);
        }
        classService.addClass(classModel);
        return ResponseUtils.success("设备种类添加成功", classModel);
    }

    @PostMapping("/update")
    public ResponseAspect updateClass(@RequestParam("userId") Integer id, ClassModel classModel)
            throws SelfExcUtils
    {
        classService.update(classModel);
        return ResponseUtils.success("设备信息更新成功", classModel);
    }
}
