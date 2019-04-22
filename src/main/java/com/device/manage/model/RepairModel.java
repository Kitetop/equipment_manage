package com.device.manage.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    @GeneratedValue
    private Integer id;

    @NotNull(message = "检修设备id不能为空")
    @Column(name = "equip_id")
    private Integer equipId;

    private String reason;

    @Column(name = "repair_date")
    private String repairDate;

    @Column(name = "pre_finishdate")
    private String finishDate;

    @NotNull(message = "维修人员信息不能为空")
    @Column(name = "repair_man")
    private String repair;

    /**
     * 根据model层得信息判断设置哪一个字段的时间
     */
    public void setTime()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(new Date());
        if(this.id == null) {
            setRepairDate(time);
        } else {
            setFinishDate(time);
        }
    }

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

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }
}
