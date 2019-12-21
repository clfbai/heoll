package com.boyu.erp.platform.usercenter.model.emp;

import com.boyu.erp.platform.usercenter.entity.employee.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmpOtherModel implements Serializable {
    /**
     * 员工奖惩记录添加集合对象
     */
    private List<EmplRwdPun> addEmplRwdPun = new ArrayList<>();
    /**
     * 员工奖惩记录修改集合对象
     */
    private List<EmplRwdPun> updateEmplRwdPun = new ArrayList<>();

    /**
     * 员工家庭成员添加集合对象
     */
    private List<EmplFamMem> addEmplFamMem = new ArrayList<>();
    /**
     * 员工家庭成员修改集合对象
     */
    private List<EmplFamMem> updateEmplFamMem = new ArrayList<>();

    /**
     * 员工履历添加集合对象
     */
    private List<EmplWorkExp> addEmplWorkExp = new ArrayList<>();
    /**
     * 员工履历修改集合对象
     */
    private List<EmplWorkExp> updateEmplWorkExp = new ArrayList<>();
    /**
     * 员工异动添加集合对象
     */
    private List<EmplChg> addEmplChg = new ArrayList<>();
    /**
     * 员工异动修改集合对象
     */
    private List<EmplChg> updateEmplChg = new ArrayList<>();
    /**
     * 员工教育经历添加集合对象
     */
    private List<EmplEduH> addEmplEduH = new ArrayList<>();
    /**
     * 员工教育经历修改集合对象
     */
    private List<EmplEduH> updateEmplEduH = new ArrayList<>();

    /**
     * 员工考核添加集合对象
     */
    private List<EmplEval> addEmplEval = new ArrayList<>();
    /**
     * 员工考核修改集合
     */
    private List<EmplEval> updateEmplEval = new ArrayList<>();

    /**
     * 员工薪资变动添加加集合对象
     */
    private List<EmplSalChg> addEmplSalChg = new ArrayList<>();
    /**
     * 员工薪资变动修改集合
     */
    private List<EmplSalChg> updateEmplSalChg = new ArrayList<>();


    /**
     * 员工调职记录添加加集合对象
     */
    private List<EmplPosTf> addEmplPosTf = new ArrayList<>();
    /**
     * 员工调职记录修改集合
     */
    private List<EmplPosTf> updateEmplPosTf = new ArrayList<>();
}
