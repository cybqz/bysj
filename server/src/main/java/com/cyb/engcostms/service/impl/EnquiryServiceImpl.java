package com.cyb.engcostms.service.impl;

import com.beastmybatis.core.query.Query;
import com.beastmybatis.core.query.Sort;
import com.beastmybatis.core.util.MapperUtil;
import com.cyb.common.pagination.Pagination;
import com.cyb.engcostms.dao.EnquiryMapper;
import com.cyb.engcostms.domain.Enquiry;
import com.cyb.engcostms.domain.Material;
import com.cyb.engcostms.service.EnquiryService;
import com.cyb.engcostms.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;


@Service("enquiryService")
public class EnquiryServiceImpl implements EnquiryService {
    @Resource
    private EnquiryMapper enquiryMapper;

    /**
     * 新增
     * @param record
     * @return
     */
    @Override
    public int save(Enquiry record) {
        if(StringUtils.isEmpty(record.getBelongId())){
            record.setBelongId(MyUtils.getPrimaryKey());
        }
        record.setCreateDateTime(LocalDateTime.now());
        return enquiryMapper.save(record);
    }

    /**
     * 删除
     * @param record
     * @return
     */
    @Override
    public int delete(Enquiry record) {
        return enquiryMapper.delete(record);
    }

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @Override
    public int deleteById(String id) {
        return enquiryMapper.deleteById(id);
    }

    /**
     * 更新
     * @param record
     * @return
     */
    @Override
    public int update(Enquiry record) {
        return enquiryMapper.update(record);
    }

    /**
     * 详情查询
     * @param id
     * @return
     */
    @Override
    public Enquiry detail(String id) {
        return enquiryMapper.getById(id);
    }

    /**
     * 单条查询
     * @param record
     * @return
     */
    @Override
    public Enquiry getOne(Enquiry record) {
        Query query = new Query();
        if(StringUtils.isNotEmpty(record.getBelongId())){
            query.eq("belong_id", record.getBelongId());
        }
        return enquiryMapper.getByQuery(query);
    }

    /**
     * 分页列表查询
     * @param record
     * @param pagination
     * @return
     */
    @Override
    public Pagination<Enquiry> page(Enquiry record, Pagination pagination) {
        Query query = new Query();
        query.setQueryAll(false);
        query.page(pagination.getPageIndex(), pagination.getLimit());
        if(StringUtils.isNotEmpty(record.getBelongId())){
            query.eq("belong_id", record.getBelongId());
        }
        query.orderby("create_date_time", Sort.DESC);
        return MapperUtil.query(enquiryMapper, query);
    }

    /**
     * 最新列表查询
     * @param materialName
     * @param pagination
     * @return
     */
    @Override
    public List<Enquiry> newestList(String materialName, Pagination pagination) {
        return  enquiryMapper.newestList(materialName, pagination);
    }

    /**
     * 最新列表总数查询
     * @param materialName
     * @return
     */
    @Override
    public int newestListCount(String materialName) {
        return  enquiryMapper.newestListCount(materialName);
    }
}
