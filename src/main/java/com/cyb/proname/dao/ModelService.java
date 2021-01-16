package com.cyb.proname.dao;

import com.cyb.common.pagination.Pagination;
import com.cyb.proname.domain.Model;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
   * @Author 陈迎博
   * @Description 模板服务层
   * @Date 2021/1/16
   */
public interface ModelService {

    int countByExample(Model record);

    int deleteById(String id);

    int insert(Model record);

    Model selectById(String id);

    List<Model> selectByExample(Model record, Pagination pagination);

    int updateById(@Param("record") Model record);
}
