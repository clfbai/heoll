package com.boyu.erp.platform.usercenter.model.emp;

import com.boyu.erp.platform.usercenter.entity.employee.*;
import com.boyu.erp.platform.usercenter.vo.Emp.EmployeeVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class EmployeeModel implements Serializable {
    private Long ownerId;

    private String empCode;

    private String empNum;

    private String empName;

    private String empStatus;
    /**
     * 部门Id
     */
    private Long deptId;

    /**
     * 员工、人员对象
     */
    @Valid
    @NotNull(message = "人员、员工信息不能为空")
    private EmployeeVo empvo;
    /**
     * 员工奖惩对象集合
     */
    List<EmplRwdPun> addEmplRwdPun = new ArrayList<>();
    /**
     * 员工家庭成员对象集合
     */
    List<EmplFamMem> addEmplFamMem = new ArrayList<>();
    /**
     * 员工履历对象集合
     */
    List<EmplWorkExp> addEmplWorkExp = new ArrayList<>();
    /**
     * 员工异动记录对象集合
     */
    List<EmplChg> addEmplChg = new ArrayList<>();
    /**
     * 员工教育经历对象集合
     */
    List<EmplEduH> addEmplEduH = new ArrayList<>();
    /**
     * 员工考核对象集合
     */
    List<EmplEval> addEmplEval = new ArrayList<>();
    /**
     * 员工薪资变动对象集合
     */
    List<EmplSalChg> addEmplSalChg = new ArrayList<>();
    /**
     * 员工调职记录
     */
    List<EmplPosTf> addEmplPosTf = new ArrayList<>();
}
