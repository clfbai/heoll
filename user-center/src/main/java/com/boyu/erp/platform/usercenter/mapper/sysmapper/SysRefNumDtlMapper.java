package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNum;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @description: 编号明细
 * @author: CLF
 */
public interface SysRefNumDtlMapper {

    List<SysRefNumDtl> selectByPrimary(SysRefNumDtl refNumId);

    SysRefNumDtl finbyId(SysRefNumDtl record);

    int insertSelective(SysRefNumDtl record);

    int updateByPrimaryKeySelective(SysRefNumDtl record);

    int deleteByPrimaryKey(String refNumId);

    /**
     * 获得最大编号 和增量
     */
    SysRefNumDtl getLastStep(SysRefNumDtl sysRefNumDtl);

    /**
     * 查询增量明细是否存在
     */
    int countRefNumDtl(SysRefNumDtl sysRefNumDtl);

    /**
     * 查询最新编号
     */
    Long getLastNum(SysRefNumDtl sysRefNumDtl);
    /**
     * 根据编号Id和组织Id查询编号Dtl
     * @author HHe
     * @date 2019/9/19 19:58
     */
    SysRefNumDtl querySysRefDtlByNumIdAndUnitId(SysRefNumDtl sysRefNumDtl);
    /**
     * 自增最大编号并且返回
     * @author HHe
     * @date 2019/9/20 10:59
     */
    @Update("update sys_ref_num_dtl set last_num=(IF(last_num is null,#{stepSize},last_num+#{stepSize})) where ref_num_id=#{sysRefNumDtl.refNumId} and unit_id=#{sysRefNumDtl.unitId}")
//    @SelectKey(keyProperty = "lastNum",resultType = SysRefNumDtl.class,before = false,statement = "select last_num AS lastNum from sys_ref_num_dtl where ref_num_id=#{sysRefNumDtl.refNumId} and unit_id=#{sysRefNumDtl.unitId}" )
    Long updateAutoIncrementLastNum(@Param("sysRefNumDtl") SysRefNumDtl sysRefNumDtl, @Param("stepSize") Long stepSize);
}