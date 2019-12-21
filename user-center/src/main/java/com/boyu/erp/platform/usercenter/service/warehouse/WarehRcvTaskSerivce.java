package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Money;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.WarehRcvTaskModel;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehRcvTaskDtlVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehRcvTaskVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Description: 入库任务接口
 * @auther: CLF
 * @date: 2019/7/15 10:48
 */
public interface WarehRcvTaskSerivce {

    PageInfo<WarehRcvTaskVo> getWarehRcvTask(Integer page, Integer size, WarehRcvTaskModel warehRcvTask, SysUser user);

    /**
     * 功能描述:  入库任务明细查询
     * 这里是根据采购单、退购单等直接获取相应的明细 暂时待完善
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/24 17:01
     */
    PageInfo<WarehRcvTaskDtlVo> getWarehRcvTaskDtl(Integer page, Integer size, WarehRcvTaskModel vo, SysUser sessionSysUser);

    /**
     * 功能描述: 增加入库任务1
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/8 9:56
     */
    int addWarehRcvTask(WarehRcvTask vo, SysUser sessionSysUser) throws Exception;

    /**
     * 功能描述:  入库任务生成入库单
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/31 14:33
     */
    int WarehRcvTaskGrn(WarehRcvTask model, SysUser sessionSysUser) throws ServiceException, Exception;

    /**
     * 功能描述: 修改入库任务状态
     * 将是否生成 入库单 为否
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/1 17:30
     */
    int updateWarehRcvTask(WarehRcvTask warehRcvTask);

    /**
     * 功能描述: 前端修改入库仓库
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 11:34
     */
    int webUpdateWarehRcvTask(WarehRcvTask warehRcvTask, SysUser user) throws Exception;

    /**
     * 功能描述: 撤销入库任务
     * 主要给后台 采购模块调用
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 14:41
     */
    int deregisterWarehRcvTask(WarehRcvTask warehRcvTask, SysUser user) throws Exception;

    WarehRcvTask selectWarehRcvTask(WarehRcvTask warehRcvTask);

    /**
     * 组合模式屏蔽字段
     */
    List<PurKeyValue> selectWarehRcvTaskShield(int group);

    /**
     * 查询总金额总数量
     */
    Money selectWarehRcvTaskMoney(WarehRcvTaskModel model);
}
