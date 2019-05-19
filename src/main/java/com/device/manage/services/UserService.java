package com.device.manage.services;

import com.device.manage.model.DepartModel;
import com.device.manage.model.UserModel;
import com.device.manage.repository.DepartRepository;
import com.device.manage.repository.UserRepository;
import com.device.manage.utils.SelfExcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public UserModel setColum(UserModel user, String key, Object value) {
        switch (key) {
            case "username":
                user.setUsername((String) value);
                break;
            case "password":
                user.setPassword((String) value);
                break;
            case "type":
                user.setType((String) value);
                break;
            case "depart":
                user.setDepart((Integer) value);
                break;

        }
        return user;
    }

    /**
     * 增加用户
     *
     * @param userModel
     * @throws SelfExcUtils
     */
    public void addUser(UserModel userModel) throws SelfExcUtils {
        addRepeat(userModel);
        if (checkDepart(userModel.getDepart())) {
            userRepository.save(userModel);
        } else {
            throw new SelfExcUtils(400, "非法的部门");
        }
    }

    /**
     * 避免重复注册
     *
     * @param userModel
     * @throws SelfExcUtils
     */
    public void addRepeat(UserModel userModel) throws SelfExcUtils {
        UserModel model = userRepository.findByAccount(userModel.getAccount()).orElse(null);
        if (model != null) {
            throw new SelfExcUtils(400, "账号已经存在，可以直接登录");
        }
    }

    /**
     * 判断此部门id是否存在
     *
     * @param depart
     * @return
     */
    public Boolean checkDepart(Integer depart) {
        return departRepository.existsById(depart);
    }

    /**
     * 获得部门的名称
     *
     * @param id
     * @return
     * @throws SelfExcUtils
     */
    public String getDepartName(Integer id) throws SelfExcUtils {
        DepartModel departModel = departRepository.findById(id).orElse(null);
        if (departModel != null) {
            return departModel.getDepart();
        } else {
            throw new SelfExcUtils(400, "非法的部门编号");
        }
    }

    /**
     * 判断此id的用户是否是管理员
     *
     * @param id 用户id
     * @return Boolean
     */
    public Boolean checkType(Integer id) {
        UserModel userModel = userRepository.findById(id).orElse(null);
        if (userModel != null && userModel.getType().equals(UserModel.ADMINER_USER)) {
            return true;
        }
        return false;
    }

    /**
     * 判断用户是否是维修员
     * @param id
     * @return
     */
    public Boolean checkRepair(Integer id) {
        UserModel userModel = userRepository.findById(id).orElse(null);
        if(userModel != null && userModel.getType().equals(UserModel.REPAIR_USER)) {
            return true;
        } else {
            return false;
        }
    }

    public Page findByQuery(Object query, Pageable pageable) {
        if (query == null) {
            return userRepository.findAll(pageable);
        }
        return userRepository.findByQuery(((String) query).trim(), pageable);
    }

    /**
     * 对查询到的数据进行选择性的返回显示
     *
     * @param users
     * @return
     */
    public Map formateDate(List users) {
        Map results = new HashMap();
        UserModel model;
        int index = 0;
        for (Object user : users) {
            String type = "检修员";
            Map<String, Object> result = new HashMap<>();
            model = (UserModel) user;
            result.put("id", model.getId());
            result.put("account", model.getAccount());
            result.put("username", model.getUsername());
            result.put("depart", getDepartName(model.getDepart()));
            if (model.getType().equals(UserModel.ADMINER_USER)) {
                type = "管理员";
            } else if (model.getType().equals(UserModel.REPAIR_USER)) {
                type = "维修员";
            }
            result.put("type", type);
            results.put(index, result);
            index++;
        }
        return results;
    }
}
