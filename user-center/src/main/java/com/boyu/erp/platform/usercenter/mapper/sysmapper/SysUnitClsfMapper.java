package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysUnitClsf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUnitClsfMapper {
    int deleteSysUnitClsf(SysUnitClsf key);

    int insertSysUnitClsf(SysUnitClsf record);

    SysUnitClsf selectByPrimaryKey(SysUnitClsf record);

    /**
     * 删除仓库时删除时组织类型
     */
    int deleteSysUnitClsfWareh(SysUnitClsf record);

    /**
     * 查询组织类型
     */
    List<SysUnitClsf> selectByUnit(SysUnitClsf record);

    /**
     * 采购商 供应商中查询是否存在伙伴关系
     * @param record
     * @return
     */
    List<SysUnitClsf> selectByUnitType(SysUnitClsf record);

    /**
     * 查询是否存在有效类型
     * @param record
     * @return
     */
    List<SysUnitClsf> selectByVoid(SysUnitClsf record);

    /**
     * 更新状态
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysUnitClsf record);

    /**
     * 查询相关联的数据集合
     * @param record
     * @return
     */
    List<SysUnitClsf> selectByFreeze(SysUnitClsf record);
    /**
     * 查询并返回含有组织Id的组织Id
     * @author HHe
     * @date 2019/10/12 9:57
     */
    List<Long> queryHaveInUnitIds(@Param("unitType") String unitType, @Param("ownerId") Long ownerId, @Param("unitIds") List<Long> unitIds);
}