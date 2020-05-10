package com.cyb.engcostms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    /** 项目编号, 数据库字段：project_id */
    private String projectId;

    /** 项目名称, 数据库字段：project_name */
    private String projectName;

    /** 内容, 数据库字段：contains */
    private String contains;

    /** 审核状态, 数据库字段：audit_status */
    private Integer auditStatus;

    /** 报价合计, 数据库字段：total_old */
    private Double totalOld;

    /** 实际合计, 数据库字段：total_new */
    private Double totalNew;

    /** 创建时间, 数据库字段：create_date_time */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime;

    /** 更新时间, 数据库字段：update_date_time */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDateTime;
}
