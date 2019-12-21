package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.entity.basic.TranDiffEvt;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.mq.order.OrderItems;
import com.boyu.erp.platform.usercenter.entity.mq.order.OrderItemsToB;
import com.boyu.erp.platform.usercenter.entity.mq.order.SrcOrderAdd;
import com.boyu.erp.platform.usercenter.entity.mq.order.SrcOrderItems;
import com.boyu.erp.platform.usercenter.entity.purchase.Prc;
import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.entity.purchase.Rtc;
import com.boyu.erp.platform.usercenter.entity.purchase.RtcDtl;
import com.boyu.erp.platform.usercenter.entity.sales.*;
import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.CaMapper;
import com.boyu.erp.platform.usercenter.mapper.basic.CompanyMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PrcMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsaMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.RtcDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.RtcMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SrcMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SrcTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysDomainMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehDelivTaskMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehRcvTaskMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehStkPgMapper;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.base.SendSerivce;
import com.boyu.erp.platform.usercenter.service.caservice.CaAccService;
import com.boyu.erp.platform.usercenter.service.caservice.TranDiffEvtService;
import com.boyu.erp.platform.usercenter.service.common.impl.ButtonCommonService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PrcService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.RtcDtlService;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoService;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcService;
import com.boyu.erp.platform.usercenter.service.salesservice.SrcService;
import com.boyu.erp.platform.usercenter.service.system.RtqQtaService;
import com.boyu.erp.platform.usercenter.service.system.RtvQtaService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.warehouse.*;
import com.boyu.erp.platform.usercenter.utils.DateUtil;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.PurchaseUtils;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.*;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbDtlVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Classname SrcServiceImpl
 * @Description TODO
 * @Date 2019/8/13 16:05
 * @Created by wz
 */
@Slf4j
@Service
@Transactional
public class SrcServiceImpl implements SrcService {
    static final String YW = "ER_";

    //退购合同模块按钮参数
    static final String parameter = "srcButton";

    //确认
    static final String confirm = "/sales/src/confirm";
    //重做
    static final String redo = "/sales/src/redo";
    //审核
    static final String examine = "/sales/src/examine";
    //反审
    static final String reiterate = "/sales/src/reiterate";
    //挂起
    static final String hangUp = "/sales/src/hangUp";
    //恢复
    static final String recovery = "/sales/src/recovery";
    //作废
    static final String toVoid = "/sales/src/toVoid";
    //关闭
    static final String close = "/sales/src/close";
    //重用
    static final String reusing = "/sales/src/reusing";

    @Autowired
    private UsersService usersService;
    @Autowired
    private SrcMapper srcMapper;
    @Autowired
    private SrcTypeMapper srcTypeMapper;
    @Autowired
    private SysParameterMapper sysParameterMapper;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private SysDomainMapper sysDomainMapper;
    @Autowired
    private RtcDtlService rtcDtlService;
    @Autowired
    private RtcMapper rtcMapper;
    @Autowired
    private PrcService prcService;
    @Autowired
    private PurchaseUtils purchaseUtils;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private PrcMapper prcMapper;
    @Autowired
    private WarehRcvTaskMapper warehRcvTaskMapper;
    @Autowired
    private WarehDelivTaskMapper warehDelivTaskMapper;
    @Autowired
    private RtcDtlMapper rtcDtlMapper;
    @Autowired
    private WarehStkPgMapper warehStkPgMapper;
    @Autowired
    private RtqQtaService rtqQtaService;
    @Autowired
    private RtvQtaService rtvQtaService;
    @Autowired
    private PsaMapper psaMapper;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private CaMapper caMapper;
    @Autowired
    private CaAccService caAccService;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private ButtonCommonService buttonCommonService;
    @Autowired
    private RgoService rgoService;
    @Autowired
    private StbService stbService;
    @Autowired
    private StbDtlService stbDtlService;
    @Autowired
    private SlcService slcService;
    @Autowired
    private GdnExternalCiteService gdnExternalCiteService;
    @Autowired
    private TranDiffEvtService tranDiffEvtService;
    @Autowired
    private WarehRcvTaskSerivce warehRcvTaskSerivce;
    @Autowired
    private SendSerivce sendSerivce;

    /**
     * 分页查询
     *
     * @param vo
     * @param page
     * @param size
     * @param user
     * @return
     */
    @Override
    public PageInfo<PrcVo> selectAll(PrcVo vo, Integer page, Integer size, SysUser user) {
        List<PrcVo> list = null;
        /**
         * 系统管理员
         * */
        if (false) {
            PageHelper.startPage(page, size);
            list = srcMapper.selectALL(vo);
        } else {
            PageHelper.startPage(page, size);
            list = srcMapper.selectByUnit(vo);
        }
        PageInfo<PrcVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 退购合同中确认生成退销合同
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertByPrc(PrcVo vo, SysUser user) {
        Src src = new Src();
        //通过rtcType查找srcType
        SrcType srcType = srcTypeMapper.selectByRtcType(vo.getRtcType());
        if (srcType != null) {
            src.setSrcType(srcType.getSrcType());
        }
        src.setUnitId(vo.getVenderId());
        src.setSrBrdgMode(srcType.getSrBrdgMode());
        src.setSuspended("F");
        src.setAfReqd("F");
        src.setAfBnd("F");
        src.setPrFwdd("F");
        src.setRtcNum(vo.getRtcNum());
        //生成销售编号
        //通过系统参数PS_DOCUMENT_UNIQUED_NUMBERED去生成购销合同编号或者采购合同编号
        SysParameter sys = sysParameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        if (sys.getParmVal().equals("T")) {
            src.setSrcNum(vo.getRtcNum());
        } else {
            String srcNum = sysRefNumService.viceNum(user, "SRC_NUM");
            src.setSrcNum(srcNum);
        }
        return srcMapper.insertSelective(src);
    }

    /**
     * 退购合同中删除退销合同
     *
     * @param vo
     * @return
     */
    @Override
    public int deleteByPrc(PrcVo vo) {
        return srcMapper.deleteByPrc(vo.getVenderId() + "", vo.getRtcNum());
    }

    /**
     * 新增退销合同
     *
     * @param v
     * @param user
     * @return
     */
    @Override
    public String insertSrc(PrcVo v, SysUser user) {
        //通过系统参数PS_DOCUMENT_UNIQUED_NUMBERED去生成购销合同编号或者采购合同编号
        SysParameter sys = sysParameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        SysUser sysU = new SysUser(Long.valueOf(v.getVenderId()), user.getPrsnl().getPrsnlId());
        if (sys.getParmVal().equals("T")) {
            String rtcNum = sysRefNumService.mainNum(sysU, "RTC_NUM");
            v.setPrcNum(rtcNum);
            v.setRtcNum(rtcNum);
        } else {
            String srcNum = sysRefNumService.viceNum(sysU, "SRC_NUM");
            String rtcNum = sysRefNumService.mainNum(sysU, "RTC_NUM");
            v.setSrcNum(srcNum);
            v.setRtcNum(rtcNum);
        }

        v = this.updateSrcVo(v, user);

        v.setEffective("F");
        v.setCancelled("F");
        v.setSuspended("F");
        v.setProgress("PG");

        //默认退销合同已生成
        v.setVdrInvd("T");
        v.setSrcGen("T");
        v.setPrFwdd("F");

        v.setOprId(user.getPrsnl().getPrsnlId());

        if (v.getRtcDtlList() != null && v.getRtcDtlList().size() > 0) {
            v = rtcDtlService.insertByPrc(v);
        }

        srcMapper.insertByRtcVo(v);

        rtcMapper.insertByRtcVo(v);

        return v.getRtcNum();
    }

    //修改退销合同
    @Override
    public int updateSrc(PrcVo v, SysUser user) {
        v.setOprId(user.getPrsnl().getPrsnlId());
        v = this.updateSrcVo(v, user);
        srcMapper.updateByRtcVo(v);
        return rtcMapper.updateByPrcVo(v);

    }

    /**
     * 删除退销合同
     *
     * @param v
     * @param user
     * @return
     */
    @Override
    public int deleteSrc(PrcVo v, SysUser user) throws Exception {
        //清空数据
        v.setEndRtcNum("");
        //删除src表数据
        srcMapper.deleteRtcVo(v.getVenderId() + "", v.getSrcNum());

        v.setSrcGen("F");

        if (StringUtils.isNotEmpty(v.getEndRtcNum())) {
            // 如果ST_RTC_NUM非空，调用ST_RTC_NUM对应退销合同的SalesReturnContractHome.detach方法，取消关联。
            v.setStRtcNum("");
            prcService.relation(v.getEndRtcNum(), new SysUser(Long.valueOf(v.getVenderId()), user.getPrsnl().getPrsnlId()));
        }

        if (v.getPrcAutoGen().equals("T") && v.getPrcGen().equals("T")) {
            //调用销售合同接口 删除slc表数据
            return prcService.deletePrc(v, new SysUser(Long.valueOf(v.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }

        //判断是否删除rtc表  否则就更新它 设置PRC_GEN = FALSE。
        if (v.getSrcGen().equals("F")) {
            return rtcMapper.deleteByPrimaryKey(v.getRtcNum());
        }
        return 1;
    }

    /**
     * 退购合同中取消关联
     *
     * @param rtcNum
     * @param user
     * @return
     */
    @Override
    public void relation(String rtcNum, SysUser user) throws Exception {
        Rtc rtc = rtcMapper.selectByPrimaryKey(rtcNum);
        if (rtc != null) {
            if (rtc.getVdeInvd().equals("F") && rtc.getProgress().equals("CK")) {
                //登记入库任务
                purchaseUtils.generate("SRC", rtcNum, user);
            }
        }
    }

    /**
     * 确认单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int confirm(PrcVo vo, SysUser user) {
        //将状态改为已确认
        vo.setProgress("C");

        vo.setOprId(user.getPrsnl().getPrsnlId());
        //查询供应商，采购商的会计组织id 存入购销合同
        vo.setVdrFsclUnitId(
                companyMapper.selectByPrimaryKey(Long.valueOf(vo.getVenderId())).getFsclUnitId());
        vo.setVdeFsclUnitId(
                companyMapper.selectByPrimaryKey(Long.valueOf(vo.getVendeeId())).getFsclUnitId());
        if (vo.getPrcGen().equals("F") && vo.getPrcAutoGen().equals("T")) {
            vo.setPrcGen("T");
            prcService.insertByPrc(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }
        return rtcMapper.updateByPrcVo(vo);
    }

    /**
     * 重做单据
     *
     * @param vo
     * @return
     */
    @Override
    public int redo(PrcVo vo) {
        vo.setProgress("PG");
        vo.setVdeFsclUnitId(null);
        vo.setVdrFsclUnitId(null);
        if (vo.getPrcGen().equals("T") && vo.getPrcAutoGen().equals("T")) {
            vo.setPrcGen("F");
            prcMapper.deleteByRtcNum(vo.getRtcNum());
        }
        return rtcMapper.updateBystate(vo);
    }

    /**
     * 审核单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int examine(PrcVo vo, SysUser user, int orderType) throws Exception {
        //将状态改为已审核状态  并修改采购合同与销售合同审核状态
        if (vo.getProgress().equals("C") && vo.getPrcGen().equals("T")) {
            vo.setProgress("RK");
            vo.setEffective("F");
        } else {
            vo.setProgress("CK");
            vo.setEffective("T");
        }

        if (prcService.contract(vo)) {
            //合同明细
            String sql = " ,qty ,val";
            List<RtcDtlVo> dtlList = rtcDtlMapper.selectByBill(vo.getRtcNum(), sql);

            this.publicMethodT(vo, dtlList, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        }

        //监管往来
        if (purchaseService.judgeRtc(vo.getRtcNum()) && vo.getAccEnabled().equals("T") && vo.getRtnAccSite().equals("CK")
                && !vo.getVdrFsclUnitId().equals(vo.getVendeeId()) && vo.getAccVal().compareTo(new BigDecimal("0")) != 0) {
            Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
            if (ca != null) {
                //授信
                caAccService.accredit(new CaAcc(0L, ca.getCaId(), "SRC", vo.getVenderId(), vo.getSrcNum()), new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()), ca, new BigDecimal("0"), vo.getAccVal(), true);
            }
        }

        //将退购合同以及退销合同的审核状态信息补充
        srcMapper.updateByChkr(user.getPrsnl().getPrsnlId() + "", vo.getRtcNum());

        if (prcService.contract(vo) && vo.getVdeInvd().equals("F")
                && vo.getProgress().equals("CK") && (StringUtils.isNotEmpty(vo.getSrcDocType()) ? !vo.getSrcDocType().equals("RGO") : true)
                && StringUtils.NullEmpty(vo.getEndRtcNum())) {
            //入库任务
            purchaseUtils.generate("SRC", vo.getRtcNum(), new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        }

        if (vo.getPrcGen().equals("T")) {
            if (StringUtils.isNotEmpty(vo.getSrcNum())) {
                vo.setDocNum(prcMapper.selectByOnly(vo).getPrcNum());
                //退销合同通知退购合同审核事件
                int type = prcService.noticeExamine(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
                //推送
                if(orderType==1){
                    this.sned(vo);
                }
                return type;
            } else {
                rtcMapper.updateBystate(vo);
                //出库任务
                purchaseUtils.outStock("PRC", vo.getRtcNum(), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
            }
        }
        //推送
        if(orderType==1){
            this.sned(vo);
        }
        return rtcMapper.updateBystate(vo);
    }

    //退销合同审核时推送消息+
    private void sned(PrcVo vo) {
        //查询出商品明细
        List<SrcOrderItems> items = rtcDtlMapper.selectByOrderItems(vo.getRtcNum());
        if(!items.isEmpty()){
            //查询参数获得订单类型
            SysParameter sysPa = parameterMapper.findById("B2B_SRC_TYPE");
            int type = 0;
            if (sysPa != null) {
                String val[] = sysPa.getParmVal().split(";");
                if(val!=null){
                    for(String s:val){
                        int a = s.indexOf(vo.getSrcType());
                        if(a!=-1){
                            type = Integer.valueOf(s.substring(s.length()-1));
                        }
                    }
                }
            }
            if(type>0){
                SrcOrderAdd add = new SrcOrderAdd();
                add.setId(YW + vo.getSrcNum());
                if(StringUtils.isNotEmpty(vo.getVdeWarehId()+"")){
                    add.setShopId(YW + vo.getVdeWarehId());
                }
                add.setAmount(vo.getTtlDelivVal());
                add.setType(type);
                add.setSupplierCode(vo.getVenderNum());
                add.setCreateTime(DateUtil.dateToString(new Date()));
                add.setItems(items);
                MessageObject msg = new MessageObject("bb.aftersales.add", vo.getRtcNum(), add);
                sendSerivce.send("exh.order.bb.up", "order.bb.up", msg);
            }
        }
    }

    //审核公共方法
    private void publicMethodT(PrcVo vo, List<RtcDtlVo> dtlList, SysUser user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("unitId", vo.getVenderId());
        map.put("docNum", vo.getDocNum());
        //获取退货控制类别去冻结相关数据
        if (vo.getRtnaInvd().equals("T")) {
            Psa psa = psaMapper.selectByVde(new Psa(vo.getVenderId(), vo.getVendeeId()));
            if (psa != null) {
                if (psa.getRtnCtrl().equals("Q")) {
                    //按数量
                    rtqQtaService.freeze(psa, dtlList, vo, "SRC", map, true);
                } else if (psa.getRtnCtrl().equals("V")) {
                    //按金额
                    rtvQtaService.freeze(psa, dtlList, vo, "SRC", map, true);
                }
            }
        }
        //登记未决  获取单据明细
        if (dtlList != null && dtlList.size() > 0) {
            map.put("stkType", "EP");
            map.put("docType", "SRC");
            map.put("warehId", vo.getVdrWarehId());
            map.put("dtlList", dtlList);
            warehStkPgMapper.insertByDtl(map);
        }
    }

    /**
     * 重审单据
     *
     * @param vo
     * @return
     */
    @Override
    public int reiterate(PrcVo vo, SysUser user) throws Exception {
        //判断重审后进度的状态
        if (vo.getProgress().equals("CK") && vo.getPrcGen().equals("T")) {
            vo.setProgress("EK");
        } else {
            vo.setProgress("C");
        }
        vo.setEffective("F");

        //公共方法
        this.publicMethod(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));

        //清空退销合同审核信息
        srcMapper.updateByChkr(null, vo.getRtcNum());

        if (prcService.contract(vo)) {
            if (vo.getVdeInvd().equals("F")) {
                //删除入库
                warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "SRC", vo.getUnitId(), vo.getSrcNum()), user);
            }
        }
        if (vo.getPrcGen().equals("T") && StringUtils.isNotEmpty(vo.getSrcNum())) {
            vo.setDocNum(prcMapper.selectByRtcNum(vo.getRtcNum()).getPrcNum());
            return prcService.noticeReiterate(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }
        return rtcMapper.updateBystate(vo);
    }

    /**
     * 挂起单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int hangUp(PrcVo vo, SysUser user) {
        vo.setSuspended("T");
        if (vo.getPrcGen().equals("T")) {
            this.prcSu(vo.getRtcNum(), vo.getSuspended());
        }
        return srcMapper.updateByRtcVo(vo);
    }

    /**
     * 恢复单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int recovery(PrcVo vo, SysUser user) {
        vo.setSuspended("F");
        if (vo.getPrcGen().equals("T")) {
            this.prcSu(vo.getRtcNum(), vo.getSuspended());
        }
        return srcMapper.updateByRtcVo(vo);
    }

    /**
     * 操作退购合同挂起
     *
     * @param rtcNum
     * @param suspended
     */
    public void prcSu(String rtcNum, String suspended) {
        Prc prc = prcMapper.selectByRtcNum(rtcNum);
        if (prc != null) {
            prc.setSuspended(suspended);
            prcMapper.updateByPrimaryKeySelective(prc);
        }
    }

    /**
     * 关闭单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int close(PrcVo vo, SysUser user) throws Exception {
        vo.setProgress("CL");
        this.doClose(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        if (vo.getSrcGen().equals("T")) {
            //调用退销合同退货方关闭通知
            prcService.venderClosed(prcMapper.selectByOnly(new PrcVo(vo.getRtcNum())), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }
        return rtcMapper.updateBystate(vo);
    }

    /**
     * 重用单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int reusing(PrcVo vo, SysUser user) throws Exception {
        vo.setProgress("CK");
        this.doReuse(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        if (vo.getPrcGen().equals("T")) {
            //调用退销合同退货方重用通知
            prcService.venderReused(prcMapper.selectByOnly(new PrcVo(vo.getRtcNum())), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }
        return rtcMapper.updateBystate(vo);
    }

    /**
     * 作废单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int toVoid(PrcVo vo, SysUser user) throws Exception {
        vo.setCancelled("T");
        this.doAbolish(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        if (vo.getPrcGen().equals("T")) {
            //调用退销合同退货方作废通知
            prcService.venderAbolished(srcMapper.selectByOnly(new PrcVo(vo.getRtcNum())), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }
        return rtcMapper.updateBystate(vo);
    }


    /**
     * 退购合同可操作状态
     *
     * @param vo
     * @return
     */
    @Override
    public List<OperationVo> initButtons(PrcVo vo) {
        List<PurKeyValue> keyValues = buttonCommonService.initButton(parameter);
        PrcVo prc = srcMapper.selectByOnly(vo);
        if (prc == null) {
            throw new ServiceException("403", "单据异常！");
        }
        return creatButton(keyValues, prc);
    }


    public List<OperationVo> creatButton(List<PurKeyValue> keyValues, PrcVo vo) {
        List<OperationVo> list = new ArrayList<>();
        for (PurKeyValue pur : keyValues) {
            List<RtcDtl> dtlList = rtcDtlMapper.selectByOperation(vo.getRtcNum(), " ((deliv_qty!= null OR deliv_qty!=0) OR (rcv_qty!= null OR rcv_qty!=0)) ");
            if (("confirm").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("PG") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")
                        && StringUtils.NullEmpty(vo.getItmdRtcNum())) {
                    list.add(new OperationVo("确认", "T", confirm));
                } else {
                    list.add(new OperationVo("确认", "F", confirm));
                }
            }
            if (("redo").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("C") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")
                        && StringUtils.NullEmpty(vo.getItmdRtcNum()) && StringUtils.NullEmpty(vo.getExecRtcNum())
                        && StringUtils.NullEmpty(vo.getStRtcNum()) && StringUtils.NullEmpty(vo.getEndRtcNum())) {
                    list.add(new OperationVo("重做", "T", redo));
                } else {
                    list.add(new OperationVo("重做", "F", redo));
                }
            }
            if (("check").equals(pur.getOptionValue())) {
                if ((vo.getProgress().equals("C") || vo.getProgress().equals("EK")) && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("审核", "T", examine));
                } else {
                    list.add(new OperationVo("审核", "F", examine));
                }
            }
            if (("uncheck").equals(pur.getOptionValue())) {
                if ((vo.getProgress().equals("RK") || vo.getProgress().equals("CK")) && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F") && vo.getTasksInDeliv() == 0 && vo.getTasksInRcv() == 0
                        && dtlList.isEmpty()) {
                    list.add(new OperationVo("反审", "T", reiterate));
                } else {
                    list.add(new OperationVo("反审", "F", reiterate));
                }
            }
            if (("suspend").equals(pur.getOptionValue())) {
                if (!vo.getProgress().equals("RD") && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F")) {
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
            if (("close").equals(pur.getOptionValue())) {
                if (vo.getMultiImpl().equals("T") && vo.getProgress().equals("CK") && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("关闭", "T", close));
                } else {
                    list.add(new OperationVo("关闭", "F", close));
                }
            }
            if (("reuse").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("CL") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("重用", "T", reusing));
                } else {
                    list.add(new OperationVo("重用", "F", reusing));
                }
            }
            if (("abolish").equals(pur.getOptionValue())) {
                if ((vo.getProgress().equals("PG") || vo.getProgress().equals("C") || vo.getProgress().equals("RK") || vo.getProgress().equals("EK")
                        || vo.getProgress().equals("CK")) && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")
                        && vo.getTasksInDeliv() == 0 && vo.getTasksInRcv() == 0 && dtlList.isEmpty()
                        && StringUtils.NullEmpty(vo.getItmdRtcNum()) && StringUtils.NullEmpty(vo.getExecRtcNum())) {
                    list.add(new OperationVo("作废", "T", toVoid));
                } else {
                    list.add(new OperationVo("作废", "F", toVoid));
                }
            }
        }

        return list;
    }


    //重审/关闭公共方法
    private void publicMethod(PrcVo vo, SysUser user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("unitId", vo.getVenderId());
        map.put("docNum", vo.getDocNum());
        //非居间合同
        if (prcService.contract(vo)) {
            //按数量
            String sql = " ,qty ,val";
            List<RtcDtlVo> dtlList = rtcDtlMapper.selectByBill(vo.getRtcNum(), sql);
            if (vo.getRtnaInvd().equals("T")) {
                Psa psa = psaMapper.selectByVde(new Psa(vo.getVenderId(), vo.getVendeeId()));
                if (psa != null) {
                    if (psa.getRtnCtrl().equals("Q")) {

                        rtqQtaService.defreeze(psa, dtlList, vo, "SRC", map);
                    } else if (psa.getRtnCtrl().equals("V")) {
                        //按金额
                        rtvQtaService.defreeze(psa, vo, "SRC", map);
                    }
                }
            }

            //取消未决库存
            List<String> stkList = new ArrayList<>();
            stkList.add("EP");
            map.put("stkList", stkList);
            this.cancel(vo, map, dtlList);
        }

        //监管往来
        if (purchaseService.judgeRtc(vo.getRtcNum()) && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
            //取消授信
            caAccService.revoke(new CaAcc(0L, 0L, "SRC", vo.getVenderId(), vo.getDocNum()), user);
        }
    }

    /**
     * 退购合同通知退销合同审核事件
     *
     * @param vo
     * @return
     */
    @Override
    public int noticeExamine(PrcVo vo, SysUser user) throws Exception {
        if ((StringUtils.isNotEmpty(vo.getSrcDocType()) ? vo.getSrcDocType().equals("RGO") : false) && (StringUtils.isNotEmpty(vo.getSrcDocUnitId() + "") ? vo.getSrcDocUnitId().equals(vo.getVenderId()) : false)
                && vo.getProgress().equals("CK")) {
            rgoService.deliveryUnitChecked(rgoService.selectByOnly(new RgoVo(vo.getSrcDocUnitId(), vo.getSrcDocNum())));
        }
        if (vo.getSrcAutoChk().equals("T") && vo.getProgress().equals("EK")) {
            return this.examine(vo, user, 1);
        }
        return rtcMapper.updateBystate(vo);
    }

    //通知反审事件
    @Override
    public int noticeReiterate(PrcVo vo, SysUser user) throws Exception {
        if (vo.getProgress().equals("RK") && StringUtils.isNotEmpty(vo.getEndRtcNum()) &&
                sysParameterMapper.findById("SALES_RETURN_CONTRACT_AUTO_FORWARD").getParmVal().equals("T")) {
            //根据转送单据编号获取退购合同编号 。如果退购合同未作废，则作废退购合同
        }
        if (vo.getSrcAutoChk().equals("T") && vo.getProgress().equals("RK")) {
            return this.reiterate(vo, user);
        }
        return rtcMapper.updateBystate(vo);
    }

    //退货方关闭通知
    @Override
    public void vendeeClosed(PrcVo vo, SysUser user) throws Exception {
        this.doClose(vo, user);
    }

    //退货方重用通知
    @Override
    public void vendeeReused(PrcVo vo, SysUser user) throws Exception {
        this.doReuse(vo, user);
    }

    //退货方作废通知
    @Override
    public void vendeeAbolished(PrcVo vo, SysUser user) throws Exception {
        this.doAbolish(vo, user);

    }

    /**
     * 生成退销，确认/审核
     *
     * @param vo
     * @param user
     */
    @Override
    public String creatSrcByRgo(RgoVo vo, SysUser user, RgoType rgoType) throws Exception {
        //新增单据
        PrcVo prc = this.creatSrc(vo, user, rgoType);
        //确认单据
        this.confirm(prc, user);
        //判断是否调用审核
        if (vo.getSrcUnitInvd().equals("F") || vo.getSrcUnitInvd().equals(vo.getPrcAutoGen())) {
            this.examine(srcMapper.selectByOnly(prc), user, 1);
        }
        return prc.getRtcNum();
    }

    //查询单条记录
    @Override
    public PrcVo selectByOnly(PrcVo vo) {
        return srcMapper.selectByOnly(vo);
    }


    /**
     * 调配单审核时生成退销合同
     *
     * @param vo
     * @param user
     * @return 返回更方便调用确认 审核
     */
    public PrcVo creatSrc(RgoVo vo, SysUser user, RgoType rgoType) throws Exception {
        //初始化对象并赋值
        PrcVo rtc = new PrcVo();

        rtc.setPscReqd("F");
        rtc.setVdeInvd(vo.getSrcUnitInvd());
        rtc.setPrcAutoGen(vo.getPrcAutoGen());
        rtc.setPrcAutoChk(vo.getPrcAutoChk());
        rtc.setSrcAutoChk("F");
        rtc.setDrDiffJgd(vo.getDrDiffJgd());
        rtc.setMultiImpl("F");
        rtc.setPrcSite("CT");
        rtc.setSplEnabled("F");
        rtc.setIsCms("F");
        rtc.setRtnaInvd("F");
        rtc.setAccEnabled("F");


        //默认没设置退销合同类别
        rtc.setSrcAutoGen("F");
        rtc.setSrBrdgMode("D");
        rtc.setAfReqd("F");
        rtc.setAfBnd("F");
        rtc.setPrFwdd("F");
        rtc.setPrcGen("F");
        rtc.setIsPrItmd("F");
        rtc.setIsSrItmd("F");
        rtc.setBxiEnabled("F");
        rtc.setEffective("F");
        rtc.setCancelled("F");
        rtc.setSuspended("F");
        rtc.setProgress("PG");


        //生成退销/退货编号
        //通过系统参数PS_DOCUMENT_UNIQUED_NUMBERED去生成购销合同编号或者采购合同编号
        SysParameter sys = sysParameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        //生成编号时所需的用户数据
        SysUser sysU = new SysUser(Long.valueOf(vo.getUnitId()), user.getPrsnl().getPrsnlId());
        if (sys.getParmVal().equals("T")) {
            String rtcNum = sysRefNumService.mainNum(sysU, "RTC_NUM");
            rtc.setPrcNum(rtcNum);
            rtc.setRtcNum(rtcNum);
        } else {
            rtc.setSrcNum(sysRefNumService.viceNum(sysU, "SRC_NUM"));
            rtc.setRtcNum(sysRefNumService.mainNum(sysU, "RTC_NUM"));
        }

        rtc.setUnitId(vo.getUnitId());
        //判断调配单中是否有退销类别
        if (StringUtils.isNotEmpty(rgoType.getSrcType())) {
            rtc.setSrcType(rgoType.getSrcType());
            //查询退货合同类别  不为空时修改字段属性值
            RtcAutoVo rtcVo = srcTypeMapper.selectByRtcAuto(rgoType.getSrcType());
            //存在退销协议 更新数据
            if (rtcVo != null) {
                rtc.setRtcType(rtcVo.getRtcType());
                //更新字段值
                rtc.setBxiEnabled(rtcVo.getBxiEnabled());
                rtc.setSrcAutoGen(rtcVo.getSrcAutoGen());
                rtc.setSrBrdgMode(rtcVo.getSrBrdgMode());
                rtc.setAfReqd(rtcVo.getAfReqd());
                rtc.setIsPrItmd(rtcVo.getIsPrItmd());
                rtc.setIsSrItmd(rtcVo.getIsSrItmd());
                rtc.setBxiEnabled(rtcVo.getBxiEnabled());

                rtc.setPrcSite(rtcVo.getPrcSite());
                rtc.setSplEnabled(rtcVo.getSplEnabled());
                rtc.setIsCms(rtcVo.getIsCms());
                rtc.setRtnaInvd(rtcVo.getRtnaInvd());
                rtc.setAccEnabled(rtcVo.getAccEnabled());
            }
        }
        rtc.setVendeeId(vo.getSrcUnitId());
        rtc.setVdeWarehId(vo.getDelivWarehId());
        rtc.setVenderId(vo.getUnitId());
        //中转方不为空就作为供应商仓库
        if (StringUtils.isNotEmpty(vo.getTranWarehId() + "")) {
            rtc.setVdrWarehId(vo.getTranWarehId());
        }
        rtc.setVdrInvd("T");
        rtc.setSrcGen("T");
        rtc.setSrcDocType("RGO");
        rtc.setSrcDocUnitId(vo.getUnitId());
        rtc.setSrcDocNum(vo.getRgoNum());
        rtc.setTtlQty(vo.getTtlQty());
        rtc.setTtlBox(vo.getTtlBox());
        rtc.setTtlVal(vo.getTtlSrcVal());
        rtc.setTtlTax(vo.getTtlSrcTax());
        rtc.setTtlMkv(vo.getTtlSrcMkv());
        rtc.setOprId(user.getPrsnl().getPrsnlId());
        rtc.setRemarks("调配业务。调出方【" + vo.getSrcUnitNum() + "】" + vo.getSrcUnitCp() + ",【" + vo.getDelivWarehNum() + "】" + vo.getDelivWarehCp()
                + ";调入方【" + vo.getDestUnitNum() + "】" + vo.getDestUnitCp() + ",【" + vo.getRcvWarehNum() + "】" + vo.getRcvWarehCp() + "。");

        srcMapper.insertByRtcVo(rtc);
        rtcMapper.insertByRtcVo(rtc);
        //生成明细
        rtcDtlService.insertByPrc(new PrcVo(rtc.getRtcNum(), rtcDtlMapper.selectDtlByRgo(vo)));
        return rtc;
    }

    //退销合同作废事件
    public void doAbolish(PrcVo vo, SysUser user) throws Exception {
        this.publicMethod(vo, user);
        if (prcService.contract(vo)) {
            //删除出库
            warehDelivTaskMapper.deleteByBill(new WarehDelivTask(Long.valueOf(vo.getVendeeId() + ""), "PRC", Long.valueOf(vo.getUnitId() + ""), vo.getPrcNum()));
        }
    }

    //退销合同重用事件
    private void doReuse(PrcVo vo, SysUser user) throws Exception {
        if (prcService.contract(vo)) {
            //合同明细
            String sql = " ,CASE WHEN qty-rcv_qty IS NULL THEN qty ELSE qty-rcv_qty END qty " +
                    " ,CASE WHEN val-rcv_val IS NULL THEN val ELSE val-rcv_val END val ";
            List<RtcDtlVo> dtlList = rtcDtlMapper.selectByBill(vo.getRtcNum(), sql);

            Map<String, Object> map = new HashMap<>();
            map.put("unitId", vo.getVenderId());
            map.put("docNum", vo.getDocNum());
            //获取退货控制类别去冻结相关数据
            if (vo.getRtnaInvd().equals("T")) {
                Psa psa = psaMapper.selectByVde(new Psa(vo.getVenderId(), vo.getVendeeId()));
                if (psa != null) {
                    if (psa.getRtnCtrl().equals("Q")) {
                        //按数量
                        rtqQtaService.freeze(psa, dtlList, vo, "SRC", map, true);
                    } else if (psa.getRtnCtrl().equals("V")) {
                        //按金额
                        rtvQtaService.freeze(psa, dtlList, vo, "SRC", map, true);
                    }
                }
            }
            //公用
            map.put("docType", "SRC");
            map.put("warehId", vo.getVdrWarehId());
            //判断采购商是否介入
            if (vo.getVdeInvd().equals("F")) {
                //登记未决
                if (dtlList != null && dtlList.size() > 0) {
                    map.put("stkType", "EP");
                    map.put("dtlList", dtlList);
                    warehStkPgMapper.insertByDtl(map);
                }
            } else {
                //预期
                sql = " ,CASE WHEN qty-deliv_qty IS NULL THEN qty ELSE qty-deliv_qty END qty ";
                dtlList = rtcDtlMapper.selectByBill(vo.getRtcNum(), sql);
                if (dtlList != null && dtlList.size() > 0) {
                    map.put("stkType", "EP");
                    map.put("dtlList", dtlList);
                    warehStkPgMapper.insertByDtl(map);
                    //在途
                    sql = " ,CASE WHEN deliv_qty-rcv_qty IS NULL THEN deliv_qty ELSE deliv_qty-rcv_qty END qty ";
                    dtlList = rtcDtlMapper.selectByBill(vo.getRtcNum(), sql);
                    map.put("stkType", "IT");
                    map.put("dtlList", dtlList);
                    warehStkPgMapper.insertByDtl(map);
                }
            }


        }

        //监管往来
        if (purchaseService.judgeRtc(vo.getRtcNum()) && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
            Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId())));
            if (ca != null) {
                BigDecimal debitValue = new BigDecimal("0");
                if (vo.getTtlVal().compareTo(debitValue) != 0 && vo.getTtlDelivVal().compareTo(debitValue) != 0 && vo.getAccVal().compareTo(debitValue) != 0) {
                    debitValue = (vo.getTtlVal().subtract(vo.getTtlDelivVal())).multiply(vo.getAccVal().divide(vo.getTtlVal()));
                }
                //授信
                caAccService.accredit(new CaAcc(0L, ca.getCaId(), "PRC", vo.getVenderId(), vo.getDocNum()), user, ca, new BigDecimal("0"), debitValue, true);
            }
        }
    }

    //退销合同关闭事件
    private void doClose(PrcVo vo, SysUser user) throws Exception {
        this.publicMethod(vo, user);
        if (prcService.contract(vo)) {
            //删除入库
            warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "SRC", vo.getUnitId(), vo.getSrcNum()), user);
        }
    }

    /**
     * @param v 新增，修改走得公共方法
     * @return
     */
    private PrcVo updateSrcVo(PrcVo v, SysUser user) {
        if (StringUtils.NullEmpty(v.getVdeInvd())) {
            v.setVdeInvd("F");
        }
        //判断采购商是否介入设置采购相关数据
        if (v.getVdeInvd().equals("T")) {
            v.setPrcAutoGen("T");
        } else {
            v.setPrcAutoGen("F");
            v.setPrcAutoChk("F");
        }
//        //通过采购商id判断是否存在领域 来判定采购商是否介入
//        //查询此采购商是否存在活动领域
//        SysDomain sysDo = sysDomainMapper.selectByPrimaryKey(Long.valueOf(v.getVendeeId()));
//        if (sysDo != null) {
//            if (sysDo.getDomainStatus().equals("A")) {
//                v.setVdeInvd("T");
//                v.setPrcAutoGen("T");
//            } else {
//                v.setVdeInvd("F");
//                v.setPrcAutoGen("F");
//                v.setPrcAutoChk("F");
//            }
//        } else {
//            v.setVdeInvd("F");
//            v.setPrcAutoGen("F");
//            v.setPrcAutoChk("F");
//        }
        v.setPrcGen("F");
        return v;
    }

    //取消未决公共方法
    private int cancel(PrcVo vo, Map<String, Object> map, List<RtcDtlVo> dtlList) {
        if (dtlList != null && dtlList.size() > 0) {
            map.put("docType", "SLC");
            map.put("vo", vo);
            map.put("warehId", vo.getVdrWarehId());
            map.put("dtlList", dtlList);
            return warehStkPgMapper.deleteByDtl(map);
        }
        return 0;
    }

    /**
     * 出入库明细查询
     *
     * @param vo
     * @return
     */
    @Override
    public PrcVo selectObject(PrcVo vo) {
        return srcMapper.selectObject(vo);
    }

    /**
     * 导入退购合同明细
     *
     * @param vo
     * @return
     */
    @Override
    public Map<String, Object> importBill(PrcVo vo) {
        Map<String, Object> map = new HashMap<>();
        List<RtcDtlVo> list = rtcDtlMapper.importBill(vo);
        map.put("rtcDtlList", list);
        return map;
    }

    /**
     * 导入更新
     *
     * @param vo
     * @return
     */
    @Override
    public int importUpdate(PrcVo vo) {
        //删除之前明细
        prcService.deletePrcDtl(vo.getRtcNum(), null);
        RtcDtlVo dtlVo = new RtcDtlVo();
        dtlVo.setRtcNum(vo.getRtcNum());
        dtlVo.setList(vo.getRtcDtlList());
        return rtcDtlService.insertRtcDtl(dtlVo);
    }

    /**
     * 退货方发货通知
     *
     * @param vo
     * @param stb
     * @param user
     * @return
     */
    @Override
    public void vendeeDelivered(PrcVo vo, Stb stb, SysUser user) throws Exception {
        //判读非居间合同
        if (prcService.contract(vo)) {
            Map<String, Object> map = new HashMap<>();
            map.put("docType", "SRC");
            map.put("unitId", vo.getUnitId());
            map.put("docNum", vo.getSrcNum());
            map.put("warehId", vo.getVdrWarehId());
            map.put("stbUnitId", stb.getUnitId());
            map.put("stbNum", stb.getStbNum());
            if (vo.getMultiImpl().equals("T")) {
                map.put("stkType", "EP");
                warehStkPgMapper.reduceStkPg(map);
            } else {
                map.put("stkType", "EP");
                warehStkPgMapper.deleteStkPg(map);
            }
            map.put("stkType", "IT");
            warehStkPgMapper.increaseStkPg(map);

            if (vo.getAccEnabled().equals("T") && vo.getRtnAccSite().equals("DD") && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
                Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                BigDecimal creditValue = new BigDecimal("0");
                if (ca != null) {
                    //取消授信
                    if (vo.getMultiImpl().equals("T")) {
                        caAccService.revoke(new CaAcc(0L, 0L, "SRC", vo.getVenderId(), vo.getDocNum()), user);
                        creditValue = vo.getTtlDelivVal().subtract(vo.getTtlRcvVal());
                    } else {
                        creditValue = vo.getTtlDelivVal();
                    }
                    //授信
                    caAccService.accredit(new CaAcc(0L, ca.getCaId(), "SRC", vo.getVenderId(), vo.getSrcNum()), user, ca, new BigDecimal("0"), creditValue, true);
                }
            }
            //判断是否生成入库任务
            if (vo.getMultiImpl().equals("F") || (vo.getMultiImpl().equals("T") && !rtcDtlMapper.selectByOperation(vo.getRtcNum(), "deliv_qty > IFNULL(rcv_qty,0)").isEmpty())
                    || StringUtils.isNotEmpty(vo.getEndRtcNum())) {
                purchaseUtils.generate("SRC", vo.getRtcNum(), user);
            }
        }
        if ((StringUtils.isNotEmpty(vo.getSrcDocType()) ? vo.getSrcDocType().equals("RGO") : false) && (StringUtils.isNotEmpty(vo.getSrcDocUnitId() + "") ? vo.getSrcDocUnitId().equals(vo.getVenderId()) : false)) {
            rgoService.deliveryUnitDelivered(rgoService.selectByOnly(new RgoVo(vo.getSrcDocUnitId(), vo.getSrcDocNum())), stb);
        }
    }

    /**
     * 退货方取消发货通知
     *
     * @param vo
     * @param stb
     * @param user
     * @return
     */
    @Override
    public int vendeeReverseDelivered(PrcVo vo, Stb stb, SysUser user) throws Exception {
        //判读非居间合同
        if (prcService.contract(vo)) {
            Map<String, Object> map = new HashMap<>();
            map.put("docType", "SRC");
            map.put("unitId", vo.getUnitId());
            map.put("docNum", vo.getSrcNum());
            map.put("warehId", vo.getVdrWarehId());
            map.put("stbUnitId", stb.getUnitId());
            map.put("stbNum", stb.getStbNum());
            if (vo.getMultiImpl().equals("T")) {
                map.put("stkType", "EP");
                warehStkPgMapper.increaseStkPg(map);
                map.put("stkType", "IT");
                warehStkPgMapper.reduceStkPg(map);
            } else {
                map.put("stkType", "IT");
                warehStkPgMapper.deleteStkPg(map);
                map.put("stkType", "EP");
                map.put("dtlList", rtcDtlMapper.selectByOperation(vo.getRtcNum(), ""));
                warehStkPgMapper.insertByDtl(map);
            }

            if (vo.getAccEnabled().equals("T") && vo.getRtnAccSite().equals("DD") && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
                Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                if (ca != null) {
                    //取消授信
                    caAccService.revoke(new CaAcc(0L, 0L, "SRC", vo.getVenderId(), vo.getDocNum()), user);
                    if (vo.getMultiImpl().equals("T")) {
                        BigDecimal creditValue = vo.getTtlDelivVal().subtract(vo.getTtlRcvVal());
                        //授信
                        caAccService.accredit(new CaAcc(0L, ca.getCaId(), "SRC", vo.getVenderId(), vo.getSrcNum()), user, ca, new BigDecimal("0"), creditValue, true);
                    }
                }
            }
            //判断是否撤销入库任务
            if (vo.getMultiImpl().equals("F") || (vo.getMultiImpl().equals("T") && rtcDtlMapper.selectByOperation(vo.getRtcNum(), "deliv_qty > rcv_qty").isEmpty()
                    || StringUtils.isNotEmpty(vo.getEndRtcNum()))) {
                warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "SRC", vo.getUnitId(), vo.getSrcNum()), user);
            }
        }
        if ((StringUtils.isNotEmpty(vo.getSrcDocType()) ? vo.getSrcDocType().equals("RGO") : false) && (StringUtils.isNotEmpty(vo.getSrcDocUnitId() + "") ? vo.getSrcDocUnitId().equals(vo.getVenderId()) : false)) {
            rgoService.deliveryUnitReverseDelivered(rgoService.selectByOnly(new RgoVo(vo.getSrcDocUnitId(), vo.getSrcDocNum())), stb);
        }
        return 0;
    }

    /**
     * 退销合同开始收货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @return
     */
    @Override
    public int startReceive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        PrcVo vo = srcMapper.selectObject(new PrcVo("", srcDocNum, srcDocUnitId + ""));
        if (vo != null) {
            if ((vo.getProgress().equals("CK") || vo.getProgress().equals("DD")) && vo.getSuspended().equals("F")
                    && vo.getCancelled().equals("F")) {
                if (vo.getMultiImpl().equals("F")) {
                    vo.setProgress("RG");
                } else {
                    vo.setProgress("CK");
                }
                vo.setTasksInRcv(vo.getTasksInRcv() + 1);
                //判断是否撤销入库任务
                if (prcService.contract(vo) && vo.getMultiImpl().equals("F")) {
                    warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "SRC", vo.getUnitId(), vo.getSrcNum()), user);
                }
            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
        return rtcMapper.updateByPrcVo(vo);
    }

    /**
     * 退销合同终止收货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int stopReceive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        PrcVo vo = srcMapper.selectObject(new PrcVo("", srcDocNum, srcDocUnitId + ""));
        if (vo != null) {
            if ((vo.getProgress().equals("CK") || vo.getProgress().equals("RG")) && vo.getSuspended().equals("F")) {
                if (vo.getMultiImpl().equals("F") && vo.getVdeInvd().equals("T")) {
                    vo.setProgress("DD");
                } else {
                    vo.setProgress("CK");
                }
                vo.setTasksInRcv(vo.getTasksInRcv() - 1);
                //判断是否登记入库任务
                if (StringUtils.isNotEmpty(vo.getEndRtcNum())) {
                    purchaseUtils.generate("SRC", vo.getRtcNum(), user);
                }
            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
        return rtcMapper.updateByPrcVo(vo);
    }

    /**
     * 退销合同收货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @param docUnitId    出库单组织id
     * @param docNum       出库单据编号
     * @param user         用户
     * @return
     * @throws Exception
     */
    @Override
    public int receive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        PrcVo vo = srcMapper.getSrcByStb(srcDocUnitId, srcDocNum, docUnitId, docNum, true);
        //单据信息
        Stb stb = stbService.getStbByPk(new Stb(docUnitId, docNum));
        if (vo != null) {
            if ((vo.getProgress().equals("RG") || (vo.getMultiImpl().equals("T") && vo.getProgress().equals("CK"))) &&
                    vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                if (vo.getMultiImpl().equals("T")) {
                    vo.setProgress("CK");
                } else {
                    vo.setProgress("RD");
                }

                //通过sql语句查询更新明细
                List<RtcDtl> dtlList = null;
                if (vo.getPrcGen().equals("T")) {
                    dtlList = rtcDtlMapper.selectByStbDtl(vo.getRtcNum(), docUnitId, docNum, true, "SRC");
                } else {
                    dtlList = rtcDtlMapper.findByStbDtl(vo.getRtcNum(), docUnitId, docNum, true);
                }
                if (dtlList != null && dtlList.size() > 0) {
                    rtcDtlMapper.updateDtlList(dtlList);
                }

                //查询stb明细
                List<StbDtlVo> stbDtlVo = stbDtlService.getStbDtlList(new StbDtl(docUnitId, docNum));

                //判断购销合同是否为空
                if (StringUtils.isNotEmpty(vo.getPscNum())) {
                    //更新相应的购销合同数据
                    slcService.returnReceive(vo.getPscNum(), docUnitId, docNum);
                }

                //非居间合同
                if (prcService.contract(vo)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("unitId", vo.getVenderId());
                    map.put("docNum", vo.getDocNum());
                    //退货控制类别
                    if (vo.getRtnaInvd().equals("T")) {
                        Psa psa = psaMapper.selectByVde(new Psa(vo.getVenderId(), vo.getVendeeId()));
                        if (psa != null) {
                            if (psa.getRtnCtrl().equals("Q")) {
                                List<RtcDtlVo> rtqList = rtcDtlMapper.selectByBillQty(docUnitId, docNum);
                                //按数量
                                if (vo.getMultiImpl().equals("T")) {
                                    //减少冻结
                                    rtqQtaService.freeze(psa, rtqList, vo, "SRC", map, false);
                                } else {
                                    //取消冻结
                                    rtqQtaService.defreeze(psa, rtqList, vo, "SRC", map);
                                }
                                rtqQtaService.consume(psa, rtqList, vo, "SRC", true);
                            } else if (psa.getRtnCtrl().equals("V")) {
                                List<RtcDtlVo> rtqList = rtcDtlMapper.selectByBillVal(docUnitId, docNum);
                                //按金额
                                if (vo.getMultiImpl().equals("T")) {
                                    //减少冻结
                                    rtvQtaService.freeze(psa, rtqList, vo, "SRC", map, false);
                                } else {
                                    //取消冻结
                                    rtvQtaService.defreeze(psa, vo, "SRC", map);
                                }
                                rtvQtaService.consume(psa, rtqList, vo, "SRC", true);
                            }
                        }
                    }
                    //登记未决
                    map.put("docType", "SRC");
                    map.put("warehId", vo.getVdrWarehId());
                    if (vo.getVdeInvd().equals("T")) {
                        map.put("stkType", "IT");
                    } else {
                        map.put("stkType", "BK");
                    }
                    if (vo.getMultiImpl().equals("T")) {
                        map.put("dtlList", stbDtlVo);
                        map.put("flag", "stb_j");
                        warehStkPgMapper.updateByNegDtl(map);
                    } else {
                        //删除
                        List<String> stkList = new ArrayList<>();
                        stkList.add(map.get("stkType").toString());
                        map.put("stkList", stkList);
                        map.put("dtlList", dtlList);
                        warehStkPgMapper.deleteByDtl(map);
                    }
                }
                //判断监管往来
                if (purchaseService.judgeRtc(vo.getRtcNum()) && vo.getAccEnabled().equals("T")
                        && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
                    Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                    if (ca != null) {
                        if (vo.getMultiImpl().equals("T")) {
                            //负数登记
                            BigDecimal creditValue = new BigDecimal("0");
                            if (vo.getRtnAccSite().equals("CK")) {
                                if (vo.getTtlVal().compareTo(creditValue) != 0) {
                                    creditValue = (creditValue.subtract(stb.getTtlVal())).multiply(vo.getAccVal().divide(vo.getTtlVal()));
                                }
                            } else if (vo.getRtnAccSite().equals("DD")) {
                                creditValue = creditValue.subtract(stb.getTtlVal());
                            }
                            //授信
                            caAccService.accredit(new CaAcc(0L, ca.getCaId(), "SRC", vo.getVendeeId(), vo.getDocNum()), user, ca, new BigDecimal("0"), creditValue, false);
                        } else {
                            //注销
                            caAccService.revoke(new CaAcc(0L, 0L, "SRC", vo.getVendeeId(), vo.getDocNum()), user);
                        }
                    }
                }

                vo.setTasksInRcv(vo.getTasksInRcv() - 1);
                if (vo.getPrcGen().equals("F")) {
                    vo.setTtlDelivQty(vo.getTtlRcvQty());
                    vo.setTtlDelivBox(vo.getTtlRcvBox());
                    vo.setTtlDelivVal(vo.getTtlRcvVal());
                    vo.setTtlDelivTax(vo.getTtlRcvTax());
                    vo.setTtlDelivMkv(vo.getTtlRcvMkv());
                }
                //判断原始单据是否调配单
                if ((StringUtils.isNotEmpty(vo.getSrcDocType()) ? vo.getSrcDocType().equals("RGO") : false) && (StringUtils.isNotEmpty(vo.getSrcDocUnitId() + "") ? vo.getSrcDocUnitId().equals(vo.getVenderId()) : false)) {
                    rgoService.transferUnitReceived(rgoService.selectByOnly(new RgoVo(vo.getSrcDocUnitId(), vo.getSrcDocNum())), user);
                }

                //判断是否自动关闭
                int closeType = 0;
                SysParameter sysPa = parameterMapper.findById("RTC_AUTO_CLOSE_WHEN_EXPECTATION_REACHED");
                if (vo.getMultiImpl().equals("T") && sysPa.getParmVal().equals("T") && vo.getTasksInDeliv() == 0 && vo.getTasksInRcv() == 0
                        && rtcDtlMapper.selectByOperation(vo.getRtcNum(), " IFNULL(rcv_qty,0) < qty ").isEmpty()) {
                    closeType = this.close(vo, user);
                    if (closeType == 1) {
                        //更新状态避免最后更新的时候状态不对
                        vo.setProgress("CL");
                    }
                }

                //非居间合同下
                if (prcService.contract(vo)) {
                    //判断是否登记收发差异
                    if (vo.getPrcGen().equals("T") && vo.getMultiImpl().equals("F") && !rtcDtlMapper.selectByOperation(vo.getRtcNum(), " deliv_qty <> rcv_qty ").isEmpty()) {
                        //查询退购合同信息
                        Prc prc = prcMapper.selectByRtcNum(vo.getRtcNum());
                        //添加收发差异  判断是否有出库仓库与入库仓库
                        if (StringUtils.NullEmpty(vo.getVdeWarehId() + "")) {
                            Long vdeWareh = gdnExternalCiteService.queryDelivWarehIdBySrcDocMess("PRC", prc.getPrcNum(), Long.valueOf(prc.getUnitId()), Long.valueOf(vo.getVenderId()));
                            vo.setVdeWarehId(vdeWareh);
                        }
                        if (StringUtils.NullEmpty(vo.getVdrWarehId() + "")) {
                            Long vdrWareh = gdnExternalCiteService.queryRcvWarehIdBySrcDocMess("SRC", vo.getSrcNum(), Long.valueOf(vo.getUnitId()), Long.valueOf(vo.getVendeeId()));
                            vo.setVdrWarehId(vdrWareh);
                        }
                        //调用收发差异登记
                        tranDiffEvtService.register(new TranDiffEvt(Long.valueOf(prc.getUnitId()), "PRC", prc.getPrcNum(), vo.getUnitId(), "SRC", vo.getDocNum(), vo.getVdrWarehId(), vo.getVdeWarehId(), "R"));

                    }

                    //判断是撤销还是登记入库任务
                    if (vo.getMultiImpl().equals("T") && closeType == 0 && StringUtils.NullEmpty(vo.getEndRtcNum())) {
                        if (rtcDtlMapper.selectByOperation(vo.getRtcNum(), " IFNULL(deliv_qty,0) < IFNULL(rcv_qty,0) ").isEmpty()) {
                            //撤销
                            warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "SRC", vo.getUnitId(), vo.getSrcNum()), user);
                        } else {
                            rtcMapper.updateByPrcVo(vo);
                            //登记
                            purchaseUtils.generate("SRC", vo.getRtcNum(), user);
                        }
                    }

                    if (vo.getMultiImpl().equals("F") && StringUtils.NullEmpty(vo.getEndRtcNum()) && closeType == 0) {
                        //调整进度
                        if (vo.getVdeInvd().equals("T")) {
                            vo.setProgress("DD");
                        } else {
                            vo.setProgress("CK");
                        }
                        //登记
                        rtcMapper.updateByPrcVo(vo);
                        purchaseUtils.generate("SRC", vo.getRtcNum(), user);
                    }
                }

            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
        return rtcMapper.updateByPrcVo(vo);
    }

    /**
     * 退销合同取消收货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @param docUnitId    出库单组织id
     * @param docNum       出库单据编号
     * @param user         用户
     * @return
     * @throws Exception
     */
    @Override
    public int reverseReceive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        PrcVo vo = srcMapper.getSrcByStb(srcDocUnitId, srcDocNum, docUnitId, docNum, false);
        //单据信息
        Stb stb = stbService.getStbByPk(new Stb(docUnitId, docNum));
        if (vo != null) {
            SysParameter sysPa = parameterMapper.findById("SALES_RETURN_CONTRACT_SDMR_ENABLED");
            //初始单据状态
            String oldProgress = vo.getProgress();
            if (vo.getProgress().equals("RD") || (sysPa.getParmVal().equals("T") && (vo.getProgress().equals("DD") || vo.getProgress().equals("CK"))) &&
                    vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                if (vo.getMultiImpl().equals("T")) {
                    vo.setProgress("CK");
                } else {
                    vo.setProgress("RG");
                }

                //通过sql语句查询更新明细
                List<RtcDtl> dtlList = null;
                if (vo.getSrcGen().equals("T")) {
                    dtlList = rtcDtlMapper.selectByStbDtl(vo.getRtcNum(), docUnitId, docNum, false, "SRC");
                } else {
                    dtlList = rtcDtlMapper.findByStbDtl(vo.getRtcNum(), docUnitId, docNum, false);
                }
                if (dtlList != null && dtlList.size() > 0) {
                    rtcDtlMapper.updateDtlList(dtlList);
                }

                //查询stb明细
                List<StbDtlVo> stbDtlVo = stbDtlService.getStbDtlList(new StbDtl(docUnitId, docNum));

                //判断购销合同是否为空
                if (StringUtils.isNotEmpty(vo.getPscNum())) {
                    //更新相应的购销合同数据
                    slcService.reverseReturnReceive(vo.getPscNum(), docUnitId, docNum);
                }

                //非居间合同
                if (prcService.contract(vo)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("unitId", vo.getVenderId());
                    map.put("docNum", vo.getDocNum());
                    //退货控制类别
                    if (vo.getRtnaInvd().equals("T")) {
                        Psa psa = psaMapper.selectByVde(new Psa(vo.getVenderId(), vo.getVendeeId()));
                        if (psa != null) {
                            if (psa.getRtnCtrl().equals("Q")) {
                                List<RtcDtlVo> rtqList = rtcDtlMapper.selectByBillQty(docUnitId, docNum);
                                //按数量
                                rtqQtaService.consume(psa, rtqList, vo, "SRC", false);
                                //增加冻结
                                if (vo.getMultiImpl().equals("F")) {
                                    rtqList = rtcDtlMapper.selectByBill(vo.getRtcNum(), " ,(rd.qty - IFNULL(rd.rcv_qty,0)) AS qty ");
                                }
                                rtqQtaService.freeze(psa, rtqList, vo, "SRC", map, false);

                            } else if (psa.getRtnCtrl().equals("V")) {
                                List<RtcDtlVo> rtqList = rtcDtlMapper.selectByBillVal(docUnitId, docNum);
                                rtvQtaService.consume(psa, rtqList, vo, "SRC", false);
                                //按金额
                                if (vo.getMultiImpl().equals("F")) {
                                    rtqList = rtcDtlMapper.selectByBill(vo.getRtcNum(), " ,(rd.val - IFNULL(rd.rcv_val,0)) AS val ");
                                }
                                rtvQtaService.freeze(psa, rtqList, vo, "SRC", map, true);
                            }
                        }
                    }
                    //登记未决
                    map.put("docType", "SRC");
                    map.put("warehId", vo.getVdrWarehId());
                    if (vo.getVdeInvd().equals("T")) {
                        map.put("stkType", "IT");
                    } else {
                        map.put("stkType", "BK");
                    }
                    if (vo.getMultiImpl().equals("T")) {
                        map.put("dtlList", stbDtlVo);
                    } else {
                        List<RtcDtlVo> rtqList = rtcDtlMapper.selectByBill(vo.getRtcNum(), " ,(rd.qty - IFNULL(rd.rcv_qty,0)) AS qty ");
                        map.put("dtlList", rtqList);
                    }
                    warehStkPgMapper.insertByDtl(map);
                }

                //判断监管往来
                if (purchaseService.judgeRtc(vo.getRtcNum()) && vo.getAccEnabled().equals("T")
                        && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
                    Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                    if (ca != null) {
                        BigDecimal creditValue = new BigDecimal("0");
                        if (vo.getMultiImpl().equals("T")) {
                            //登记
                            if (vo.getRtnAccSite().equals("CK")) {
                                if (vo.getTtlVal().compareTo(creditValue) != 0) {
                                    creditValue = (stb.getTtlVal()).multiply(vo.getAccVal().divide(vo.getTtlVal()));
                                }
                            } else if (vo.getRtnAccSite().equals("DD")) {
                                creditValue = stb.getTtlVal();
                            }
                            //授信
                            caAccService.accredit(new CaAcc(0L, ca.getCaId(), "SRC", vo.getVendeeId(), vo.getDocNum()), user, ca, new BigDecimal("0"), creditValue, true);
                        } else {
                            //注销
                            caAccService.revoke(new CaAcc(0L, ca.getCaId(), "SRC", vo.getVendeeId(), vo.getDocNum()), user);
                            if (vo.getRtnAccSite().equals("CK")) {
                                creditValue = vo.getAccVal();
                            } else if (vo.getRtnAccSite().equals("DD")) {
                                creditValue = vo.getTtlDelivVal();
                            }
                            caAccService.accredit(new CaAcc(0L, ca.getCaId(), "SRC", vo.getVendeeId(), vo.getDocNum()), user, ca, new BigDecimal("0"), creditValue, true);
                        }
                    }
                }

                vo.setTasksInRcv(vo.getTasksInRcv() + 1);

                if (vo.getPrcGen().equals("F")) {
                    vo.setTtlDelivQty(vo.getTtlRcvQty());
                    vo.setTtlDelivBox(vo.getTtlRcvBox());
                    vo.setTtlDelivVal(vo.getTtlRcvVal());
                    vo.setTtlDelivTax(vo.getTtlRcvTax());
                    vo.setTtlDelivMkv(vo.getTtlRcvMkv());
                }

                //判断原始单据是否调配单
                if ((StringUtils.isNotEmpty(vo.getSrcDocType()) ? vo.getSrcDocType().equals("RGO") : false) && (StringUtils.isNotEmpty(vo.getSrcDocUnitId() + "") ? vo.getSrcDocUnitId().equals(vo.getVenderId()) : false)) {
                    rgoService.transferUnitReverseReceived(rgoService.selectByOnly(new RgoVo(vo.getSrcDocUnitId(), vo.getSrcDocNum())), user);
                }
                //非居间合同
                if (prcService.contract(vo)) {
                    //判断是否撤销收发差异
                    if (vo.getPrcGen().equals("T") && vo.getMultiImpl().equals("F")) {
                        //查询出退购合同号
                        Prc prc = prcMapper.selectByRtcNum(vo.getRtcNum());
                        //删除收发差异
                        tranDiffEvtService.deregister(new TranDiffEvt(Long.valueOf(prc.getUnitId()), "PRC", prc.getPrcNum(), vo.getUnitId(), "SRC", vo.getSrcNum()));
                    }

                    //判断是否登记入库任务
                    if (vo.getMultiImpl().equals("T") && vo.getVdeInvd().equals("T") && StringUtils.NullEmpty(vo.getEndRtcNum())
                            && !rtcDtlMapper.selectByOperation(vo.getRtcNum(), " deliv_qty > rcv_qty ").isEmpty()) {
                        rtcMapper.updateByPrcVo(vo);
                        purchaseUtils.generate("SRC", vo.getRtcNum(), user);
                    }

                    //判断是否撤销入库任务
                    if (vo.getMultiImpl().equals("F") && StringUtils.NullEmpty(vo.getEndRtcNum()) && oldProgress.equals("RD")) {
                        warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "SRC", vo.getUnitId(), vo.getSrcNum()), user);
                    }
                }

            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
        return rtcMapper.updateByPrcVo(vo);
    }
}
