package com.cyb.proname.business.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class ModelSearchVO implements Serializable {

    private String name;

    private Integer age;

    private String description;

    private String dateTime;
}