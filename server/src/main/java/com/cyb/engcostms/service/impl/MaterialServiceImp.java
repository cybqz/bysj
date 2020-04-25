package com.cyb.engcostms.service.impl;

import com.beastmybatis.core.query.Query;

import com.beastmybatis.core.query.Sort;
import com.cyb.engcostms.dao.MaterialMapper;
import com.cyb.engcostms.domain.Material;
import com.cyb.engcostms.service.MaterialService;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;


@Service("materialService")
public class MaterialServiceImp implements MaterialService {
    @Resource
    private MaterialMapper materialMapper;

    /**
     * 新增
     * @param record
     * @return
     */
    @Override
    public int save(Material record) {
        Query query = new Query();
        query.orderby("create_date_time", Sort.ASC);
        List<Material> materialList = materialMapper.list(query);
        if(null == materialList || CollectionUtils.isEmpty(materialList)){
            record.setMaterialId("00000");
        }else{
            Integer materialId = Integer.valueOf(materialList.get(0).getMaterialId()) + 1;
            record.setMaterialId(String.valueOf(materialId));
        }
        if(null == record.getIsStop()){
            record.setIsStop(0);
        }
        record.setCreateDateTime(LocalDateTime.now());
        return materialMapper.save(record);
    }

    /**
     * 删除
     * @param record
     * @return
     */
    @Override
    public int delete(Material record) {
        return materialMapper.delete(record);
    }

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @Override
    public int deleteById(String id) {
        return materialMapper.deleteById(id);
    }

    /**
     * 更新
     * @param record
     * @return
     */
    @Override
    public int update(Material record) {
        return materialMapper.update(record);
    }

    /**
     * 详情查询
     * @param id
     * @return
     */
    @Override
    public Material detail(String id) {
        return materialMapper.getById(id);
    }

    /**
     * 列表查询
     * @param record
     * @return
     */
    @Override
    public List<Material> list(Material record) {
        Query query = new Query();

        return materialMapper.list(query);
    }
}
