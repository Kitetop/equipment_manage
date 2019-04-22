package com.device.manage.action;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.RepairModel;
import com.device.manage.services.RepairService;
import com.device.manage.utils.ResponseUtils;
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
 * Date: 2019/04/22
 * 设备进行维修的action层
 */
@RestController
@RequestMapping("/repair")
public class RepairAction {

    @Autowired
    private RepairService repairService;

    @PostMapping("/add")
    public ResponseAspect addRepair(
            @RequestParam("userId") Integer userId,
            @Valid RepairModel model,
            BindingResult result
            ) {
        if(result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return ResponseUtils.error(400, message);
        }
        repairService.addRepair(model);
        return ResponseUtils.success("设备维修记录添加成功", null);
    }
}
