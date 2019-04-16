package com.device.manage.services;

import com.device.manage.model.UserModel;
import com.device.manage.repository.DepartRepository;
import com.device.manage.repository.UserRepository;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
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
     * 增加用户
     * @param userModel
     * @throws SelfExcUtils
     */
    public void addUser(UserModel userModel) throws SelfExcUtils
    {
        addRepeat(userModel);
        if(checkDepart(userModel.getDepart())) {
            if(userModel.getType() == null) {
                userModel.setType(UserModel.NORMAL_USER);
            }
            userRepository.save(userModel);
        } else {
            throw new SelfExcUtils(400, "非法的部门");
        }
    }

    /**
     * 避免重复注册
     * @param userModel
     * @throws SelfExcUtils
     */
    public void addRepeat(UserModel userModel) throws SelfExcUtils
    {
        UserModel model = userRepository.findByAccount(userModel.getAccount()).orElse(null);
        if(model != null) {
            throw new SelfExcUtils(400, "账号已经存在，可以直接登录");
        }
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
