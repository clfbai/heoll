package com.boyu.erp.platform.usercenter.TPOS.service.impl;

import com.boyu.erp.platform.usercenter.TPOS.entity.DhOrdTaskU;
import com.boyu.erp.platform.usercenter.TPOS.mapper.DhOrdTaskUMapper;
import com.boyu.erp.platform.usercenter.TPOS.service.DhOrdTaskUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 上传单据任务服务
 * @author HHe
 * @date 2019/11/8 15:57
 */
@Service
@Transactional
public class DhOrdTaskUServiceImpl implements DhOrdTaskUService {
    @Autowired
    private DhOrdTaskUMapper dhOrdTaskUMapper;
    /**
     * 登记上传单据任务接口
     * @param dhOrdTaskU 上传单据任务对象
     * @return 数据库执行数
     * @author HHe
     * @date 2019/11/8 15:56
     */
    @Async
    @Override
    public void registerDhOrdTaskU(DhOrdTaskU dhOrdTaskU) {
        dhOrdTaskUMapper.insertSelective(dhOrdTaskU);
    }
    /**
     * 修改上传任务状态和信息
     * @param dhOrdTaskU 状态、异常信息
     * @author HHe
     * @date 2019/12/4 14:39
     */
    @Async
    @Override
    public void updateDhOrdTaskU(DhOrdTaskU dhOrdTaskU) {
        dhOrdTaskUMapper.updateByPrimaryKeySelective(dhOrdTaskU);
    }
}
