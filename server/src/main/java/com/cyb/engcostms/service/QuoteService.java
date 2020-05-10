package com.cyb.engcostms.service;

import com.cyb.common.pagination.Pagination;

import com.cyb.engcostms.domain.Quote;

import java.util.List;


/**
 * Quote服务层
 *
 * @author generation
 */
public interface QuoteService {
    /**
     * 新增
     * @param record
     * @return
     */
    int save(Quote record);

    /**
     * 删除
     * @param record
     * @return
     */
    int delete(Quote record);

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
    int update(Quote record);

    /**
     * 详情查询
     * @param id
     * @return
     */
    Quote detail(String id);

    /**
     * 单条查询
     * @param record
     * @return
     */
    Quote getOne(Quote record);

    /**
     * 列表查询
     * @param record
     * @return
     */
    List<Quote> list(Quote record);

    /**
     * 分页列表查询
     * @param record
     * @param pagination
     * @return
     */
    Pagination<Quote> page(Quote record, Pagination pagination);
}
