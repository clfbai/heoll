package com.boyu.erp.platform.usercenter.vo.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @program: boyu-platform_text
 * @description: 仓库返回数据模型
 * @author: clf
 * @create: 2019-06-26 15:44
 */
@Data
@ToString
@NoArgsConstructor
public class WarehVo {
    /**
     * 成本组名称
     */
    private String costGrpName;
    /**
     * 组织编号
     */
    private String unitNum;
    /**
     * 会计组织代码
     */
    private String swUnitCode;
    /**
     * 会计组织编号
     */
    private String swUnitName;
    /**
     * 管理组织名称
     */
    private String adUnitName;
    /**
     * 管理组织代码
     */
    private String adUnitCode;

    /**
     * 操作员姓名
     */
    private String adFullName;
    /**
     * 操作员代码
     */
    private String adPrsnlCode;


    /**
     * 联系人代码
     */
    private String  upsPrsnlCode;
    /**
     * 产权方姓名
     */
    private String  propUnitName;
    /**
     * 产权方代码
     */
    private String  propUnitCode;




    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 仓库属性
     */
    private String warehProp;

    /**
     * 库存管理
     */
    private String stkAdopted;

    /**
     * 唯一码管理
     */
    private String uidAdopted;

    /**
     * 允许负动存
     */
    private String negAtk;

    /**
     * 允许负库存
     */
    private String negStk;

    /**
     * 配码库存管理
     */
    private String astAdopted;

    /**
     * 装箱库存管理
     */
    private String bstAdopted;

    /**
     * 货位管理
     */
    private String locAdopted;

    /**
     * 允许货位负动存
     */
    private String negLocAtk;

    /**
     * 允许货位负库存
     */
    private String negLocStk;

    /**
     * 发货货位ID
     */
    private Integer delivLocId;

    /**
     * 收货货位ID
     */
    private Integer rcvLocId;

    /**
     * 打包货位ID
     */
    private Integer pckLocId;

    /**
     * 拆包货位ID
     */
    private Integer upkLocId;

    /**
     * 启用唯一码验收
     */
    private String acptUidReqd;

    /**
     * 启用唯一码分储
     */
    private String paUidReqd;

    /**
     * 启用唯一码分拣
     */
    private String pickUidReqd;

    /**
     * 启用唯一码复核
     */
    private String rckUidReqd;

    /**
     * 启用唯一码装箱
     */
    private String boxUidReqd;

    /**
     * 会计组织ID
     */
    private Long fsclUnitId;

    /**
     * 成本组ID
     */
    private Long costGrpId;

    /**
     * 产权方ID
     */
    private Long propOwnerId;

    /**
     * 仓库状态
     */
    private String warehStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 集货区管理
     */
    private String clnAreaAdopted;

    /**
     * 启用分货复核
     */
    private String clnRckReqd;





    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 组织代码(添加时为 仓库代码)
     */
    private String unitCode;

    /**
     * 组织名称(添加时为 仓库名称)
     */
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
     * 修改组织代码
     */
    private String updateUnitCode;
    /**
     * 修改组织名称
     */
    private String updateUnitName;



}
