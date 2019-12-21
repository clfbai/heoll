package com.boyu.erp.platform.usercenter.TPOS.service.impl;

import com.boyu.erp.platform.usercenter.TPOS.entity.DhOrdTask;
import com.boyu.erp.platform.usercenter.TPOS.mapper.DhOrdTaskMapper;
import com.boyu.erp.platform.usercenter.TPOS.service.DhOrdTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 下发任务服务
 * @author HHe
 * @date 2019/12/4 20:58
 */
@Service
@Transactional
@Slf4j
public class DhOrdTaskServiceImpl implements DhOrdTaskService {
    @Autowired
    private DhOrdTaskMapper dhOrdTaskMapper;
    /**
     * 登记下发任务
     * @param dhOrdTask 下发任务
     * @author HHe
     * @date 2019/12/4 21:22
     */
    @Override
    public int registerDhOrdTask(DhOrdTask dhOrdTask) {
        return dhOrdTaskMapper.insertSelective(dhOrdTask);
    }
    /**
     * 修改下发任务
     * @param dhOrdTask 下发任务
     * @return
     * @author HHe
     * @date 2019/12/5 17:34
     */
    @Override
    public int updateTask(DhOrdTask dhOrdTask) {
        return dhOrdTaskMapper.updateByPrimaryKeySelective(dhOrdTask);
    }
}
