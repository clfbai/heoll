package com.boyu.erp.platform.workflow.run;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        log.info("WorkFlow服务器启动成功！<<<<使用ApplicationRunner接口");
    }
}