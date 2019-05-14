package com.device.manage.action;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.DepartModel;
import com.device.manage.services.DepartService;
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
import java.util.List;
import java.util.Map;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/16
 * 对部门操作的action
 */
@CrossOrigin
@RestController
@RequestMapping("/depart")
public class DepartAction {
    @Autowired
    private DepartService departService;

    private final static Integer LIMIT = 10;
    private final static Integer PAGE = 1;

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

    @GetMapping("/list")
    public ResponseAspect departList(
            @RequestParam("userId") Integer userId,
            @RequestParam("page") Object page,
            @RequestParam("limit") Object limit
    ) {
        PageUtils pageUtils = new PageUtils();
        if(!pageUtils.validata(page, limit)) {
            limit = 5;
            pageUtils = new PageUtils(1, 5);
        }
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        Sort sort = new Sort(order);
        Pageable pageable = pageUtils.getPageable(sort);
        Page departs = departService.findAll(pageable);
        Map results = departService.formateData(departs.getContent());
        results.put("total", departs.getTotalElements());
        results.put("limit", limit);
        return ResponseUtils.success("查询成功", results);
    }

    @GetMapping("/search")
    public ResponseAspect search(
            @RequestParam("userId") Integer userId,
            @RequestParam("query") String query
    ) {
        List departs = departService.search(query);
        Map results = departService.formateData(departs);
        return ResponseUtils.success("查询成功", results);
    }
}
