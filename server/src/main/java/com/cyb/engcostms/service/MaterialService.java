package com.cyb.engcostms.service;

import com.cyb.common.pagination.Pagination;
import com.cyb.engcostms.domain.Material;


/**
 * Material服务层
 *
 * @author generation
 */
public interface MaterialService {
    /**
     * 新增
     * @param record
     * @return
     */
    int save(Material record);

    /**
     * 删除
     * @param record
     * @return
     */
    int delete(Material record);

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
    int update(Material record);

    /**
     * 详情查询
     * @param id
     * @return
     */
    Material detail(String id);

    /**
     * 查询
     * @param material
     * @return
     */
    Material getOne(Material material);

    /**
     * 分页列表查询
     * @param record
     * @param pagination
     * @return
     */
    Pagination<Material> page(Material record, Pagination pagination);
}
