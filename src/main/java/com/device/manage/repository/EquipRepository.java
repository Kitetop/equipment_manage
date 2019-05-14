package com.device.manage.repository;

import com.device.manage.model.EquipModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query(value = "select * from manage_info where equip_state = :state order by class_id, id", nativeQuery = true)
    Page<EquipModel> findEquipByState(@Param("state") Integer state, Pageable pageable);

    @Query(value = "select * from manage_info where equip_type like %:query% and equip_state = :state order by class_id, id", nativeQuery = true)
    Page<EquipModel> findEquip(@Param("query") String query, @Param("state") Integer state, Pageable pageable);

    @Modifying
    @Query(value = "update manage_info set equip_state = :state where id = :id", nativeQuery = true)
    void updateState(@Param("id") Integer id, @Param("state") Integer state);
}
