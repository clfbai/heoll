package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttProdCat;

import java.util.List;
/**
 * 盘点表商品分类
 * @author HHe
 * @date 2019/9/18 15:14
 */
public interface SttProdCatService {
    /**
     * 添加清点表商品分类集合
     * @author HHe
     * @date 2019/9/18 15:14
     */
    int addSttProdCatList(List<SttProdCat> sttProdCatList, String sttNum, SysUser sysUser);
    /**
     * 根据盘点表编号和组织Id删除盘点表商品分类
     * @author HHe
     * @date 2019/9/18 16:32
     */
    int delSttProdCatByNumAndUnitId(Stt stt, SysUser sysUser);
    /**
     * 根据不为空属性查询盘点表商品分类集合
     * @author HHe
     * @date 2019/9/19 12:27
     */
    List<SttProdCat> querySttProdCatByObj(SttProdCat sttProdCat);
}
