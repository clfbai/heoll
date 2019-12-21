package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehUgMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehUgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * 仓库分组服务
 * @author HHe
 * @date 2019/9/5 10:46
 */
@Service
@Transactional
public class WarehUgServiceImpl implements WarehUgService {
    @Autowired
    private WarehUgMapper warehUgMapper;
    /**
     * 根据组织分组多选加载仓库分组
     * @author HHe
     * @date 2019/9/5 10:46
     */
    @Transactional(readOnly = true)
    @Override
    public Map<String, Object> loadWarehUg(List<Long> sysUgIds) {
        Map<String, Object> map = new LinkedHashMap<>();
        if(sysUgIds!=null&&sysUgIds.size()>0){
            map.put("warehUgIds", warehUgMapper.loadWarehUg(sysUgIds));
        }
        return map;
    }
    /**
     * 根据组织分组Id查询仓库分组Id集合
     * @author HHe
     * @date 2019/10/13 11:31
     */
    @Override
    public List<Long> queryWarehUgIdsBySysUgId(List<Long> unitUgIds) {
        return warehUgMapper.queryWarehUgIdsBySysUgId(unitUgIds);
    }
}
