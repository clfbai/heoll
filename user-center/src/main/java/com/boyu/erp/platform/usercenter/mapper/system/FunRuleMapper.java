package com.boyu.erp.platform.usercenter.mapper.system;

import com.boyu.erp.platform.usercenter.entity.system.FunRule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunRuleMapper {
    /**
     * 查询功能规则集合
     * @author HHe
     * @date 2019/10/9 12:09
     */
    List<FunRule> queryFunctionList(FunRule funRule);
}
