package com.boyu.erp.platform.usercenter.mapper.collarUse;

import com.boyu.erp.platform.usercenter.entity.collarUse.ArqType;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;

import java.util.List;

public interface ArqTypeMapper {
    int deleteByPrimaryKey(String arqType);

    int insert(ArqType record);

    int insertSelective(ArqType record);

    ArqType selectByPrimaryKey(String arqType);

    int updateByPrimaryKeySelective(ArqType record);

    int updateByPrimaryKey(ArqType record);

    List<ArqType> selectALL(ArqType record);

    List<ArqType> listByArqType(String arqType);

    List<PurKeyValue> optionGet();
}