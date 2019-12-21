package com.boyu.erp.platform.workflow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@MapperScan("com.boyu.erp.platform.usercenter.mapper")
public class WorkflowCenterApplication {

    public static void main(String[] args) {

        SpringApplication.run(WorkflowCenterApplication.class, args);
    }
}
