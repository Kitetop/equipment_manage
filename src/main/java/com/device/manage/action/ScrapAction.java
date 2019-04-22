package com.device.manage.action;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.ScrapModel;
import com.device.manage.services.ScrapService;
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
 */
@RestController
@RequestMapping("/scrap")
public class ScrapAction {
    @Autowired
    private ScrapService scrapService;

    @PostMapping("/add")
    public ResponseAspect addScrap(@RequestParam ("userId") Integer userId,
                                   @Valid ScrapModel model,
                                   BindingResult result) {
        if(result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return ResponseUtils.error(400, message);
        }
        scrapService.addScrap(model);
        return ResponseUtils.success("设备报废成功", null);
    }
}
