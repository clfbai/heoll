package com.boyu.erp.platform.usercenter.run;

import com.boyu.erp.platform.usercenter.utils.common.TablePrivRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 初始化应用接口，可以做些配置之类的
 */
@Slf4j
@Component
@Order(value = 1)
public class ApplicationStartRunner implements ApplicationRunner {

    @Autowired
    private TablePrivRedisUtils tablePrivRedisUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 初始化
         * 1. 将需要过滤的表加载到redis
         * */
        //tablePrivRedisUtils.PrivReis();
        log.info("服务器启动成功！<<<<使用ApplicationRunner接口");
    }
}