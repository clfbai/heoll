package com.boyu.erp.platform.workflow.controller.Test;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @Classname Test
 * @Description TODO
 * @Date 2019/9/18 19:59
 * @Created by wz
 */
public class Test {

    public static void main(String[] args) throws IOException {
        //默认引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

//        IdentityService is = engine.getIdentityService();
//        for (int i = 1;i<=10 ; i++){
//            Group group = is.newGroup(i+"");
//            group.setName("A_"+i);
//            group.setType("T_A_"+i);
//            is.saveGroup(group);
//        }


//        List<Group> listgoods =  is.createGroupQuery().listgoods();
//        listgoods = listgoods.stream().filter(Group -> Group.getName().equals("A_10")).collect(toList());
//        System.err.println(listgoods.get(0).getId());

//        List<Group> listgoods =  is.createGroupQuery().listPage(0,5);
//        listgoods.stream().forEach(System.err::println);

//        long l = is.createGroupQuery().count();
//        System.err.println(l);

//        List<Group> listgoods =  is.createGroupQuery().orderByGroupId().asc().listgoods();
//        listgoods.stream().forEach(System.err::println);

//        engine.close();

        //动态生成
//        RepositoryService rs = engine.getRepositoryService();
//        DeploymentBuilder builder = rs.createDeployment();
        //将压缩文件存入数据库
        //        FileInputStream fis = new FileInputStream(new File(""));
        //        ZipInputStream zip = new ZipInputStream(fis);
        //        builder.addZipInputStream(zip);
//
//        builder.addBpmnModel("MY_DEMO",crear());
//        builder.deploy();

        //部署验证
//        RepositoryService rs = engine.getRepositoryService();
//        DeploymentBuilder builder = rs.createDeployment();
//        builder.addClasspathResource("bpmn/work.bpmn");
        //(该方法是关闭部署验证)builder.disableSchemaValidation();
        //(该方法是关闭部署验证流程)builder.disableBpmnValidation();
//        builder.deploy();


//        RepositoryService rs = engine.getRepositoryService();
//        DeploymentBuilder builder = rs.createDeployment();
//        builder.addClasspathResource("bpmn/work.bpmn");
//        Deployment dep = builder.deploy();
        //查询资源
//        InputStream is = rs.getResourceAsStream(dep.getId(),"bpmn/work.bpmn");

//        ProcessDefinition def = rs.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();
//        InputStream is = rs.getProcessModel(def.getId());
//        int count = is.available();
//        byte[] contents = new byte[count];
//        is.read(contents);
//        String result = new String(contents);
//        //输出
//        System.out.println(result);

        //流程图查询
        RepositoryService rs = engine.getRepositoryService();
        DeploymentBuilder builder = rs.createDeployment();
        builder.addClasspathResource("bpmn/work.bpmn");
        Deployment dep = builder.deploy();
        ProcessDefinition def = rs.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();
        InputStream is = rs.getProcessDiagram(def.getId());
        BufferedImage image = ImageIO.read(is);
        File file = new File("D:/boyuWorks/workflow-center/src/main/resources/a.png");
        if(!file.exists())file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ImageIO.write(image,"png",fos);
        fos.close();
        is.close();
    }

    private static BpmnModel crear() {
        //创建模型对象
        BpmnModel model = new BpmnModel();

        Process process = new Process();
        model.addProcess(process);
        process.setId("My Process");
        process.setName("My Process");

        //开始事件
        UserTask userTask = new UserTask();
        userTask.setId("userTask");
        userTask.setName("User Task");
        process.addFlowElement(userTask);

        //结束事件
        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent");
        process.addFlowElement(endEvent);

        //添加流程顺序
        process.addFlowElement(new SequenceFlow("startEvent","userTask"));
        process.addFlowElement(new SequenceFlow("userTask","endEvent"));

        return  model;
    }

}
