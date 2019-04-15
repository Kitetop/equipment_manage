package com.device.manage.services;

import com.device.manage.model.UserModel;
import com.device.manage.repository.DepartRepository;
import com.device.manage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release:
 * Date: 2019/04/11
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartRepository departRepository;

    public UserModel setColum(UserModel user, String key, Object value)
    {
        switch (key) {
            case "username":
                user.setUsername((String) value);
                break;
            case "password":
                user.setPassword((String) value);
                break;
            case "type":
                user.setType((String)value);
                break;
            case "depart":
                user.setDepart((Integer) value);
                break;

        }
        return user;
    }

    /**
     * 判断此部门id是否存在
     * @param depart
     * @return
     */
    public Boolean checkDepart(Integer depart)
    {
        return departRepository.existsById(depart);
    }

    /**
     * 判断此id的用户是否是管理员
     * @param id 用户id
     * @return Boolean
     */
    public Boolean checkType(Integer id)
    {
        UserModel userModel = userRepository.findById(id).orElse(null);
        if(userModel != null && userModel.getType().equals(UserModel.ADMINER_USER)) {
            return true;
        }
        return false;
    }
}
