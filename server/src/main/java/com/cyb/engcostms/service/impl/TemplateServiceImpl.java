package com.cyb.engcostms.service.impl;

import com.beastmybatis.core.query.Query;
import com.beastmybatis.core.util.MapperUtil;

import com.cyb.common.pagination.Pagination;

import com.cyb.engcostms.dao.TemplateMapper;
import com.cyb.engcostms.domain.Template;
import com.cyb.engcostms.service.TemplateService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;


@Service("templateService")
public class TemplateServiceImpl implements TemplateService {
    @Resource
    private TemplateMapper templateMapper;

    /**
     * 新增
     * @param record
     * @return
     */
    @Override
    public int save(Template record) {
        record.setCreateDateTime(LocalDateTime.now());
        return templateMapper.save(record);
    }

    /**
     * 删除
     * @param record
     * @return
     */
    @Override
    public int delete(Template record) {
        return templateMapper.delete(record);
    }

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @Override
    public int deleteById(String id) {
        return templateMapper.deleteById(id);
    }

    /**
     * 更新
     * @param record
     * @return
     */
    @Override
    public int update(Template record) {
        record.setUpdateDateTime(LocalDateTime.now());
        return templateMapper.updateIgnoreNull(record);
    }

    /**
     * 详情查询
     * @param id
     * @return
     */
    @Override
    public Template detail(String id) {
        return templateMapper.getById(id);
    }

    /**
     * 单条查询
     * @param record
     * @return
     */
    @Override
    public Template getOne(Template record) {
        Query query = new Query();

        return templateMapper.getByQuery(query);
    }

    /**
     * 列表查询
     * @param record
     * @return
     */
    @Override
    public List<Template> list(Template record) {
        Query query = new Query();

        return templateMapper.list(query);
    }

    /**
     * 分页列表查询
     * @param record
     * @param pagination
     * @return
     */
    @Override
    public Pagination<Template> page(Template record, Pagination pagination) {
        Query query = new Query();
        query.setQueryAll(false);
        query.page(pagination.getPageIndex(), pagination.getLimit());
        Pagination<Template> result = MapperUtil.query(templateMapper, query);
        return result;
    }
}
