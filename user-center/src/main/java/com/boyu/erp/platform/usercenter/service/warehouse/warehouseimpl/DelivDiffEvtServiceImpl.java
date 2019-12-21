package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.DelivDiffEvt;
import com.boyu.erp.platform.usercenter.mapper.warehouse.DelivDiffEvtMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.DelivDiffEvtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 出库差异事件服务
 * @author HHe
 * @date 2019/10/29 9:53
 */
@Service
@Transactional
public class DelivDiffEvtServiceImpl implements DelivDiffEvtService {
    @Autowired
    private DelivDiffEvtMapper delivDiffEvtMapper;

    /**
     * 登记出库差异事件
     * @param unitId 组织Id
     * @param gdnNum 出库单编号
     * @return 数据库操作数
     * @author HHe
     * @date 2019/10/29 11:00
     */
    @Override
    public int register(Long unitId, String gdnNum) {
        DelivDiffEvt delivDiffEvt = new DelivDiffEvt();
        delivDiffEvt.setUnitId(unitId);
        delivDiffEvt.setGdnNum(gdnNum);
        delivDiffEvt.setProgress("PG");
        return delivDiffEvtMapper.insertSelective(delivDiffEvt);
    }
    /**
     * 撤销出库差异事件
     * @param unitId 组织Id
     * @param gdnNum 出库单编号
     * @return
     * @author HHe
     * @date 2019/12/2 15:59
     */
    @Override
    public int revocation(Long unitId, String gdnNum) {
        DelivDiffEvt delivDiffEvt = new DelivDiffEvt();
        delivDiffEvt.setUnitId(unitId);
        delivDiffEvt.setGdnNum(gdnNum);
        return delivDiffEvtMapper.delete(delivDiffEvt);
    }
}
