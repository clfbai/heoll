package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStkLmt;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehStkLmtMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehStkLmtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class WarehStkLmtServiceImpl implements WarehStkLmtService {
    @Autowired
    private WarehStkLmtMapper warehStkLmtMapper;

    @Override
    public void addwarehStkLmtList(List<WarehStkLmt> warehStkLmts) {
        if (CollectionUtils.isNotEmpty(warehStkLmts)) {
            warehStkLmts.forEach(s -> warehStkLmtMapper.insertSelective(s));
        }
    }

    @Override
    public int addwarehStkLmt(WarehStkLmt warehStkLmt) {

        return warehStkLmtMapper.insertSelective(warehStkLmt);
    }
}
