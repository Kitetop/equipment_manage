package com.device.manage.services;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.ClassModel;
import com.device.manage.model.DepartModel;
import com.device.manage.repository.ClassRepository;
import com.device.manage.utils.ResponseUtils;
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
 * Date: 2019/04/15
 */
@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    /**
     * 增加设备种类
     * @param classModel
     */
    public void addClass(ClassModel classModel) {
        if (checkRepeat(classModel.getName())) {
            throw new SelfExcUtils(400, "该种类已存在，请勿重复添加");
        }
        classRepository.save(classModel);
    }

    /**
     * 判断设备种类是否已经存在
     * @param id
     * @return
     */
    public Boolean existClass(Integer id) {
        return classRepository.existsById(id);
    }

    /**
     * 更新设备种类信息
     * @param classModel
     * @throws SelfExcUtils
     */
    @Transactional
    public void update(ClassModel classModel) throws SelfExcUtils {
        String name = classModel.getName();
        ClassModel model = classRepository.findById(classModel.getId()).orElse(null);
        model.setName(name);
        model.setDesc(classModel.getDesc());
        classRepository.save(model);
        if (classRepository.countByName(name) != 1) {
            throw new SelfExcUtils(400, "已存在相同的种类名，请重新设置要修改的种类信息");
        }
    }

    /**
     * 获得全部的种类数据
     * @param pageable
     * @return
     */
    public Page findAll(Pageable pageable) {
        return classRepository.findAll(pageable);
    }

    /**
     * 根据设备种类名称查找设备种类
     * @param query
     * @param pageable
     * @return
     */
    public Page<ClassModel> search(String query, Pageable pageable) {
        return classRepository.search(query, pageable);
    }

    /**
     * 设置数据的组装格式
     * @param Class
     * @return
     */
    public Map<Integer, Object> formateData(List Class) {
        Map<Integer, Object> results = new HashMap<>();
        ClassModel model;
        int index = 0;
        for (Object type : Class) {
            Map<String, Object> result = new HashMap<>();
            model = (ClassModel) type;
            result.put("id", model.getId());
            result.put("class", model.getName());
            result.put("desc", model.getDesc());
            results.put(index, result);
            index++;
        }
        return results;
    }

    /**
     * 删除指定id的种类
     * @param id
     * @return
     */
    public ResponseAspect delete(Integer id) {
        ClassModel classModel = classRepository.findById(id).orElse(null);
        if (classModel == null) {
            return ResponseUtils.error(400, "种类不存在，无法进行删除操作");
        } else {
            classRepository.deleteById(id);
            return ResponseUtils.success("删除成功", null);
        }
    }

    /**
     * 验证此类是否重复
     *
     * @param name
     * @return
     */
    private Boolean checkRepeat(String name) {
        ClassModel model = classRepository.findByName(name).orElse(null);
        if (model == null) {
            return false;
        }
        return true;
    }

    /**
     * 根据设备种类id获得种类名
     * @param id
     * @return
     */
    public String getClassName(Integer id) {
        ClassModel classModel = classRepository.findById(id).orElse(null);
        if(classModel != null) {
            return classModel.getName();
        } else {
            throw new SelfExcUtils(400, "此设备种类不存在");
        }
    }
}
