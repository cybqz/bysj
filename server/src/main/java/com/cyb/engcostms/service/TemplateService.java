package com.cyb.engcostms.service;

import com.cyb.common.pagination.Pagination;

import com.cyb.engcostms.domain.Template;

import java.util.List;


/**
 * Template服务层
 *
 * @author generation
 */
public interface TemplateService {
    /**
     * 新增
     * @param record
     * @return
     */
    int save(Template record);

    /**
     * 删除
     * @param record
     * @return
     */
    int delete(Template record);

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 更新
     * @param record
     * @return
     */
    int update(Template record);

    /**
     * 详情查询
     * @param id
     * @return
     */
    Template detail(String id);

    /**
     * 单条查询
     * @param record
     * @return
     */
    Template getOne(Template record);

    /**
     * 列表查询
     * @param record
     * @return
     */
    List<Template> list(Template record);

    /**
     * 分页列表查询
     * @param record
     * @param pagination
     * @return
     */
    Pagination<Template> page(Template record, Pagination pagination);
}
