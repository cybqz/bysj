package com.cyb.engcostms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：enquiry
 * 备注：询价表
 *
 * @author generation
 */
@Table(name = "enquiry")
@Data
public class Enquiry {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    /** ID, 数据库字段：id */
    private String id;

    /** 所属ID, 数据库字段：belong_id */
    private String belongId;

    /** 物料编号, 数据库字段：material_id */
    private String materialId;

    /** 供应商编号, 数据库字段：supplier_id */
    private String supplierId;

    /** 价格, 数据库字段：price */
    private BigDecimal price;

    /** 单位, 数据库字段：unit */
    private String unit;

    /** 创建时间, 数据库字段：create_date_time */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime;

    /** 更新时间, 数据库字段：update_date_time */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDateTime;
}
