package com.boyu.erp.platform.usercenter.model.shop;

import com.boyu.erp.platform.usercenter.entity.shop.ShopEmp;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class ShopEmpModel implements Serializable {
    private Long shopId;
    /**
     * 修改员工集合
     */
    private List<ShopEmp> updateEmp;
    /**
     * 增加员工集合
     */
    private List<ShopEmp> addEmp;
    /**
     * 删除员工集合
     */
    private List<ShopEmp> deleteEmp;
}
