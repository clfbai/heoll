package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStkX;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehStkXMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehStkXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
/**
 * 仓库库存快照服务
 * @author HHe
 * @date 2019/9/18 20:17
 */
@Service
@Transactional
public class WarehStkXServiceImpl implements WarehStkXService {
    @Autowired
    private WarehStkXMapper warehStkXMapper;
    /**
     * 存储仓库库存快照
     * @author HHe
     * @date 2019/9/18 20:17
     */
    @Override
    public int addWarehStkXList(List<WarehStkX> warehStkXList, Timestamp nowDate) {
        return warehStkXMapper.addWarehStkXList(warehStkXList,nowDate);
    }
    /**
     * 根据盘点时间和仓库Id删除所有快照
     * @author HHe
     * @date 2019/9/19 16:56
     */
    @Override
    public int delWarehStkXByTimeAndWarehId(Timestamp snptTime, Long warehId) {
        return warehStkXMapper.delWarehStkXByTimeAndWarehId(snptTime, warehId);
    }
    /**
     * 根据仓库id和快照时间查询仓库快照
     * @author HHe
     * @date 2019/9/26 19:43
     */
    @Override
    public List<WarehStkX> queryWarehStkXList(Long warehId, Timestamp snptTime) {
        return warehStkXMapper.queryWarehStkXList(warehId,snptTime);
    }
}
