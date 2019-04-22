package com.device.manage.services;

import com.device.manage.model.EquipModel;
import com.device.manage.model.RepairModel;
import com.device.manage.repository.RepairRepository;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 添加设备检修记录
     *
     * @param model 设备检修的model对象
     * @throws SelfExcUtils 抛出自定义异常
     */
    @Transactional
    public void addRepair(RepairModel model) throws SelfExcUtils {
        Integer equipId = model.getEquipId();
        if (equipService.equipAbNormal(equipId)) {
            repairRepository.save(model);
            equipService.changeState(equipId, EquipModel.getREPAIR());
        } else {
            throw new SelfExcUtils(400, "非法设备");
        }
    }

    /**
     * 更新检修记录，补全信息
     *
     * @param model
     */
    @Transactional
    public void update(RepairModel model) {
        Integer id = model.getId();
        if (id != null && checkExist(id) && !checkHasFinish(id)) {
            RepairModel repairModel = repairRepository.findById(id).orElse(null);
            repairModel.setFee(model.getFee());
            repairModel.setFinishDate(model.getFinishDate());
            Integer equipId = repairModel.getEquipId();
            equipService.changeState(equipId, EquipModel.getNORMAL());
            repairRepository.save(repairModel);
        } else {
            throw new SelfExcUtils(400, "无效的交付信息");
        }
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
     * 判断指定记录是否存在且已经完成交付
     *
     * @param id 检修记录ID
     * @return
     */
    private Boolean checkHasFinish(Integer id) {
        RepairModel model = repairRepository.findById(id).orElse(null);
        if (model != null && model.getFee() != null) {
            return true;
        }
        return false;
    }
}
