package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.vo.purchase.PuaTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;


import java.util.List;

public interface PuaTypeMapper {
    int deletePuaType(PuaType puaType);

    int insertPuaType(PuaType puaType);

    int updatePuaType(PuaType puaType);

    public List<PuaTypeVo> selectALL(PuaTypeVo vo);

    public List<PuaTypeVo> selectOnePsx(PuaType puaType);

    List<PurKeyValue> optionGet();

    PuaType selectByPuaType(String puaType);

    PuaType selectByPsxType(String psxType);

    List<PuaType> listByPuaType(String puaType,String psxType);

    List<PuaType> listByPsxType(String puaType,String psxType);
}