package com.device.manage.services;

import com.device.manage.model.ClassModel;
import com.device.manage.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release:
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
}
