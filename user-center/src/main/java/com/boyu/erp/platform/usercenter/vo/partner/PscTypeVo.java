package com.boyu.erp.platform.usercenter.vo.partner;

import com.boyu.erp.platform.usercenter.entity.purchase.PscType;
import lombok.Data;

/**
 * @Classname PscTypeVo
 * @Description TODO
 * @Date 2019/9/25 9:13
 * @Created by wz
 */
@Data
public class PscTypeVo extends PscType {
    /**
     * 商品分类名称
     */
    private String prodCatName;
}
