package com.boyu.erp.platform.usercenter.model.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述: 普通用户切换领域数据模型
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/14 10:56
 */
@Data
@NoArgsConstructor
public class OrdinaryDomainModel implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 组织Id
     */
    private Long unitId;
    /**
     * 组织代码
     */
    private String unitCode;

    /**
     * (领域代码)组织代码
     */
    private String domainId;
    /**
     * 组织名称
     */
    private String unitName;

    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 属组Id
     */
    private Long ownerId;
    /**
     * 领域组织状态
     */
    private String unitStatus;
    /**
     * 增加普通用户领域信息 集合
     */
    private List<OrdinaryDomainModel> addList = new ArrayList<>();
    /**
     * 删除普通用户领域信息 集合
     */
    private List<OrdinaryDomainModel> deleteList = new ArrayList<>();
}
