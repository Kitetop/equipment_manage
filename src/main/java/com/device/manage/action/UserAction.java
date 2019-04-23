package com.device.manage.action;

import com.device.manage.aspect.ResponseAspect;
import com.device.manage.model.UserModel;
import com.device.manage.repository.UserRepository;
import com.device.manage.services.UserService;
import com.device.manage.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private final static Integer LIMIT = 10;
    private final static Integer PAGE = 1;

    @PostMapping("/register")
    public ResponseAspect addUser(@Valid UserModel userModel, BindingResult result) {
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            return ResponseUtils.error(400, message);

        }
        userService.addUser(userModel);
        Map map = new HashMap();
        map.put("id", userModel.getId());
        map.put("username", userModel.getUsername());
        map.put("account", userModel.getAccount());
        return ResponseUtils.success("用户创建成功", map);
    }

    @PostMapping("/login")
    public ResponseAspect login(UserModel userModel) {
        userModel = repository.findByAccountAndPassword(userModel.getAccount(), userModel.getPassword());
        Map messageMap = new HashMap<String, Object>();
        if (userModel == null) {
            return ResponseUtils.error(400, "账号或者密码错误，请核对后重试");
        }
        messageMap.put("id", userModel.getId());
        messageMap.put("name", userModel.getUsername());
        messageMap.put("type", userModel.getType());
        return ResponseUtils.success("登录成功", messageMap);
    }

    /**
     * 显示用户列表
     * @return
     */
    @GetMapping("/list")
    public ResponseAspect userList(@RequestParam("userId") Integer userId,
                                   @RequestParam("query") String query,
                                   @RequestParam("limit") Integer limit,
                                   @RequestParam("page") Integer page) {
        page = page < 1 ? PAGE : page;
        limit = limit < 1 ? LIMIT : limit;
        Pageable pageable = new PageRequest(page -1, limit);
        Page users = userService.findByQuery(query, pageable);
        List results = userService.formateDate(users.getContent());
        Map<String, Object> map = new HashMap<>();
        map.put("total", users.getTotalPages());
        map.put("number", users.getNumberOfElements());
        results.add(map);
        return ResponseUtils.success("查询成功", results);
    }


    @PostMapping("/delete")
    public Map delete(@RequestParam Integer id) {
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
    public Map update(UserModel user, @RequestParam Map<String, String> map) {
        userModel = repository.findById(user.getId()).orElse(null);
        Map messageMap = new HashMap<String, Object>();
        if (userModel == null) {
            messageMap.put("message", "This user not exist");
            return messageMap;
        }
        for (Map.Entry entry : map.entrySet()) {
            String key = (String) entry.getKey();
            if (key.equals("depart") && !userService.checkDepart((Integer) entry.getValue())) {
                messageMap.put("message", "Illegal depart id");
            }
            userModel = userService.setColum(userModel, (String) entry.getKey(), entry.getValue());
        }
        repository.save(userModel);
        messageMap.put("message", "update success");
        return messageMap;
    }
}
