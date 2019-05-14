package com.device.manage.action;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.ClassModel;
import com.device.manage.services.ClassService;
import com.device.manage.utils.PageUtils;
import com.device.manage.utils.ResponseUtils;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release:v1.0
 * Date: 2019/04/15
 */
@CrossOrigin
@RestController
@RequestMapping("/class")
public class ClassAction {
    @Autowired
    private ClassService classService;
    @Autowired
    private ResponseAspect responseAspect;


    /**
     * 添加设备种类
     *
     * @param id         添加种类的管理员的id
     * @param classModel
     * @param result
     * @return
     */
    @PostMapping("/add")
    public ResponseAspect addClass(@RequestParam("userId") Integer id, @Valid ClassModel classModel, BindingResult result) {
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return ResponseUtils.error(400, message);
        }
        classService.addClass(classModel);
        return ResponseUtils.success("设备种类添加成功", classModel);
    }

    @PostMapping("/update")
    public ResponseAspect updateClass(@RequestParam("userId") Integer id, ClassModel classModel)
            throws SelfExcUtils {
        classService.update(classModel);
        return ResponseUtils.success("设备信息更新成功", classModel);
    }

    @GetMapping("/list")
    public ResponseAspect listClass(
            @RequestParam("userId") Integer userId,
            @RequestParam("page") String page,
            @RequestParam("limit") String limit) {
        PageUtils pageUtils = new PageUtils();
        pageUtils.checkData(page, limit);
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        Sort sort = new Sort(order);
        Pageable pageable = pageUtils.getPageable(sort);
        Page departs = classService.findAll(pageable);
        Map results = classService.formateData(departs.getContent());
        results.put("total", departs.getTotalElements());
        results.put("limit", limit);
        return ResponseUtils.success("查询成功", results);
    }

    @GetMapping("/search")
    public ResponseAspect search(
            @RequestParam("userId") Integer userId,
            @RequestParam("query") String query,
            @RequestParam("page") String page,
            @RequestParam("limit") String limit
    ) {
        PageUtils pageUtils = new PageUtils();
        pageUtils.checkData(page, limit);
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        Sort sort = new Sort(order);
        Pageable pageable = pageUtils.getPageable(sort);
        Page Class = classService.search(query.trim(), pageable);
        Map results = classService.formateData(Class.getContent());
        results.put("total", Class.getTotalElements());
        results.put("limit", limit);
        return ResponseUtils.success("查询成功", results);
    }
}
