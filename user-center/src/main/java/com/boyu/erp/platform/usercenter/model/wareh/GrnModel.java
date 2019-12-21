package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 入库单查询model
 *
 * @author
 */
@Data
@NoArgsConstructor
public class GrnModel implements Serializable {
    /**
     * 组织ID
     */
    @NotNull(message = "组织id不能为空")
    private Long unitId;

    /**
     * 入库单编号
     */
    @NotBlank(message = "入库单编号不能为空")
    private String grnNum;
    /**
     * 进度
     */
    private String progress;

    /**
     * 发货仓编号(发货仓库代码)
     */
    private String warehCode;

    /**
     * 发货方代码
     */
    private String delivUnitCode;

    /**
     * 发货仓编号(发货仓库代码)
     */
    private String delivWarehCode;

    /**
     * 基准日期 1 代表 业务 2 代表 会计
     */
    private Integer baseInst;

    /**
     * 会计日期开始时间
     */
    private String fsclDateFrom;

    /**
     * 会计日期结束时间
     */
    private String fsclDateTo;

    /**
     * 单据日期开始
     */
    private String docDateFrom;

    /**
     * 单据日期结束时间
     */
    private String docDateTo;

    /**
     * 入库方式
     */
    private String rcvMode;

    /**
     * 入库方式
     */
    private Long warehId;
    private List<StbDtl> stbDtls = new ArrayList<>();
}
