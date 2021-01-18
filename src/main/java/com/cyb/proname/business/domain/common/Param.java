package com.cyb.proname.business.domain.common;

import lombok.Data;

@Data
public class Param {

    private String id;

    private String name;

    private String value;

    private String group;

    private int order;

    private String remark;

    public Param(String id, String name, String value, String group) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.group = group;
    }

}