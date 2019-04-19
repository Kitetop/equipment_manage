package com.device.manage.model;

import com.device.manage.utils.SelfExcUtils;
import org.springframework.stereotype.Component;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/17
 * 设备检修表
 */
@Entity
@Component
@Table(name = "manage_examine")
public class ExamineModel {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull(message = "设备id不能为空")
    @Column(name = "equip_id")
    private Integer equipId;
    @Column(name = "check_date")
    private String date;
    @NotNull(message = "检修人员id不能为空")
    @Column(name = "check_person")
    private Integer checker;
    @Column(name = "check_state")
    private Integer state;

    private final static Integer NORMAl = 0;
    private final static Integer ABNORMAL = -1;

    /**
     * 设置检修日期
     */
    public void setCheckTime()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        setDate(date);
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

    public Integer getChecker() {
        return checker;
    }

    public void setChecker(Integer checker) {
        this.checker = checker;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        if(state.equals(NORMAl) || state.equals(ABNORMAL)) {
            this.state = state;
        } else {
            throw new SelfExcUtils(400, "设备运行状态码错误");
        }
    }

    public static Integer getNORMAl() {
        return NORMAl;
    }

    public static Integer getABNORMAL() {
        return ABNORMAL;
    }
}
