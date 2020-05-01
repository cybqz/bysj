package com.cyb.engcostms.service.impl;

import com.beastmybatis.core.query.Query;
import com.cyb.engcostms.dao.EnquiryMapper;
import com.cyb.engcostms.domain.Enquiry;
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
     * 最新列表查询
     * @param record
     * @return
     */
    @Override
    public List<Enquiry> list(Enquiry record) {
        return  enquiryMapper.newestList(record);
    }
}
