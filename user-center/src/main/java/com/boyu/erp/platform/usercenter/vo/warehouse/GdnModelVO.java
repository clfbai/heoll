package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.boyu.erp.platform.usercenter.model.wareh.GdnModel;
import lombok.Data;

@Data
public class GdnModelVO extends GdnModel {
    /**
     * 仓库编号
     */
    private String warehNum;
    /**
     * 仓库名称
     */
    private String warehName;
    /**
     * 唯一码管理
     */
    private String uidAdopted;
    /**
     * 唯一码管理（中文）
     */
    private String uidAdoptedCp;
    /**
     * 会计组织代码
     */
    private String fsclUnitCode;
    /**
     * 会计组织名称
     */
    private String fsclUnitName;
    /**
     * 原始单据类别
     */
    private String srcDocTypeCp;
    /**
     * 货位管理
     */
    private String locAdoptedCp;
    /**
     * 桥接方式
     */
    private String brdgModeCp;
    /**
     * 启用配码
     */
    private String bxiEnabledCp;
    /**
     * 启用装箱
     */
    private String boxReqdCp;
    /**
     * 操作员编号
     */
    private String oprNum;
    /**
     * 操作员姓名
     */
    private String oprName;
    /**
     * 已生效
     */
    private String effectiveCp;
    /**
     * 挂起
     */
    private String suspendedCp;
    /**
     * 撤销
     */
    private String cancelledCp;
    /**
     * 出库方式
     */
    private String delivModeCp;
    /**
     * 会计出库方式
     */
    private String fsclDelivModeCp;
    /**
     * 成本组名称
     */
    private String costGrpName;
    /**
     * 中转方编号
     */
    private String tranUnitNum;
    /**
     * 中转方名称
     */
    private String tranUnitName;
    /**
     * 收货方代码
     */
    private String rcvUnitNum;
    /**
     * 收货方名称
     */
    private String rcvUnitName;
    /**
     * 收货方仓库代码
     */
    private String rcvWarehNum;
    /**
     * 收货方仓库名称
     */
    private String rcvWarehName;
    /**
     * 收货方会计组织代码
     */
    private String rcvFsclUnitCode;
    /**
     * 收货方会计组织名称
     */
    private String rcvFsclUnitName;
    /**
     * 转送方代码
     */
    private String endUnitNum;
    /**
     * 转送方名称
     */
    private String endUnitName;
    /**
     * 转送仓库编号
     */
    private String endWarehNum;
    /**
     * 转送仓库名称
     */
    private String endWarehName;
    /**
     * 送货方式
     */
    private String delivMthdCp;
    /**
     * 约定会计日期
     */
    private String fsclDateAptdCp;
    /**
     * 启用分拣
     */
    private String pickReqdCp;
    /**
     * 启用复核
     */
    private String rckReqdCp;
    /**
     * 预定装箱
     */
    private String boxSchdCp;
    /**
     * 即时结算
     */
    private String instStlCp;
    /**
     * 启用派车
     */
    private String vehReqdCp;
    /**
     * 启用托运
     */
    private String cnsnReqdCp;
    /**
     * 拣货人编号
     */
    private String ftchrNum;
    /**
     * 拣货人姓名
     */
    private String ftchrName;
    /**
     * 发货人编号
     */
    private String dlvrNum;
    /**
     * 发货人姓名
     */
    private String dlvrName;
    /**
     * 托运人编号
     */
    private String cngnrNum;
    /**
     * 托运人姓名
     */
    private String cngnrName;
    /**
     * 进度
     */
    private String progressCp;
    /**
     * 货位编号
     */
    private String locNum;
}
