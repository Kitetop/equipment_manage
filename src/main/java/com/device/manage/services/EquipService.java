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
     *
     * @param model
     * @throws SelfExcUtils
     */
    public void addEquip(EquipModel model) throws SelfExcUtils {
        Integer class_id = model.getClassId();
        if (service.existClass(class_id)) {
            repository.save(model);
        } else {
            throw new SelfExcUtils(400, "非法的设备种类");
        }
    }

    /**
     * 查询设备表中的所有信息
     *
     * @param pageable
     * @return
     */
    public Page<EquipModel> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * 根据设备种类查找分页
     *
     * @param id
     * @param pageable
     * @return
     */
    public Page<EquipModel> findByClass(Integer id, Pageable pageable) {
        return repository.findByClassId(id, pageable);
    }

    /**
     * 根据设备型号查找分页
     *
     * @param type
     * @param pageable
     * @return
     */
    public Page<EquipModel> findByType(String type, Pageable pageable) {
        return repository.findByType(type, pageable);
    }

    /**
     * 查询状态异常的设备
     *
     * @param state
     * @param pageable
     * @return
     */
    public Page<EquipModel> findAbNormal(Integer state, Pageable pageable) {
        return repository.findAbNormal(state, pageable);
    }

    /**
     * 修改设备的运行状态
     *
     * @param id
     * @param state
     */
    public void changeState(Integer id, Integer state) throws SelfExcUtils {
        if (state.equals(EquipModel.getABNORMAL()) ||
                state.equals(EquipModel.getREPAIR()) ||
                state.equals(EquipModel.getDESTORY())
        ) {
            repository.updateState(id, state);
        } else {
            throw new SelfExcUtils(400, "不能识别的设备运行码");
        }
    }

    /**
     * 检测设备是否存在
     *
     * @param id
     * @return
     */
    public Boolean equipExist(Integer id) {
        return repository.existsById(id);
    }

    /**
     * 检测设备是否为异常状态
     * @param id
     * @return
     */
    public Boolean equipAbNormal(Integer id) {
        EquipModel model = repository.findById(id).orElse(null);
        if(model != null && model.getState().equals(EquipModel.getABNORMAL())){
            return true;
        }
        return false;
    }
}
