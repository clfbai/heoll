package com.boyu.erp.platform.usercenter.vo.price;

import com.boyu.erp.platform.usercenter.entity.purchase.PpnScp;
import com.boyu.erp.platform.usercenter.entity.sales.SpnScp;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname PpnScpVo
 * @Description TODO
 * @Date 2019/8/26 14:31
 * @Created by wz
 */
@Data
public class SpnScpVo extends SpnScp implements Serializable {
    /**
     * 采购商编号
     */
    private String vendeeNum;
    /**
     * 采购商名称
     */
    private String vendeeName;

    /**
     * 供应商编号
     */
    private String venderNum;
    /**
     * 供应商名称
     */
    private String venderName;

    /**
     * 批量
     */
    private List<SpnScpVo> list;

    public SpnScpVo(Long unitId, String spnNum) {
        super();
        super.setUnitId(unitId);
        super.setSpnNum(spnNum);
    }
}
