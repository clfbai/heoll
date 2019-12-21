package com.boyu.erp.platform.usercenter.model.goods;

import com.boyu.erp.platform.usercenter.entity.goods.ProdClsAttr;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: boyu-platform_text
 * @description: 修改商品自定义属性数据模型(前台传入)
 * @author: clf
 * @create: 2019-06-18 09:13
 */
@Data
@ToString
@NoArgsConstructor
public class ProdClsAttrModel implements Serializable {

    /**
     * 增加自定义商品品种属性
     */
    private List<ProdClsAttr> addProdClsAttr=new ArrayList<>();
    /**
     * 删除自定义商品品种属性
     */
    private List<ProdClsAttr> deleteProdClsAttr=new ArrayList<>();

    /**
     * 修改自定义商品品种属性
     */
    private List<ProdClsAttr> updateProdClsAttr=new ArrayList<>();
}
