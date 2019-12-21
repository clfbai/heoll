package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.FunRule;

import java.util.List;
/**
 * 功能规则接口
 * @author HHe
 * @date 2019/10/9 12:07
 */
public interface FunRuleService {
    /**
     * 查询单据规则列表
     * @author HHe
     * @date 2019/10/9 12:07
     */
    List<FunRule> queryFunctionList(FunRule funRule);
}
