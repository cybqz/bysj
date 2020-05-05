package com.cyb.engcostms.dao;

import com.beastmybatis.core.mapper.CrudMapper;

import com.cyb.common.pagination.Pagination;
import com.cyb.engcostms.domain.Enquiry;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author generation
 */
public interface EnquiryMapper extends CrudMapper<Enquiry, String> {

    List<Enquiry> newestList(@Param("materialName") String materialName, @Param("pagination") Pagination pagination);

    int newestListCount(@Param("materialName") String materialName);
}
