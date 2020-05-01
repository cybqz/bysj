package com.cyb.engcostms.service.impl;

import com.beastmybatis.core.query.Query;
import com.beastmybatis.core.query.Sort;
import com.cyb.engcostms.common.Constant;
import com.cyb.engcostms.dao.MaterialMapper;
import com.cyb.engcostms.domain.Material;
import com.cyb.engcostms.service.MaterialService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Resource;


@Service("materialService")
public class MaterialServiceImpl implements MaterialService {


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
        query.orderby("create_date_time", Sort.DESC);
        List<Material> materialList = materialMapper.list(query);
        if(null == materialList || CollectionUtils.isEmpty(materialList)){
            record.setMaterialId(Constant.DEFAULT_NO);
        }else{
            String materialId = String.valueOf(Integer.valueOf(materialList.get(0).getMaterialId()) + 1);
            int diffLength = Constant.DEFAULT_NO.length() - materialId.length();
            if(diffLength > 0){
                for(int i= 0; i < diffLength; i++){
                    materialId = "0" + materialId;
                }
            }
            record.setMaterialId(materialId);
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
        record.setUpdateDateTime(LocalDateTime.now());
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
     * 查询
     * @param record
     * @return
     */
    @Override
    public Material getOne(Material record) {
        Query query = new Query();
        if(StringUtils.isNotEmpty(record.getMaterialId())){
            query.eq("material_id", record.getMaterialId());
        }
        if(StringUtils.isNotEmpty(record.getType())){
            query.eq("type", record.getType());
        }
        if(StringUtils.isNotEmpty(record.getMaterialName())){
            query.eq("material_name", record.getMaterialName());
        }
        return materialMapper.getByQuery(query);
    }

    /**
     * 列表查询
     * @param record
     * @return
     */
    @Override
    public List<Material> list(Material record) {
        Query query = new Query();
        if(StringUtils.isNotEmpty(record.getType())){
            query.eq("type", record.getType());
        }
        if(StringUtils.isNotEmpty(record.getMaterialName())){
            query.like("material_name", record.getMaterialName());
        }
        query.orderby("material_id", Sort.ASC);
        return materialMapper.list(query);
    }
}
