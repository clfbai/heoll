package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttBrand;

import java.util.List;
/**
 * 盘点表品牌
 * @author HHe
 * @date 2019/9/18 14:58
 */
public interface SttBrandService {
    /**
     * 添加多条盘点表品牌集合
     * @author HHe
     * @date 2019/9/18 14:58
     */
    int addSttBrandList(List<SttBrand> sttBrandList, String sttNum,SysUser sysUser);
    /**
     * 根据盘点表编号和组织Id删除盘点表品牌
     * @author HHe
     * @date 2019/9/18 16:24
     */
    int delSttBrandByNumAndUnitId(Stt stt, SysUser sysUser);
    /**
     * 根据对象的非空值查询集合
     * @author HHe
     * @date 2019/9/19 10:10
     */
    List<SttBrand> querySttBrandByObj(SttBrand sttBrand);

}
