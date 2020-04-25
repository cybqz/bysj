package com.cyb.engcostms.service.impl;

import com.beastmybatis.core.query.Query;

import com.cyb.engcostms.dao.SupplierMapper;
import com.cyb.engcostms.domain.Supplier;
import com.cyb.engcostms.service.SupplierService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;


@Service("supplierService")
public class SupplierServiceImp implements SupplierService {
    @Resource
    private SupplierMapper supplierMapper;

    /**
     * 新增
     * @param record
     * @return
     */
    @Override
    public int save(Supplier record) {
        return supplierMapper.save(record);
    }

    /**
     * 删除
     * @param record
     * @return
     */
    @Override
    public int delete(Supplier record) {
        return supplierMapper.delete(record);
    }

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @Override
    public int deleteById(String id) {
        return supplierMapper.deleteById(id);
    }

    /**
     * 更新
     * @param record
     * @return
     */
    @Override
    public int update(Supplier record) {
        return supplierMapper.update(record);
    }

    /**
     * 详情查询
     * @param id
     * @return
     */
    @Override
    public Supplier detail(String id) {
        return supplierMapper.getById(id);
    }

    /**
     * 详情查询
     * @param supplierId
     * @return
     */
    @Override
    public Supplier detailBySupplierId(String supplierId) {

        return supplierMapper.getByColumn("supplier_id", supplierId);
    }

    /**
     * 列表查询
     * @param record
     * @return
     */
    @Override
    public List<Supplier> list(Supplier record) {
        Query query = new Query();

        return supplierMapper.list(query);
    }
}
