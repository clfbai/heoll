package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.AccoCost;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AccoCostMapper {
    int insert(AccoCost record);

    int insertSelective(AccoCost record);

    int updateByPrimaryKeySelective(AccoCost record);

    int updateByPrimaryKey(AccoCost record);
    /**
     * 查询核算组织成本集合
     * @author HHe
     * @date 2019/10/25 10:55
     */
    List<AccoCost> queryAccoCostListByClsAndUnit(@Param("prodClsIds") Set<Long> prodClsIds, @Param("fsclUnitId") Long fsclUnitId);
}