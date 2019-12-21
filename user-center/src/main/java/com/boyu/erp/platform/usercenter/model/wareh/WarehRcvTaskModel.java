package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WarehRcvTaskModel extends BaseData implements Serializable {

    private Long unitId;

    private Long warehId;

    /**
     * 入库方式
     */
    private String rcvMode;
    /**
     * 开始时间
     */
    private String bengTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 发货方ID
     */
    private Long delivUnitId;

    /**
     * 发货仓库ID
     */
    private Long delivWarehId;
    /**
     * 发货仓库编号
     */
    private String delivWarehCode;
    /**
     * 发货方编号
     */
    private String delivUnitCode;
    /**
     * 挂起
     */
    private String suspended;

    /**
     * 任务单据编号
     */
    private String taskDocNum;
    /**
     * 仓库编号
     */
    private String waerhNum;
    /**
     * 组合 0,1,2.....
     */
    private Integer group;
}
