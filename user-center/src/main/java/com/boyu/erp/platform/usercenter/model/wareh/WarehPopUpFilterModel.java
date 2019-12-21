package com.boyu.erp.platform.usercenter.model.wareh;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 仓库弹框过滤类
 * @author HHe
 * @date 2019/9/15 15:02
 */
@Data
public class WarehPopUpFilterModel implements Serializable {
    /**
     * 仓库Id
     * 如果是收货仓库，需要指定仓库所属组织Id；
     */
    private Long warehOwnerId=-1L;
    /**
     * 组织编号
     */
    private String unitCode;
    /**
     * 组织名称
     */
    private String unitName;
    /**
     * 助记码
     */
    private String inputCode;
    /**
     * 组织状态
     */
    private String unitStatus;
    /**
     * 筛选仓库id集合
     */
    List<Long> warehIds;
}
