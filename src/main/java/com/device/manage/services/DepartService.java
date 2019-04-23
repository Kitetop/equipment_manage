package com.device.manage.services;

import com.device.manage.model.DepartModel;
import com.device.manage.repository.DepartRepository;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/16
 */
@Service
public class DepartService {
    @Autowired
    private DepartModel departModel;
    @Autowired
    private DepartRepository departRepository;

    /**
     * 增加部门
     * @param departModel
     * @throws SelfExcUtils
     */
    public void addDepart(DepartModel departModel) throws SelfExcUtils
    {
        checkRepeat(departModel.getDepart());
        departRepository.save(departModel);
    }

    /**
     * 避免重复添加部门
     * @param depart
     * @throws SelfExcUtils
     */
    private void checkRepeat(String depart) throws SelfExcUtils
    {
        DepartModel departModel = departRepository.findByDepart(depart).orElse(null);
        if (departModel != null) {
            throw new SelfExcUtils(400, "部门已存在，无需重复添加");
        }
    }

    public Page findAll(Pageable pageable) {
        return departRepository.findAll(pageable);
    }
}
