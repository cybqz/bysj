package com.cyb.goodsms.dao;

import com.cyb.common.pagenation.Pagenation;
import com.cyb.goodsms.domain.Stock;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockMapper {
    int countByExample(Stock record);

    int deleteById(String id);

    int insert(Stock record);

    Stock selectById(String id);

    List<Stock> selectByExample(Stock record, Pagenation pagenation);

    int updateById(@Param("record") Stock record);
}