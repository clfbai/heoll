package com.boyu.erp.platform.usercenter.model.wareh;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 出库单列表筛选对象
 * @author HHe
 * @date 2019/11/12 9:40
 */
@Data
public class GdnFilterModel implements Serializable {
    /**
     * 切换组织Id
     */
    private Long sUnitId;
    /**
     * 组织Id
     */
    private Long unitId;
    /**
     * 基准日期（判断是业务日期筛选还是会计日期筛选）
     */
    private String dateType;
    /**
     * 库存单编号
     */
    private String stbNum;
    /**
     * 开始时间
     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String minTime;
    /**
     * 结束时间
     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String maxTime;
    /**
     * 仓库编号
     */
    private String warehNum;
    /**
     * 仓库Id集合
     */
    private List<Long> warehIdList;
    /**
     * 收货方编号
     */
    private String rcvUnitNum;
    /**
     * 收货方Id集合
     */
    private List<Long> rcvUnitIdList;
    /**
     * 收货仓库编号
     */
    private String rcvWarehNum;
    /**
     * 收货方仓库Id集合
     */
    private List<Long> rcvWarehIdList;
    /**
     * 进度
     */
    private String progress;
    /**
     * 组织分组
     */
    private List<Long> unitUgIds;
    /**
     * 仓库分组
     */
    private List<Long> warehUgIds;
    /**
     * 参与分组仓库Id
     */
    private List<Long> ugWarehIds;

    public Long getsUnitId() {
        return sUnitId;
    }

    public void setsUnitId(Long sUnitId) {
        this.sUnitId = sUnitId;
    }
}
