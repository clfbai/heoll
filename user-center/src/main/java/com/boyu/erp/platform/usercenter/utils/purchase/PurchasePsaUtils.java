package com.boyu.erp.platform.usercenter.utils.purchase;

import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaFieldVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * @program: boyu-platform_text
 * @description: 采购协议公共方法类
 * @author: clf
 * @create: 2019-7-8 9:38
 */
@Component
public class PurchasePsaUtils {

    @Autowired
    private SysParameterService parameterService;

    public List<PsaFieldVo> full(String type){
        List<PsaFieldVo> list = new ArrayList<PsaFieldVo>();

        if(type.equals("DR")){
            SysParameter pscType = parameterService.findByParameter("PSA_DR_PSC_TYPE_ENABLED");
            if(pscType.getParmVal().equals("F")){
                list.add(new PsaFieldVo("pscType"));
            }
            SysParameter prodCatId = parameterService.findByParameter("PSA_DR_PROD_CAT_ENABLED");
            if(prodCatId.getParmVal().equals("F")){
                list.add(new PsaFieldVo("prodCatId"));
                list.add(new PsaFieldVo("prodCatName"));
            }
        }else if(type.equals("RTR")){
            SysParameter pscType = parameterService.findByParameter("PSA_RTR_PSC_TYPE_ENABLED");
            if(pscType.getParmVal().equals("F")){
                list.add(new PsaFieldVo("pscType"));
            }
            SysParameter prodCatId = parameterService.findByParameter("PSA_RTR_PROD_CAT_ENABLED");
            if(prodCatId.getParmVal().equals("F")){
                list.add(new PsaFieldVo("prodCatId"));
                list.add(new PsaFieldVo("prodCatName"));
            }
        }else if(type.equals("TR")){
            SysParameter pscType = parameterService.findByParameter("PSA_TR_PSC_TYPE_ENABLED");
            if(pscType.getParmVal().equals("F")){
                list.add(new PsaFieldVo("pscType"));
            }
            SysParameter prodCatId = parameterService.findByParameter("PSA_TR_PROD_CAT_ENABLED");
            if(prodCatId.getParmVal().equals("F")){
                list.add(new PsaFieldVo("prodCatId"));
                list.add(new PsaFieldVo("prodCatName"));
            }
        }
        return list;
    }
}
