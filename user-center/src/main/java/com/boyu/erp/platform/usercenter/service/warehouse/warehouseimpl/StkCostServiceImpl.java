package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.CostGrp;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.StkCost;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehCost;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.StkCostMapper;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;
import com.boyu.erp.platform.usercenter.model.wareh.StkCostModel;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.CostGrpService;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.service.warehouse.AsyncService;
import com.boyu.erp.platform.usercenter.service.warehouse.CostDmodeService;
import com.boyu.erp.platform.usercenter.service.warehouse.StkCostService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehCostService;
import com.boyu.erp.platform.usercenter.utils.warehouse.AsynchronizationUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.StkCostUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 库存成本
 *
 * @author HHe
 * @date 2019/8/28 10:48
 */

@Service
@Transactional
public class StkCostServiceImpl implements StkCostService {
    @Autowired
    private StkCostMapper stkCostMapper;
    @Autowired
    private CostDmodeService costDmodeService;
    @Autowired
    private StkCostUtils stkCostUtils;
    @Autowired
    private ProductService productService;
    @Autowired
    private AsynchronizationUtils asynUtils;
    @Autowired
    private WarehCostService warehCostService;
    @Autowired
    private ProdClsService prodClsService;
    @Autowired
    private CostGrpService costGrpService;
    @Autowired
    private SysParameterService sysParameterService;
    @Autowired
    private AsyncService asyncService;
    /**
     * 成本核算时是否价税分离参数
     */
    private static final String TAX_STRIPPED_WHEN_COSTING = "TAX_STRIPPED_WHEN_COSTING";

    /**
     * 根据组织和品种id查询成本
     *
     * @author HHe
     * @date 2019/8/28 10:59
     */
    @Override
    public StkCost queryStkCostByUnitCls(StkCost stkCost) {
        return stkCostMapper.queryStkCostByUnitCls(stkCost);

    }

    /**
     * 根据组织Id和商品信息集合的品种信息查询成本集合
     *
     * @author HHe
     * @date 2019/9/5 19:58
     */
    @Override
    public List<StkCost> queryStkCostListByUnitproductLists(Long unitId, List<Product> productList) {
        return stkCostMapper.queryStkCostListByUnitproductLists(unitId, productList);
    }

    /**
     * 判断并核算成本
     * costChanged 是否影响成本
     * reversed 是否取反
     *
     * @author HHe
     * @date 2019/10/22 10:22
     */
    @Override
    public List<CountCostModel> instantCalculate(StkCostModel stkCostModel) {
        //==============================================================================================
        //----------------------------------------------------------------------------------------------
        //计算成本集合（用于接收明细中的所有数据）
        List<CountCostModel> countCostModels = new ArrayList<>();
        //获取商品信息；
        List<Long> prodIds = new ArrayList<>();
        //查询成本组信息
        CostGrp costGrp = costGrpService.queryCostGrpByUnitId(stkCostModel.getFsclUnitId());
        for (StbDtl stbDtl : stkCostModel.getStbDtls()) {
            CountCostModel countCostModel = new CountCostModel();
            //商品Id
            countCostModel.setProdId(stbDtl.getProdId());
            //数量
            countCostModel.setQty(stbDtl.getQty());
            //金额
            countCostModel.setVal(stbDtl.getVal());
            //税款
            countCostModel.setTax(stbDtl.getTax());
            countCostModels.add(countCostModel);
            prodIds.add(stbDtl.getProdId());
        }
        //----------------------------------------------------------------------------------------------
        //获取对应品种信息用于查询成本,和商品库存管理信息；
        List<Product> products = productService.queryProductListByProdList(prodIds);
        Set<Long> prodClsIds = new HashSet<>();
        //将品种信息封装入商品成本集合中
        for (Product product : products) {
            prodClsIds.add(product.getProdClsId());
            for (CountCostModel countCostModel : countCostModels) {
                if (countCostModel.getProdId().equals(product.getProdId())) {
                    countCostModel.setProdClsId(product.getProdClsId());
                }
            }
        }
        //----------------------------------------------------------------------------------------------
        //查询商品品种是否库存管理
        List<ProdCls> prodClsList = prodClsService.queryProdClsListByIds(prodClsIds);
        //库存管理的商品品种
        Set<Long> stkAdpProdCls = new HashSet<>();
        for (ProdCls prodCls : prodClsList) {
            if ("T".equals(prodCls.getStkAdopted())) {
                stkAdpProdCls.add(prodCls.getProdClsId());
            }
        }
        //----------------------------------------------------------------------------------------------
        //叠加去重（为了少的调用数据库修改成本）
        //根据品种判断是否相同，相同则将相同的品种的金额和数量添加
        List<CountCostModel> costModels = new ArrayList<>();
        for (CountCostModel countCostModel : countCostModels) {
            //默认放第一个值进行比较
            if (costModels.isEmpty()) {
                costModels.add(countCostModel);
                continue;
            }
            //控制添加和修改，如果已经修改就无须添加
            boolean b = true;
            for (int i = 0; i < costModels.size(); i++) {
                if (costModels.get(i).getProdClsId().equals(countCostModel.getProdClsId())) {
                    //叠加金额
                    costModels.get(i).setVal(costModels.get(i).getVal().add(countCostModel.getVal()));
                    //叠加数量
                    costModels.get(i).setQty(costModels.get(i).getQty().add(countCostModel.getQty()));
                    //叠加税款
                    costModels.get(i).setTax(costModels.get(i).getTax().add(countCostModel.getTax()));
                    b = false;
                    break;
                }
            }
            if (b) {
                costModels.add(countCostModel);
            }
        }
        //----------------------------------------------------------------------------------------------
        //查询仓库成本和库存成本信息
        List<WarehCost> warehCostList = warehCostService.queryWarehCostListByClsAndWareh(stkAdpProdCls, stkCostModel.getWarehId());
        List<StkCost> stkCostList = stkCostMapper.queryListByWarehAndClsList(stkAdpProdCls, stkCostModel.getFsclUnitId());
        //计算成本
        if (stkCostModel.getCostChanged()) {
            //改变成本；
            //计算成本，有修改，没有添加；
            //根据品种核算出来的成本
            List<CountCostModel> list = countCost(warehCostList, stkCostList, costModels, stkCostModel.getReversed());
            //todo 异步修改库存成本（只改成本，数量由库存数量修改）
            asyncService.updateCost(list, costGrp.getCostGrpId(), stkCostModel.getWarehId(), stkCostModel.getFsclUnitId());
            //封装成本
            for (CountCostModel clsCost : list) {
                for (CountCostModel countCostModel : countCostModels) {
                    if (clsCost.getProdClsId().equals(countCostModel.getProdClsId())) {
                        //组织成本
                        countCostModel.setUnitCost(clsCost.getUnitCost());
                        //仓库成本
                        countCostModel.setWarehCost(clsCost.getWarehCost());
                    }
                }
            }
        } else {
            //不改变成本
            //库存数量、仓库数量、商品库存管理数量不相等则异常
//            if(warehCostList.size()!=stkAdpProdCls.size()||stkCostList.size()!=stkAdpProdCls.size()){
//                throw new ServiceException("201", "成本信息差异");
//            }
            //封装信息
            for (CountCostModel countCostModel : countCostModels) {
                boolean wB = true;
                boolean sB = true;
                for (WarehCost warehCost : warehCostList) {
                    if (countCostModel.getProdClsId().equals(warehCost.getProdClsId())) {
                        countCostModel.setWarehCost(warehCost.getWarehCost());
                        wB = false;
                    }
                }
                for (StkCost stkCost : stkCostList) {
                    if (countCostModel.getProdClsId().equals(stkCost.getProdClsId())) {
                        countCostModel.setUnitCost(stkCost.getUnitCost());
                        sB = false;
                    }
                }
                if (wB || sB) {
                    throw new ServiceException("201", "库存成本信息异常");
                }
            }
        }
        return countCostModels;
    }

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
    @Override
    public List<StkCost> createStkCost(StkCostModel stkCostModel) throws ServiceException {
        if (CollectionUtils.isEmpty(stkCostModel.getStbDtls())) {
            throw new ServiceException("403", "库存明细为空成本为空");
        }
        return stkCostUtils.createStkCost(stkCostModel);
    }

    /**
     * 功能描述: 存在库存修改不存在添加
     * 1.修改组织成本本身
     * 2.修改库存单、库存单明细成本
     *
     * @param stkCosts
     * @return:
     * @auther: CLF
     * @date: 2019/11/18 15:21
     */
    @Override
    public int addUpdateStkcost(List<StkCost> stkCosts, Long unitId) {
        if (CollectionUtils.isNotEmpty(stkCosts)) {
            int a = stkCostMapper.insertUpdateStkCostList(stkCosts);
            //异步修改所有库存明细单据库存成本
            asynUtils.stkCostUpdateStbDtl(stkCosts, unitId);
            return a;
        }
        return 0;
    }

    /**
     * 添加修改库存成本（只修改成本）
     *
     * @author HHe
     * @date 2019/11/22 11:19
     */
    @Override
    public int insertUpdateStkCost(List<CountCostModel> list, Long costGrpId, Long fsclUnitId) {
        return stkCostMapper.insertUpdateStkCost(list, costGrpId, fsclUnitId);
    }

    /**
     * 修改库存数量
     *
     * @author HHe
     * @date 2019/11/22 12:31
     */
    @Override
    public int updateByQty(List<CountCostModel> countCostModels, Long fsclUnitId) {
        return stkCostMapper.updateByQty(countCostModels, fsclUnitId);
    }


    /**
     * 计算成本
     *
     * @param warehCostList 库中仓库成本信息
     * @param stkCostList   库中组织成本信息
     * @param costModels    需要参与计算的单据信息
     * @author HHe
     * @date 2019/10/25 11:25
     */
    private List<CountCostModel> countCost(List<WarehCost> warehCostList, List<StkCost> stkCostList, List<CountCostModel> costModels, boolean reversed) {
        //取反（库存金额-单据金额）/（库存数量-单据数量）
        //1.库存数量；2.库存成本；3.单据数量；4.单据金额；5.未决金额；6.税款
        //需要判断价税分离：如果价税分离则需要将明细中的金额减去税款（tax）作为单据金额
        SysParameter pTSeparate = sysParameterService.findByParameter(TAX_STRIPPED_WHEN_COSTING);
        if (pTSeparate == null) {
            throw new ServiceException("201", "价税分离参数信息有误");
        }
        //是否价税分离
        boolean ptb = "T".equals(pTSeparate.getParmVal().toUpperCase()) ? true : false;
        //需要修改的信息
        for (CountCostModel costModel : costModels) {
            //初始化全部参与计算的参数
            //单据金额（参与取反）
            BigDecimal ttlVal = reversed ? costModel.getVal().negate() : costModel.getVal();
            //单据数量（参与取反）
            BigDecimal ttlQty = reversed ? costModel.getQty().negate() : costModel.getQty();
            if (ptb) {
                //价税分离，金额减掉税款
                ttlVal = costModel.getVal().subtract(costModel.getTax());
            }
            //仓库未决金额
            BigDecimal warehPPgVal = BigDecimal.ZERO;
            //仓库数量
            BigDecimal warehPQty = BigDecimal.ZERO;
            //仓库成本
            BigDecimal warehPCost = BigDecimal.ZERO;
            //仓库金额
            BigDecimal warehPVal = BigDecimal.ZERO;
            for (WarehCost warehCost : warehCostList) {
                //匹配品种信息
                if (warehCost.getProdClsId().equals(costModel.getProdClsId())) {
                    warehPPgVal = warehCost.getPgVal();
                    warehPQty = warehCost.getTotQty();
                    warehPCost = warehCost.getWarehCost();
                    warehPVal = warehCost.getTotVal();
                }
            }
            //库存成本
            //组织未决金额
            BigDecimal unitPPgVal = BigDecimal.ZERO;
            //组织数量
            BigDecimal unitPQty = BigDecimal.ZERO;
            //组织成本
            BigDecimal unitPCost = BigDecimal.ZERO;
            for (StkCost stkCost : stkCostList) {
                if (stkCost.getProdClsId().equals(costModel.getProdClsId())) {
                    unitPPgVal = stkCost.getPgVal();
                    unitPQty = stkCost.getTotQty();
                    unitPCost = stkCost.getUnitCost();
                }
            }
            //成本算法：（当前成本 * 当前库存 + 未决金额 + 入库金额） / (当前库存 + QTY）
            //仓库
            //除数(库存数量+单据数量)
            BigDecimal wDivisor = warehPQty.add(ttlQty);
            //除数为0，成本不变，登记未决为被除数
            if (wDivisor.signum() == 0) {
                warehPPgVal = warehPCost.multiply(warehPQty).add(warehPPgVal).add(ttlVal);
            } else {
                warehPCost = warehPCost.multiply(warehPQty).add(warehPPgVal).add(ttlVal).divide(wDivisor).setScale(4, BigDecimal.ROUND_HALF_UP);
                warehPPgVal = BigDecimal.ZERO;
            }
//            warehPQty = wDivisor;
            warehPVal = warehPVal.add(ttlVal);
            //组织
            BigDecimal uDivisor = unitPQty.add(ttlVal);
            if (uDivisor.signum() == 0) {
                unitPPgVal = unitPCost.multiply(unitPQty).add(unitPPgVal).add(ttlVal);
            } else {
                unitPCost = unitPCost.multiply(unitPQty).add(unitPPgVal).add(ttlVal).divide(uDivisor).setScale(4, BigDecimal.ROUND_HALF_UP);
                unitPPgVal = BigDecimal.ZERO;
            }
//            unitPQty = wDivisor;
            //商品仓库成本
            costModel.setWarehCost(warehPCost);
            //商品仓库数量
//            costModel.setWarehQty(warehPQty);
            //商品仓库金额
            costModel.setWarehVal(warehPVal);
            //仓库未决金额
            costModel.setWarehPgVal(warehPPgVal);
            //商品组织成本
            costModel.setUnitCost(unitPCost);
            //商品组织数量
//            costModel.setUnitQty(unitPQty);
            //组织未决金额
            costModel.setUnitPgVal(unitPPgVal);
            //优化后
            //==============================================================================================
            //优化前
//            boolean wb = true;
//            boolean sb = true;
//            BigDecimal warehProdQty = new BigDecimal("0");
//            BigDecimal warehProdVal = new BigDecimal("0");
//            BigDecimal warehProdCost = new BigDecimal("0");
//            BigDecimal warehProdPgVal = new BigDecimal("0");
//            BigDecimal unitProdQty = new BigDecimal("0");
//            BigDecimal unitProdCost = new BigDecimal("0");
//            //仓库
//            for (WarehCost warehCost : warehCostList) {
//                if (warehCost.getProdClsId().equals(costModel.getProdClsId())) {
//                    wb = false;
//                    if (reversed) {
//                        //仓库数量
//                        warehProdQty = warehCost.getTotQty().subtract(costModel.getQty());
//                        if ("T".equals(pTSeparate.getParmVal())) {
//                            //仓库金额
//                            warehProdVal = warehCost.getTotVal().subtract(costModel.getVal().subtract(costModel.getTax()));
//                            //除数
//                            BigDecimal divisor = warehCost.getTotQty().subtract(costModel.getQty());
//                            //成本
//                            //除数为0，记录未决金额，不修改单位成本
//                            if(divisor.signum()==0){
//                                warehProdCost = warehCost.getWarehCost();
//                                warehProdPgVal = warehCost.getTotVal().subtract(costModel.getVal().subtract(costModel.getTax()));
//                            }else{
//                                warehProdCost = (warehCost.getTotVal().subtract(costModel.getVal().subtract(costModel.getTax()))).divide(divisor).setScale(4,BigDecimal.ROUND_HALF_UP);
//                                warehProdPgVal = new BigDecimal("0");
//                            }
//                        } else {
//                            //仓库金额
//                            warehProdVal = warehCost.getTotVal().subtract(costModel.getVal());
//                            //除数
//                            BigDecimal divisor = warehCost.getTotQty().subtract(costModel.getQty());
//                            if(divisor.signum()==0){
//                                warehProdCost = warehCost.getWarehCost();
//                                warehProdPgVal = warehCost.getTotVal().subtract(costModel.getVal());
//                            }else{
//                                //成本
//                                warehProdCost = (warehCost.getTotVal().subtract(costModel.getVal())).divide(divisor).setScale(4,BigDecimal.ROUND_HALF_UP);
//                                warehProdPgVal = new BigDecimal("0");
//                            }
//                        }
//                    } else {
//                        //仓库数量
//                        warehProdQty = warehCost.getTotQty().add(costModel.getQty());
//                        if ("T".equals(pTSeparate.getParmVal())) {
//                            //仓库金额
//                            warehProdVal = warehCost.getTotVal().add(costModel.getVal().subtract(costModel.getTax()));
//                            //成本
//                            warehProdCost = (warehCost.getTotVal().add(costModel.getVal().subtract(costModel.getTax()))).divide(warehCost.getTotQty().add(costModel.getQty()).setScale(4,BigDecimal.ROUND_HALF_UP));
//                        } else {
//                            //仓库金额
//                            warehProdVal = warehCost.getTotVal().add(costModel.getVal());
//                            //成本
//                            warehProdCost = (warehCost.getTotVal().add(costModel.getVal())).divide(warehCost.getTotQty().add(costModel.getQty()).setScale(4,BigDecimal.ROUND_HALF_UP));
//                        }
//                    }
//                }
//            }
//            //库存中不包含商品信息
//            if (wb) {
//                if (reversed) {
//                    //仓库数量
//                    warehProdQty = costModel.getQty().negate();
//                    if ("T".equals(pTSeparate.getParmVal())) {
//                        //仓库金额
//                        warehProdVal = costModel.getVal().subtract(costModel.getTax()).negate();
//                        //成本
//                        warehProdCost = costModel.getVal().subtract(costModel.getTax()).divide(costModel.getQty().setScale(4,BigDecimal.ROUND_HALF_UP));
//                    } else {
//                        //仓库金额
//                        warehProdVal = costModel.getVal().negate();
//                        //成本
//                        warehProdCost = costModel.getVal().divide(costModel.getQty().setScale(4,BigDecimal.ROUND_HALF_UP));
//                    }
//                } else {
//                    //仓库数量
//                    costModel.setWarehQty(costModel.getQty());
//                    if ("T".equals(pTSeparate.getParmVal())) {
//                        //仓库金额
//                        costModel.setWarehVal(costModel.getVal().subtract(costModel.getTax()));
//                        //成本
//                        costModel.setWarehCost(costModel.getVal().subtract(costModel.getTax()).divide(costModel.getQty()).setScale(4,BigDecimal.ROUND_HALF_UP));
//                    } else {
//                        //仓库金额
//                        costModel.setWarehVal(costModel.getVal());
//                        //成本
//                        costModel.setWarehCost(costModel.getVal().divide(costModel.getQty()).setScale(4,BigDecimal.ROUND_HALF_UP));
//                    }
//                }
//            }
//            costModel.setWarehQty(warehProdQty);
//            costModel.setWarehVal(warehProdVal);
//            costModel.setWarehCost(warehProdCost);
//            //库存成本
//            for (StkCost stkCost : stkCostList) {
//                if (stkCost.getProdClsId().equals(costModel.getProdClsId())) {
//                    sb = false;
//                    if (reversed) {
//                        //组织总数
//                        costModel.setUnitQty(stkCost.getTotQty().subtract(costModel.getQty()));
//                        if ("T".equals(pTSeparate.getParmVal())) {
//                            //计算成本
//                            BigDecimal cost = (stkCost.getTotQty().multiply(stkCost.getUnitCost()).subtract(costModel.getVal().subtract(costModel.getTax()))).divide(stkCost.getTotQty().subtract(costModel.getQty())).setScale(4,BigDecimal.ROUND_HALF_UP);
//                            //组织成本
//                            costModel.setUnitCost(cost);
//                        } else {
//                            //计算成本
//                            BigDecimal cost = (stkCost.getTotQty().multiply(stkCost.getUnitCost()).subtract(costModel.getVal())).divide(stkCost.getTotQty().subtract(costModel.getQty())).setScale(4,BigDecimal.ROUND_HALF_UP);
//                            //组织成本
//                            costModel.setUnitCost(cost);
//                        }
//                    } else {
//                        //组织总数
//                        costModel.setUnitQty(stkCost.getTotQty().add(costModel.getQty()));
//                        if ("T".equals(pTSeparate.getParmVal())) {
//                            //计算成本
//                            BigDecimal cost = (stkCost.getTotQty().multiply(stkCost.getUnitCost()).add(costModel.getVal().subtract(costModel.getTax()))).divide(stkCost.getTotQty().add(costModel.getQty())).setScale(4,BigDecimal.ROUND_HALF_UP);
//                            //组织成本
//                            costModel.setUnitCost(cost);
//                        } else {
//                            //计算成本
//                            BigDecimal cost = (stkCost.getTotQty().multiply(stkCost.getUnitCost()).add(costModel.getVal())).divide(stkCost.getTotQty().add(costModel.getQty())).setScale(4,BigDecimal.ROUND_HALF_UP);
//                            //组织成本
//                            costModel.setUnitCost(cost);
//                        }
//                    }
//                }
//            }
//            if (sb) {
//                if (reversed) {
//                    //组织总数
//                    costModel.setUnitQty(costModel.getUnitQty().negate());
//                    if ("T".equals(pTSeparate.getParmVal())) {
//                        //组织成本
//                        costModel.setUnitCost(costModel.getVal().subtract(costModel.getTax()).divide(costModel.getQty()).setScale(4,BigDecimal.ROUND_HALF_UP));
//                    } else {
//                        //组织成本
//                        costModel.setUnitCost(costModel.getVal().divide(costModel.getQty()).setScale(4,BigDecimal.ROUND_HALF_UP));
//                    }
//                } else {
//                    //组织总数
//                    costModel.setUnitQty(costModel.getUnitQty());
//                    if ("T".equals(pTSeparate.getParmVal())) {
//                        //组织成本
//                        costModel.setUnitCost(costModel.getVal().subtract(costModel.getTax()).divide(costModel.getQty()).setScale(4,BigDecimal.ROUND_HALF_UP));
//                    } else {
//                        //组织成本
//                        costModel.setUnitCost(costModel.getVal().divide(costModel.getQty()).setScale(4,BigDecimal.ROUND_HALF_UP));
//                    }
//                }
//            }
        }
        return costModels;
    }
}
