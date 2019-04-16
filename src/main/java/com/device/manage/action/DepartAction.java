package com.device.manage.action;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.DepartModel;
import com.device.manage.services.DepartService;
import com.device.manage.utils.ResponseUtils;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/16
 * 对部门操作的action
 */
@RestController
@RequestMapping("/depart")
public class DepartAction {
    @Autowired
    private DepartService departService;
    @PostMapping("/add")
    public ResponseAspect add(@RequestParam ("userId") Integer id,
                              @Valid DepartModel departModel,
                              BindingResult result)
            throws SelfExcUtils
    {
        if(result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return ResponseUtils.error(400, message);
        }
        departService.addDepart(departModel);
        return ResponseUtils.success("部门添加完毕", departModel);
    }
}
