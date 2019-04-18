package com.device.manage.repository;

import com.device.manage.model.ExamineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release:
 * Date: 2019/04/18
 */
public interface ExamineRepository extends JpaRepository<ExamineModel, Integer> {
    List<ExamineModel> findByCheckerAndEquipId(Integer check, Integer equip);
}
