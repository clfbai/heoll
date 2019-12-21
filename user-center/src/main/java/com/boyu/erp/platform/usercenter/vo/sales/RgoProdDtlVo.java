package com.boyu.erp.platform.usercenter.vo.sales;

import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname RgoProdDtlVo
 * @Description TODO
 * @Date 2019/10/24 10:27
 * @Created by wz
 */
@Data
public class RgoProdDtlVo implements Serializable {

    /**
     * 调出方
     */
    private Long srcUnitId;
    /**
     * 调入方
     */
    private Long destUnitId;
    /**
     * 当前组织
     */
    private Long unitId;
    /**
     * 调配单类别
     */
    private String rgoType;

    /**
     * 数量
     */
    private int qty;

    /**
     * 详情
     */
    private List<RgoDtlVo> vo;

}
