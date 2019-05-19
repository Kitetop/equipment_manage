package com.device.manage.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/19
 */
@Entity
@Component
@Table(name = "manage_repair")
public class RepairModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "维修设备id不能为空")
    @Column(name = "equip_id")
    private Integer equipId;

    private String reason;
    @NotNull(message = "维修人员信息不能为空")
    @Column(name = "repair_man")
    private Integer repair;
    private Integer finish = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEquipId() {
        return equipId;
    }

    public void setEquipId(Integer equipId) {
        this.equipId = equipId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getRepair() {
        return repair;
    }

    public void setRepair(Integer repair) {
        this.repair = repair;
    }

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
    }
}
