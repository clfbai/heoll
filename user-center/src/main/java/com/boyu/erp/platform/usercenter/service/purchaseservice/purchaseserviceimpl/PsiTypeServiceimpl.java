package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;



import com.boyu.erp.platform.usercenter.entity.purchase.PsiType;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsiTypeMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsiTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname PuiTypeServiceimpl
 * @Description TODO
 * @Date 2019/6/18 16:40
 * @Created wz
 */
@Service
@Transactional
public class PsiTypeServiceimpl implements PsiTypeService {

    @Autowired
    private PsiTypeMapper psiTypeMapper;

    @Override
    public List<PsiType> optionGet() {
        return psiTypeMapper.optionList();
    }

}
