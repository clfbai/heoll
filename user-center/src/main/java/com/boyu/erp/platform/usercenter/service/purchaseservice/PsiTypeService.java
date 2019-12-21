package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.purchase.PsiType;

import java.util.List;


/**
 * @Classname PsiTypeService
 * @Description TODO
 * @Date 2019/6/18 16:39
 * @Created wz
 */
public interface PsiTypeService {

    /**
     * 购销意向类别下拉
     */
    List<PsiType> optionGet();
}
