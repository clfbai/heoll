package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.AccoCost;
import com.boyu.erp.platform.usercenter.mapper.warehouse.AccoCostMapper;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;
import com.boyu.erp.platform.usercenter.service.warehouse.AccoCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 核算组织成本服务
 *
 * @author HHe
 * @date 2019/10/25 10:24
 */
@Service
@Transactional
public class AccoCostServiceImpl implements AccoCostService {
    @Autowired
    private AccoCostMapper accoCostMapper;

    /**
     * 查询核算组织成本集合
     *
     * @param prodClsIds 商品品种
     * @param fsclUnitId 会计组织Id
     * @return 核算组织成本集合
     * @author HHe
     * @date 2019/10/25 10:52
     */
    @Transactional(readOnly = true)
    @Override
    public List<AccoCost> queryAccoCostListByClsAndUnit(Set<Long> prodClsIds, Long fsclUnitId) {
        return accoCostMapper.queryAccoCostListByClsAndUnit(prodClsIds, fsclUnitId);
    }

    /**
     * 修改组织成本
     *
     * @param countCostModel 成本数据
     * @param fsclUnitId      会计组织Id
     * @return 数据库操作数
     * @author HHe
     * @date 2019/10/25 15:16
     */
    @Override
    public int updateByCountCost(CountCostModel countCostModel, Long fsclUnitId) {
        AccoCost accoCost = new AccoCost();
        accoCost.setUnitId(fsclUnitId);
        accoCost.setProdClsId(countCostModel.getProdClsId());
        accoCost.setTotQty(countCostModel.getUnitQty());
        accoCost.setUnitCost(countCostModel.getUnitCost());
        return accoCostMapper.updateByPrimaryKeySelective(accoCost);
    }
}
