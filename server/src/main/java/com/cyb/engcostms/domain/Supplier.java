package com.cyb.engcostms.domain;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：supplier
 * 备注：供应商表
 *
 * @author generation
 */
@Table(name = "supplier")
@Data
public class Supplier {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    /** ID, 数据库字段：id */
    private String id;

    /** 供应商编号, 数据库字段：supplier_id */
    private String supplierId;

    /** 供应商名称, 数据库字段：supplier_name */
    private String supplierName;

    /** 供应商联系人, 数据库字段：supplier_user */
    private String supplierUser;

    /** 供应商联系电话, 数据库字段：supplier_phone */
    private String supplierPhone;

    /** 产地, 数据库字段：origin_place */
    private String originPlace;

    /** 地址, 数据库字段：address */
    private String address;

    /** 创建时间, 数据库字段：create_date_time */
    private LocalDateTime createDateTime;

    /** 更新时间, 数据库字段：update_date_time */
    private LocalDateTime updateDateTime;
}
