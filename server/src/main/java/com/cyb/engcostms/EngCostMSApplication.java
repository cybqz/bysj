package com.cyb.engcostms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author CYB
 */
@SpringBootApplication(scanBasePackages = {"com.cyb.*"})
@MapperScan({"com.cyb.engcostms.dao"})
public class EngCostMSApplication {

    public static void main(String[] args) {

        SpringApplication.run(EngCostMSApplication.class, args);
    }
}
