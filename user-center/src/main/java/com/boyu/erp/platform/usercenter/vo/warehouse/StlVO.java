package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Stl;
import lombok.Data;

import java.util.List;

/**
 * 盘点清单view obj
 * @author HHe
 * @date 2019/9/23 11:12
 */
@Data
public class StlVO extends Stl {
    /**
     * 仓库编号
     */
    private String warehNum;
    /**
     * 仓库名称
     */
    private String warehCp;
    /**
     * 操作员编号
     */
    private String oprNum;
    /**
     * 操作员姓名
     */
    private String oprCp;
    /**
     * 盘点人编号
     */
    private String stkrNum;
    /**
     * 盘点人名称
     */
    private String stkrCp;
    /**
     * 货位管理中文
     */
    private String locAdoptedCp;
    /**
     * 库存形态中文
     */
    private String stkFormCp;
    /**
     * 进度中文
     */
    private String progressCp;
    /**
     * 挂起中文
     */
    private String suspendedCp;

    /**
     * 盘点清单明细
     */
    private List<StlDtlVO> stlDtlVOList;

}
