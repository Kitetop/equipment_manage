package com.device.manage.services;

import com.device.manage.model.UserModel;
import org.springframework.stereotype.Service;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release:
 * Date: 2019/04/11
 */
@Service
public class UserService {
    public UserModel setColum(UserModel user, String key, String value)
    {
        switch (key) {
            case "username":
                user.setUsername(value);
                break;
            case "password":
                user.setPassword(value);
                break;
            case "type":
                user.setType(value);
                break;

        }
        return user;
    }
}
