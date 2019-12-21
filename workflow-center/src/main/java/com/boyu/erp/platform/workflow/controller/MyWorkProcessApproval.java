package com.boyu.erp.platform.workflow.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.junit.Test;

/**
 * @Classname MyWorkProcessApproval
 * @Description TODO
 * @Date 2019/9/9 19:44
 * @Created by wz
 */
public class MyWorkProcessApproval {


    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void startProcessApproval(){

        TaskService taskService = processEngine.getTaskService();
        //taskId 就是查询任务中的 ID
        String taskId = "5002";
        //完成请假申请任务
        taskService.complete(taskId);

    }

}
