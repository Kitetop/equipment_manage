package com.device.manage.repository;

import com.device.manage.model.ClassModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/15
 */
public interface ClassRepository extends JpaRepository<ClassModel, Integer> {
    Optional<ClassModel> findByName(String name);

    @Query(value = "select * from manage_class where class_name like %:query% order by id asc ", nativeQuery = true)
    Page<ClassModel> search(String query, Pageable pageable);
    Integer countByName(String name);
}
