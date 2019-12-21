package com.boyu.erp.platform.usercenter.model.wareh;

import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttBrand;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttProdCat;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttProdCls;
import lombok.Data;

import java.util.List;

/**
 * 盘点表带范围明细对象
 * @author HHe
 * @date 2019/9/18 12:05
 */
@Data
public class SttModel extends Stt {
    /**
     * 仓库编号
     * @use 添加流程控制
     */
    private String warehCode;

    /**
     * 盘点表品牌集合
     */
    private List<SttBrand> sttBrandList;

    /**
     * 盘点表商品分类集合
     */
    private List<SttProdCat> sttProdCatList;

    /**
     * 盘点表商品品种集合
     */
    private List<SttProdCls> sttProdClsList;
}
