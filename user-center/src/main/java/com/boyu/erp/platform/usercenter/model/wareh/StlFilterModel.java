package com.boyu.erp.platform.usercenter.model.wareh;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 盘点清单过滤信息类
 * @author HHe
 * @date 2019/9/23 11:03
 */
@Data
public class StlFilterModel implements Serializable {
    /**
     * 组织Id
     */
    private Long unitId;
    /**
     * 清点编号%
     */
    private String stlNum;
    /**
     * 最小单据时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date minDocDate;
    /**
     * 最大单据时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date maxDocDate;
    /**
     * 盘点表编号%
     */
    private String sttNum;
    /**
     * 仓库Id
     */
    private List<Long> warehIds;
    /**
     * 仓库编号%
     */
    private String warehNum;
    /**
     * 进度
     */
    private String progress;

}
