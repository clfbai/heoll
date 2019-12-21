package com.boyu.erp.platform.workflow.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;

/**
 * @Classname MyWorkController
 * @Description TODO
 * @Date 2019/9/9 18:56
 * @Created by wz
 */
public class MyWorkController {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //部署流程  第一步
    @Test
    public void deployProcess(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("bpmn/work.bpmn");//bpmn文件的名称
        builder.deploy();
    }
}
