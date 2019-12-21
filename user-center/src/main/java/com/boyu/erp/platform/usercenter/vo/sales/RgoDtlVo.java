package com.boyu.erp.platform.usercenter.vo.sales;

import com.boyu.erp.platform.usercenter.entity.sales.RgoDtl;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname RgoDtlVo
 * @Description TODO
 * @Date 2019/10/7 11:54
 * @Created by wz
 * 调配单明细
 */
@Data
public class RgoDtlVo extends RgoDtl implements Serializable {

    /**
     * 商品代码
     */
    private String prodCode;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 助记码
     */
    private String inputCode;

    /**
     * 序号
     */
    private Integer seqNum;

    /**
     * 计量单位
     */
    private String uom;

    /**
     * 颜色
     */
    private String color;
    /**
     * 规格
     */
    private String spec;
    /**
     * 版型
     */
    private String edition;

    /**
     * 商品品种ID  主键
     */
    private Long prodClsId;

    /**
     * 商品分类Id
     */
    private String prodCatId;

    //添加集合
    private List<RgoDtlVo> list;

}
