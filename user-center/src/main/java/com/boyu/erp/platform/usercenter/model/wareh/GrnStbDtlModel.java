package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.vo.warehouse.GrnGoodsDtl;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class GrnStbDtlModel extends GrnGoodsDtl implements Serializable {
    /**
     * 组织ID
     */
    @NotNull(message = "组织id不能为空")
    private Long unitId;
    /**
     * 入库方式
     */
    private String rcvMode;
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
     * 原始单据编号
     */
    private String srcDocType;

    /**
     * 原始单据编号
     */
    private String srcDocNum;

    private String type;


}
