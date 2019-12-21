package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehUgDtlMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehUgDtlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 仓库分组明细服务
 * @author HHe
 * @date 2019/10/13 11:34
 */
@Service
public class WarehUgDtlServiceImpl implements WarehUgDtlService {
    @Autowired
    private WarehUgDtlMapper warehUgDtlMapper;
    /**
     * 根据组织分组Id查询仓库分组Id集合
     * @author HHe
     * @date 2019/10/13 11:35
     */
    @Override
    public List<Long> queryWarehIdsByWarehUgIds(List<Long> warehUgIds) {
        return warehUgDtlMapper.queryWarehIdsByWarehUgIds(warehUgIds);
    }
}
