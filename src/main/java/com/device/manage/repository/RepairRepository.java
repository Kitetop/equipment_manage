package com.device.manage.repository;

import com.device.manage.model.RepairModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/22
 */
public interface RepairRepository extends JpaRepository<RepairModel, Integer> {

    @Query(value = "select * from manage_repair where repair_man = 0 and finish = 0", nativeQuery = true)
    Page<RepairModel> findAllRepair(Pageable pageable);

    @Query(value = "select * from manage_repair where repair_man = :id and finish = 0", nativeQuery = true)
    Page<RepairModel> findMyRepair(@Param("id") Integer id, Pageable pageable);

    @Modifying
    @Query(value = "update manage_repair set repair_man = :userId where id = :id", nativeQuery = true)
    void choseRepair(@Param("userId") Integer userId, @Param("id") Integer id);
}
