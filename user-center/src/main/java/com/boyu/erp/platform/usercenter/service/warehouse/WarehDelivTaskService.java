package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.WarehDelivTaskFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.WarehDelivTaskModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehDelivTaskVO;
import com.github.pagehelper.PageInfo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface WarehDelivTaskService {

    /**
     * 添加出库任务
     * @param delivTask
     * @param sysUser
     * @return
     * @throws ServiceException
     */
    int addDelivTask(WarehDelivTask delivTask, SysUser sysUser) throws Exception;

    /**
     * 删除出库任务
     * @param
     * @return
     * @author HHe
     * @date 2019/11/4 17:56
     */
    int delDelivTask(WarehDelivTask delivTask, SysUser sysUser);

    /**
     * 查询出库任务
     * @param page
     * @param size
     * @param sysUser
     * @return
     */
    PageInfo<WarehDelivTaskVO> selectAll(WarehDelivTaskFilterModel warehDelivTaskFilterModel, Integer page, Integer size, SysUser sysUser) throws InvocationTargetException, NoSuchMethodException, IntrospectionException, NoSuchFieldException, IllegalAccessException;

    /**
     * 生成出库单（PLAN2）【待完善】【最终效果】
     * 备注：不并单，不判断字段，直接循环生成；
     * @author HHe
     * @date 2019/8/30 20:18
     */
    String addGdn4Task1(WarehDelivTaskModel warehDelivTaskModel, SysUser sysUser) throws Exception;
    /**
     * 出库任务下拉
     * @return
     */
    Map<String, Object> delivTaskPullDown(Long sUnitId,SysUser sysUser);
    /**
     * 查询列表中的总数量、总金额
     * @param warehDelivTaskFilterModel 查询的筛选条件
     * @teturn tot_val（总金额）、tot_qty（总数量）MAP
     * @author HHe
     * @date 2019/11/18 17:17
     */
    Map<String,Object> getListTotal(WarehDelivTaskFilterModel warehDelivTaskFilterModel);
    /**
     * 修改出库任务
     * @author HHe
     * @date 2019/12/2 16:28
     */
    int updateDelivTask(WarehDelivTask delivTask);
}
