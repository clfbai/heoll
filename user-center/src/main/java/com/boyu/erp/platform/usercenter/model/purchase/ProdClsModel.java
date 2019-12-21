package com.boyu.erp.platform.usercenter.model.purchase;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class ProdClsModel extends BaseData implements Serializable {
    /**
     * 组织Id
     */
    private Long unitId;

    private Long ctrlUnitId;

    /**
     * 商品品种ID  主键
     */
    private Long prodClsId;

    /**
     * 商品分类ID
     */
    private String prodCatId;

    /**
     * 商品品种代码  非空
     */
    private String prodClsCode;

    /**
     * 品牌ID
     */
    private String brandId;

    /**
     * 助记码
     */
    private String inputCode;

    /**
     * 商品名称
     */
    private String prodName;
    /**
     * 商品状态
     */
    private String goodsStatus;
    /**
     * 是否是管理员
     */
    private String isAdmin;
    /**
     * 是否共享
     */
    private String shared;
    /**
     * this 代表当前组织 other 代表其他组织
     */
    private String unitType = "this";

    private int isAudit;

    /**
     * 商品库存管理
     */
    private String  stkAdopted ;

    List<ProdClsModel> prodClsModels = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ProdClsModel)) {
            return false;
        }
        ProdClsModel model = (ProdClsModel) o;
        return Objects.equals(prodClsId, model.getProdClsId()) &&
                Objects.equals(prodClsCode, model.getProdClsCode());

    }

    @Override
    public int hashCode() {
        return Objects.hash(prodClsId, prodClsCode);
    }


}
