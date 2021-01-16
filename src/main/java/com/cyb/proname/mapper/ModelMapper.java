package com.cyb.proname.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyb.common.pagination.Pagination;
import com.cyb.proname.domain.Model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ModelMapper extends BaseMapper<Model> {

}