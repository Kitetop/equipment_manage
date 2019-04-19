package com.device.manage.repository;

import com.device.manage.model.EquipModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/19
 */
public interface EquipRepository extends JpaRepository<EquipModel, Integer> {
    Page<EquipModel> findByClassId(Integer id, Pageable pageable);

    @Query(value = "select * from manage_info where equip_type like %:type%", nativeQuery = true)
    Page<EquipModel> findByType(@Param("type") String type, Pageable pageable);

    @Query(value = "select * from manage_info where equip_state = :state", nativeQuery = true)
    Page<EquipModel> findAbNormal(@Param("state") Integer state, Pageable pageable);
}
