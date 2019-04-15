package com.device.manage.model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Kitetop <1363215999@qq.com>
 * @version Release: v1.0
 * Date: 2019/04/15
 */
@Entity
@Component
@Table(name = "manage_class")
public class ClassModel {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull(message = "种类名称不能为空")
    private String class_name;
    private String class_desc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name.trim();
    }

    public String getClass_desc() {
        return class_desc;
    }

    public void setClass_desc(String class_desc) {
        this.class_desc = class_desc.trim();
    }
}
