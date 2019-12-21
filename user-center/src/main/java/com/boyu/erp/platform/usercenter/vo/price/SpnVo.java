package com.boyu.erp.platform.usercenter.vo.price;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname PpnVo
 * @Description TODO
 * @Date 2019/8/26 9:54
 * @Created by wz
 */
@Data
@NoArgsConstructor
public class SpnVo implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 价格单编号
     */
    private String spnNum;
    /**
     * 单据日期
     */
    private String docDate;

    /**
     * 价格类别
     */
    private String xpType;

    /**
     * 定价范围
     */
    private String prcScp;

    /**
     * 保留组织定价
     */
    private String rsvUnit;

    /**
     * 组织层级ID
     */
    private String unitHierId;

    /**
     * 向下传递
     */
    private String handOn;

    /**
     * 定价原因
     */
    private String prcRsn;

    /**
     * 生效日期
     */
    private String effDate;

    /**
     * 失效日期
     */
    private String expdDate;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作员编号
     */
    private String oprNum;

    /**
     * 操作员姓名
     */
    private String oprName;

    /**
     * 操作时间
     */
    private String opTime;

    /**
     * 审核人ID
     */
    private Long chkrId;

    /**
     * 审核人编号
     */
    private String chkrNum;

    /**
     * 审核人姓名
     */
    private String chkrName;

    /**
     * 审核时间
     */
    private String chkTime;

    /**
     * 执行人ID
     */
    private Long extrId;

    /**
     * 执行人编号
     */
    private String extrNum;

    /**
     * 执行人姓名
     */
    private String extrName;

    /**
     * 执行时间
     */
    private String execTime;

    /**
     * 已生效
     */
    private String effective;

    /**
     * 进度
     */
    private String progress;

    /**
     * 挂起
     */
    private String suspended;

    /**
     * 撤销
     */
    private String cancelled;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 搜索条件 开始日期
     */
    private String startTime;

    /**
     * 搜索条件结束日期
     */
    private String endTime;

    /**
     * 批量明细
     */
    private List<SpnDtlVo> dtlList;
    /**
     * 批量范围
     */
    private List<SpnScpVo> scpList;
}
