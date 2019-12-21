package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehDelivTaskMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.StbService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehDelivTaskExternalCiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 出库任务外部调用服务
 * @author HHe
 * @date 2019/12/2 16:21
 */
@Service
@Transactional
@Slf4j
public class WarehDelivTaskExternalCiteServiceImpl implements WarehDelivTaskExternalCiteService {
    @Autowired
    private StbService stbService;
    @Autowired
    private WarehDelivTaskMapper warehDelivTaskMapper;
    /**
     * 修改出库任务
     * @param delivTask
     * @return
     * @author HHe
     * @date 2019/12/2 16:20
     */
    @Override
    public int updateWarehDelivTask(WarehDelivTask delivTask) {
        return warehDelivTaskMapper.updateDelivTask(delivTask);
    }
    /**
     * 更新任务执行次数
     * @param delivTask 任务信息:
     *                  任务单据组织Id；
     *                  任务单据编号；
     *                  任务单据类别；
     *                  组织Id；
     * @return 数据库执行数
     * @author HHe
     * @date 2019/12/3 11:49
     */
    @Override
    public int renewalTaskImplTimes(WarehDelivTask delivTask) {
        log.info("多次执行，查询出库单数量");
        Stb stb = new Stb();
        stb.setCancelled("F");
        stb.setSrcDocUnitId(delivTask.getTaskDocUnitId());
        stb.setSrcDocNum(delivTask.getTaskDocNum());
        stb.setSrcDocType(delivTask.getTaskDocType());
        //查询出库单数量
        Long implTimes = stbService.queryCountByErc(stb);
        //修改
        WarehDelivTask warehDelivTaskUp = new WarehDelivTask();
        warehDelivTaskUp.setUnitId(delivTask.getUnitId());
        warehDelivTaskUp.setTaskDocNum(delivTask.getTaskDocNum());
        warehDelivTaskUp.setTaskDocType(delivTask.getTaskDocType());
        warehDelivTaskUp.setTaskDocUnitId(delivTask.getTaskDocUnitId());
        warehDelivTaskUp.setImplTimes(implTimes);
        return warehDelivTaskMapper.updateDelivTask(warehDelivTaskUp);
    }

}
