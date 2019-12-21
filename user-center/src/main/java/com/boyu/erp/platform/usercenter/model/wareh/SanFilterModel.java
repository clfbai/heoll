package com.boyu.erp.platform.usercenter.model.wareh;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 库存调整单筛选对象
 * @author HHe
 * @date 2019/10/6 16:56
 */
@Data
public class SanFilterModel {
    /**
     * 组织Id
     */
    private Long unitId;
    /**
     * 调整单编号%
     */
    private String sanNum;
    /**
     * 最小库存时间
     */
    private Date minDocDate;
    /**
     * 最大库存时间
     */
    private Date maxDocDate;
    /**
     * 仓库编号%
     */
    private String warehNum;
    /**
     * 仓库Id集合
     */
    private List<Long> warehIds;
    /**
     * 进度
     */
    private String progress;
}

