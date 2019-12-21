package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.San;
import lombok.Data;

import java.util.List;

/**
 * 库存调整表vo
 * @author HHe
 * @date 2019/10/6 17:08
 */
@Data
public class SanVO extends San {
    /**
     * 仓库编号
     */
    private String warehNum;
    /**
     * 仓库名称
     */
    private String warehCp;
    /**
     * 会计组织代码
     */
    private String fsclUnitCode;
    /**
     * 会计组织名称
     */
    private String fsclUnitCp;
    /**
     * 操作员编号
     */
    private String oprNum;
    /**
     * 操作员名称
     */
    private String oprCp;
    /**
     * 审核人编号
     */
    private String chkrNum;
    /**
     * 审核人名称
     */
    private String cnkrCp;
    /**
     * 记账人编号
     */
    private String acckNum;
    /**
     * 记账人名称
     */
    private String acckCp;
    /**
     * 货位管理（别名）
     */
    private String locAdoptedCp;
    /**
     * 库存形态（别名）
     */
    private String stkFormCp;
    /**
     * 调整类别（别名）
     */
    private String sadTypeCp;
    /**
     * 已生效（别名）
     */
    private String effectiveCp;
    /**
     * 进度（别名）
     */
    private String progressCp;
    /**
     * 挂起（别名）
     */
    private String suspendedCp;
    /**
     * 撤销（别名）
     */
    private String cancelledCp;
    /**
     * 库存明细集合
     */
    private List<SanDtlVO> sanDtlVOList;
}
