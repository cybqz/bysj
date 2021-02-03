package com.cyb.proname.business.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.common.pagination.Pagination;
import com.cyb.proname.business.domain.common.Dictionaries;
import com.cyb.proname.business.mapper.common.DictionariesMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
   * @Author 陈迎博
   * @Description 参数服务层实现
   * @Date 2021/1/16
   */
@Service
public class DictionariesService extends ServiceImpl<DictionariesMapper, Dictionaries> {

    @Resource
    private DictionariesMapper dictionariesMapper;

    /**
     * @Author 陈迎博
     * @Title 插入一条数据
     * @Description 插入一条数据
     * @Date 2021/1/16
     */
    public int insert(Dictionaries record) {
        return dictionariesMapper.insert(record);
    }

    /**
     * @Author 陈迎博
     * @Title 根据主键删除数据
     * @Description 根据主键删除数据
     * @Date 2021/1/16
     */
    public int deleteById(String id) {
        return dictionariesMapper.deleteById(id);
    }

    /**
     * @Author 陈迎博
     * @Title 根据条件查询总数
     * @Description 根据条件查询总数
     * @Date 2021/1/16
     */
    public int count(Dictionaries record) {

        LambdaQueryWrapper<Dictionaries> queryWrapper = null;
        if(null != record){
            queryWrapper = new LambdaQueryWrapper<Dictionaries>();
            queryWrapper.like(StringUtils.isNotBlank(record.getName()), Dictionaries::getName, record.getName());
        }
        Integer result = dictionariesMapper.selectCount(queryWrapper);
        return result == null? 0: result.intValue();
    }

    /**
     * @Author 陈迎博
     * @Title 根据主键查询
     * @Description 根据主键查询一条数据
     * @Date 2021/1/16
     */
    public Dictionaries selectById(String id) {
        return dictionariesMapper.selectById(id);
    }

    /**
     * @Author 陈迎博
     * @Title 分页查询
     * @Description 分页查询
     * @Date 2021/1/16
     */
    public IPage<Dictionaries> selectPage(Dictionaries record, Pagination pagination) {

        LambdaQueryWrapper<Dictionaries> queryWrapper = queryWrapper = new LambdaQueryWrapper<Dictionaries>();
        queryWrapper.orderByDesc(Dictionaries::getCreateDateTime);
        if(null != record){
            queryWrapper = queryWrapper.like(StringUtils.isNotBlank(record.getName()), Dictionaries::getName, record.getName());
        }

        Page page = null;
        if(null != pagination){
            page = new Page(pagination.getPageIndex(), pagination.getLimit());
        }
        return dictionariesMapper.selectPage(page, queryWrapper);
    }
}
