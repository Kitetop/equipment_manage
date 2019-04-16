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

    public void addClass(ClassModel classModel)
    {
        classRepository.save(classModel);
    }
    public Boolean existClass(Integer id) {return classRepository.existsById(id);}
    public void update(ClassModel classModel) throws SelfExcUtils {
        if(classModel.getId() == null) {
            throw new SelfExcUtils(400, "要更新的种类的id不能为空");
        }
        ClassModel model = classRepository.findById(classModel.getId()).orElse(null);
        model.update(classModel);
        classRepository.save(model);
    }
}
