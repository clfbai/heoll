package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PsiType;

import java.util.List;

public interface PsiTypeMapper {
    int deleteByPrimaryKey(String psiType);

    int insert(PsiType record);

    int insertSelective(PsiType record);

    PsiType selectByPrimaryKey(String psiType);

    int updateByPrimaryKeySelective(PsiType record);

    int updateByPrimaryKey(PsiType record);

    List<PsiType> optionList();
}