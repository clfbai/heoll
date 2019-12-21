package com.boyu.erp.platform.usercenter.model.purchase;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import com.boyu.erp.platform.usercenter.entity.purchase.VdrAttr;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * @program: boyu-platform_text
 * @description: 修改采购协议自定义属性(前台传入)
 * @author: wz
 * @create: 2019-7-9 14:07
 */
@Data
@ToString
@NoArgsConstructor
public class VdrAttrModel extends BaseData implements Serializable {

    /**
     * 增加自定义属性
     */
    private List<VdrAttr> addVdrAttrr;
    /**
     * 删除自定义属性
     */
    private List<VdrAttr> deleteVdrAttr;

    /**
     * 修改自定义属性
     */
    private List<VdrAttr> updateVdrAttr;
}
