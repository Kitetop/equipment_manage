package com.device.manage.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/22
 */
@Entity
@Component
@Table(name = "manage_scrap")
public class ScrapModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "设备id不能为空")
    @Column(name = "equip_id")
    private Integer equipId;

    @Column(name = "scrap_date")
    private String date;

    @NotNull(message = "报废原因不能为空")
    @Column(name = "scrap_reason")
    private String reason;

    @NotNull(message = "报废处理人id不能为空")
    @Column(name = "request_man")
    private Integer userId;


    /**
     * 设置时间
     */
    public void setTime()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(new Date());
        setDate(time);
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String resaon) {
        this.reason = resaon.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
