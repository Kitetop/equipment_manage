package com.device.manage.services;

import com.device.manage.model.ClassModel;
import com.device.manage.model.DepartModel;
import com.device.manage.repository.ClassRepository;
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

    public void addClass(ClassModel classModel) {
        if (checkRepeat(classModel.getName())) {
            throw new SelfExcUtils(400, "该种类已存在，请勿重复添加");
        }
        classRepository.save(classModel);
    }

    public Boolean existClass(Integer id) {
        return classRepository.existsById(id);
    }

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
}
