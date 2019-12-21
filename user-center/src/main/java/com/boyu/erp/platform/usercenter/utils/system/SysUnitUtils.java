package com.boyu.erp.platform.usercenter.utils.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SysUnitUtils {
    @Autowired
    private SysUnitService unitService;
     /**
      * 判断组织名称和代码有无重复
      * */
    public boolean isCodeAndName(String unitName, String unitCode,String type) {
        SysUnit unit = new SysUnit();
        unit.setUnitName(unitName);
        unit.setUnitCode(unitCode);
        return unitService.getCodeAndName(unit,type);
    }





}
