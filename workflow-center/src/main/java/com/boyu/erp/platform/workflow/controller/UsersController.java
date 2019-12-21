package com.boyu.erp.platform.workflow.controller;

import com.boyu.erp.platform.workflow.result.JsonResult;
import com.boyu.erp.platform.workflow.result.JsonResultCode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/workflow")
public class UsersController
{

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //启动流程  第二部
    @Test
    public void startProcess(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        runtimeService.startProcessInstanceByKey("work");//流程的名称，对应流程定义表的key字段，也可以使用ByID来启动流程
    }


}
