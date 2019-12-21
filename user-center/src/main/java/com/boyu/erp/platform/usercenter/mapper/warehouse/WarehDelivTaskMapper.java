package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import com.boyu.erp.platform.usercenter.model.wareh.WarehDelivTaskFilterModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehDelivTaskVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WarehDelivTaskMapper{
    /**
     * 查询是否有相同出库任务
     * @param delivTask
     * @return
     */
    WarehDelivTask queryDelivTaskIsNull(@Param("delivTask") WarehDelivTask delivTask);
    /**
     * 登记出库任务
     * @param delivTask
     * @return
     */
    int insertSelective(WarehDelivTask delivTask);
    /**
     * 更新出库任务价格和数量
     * @param delivTask
     * @return
     */
    int updateDelivTask(WarehDelivTask delivTask);

//    List<WarehDelivTaskVO> getDelivTasklist(@Param("delivTaskVO") WarehDelivTaskVO delivTaskVO, @Param("sysUser") SysUser sysUser);


    /**
     * 查询列表
     * @return
     */
    List<WarehDelivTaskVO> selectDelivByUnitId(WarehDelivTaskFilterModel warehDelivTaskFilterModel);

    WarehDelivTask selectWarehDelivTask(WarehDelivTask task);

    /**
     * 修改出库任务状态
     * @author HHe
     */
    int updateDelivTaskProgress(@Param("taskDocUnitId") Long taskDocUnitId,@Param("taskDocNum") String taskDocNum, @Param("taskDocType") String taskDocType, @Param("yg") String yg);

    /**
     * 删除任务
     * @param task
     * @return
     */
    int deleteByBill(WarehDelivTask task);
    /**
     * 查询出库任务
     * @author HHe
     * @date 2019/11/6 12:09
     */
    WarehDelivTask queryDelivTaskByBill(WarehDelivTask delivTaskP);
    /**
     * 根据筛选条件查询列表中的总数量、总金额
     * @author HHe
     * @date 2019/11/18 17:21
     */
    Map<String, Object> getListTotal(WarehDelivTaskFilterModel warehDelivTaskFilterModel);
    /**
     * 查询出库任务列表
     * @author HHe
     * @date 2019/11/20 15:39
     */
    List<WarehDelivTask> queryDelivTaskByList(@Param("delivTaskList") List<WarehDelivTask> delivTaskList, @Param("unitId") Long unitId);
    /**
     * 添加出库任务，主键重复则修改
     * @author HHe
     * @date 2019/11/22 20:35
     */
    int insertUpdateTask(WarehDelivTask delivTask);
}