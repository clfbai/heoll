package com.boyu.erp.platform.usercenter.entity.goods;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * prod_cls(商品品种表)
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProdCls extends BaseData implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 成分
     */
    private String ingredient;
    /**
     * 是否淘汰
     */
    private String isWeed;
    /**
     * 商品品种ID  主键
     */
    private Long prodClsId;
    /**
     * 卖点
     */
    private String sellingPoints;
    /**
     * 商品品种代码  非空
     */
    private String prodClsCode;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 商品分类ID
     */
    private String prodCatId;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 性别
     */
    private String gender;

    /**
     * 套件属性   非空
     */
    private String suiteProp;

    /**
     * 套件ID
     */
    private Integer suiteClsId;

    /**
     * 款/楦号
     */
    private String model;

    /**
     * 子款号
     */
    private String subModel;

    /**
     * 主/鞋型
     */
    private String mainStyle;

    /**
     * 从/帮型
     */
    private String assisStyle;

    /**
     * 辅/跟型
     */
    private String subStyle;

    /**
     * 计量单位
     */
    private String uom;

    /**
     * 数量精度  非空
     */
    private BigDecimal qtyDigit;

    /**
     * 年份
     */
    private Short yearVal;

    /**
     * 适销季节
     */
    private String season;

    /**
     * 上市日期
     */
    private Date salesDate;

    /**
     * 过季日期
     */
    private Date expdDate;

    /**
     * 批次
     */
    private String batchNum;

    /**
     * 市场级别
     */
    private String mktGrd;

    /**
     * 营销系列
     */
    private String mktSort;

    /**
     * 营销类别
     */
    private String mktType;

    /**
     * 销售渠道
     */
    private String salesChnl;

    /**
     * 价格档
     */
    private String prcLvl;

    /**
     * 挂牌价
     */
    private BigDecimal lstPrice;

    /**
     * 面料
     */
    private String outMtrl;

    /**
     * 里料
     */
    private String inMtrl;

    /**
     * 填充物
     */
    private String stuffing;

    /**
     * 工艺
     */
    private String technology;

    /**
     * 设计说明
     */
    private String dsgnDesc;

    /**
     * 自产
     */
    private String selfMade;

    /**
     * 原料周期
     */
    private BigDecimal mtrlPerd;

    /**
     * 生产周期
     */
    private BigDecimal pdnPerd;

    /**
     * 补单周期
     */
    private BigDecimal adoPerd;

    /**
     * 投产起订量
     */
    private BigDecimal minPdnQty;

    /**
     * 补单起订量
     */
    private BigDecimal minAdoQty;

    /**
     * 生产成本
     */
    private BigDecimal pdnCost;

    /**
     * 停产
     */
    private String endOfPdn;

    /**
     * 产地
     */
    private String origin;

    /**
     * 厂商ID
     */
    private BigDecimal mfrId;

    /**
     * 厂商品牌ID
     */
    private String mfrBrandId;

    /**
     * 标准
     */
    private String prodStd;

    /**
     * 等级
     */
    private String prodGrd;

    /**
     * 设计方ID
     */
    private Integer dsgnUnitId;

    /**
     * 设计师ID
     */
    private Integer stylistId;

    /**
     * 版单号
     */
    private String pbpNum;

    /**
     * 产品风格
     */
    private String prodStyle;

    /**
     * 产品线
     */
    private String prodLine;

    /**
     * 故事线
     */
    private String storyLine;

    /**
     * 是否样品   非空
     */
    private String isSample;

    /**
     * 样品编号
     */
    private String sampleNum;

    /**
     * 库存管理  非空
     */
    private String stkAdopted;

    /**
     * 唯一码管理   非空
     */
    private String uidAdopted;

    /**
     * 库存级别
     */
    private String stkGrd;

    /**
     * QC比例
     */
    private BigDecimal qcPct;

    /**
     * 单位重量
     */
    private BigDecimal unitWgt;

    /**
     * 单位体积
     */
    private BigDecimal unitVol;

    /**
     * 单位长度
     */
    private BigDecimal unitLen;

    /**
     * 单位宽度
     */
    private BigDecimal unitWd;

    /**
     * 单位高度
     */
    private BigDecimal unitHt;

    /**
     * 标准装箱数
     */
    private Integer stdPackQty;

    /**
     * 标准箱重量
     */
    private BigDecimal stdPackWgt;

    /**
     * 标准箱体积
     */
    private BigDecimal stdPackVol;

    /**
     * 标准箱长度
     */
    private BigDecimal stdPackLen;

    /**
     * 标准箱宽度
     */
    private BigDecimal stdPackWd;

    /**
     * 标准箱高度
     */
    private BigDecimal stdPackHt;

    /**
     * 内包装
     */
    private String innerPck;

    /**
     * 打包类型
     */
    private String pckType;

    /**
     * 是否捎带品
     */
    private String isPgb;

    /**
     * 商品链接
     */
    private String prodLink;

    /**
     * 是否共享   非空 (F 不共享 代表私有化)
     */
    private String shared;

    /**
     * 管理组织ID
     */
    private Long ctrlUnitId;

    /**
     * 分颜色   非空
     */
    private String multiColor;

    /**
     * 颜色ID   非空
     */
    private Long colorId;

    /**
     * 颜色标注
     */
    private String colorCmt;

    /**
     * 分规格  非空
     */
    private String multiSpec;

    /**
     * 规格组ID
     */
    private String specGrpId;

    /**
     * 规格范围ID
     */
    private String specScpId;

    /**
     * 规格ID   非空
     */
    private Integer specId;

    /**
     * 规格标注
     */
    private String specCmt;

    /**
     * 分版型   非空
     */
    private String multiEdition;

    /**
     * 版型   非空
     */
    private String edition;

    /**
     * 版型标注
     */
    private String editionCmt;

    /**
     * 助记码
     */
    private String inputCode;

    /**
     * 标牌码
     */
    private String lblBc;

    /**
     * 序号
     */
    private Integer seqNum;

    /**
     * 描述
     */
    private String description;

    /**
     * 商品属性1
     */
    private String prodProp1;

    /**
     * 商品属性2
     */
    private String prodProp2;

    /**
     * 商品状态   非空
     */
    private String prodStatus;

    /**
     * 操作员ID
     */
    private Integer oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 同步序号
     */
    private Integer synSn;
    /**
     * 审核状态 se 保存 am 已审核
     */
    private String auditType;
    /**
     * 是否审核过 0 否 1是
     */
    private Integer isAudit;
}