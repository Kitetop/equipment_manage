package com.device.manage.action;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.EquipModel;
import com.device.manage.services.EquipService;
import com.device.manage.utils.ResponseUtils;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/19
 * 设备信息表的action层
 */
@RestController
@RequestMapping("/equip")
public class EquipAction {

    @Autowired
    private EquipService service;

    private final static Integer PAGE = 1;
    private final static Integer LIMIT = 10;

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

    @GetMapping("/list")
    public ResponseAspect listEquip(@RequestParam("userId") Integer userId,
                                    EquipModel model,
                                    @RequestParam("page") Integer page,
                                    @RequestParam("limit") Integer limit) {
        page = page < 1 ? PAGE : page;
        limit = limit < 1 ? LIMIT : limit;
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(page - 1, limit, sort);
        //设备的查询信息
        Integer class_id = model.getClassId();
        String type = model.getType();
        Integer state = model.getState();
        Page<EquipModel> result;
        if (class_id != null) {
            result = service.findByClass(class_id, pageable);
        } else if (type != null) {
            result = service.findByType(type, pageable);
        } else if (state != 0) {
            result = service.findAbNormal(state, pageable);
        } else {
            result = service.findAll(pageable);
        }
        List list = new ArrayList();
        Map<String, Object> map = new HashMap<>();
        map.put("total", result.getTotalPages());
        map.put("number", result.getNumberOfElements());
        list.add(result.getContent());
        list.add(map);
        return ResponseUtils.success("查找成功", list);
    }
}
