package com.cyb.engcostms.service.impl;

import com.beastmybatis.core.query.Query;
import com.beastmybatis.core.util.MapperUtil;

import com.cyb.common.pagination.Pagination;

import com.cyb.engcostms.dao.QuoteMapper;
import com.cyb.engcostms.domain.Quote;
import com.cyb.engcostms.service.QuoteService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;


@Service("quoteService")
public class QuoteServiceImpl implements QuoteService {
    @Resource
    private QuoteMapper quoteMapper;

    /**
     * 新增
     * @param record
     * @return
     */
    @Override
    public int save(Quote record) {
        record.setCreateDateTime(LocalDateTime.now());
        return quoteMapper.save(record);
    }

    /**
     * 删除
     * @param record
     * @return
     */
    @Override
    public int delete(Quote record) {
        return quoteMapper.delete(record);
    }

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @Override
    public int deleteById(String id) {
        return quoteMapper.deleteById(id);
    }

    /**
     * 更新
     * @param record
     * @return
     */
    @Override
    public int update(Quote record) {
        record.setUpdateDateTime(LocalDateTime.now());
        return quoteMapper.updateIgnoreNull(record);
    }

    /**
     * 详情查询
     * @param id
     * @return
     */
    @Override
    public Quote detail(String id) {
        return quoteMapper.getById(id);
    }

    /**
     * 单条查询
     * @param record
     * @return
     */
    @Override
    public Quote getOne(Quote record) {
        Query query = new Query();

        return quoteMapper.getByQuery(query);
    }

    /**
     * 列表查询
     * @param record
     * @return
     */
    @Override
    public List<Quote> list(Quote record) {
        Query query = new Query();

        return quoteMapper.list(query);
    }

    /**
     * 分页列表查询
     * @param record
     * @param pagination
     * @return
     */
    @Override
    public Pagination<Quote> page(Quote record, Pagination pagination) {
        Query query = new Query();
        query.setQueryAll(false);
        query.page(pagination.getPageIndex(), pagination.getLimit());

        Pagination<Quote> result = MapperUtil.query(quoteMapper, query);

        return result;
    }
}
