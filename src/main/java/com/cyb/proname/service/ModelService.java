package com.cyb.proname.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.common.pagination.Pagination;
import com.cyb.proname.domain.Model;
import com.cyb.proname.mapper.ModelMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
   * @Author 陈迎博
   * @Description 模板服务层实现
   * @Date 2021/1/16
   */
@Service
public class ModelService extends ServiceImpl<ModelMapper,Model> {

    @Resource
    private ModelMapper modelMapper;

    /**
     * @Author 陈迎博
     * @Title 插入一条数据
     * @Description 插入一条数据
     * @Date 2021/1/16
     */
    public int insert(Model record) {
        return modelMapper.insert(record);
    }

    /**
     * @Author 陈迎博
     * @Title 根据主键删除数据
     * @Description 根据主键删除数据
     * @Date 2021/1/16
     */
    public int deleteById(String id) {
        return modelMapper.deleteById(id);
    }

    /**
     * @Author 陈迎博
     * @Title 根据主键更新一条数据
     * @Description
     * @Date 2021/1/16
     */
    public boolean updateById(Model record) {
        int count = modelMapper.updateById(record);
        return count > 0;
    }

    /**
     * @Author 陈迎博
     * @Title 根据条件查询总数
     * @Description 根据条件查询总数
     * @Date 2021/1/16
     */
    public int count(Model record) {

        LambdaQueryWrapper<Model> queryWrapper = null;
        if(null != record){
            queryWrapper = new LambdaQueryWrapper<Model>();
            queryWrapper.like(StringUtils.isNotBlank(record.getName()), Model::getName, record.getName());
        }
        Integer result = modelMapper.selectCount(queryWrapper);
        return result == null? 0: result.intValue();
    }

    /**
     * @Author 陈迎博
     * @Title 根据主键查询
     * @Description 根据主键查询一条数据
     * @Date 2021/1/16
     */
    public Model selectById(String id) {
        return modelMapper.selectById(id);
    }

    /**
     * @Author 陈迎博
     * @Title 分页查询
     * @Description 分页查询
     * @Date 2021/1/16
     */
    public IPage<Model> selectPage(Model record, Pagination pagination) {

        LambdaQueryWrapper<Model> queryWrapper = queryWrapper = new LambdaQueryWrapper<Model>();
        queryWrapper.orderByDesc(Model::getCreateDateTime);
        if(null != record){
            queryWrapper = queryWrapper.like(StringUtils.isNotBlank(record.getName()), Model::getName, record.getName());
        }

        Page page = null;
        if(null != pagination){
            page = new Page(pagination.getPageIndex(), pagination.getLimit());
        }
        return modelMapper.selectPage(page, queryWrapper);
    }
}
