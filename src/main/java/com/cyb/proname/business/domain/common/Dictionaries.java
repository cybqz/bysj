package com.cyb.proname.business.domain.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@TableName(value = "params")
public class Dictionaries {

    @TableId(value = "id")
    private String id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "value")
    private String value;

    @TableField(value = "group")
    private String group;

    @TableField(value = "order")
    private int order;

    @TableField(value = "remark")
    private String remark;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "create_date_time")
    private Date createDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "update_date_time")
    private Date updateDateTime;
}