package com.device.manage.action;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.RepairModel;
import com.device.manage.services.RepairService;
import com.device.manage.utils.PageUtils;
import com.device.manage.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/22
 * 设备进行维修的action层
 */
@CrossOrigin
@RestController
@RequestMapping("/repair")
public class RepairAction {

    @Autowired
    private RepairService repairService;

    /**
     * 获得所有需要维修但是未被分配的设备列表
     *
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/all")
    public ResponseAspect getAllRepair(@RequestParam("userId") Integer userId,
                                       @RequestParam("page") String page,
                                       @RequestParam("limit") String limit) {
        PageUtils pageUtils = new PageUtils();
        pageUtils.checkData(page, limit);
        Pageable pageable = pageUtils.getPageable();
        Page<RepairModel> equips = repairService.findAllRepair(pageable);
        Map results = repairService.formateData(equips.getContent());
        results.put("total", equips.getTotalElements());
        results.put("limit", limit);
        return ResponseUtils.success("查询成功", results);
    }

    /**
     * 获得需要当前用户进行维修的设备
     *
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/my")
    public ResponseAspect getMyRepair(@RequestParam("userId") Integer userId,
                                      @RequestParam("page") String page,
                                      @RequestParam("limit") String limit) {
        PageUtils pageUtils = new PageUtils();
        pageUtils.checkData(page, limit);
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        Sort sort = new Sort(order);
        Pageable pageable = pageUtils.getPageable(sort);
        Page<RepairModel> equips = repairService.findMyRepair(userId, pageable);
        Map results = repairService.formateData(equips.getContent());
        results.put("total", equips.getTotalElements());
        results.put("limit", limit);
        return ResponseUtils.success("查询成功", results);
    }

    /**
     * 用户选择需要维修的设备进行维修
     *
     * @param userId
     * @param id
     * @return
     */
    @PostMapping("/chose")
    public ResponseAspect choseRepair(@RequestParam("userId") Integer userId,
                                      @RequestParam("id") Integer id) {
        repairService.choseRepair(userId, id);
        return ResponseUtils.success("选择成功，请尽快维修完成", null);
    }

    /**
     * 设备维修完毕交付
     * @param userId
     * @param id
     * @param reason
     * @return
     */
    @PostMapping("/finish")
    public ResponseAspect finish(@RequestParam("userId") Integer userId,
                                 @RequestParam("id") Integer id,
                                 @RequestParam("reason") String reason) {
        repairService.finish(id, reason);
        return ResponseUtils.success("设备交付完成", null);
    }
}
