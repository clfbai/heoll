package com.boyu.erp.platform.usercenter.model.wareh;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 盘点单筛选对象
 * @author HHe
 * @date 2019/9/10 20:01
 */
@Data
public class SttFilterModel {
    /**
     * 组织Id
     */
    private Long unitId;
    /**
     * 盘点表编号
     */
    private String sttNum;
    /**
     * 最小单据日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date minDocDate;
    /**
     * 最大单据日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date maxDocDate;
    /**
     * 仓库ID
     */
    private List<Long> warehIds;
    /**
     * 仓库编号
     */
    private String warehNum;
    /**
     * 仓库代码
     */
    private String warehCode;
    /**
     * 进度
     */
    private String progress;

}
