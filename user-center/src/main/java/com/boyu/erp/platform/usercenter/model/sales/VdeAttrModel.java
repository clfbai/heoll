package com.boyu.erp.platform.usercenter.model.sales;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import com.boyu.erp.platform.usercenter.entity.sales.VdeAttr;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: boyu-platform_text
 * @description: 修改采购商自定义属性(前台传入)
 * @author: wz
 * @create: 2019-7-9 14:07
 */
@Data
@NoArgsConstructor
public class VdeAttrModel extends BaseData implements Serializable {

    /**
     * 判断权限用
     */
    private Long ownerId;
    /**
     * 增加自定义属性
     */
    private List<VdeAttr> addVdeAttrr;
    /**
     * 删除自定义属性
     */
    private List<VdeAttr> deleteVdeAttr;

    /**
     * 修改自定义属性
     */
    private List<VdeAttr> updateVdeAttr;
}
