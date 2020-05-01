package com.cyb.engcostms.service;

import com.cyb.engcostms.domain.Enquiry;

import java.util.List;


/**
 * Enquiry服务层
 *
 * @author generation
 */
public interface EnquiryService {
    /**
     * 新增
     * @param record
     * @return
     */
    int save(Enquiry record);

    /**
     * 删除
     * @param record
     * @return
     */
    int delete(Enquiry record);

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
    int update(Enquiry record);

    /**
     * 详情查询
     * @param id
     * @return
     */
    Enquiry detail(String id);

    /**
     * 单条查询
     * @param record
     * @return
     */
    Enquiry getOne(Enquiry record);

    /**
     * 列表查询
     * @param record
     * @return
     */
    List<Enquiry> list(Enquiry record);
}
