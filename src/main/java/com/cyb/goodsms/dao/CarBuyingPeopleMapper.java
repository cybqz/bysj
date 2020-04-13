package com.cyb.goodsms.dao;

import com.cyb.goodsms.domain.CarBuyingPeople;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarBuyingPeopleMapper {
    int countByExample(CarBuyingPeopleExample example);

    int deleteByExample(CarBuyingPeopleExample example);

    int insert(CarBuyingPeople record);

    int insertSelective(CarBuyingPeople record);

    List<CarBuyingPeople> selectByExample(CarBuyingPeopleExample example);

    int updateByExampleSelective(@Param("record") CarBuyingPeople record, @Param("example") CarBuyingPeopleExample example);

    int updateByExample(@Param("record") CarBuyingPeople record, @Param("example") CarBuyingPeopleExample example);
}