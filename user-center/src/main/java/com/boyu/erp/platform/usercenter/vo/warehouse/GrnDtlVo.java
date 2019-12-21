package com.boyu.erp.platform.usercenter.vo.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class GrnDtlVo implements Serializable {

    /**
     * 组织ID
     */
    private long unitId;

    /**
     * 入库单编号
     */
    private String grnNum;
    /**
     * 商品Id
     */
    private Long prodId;
    /**
     * 颜色名称
     */
    private String colorName;
    /**
     * 商品代码
     */
    private String prodCode;

    /**
     * 助记码(来自商品品种)
     */
    private String inputCode;
    /**
     * 商品名称(来自商品品种)
     */
    private String prodName;
    /**
     * 序号(来自商品品种)
     */
    private String synSn;
    /**
     * 计量单位
     */
    private String uomName;
    /**
     * 规格
     */
    private String specId;
    /**
     * 版型名
     */
    private String editionCp;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 折率
     */
    private BigDecimal discRate;
    /**
     * 折后价
     */
    private BigDecimal fnlPrice;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 数量
     */
    private BigDecimal qty;
    /**
     * 预期数量
     */
    private BigDecimal expdQty;
    /**
     * 金额
     */
    private BigDecimal val;
    /**
     * 返利
     */
    private BigDecimal rwd;
    /**
     * 税款
     */
    private BigDecimal tax;

    /**
     * 市值
     */
    private BigDecimal mkv;

    /**
     * 市场单价
     */
    private BigDecimal mkUnitPrice;


    /**
     * 附加价格
     */
    private BigDecimal appPrice;

    /**
     * 单位成本
     */
    private BigDecimal unitCost;

    /**
     * 成本
     */
    private BigDecimal cost;
    /**
     * 备注
     */
    private String dtlRemarks;

    //基本字段
    /**
     * 约定会计日期("t","f")
     */
    private String fsclDateAptd;

    /**
     * 会计日期
     */
    private String fsclDate;

    /**
     * 已生效("t","f")
     */
    private String effective;

    /**
     * 逻辑仓库名称
     */
    private String lgcUnitName;
    /**
     * 逻辑仓库编号(代码)
     */
    private String lgcUnitCode;

    /**
     * 原始单据类别
     */
    private String srcDocType;
    /**
     * 原始单据编号
     */
    private String srcDocNum;
    /**
     * 合同号
     */
    private String cntrNum;
    /**
     * 总库存单编号
     */
    private String ldgStbNum;

    /**
     * 预期总数量
     */
    private BigDecimal ttlExpdQty;

    /**
     * 预期总箱数
     */
    private BigDecimal ttlExpdBox;

    /**
     * 总数量
     */
    private BigDecimal ttlQty;

    /**
     * 总箱数
     */
    private BigDecimal ttlBox;

    /**
     * 总包裹数
     */
    private Long ttlPack;

    /**
     * 总金额
     */
    private BigDecimal ttlVal;

    /**
     * 总返利
     */
    private BigDecimal ttlRwd;

    /**
     * 总税款
     */
    private BigDecimal ttlTax;

    /**
     * 总市值
     */
    private BigDecimal ttlMkv;

    /**
     * 总成本
     */
    private BigDecimal ttlCost;

    // 入库字段
    /**
     * 始发方代码
     */
    private String stUnitCode;
    /**
     * 始发方名称
     */
    private String stUnitName;
    /**
     * 始发方仓库代码
     */
    private String stWarehCode;
    /**
     * 始发方仓库名称
     */
    private String stWarehName;
    /**
     * 发货方编号(代码)
     */
    private String delivUnitCode;
    /**
     * 发货方名称
     */
    private String delivUnitName;
    /**
     * 发货方仓库代码
     */
    private String delivWarehCode;
    /**
     * 发货方仓库名称
     */
    private String delivWarehName;

    /**
     * 即时结算("t","f")
     */
    private String instStl;

    /**
     * 收货人编号
     */
    private String tkovrCode;
    /**
     * 收货人姓名
     */
    private String tkovrName;
    /**
     * 收货开始时间
     */
    private String tkovStTime;
    /**
     * 收货结束时间
     */
    private String tkovFinTime;

    /**
     * 接受人编号
     */
    private String rcvrCode;
    /**
     * 接受人姓名
     */
    private String rcvrName;
    /**
     * 接受时间
     */
    private String rcvTime;
    /**
     * 存放人编号
     */
    private String storerCode;

    /**
     * 存放人姓名
     */
    private String storerName;
    /**
     * 存放开始时间
     */
    private String strStTime;
    /**
     * 存放结束时间
     */
    private String strFinTime;
    /**
     * 启用配码("t","f")
     */
    private String bxiEnabled;
    /**
     * 启用装箱("t","f")
     */
    private String boxReqd;
    /**
     * 启用验收
     */
    private String acptReqd;

    /**
     * 启用分储
     */
    private String paReqd;

    /**
     * 预定装箱("t","f")
     */
    private String boxSchd;

    /**
     * 货位管理("t","f")
     */
    private String locAdopted;
    /**
     * 货位ID
     */
    private Long locId;
    /**
     * 桥接方式("d","t")
     */
    private String brdgMode;
    /*其他字段*/
    /**
     * 会计组织代码
     */
    private String fsclUnitCode;

    /**
     * 会计组织名称
     */
    private String fsclUnitName;
    /**
     * 发货会计会计组织代码
     */
    private String delivFsclUnitCode;
    /**
     * 发货会计会计组织名称
     */
    private String delivFsclUnitName;
    /**
     * 会计入库方式
     */
    private String fsclRcvMode;
    /**
     * 成本组名称
     */
    private String costGrpName;
    /**
     * 中转方编号
     */
    private String tranUnitCode;
    /**
     * 中转方名称
     */
    private String tranUnitName;

    //备注
    private String maxRemarks;
}
