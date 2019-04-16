package com.device.manage.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
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
    @Column(name = "class_name")
    private String name;
    @Column(name = "class_desc")
    private String desc;

    public ClassModel update(ClassModel classModel)
    {
        String name = classModel.getName();
        String desc = classModel.getDesc();
        if(this.name.equals(name)) {
            this.name = name;
        } else if(this.desc.equals(desc)) {
            this.desc = desc;
        }
        return this;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc.trim();
    }
}
