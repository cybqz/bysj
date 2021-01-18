package com.cyb.proname;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author CYB
 */
@SpringBootApplication(scanBasePackages = {"com.cyb.*"})
@MapperScan({"com.cyb.proname.business.mapper"})
public class ApplicationRun {

    public static void main(String[] args) {

        SpringApplication.run(ApplicationRun.class, args);
    }
}
