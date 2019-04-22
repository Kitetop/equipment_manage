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
     * @param model
     * @throws SelfExcUtils
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
}
