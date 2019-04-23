package com.device.manage.repository;

import com.device.manage.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/11
 */
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findByAccountAndPassword(String account, String password);

    Optional<UserModel> findByAccount(String account);

    @Query(value = "select * from manage_user where (account like %:query% or " +
            "username like %:query% or " +
            "depart like %:query%)", nativeQuery = true)
    Page<UserModel> findByQuery(@Param("query") String query, Pageable pageable);
}
