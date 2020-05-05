package com.cyb.engcostms.service;

import com.cyb.common.pagination.Pagination;
import com.cyb.engcostms.domain.Supplier;

import java.util.List;


/**
 * Supplier服务层
 *
 * @author generation
 */
public interface SupplierService {
    /**
     * 新增
     * @param record
     * @return
     */
    int save(Supplier record);

    /**
     * 删除
     * @param record
     * @return
     */
    int delete(Supplier record);

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
    int update(Supplier record);

    /**
     * 详情查询
     * @param id
     * @return
     */
    Supplier detail(String id);

    /**
     * 详情查询
     * @param supplierId
     * @return
     */
    Supplier detailBySupplierId(String supplierId);

    /**
     * 单条查询
     * @param record
     * @return
     */
    Supplier getOne(Supplier record);

    /**
     * 列表查询
     * @param record
     * @return
     */
    List<Supplier> list(Supplier record);

    /**
     * 分页列表查询
     * @param record
     * @param pagination
     * @return
     */
    Pagination<Supplier> page(Supplier record, Pagination pagination);
}
