package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttProdCls;

import java.util.List;
/**
 * 商品品种
 * @author HHe
 * @date 2019/9/18 15:24
 */
public interface SttProdClsService {
    /**
     * 添加商品品种集合
     * @author HHe
     * @date 2019/9/18 15:24
     */
    int addSttProdClsList(List<SttProdCls> sttProdClsList, String sttNum, SysUser sysUser);
    /**
     * 根据盘点表编号和组织Id删除商品品种
     * @author HHe
     * @date 2019/9/18 16:30
     */
    int delSttProdClsByNumAndUnitId(Stt stt, SysUser sysUser);
    /**
     * 根据编号和组织Id查询品种集合
     * @author HHe
     * @date 2019/9/19 15:29
     */
    List<SttProdCls> querySttProdClsByObj(SttProdCls sttProdCls);
}
