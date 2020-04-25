package com.cyb.engcostms.domain;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：material
 * 备注：物料表
 *
 * @author generation
 */
@Table(name = "material")
@Data
public class Material {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    /** ID, 数据库字段：id */
    private String id;

    /** 物料编号, 数据库字段：material_id */
    private String materialId;

    /** 供应商编号, 数据库字段：supplier_id */
    private String supplierId;

    /** 物料名称, 数据库字段：material_name */
    private String materialName;

    /** 物料类型, 数据库字段：type */
    private Integer type;

    /** 标准, 数据库字段：standrad */
    private String standrad;

    /** 是否停用1：已停用，0：未停用, 数据库字段：is_stop */
    private Integer isStop;

    /** 创建时间, 数据库字段：create_date_time */
    private LocalDateTime createDateTime;

    /** 更新时间, 数据库字段：update_date_time */
    private LocalDateTime updateDateTime;
}
