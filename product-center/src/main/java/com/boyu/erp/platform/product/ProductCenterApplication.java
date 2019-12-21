package com.boyu.erp.platform.product;

import com.boyu.erp.platform.product.utils.DateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@RestController
@SpringBootApplication
public class ProductCenterApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProductCenterApplication.class, args);
    }

    @RequestMapping(value="/",method = {RequestMethod.GET,RequestMethod.POST})
    public String index()
    {
        return "Product-Center微服务运营正常,现在的时间是："+DateUtil.dateToString(new Date());
    }
}
