package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stl;
import com.boyu.erp.platform.usercenter.model.wareh.StlFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.StlModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.StlVO;
import com.github.pagehelper.PageInfo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 盘点清单服务
 * @author HHe
 * @date 2019/9/23 10:54
 */
public interface StlService {
    /**
     * 根据筛选条件查询盘点清单列表
     * @author HHe
     * @date 2019/9/23 11:07
     */
    PageInfo<StlVO> queryList(Integer page, Integer size, StlFilterModel stlFilterModel, SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException;
    /**
     * 添加盘点清单
     * @author HHe
     * @date 2019/9/23 14:29
     */
    int addStl(StlModel stlModel, SysUser sysUser);
    /**
     * 修改盘点清单
     * @author HHe
     * @date 2019/9/23 15:22
     */
    int updateStl(StlModel stlModel, SysUser sysUser);
    /**
     * 删除盘点清单
     * @author HHe
     * @date 2019/9/23 16:26
     */
    int delStl(StlModel stlModel, SysUser sysUser);
    /**
     * 查询盘点清单详情
     * @author HHe
     * @date 2019/9/25 9:47
     */
    StlVO queryDetails(Stl stl, SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException;
    /**
     * 确认盘点清单
     * @author HHe
     * @date 2019/9/25 11:47
     */
    int atterStl(Stl stl, SysUser sysUser,String type);
    /**
     * 根据盘点表编号查询匹配盘点清单集合
     * @author HHe
     * @date 2019/9/26 15:06
     */
    List<Stl> queryListBySttNumAndId(String sttNum, SysUser sysUser);
    /**
     * 根据盘点表编号和组织id查询盘点清单编号集合
     * @author HHe
     * @date 2019/9/26 19:49
     */
    List<String> queryStlNumListBySttNumAndId(String sttNum, Long unitId);
    /**
     * 加载页面下拉
     * @author HHe
     * @date 2019/10/8 16:46
     */
    Map<String,Object> loadStlPullDown();
}
