package com.device.manage.repository;

import com.device.manage.model.ClassModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/15
 */
public interface ClassRepository extends JpaRepository<ClassModel, Integer> {
    Optional<ClassModel> findByName(String name);
}
