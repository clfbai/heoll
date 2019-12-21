package com.boyu.erp.platform.usercenter.model.purchase;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyProdVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class VdrSupplyProdModel extends BaseData implements Serializable {
    /**
     * 判断权限用
     */
    private Long sUnitId;
    /**
     * 供应商  主键
     */
    private Long venderId;

    /**
     * 采购商  主键
     */
    private Long vendeeId;

    /**
     * 添加商品集合
     */
    private List<VdrSupplyProdVo> add ;


    /**
     * 删除商品集合
     */
    private List<VdrSupplyProdVo> delete ;

    /**
     * 操作人id
     */
    private Long oprId;
}
