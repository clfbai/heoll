package com.boyu.erp.platform.usercenter.vo.Emp;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class EmployeeVo implements Serializable {

    /**
     * 操作人代码(操作员编号)
     */
    private String orpCode;
    /**
     * 操作人代码(操作员姓名)
     */
    private String orpName;
    /**
     * 员工编号
     */
    @NotNull(message = "员工编号不能为空")
    private String empNum;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门编号
     */
    private String deptNum;
    /**
     * 人员状态中文别名
     */
    private String prsnlStatusCp;
    /**
     * 员工状态中文别名
     */
    private String emplStatusCp;
    /**
     * 性别中文别名
     */
    private String genderCp;
    /**
     * 证件类型别名
     */
    private String idTypeCp;
    /**
     * 知否共享中文别名
     */
    private String sharedCp;

    /**
     * 员工ID
     */
    private Long emplId;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 行号
     */
    private Integer lineNum;
    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 照片(照片路径)
     */
    private String portrait;

    /**
     * 职位
     */
    private String jobPos;

    /**
     * 岗位
     */
    private String station;

    /**
     * 工作地点
     */
    private String workplace;

    /**
     * 入职日期
     */
    private String enrlDateCp;

    /**
     * 离职日期
     */
    private String dimsDateCp;
    /**
     * 生日
     */
    private String birthdayCp;

    /**
     * 毕业日期
     */
    private String gradDateCp;
    /**
     * 更新时间
     */
    private String updTime;

    /**
     * 离职原因
     */
    private String dimsRsn;

    /**
     * 时间表ID
     */
    private String wttId;


    /**
     * 婚姻状态
     */
    private String mrtStatus;

    /**
     * 政治面貌
     */
    private String party;

    /**
     * 民族
     */
    private String folk;

    /**
     * 籍贯
     */
    private String natvPlc;

    /**
     * 户籍地址
     */
    private String regAddr;

    /**
     * 学历
     */
    private String acadDeg;

    /**
     * 学校
     */
    private String school;

    /**
     * 专业
     */
    private String specialty;


    /**
     * 职称
     */
    private String techTtl;

    /**
     * 专长
     */
    private String skill;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 银行账号
     */
    private String bankAcNum;

    /**
     * 员工状态
     */
    private String emplStatus;

    /**
     * 操作员ID
     */
    private Long oprId;


    /**
     * 人员ID
     */
    private Long prsnlId;

    /**
     * 人员代码(员工代码)
     */
    @NotNull(message = "员工代码不能为空")
    private String prsnlCode;
    /**
     * 人员所在组织层级
     */
    private String unitHierarchy;
    /**
     * 姓氏
     */
    private String surname;

    /**
     * 名字
     */
    private String givenName;

    /**
     * 全名(员工姓名)
     */
    private String fullName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 证件类别
     */
    private String idType;

    /**
     * 证件编号
     */
    private String idNum;

    /**
     * 办公电话号码
     */
    private String officeNum;

    /**
     * 移动电话号码
     */
    private String mobileNum;

    /**
     * 家庭电话号码
     */
    private String homeNum;

    /**
     * 传真号码
     */
    private String faxNum;

    /**
     * 电码
     */
    private String tlgpNum;

    /**
     * 电子邮件地址
     */
    private String emailAddr;

    /**
     * 即时工具ID
     */
    private String imId;

    /**
     * 邮政编码
     */
    private String postcode;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县
     */
    private String district;

    /**
     * 地址
     */
    private String address;

    /**
     * 管理组织ID
     */
    private Long ctrlUnitId;

    /**
     * 是否共享
     */
    private String shared;

    /**
     * 助记码
     */
    private String inputCode;

    /**
     * 序号
     */
    private String seqNum;

    /**
     * 人员状态
     */
    private String prsnlStatus;
    /**
     * 修改前人员状态
     */
    private String updatePrsnlStatus;

    /**
     * 备注
     */
    private String remarks;
}
