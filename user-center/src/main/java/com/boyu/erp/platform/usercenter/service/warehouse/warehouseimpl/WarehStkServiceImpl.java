package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStk;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehStkMapper;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehStkService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class WarehStkServiceImpl implements WarehStkService {
    @Autowired
    private WarehStkMapper warehStkMapper;

    /**
     * 根据仓库id、商品Id查询仓库库存信息（待完善）
     *
     * @author HHe
     * @date 2019/9/3 12:19
     */
    @Override
    @Transactional(readOnly = true)
    public WarehStk queryWarehStkByWarehIdProdId(Long warehId, Long prodId) {
        return null;
    }

    /**
     * 根据仓库Id查询仓库所有实际库存非0的库存详情
     *
     * @author HHe
     * @date 2019/9/18 19:35
     */
    @Override
    public List<WarehStk> queryWarehStkListByWarehId(Long warehId) {
        Wareh wareh = new Wareh();
        wareh.setWarehId(warehId);
        return warehStkMapper.selectWarehByWarehIdIsNotZero(wareh);
    }

    /**
     * 根据仓库Id和商品Id集合查询不为0的库存数量；
     *
     * @author HHe
     * @date 2019/9/19 12:01
     */
    @Override
    public List<WarehStk> queryWarehStkListByProdIdList(Long warehId, List<Long> prodIdList) {
        return warehStkMapper.queryWarehStkListByProdIdList(warehId, prodIdList);
    }

    /**
     * 查询仓库中的商品集合
     *
     * @author HHe
     * @date 2019/10/21 11:27
     */
    @Override
    public List<WarehStk> queryWarehStkByProdsAndWareh(Set<Long> prodIds, Long warehId) {
        return warehStkMapper.queryWarehStkByProdsAndWareh(prodIds, warehId);
    }


    /**
     * 功能描述: 增加库存有重复记录则修改库存实际数量
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 19:52
     */
    @Override
    public int updateAddWarehStkList(List<WarehStk> warehStkList) throws ServiceException {
        if (CollectionUtils.isNotEmpty(warehStkList.stream().filter(s -> StringUtils.NullEmpty(s.getWarehId() + "")).collect(Collectors.toList()))) {
            throw new ServiceException("403", "仓库Id不能为空");
        }
        if (CollectionUtils.isNotEmpty(warehStkList)) {
            warehStkList.forEach( System.out::println);
            return warehStkMapper.updateAddWarehStkList(warehStkList);
        }
        return 0;
    }

    /**
     * 添加库存信息如果存在则减去参数中的数量
     *
     * @author HHe
     * @date 2019/11/22 9:42
     */
    @Override
    public int insertUpdateWarehStkList(List<CountCostModel> countCostModels, Long warehId) {
        return warehStkMapper.insertUpdateWarehStkList(countCostModels, warehId);
    }
}
