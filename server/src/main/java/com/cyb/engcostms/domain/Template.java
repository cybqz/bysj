package com.cyb.engcostms.domain;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：template
 *
 * @author generation
 */
@Table(name = "template")
@Data
public class Template {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    /** ID, 数据库字段：id */
    private String id;

    /** 内容, 数据库字段：contains */
    private String contains;

    /** 创建时间, 数据库字段：create_date_time */
    private LocalDateTime createDateTime;

    /** 更新时间, 数据库字段：update_date_time */
    private LocalDateTime updateDateTime;
}
