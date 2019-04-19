package com.device.manage.services;

import com.device.manage.model.ExamineModel;
import com.device.manage.repository.ExamineRepository;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/18
 */
@Service
public class ExamineService {

    @Autowired
    private ExamineRepository repository;
    @Autowired
    private EquipService service;

    private void checkRepeat(ExamineModel model) {
        Integer checker = model.getChecker();
        Integer equip = model.getEquipId();
        Integer state = model.getState();
        String date = model.getDate();
        List<ExamineModel> models = repository.findByCheckerAndEquipIdAndDateAndState(checker, equip, date, state);
        if (!models.isEmpty()) {
            throw new SelfExcUtils(400, "请勿重复检修");
        }
    }

    /**
     * 添加检修记录
     * @param model
     */
    @Transactional
    public void addExamine(ExamineModel model) throws SelfExcUtils{
        checkRepeat(model);
        if(!service.equipExist(model.getEquipId())) {
            throw new SelfExcUtils(400, "非法检修的设备，设备不存在");
        }
        if(model.getState().equals(ExamineModel.getABNORMAL())) {
            //修改设备表中的状态
            service.changeState(model.getEquipId(), ExamineModel.getABNORMAL());
        }
        repository.save(model);
    }
}
