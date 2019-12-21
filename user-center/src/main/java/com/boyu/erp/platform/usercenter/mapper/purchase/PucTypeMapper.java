package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.entity.purchase.PucType;

import java.util.List;

import com.boyu.erp.platform.usercenter.vo.purchase.PscAutoVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PuaTypeVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PucTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import org.apache.ibatis.annotations.Param;

public interface PucTypeMapper {
    int deletePucType(PucType pucType);

    int insertPucType(PucType pucType);

    int updatePucType(PucType pucType);

    public List<PucTypeVo> selectALL(PucType pucType);

    public List<PucTypeVo> selectOnePsc(PucType pucType);

    List<PurKeyValue> optionGet();

    PscAutoVo selectByPscAuto(String pucType);

    PucType pucTypeList(String pucType);

    PucType selectByPscType(String pscType);

    List<PucType> listByPucType(String pucType,String pscType);

    List<PucType> listByPscType(String pucType,String pscType);
}