package com.boyu.erp.platform.usercenter.vo.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class ShopVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 门店ID
     */
    private Long shopId;

    /**
     * 属主Id
     */
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
    private String openDateCp;

    /**
     * 停业日期
     */
    private String clsDateCp;

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
     * 门店操作员ID
     */
    private Long shopOprId;
    /**
     * 组织操作员ID
     */
    private Long unitOprId;
    /**
     * 更新时间
     */
    private String updTimeCp;


    //组织字段
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 组织代码(门店代码)
     */
    private String unitCode;

    /**
     * 组织名称(门店名称)
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
     * 组织状态(门店状态)
     */
    private String unitStatusCp;

    /**
     * 备注
     */
    private String remarks;
    /**
     * 联系人名称
     */
    private String fullName;



    /*中文名称*/
    /**
     * 零售圈名称
     */
    private String dfltRccIdCp;
    /**
     * 外部系统
     */
    private String exsysIdCp;
    /**
     * 门店编号
     */
    private String shopNum;
    /**
     * 上级门店编号
     */
    private String parnShopNum;
    /**
     * 上级门店名称
     */
    private String parnShopName;
    /**
     * 门店类别中文名称
     */
    private String shopTypeCp;
    /**
     * 是否代销中文名称
     */
    private String isCmsCp;
    /**
     * 上级组织代码(上级属主代码)
     */
    private String parnUnitCode;
    /**
     * 上级组织名称(上级属主名称)
     */
    private String parnUnitName;
    /**
     * 代销组织代码
     */
    private String cmsUnitCode;
    /**
     * 代销组织名称
     */
    private String cmsUnitName;
    /**
     * 门店区域代码中文别名 (区域(大区))  门店区域名称
     */
    private String shopAreaCodeCp;
    /**
     * 门店模式中文别名 (分公司 )
     */
    private String shopModeCp;
    /**
     * 结算方式中文别名
     */
    private String shopStlModeCp;
    /**
     * 面积级中文 别名 (物流方式)
     */
    private String acrLvlCp;
    /**
     * 销售级中文名称
     */
    private String salesLvlCp;
    /**
     * 统一租金中文名称
     */
    private String univRentValCp;
    /**
     * 接受VIP中文名称
     */
    private String vipAcptCp;
    /**
     * 统一扣点中文名称
     */
    private String univSpFmlCp;
    /**
     * 接受储值中文名称
     */
    private String depAcptCp;
    /**
     * 收银暂存中文名称
     */
    private String ctDepCp;
    /**
     * 收银员账户管理中文名称
     */
    private String ccEnabledCp;
    /**
     * 营业员业绩管理中文名称
     */
    private String svEnabledCp;
    /**
     * 门店状态中文名称
     */
    private String shopStatusCp;
    /**
     * 联络人代码(联系人代码)
     */
    private String lmPrsnlCode;
    /**
     * 联系人姓名
     */
    private String lmFullName;
    /**
     * 管理组织代码
     */
    private String ctrlUnitCode;
    /**
     * 管理组织名称
     */
    private String ctrlUnitName;
    /**
     * 缺省服务仓库编号(代码)
     */
    private String dfltSvWarehCode;
    /**
     * 缺省服务仓库名称
     */
    private String dfltSvWarehName;
    /**
     * 负责人编号
     */
    private String manCode;
    /**
     * 负责人姓名
     */
    private String manName;
    /**
     * 操作员代码
     */
    private String oprCode;
    /**
     * 操作员姓名
     */
    private String oprName;
    /**
     * 是否共享中文名称
     */
    private String sharedCp;
    /**
     * 执照类别
     */
    private String licTypeCp;

    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 商场名称
     */
    private String dpsName;
}
