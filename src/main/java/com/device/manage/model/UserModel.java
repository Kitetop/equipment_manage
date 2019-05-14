package com.device.manage.model;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/11
 */
@Entity
@Component
@Table(name = "manage_user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "用户密码不能为空")
    private String password;
    @NotNull(message = "账户不能为空")
    private String account;
    @NotNull(message = "用户类型不能为空")
    private String type;
    @NotNull(message = "请设置部门")
    private Integer depart;
    public static final String CHECKER_USER = "1";
    public static final String ADMINER_USER = "2";
    public static final String REPAIR_USER = "3";

    public Integer getDepart() {
        return depart;
    }

    public void setDepart(Integer depart) {
        this.depart = depart;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = DigestUtils.md5DigestAsHex(password.trim().getBytes());
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (!type.trim().equals(CHECKER_USER)
                && !type.trim().equals(ADMINER_USER)
                && !type.trim().equals(REPAIR_USER)
        ) {
            return;
        }
        this.type = type;
    }
}
