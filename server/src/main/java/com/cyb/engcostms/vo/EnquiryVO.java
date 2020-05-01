package com.cyb.engcostms.vo;

import com.cyb.engcostms.domain.Enquiry;
import lombok.Data;

/**
 * 询价表VO
 */
@Data
public class EnquiryVO extends Enquiry {

    private String materialName;

    private String supplierName;
}
