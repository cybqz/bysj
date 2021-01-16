package com.cyb.proname.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyb.common.pagination.Pagination;
import com.cyb.proname.domain.Model;
import com.cyb.proname.mapper.ModelMapper;
import javax.annotation.Resource;
import java.util.List;

/**
   * @Author 陈迎博
   * @Description 模板服务层实现
   * @Date 2021/1/16
   */
public class ModelServiceImpl implements ModelService {

    @Resource
    private ModelMapper modelMapper;

    @Override
    public int countByExample(Model record) {

        QueryWrapper queryWrapper = new QueryWrapper<Model>();
        queryWrapper.lambda().like(true, "name", record.getName());
        return modelMapper.selectCount(queryWrapper);
    }

    @Override
    public int deleteById(String id) {
        return modelMapper.deleteById(id);
    }

    @Override
    public int insert(Model record) {
        return modelMapper.insert(record);
    }

    @Override
    public Model selectById(String id) {
        return modelMapper.selectById(id);
    }

    @Override
    public List<Model> selectByExample(Model record, Pagination pagination) {
        Page page = new Page(pagination.getPageIndex(), pagination.getPageCount());
        QueryWrapper queryWrapper = new QueryWrapper<Model>();
        queryWrapper.lambda().like(true, "name", record.getName());
        return modelMapper.selectPage(page, queryWrapper).getRecords();
    }

    @Override
    public int updateById(Model record) {
        return modelMapper.updateById(record);
    }
}
