package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PuiType;
import com.boyu.erp.platform.usercenter.vo.purchase.PuiTypeVo;

import java.util.List;

public interface PuiTypeMapper {
    int deletePuiType(PuiType puiType);

    int insertPuiType(PuiType puiType);

    int updatePuiType(PuiType puiType);

    public List<PuiTypeVo> selectALL(PuiType puiType);

    public List<PuiTypeVo> selectOnePsi(PuiType puiType);
}