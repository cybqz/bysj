package com.cyb.engcostms.vo;

import com.cyb.engcostms.domain.Material;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 * 物料表VO
 * @author generation
 */
@Data
public class MaterialVO extends Material {

    private String typeName;

    private String supplierName;
}
