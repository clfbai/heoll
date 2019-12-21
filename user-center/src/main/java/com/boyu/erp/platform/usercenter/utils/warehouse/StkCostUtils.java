package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.StkCost;
import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.StkCostMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehMapper;
import com.boyu.erp.platform.usercenter.model.wareh.StkCostModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述: 组织成本核算工具类
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/11/14 17:35
 */
@Component
public class StkCostUtils {

    @Autowired
    private WarehMapper warehMapper;
    @Autowired
    private WarehSingUtils warehSingUtils;
    @Autowired
    private StkCostMapper stkCostMapper;

    /**
     * 功能描述: 根据会计组织Id和入库单明细生成需要修改组织成本对象
     * <p>
     * 会计组织 通过查询 仓库 和 仓库属主Id 在sys_unit_clf 表中的关系得到
     * 这里只需要   给StkCostModel 赋值 warehId 和 stbDtl这两个值
     * //todo 注意 stbDtl 集合是所有开启库存本商品的明细
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/14 15:09
     */
    public List<StkCost> createStkCost(StkCostModel stkCostModel) throws ServiceException {
        //验证仓库参数
        Wareh wareh = this.isVidate(stkCostModel);
        //找到需要入库的商品的组织成本
        List<StbDtl> max = warehSingUtils.setStbDtlProdClsId(stkCostModel.getStbDtls());
        List<Long> prodClsId = max.stream().map(StbDtl::getProdClsId).collect(Collectors.toList());
        List<StkCost> stkCosts = stkCostMapper.queryListByWarehAndClsList(
                stkCostModel.getStbDtls().stream().map(s -> s.getProdClsId()).collect(Collectors.toSet()), wareh.getFsclUnitId());
        System.out.println("cost: " + stkCosts);
        //仓库的会计组织和属主一致
        if (wareh.getOwnerId().equals(wareh.getFsclUnitId())) {
            List<StkCost> returnList = new ArrayList<>();
            //组织成本集合不为空
            if (CollectionUtils.isNotEmpty(stkCosts)) {
                //添加修改库存集合
                List<StkCost> updateStkCost = stkCosts.stream().filter(s -> prodClsId.contains(s.getProdClsId())).collect(Collectors.toList());
                returnList.addAll(createStkCostNotEmp(wareh.getOwnerId(), max, updateStkCost, stkCostModel.getReversed()));
                //剩余直接新增的
                prodClsId.removeAll(updateStkCost.stream().map(StkCost::getProdClsId).collect(Collectors.toList()));
                if (CollectionUtils.isNotEmpty(prodClsId)) {
                    //添加新增库存集合
                    returnList.addAll(createStkCostEmp(wareh.getOwnerId(),
                            max.stream().filter(s -> prodClsId.contains(s.getProdClsId())).collect(Collectors.toList()), stkCostModel.getReversed()));
                }
                return returnList;
            }
            //组织成本为空
            return createStkCostEmp(wareh.getOwnerId(), max, stkCostModel.getReversed());
        } else {
            //todo 仓库和会计组织不一致的 待编写
            return null;
        }
    }

    /**
     * 根据商品Id、组织Id没有查询到组织成本记录且需要新增的组织成本集合
     * (新增组织成本对相集合)
     */
    private List<StkCost> createStkCostEmp(Long ownerId, List<StbDtl> stbDtlList, boolean b) {
        List<StkCost> rest = new ArrayList<>();
        if (b) {
            throw new ServiceException("403", "没有库存成本不能去反");
        }
        for (StbDtl s : stbDtlList) {
            StkCost cost = new StkCost();
            if (b) {
                //取反 且没有组织成本  成本为负数
                cost.setTotQty(s.getQty().negate());
                cost.setUnitCost(s.getQty().negate());
                //组织成本
                cost.setUnitCost(cost.getUnitCost().divide(cost.getTotQty(), 4, RoundingMode.CEILING).negate());
            } else {
                cost.setTotQty(s.getQty());
                cost.setUnitCost(s.getQty());
                //组织成本
                cost.setUnitCost(cost.getUnitCost().divide(cost.getTotQty(), 4, RoundingMode.CEILING));
            }
            //未决
            cost.setPgVal(new BigDecimal("0"));
            //组织Id(会计组织Id)
            cost.setUnitId(ownerId);
            //成本组
            cost.setCostGrpId(0L);
            cost.setProdClsId(s.getProdClsId());
            rest.add(cost);
        }
        return rest;

    }

    /**
     * 功能描述:已有组织成本的记录返回修改、或添加的组织成本记录集合
     *
     * @param ownerId  (仓库属主Id)
     * @param list     (组织成本集合)
     * @param stkCosts (组织成本集合)
     * @param b        (是否取反)
     * @return:
     * @auther: CLF
     * @date: 2019/11/15 9:37
     */
    public List<StkCost> createStkCostNotEmp(Long ownerId, List<StbDtl> list, List<StkCost> stkCosts, boolean b) {
        List<StkCost> returnList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list) && CollectionUtils.isNotEmpty(stkCosts)) {
            for (StkCost cost : stkCosts) {
                for (StbDtl stb : list) {
                    if (cost.getProdClsId().equals(stb.getProdClsId())) {
                        String val = "";
                        if (b) {
                            cost.setTotQty(cost.getTotQty().subtract(stb.getQty()));
                            if (cost.getTotQty().compareTo(new BigDecimal("0")) == 0) {
                                //数量为0 组织成本为 现有组织成本-库存成本
                                cost.setPgVal(cost.getTotQty().multiply(cost.getUnitCost()).add(cost.getPgVal().subtract(stb.getVal())));
                            } else {
                                cost.setPgVal(new BigDecimal("0"));
                                //总金额 数量*成本+未决-取反金额
                                val = cost.getTotQty().multiply(cost.getUnitCost()).add(cost.getPgVal()).subtract(stb.getVal()) + "";
                                //成本
                                cost.setUnitCost(new BigDecimal(val).divide(cost.getTotQty(), 4, BigDecimal.ROUND_CEILING));
                            }
                        } else {
                            cost.setTotQty(cost.getTotQty().add(stb.getQty()));
                            if (cost.getTotQty().compareTo(new BigDecimal("0")) == 0) {
                                cost.setPgVal(cost.getTotQty().multiply(cost.getUnitCost()).add(cost.getPgVal().add(stb.getVal())));
                            } else {
                                cost.setPgVal(new BigDecimal("0"));
                                //总金额 数量*成本+未决-取反金额
                                val = cost.getTotQty().multiply(cost.getUnitCost()).add(cost.getPgVal()).add(stb.getVal()) + "";
                                cost.setUnitCost(new BigDecimal(val).divide(cost.getTotQty(), 4, BigDecimal.ROUND_CEILING));
                            }
                        }
                        returnList.add(cost);
                        continue;
                    }
                }
            }
        }
        return returnList;
    }

    /**
     * 库存成本核算前验证仓库、会计组织、入库方式信息
     */
    public Wareh isVidate(StkCostModel stkCostModel) throws ServiceException {
        if (stkCostModel.getWarehId() == null) {
            throw new ServiceException("403", "仓库Id不能为空");
        }
        Wareh w = warehMapper.selectByWarehId(stkCostModel.getWarehId());
        if (w == null) {
            throw new ServiceException("403", "没有查询到仓库仓库Id:stkCostModel.getWarehId()");
        }
        if (w.getFsclUnitId() == null) {
            throw new ServiceException("403", "没有查询到仓库的会计组织");
        }
        if (StringUtils.isBlank(stkCostModel.getMode())) {
            throw new ServiceException("403", "入库方式不能为空");
        }
        return w;
    }
}
