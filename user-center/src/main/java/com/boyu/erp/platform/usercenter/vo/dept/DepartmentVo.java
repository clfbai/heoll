package com.boyu.erp.platform.usercenter.vo.dept;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class DepartmentVo implements Serializable {
    /**
     * 部门编号
     */
    private String deptNum;
    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 上级部门ID
     */
    private Long supDeptId=0L;

    /**
     * 部门级别
     */
    private Integer deptLvl;

    /**
     * 部门职责
     */
    private String deptBiz;

    /**
     * 负责人ID
     */
    private Long manId;

    /**
     * 部门状态
     */
    private String deptStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 组织代码
     */
    private String unitCode;

    /**
     * 组织名称
     */
    @NotEmpty(message = "组织名称不能为空")
    private String unitName;
    /**
     * 组织层级
     */
    private String unitHierarchy;

    /**
     * 组织类型
     */
    private String unitType;

    /**
     * 执照类别
     */
    private String licType;

    /**
     * 执照编号
     */
    private String licNum;

    /**
     * 电话号码
     */
    private String telNum;

    /**
     * 传真号码
     */
    private String faxNum;

    /**
     * 电子邮件地址
     */
    private String emailAddr;

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
     * 网站
     */
    private String website;

    /**
     * 联络人ID
     */
    private Long lmId;
    /**
     * 联络人代码
     */
    private String lmCode;


    /**
     * 管理组织ID
     */
    private Long ctrlUnitId;

    /**
     * 是否共享
     */
    private String shared;

    /**
     * 可征募
     */
    private String recruitable;

    /**
     * 助记码
     */
    private String inputCode;

    /**
     * 序号
     */
    private String seqNum;

    /**
     * 组织状态
     */
    private String unitStatus;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 联系人名称
     */
    private String fullName;
    /**
     * 管理组织名称
     */
    private String ctrlUnitCode;
    /**
     * 管理组织名称
     */
    private String ctrlUnitName;
    /**
     * 负责人代码(负责人编号)
     */
    private String manCode;
    /**
     * 负责人名称
     */
    private String manName;
    /**
     * 是否有下级 false 有
     */
  // private Boolean last=null;

   // private List<DepartmentVo> treeDepts=null;
}
