package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Tfn;
import com.boyu.erp.platform.usercenter.entity.warehouse.TfnBxi;
import com.boyu.erp.platform.usercenter.entity.warehouse.TfnDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.service.warehouse.TfnBxiService;
import com.boyu.erp.platform.usercenter.service.warehouse.TfnDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.TfnService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehService;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnBxiVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnDtlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 调拨单工具类
 */
@Slf4j
@Component
public class TfnUtil {
    @Autowired
    private TfnService tfnService1;

    @Autowired
    private TfnDtlService tfnDtlService;

    @Autowired
    private TfnBxiService tfnBxiService;

    @Autowired
    private WarehService warehService;


    /**
     * 生成调拨单编号
     * 规则（同一组织下增长1，不满8位用0填充）
     */
    public String generateTfnNum (Tfn tfn){
        String newTfnNum = "";
        String maxTfnNum = tfnService1.getMaxTfnNum(tfn);
        long tfnMumLong = Long.parseLong(maxTfnNum);
        long newTfnNumLong = tfnMumLong + 1;
        for (int i = (newTfnNumLong + "").length(); i < 8; i++) {
            newTfnNum += "0";
        }
        return newTfnNum + newTfnNumLong;
    }

    /**
     * 每次更新/删除/修改调拨单明细或者调配单，都会更新主表关联字段
     * (总数量，总金额，总税款，总市值，总箱数)
     */
    public int reCalTfnInfo(Tfn tfn) {
        Tfn info = tfnService1.getTfnByPk(tfn);
        String bxiEnabled = info.getBxiEnabled();
        BigDecimal totalNum = new BigDecimal(0);
        BigDecimal totalAmt = new BigDecimal(0);
        BigDecimal totalTax = new BigDecimal(0);
        BigDecimal totalTak = new BigDecimal(0);
        int totalBox = 0;
        if (!"Y".equals(bxiEnabled)) {
            TfnDtl dtl = new TfnDtl();
            dtl.setUnitId(tfn.getUnitId());
            dtl.setTfnNum(tfn.getTfnNum());
            List<TfnDtlVo> tfnDtlList = tfnDtlService.getTfnDtlList(dtl);
            for (TfnDtlVo _tfnDtl : tfnDtlList) {
                totalNum = totalNum.add(_tfnDtl.getQty());
                totalAmt = totalAmt.add(_tfnDtl.getVal());
                totalTax = totalTax.add(_tfnDtl.getTax());

            }
            totalTak = totalAmt.subtract(totalTax);
        } else {
            TfnBxi tfnBxi = new TfnBxi();
            tfnBxi.setUnitId(tfn.getUnitId());
            tfnBxi.setTfnNum(tfn.getTfnNum());
            List<TfnBxiVo> tfnBxiVoList = tfnBxiService.getTfnBxiList(tfnBxi);
            for (TfnBxiVo _tfnBxi : tfnBxiVoList) {
                totalNum = totalNum.add(_tfnBxi.getUnitQty().multiply(new BigDecimal(_tfnBxi.getBox())));
                //TODO 更新金额，需关联零售价格表
            }
            totalTak = totalAmt.subtract(totalTax);
        }
        info.setTtlQty(totalNum);
        info.setTtlVal(totalAmt);
        info.setTtlTax(totalTax);
        info.setTtlMkv(totalTak);
        info.setTtlBox(totalBox);
        return tfnService1.update(info);
    }

    /**
     * 准备部分需要关联查询基础数据
     */
    public void prepareTfnInfo(Tfn tfn) {
        tfn.setFsclUnitId(tfn.getUnitId());
        tfn.setFsclDate(new Date());
        tfn.setDelivUnitId(tfn.getDelivWarehId());
        Wareh delivWar  = warehService.selectByWarehId(tfn.getDelivWarehId());
        if (delivWar != null) {
            tfn.setDelivFsclUnitId(delivWar.getFsclUnitId());
        }
        tfn.setRcvUnitId(tfn.getRcvWarehId());
        Wareh rcvWar  = warehService.selectByWarehId(tfn.getRcvWarehId());
        if (rcvWar != null) {
            tfn.setRcvFsclUnitId(rcvWar.getFsclUnitId());
        }
        tfn.setOpTime(new Date());
    }
}
