package com.device.manage.services;

import com.device.manage.model.EquipModel;
import com.device.manage.model.ScrapModel;
import com.device.manage.repository.ScrapRepository;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/22
 * 设备报废的Service层
 */
@Service
public class ScrapService {
    @Autowired
    private ScrapRepository scrapRepository;
    @Autowired
    private EquipService equipService;
    @Autowired
    private RepairService repairService;

    @Transactional
    public  void add(ScrapModel scrapModel, Integer id) throws SelfExcUtils {
        Integer equipId = scrapModel.getEquipId();
        if(!equipService.equipExist(equipId)) {
            throw new SelfExcUtils(400, "不存在的设备");
        }
        if(!equipService.couldDestroy(equipId)) {
            throw new SelfExcUtils(400, "设备状态异常，无法报废");
        }
        //更新自己的任务列表
        repairService.finish(id, "报废");
        //变更设备的状态
        equipService.changeState(equipId, EquipModel.getDESTROY());
        scrapRepository.save(scrapModel);
    }
}
