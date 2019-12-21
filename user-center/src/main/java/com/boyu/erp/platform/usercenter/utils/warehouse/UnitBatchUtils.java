package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.BatchRefNum;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatch;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchDetail;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchNum;
import com.boyu.erp.platform.usercenter.mapper.warehouse.UnitBatchNumMapper;
import com.boyu.erp.platform.usercenter.model.batch.BatchModel;
import com.boyu.erp.platform.usercenter.service.warehouse.UnitBatchDetailSerivce;
import com.boyu.erp.platform.usercenter.service.warehouse.UnitBatchNumService;
import com.boyu.erp.platform.usercenter.utils.system.SysRefNumUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @program: boyu-platform_text
 * @description: 批次工具类
 * @author: clf
 * @create: 2019-07-05 10:54
 */
@Slf4j
@Component
@Transactional
public class UnitBatchUtils {
    @Autowired
    private BatchRefNumUtils batchRefNumUtils;
    @Autowired
    private UnitBatchNumService unitBatchNumService;
    @Autowired
    private UnitBatchDetailSerivce unitBatchDetailSerivce;
    @Autowired
    private SysRefNumUtils sysRefNumUtils;
    @Autowired
    private UnitBatchNumUtils unitBatchNumUtils;
    @Autowired
    private UnitBatchNumMapper unitBatchNumMapper;


    /**
     * 功能描述: 生产唯一批次编号(根据商品Id、组织Id 增量 生成唯一批次号)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/9 9:25
     */
    public String creatUnitBatchId(BatchModel unitBatch, SysUser user) throws Exception {
        String docType = unitBatch.getUnitBatchNum().getDocType();
        String docNume = unitBatch.getUnitBatchNum().getDocNum();
        UnitBatch ub = unitBatch.getUnitBatch();
        BatchRefNum b = new BatchRefNum();
        BeanUtils.copyProperties(ub, b);

        //增加批次号编号
        String batchId = batchRefNumUtils.addAndUpdate(b, user);

        /**
         * 根据商品首位数字对应不同英文
         * 0-9 对应 A,B,C,D,E,F,G,H,I
         * */
        String prodId = String.valueOf(b.getProdId()).substring(0, 1);
        String str = "A,B,C,D,E,F,G,H,I";
        m:
        for (int i = 0; i < 10; i++) {
            if (i == Integer.parseInt(prodId)) {
                prodId = str.split(",")[i];
                break m;
            }
        }
        log.info("批次编号：" + prodId + batchId);
        //增加商品编号
        sysRefNumUtils.instrSysRefNum(String.valueOf(ub.getProdId()), user);
        //增加商品编号明细
        sysRefNumUtils.instrSysRefNumDtl(String.valueOf(ub.getProdId()), ub.getUnitId(), user);
        //入库类型 (采购入库(供应商未介入)
        ub.setBatchId(prodId + batchId);
        //增加批次号序号
        UnitBatchNum batchNum = unitBatchType(ub);
        batchNum.setDocType(docType);
        batchNum.setDocNum(docNume);
        unitBatchNumService.addUnitBatchNum(batchNum);
        unitBatchDetailSerivce.addUnitBatchDetail(unitBatchTypeDetail(ub, batchNum, user), user);
        //记录批次异动信息
        unitBatchNumUtils.addUnitBatchReverse(batchNum, batchNum.getSurplusQuantity(), batchNum.getDocType(), batchNum.getDocNum());
        return prodId + batchId;
    }

    /**
     * 功能描述:  根据入库类型才生批次编号序号对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/9 15:29
     */
    public UnitBatchNum unitBatchType(UnitBatch ub) {
        log.info("{批次信息： }" + ub);
        int qty = 0;
        if (CommonFainl.BatchTypePe.equalsIgnoreCase(ub.getBatchCreatType())) {
            //采购
            qty = ub.getPurchaseQuantity();
        }
        if (CommonFainl.BatchTypePn.equalsIgnoreCase(ub.getBatchCreatType())) {
            //生产
            qty = ub.getProductionQuantity();
        }
        if (CommonFainl.BatchTypePe.equalsIgnoreCase(ub.getBatchCreatType()) || CommonFainl.BatchTypePn.equalsIgnoreCase(ub.getBatchCreatType())) {
            UnitBatchNum num = new UnitBatchNum();
            num.setBatchId(ub.getBatchId());
            num.setProdId(ub.getProdId());
            //供应商(供应商采购商都是自己)
            num.setVendeeId(ub.getUnitId());
            //采购商
            num.setVenderId(ub.getUnitId());
            //组织
            num.setUnitId(ub.getUnitId());
            //批次序号
            num.setBatchNumber(Integer.parseInt(sysRefNumUtils.getLastNum(ub.getProdId() + "", ub.getUnitId()) + ""));
            //采购商品数量 供应商不介入时可能是生成入库或采购入库对批次明细来说都是采购入库 因此需要传入数量
            num.setPurchaseQuantity(qty);
            //销售数量
            num.setMarketQuantity(0);
            //剩余商品数量
            num.setSurplusQuantity(ub.getSurplusQuantity());
            // 顾客可退数量(顾客可退给门店或组织)
            num.setReturnableShopQuantity(0);
            //组织可退数量(门店可退给组织或组织可退给组织)
            num.setReturnableUnitQuantity(0);
            //批次价格(采购成本价格)
            log.info("价格：" + ub.getPrice());
            num.setPrice(ub.getPrice());
            //'nol 正常批次号 spi 特殊批次号'
            //    private String batchType;
            num.setBatchType(CommonFainl.BatchType_NOL);
            num.setCreatTime(new Date());
            //入库单编号(不重要字段)
            num.setDocNum(ub.getDocNum());
            log.info("增加新批次信息:{UnitBatchNum} " + num);
            return num;
        }
        return null;
    }

    /**
     * 功能描述:  根据入库类型才生批次明细对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/9 15:29
     */
    public UnitBatchDetail unitBatchTypeDetail(UnitBatch ub, UnitBatchNum ubNum, SysUser user) {
        UnitBatchDetail batchDetail = new UnitBatchDetail();
        if (CommonFainl.BatchTypePe.equalsIgnoreCase(ub.getBatchCreatType())) {
            //操作描述
            //    private String description;
            batchDetail.setDescription("采购入库");
            batchDetail.setBatchReverse("utpe");
            // 批次号扭转类型
            setUnitBatchDetail(ub, ubNum, user, batchDetail);
        }
        if (CommonFainl.BatchTypePn.equalsIgnoreCase(ub.getBatchCreatType())) {
            setUnitBatchDetail(ub, ubNum, user, batchDetail);
            batchDetail.setDescription("生产入库");
            batchDetail.setBatchReverse("pdcn");
        }
        return batchDetail;
    }

    private void setUnitBatchDetail(UnitBatch ub, UnitBatchNum ubNum, SysUser user, UnitBatchDetail batchDetail) {
        // 批次Id
        batchDetail.setBatchId(ub.getBatchId());
        //批次序号
        batchDetail.setBatchNumber(Long.parseLong(ubNum.getBatchNumber() + ""));
        //组织Id
        batchDetail.setUnitId(ub.getUnitId());
        //采购商Id(如果门店卖给顾客则为缺省值为-1)
        batchDetail.setVenderId(ub.getUnitId());
        //供应商(供应商采购商都是自己)
        batchDetail.setVendeeId(ub.getUnitId());
        if (ub.getBatchCreatType().equalsIgnoreCase("pe") ||
                ub.getBatchCreatType().equalsIgnoreCase(CommonFainl.BatchTypeIl)) {
            //采购数量
            batchDetail.setPurchaseQuantity(ub.getPurchaseQuantity());
        }
        if (ub.getBatchCreatType().equalsIgnoreCase("pn")) {
            //采购数量
            batchDetail.setPurchaseQuantity(ub.getProductionQuantity());
        }
        // 销售数量quantitymarketQuantity
        batchDetail.setQuantitymarketQuantity(0);
        //批次价格(采购成本价格)
        batchDetail.setPrice(ub.getPrice());
        //    创建时间
        batchDetail.setCreatTime(new Date());
        //操作用户
        batchDetail.setOprId(user.getUserId());
        batchDetail.setBatchType(CommonFainl.BatchType_NOL);
    }


}
