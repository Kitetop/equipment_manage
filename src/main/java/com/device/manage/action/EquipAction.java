package com.device.manage.action;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.EquipModel;
import com.device.manage.services.EquipService;
import com.device.manage.utils.PageUtils;
import com.device.manage.utils.ResponseUtils;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/19
 * 设备信息表的action层
 */
@CrossOrigin
@RestController
@RequestMapping("/equip")
public class EquipAction {

    @Autowired
    private EquipService service;

    /**
     * 增加设备
     *
     * @param userId
     * @param model
     * @param result
     * @return
     * @throws SelfExcUtils
     */
    @PostMapping("/add")
    public ResponseAspect addEquip(@RequestParam("userId") Integer userId,
                                   @Valid EquipModel model, BindingResult result)
            throws SelfExcUtils {
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return ResponseUtils.error(400, message);
        }
        service.addEquip(model);
        return ResponseUtils.success("设备添加成功", model);
    }

    /**
     * 获得设备列表
     *
     * @param userId
     * @param query
     * @param state
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/list")
    public ResponseAspect listEquip(@RequestParam("userId") Integer userId,
                                    @RequestParam(value = "query", required = false) String query,
                                    @RequestParam("state") Integer state,
                                    @RequestParam("page") String page,
                                    @RequestParam("limit") String limit) {
        PageUtils pageUtils = new PageUtils();
        pageUtils.checkData(page, limit);
        Pageable pageable = pageUtils.getPageable();
        Page<EquipModel> equips = service.findAllEquip(pageable, query, state);
        Map results = service.formateData(equips.getContent());
        results.put("total", equips.getTotalElements());
        results.put("limit", limit);
        return ResponseUtils.success("查询成功", results);
    }

    /**
     * 维修设备
     * @param userId
     * @param id
     * @return
     */
    @PostMapping("/repair")
    public ResponseAspect repairEquip(@RequestParam("userId") Integer userId,
                                      @RequestParam("id") Integer id) {
        service.RepairEquip(id);
        return ResponseUtils.success("状态已改变", null);
    }
}
