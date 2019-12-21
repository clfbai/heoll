package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitClsf;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitClsfMapper;
import com.boyu.erp.platform.usercenter.model.wareh.GrnDtlModel;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehService;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehSrcInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class WarehRcvTaskUtils {

    //仓库上传参数
    private String WMS_WarehId = "USE_WMS_WAREH_IDS";

    @Autowired
    private SysParameterMapper sysParameterMapper;
    @Autowired
    private StbUtil stbUtil;
    @Autowired
    private SysUnitClsfMapper unitClsfMapper;
    @Autowired
    private WarehService warehService;
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 根据参数检查仓库是否上传到WMS
     */
    public boolean upWMS(Long warehId) {
        SysParameter parameter = sysParameterMapper.findById(WMS_WarehId);
        if (parameter == null) {
            throw new ServiceException("403", "上传C-WMS 参数Id: WMS_WarehId 不存在");
        }
        if (StringUtils.isNotBlank(parameter.getParmVal()) && parameter.getParmVal().indexOf(",") < 0) {
            throw new ServiceException("403", "上传C-WMS参数Id: WMS_WarehId对应的值设置有误，请以 仓库Id+','分隔");
        }
        if (parameter.getParmVal() != null) {
            String[] warehs = parameter.getParmVal().split(",");
            for (String s : warehs) {
                if (s.equalsIgnoreCase(warehId + "")) {
                    //找到需要上传的仓库上传
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据原始单据号、原始单据类型、原始单据组织
     * 生成添加入库单、库存单、库存单明细对象
     */
    public GrnDtlModel addModel(WarehRcvTask warehRcvTask, SysUser user, String type) throws ServiceException {
        Wareh wareh = warehService.selectByWarehId(warehRcvTask.getWarehId());
        log.info("组织的入库任务 taskDocNum " + warehRcvTask.getUnitId() + " " + warehRcvTask.getTaskDocNum());
        WarehSrcInfoVo purchase = purchaseService.selectWarehSrc(warehRcvTask.getTaskDocType(), warehRcvTask.getUnitId() + "", warehRcvTask.getTaskDocNum());
        isOriginal(purchase);
        List<CommonDtl> list = purchaseService.selectBillInfo(warehRcvTask.getTaskDocType(), purchase.getCntrNum());
        log.info("原始单据信息Tow ：" + purchase);
        //入库单赋值
        Grn grn = createGrn(warehRcvTask, wareh, purchase, user, type);
        //库存单赋值
        Stb stb = createStb(warehRcvTask, wareh, purchase, user, type);
        //入库单明细赋值
        List<StbDtl> stbDtls = createStbDtl(list, type);
        warehRcvTask.setIsStb(CommonFainl.TRUE);
        stbUtil.setStbNum(stb, grn, stbDtls, user);
        GrnDtlModel dtlModel = new GrnDtlModel(warehRcvTask, grn, stb, stbDtls, new ArrayList<>());
        warehRcvTask.setGrnNum(dtlModel.getGrn().getGrnNum());
        return dtlModel;
    }


    /**
     * 功能描述: 生成入库单对象
     *
     * @param warehRcvTask
     * @param wareh
     * @param purchase     原单明细
     * @param user         操作人
     * @param type         是否自动执行
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/8 10:49
     */
    public Grn createGrn(WarehRcvTask warehRcvTask, Wareh wareh, WarehSrcInfoVo purchase, SysUser user, String type) {
        Grn model = new Grn();
        BeanUtils.copyProperties(warehRcvTask, model);
        BeanUtils.copyProperties(purchase, model);
        //发货方会计组织Id等当前组织Id 或发货方Id等于当前组织Id
        if (warehRcvTask.getUnitId().equals(model.getDelivFsclUnitId())
                || warehRcvTask.getUnitId().equals(warehRcvTask.getDelivUnitId())) {
            //会计入库方式为调拨
            model.setFsclRcvMode("TRA");
        } else {
            model.setFsclRcvMode(warehRcvTask.getRcvMode());
        }
        if (CommonFainl.TRUE.equalsIgnoreCase(type)) {
            //收货开始时间
            model.setTkovStTime(new Date());
            //收货结束时间
            model.setTkovFinTime(new Date());
            //s收货人id
            model.setRcvrId(user.getUserId());
            //收货时间
            model.setRcvTime(new Date());
        }
        //Grn 表 启用验收
        model.setAcptReqd(wareh.getAcptUidReqd());
        //默认不起用分储
        model.setPaReqd(CommonFainl.FALSE);
        //收货开始时间为当前生成订单时间
        model.setTkovStTime(new Date());
        //此方法默认全部不显示
        model.setShowDoc(CommonFainl.FALSE);
        return model;
    }

    /**
     * 功能描述: 根据入库原始单据明细生成库存单
     *
     * @param warehRcvTask
     * @param wareh
     * @param purchase
     * @param type         (自动执行和非自动执行) 自动执行 数量与预期相同非自动执行只有预期实际为0
     *                     手动点击生如同自动执行
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/8 15:27
     */
    public Stb createStb(WarehRcvTask warehRcvTask, Wareh wareh, WarehSrcInfoVo purchase, SysUser user, String type) {
        Stb stb = new Stb();
        BeanUtils.copyProperties(purchase, stb);
        //原始单据类别
        stb.setSrcDocType(warehRcvTask.getTaskDocType());
        //原始单据合同号
        stb.setCntrNum(purchase.getCntrNum());
        //原始单据
        stb.setSrcDocNum(warehRcvTask.getTaskDocNum());
        String rvcMode = warehRcvTask.getRcvMode();
        stb.setUnitId(warehRcvTask.getUnitId());
        stb.setWarehId(wareh.getWarehId());
        //原始单据编号组织Id
        stb.setSrcDocUnitId(warehRcvTask.getTaskDocUnitId());
        // 单据日期
        stb.setDocDate(new Date());
        //Stb 表 启用装箱
        stb.setBoxReqd(wareh.getBoxUidReqd());
        //配码 不起用
        stb.setBxiEnabled(CommonFainl.FALSE);
        //货位管理
        stb.setLocAdopted(wareh.getLocAdopted());
        //默认不起用预定义装箱
        stb.setBoxSchd(CommonFainl.FALSE);
        //及时结算
        stb.setInstStl(CommonFainl.FALSE);
        //PURC采购入库需要修改成本  RTRT零售退货 SLRT销售退货
        if ("PURC".equals(rvcMode) || "RTRT".equals(rvcMode) || "SLRT".equals(rvcMode)) {
            stb.setCostChg(CommonFainl.TRUE);
        } else {
            stb.setCostChg(CommonFainl.FALSE);
        }
        //查找会计组织
        SysUnitClsf unitClsf = new SysUnitClsf();
        unitClsf.setUnitType("SW");
        unitClsf.setOwnerId(warehRcvTask.getUnitId());
        unitClsf.setUnitId(stb.getWarehId());
        Long s = unitClsfMapper.selectByPrimaryKey(unitClsf) == null ? 0L : warehRcvTask.getUnitId();
        //会计组织Id 0 不存在
        stb.setFsclUnitId(s);
        //约定会计日期 默认F
        stb.setFsclDateAptd(CommonFainl.FALSE);
        //入库
        stb.setDrType("R");
        //过账控制
        stb.setPostCtrl("U");
        //操作员
        stb.setOprId(user.getUserId());
        //操时间
        stb.setOpTime(new Date());
        //已生效("t","f")
        stb.setEffective(CommonFainl.TRUE);
        //挂起("t","f")
        stb.setSuspended(CommonFainl.FALSE);
        // 撤销("t","f")
        stb.setCancelled(CommonFainl.FALSE);
        //已冲单("t","f")
        stb.setReversed(CommonFainl.FALSE);
        //是否冲单("t","f")
        stb.setIsRev(CommonFainl.FALSE);
        //预期总数量
        stb.setTtlExpdQty(purchase.getTtlQty());
        if (CommonFainl.TRUE.equalsIgnoreCase(type)) {
            stb.setTtlQty(purchase.getTtlQty());
        }
        if (CommonFainl.FALSE.equalsIgnoreCase(type)) {
            stb.setTtlQty(new BigDecimal("0"));
        }
        return stb;
    }

    /**
     * 功能描述: 根据入库原始单据明细生成库存单明细
     *
     * @param list
     * @param type (是否自动执行)
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/8 15:27
     */
    public List<StbDtl> createStbDtl(List<CommonDtl> list, String type) throws ServiceException {
        List<StbDtl> model = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (CommonDtl c : list) {
                model.add(createStbdtl(c, type));
            }
        } else {
            throw new ServiceException("403", "原始单据明细为空");
        }
        return model;
    }

    private StbDtl createStbdtl(CommonDtl c, String type) {
        StbDtl dtl = new StbDtl();
        if (c.getUnitPrice() == null) {
            throw new ServiceException("403", "原始单据商品价格为空");
        }
        if (c.getQty() == null) {
            throw new ServiceException("403", "原始单据商品数量为空");
        }
        BeanUtils.copyProperties(c, dtl);
        dtl.setExpdQty(c.getQty());
        dtl.setProdId(c.getProdId());
        dtl.setQty(new BigDecimal(0));
        dtl.setVal(new BigDecimal(0));
        //单位成本
        dtl.setUnitCost(dtl.getUnitPrice());
        //成本
        dtl.setCost(dtl.getUnitPrice());
        //自动执行或手动生成
        if (CommonFainl.TRUE.equalsIgnoreCase(type)) {
            dtl.setQty(c.getQty());
            //总金额
            dtl.setVal(c.getQty().multiply(c.getUnitPrice()));
        }
        return dtl;
    }


    public void isOriginal(WarehSrcInfoVo vo) {
        if (vo == null) {
            throw new ServiceException("403", "原始单据为空");
        }
        if (vo.getTtlQty() == null || vo.getTtlQty().compareTo(new BigDecimal(0)) == 0) {
            throw new ServiceException("403", "原始单据总数量为空或0，无效单据");
        }
        if (vo.getTtlVal() == null || vo.getTtlVal().compareTo(new BigDecimal(0)) == 0) {
            throw new ServiceException("403", "原始单据总金额为空或0，无效单据");
        }
        if (vo.getTtlTax() == null || vo.getTtlTax().compareTo(new BigDecimal(0)) == 0) {
            throw new ServiceException("403", "原始单据总税款为空或0，无效单据");
        }
    }


}
