package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.components.SpringContextUtil;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PrcService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PucService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PuoService;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcService;
import com.boyu.erp.platform.usercenter.service.salesservice.SloService;
import com.boyu.erp.platform.usercenter.service.salesservice.SrcService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehPopupSerivce;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Classname WarehPopupServiceImpl
 * @Description TODO
 * @Date 2019/10/28 20:28
 * @Created by wz
 */
@Slf4j
@Service
@Transactional
public class WarehPopupServiceImpl implements WarehPopupSerivce {

    @Autowired
    SpringContextUtil springContextUtil;

    /**
     * 出入库详细
     *
     * @param vo
     * @return
     */
    @Override
    public Object getWarehInfo(WarehInfoVo vo) {
        if (("PUC").equals(vo.getSrcDocType())) {
            return springContextUtil.getBean(PucService.class).selectObject(new PscVo(vo.getSrcDocNum(), "", vo.getSrcDocUnitId() + ""));
        }
        if("PUO".equalsIgnoreCase(vo.getSrcDocType())){
            return springContextUtil.getBean(PuoService.class).selectObject(new PsoVo(vo.getSrcDocNum(), "", vo.getSrcDocUnitId() + ""));
        }
        if("PRC".equalsIgnoreCase(vo.getSrcDocType())){
            return springContextUtil.getBean(PrcService.class).selectObject(new PrcVo(vo.getSrcDocNum(), "", vo.getSrcDocUnitId() + ""));
        }
        if("SLC".equalsIgnoreCase(vo.getSrcDocType())){
            return springContextUtil.getBean(SlcService.class).selectObject(new PscVo("", vo.getSrcDocNum(), vo.getSrcDocUnitId() + ""));
        }
        if("SLO".equalsIgnoreCase(vo.getSrcDocType())){
            return springContextUtil.getBean(SloService.class).selectObject(new PsoVo("", vo.getSrcDocNum(), vo.getSrcDocUnitId() + ""));
        }
        if("SRC".equalsIgnoreCase(vo.getSrcDocType())){
            return springContextUtil.getBean(SrcService.class).selectObject(new PrcVo("", vo.getSrcDocNum(), vo.getSrcDocUnitId() + ""));
        }
        return null;
    }
}
