package com.boyu.erp.platform.usercenter.model.wareh;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 出库任务筛选条件
 * @author HHe
 * @date 2019/9/4 15:26
 */
@Data
public class WarehDelivTaskFilterModel implements Serializable {
    /**
     * 切换组织Id
     */
    private Long sUnitId;
    /**
     * 仓库编号%
     */
    private String warehNum;
    /**
     * 仓库Id集合
     */
    private List<Long> warehIdList;
    /**
     * 最小入队日期
     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private String minJoinTime;
    /**
     * 最大入队日期
     */
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private String maxJoinTime;
    /**
     * 出库方式
     */
    private String delivMode;
    /**
     * 收货方编号%
     */
    private String rcvUnitNum;

    /**
     * 收货方Id集合
     */
    private List<Long> rcvUnitIdList;
    /**
     * 收货方仓库编号%
     */
    private String rcvWarehNum;

    /**
     * 收货方仓库Id集合
     */
    private List<Long> rcvWarehIdList;
    /**
     * 任务单据编号%
     */
    private String taskDocNum;
    /**
     * 挂起
     */
    private String suspended;
    /**
     * 组织分组Id集合
     */
    private List<Long> unitUgIds;
    /**
     * 仓库分组Id集合
     */
    private List<Long> warehUgIds;
    /**
     * 分组对应仓库Id
     */
    private List<Long> ugWarehIds;
}
