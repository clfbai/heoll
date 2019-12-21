package com.boyu.erp.platform.usercenter.vo.goods;

import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: boyu-platform_text
 * @description: 商品自定义属性数据模型
 * @author: clf
 * @create: 2019-06-17 17:53
 */
@Data
@NoArgsConstructor
public class ProdAttrDefVo {
    /**
     * 商品自定义属性类别
     */
    private String attrType;
    /**
     * 商品自定义属性名称
     */
    private String attrName;
    /**
     * 编辑定义
     */
    private String edtFml;
    /**
     * 是否能修改
     */
    private String amend;
    /**
     * 是否下拉
     */
    private String isOpetion;
    /**
     * 下拉选项
     */
    List<SysCodeDtl> codeDtls;
}
