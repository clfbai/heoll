package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.entity.basic.Company;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.mq.order.*;
import com.boyu.erp.platform.usercenter.entity.purchase.Psc;
import com.boyu.erp.platform.usercenter.entity.purchase.Rtc;
import com.boyu.erp.platform.usercenter.entity.sales.Rgo;
import com.boyu.erp.platform.usercenter.entity.sales.RgoDtl;
import com.boyu.erp.platform.usercenter.entity.sales.RgoType;
import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.CompanyMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PscMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.RtcMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.RgoDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.RgoMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.RgoTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysDomainMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehDelivTaskMapper;
import com.boyu.erp.platform.usercenter.service.base.RabbitTemplateSerivce;
import com.boyu.erp.platform.usercenter.service.common.impl.ButtonCommonService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscService;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoDtlService;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoService;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcService;
import com.boyu.erp.platform.usercenter.service.salesservice.SrcService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.purchase.PurchaseUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;
import com.boyu.erp.platform.usercenter.vo.system.CaVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: RgoServiceImpl
 * @description: 调配单接口实现
 * @author: wz
 * @create: 2019-10-6 16:00
 */
@Slf4j
@Service
@Transactional
public class RgoServiceImpl implements RgoService {

    static final String YW = "ER_";

    //调配单模块按钮参数
    static final String parameter = "rgoButton";

    //确认
    static final String confirm = "/sales/rgo/confirm";
    //重做
    static final String redo = "/sales/rgo/redo";
    //审核
    static final String examine = "/sales/rgo/examine";
    //反审
    static final String reiterate = "/sales/rgo/reiterate";
    //挂起
    static final String hangUp = "/sales/rgo/hangUp";
    //恢复
    static final String recovery = "/sales/rgo/recovery";
    //作废
    static final String toVoid = "/sales/rgo/toVoid";

    @Autowired
    private UsersService usersService;
    @Autowired
    private RgoMapper rgoMapper;
    @Autowired
    private DelivUtil delivUtil;
    @Autowired
    private SysDomainMapper sysDomainMapper;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private RgoDtlMapper rgoDtlMapper;
    @Autowired
    private RgoDtlService rgoDtlService;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private SrcService srcService;
    @Autowired
    private SlcService slcService;
    @Autowired
    private RgoTypeMapper rgoTypeMapper;
    @Autowired
    private PurchaseUtils purchaseUtils;
    @Autowired
    private RtcMapper rtcMapper;
    @Autowired
    private PscMapper pscMapper;
    @Autowired
    private SrcServiceImpl srcServiceImpl;
    @Autowired
    private SlcServiceImpl slcServiceImpl;
    @Autowired
    private ButtonCommonService buttonCommonService;
    @Autowired
    private RabbitTemplateSerivce templateSerivce;
    @Autowired
    private SlcMapper slcMapper;
    @Autowired
    private WarehDelivTaskMapper warehDelivTaskMapper;

    /**
     * @param vo
     * @param page
     * @param size
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<RgoVo> selectAll(RgoVo vo, Integer page, Integer size, SysUser user) throws Exception {
        List<RgoVo> list = null;
        /**
         * 系统管理员
         * */
        if (false) {
            PageHelper.startPage(page, size);
            list = rgoMapper.selectALL(vo);
        } else {
            PageHelper.startPage(page, size);
            list = rgoMapper.selectByUnit(vo);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sys_prsnl", new String[]{"oprId", "chkrId"});
        map.put("sys_unit", new String[]{"fsclUnitId", "tranWarehId", "srcUnitId", "srcFsclUnitId", "delivWarehId", "destUnitId", "destFsclUnitId"
                , "rcvWarehId", "srcDocUnitId"});
        Map<String, Object> uMap = new HashMap<>();
        uMap.put("fsclUnitId", "fsclUnitCode-unitCode");
        uMap.put("srcFsclUnitId", "srcFsclUnitCode-unitCode");
        uMap.put("destFsclUnitId", "destFsclUnitCode-unitCode");
        map.put("unitProp", uMap);
        list = delivUtil.loadCodeName2VO2(map, list);
        Map<String, Object> codeMap = new LinkedHashMap<>();
        codeMap.put("srcUnitInvd", "BOOLEA");
        codeMap.put("prcAutoGen", "BOOLEA");
        codeMap.put("prcAutoChk", "BOOLEA");
        codeMap.put("destUnitInvd", "BOOLEA");
        codeMap.put("pucAutoGen", "BOOLEA");
        codeMap.put("pucAutoChk", "BOOLEA");
        codeMap.put("drDiffJgd", "DR_ROLE");
        codeMap.put("bxiEnabled", "BOOLEA");
        codeMap.put("effective", "BOOLEA");
        codeMap.put("progress", "R_PROG");
        codeMap.put("cancelled", "DR_ROLE");
        codeMap.put("suspended", "BOOLEA");
        list = delivUtil.loadCPByCodeDtl(codeMap, list);
        PageInfo<RgoVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 添加调配单
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertRgo(RgoVo vo, SysUser user) {

        vo = this.verification(vo);

        //生成调配单号
        SysUser sysU = new SysUser(vo.getUnitId(), user.getPrsnl().getPrsnlId());
        String rgoNum = sysRefNumService.viceNum(user, "RGO_NUM");
        vo.setRgoNum(rgoNum);

        vo.setOprId(user.getPrsnl().getPrsnlId());
        vo.setEffective("F");
        vo.setCancelled("F");
        vo.setSuspended("F");
        vo.setProgress("PG");
        if (vo.getList() != null && vo.getList().size() > 0) {
            vo = rgoDtlService.insertByRgo(vo);
        }

        return rgoMapper.insertSelective(vo);
    }


    /**
     * 修改调配单
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int updateRgo(RgoVo vo, SysUser user) {
        vo = this.verification(vo);
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 删除调配单
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int deleteRgo(RgoVo vo, SysUser user) {
        //删除调配单明细数据
        rgoDtlMapper.deleteByPrimaryKey(new RgoDtl(vo.getUnitId(), vo.getRgoNum()));
        return rgoMapper.deleteByPrimaryKey(vo);
    }

    /**
     * 查询是否固定单价
     *
     * @param vo
     * @return
     */
    @Override
    public Map<String, Object> getFixedPrice(RgoVo vo) {
        Map<String, Object> map = new HashMap<>();
        RgoType type = rgoTypeMapper.selectByRgoType(vo.getRgoType());
        if (type != null) {
            map.put("fixedUnitPrice", type.getFixedUnitPrice());//是否固定单价
            return map;
        }
        return null;
    }

    /**
     * 确认单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int confirm(RgoVo vo, SysUser user) {
        vo.setProgress("C");
        //补填会计组织信息
        Company com = companyMapper.selectByPrimaryKey(vo.getUnitId());
        if (com != null) {
            vo.setFsclUnitId(com.getFsclUnitId());
        }
        if (StringUtils.isEmpty(vo.getDelivWarehId())) {
            com = companyMapper.selectByPrimaryKey(vo.getSrcUnitId());
        } else {
            com = companyMapper.selectByPrimaryKey(vo.getDelivWarehId());
        }
        if (com != null) {
            vo.setSrcFsclUnitId(com.getFsclUnitId());
        }
        if (StringUtils.isEmpty(vo.getRcvWarehId())) {
            com = companyMapper.selectByPrimaryKey(vo.getDestUnitId());
        } else {
            com = companyMapper.selectByPrimaryKey(vo.getRcvWarehId());
        }
        if (com != null) {
            vo.setDestFsclUnitId(com.getFsclUnitId());
        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 重做单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int redo(RgoVo vo, SysUser user) {
        //清空会计组织
        vo.setProgress("PG");
        return rgoMapper.updateByRedo(vo);
    }

    /**
     * 审核单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int examine(RgoVo vo, SysUser user) throws Exception {
        //根据条件 修改状态
        if (vo.getSrcUnitInvd().equals("T") && vo.getDestUnitInvd().equals("T")) {
            vo.setProgress("TK");
        } else if (vo.getSrcUnitInvd().equals("T") && vo.getDestUnitInvd().equals("F")) {
            vo.setProgress("RK");
        } else if (vo.getSrcUnitInvd().equals("F") && vo.getDestUnitInvd().equals("T")) {
            vo.setProgress("DK");
        } else {
            vo.setProgress("CK");
            vo.setEffective("T");
        }
        //查询调配单类别
        RgoType rgoType = rgoTypeMapper.selectByRgoType(vo.getRgoType());
        //生成退销合同
        String rtcNum = srcService.creatSrcByRgo(vo, new SysUser(vo.getUnitId(), user.getPrsnl().getPrsnlId()), rgoType);
        //生成销售合同
        slcService.creatSlcByRgo(vo, new SysUser(vo.getUnitId(), user.getPrsnl().getPrsnlId()), rgoType);
        //重新获取调配单进度
        Rgo rgo = rgoMapper.selectByPrimaryKey(vo);
        if (rgo != null) {
            if (rgo.getProgress().equals("CK")) {
                this.launchTask(rgo, new SysUser(vo.getUnitId(), user.getPrsnl().getPrsnlId()), rtcNum);
            }
        }
        vo.setChkrId(user.getPrsnl().getPrsnlId());
        vo.setChkTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //推送消息
        this.sned(vo);

        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    //调配单推送消息
    private void sned(RgoVo vo) {
        RgoOrderAdd add = new RgoOrderAdd();
        add.setId(YW + vo.getRgoNum());

        add.setShopId(YW + vo.getRcvWarehId());
        add.setSourceShopId(YW + vo.getDelivWarehId());
        add.setRemarks(vo.getRemarks());
        List<RgoOrderItems> items = rgoDtlMapper.selectByOrderItems(vo.getUnitId() + "", vo.getRgoNum());
        add.setItems(items);
        templateSerivce.sendByOrder(new MessageObject("b2b.allocateorder.add", add));
    }


    /**
     * 重审单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int reiterate(RgoVo vo, SysUser user) throws Exception {
        vo.setProgress("C");
        //退销合同流程
        Rtc rtc = rtcMapper.selectBySrcDoc(new Rtc("RGO", vo.getUnitId(), vo.getRgoNum()));
        if (rtc != null) {
            if (rtc.getProgress().equals("CK") || rtc.getProgress().equals("RK")) {
                //调用退销合同取消审核
                srcService.reiterate(srcService.selectByOnly(new PrcVo(rtc.getRtcNum())), new SysUser(vo.getUnitId(), user.getPrsnl().getPrsnlId()));
                rtc = rtcMapper.selectByPrimaryKey(rtc.getRtcNum());
                if (rtc != null) {
                    if (rtc.getProgress().equals("C")) {
                        srcService.deleteSrc(srcService.selectByOnly(new PrcVo(rtc.getRtcNum())), new SysUser(vo.getUnitId(), user.getPrsnl().getPrsnlId()));
                    }
                }
            }
        }
        //销售合同流程
        Psc psc = pscMapper.selectBySrcDoc(new Psc("RGO", vo.getUnitId(), vo.getRgoNum()));
        if (psc != null) {
            if (psc.getProgress().equals("CK") || psc.getProgress().equals("RK")) {
                //调用销售合同取消审核
                slcService.reiterate(slcService.selectByOnly(new PscVo(psc.getPscNum())), new SysUser(vo.getUnitId(), user.getPrsnl().getPrsnlId()));
                psc = pscMapper.selectByPrimaryKey(psc.getPscNum());
                if (psc != null) {
                    if (psc.getProgress().equals("C")) {
                        slcService.deleteSlc(slcService.selectByOnly(new PscVo(psc.getPscNum())), new SysUser(vo.getUnitId(), user.getPrsnl().getPrsnlId()));
                    }
                }
            }
        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 挂起单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int hangUp(RgoVo vo, SysUser user) {
        vo.setSuspended("T");
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 恢复单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int recovery(RgoVo vo, SysUser user) {
        vo.setSuspended("F");
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 作废单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int toVoid(RgoVo vo, SysUser user) throws Exception {
        vo.setCancelled("T");
        //退销合同流程
        Rtc rtc = rtcMapper.selectBySrcDoc(new Rtc("RGO", vo.getUnitId(), vo.getRgoNum()));
        if (rtc != null) {
            srcServiceImpl.doAbolish(srcService.selectByOnly(new PrcVo(rtc.getRtcNum())), new SysUser(vo.getUnitId(), user.getPrsnl().getPrsnlId()));
        }
        //销售合同流程
        Psc psc = pscMapper.selectBySrcDoc(new Psc("RGO", vo.getUnitId(), vo.getRgoNum()));
        if (psc != null) {
            slcServiceImpl.doAbolish(slcService.selectByOnly(new PscVo(psc.getPscNum())), new SysUser(vo.getUnitId(), user.getPrsnl().getPrsnlId()));
        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 查询单条记录
     *
     * @param vo
     * @return
     */
    @Override
    public RgoVo selectByOnly(RgoVo vo) {
        return rgoMapper.selectByOnly(vo);
    }

    /**
     * 查询调配单可操作状态
     *
     * @param vo
     * @return
     */
    @Override
    public List<OperationVo> initButtons(RgoVo vo) {
        List<PurKeyValue> keyValues = buttonCommonService.initButton(parameter);
        RgoVo rgo = rgoMapper.selectByOnly(vo);
        if (rgo == null) {
            throw new ServiceException("403", "单据异常！");
        }
        return creatButton(keyValues, rgo);
    }

    /**
     * 中转发货
     *
     * @param vo
     * @return
     */
    @Override
    public int transferUnitDelivered(RgoVo vo) {
        if (vo.getProgress().equals("TR") && vo.getSuspended().equals("F")) {
            vo.setProgress("TD");
        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 中转收货
     * @param vo
     * @return
     */
    @Override
    public int transferUnitReceived(RgoVo vo, SysUser user) throws Exception {
        if (vo.getProgress().equals("DD") && vo.getSuspended().equals("F")) {
            vo.setProgress("RD");
            //查询销售合同
            PscVo psc = slcMapper.selectByRgo(vo.getUnitId(),vo.getDestUnitId(),vo.getRgoNum());
            purchaseUtils.outStock("SLC", psc.getPscNum(), user);
        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 取消中转发货
     *
     * @param vo
     * @return
     */
    @Override
    public int transferUnitReversedDelivered(RgoVo vo) {
        if (vo.getProgress().equals("TD") && vo.getSuspended().equals("F")) {
            vo.setProgress("TR");
        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 取消中转收货
     * @param vo
     * @return
     */
    @Override
    public int transferUnitReverseReceived(RgoVo vo, SysUser user) {
        if (vo.getProgress().equals("RD") && vo.getSuspended().equals("F")) {
            vo.setProgress("DD");
            //查询销售合同
            PscVo psc = slcMapper.selectByRgo(vo.getUnitId(),vo.getDestUnitId(),vo.getRgoNum());
            warehDelivTaskMapper.deleteByBill(new WarehDelivTask(null, "SLC", Long.valueOf(psc.getVenderId()), psc.getSlcNum()));
        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 收货方审核
     *
     * @param srcDocUnitId
     * @param srcDocNum
     * @return
     */
    @Override
    public int receivingUnitChecked(Long srcDocUnitId, String srcDocNum) {
        RgoVo vo = rgoMapper.selectByOnly(new RgoVo(srcDocUnitId, srcDocNum));
        if (vo.getProgress().equals("TK")) {
            vo.setProgress("RK");
        } else if (vo.getProgress().equals("DK")) {
            vo.setProgress("CK");
            vo.setEffective("T");
        }
        if (vo.getProgress().equals("CK")) {
            //启动出入库

        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 收货方取消审核
     * @param srcDocUnitId
     * @param srcDocNum
     * @return
     */
    @Override
    public int receivingUnitUnchecked(Long srcDocUnitId, String srcDocNum) {
        RgoVo vo = rgoMapper.selectByOnly(new RgoVo(srcDocUnitId, srcDocNum));
        if(vo.getProgress().equals("CK")){
            vo.setProgress("DK");
            vo.setEffective("F");
        }else if(vo.getProgress().equals("RK")){
            vo.setProgress("TK");
        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 调入方收货
     *
     * @param vo
     * @param stb
     * @return
     */
    @Override
    public int receivingUnitReceived(RgoVo vo, Stb stb) {
        if (vo.getProgress().equals("TD") && vo.getSuspended().equals("F")) {
            vo.setProgress("RD");
            //累计调入方数据
            BigDecimal rcv = new BigDecimal("0");
            if (vo.getTtlRcvQty().compareTo(rcv) == 0) {
                vo.setTtlRcvQty(stb.getTtlQty());
            } else {
                vo.setTtlRcvQty(vo.getTtlRcvQty().add(stb.getTtlQty()));
            }
            if (vo.getTtlRcvBox().equals(0)) {
                vo.setTtlRcvBox(stb.getTtlBox().longValue());
            } else {
                vo.setTtlRcvBox(vo.getTtlRcvBox() + stb.getTtlQty().longValue());
            }
            if (vo.getTtlRcvVal().compareTo(rcv) == 0) {
                vo.setTtlRcvVal(stb.getTtlVal());
            } else {
                vo.setTtlRcvVal(vo.getTtlRcvVal().add(stb.getTtlVal()));
            }
            if (vo.getTtlRcvTax().compareTo(rcv) == 0) {
                vo.setTtlRcvTax(stb.getTtlTax());
            } else {
                vo.setTtlRcvTax(vo.getTtlRcvTax().add(stb.getTtlTax()));
            }
            if (vo.getTtlRcvMkv().compareTo(rcv) == 0) {
                vo.setTtlRcvMkv(stb.getTtlMkv());
            } else {
                vo.setTtlRcvMkv(vo.getTtlRcvMkv().add(stb.getTtlMkv()));
            }
            //更新明细数据
            rgoDtlMapper.updateByStbToRcv(vo.getUnitId(), vo.getRgoNum(), stb.getUnitId(), stb.getStbNum(), true);
        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 调入方取消收货
     *
     * @param vo
     * @param stb
     * @return
     */
    @Override
    public int receivingUnitReverseReceived(RgoVo vo, Stb stb) {
        if (vo.getProgress().equals("RD") && vo.getSuspended().equals("F")) {
            vo.setProgress("TD");
            //扣减调入方数据
            BigDecimal rcv = new BigDecimal("0");
            vo.setTtlRcvQty(vo.getTtlRcvQty().subtract(stb.getTtlQty()));
            vo.setTtlRcvBox(vo.getTtlRcvBox() - stb.getTtlQty().longValue());
            vo.setTtlRcvVal(vo.getTtlRcvVal().subtract(stb.getTtlVal()));
            vo.setTtlRcvTax(vo.getTtlRcvTax().subtract(stb.getTtlTax()));
            vo.setTtlRcvMkv(vo.getTtlRcvMkv().subtract(stb.getTtlMkv()));
            //更新明细数据
            rgoDtlMapper.updateByStbToRcv(vo.getUnitId(), vo.getRgoNum(), stb.getUnitId(), stb.getStbNum(), false);
        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 调出方发货
     *
     * @param vo
     * @param stb
     * @return
     */
    @Override
    public int deliveryUnitDelivered(RgoVo vo, Stb stb) {
        if (vo.getProgress().equals("CK") && vo.getSuspended().equals("F")) {
            vo.setProgress("DD");
            //累计调出方数据
            BigDecimal deliv = new BigDecimal("0");
            if (vo.getTtlDelivQty().compareTo(deliv) == 0) {
                vo.setTtlDelivQty(stb.getTtlQty());
            } else {
                vo.setTtlDelivQty(vo.getTtlDelivQty().add(stb.getTtlQty()));
            }
            if (vo.getTtlDelivBox().equals(0)) {
                vo.setTtlDelivBox(stb.getTtlBox().longValue());
            } else {
                vo.setTtlDelivBox(vo.getTtlDelivBox() + stb.getTtlQty().longValue());
            }
            if (vo.getTtlDelivVal().compareTo(deliv) == 0) {
                vo.setTtlDelivVal(stb.getTtlVal());
            } else {
                vo.setTtlDelivVal(vo.getTtlDelivVal().add(stb.getTtlVal()));
            }
            if (vo.getTtlDelivTax().compareTo(deliv) == 0) {
                vo.setTtlDelivTax(stb.getTtlTax());
            } else {
                vo.setTtlDelivTax(vo.getTtlDelivTax().add(stb.getTtlTax()));
            }
            if (vo.getTtlDelivMkv().compareTo(deliv) == 0) {
                vo.setTtlDelivMkv(stb.getTtlMkv());
            } else {
                vo.setTtlDelivMkv(vo.getTtlDelivMkv().add(stb.getTtlMkv()));
            }
            //更新明细数据
            rgoDtlMapper.updateByStbToDeliv(vo.getUnitId(), vo.getRgoNum(), stb.getUnitId(), stb.getStbNum(), true);
        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 调出方取消发货
     *
     * @param vo
     * @param stb
     * @return
     */
    @Override
    public int deliveryUnitReverseDelivered(RgoVo vo, Stb stb) {
        if (vo.getProgress().equals("DD") && vo.getSuspended().equals("F")) {
            vo.setProgress("CK");
            //累计调出方数据
            vo.setTtlDelivQty(vo.getTtlDelivQty().subtract(stb.getTtlQty()));
            vo.setTtlDelivBox(vo.getTtlDelivBox() - stb.getTtlQty().longValue());
            vo.setTtlDelivVal(vo.getTtlDelivVal().subtract(stb.getTtlVal()));
            vo.setTtlDelivTax(vo.getTtlDelivTax().subtract(stb.getTtlTax()));
            vo.setTtlDelivMkv(vo.getTtlDelivMkv().subtract(stb.getTtlMkv()));
            //更新明细数据
            rgoDtlMapper.updateByStbToDeliv(vo.getUnitId(), vo.getRgoNum(), stb.getUnitId(), stb.getStbNum(), false);
        }
        return rgoMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 发货方审核
     * @param vo
     * @return
     */
    @Override
    public int deliveryUnitChecked(RgoVo vo) {
        if(vo.getProgress().equals("TK")){
            vo.setProgress("DK");
        }else if(vo.getProgress().equals("RK")){
            vo.setProgress("CK");
            vo.setEffective("T");
        }
        return 0;
    }

    private List<OperationVo> creatButton(List<PurKeyValue> keyValues, RgoVo vo) {
        List<OperationVo> list = new ArrayList<>();
        for (PurKeyValue pur : keyValues) {
            if (("confirm").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("PG") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("确认", "T", confirm));
                } else {
                    list.add(new OperationVo("确认", "F", confirm));
                }
            }
            if (("redo").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("C") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("重做", "T", redo));
                } else {
                    list.add(new OperationVo("重做", "F", redo));
                }
            }
            if (("check").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("C") && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("审核", "T", examine));
                } else {
                    list.add(new OperationVo("审核", "F", examine));
                }
            }
            if (("uncheck").equals(pur.getOptionValue())) {
                if ((vo.getProgress().equals("TK") || ((vo.getSrcUnitInvd().equals("F") || vo.getPrcAutoChk().equals("T")) && vo.getProgress().equals("DK"))
                        || ((vo.getDestUnitInvd().equals("F") || vo.getPucAutoChk().equals("T")) && vo.getProgress().equals("RK")) || (vo.getSrcUnitInvd().equals("F") &&
                        vo.getDestUnitInvd().equals("F") && vo.getProgress().equals("CK")) && vo.getSuspended().equals("F") && vo.getCancelled().equals("F"))) {
                    list.add(new OperationVo("反审", "T", reiterate));
                } else {
                    list.add(new OperationVo("反审", "F", reiterate));
                }
            }
            if (("suspend").equals(pur.getOptionValue())) {
                if (!vo.getProgress().equals("CL") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("挂起", "T", hangUp));
                } else {
                    list.add(new OperationVo("挂起", "F", hangUp));
                }
            }
            if (("resume").equals(pur.getOptionValue())) {
                if (vo.getSuspended().equals("T")) {
                    list.add(new OperationVo("恢复", "T", recovery));
                } else {
                    list.add(new OperationVo("恢复", "F", recovery));
                }
            }
            if (("abolish").equals(pur.getOptionValue())) {
                if ((vo.getProgress().equals("PG") || vo.getProgress().equals("C") || vo.getProgress().equals("RK") || vo.getProgress().equals("TK")
                        || vo.getProgress().equals("DK")) && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("作废", "T", toVoid));
                } else {
                    list.add(new OperationVo("作废", "F", toVoid));
                }
            }
        }
        return list;
    }

    /**
     * 判断并填充数据
     *
     * @param vo
     * @return
     */
    private RgoVo verification(RgoVo vo) {
        //判断调出方是否介入
        SysDomain sysDo = sysDomainMapper.selectByPrimaryKey(Long.valueOf(vo.getSrcUnitId()));
        if (sysDo != null) {
            vo.setSrcUnitInvd("T");
        } else {
            vo.setSrcUnitInvd("F");
        }
        //判断调入放是否介入
        sysDo = sysDomainMapper.selectByPrimaryKey(Long.valueOf(vo.getDestUnitId()));
        if (sysDo != null) {
            vo.setDestUnitInvd("T");
        } else {
            vo.setDestUnitInvd("F");
        }
        return vo;
    }

    /**
     * 调配单启动任务
     *
     * @param rgo
     */
    private void launchTask(Rgo rgo, SysUser user, String rtcNum) throws Exception {
        //登记出入库任务
        if (rgo.getSrcUnitInvd().equals("T")) {
            //登记出库任务
            purchaseUtils.outStock("PRC", rtcNum, user);
        } else {
            //入库任务
            purchaseUtils.generate("SRC", rtcNum, user);
        }
    }
}
