package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.SanDtl;
import lombok.Data;

/**
 * 库存明细vo
 * @author HHe
 * @date 2019/10/7 10:58
 */
@Data
public class SanDtlVO extends SanDtl {
    private String prodCode;
    private String prodName;
    private String inputCode;
    private String uomCp;
    private String colorCp;
    private String specCp;
    private String editionCp;
}
