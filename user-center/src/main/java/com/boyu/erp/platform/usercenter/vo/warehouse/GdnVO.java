package com.boyu.erp.platform.usercenter.vo.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class GdnVO implements Serializable {
    /**
     * 组织ID√
     * NOT NULL
     */
    private Long unitId;

    /**
     * 出库单编号√
     * NOT NULL
     */
    private String gdnNum;

    /**
     * 出库方式√
     * NOT NULL
     */
    private String delivMode;

    /**
     * 会计出库方式
     */
    private String fsclDelivMode;

    /**
     * 收货方ID√
     */
    private Long rcvUnitId;

    /**
     * 收货仓库ID√
     */
    private Long rcvWarehId;

    /**
     * 收货会计组织ID
     */
    private Long rcvFsclUnitId;

    /**
     * 转送方ID
     */
    private Long endUnitId;

    /**
     * 转送仓库ID
     */
    private Long endWarehId;

    /**
     * 送货方式
     */
    private String delivMthd;

    /**
     * 邮政编码
     */
    private String delivPstd;

    /**
     * 送货地址
     */
    private String delivAddr;

    /**
     * 启用分拣√
     * NOT NULL
     */
    private String pickReqd;

    /**
     * 启用复核√
     * NOT NULL
     */
    private String rckReqd;

    /**
     * 启用派车√
     * NOT NULL
     */
    private String vehReqd;

    /**
     * 派车单编号
     */
    private String vwcNum;

    /**
     * 启用托运√
     * NOT NULL
     */
    private String cnsnReqd;

    /**
     * 托运单号
     */
    private String csbNum;

    /**
     * 拣货人ID
     */
    private Long ftchrId;

    /**
     * 拣货开始时间
     * NOT NULL
     * 默认为创建时间
     */
    private Date ftchStTime;

    /**
     * 拣货结束时间
     * NOT NULL
     * 默认为创建时间
     */
    private Date ftchFinTime;

    /**
     * 发货人ID
     */
    private Long dlvrId;

    /**
     * 发货时间
     * NOT NULL
     * 默认为创建时间
     */
    private Date delivTime;

    /**
     * 托运人ID
     */
    private Long cngnrId;

    /**
     * 托运时间
     * NOT NULL
     * 默认为创建时间
     */
    private Date cnsnTime;

    /**
     * 进度√
     * NOT NULL
     * 'pg':'已保存','cn':'','fi':'分拣中','fd':'已分拣','dd':'已出库','vi':'派车中',
     * 'vd':'已派车','ci':'托运中','cd':'已托运'
     */
    private String progress;

    /**
     * 自动执行√
     */
    private String autoExec;

    /**
     * 启用装箱("t","f")√
     * StbGdnVO
     */
    private String boxReqd;

    /**
     * 预定装箱("t","f")√
     * StbGdnVO
     */
    private String boxSchd;

    /**
     * 监控差异√
     */
    private String diffMtrd;

    /**
     * 差异控制√
     */
    private String diffCtrl;
    /**
     * 手工返利√
     */
    private String mnlRwd;
    /**
     * 即时结算("t","f")√
     * StbGdnVO
     */
    private String instStl;
    /**
     * 固定单价√
     */
    private String fixedUnitPrice;
    /**
     * 需指定收货方（判定必填）
     */
    private String rcvUnitReqd;
    /**
     * 需指定收货仓库（判定必填）
     */
    private String rcvWarehReqd;
    /**
     * 库存单
     */
    private StbGdnVO stb;
    /**
     * 库存单列表
     * 多个任务生成一张出库单时使用
     */
    private List<StbGdnVO> stbList;
}
