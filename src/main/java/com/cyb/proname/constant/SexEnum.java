package com.cyb.proname.constant;

public enum SexEnum{

    MAN(1, "男"), WOMAN(0, "女");

    private Integer code;
    private String name;

    SexEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
