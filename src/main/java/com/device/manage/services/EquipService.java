package com.device.manage.services;

import com.device.manage.model.EquipModel;
import com.device.manage.repository.EquipRepository;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 根据设备的状态查询设备
     *
     * @param state
     * @param pageable
     * @return
     */
    private Page<EquipModel> findEquipByState(Integer state, Pageable pageable) {
        return repository.findEquipByState(state, pageable);
    }

    private Page<EquipModel> findEquip(Pageable pageable, String query, Integer state) {
        return repository.findEquip(query, state, pageable);
    }

    /**
     * 修改设备的运行状态
     *
     * @param id
     * @param state
     */
    public void changeState(Integer id, Integer state) throws SelfExcUtils {
        this.checkState(state);
        repository.updateState(id, state);
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
     *
     * @param id
     * @return
     */
    public Boolean equipAbNormal(Integer id) {
        EquipModel model = repository.findById(id).orElse(null);
        if (model != null && model.getState().equals(EquipModel.getABNORMAL())) {
            return true;
        }
        return false;
    }

    /**
     * 判断此设备是否可以进行报废流程
     *
     * @param id 设备id
     * @return Boolean
     */
    public Object couldDestroy(Integer id) {
        EquipModel model = repository.findById(id).orElse(null);
        if (model != null) {
            Integer state = model.getState();
            if (state.equals(EquipModel.getNORMAL()) || state.equals(EquipModel.getDESTROY())) {
                return false;
            }
            return model;
        }
        return false;
    }

    /**
     * 检测设置的设备状态是否合理
     *
     * @param state
     * @return
     */
    private Boolean checkState(Integer state) {
        if (state == EquipModel.getNORMAL()||
                state.equals(EquipModel.getABNORMAL()) ||
                state.equals(EquipModel.getREPAIR()) ||
                state.equals(EquipModel.getDESTROY())
        ) {
            return true;
        } else {
            throw new SelfExcUtils(400, "不能识别的设备运行码");
        }
    }

    /**
     * 根据条件查找设备信息
     *
     * @param pageable
     * @param query
     * @param state
     * @return
     */
    public Page<EquipModel> findAllEquip(Pageable pageable, Object query, Integer state) {
        this.checkState(state);
        if (query == null) {
            return this.findEquipByState(state, pageable);
        } else {
            return this.findEquip(pageable, query.toString(), state);
        }
    }

    /**
     * 设置数据的组装格式
     *
     * @param equips
     * @return
     */
    public Map<Integer, Object> formateData(List equips) {
        Map<Integer, Object> results = new HashMap<>();
        EquipModel model;
        int index = 0;
        for (Object equip : equips) {
            Map<String, Object> result = new HashMap<>();
            model = (EquipModel) equip;
            result.put("id", model.getId());
            Integer classId = model.getClassId();
            result.put("className", service.getClassName(classId));
            result.put("type", model.getType());
            result.put("facture", model.getFacture());
            result.put("proyDate", model.getProyDate());
            if (model.getState().equals(EquipModel.getDESTROY())) {
                result.put("state", "报废");
            } else if (model.getState().equals(EquipModel.getNORMAL())) {
                result.put("state", "正常");
            } else if (model.getState().equals(EquipModel.getABNORMAL())) {
                result.put("state", "异常");
            } else {
                result.put("state", "维修");
            }
            results.put(index, result);
            index++;
        }
        return results;
    }
}
