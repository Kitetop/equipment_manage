package com.device.manage.repository;

import com.device.manage.model.RepairModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/22
 */
public interface RepairRepository extends JpaRepository<RepairModel, Integer> {
}
