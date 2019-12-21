package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatch;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchDetail;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchNum;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.UnitBatchMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.UnitBatchNumMapper;
import com.boyu.erp.platform.usercenter.model.batch.BatchModel;
import com.boyu.erp.platform.usercenter.model.batch.WarehBatchModel;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumDtlSerivce;
import com.boyu.erp.platform.usercenter.service.warehouse.UnitBatchSerivce;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.UnitBatchNumUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.UnitBatchUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: boyu-platform_text
 * @description: 批次服务
 * @author: clf
 * @create: 2019-07-05 09:38
 */
@Slf4j
@Service
@Transactional
public class UnitBatchSerivceImpl implements UnitBatchSerivce {

    @Autowired
    private UnitBatchMapper unitBatchMapper;

    @Autowired
    private SysRefNumDtlSerivce sysRefNumDtlSerivce;
    @Autowired
    private UnitBatchUtils unitBatchUtils;
    @Autowired
    private UnitBatchNumMapper unitBatchNumMapper;

    @Autowired
    private UnitBatchNumUtils unitBatchNumUtils;

    /**
     * 这个是用来给销售采购生成正常批次的
     * <p>
     * 注：批次号为虚拟运行逻辑，就目前而言，仅仅是为了解决组织退货时货物价格问题。现在设定批次号后按照现进先出原则。
     * 批次号只和商品Id(sku)一一对应。
     * (由于商品Id并不能代表唯一商品，在本系统商品Id代表同一类型商品)
     * 只是在不同组织同一种商品在进货时会产生不同批次序号，这里批次序号就决定商品退货价格
     * 组织生成批次号:分为生产入库，采购入库(供应未介入)则产生第一次的批次号
     * <p>
     * 添加编号表(仅在第一次生成批次号时添加编号，后续添加编号明细)
     * 判断商品Id在编号表内没有编号就生成编号
     * 1.添加组织编号表
     * 2.添加编号批次表
     * 3.添加编号明细表
     */
    @Override
    public int addUnitBatch(BatchModel u, String docType, SysUser user) throws Exception {
        if (StringUtils.NullEmpty(docType) || StringUtils.NullEmpty(u.getUnitBatch().getDocNum())) {
            throw new ServiceException("403", "批次单据信息为空");
        }
        UnitBatch batch = u.getUnitBatch();
        u.getUnitBatchNum().setDocNum(u.getUnitBatch().getDocNum());
        u.getUnitBatchNum().setDocType(docType);
        //供应商未介入
        if (!batch.getBatchCreatType().equalsIgnoreCase(CommonFainl.BatchTypeIl)) {
            //增加批次明细
            String batchId = unitBatchUtils.creatUnitBatchId(u, user);
            batch.setBatchId(batchId);
            SysRefNumDtl dtl = new SysRefNumDtl();
            dtl.setRefNumId(batch.getProdId() + "");
            dtl.setUnitId(batch.getUnitId());
            batch.setNumber(Integer.parseInt(sysRefNumDtlSerivce.findById(dtl).getLastNum() + ""));
            batch.setCreatTime(new Date());
            //入库类型 (采购入库(供应商未介入)
            if (CommonFainl.BatchTypePe.equalsIgnoreCase(batch.getBatchCreatType()) &&
                    batch.getPurchaseQuantity() == 0) {
                //采购数量
                throw new ServiceException("403", "入库类型为采购，采购数量不能为0");
            }
            //生产入库
            if (CommonFainl.BatchTypePn.equalsIgnoreCase(batch.getBatchCreatType()) &&
                    batch.getProductionQuantity() == 0) {
                //生产商品数量
                throw new ServiceException("403", "入库类型为生成，生成数量不能为0");
            }
            return unitBatchMapper.insertUnitBatch(batch);
        } else {
            return unitBatchNumUtils.addBatchNumText(u, user);
        }
    }

    /**
     * 查询商品是否有批次号
     */
    @Override
    @Transactional(readOnly = true)
    public UnitBatch selectProdcut(UnitBatch u) {
        return unitBatchMapper.countProdCut(u);
    }

    /**
     * 功能描述:  查询组织可退商品数量与价格
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/23 18:46
     */
    @Override
    public List<UnitBatchNum> getUnitBatchNum(UnitBatchNum unitBatchNum) {
        return unitBatchNumMapper.getUnitBatchNum(unitBatchNum);
    }

    /**
     * 查询组织可退商品总数量
     *
     * @param unitBatchNum
     * @return
     */
    @Override
    public int getSumByUnitBatchNum(UnitBatchNum unitBatchNum) {
        return unitBatchNumMapper.getSumByUnitBatchNum(unitBatchNum);
    }

    /**
     * 功能描述: 通过采购商、供应商、出入库类型、商品明细生成正常批次对象
     * 1. 批次只和真实出库数量、入库收货数量相关
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/20 10:58
     */
    @Override
    public List<BatchModel> createBatch(WarehBatchModel model) throws Exception {
        //由于目前针对桃花季所有采购商都是介入 也就是说销售时所有采购商都会入库,只用考虑销售卖给顾客顾客组织为 -1L
        String type = model.getType();
        if (!("D".equalsIgnoreCase(type) || "R".equalsIgnoreCase(type))) {
            throw new ServiceException(JsonResultCode.FAILURE, "单据类型错误");
        }
        if ("R".equalsIgnoreCase(type)) {
            //供应商介入
            if (model.getIntervene()) {
                return cretaeStbDtl(model.getVendeeId(), model.getVenderId(), model.getStbDtls(), "il");
            } else {
                //供应商未介入
                return cretaeStbDtl(model.getVendeeId(), model.getVenderId(), model.getStbDtls(), "pe");
            }
        }
        //销售卖给会员 生成批次是顾客采购入库
        if ("D".equalsIgnoreCase(type)) {
            if (model.getVendeeId() == -1L) {
                return cretaeStbDtl(model.getVendeeId(), model.getVenderId(), model.getStbDtls(), "il");
            }
        }
        return null;
    }

    /**
     * 查询批次集合
     *
     * @param prodIds 商品Id
     * @param unitId  组织Id
     * @return 批次信息集合
     * @author HHe
     * @date 2019/12/3 17:40
     */
    @Override
    public List<UnitBatch> queryBatchsByProdIdsAndUnit(Set<Long> prodIds, Long unitId) {
        return unitBatchMapper.queryBatchsByProdIdsAndUnit(prodIds, unitId);
    }

    /**
     * 根据采购商 销售商 入库单明细 和入库类型生成批次对象
     * 1. 调用主要判断 type 值，
     * 出库调用时只有一种情况：销售给顾客出库时，实际顾客采购入库 vendeeId 为 -1L   venderId 为销售Id type为“il"。
     * 采购供应商未接入 pe
     * 采购供应商接入 il
     * 生成入库 pn
     */
    public List<BatchModel> cretaeStbDtl(Long vendeeId, Long venderId, List<StbDtl> list, String type) {
        List<BatchModel> rest = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            List<StbDtl> stbs = list.stream().filter(s -> (s.getQty().compareTo(new BigDecimal("0"))) > 0).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(stbs)) {
                for (StbDtl stbDtl : stbs) {
                    UnitBatch batch = new UnitBatch();
                    batch.setBatchCreatType(type);
                    if (CommonFainl.BatchTypeIl.equalsIgnoreCase(type)) {
                        UnitBatchNum ub = new UnitBatchNum();
                        //采购组织为顾客
                        ub.setUnitId(vendeeId);
                        ub.setVenderId(venderId);
                        ub.setVendeeId(vendeeId);
                        //采购总数量
                        ub.setPurchaseQuantity(stbDtl.getQty().intValue());
                        //商品Id
                        ub.setProdId(stbDtl.getProdId());
                        //采购价
                        ub.setPrice(Float.parseFloat(stbDtl.getFnlPrice() + ""));
                        BatchModel batchModel = new BatchModel(batch, ub, new UnitBatchDetail());
                        rest.add(batchModel);
                    } else {
                        batch.setUnitId(vendeeId);
                        batch.setProdId(stbDtl.getProdId());
                        //价格
                        batch.setPrice(Float.parseFloat(stbDtl.getUnitPrice() + ""));
                        //销售数量
                        batch.setMarketQuantity(0);
                        if (CommonFainl.BatchTypePe.equalsIgnoreCase(type)) {
                            //采购商品数量
                            batch.setPurchaseQuantity(stbDtl.getQty().intValue());
                            batch.setProductionQuantity(0);
                        }
                        if (CommonFainl.BatchTypePn.equalsIgnoreCase(type)) {
                            //生成商品数量
                            batch.setProductionQuantity(stbDtl.getQty().intValue());
                            batch.setPurchaseQuantity(0);
                        }
                        //剩余数量
                        batch.setSurplusQuantity(stbDtl.getQty().intValue());
                        BatchModel batchModel = new BatchModel(batch, new UnitBatchNum(), new UnitBatchDetail());
                        rest.add(batchModel);
                    }
                }

            }
        }
        return rest;
    }


}
