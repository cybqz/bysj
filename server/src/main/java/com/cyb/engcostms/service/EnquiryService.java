package com.cyb.engcostms.service;

import com.cyb.common.pagination.Pagination;
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
     * 分页列表查询
     * @param record
     * @param pagination
     * @return
     */
    Pagination<Enquiry> page(Enquiry record, Pagination pagination);

    /**
     * 分页最新列表查询
     * @param materialName
     * @param pagination
     * @return
     */
    List<Enquiry> newestList(String materialName, Pagination pagination);

    /**
     * 最新列表总数查询
     * @param materialName
     * @return
     */
    int newestListCount(String materialName);
}
