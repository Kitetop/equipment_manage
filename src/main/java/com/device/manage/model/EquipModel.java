package com.device.manage.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/18
 * 设备信息表的model层
 */
@Entity
@Component
@Table(name = "manage_info")
public class EquipModel {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull(message = "所属种类不能为空")
    @Column(name = "class_id")
    private Integer classId;

    @NotNull(message = "设备型号不能为空")
    @Column(name = "equip_type")
    private String type;

    @Column(name = "manufacture")
    private String facture;

    @NotNull(message = "价格信息不能为空")
    private Double price;

    //生产日期
    @Column(name = "proydate")
    private String proyDate;

    //购买日期
    @Column(name = "buydate")
    private String buyDate;

    //使用年限
    @Column(name = "pre_year")
    private Integer year;

    //设备运行状态
    @Column(name = "equip_state")
    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type.trim();
    }

    public String getFacture() {
        return facture;
    }

    public void setFacture(String facture) {
        this.facture = facture.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProyDate() {
        return proyDate;
    }

    public void setProyDate(String proyDate) {
        this.proyDate = proyDate.trim();
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}