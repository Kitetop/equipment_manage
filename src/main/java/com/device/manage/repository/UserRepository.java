package com.device.manage.repository;

import com.device.manage.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/11
 */
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    public UserModel findByAccountAndPassword(String account, String password);
}
