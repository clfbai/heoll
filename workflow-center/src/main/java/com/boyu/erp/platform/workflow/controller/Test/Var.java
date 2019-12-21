package com.boyu.erp.platform.workflow.controller.Test;

import org.activiti.engine.*;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;

import java.util.UUID;

/**
 * @Classname Var
 * @Description TODO
 * @Date 2019/9/25 15:09
 * @Created by wz
 */
public class Var {

    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    public static void main(String[] args) {
        TaskService ts = processEngine.getTaskService();

        //创建任务
        Task task = ts.newTask(UUID.randomUUID().toString());
        task.setName("测试参数");
        ts.saveTask(task);

        RuntimeService run = processEngine.getRuntimeService();
        //获取当前流程节点信息
        Execution ex = run.createExecutionQuery().executionId(task.getId()).singleResult();
        ts.setVariable(task.getId(),"varString","world");

    }
}
