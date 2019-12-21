package com.boyu.erp.platform.usercenter.entity.mq.ven;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname Unit
 * @Description TODO
 * @Date 2019/10/9 14:23
 * @Created by wz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unit  implements Serializable {

    /**
     * 组织id
     */
    private String id;

    /**
     * 上级组织id
     */
    private String parentId;

    /**
     * 组织编码
     */
    private String code;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 组织类型
     * （1->管理组织（公司）2->加盟商 3->门店 4->生产商（供应商） 5->仓库）
     */
    private int unitType;
}
