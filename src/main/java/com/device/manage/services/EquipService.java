package com.device.manage.services;

import com.device.manage.model.EquipModel;
import com.device.manage.repository.EquipRepository;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/19
 * 对设备进行逻辑处理的服务层
 */
@Service
public class EquipService {
    @Autowired
    private EquipRepository repository;
    @Autowired
    private ClassService service;

    /**
     * 新增设备
     * @param model
     * @throws SelfExcUtils
     */
    public void addEquip(EquipModel model) throws SelfExcUtils
    {
        Integer class_id = model.getClassId();
        if(service.existClass(class_id)) {
            repository.save(model);
        } else {
            throw new SelfExcUtils(400, "非法的设备种类");
        }
    }

    /**
     * 查询设备表中的所有信息
     * @param pageable
     * @return
     */
    public Page<EquipModel> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * 根据设备种类查找分页
     * @param id
     * @param pageable
     * @return
     */
    public Page<EquipModel> findByClass(Integer id, Pageable pageable)
    {
        return repository.findByClassId(id, pageable);
    }

    /**
     * 根据设备型号查找分页
     * @param type
     * @param pageable
     * @return
     */
    public Page<EquipModel> findByType(String type, Pageable pageable) {
        return repository.findByType(type, pageable);
    }
}
