package com.cyb.engcostms.service.impl;

import com.beastmybatis.core.query.Query;

import com.beastmybatis.core.query.Sort;
import com.beastmybatis.core.util.MapperUtil;
import com.cyb.common.pagination.Pagination;
import com.cyb.engcostms.common.Constant;
import com.cyb.engcostms.dao.SupplierMapper;
import com.cyb.engcostms.domain.Material;
import com.cyb.engcostms.domain.Supplier;
import com.cyb.engcostms.service.SupplierService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;


@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {
    @Resource
    private SupplierMapper supplierMapper;

    /**
     * 新增
     * @param record
     * @return
     */
    @Override
    public int save(Supplier record) {

        Query query = new Query();
        query.orderby("create_date_time", Sort.DESC);
        List<Supplier> supplierList = supplierMapper.list(query);
        if(null == supplierList || CollectionUtils.isEmpty(supplierList)){
            record.setSupplierId(Constant.DEFAULT_NO);
        }else{
            String supplierId = String.valueOf(Integer.valueOf(supplierList.get(0).getSupplierId()) + 1);
            int diffLength = Constant.DEFAULT_NO.length() - supplierId.length();
            if(diffLength > 0){
                for(int i= 0; i < diffLength; i++){
                    supplierId = "0" + supplierId;
                }
            }
            record.setSupplierId(supplierId);
        }
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

    @Override
    public Supplier getOne(Supplier record){
        Query query = new Query();
        if(StringUtils.isNotEmpty(record.getSupplierId())){
            query.like("supplier_id", record.getSupplierId());
        }
        if(StringUtils.isNotEmpty(record.getSupplierName())){
            query.like("supplier_name", record.getSupplierName());
        }
        return supplierMapper.getByQuery(query);
    }

    /**
     * 列表查询
     * @param record
     * @param pagination
     * @return
     */
    @Override
    public Pagination<Supplier> page(Supplier record, Pagination pagination) {
        Query query = new Query();
        query.setQueryAll(false);
        query.page(pagination.getPageIndex(), pagination.getLimit());
        if(StringUtils.isNotEmpty(record.getSupplierId())){
            query.like("supplier_id", record.getSupplierId());
        }
        if(StringUtils.isNotEmpty(record.getSupplierName())){
            query.like("supplier_name", record.getSupplierName());
        }
        if(StringUtils.isNotEmpty(record.getOriginPlace())){
            query.like("origin_place", record.getOriginPlace());
        }
        return MapperUtil.query(supplierMapper, query);
    }
}
