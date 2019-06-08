package com.device.manage.action;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.ScrapModel;
import com.device.manage.services.ScrapService;
import com.device.manage.utils.ResponseUtils;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/22
 */
@RestController
@CrossOrigin
@RequestMapping("/scrap")
public class ScrapAction {
    @Autowired
    private ScrapService scrapService;

    @PostMapping("/add")
    public ResponseAspect addScrap(@RequestParam("userId") Integer userId,
                                   @Valid ScrapModel scrapModel,
                                   @RequestParam("repairId") Integer repairId,
                                   BindingResult result) {
        if(result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            throw new SelfExcUtils(400, message);
        }
        scrapService.add(scrapModel, repairId);
        return ResponseUtils.success("记录添加成功", null);
    }

}
