package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.StlDtl;
import com.boyu.erp.platform.usercenter.model.wareh.StlModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.StlDtlVO;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
/**
 * 盘点清单明细
 * @author HHe
 * @date 2019/9/23 15:03
 */
public interface StlDtlService {
    /**
     * 添加盘点清单明细集合
     * @author HHe
     * @date 2019/9/23 15:04
     */
    int addStlDtlList(StlModel stlModel, SysUser sysUser);
    /**
     * 删除盘点清单明细
     * @author HHe
     * @date 2019/9/23 15:44
     */
    int delStlDtlByNumAndId(StlModel stlModel, SysUser sysUser);
    /**
     * 根据盘点清单编号和组织id查询明细集合
     * @author HHe
     * @date 2019/9/25 11:08
     */
    List<StlDtlVO> queryListByStlNumAndId(StlDtl stlDtl,SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException;
    /**
     * 根据盘点清单编号和组织id查询盘点清单明细集合
     * @author HHe
     * @date 2019/9/26 19:54
     */
    List<StlDtl> queryStlDtlListByStlNumListAndId(List<String> stlNumList, Long unitId);
}
