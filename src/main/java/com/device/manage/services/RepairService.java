package com.device.manage.services;

import com.device.manage.model.EquipModel;
import com.device.manage.model.RepairModel;
import com.device.manage.repository.RepairRepository;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/22
 */
@Service
public class RepairService {
    @Autowired
    private RepairRepository repairRepository;
    @Autowired
    private EquipService equipService;
    @Autowired
    private ClassService classService;

    /**
     * 添加设备检修记录
     *
     * @param model 设备检修的model对象
     * @throws SelfExcUtils 抛出自定义异常
     */
    public void addRepair(RepairModel model) throws SelfExcUtils {
        repairRepository.save(model);
    }

    /**
     * 判定指定的记录是否存在
     *
     * @param id 检修记录ID
     * @return true or false
     */
    private Boolean checkExist(Integer id) {
        return repairRepository.existsById(id);
    }

    /**
     * 获得所有需要检修且还没有分配的设备
     *
     * @param pageable
     * @return
     */
    public Page<RepairModel> findAllRepair(Pageable pageable) {
        return repairRepository.findAllRepair(pageable);
    }

    /**
     * 获得分配给指定用户维修的设备
     *
     * @param id
     * @param pageable
     * @return
     */
    public Page<RepairModel> findMyRepair(Integer id, Pageable pageable) {
        return repairRepository.findMyRepair(id, pageable);
    }

    /**
     * 修改指定记录中repair_man字段的数据
     *
     * @param userId
     * @param id
     */
    public void choseRepair(Integer userId, Integer id) {
        try {
            repairRepository.choseRepair(userId, id);
        } catch (Exception e) {
            throw new SelfExcUtils(500, "选择失败，请稍后在试");
        }
    }

    /**
     * 完成设备维修
     *
     * @param id
     */
    @Transactional
    public void finish(Integer id, String reason) {
        RepairModel repairModel = repairRepository.findById(id).orElse(null);
        if(repairModel == null) {
            throw new SelfExcUtils(404, "不存在的维修记录");
        } else {
            equipService.changeState(repairModel.getEquipId(), EquipModel.getNORMAL());
            repairModel.setReason(reason);
            repairModel.setFinish(1);
            repairRepository.save(repairModel);
        }
    }

    public Map formateData(List<RepairModel> repairModels) {
        Map<Integer, Object> results = new HashMap<>();
        RepairModel model;
        EquipModel equipModel;
        int index = 0;
        for (Object repair : repairModels) {
            Map<String, Object> result = new HashMap<>();
            model = (RepairModel) repair;
            result.put("id", model.getId());
            equipModel = equipService.findById(model.getEquipId());
            result.put("equip_id", model.getEquipId());
            result.put("equip_type", equipModel.getType());
            result.put("equip_class", classService.getClassName(equipModel.getClassId()));
            results.put(index, result);
            index++;
        }
        return results;
    }

}
