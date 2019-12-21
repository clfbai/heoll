package com.boyu.erp.platform.usercenter.vo.partner;

import com.boyu.erp.platform.usercenter.entity.purchase.RtcType;
import lombok.Data;

/**
 * @Classname RtcTypeVo
 * @Description TODO
 * @Date 2019/9/25 9:48
 * @Created by wz
 */
@Data
public class RtcTypeVo extends RtcType {
    /**
     * 商品分类名称
     */
    private String prodCatName;
}
