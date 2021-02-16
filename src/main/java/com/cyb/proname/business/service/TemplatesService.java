package com.cyb.proname.business.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.utils.WrapperUtil;
import com.cyb.proname.business.domain.Model;
import com.cyb.proname.business.mapper.ModelMapper;
import com.cyb.proname.business.vo.ModelSearchVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
   * @Author 陈迎博
   * @Description 模板服务层实现
   * @Date 2021/1/16
   */
@Service
public class TemplatesService extends ServiceImpl<ModelMapper,Model> {

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private WrapperUtil wrapperUtil;

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
     * @Title 根据条件查询总数
     * @Description 根据条件查询总数
     * @Date 2021/1/16
     */
    public int count(ModelSearchVO record) {

        LambdaQueryWrapper<Model> queryWrapper = commonGetQueryWrapper(record);
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
    public IPage<Model> selectPage(ModelSearchVO model, Pagination pagination) {

        LambdaQueryWrapper<Model> queryWrapper = commonGetQueryWrapper(model);

        Page page = null;
        if(null != pagination){
            page = new Page(pagination.getPageIndex(), pagination.getLimit());
        }
        return modelMapper.selectPage(page, queryWrapper);
    }

    private LambdaQueryWrapper<Model> commonGetQueryWrapper(ModelSearchVO model){

        LambdaQueryWrapper<Model> queryWrapper = queryWrapper = new LambdaQueryWrapper<Model>()
                .orderByDesc(Model::getCreateDateTime);
        if(null != model){
            queryWrapper.like(StringUtils.isNotBlank(model.getName()), Model::getName, model.getName());
        }
        SFunction<Model, LocalDateTime> column = Model::getCreateDateTime;
        wrapperUtil.appendDateTime(queryWrapper, column, model.getDateTime());
        return queryWrapper;
    }
}
