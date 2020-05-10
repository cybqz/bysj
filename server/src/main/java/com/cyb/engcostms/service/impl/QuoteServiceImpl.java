package com.cyb.engcostms.service.impl;

import com.beastmybatis.core.query.Query;
import com.beastmybatis.core.query.Sort;
import com.beastmybatis.core.util.MapperUtil;
import com.cyb.common.pagination.Pagination;
import com.cyb.engcostms.common.Constant;
import com.cyb.engcostms.dao.QuoteMapper;
import com.cyb.engcostms.domain.Quote;
import com.cyb.engcostms.service.QuoteService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
        Query query = new Query();
        query.orderby("create_date_time", Sort.DESC);
        List<Quote> quoteList = quoteMapper.list(query);
        if(null == quoteList || CollectionUtils.isEmpty(quoteList)){
            record.setProjectId(Constant.DEFAULT_NO);
        }else{
            String materialId = String.valueOf(Integer.valueOf(quoteList.get(0).getProjectId()) + 1);
            int diffLength = Constant.DEFAULT_NO.length() - materialId.length();
            if(diffLength > 0){
                for(int i= 0; i < diffLength; i++){
                    materialId = "0" + materialId;
                }
            }
            record.setProjectId(materialId);
        }
        record.setAuditStatus(0);
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
        if(StringUtils.isNotEmpty(record.getProjectName())){
            query.eq("project_name", record.getProjectName());
        }
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
        query.orderby("project_id", Sort.DESC);
        if(StringUtils.isNotEmpty(record.getProjectName())){
            query.eq("project_name", record.getProjectName());
        }
        if(null != record.getAuditStatus()){
            query.eq("audit_status", record.getAuditStatus());
        }
        Pagination<Quote> result = MapperUtil.query(quoteMapper, query);
        return result;
    }
}
