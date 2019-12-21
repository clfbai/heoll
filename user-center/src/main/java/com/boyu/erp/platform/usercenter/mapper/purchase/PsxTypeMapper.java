package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PsiType;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.vo.partner.PsxTypeVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PsxTypeMapper {
    int deleteByPrimaryKey(String psxType);

    int insert(PsxType record);

    int insertSelective(PsxType record);

    PsxType selectByPrimaryKey(String psxType);

    int updateByPrimaryKeySelective(PsxType record);

    int updateByPrimaryKey(PsxType record);

    List<PsxType> optionList();

    List<PsxTypeVo> selectALL(PsxType psxType);


}