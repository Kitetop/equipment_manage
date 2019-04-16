package com.device.manage.repository;

import com.device.manage.model.DepartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release:
 * Date: 2019/04/15
 */
@Repository
public interface DepartRepository extends JpaRepository<DepartModel, Integer> {
    Optional<DepartModel> findByDepart(String depart);
}
