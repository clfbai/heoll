package com.boyu.erp.platform.usercenter.vo.price;

import com.boyu.erp.platform.usercenter.entity.Price.PpnDtl;
import com.boyu.erp.platform.usercenter.entity.sales.SpnDtl;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname SpnDtlVo
 * @Description TODO
 * @Date 2019/8/26 9:54
 * @Created by wz
 */
@Data
public class SpnDtlVo extends SpnDtl implements Serializable {

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
     * 序号
     */
    private Integer seqNum;
    /**
     * 计量单位
     */
    private String uom;
    /**
     * 零售单价
     */
    private Float rtUnitPrice;

    /**
     * 批量数据
     */
    private List<SpnDtlVo> list;

    public SpnDtlVo() {

    }
    public SpnDtlVo(Long unitId, String spnNum) {
        super.setUnitId(unitId);
        super.setSpnNum(spnNum);
    }

}
