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

    @Transactional
    public void addScrap(ScrapModel model) throws SelfExcUtils {
        Integer equipId = model.getEquipId();
        Object result = equipService.couldDestroy(equipId);
        if (result instanceof EquipModel) {
            Double price = ((EquipModel) result).getPrice();
            model.setOldPrice(price);
            scrapRepository.save(model);
            equipService.changeState(equipId, EquipModel.getDESTROY());
        } else {
            throw new SelfExcUtils(400, "非法的报废请求");
        }
    }
}
