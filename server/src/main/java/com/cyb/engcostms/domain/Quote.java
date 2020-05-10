package com.cyb.engcostms.domain;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：quote
 * 备注：报价表
 *
 * @author generation
 */
@Table(name = "quote")
@Data
public class Quote {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    /** ID, 数据库字段：id */
    private String id;

    /** 模板ID, 数据库字段：template_id */
    private String templateId;

    /** 项目名称, 数据库字段：project_name */
    private String projectName;

    /** 内容, 数据库字段：contains */
    private String contains;

    /** 创建时间, 数据库字段：create_date_time */
    private LocalDateTime createDateTime;

    /** 更新时间, 数据库字段：update_date_time */
    private LocalDateTime updateDateTime;
}
