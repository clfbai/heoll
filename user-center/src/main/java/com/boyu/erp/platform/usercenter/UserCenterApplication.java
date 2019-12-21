package com.boyu.erp.platform.usercenter;

import com.boyu.erp.platform.usercenter.utils.DateUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@EnableAsync
@RestController
@SpringBootApplication
@MapperScan(value = {"com.boyu.erp.platform.usercenter.mapper","com.boyu.erp.platform.usercenter.TPOS.mapper"})
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index() {
        return "User-Center微服务运营正常,现在的时间是：" + DateUtil.dateToString(new Date());
    }
}