package com.cyb.engcostms.dao;

import com.beastmybatis.core.mapper.CrudMapper;

import com.cyb.engcostms.domain.Enquiry;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author generation
 */
public interface EnquiryMapper extends CrudMapper<Enquiry, String> {

    List<Enquiry> newestList(@Param("record") Enquiry record);
}
