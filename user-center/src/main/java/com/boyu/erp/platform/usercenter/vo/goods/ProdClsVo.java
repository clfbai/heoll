package com.boyu.erp.platform.usercenter.vo.goods;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 商品品种返回数据模型
 * @author: clf
 * @create: 2019-06-12 09:40
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProdClsVo  {
    private static final long serialVersionUID = 1L;
    /**
     * 是否淘汰
     */
    private String isWeed;
    /**
     * 是否审核过 0 否 1是
     */
    private Integer isAudit;
    /**
     * 设计师代码
     */
    private String dsunPrsnlCode;
    /**
     * 成分
     */
    private String ingredient;
    /**
     * 设计师名称
     */
    private String dsunFullName;

    /**
     * 设计方名称
     */
    private String dsgnUnitName;
    /**
     * 设计方代码
     */
    private String dsgnUnitCode;
    /**
     * 厂商代码
     */
    private String mftCode;
    /**
     * 厂商名称
     */
    private String mftName;
    /**
     * 颜色代码
     */
    private String colorCode;
    /**
     * 颜色名称
     */
    private String colorName;
    /**
     * 卖点
     */
    private String sellingPoints;
    /**
     * 规格组名称
     */
    private String specGrpName;
    /**
     * 规格范围名称
     */
    private String specScpName;

    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 组织Id
     */
    private Long unitId;

    /**
     * 人员代码
     */
    private String prsnlCode;

    /**
     * 全名
     */
    private String fullName;

    /**
     * 组织代码
     */
    private String unitCode;

    /**
     * 组织名称
     */
    private String unitName;

    /**
     * 供应商代码
     */
    private String venderCode;

    /**
     * 供应商名称
     */
    private String venderName;

    /**
     * 商品品种ID
     */
    private Long prodClsId;

    /**
     * 销售方式
     */
    private String salesMode;

    /**
     * 推荐等级
     */
    private Integer rcmdLvl;

    /**
     * 零售单价
     */
    private Float rtUnitPrice;

    /**
     * 分销单价
     */
    private Float wsUnitPrice;

    /**
     * 分销税率
     */
    private Float wsTaxRate;

    /**
     * 采购单价
     */
    private Float puUnitPrice;

    /**
     * 采购税率
     */
    private Float puTaxRate;

    /**
     * 供应商ID
     */
    private Integer venderId;


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
    private Integer suiteClsId = 0;

    /**
     * 款/楦号
     */
    private String model;

    /**
     * 子款号
     */
    private String subModel;

    /**
     * 主/鞋型(形状)
     */
    private String mainStyle;

    /**
     * 从/帮型（面料属性)
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
     * 厂商品牌ID(对应厚薄度)
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
     * 储存仓库 （产品线)
     */
    private String prodLine;
    /**
     * 楼层 STORY_LINE(楼层)
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
    private Long specId;

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
    @NotEmpty(message="商品状态不能为空")
    private String prodStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updTime;

    /**
     * 同步序号
     */
    private Integer synSn;

    /**
     * 商品小图
     */
    private byte[] prodThumb;

    /**
     * 商品图片
     */
    private byte[] prodPict;

    /**
     * 详情
     */
    private String detail;

    /**
     * 商品状态 中文名称
     */
    private String prodStatusCp;
    /**
     * 分颜色 中文名称
     */
    private String multiColorCp;
    /**
     * 分规格 中文名称
     */
    private String multiSpecCp;

    /**
     * 唯一码管理 中文名称
     */
    private String uidAdoptedCp;
    /**
     * 唯一码管理 中文名称
     */
    private String stkAdoptedCp;
    /**
     * 是否样品 中文名称
     */
    private String isSampleCp;
    /**
     * 停产 中文名称
     */
    private String endOfPdnCp;
    /**
     * 是否稍带品 中文名称
     */
    private String isPgbCp;
    /**
     * 分罩杯 中文名称
     */
    private String multiEditionCp;
    /**
     * 是否淘汰 中文名称
     */
    private String isWeedCp;
    /**
     * 是否共享 中文名称
     */
    public String sharedCp;

    /**
     * 性别 中文名称
     */
    private String genderCp;
    /**
     * 套件属性 中文名称
     */
    public String suitePropCp;
    /**
     * 从帮型 中文名称
     */
    public String assisStyleCp;
    /**
     * 主鞋型 中文名称
     */
    public String mainStyleCp;
    /**
     * 计量单位 中文名称
     */
    public String uomCp;
    /**
     * 适销季节 中文名称
     */
    public String seasonCp;

    /**
     * 市场级别 中文名称
     */
    private String mktGrdCp;
    /**
     * 营销系列 中文名称
     */
    private String mktSortCp;

    /**
     * 营销类别 中文名称
     */
    private String mktTypeCp;
    /**
     * 销售渠道 中文名称
     */
    private String salesChnlCp;
    /**
     * 价格档
     */
    private String prcLvlCp;

    /**
     * 版型   中文名称
     */
    private String editionCp;
    /**
     * 面料 中文名称
     */
    private String outMtrlCp;

    /**
     * 里料 中文名称
     */
    private String inMtrlCp;
    /**
     * 标准 中文名称
     */
    private String prodStdCp;
    /**
     * 等级 中文名称
     */
    private String prodGrdCp;


    /**
     * 产品风格 中文名称
     */
    private String prodStyleCp;

    /**
     * 故事线  中文名称 （STORY_LINE(楼层)
     */
    private String storyLineCp;

    /**
     * 库存级别  中文名称
     */
    private String stkGrdCp;
    /**
     * 内包装 中文名称
     */
    private String innerPckCp;

    /**
     * 打包类型 中文名称
     */
    private String pckTypeCp;
    /**
     * 销售方式 中文名称
     */
    private String salesModeCp;

    /**
     * 子款号 中文名称
     */
    private String subModelCp;
    /**
     * 厂商品牌ID 中文名称 (对应厚薄度)
     */
    private String mfrBrandIdCp;
    /**
     * 产品线(储存仓库名称)
     */
    public String prodLineCp;

    /**
     * 规格ID  中文名称
     */
    private String specIdCp;

    /**
     * 规格代码
     */
    private String specCode;
    /**
     * 上市日期
     */
    private String salesDateCp;
    /**
     * 过季日期
     */
    private String expdDateCp;
    /**
     * 审核状态 se 待审核 am 已审核
     */
    private String auditType;
    /**
     * 审核状态 s中文名称
     */
    private String auditTypeCp;
    /**
     * 商品明细添加集合
     */
    private List<ProductVo> addProduct = new ArrayList<>();
    /**
     * 商品品种添加集合(可以包含商品明细)
     */
    private List<ProdClsVo> addProdCls = new ArrayList<>();
}
