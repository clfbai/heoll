package com.boyu.erp.platform.usercenter.model;

import com.boyu.erp.platform.usercenter.entity.goods.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProdClsdText {

    private Long prodClsId;

    private List<Product> Product;
}
