package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.GdnMapper;
import com.boyu.erp.platform.usercenter.model.wareh.GdnModel;
import com.boyu.erp.platform.usercenter.service.warehouse.GdnExternalCiteService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbService;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@Slf4j
public class GdnExternalCiteServiceImpl implements GdnExternalCiteService {
    @Autowired
    private DelivUtil delivUtil;
    @Autowired
    private StbService stbService;
    @Autowired
    private GdnMapper gdnMapper;
    @Autowired
    private StbDtlService stbDtlService;
    /**
     * 添加出库单和明细
     *
     * @param
     * @return
     * @author HHe
     * @date 2019/11/6 10:10
     */
    @Override
    public String addGdnAndDtl(GdnModel gdnModel, SysUser sysUser) {
        if (gdnModel.getGdnNum() == null || "".equals(gdnModel.getGdnNum()) || gdnModel.getStbNum() == null || "".equals(gdnModel.getStbNum())) {
            //1、生成库存单编号
            SysRefNumDtl refNumDtl = new SysRefNumDtl();
            refNumDtl.setRefNumId("STB_NUM");
            refNumDtl.setUnitId(gdnModel.getUnitId());
            String docNum = delivUtil.createDocNum(refNumDtl);
            //库存单编号
            gdnModel.setStbNum(docNum);
            //出库单编号
            gdnModel.setGdnNum(docNum);
        }
        Date nowDate = new Date();
        //2、封装库存单默认字段
        //单据时间
        gdnModel.setDocDate(nowDate);
        //库存单类型
        gdnModel.setDrType("D");
        //操作人
        gdnModel.setOprId(sysUser.getPrsnl().getPrsnlId());
        //操作时间
        gdnModel.setOpTime(nowDate);
        //是否生效
        gdnModel.setEffective("F");
        //挂起
        gdnModel.setSuspended("F");
        //撤销
        gdnModel.setCancelled("F");
        //已冲单，默认F
        gdnModel.setReversed("F");
        //是否冲单
        gdnModel.setIsRev("F");
        //todo 过账控制（未知功能）
        gdnModel.setPostCtrl("U");

        //3、封装出库单默认字段
        //拣货开始时间
        gdnModel.setFtchStTime(nowDate);
        //拣货结束时间
        gdnModel.setFtchFinTime(nowDate);
        //托运时间
        gdnModel.setCnsnTime(nowDate);
        //进度
        gdnModel.setProgress("PG");
        //是否显示（如果是发送到WMS的单据会赋值不显示，其他情况一概显示）
        if (gdnModel.getShowDoc() == null || "".equals(gdnModel.getShowDoc())) {
            gdnModel.setShowDoc("T");
        }
        try {
            //3、生成库存单
            stbService.insertStb(gdnModel, sysUser);
            //5、生成出库单
            gdnMapper.insertGdnModel2Gdn(gdnModel);
            if (gdnModel.getStbDtlList() != null && gdnModel.getStbDtlList().size() > 0) {
                //添加明细
                stbDtlService.insertStbDtlListAndUnitId(gdnModel.getStbDtlList(), sysUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("201", "添加出库单异常");
        }
        return gdnModel.getStbNum();
    }
    /**
     * 根据原始单据组织id、类别、编号和收货方id查询仓库id
     *
     * @param srcDocType   原始单据类别
     * @param srcDocNum    原始单据编号
     * @param srcDocUnitId 原始单据组织id
     * @param rcvUnitId    收货方Id
     * @return warehId 仓库Id
     * @author HHe
     * @date 2019/9/16 10:01
     */
    @Override
    @Transactional(readOnly = true)
    public Long queryDelivWarehIdBySrcDocMess(String srcDocType, String srcDocNum, Long srcDocUnitId, Long rcvUnitId) {
        return gdnMapper.queryDelivWarehIdBySrcDocMess(srcDocType, srcDocNum, srcDocUnitId, rcvUnitId);
    }

    /**
     * 根据原始单据组织id、类别、编号和收货方id查询仓库id
     *
     * @param srcDocType   原始单据类别
     * @param srcDocNum    原始单据编号
     * @param srcDocUnitId 原始单据组织id
     * @param delivUnitId  发货方Id
     * @return warehId 仓库Id
     * @author wa
     * @date 2019/9/16 10:01
     */
    @Override
    @Transactional(readOnly = true)
    public Long queryRcvWarehIdBySrcDocMess(String srcDocType, String srcDocNum, Long srcDocUnitId, Long delivUnitId) {
        return gdnMapper.queryRcvWarehIdBySrcDocMess(srcDocType, srcDocNum, srcDocUnitId, delivUnitId);
    }
}
