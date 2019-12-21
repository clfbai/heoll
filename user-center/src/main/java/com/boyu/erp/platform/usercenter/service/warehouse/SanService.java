package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.San;
import com.boyu.erp.platform.usercenter.model.wareh.SanFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.SanModel;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.SanVO;
import com.github.pagehelper.PageInfo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 库存调整单服务
 *
 * @author HHe
 * @date 2019/10/6 16:52
 */
public interface SanService {
    /**
     * 根据筛选条件分页查询列表
     * @author HHe
     * @date 2019/10/6 17:07
     */
    PageInfo<SanVO> queryList(Integer page, Integer size, SanFilterModel sanFilterModel, SysUser sysUser) throws InvocationTargetException, NoSuchMethodException, IntrospectionException, NoSuchFieldException, IllegalAccessException;
    /**
     * 加载库存调整表下拉
     * @author HHe
     * @date 2019/10/6 17:51
     */
    Map<String,Object> loadSanPullDown(SysUser sysUser);
    /**
     * 查询详情
     * @author HHe
     * @date 2019/10/7 10:40
     */
    SanVO queryDetails(String sanNum, SysUser sysUser) throws InvocationTargetException, NoSuchMethodException, IntrospectionException, NoSuchFieldException, IllegalAccessException;
    /**
     * 添加库存调整表
     * @author HHe
     * @date 2019/10/7 10:54
     */
    int addSan(SanModel sanModel, SysUser sysUser);
    /**
     * 删除库存调整表
     * @author HHe
     * @date 2019/10/7 14:25
     */
    int delSan(String sanNum, SysUser sysUser);
    /**
     * 修改库存调整表
     * @author HHe
     * @date 2019/10/7 14:54
     */
    int updateSan(SanModel sanModel, SysUser sysUser);
    /**
     * 库存调整表功能列表展示
     * @author HHe
     * @date 2019/10/9 11:32
     */
    List<OperationVo> queryFunList(San san);

    /**
     * 确认单据
     * @author HHe
     * @date 2019/10/9 17:16
     */
    int confirm(SanModel sanModel);

    /**
     * 重做单据
     *
     * @author HHe
     * @date 2019/10/10 9:12
     */
    int redo(SanModel sanModel);

    /**
     * 审核单据
     * @author HHe
     * @date 2019/10/10 10:01
     */
    int check(SanModel sanModel);

    /**
     * 反审单据
     * @author HHe
     * @date 2019/10/10 10:05
     */
    int uncheck(SanModel sanModel);

    /**
     * 挂起单据
     *
     * @author HHe
     * @date 2019/10/10 10:07
     */
    int suspend(SanModel sanModel);

    /**
     * 恢复单据
     *
     * @author HHe
     * @date 2019/10/10 10:10
     */
    int resume(SanModel sanModel);

    /**
     * 作废单据
     *
     * @author HHe
     * @date 2019/10/10 10:11
     */
    int abolish(SanModel sanModel);

    /**
     * 单据过账
     * @author HHe
     * @date 2019/10/10 10:11
     */
    int post(SanModel sanModel);
}
