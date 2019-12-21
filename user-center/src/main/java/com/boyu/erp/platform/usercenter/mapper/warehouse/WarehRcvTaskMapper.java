package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Money;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.model.wareh.WarehRcvTaskModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehRcvTaskVo;

import java.util.List;


public interface WarehRcvTaskMapper {

    int deleteWarehRcvTask(WarehRcvTask key);

    int insertWarehRcvTask(WarehRcvTask record);

    WarehRcvTask selectWarehRcvTask(WarehRcvTask key);
     /**
      *
      * 功能描述:  根据组织和单据号 查询单据
      *
      *
      * @param:
      * @return:
      * @auther: CLF
      * @date: 2019/8/1 17:49
      */
    WarehRcvTask selectNum(WarehRcvTask key);

    int updateWarehRcvTask(WarehRcvTask record);

    /**
     * 系统管理员或有全局权限人查看入库任务
     */
    List<WarehRcvTaskVo> getAllRcvTask(WarehRcvTaskModel warehRcvTask);

   Money selectRcvTaskMoney(WarehRcvTaskModel model);
}