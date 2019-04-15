package com.device.manage.action;

import com.device.manage.model.UserModel;
import com.device.manage.repository.UserRepository;
import com.device.manage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/11
 */
@RestController
@RequestMapping("/user")
public class UserAction {
    @Autowired
    private UserModel userModel;
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public Map addUser(@Valid UserModel userModel, BindingResult result)
    {
        Map messageMap = new HashMap<String,Object>();
        if(result.hasErrors()) {
            String message =  result.getFieldError().getDefaultMessage();
            messageMap.put("message", message);
            return messageMap;

        }
        repository.save(userModel);
        messageMap.put("message", "user create success!");
        return messageMap;
    }
    @PostMapping("/login")
    public Map login(UserModel userModel)
    {
        userModel = repository.findByAccountAndPassword(userModel.getAccount(), userModel.getPassword());
        Map messageMap = new HashMap<String, Object>();
        if (userModel == null) {
            messageMap.put("message", "This user not exist");
            return messageMap;
        }
        messageMap.put("id", userModel.getId());
        messageMap.put("name", userModel.getUsername());
        messageMap.put("type", userModel.getType());
        return messageMap;
    }

    @PostMapping("/delete")
    public Map delete(@RequestParam Integer id)
    {
        Map messageMap = new HashMap<String, String>();
        userModel = repository.findById(id).orElse(null);
        if (userModel == null) {
            messageMap.put("message", "This user not exist");
            return messageMap;
        }
        repository.deleteById(id);
        messageMap.put("message", userModel.getUsername() + " delete success");
        return messageMap;
    }

    @PostMapping("/update")
    public Map update(UserModel user, @RequestParam Map<String, String> map)
    {
        userModel = repository.findById(user.getId()).orElse(null);
        Map messageMap = new HashMap<String, Object>();
        if (userModel == null) {
            messageMap.put("message", "This user not exist");
            return messageMap;
        }
        for (Map.Entry entry : map.entrySet()) {
            String key = (String)entry.getKey();
            if(key.equals("depart") && !userService.checkDepart((Integer) entry.getValue())) {
                 messageMap.put("message", "Illegal depart id");
            }
            userModel = userService.setColum(userModel, (String)entry.getKey(), entry.getValue());
        }
        repository.save(userModel);
        messageMap.put("message", "update success");
        return messageMap;
    }
}
