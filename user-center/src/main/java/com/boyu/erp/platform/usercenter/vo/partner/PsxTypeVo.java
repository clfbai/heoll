package com.boyu.erp.platform.usercenter.vo.partner;

import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import lombok.Data;

/**
 * @Classname PsxTypeVo
 * @Description TODO
 * @Date 2019/9/25 9:37
 * @Created by wz
 */
@Data
public class PsxTypeVo extends PsxType {
    /**
     * 商品分类名称
     */
    private String prodCatName;
}
