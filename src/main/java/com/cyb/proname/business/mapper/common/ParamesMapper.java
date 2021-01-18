package com.cyb.proname.business.mapper.common;

import com.cyb.proname.business.domain.common.Param;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ParamesMapper {

    int deleteByPrimaryKey(String id);

    int insert(Param record);

    int insertSelective(Param record);

    Param selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Param record);

    int updateByPrimaryKey(Param record);

    Param selectOneByName(String name);

    List<Param> selectByParames(Param record);
}