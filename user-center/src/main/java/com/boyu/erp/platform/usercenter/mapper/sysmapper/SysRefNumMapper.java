package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNum;
import java.util.List;


 public interface SysRefNumMapper {

     List<SysRefNum> selectByPrimary(SysRefNum refNumId);

     SysRefNum findByByRefNum(SysRefNum refNumId);

     int insertSelective(SysRefNum record);

     int updateByPrimaryKeySelective(SysRefNum record);

     int deleteByPrimaryKey(String refNumId);
    /**
     * 根据编号Id查询信息
     * @author HHe
     * @date 2019/9/19 20:14
     */
     SysRefNum querySysRefNumByNumId(String refNumId);
 }