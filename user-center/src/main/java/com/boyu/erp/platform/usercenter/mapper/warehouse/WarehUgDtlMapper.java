package com.boyu.erp.platform.usercenter.mapper.warehouse;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 仓库分组明细mapper
 * @author HHe
 * @date 2019/10/13 11:36
 */
@Repository
public interface WarehUgDtlMapper {
    /**
     * 根据组织分组Id查询仓库分组Id集合
     * @author HHe
     * @date 2019/10/13 11:36
     */
    List<Long> queryWarehIdsByWarehUgIds(@Param("warehUgIds") List<Long> warehUgIds);
}