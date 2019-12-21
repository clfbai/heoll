package com.boyu.erp.platform.usercenter.vo.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.VdrSupplyProd;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @Classname VdrSupplyVo
 * @Description TODO
 * @Date 2019/8/5 10:53
 * @Created wz
 */
@Data
public class VdrSupplyProdVo extends VdrSupplyProd implements Serializable {
    /**
     * 判断权限
     */
    private Long sUnitId;
    /**
     * 商品品种代码  非空
     */
    private String prodClsCode;
    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 助记码
     */
    private String inputCode;
    /**
     * 款式
     */
    private String model;

    public VdrSupplyProdVo() {
    }

    public VdrSupplyProdVo(Long venderId ,Long vendeeId) {
        super.setVenderId(venderId);
        super.setVendeeId(vendeeId);
    }
}
