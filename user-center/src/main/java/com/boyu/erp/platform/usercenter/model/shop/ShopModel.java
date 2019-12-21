package com.boyu.erp.platform.usercenter.model.shop;

import com.boyu.erp.platform.usercenter.entity.shop.*;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ShopModel extends Shop implements Serializable {
    /**
     * 门店属主
     */
    private Long unitId;
    /**
     * 门店代码
     */
    private String unitCode;
    /**
     * 门店编号
     */
    @NotNull(message = "门店编号不能为空")
    private String shopNum;
    /**
     * 门店状态
     */
    private String shopStatus;
    /**
     * 门店名称
     */
    private String unitName;
    /**
     * 修改门店编号
     */
    private String updateShopNum;

    //上面是查询使用到字段
    /**
     * 门店组织集合
     */
    @Valid
    @NotNull(message = "组织信息不能为空")
    SysUnit shopUnit;
    /**
     * 门店员工集合(增加、修改)
     */
    List<ShopEmp> shopEmps = new ArrayList<>();
    /**
     * 门店属性集合
     */
    List<ShopAttr> shopAtts = new ArrayList<>();
    /**
     * 门店付款方式集合
     */
    List<ShopPayMode> shopPayModes = new ArrayList<>();
    /**
     * 门店租金集合
     */
    List<ShopRentVal> shopRentVals = new ArrayList<>();
    /**
     * 门店扣点公式
     */
    List<ShopSpFml> shopSpFmls = new ArrayList<>();
    /**
     * 门店促销公式
     */
    List<ShopGpFml> shopGpFmls = new ArrayList<>();

    List<ShopModel> list = new ArrayList<>();
}
