package com.device.manage.services;

import com.device.manage.model.ClassModel;
import com.device.manage.repository.ClassRepository;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void update(ClassModel classModel) throws SelfExcUtils {
        String name = classModel.getName();
        ClassModel model = classRepository.findById(classModel.getId()).orElse(null);
        if (!model.getName().equals(name) && checkRepeat(name)) {
            throw new SelfExcUtils(400, "已存在相同的种类名，请重新设置要修改的种类信息");
        }
        model.update(classModel);
        classRepository.save(model);
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
