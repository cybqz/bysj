package com.cyb.goodsms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CustomerInfo {
    private String id;

    private String customerName;

    private String phone;

    private String email;

    private Integer sex;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDateTime;

    public CustomerInfo(String id, String customerName, String phone, String email, Integer sex, String description, Date createDateTime, Date updateDateTime) {
        this.id = id;
        this.customerName = customerName;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
        this.description = description;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }
}