package com.cyb.engcostms.service;

import com.cyb.engcostms.domain.Parames;

import java.util.List;

public interface ParamesServices {

    int deleteByPrimaryKey(String id);

    int insert(Parames record);

    int insertSelective(Parames record);

    Parames selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Parames record);

    int updateByPrimaryKey(Parames record);

    List<Parames> selectByParames(Parames record);
}