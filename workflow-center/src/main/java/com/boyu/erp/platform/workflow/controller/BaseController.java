package com.boyu.erp.platform.workflow.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/***
 * 基类，所有的类都需要继承
 */
public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    public static void main(String[] args) {
        LOGGER.info("启动我们的程序");
        //1.创建流程引擎
        //先创建流程引擎配置对象
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        ProcessEngine processEngine = cfg.buildProcessEngine();
        String name = processEngine.getName();
        String version = processEngine.VERSION;
        LOGGER.info("流程引擎名称{}，版本{}", name, version);
        //2.部署流程定义文件
        //3.启动运行流程
        //4.处理流程任务
        LOGGER.info("结束我们的程序");
    }

    @Test
    public void creatTable() {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
    }






}