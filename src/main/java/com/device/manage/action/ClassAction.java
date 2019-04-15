package com.device.manage.action;

import com.device.manage.model.ClassModel;
import com.device.manage.services.ClassService;
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


    /**
     * 添加设备种类
     * @param id 添加种类的管理员的id
     * @param classModel
     * @param result
     * @return
     */
    @PostMapping("/add")
    public Map<String, Object> addClass(@RequestParam ("id") Integer id, @Valid ClassModel classModel, BindingResult result)
    {
        Map<String, Object> messageMap = new HashMap<String, Object>();
        if(result.hasErrors()) {
            String message =  result.getFieldError().getDefaultMessage();
            messageMap.put("message", message);
            return messageMap;
        }
        classService.addClass(classModel);
        messageMap.put("message", "equipment class add success ");
        return messageMap;
    }
}
