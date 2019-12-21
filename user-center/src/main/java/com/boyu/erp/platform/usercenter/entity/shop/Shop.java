package com.boyu.erp.platform.usercenter.entity.shop;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * shop  门店
 *
 * @author
 */
@Data
public class Shop implements Serializable {
    /**
     * 门店ID
     */
    private Long shopId;

    /**
     * 属主ID
     */
    @NotNull(message = "门店属主不能为空")
    private Long ownerId;

    /**
     * 门店类别
     */
    private String shopType;

    /**
     * 上级门店ID
     */
    private Long parnShopId;

    /**
     * 是否代销
     */
    private String isCms;

    /**
     * 代销组织ID
     */
    private Long cmsUnitId;

    /**
     * 门店属性
     */
    private String shopProp;

    /**
     * 门店模式
     */
    private String shopMode;

    /**
     * 结算方式
     */
    private String shopStlMode;

    /**
     * 品牌ID
     */
    private String brandId;

    /**
     * 门店区域代码
     */
    private String shopAreaCode;

    /**
     * 商场ID
     */
    private String dpsId;

    /**
     * 缺省零售圈ID
     */
    private String dfltRccId;

    /**
     * 外部系统ID
     */
    private String exSysId;

    /**
     * 开业日期
     */
    @NotNull(message = "门店开业日期不能为空")
    private Date openDate;

    /**
     * 停业日期
     */
    private Date clsDate;

    /**
     * 所处楼层
     */
    private Long atFlr;

    /**
     * 面积
     */
    private Float acreage;

    /**
     * 面积级别
     */
    private String acrLvl;

    /**
     * 销售级别
     */
    private String salesLvl;

    /**
     * 配货权重
     */
    private Long admWgt;

    /**
     * 年保本额
     */
    private Float abeVal;

    /**
     * 最低零售折扣
     */
    private Float minRtDiscRate;

    /**
     * 赠券发放公式
     */
    private String gcFml;

    /**
     * VIP折扣公式
     */
    private String vipDiscFml;

    /**
     * 积分累积公式
     */
    private String pntAccFml;

    /**
     * 积分兑现公式
     */
    private String pntEncFml;

    /**
     * 电子赠券发放公式
     */
    private String egcPsFml;

    /**
     * 统一租金
     */
    private String univRentVal;

    /**
     * 租金
     */
    private Long rentVal;

    /**
     * 统一扣点
     */
    private String univSpFml;

    /**
     * 扣点公式
     */
    private String spFml;

    /**
     * 接受VIP
     */
    private String vipAcpt;

    /**
     * 接受储值
     */
    private String depAcpt;

    /**
     * 收银暂存
     */
    private String ctDep;

    /**
     * 收银员账户管理
     */
    private String ccEnabled;

    /**
     * 营业员业绩管理
     */
    private String svEnabled;

    /**
     * 缺省服务仓库ID
     */
    private Integer dfltSvWarehId;

    /**
     * 播放表ID
     */
    private String adsId;

    /**
     * 负责人ID
     */
    private Long manId;

    /**
     * 会计日期
     */
    private Date fsclDate;

    /**
     * 门店状态
     */
    private String shopStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    private static final long serialVersionUID = 1L;

    public Shop() {
    }

    public Shop(Long shopId, @NotNull(message = "门店属主不能为空") Long ownerId) {
        this.shopId = shopId;
        this.ownerId = ownerId;
    }
}