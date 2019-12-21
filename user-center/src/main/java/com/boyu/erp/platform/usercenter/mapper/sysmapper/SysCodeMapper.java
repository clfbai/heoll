package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysCode;

import java.util.List;
/**
 * 代码
 */
public interface SysCodeMapper {
    int deleteByPrimaryKey(SysCode codeType);

    int insert(SysCode record);

    int insertSelective(SysCode record);

    SysCode selectByPrimaryKey(SysCode codeType);

    int updateByPrimaryKeySelective(SysCode record);

    int updateByPrimaryKey(SysCode record);

    List<SysCode> selectAll(SysCode code);
}