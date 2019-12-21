package com.boyu.erp.platform.usercenter.vo.shop;

import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class ShopAttrDefVo implements Serializable {
    /**
     * 属性类别
     */
    private String attrType;

    /**
     * 下拉属性值
     */
    private String codeType;
    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 下拉值集合
     */
    private List<SysCodeDtl> list;
}
