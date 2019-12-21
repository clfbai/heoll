package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.entity.purchase.*;
import com.boyu.erp.platform.usercenter.entity.sales.Src;
import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.CaMapper;
import com.boyu.erp.platform.usercenter.mapper.basic.CompanyMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.*;
import com.boyu.erp.platform.usercenter.mapper.sales.SrcMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysDomainMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehDelivTaskMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehStkPgMapper;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.caservice.CaAccService;
import com.boyu.erp.platform.usercenter.service.common.impl.ButtonCommonService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PrcService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PucService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.RtcDtlService;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoService;
import com.boyu.erp.platform.usercenter.service.salesservice.SrcService;
import com.boyu.erp.platform.usercenter.service.system.RtqQtaService;
import com.boyu.erp.platform.usercenter.service.system.RtvQtaService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehDelivTaskService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.PurchaseUtils;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.ProdClsDtlVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbDtlVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname PrcServiceimpl
 * @Description TODO
 * @Date 2019/7/25 11:22
 * @Created wz
 */
@Slf4j
@Service
@Transactional
public class PrcServiceImpl implements PrcService {
    //退购合同模块按钮参数
    static final String parameter = "prcButton";

    //确认
    static final String confirm = "/purchase/prc/confirm";
    //重做
    static final String redo = "/purchase/prc/redo";
    //审核
    static final String examine = "/purchase/prc/examine";
    //反审
    static final String reiterate = "/purchase/prc/reiterate";
    //挂起
    static final String hangUp = "/purchase/prc/hangUp";
    //恢复
    static final String recovery = "/purchase/prc/recovery";
    //作废
    static final String toVoid = "/purchase/prc/toVoid";
    //关闭
    static final String close = "/purchase/prc/close";
    //重用
    static final String reusing = "/purchase/prc/reusing";
    @Autowired
    private UsersService usersService;
    @Autowired
    private PrcMapper prcMapper;
    @Autowired
    private RtcMapper rtcMapper;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private SysDomainMapper sysDomainMapper;
    @Autowired
    private RtcDtlMapper rtcDtlMapper;
    @Autowired
    private RtcDtlService rtcDtlService;
    @Autowired
    private SrcService srcService;
    @Autowired
    private SrcMapper srcMapper;
    @Autowired
    private PurchaseUtils purchaseUtils;
    @Autowired
    private PrcTypeMapper prcTypeMapper;
    @Autowired
    private WarehDelivTaskMapper warehDelivTaskMapper;
    @Autowired
    private PsaMapper psaMapper;
    @Autowired
    private RtqQtaService rtqQtaService;
    @Autowired
    private RtvQtaService rtvQtaService;
    @Autowired
    private WarehStkPgMapper warehStkPgMapper;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private CaAccService caAccService;
    @Autowired
    private CaMapper caMapper;
    @Autowired
    private ButtonCommonService buttonCommonService;
    @Autowired
    private RtcTypeMapper rtcTypeMapper;
    @Autowired
    private PucService pucService;
    @Autowired
    private StbDtlService stbDtlService;
    @Autowired
    private StbService stbService;
    @Autowired
    private RgoService rgoService;
    @Autowired
    private WarehDelivTaskService warehDelivTaskService;

    /**
     * 分页查询
     *
     * @param vo
     * @param page
     * @param size
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<PrcVo> selectAll(PrcVo vo, Integer page, Integer size, SysUser user) throws Exception {
        List<PrcVo> list = null;
        /**
         * 系统管理员
         * */
        if (false) {
            PageHelper.startPage(page, size);
            list = prcMapper.selectALL(vo);
        } else {
            PageHelper.startPage(page, size);
            list = prcMapper.selectByUnit(vo);
        }
        PageInfo<PrcVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增退购合同
     *
     * @param v
     * @param user
     * @return
     */
    @Override
    public int insertPrc(PrcVo v, SysUser user) {
        //通过系统参数PS_DOCUMENT_UNIQUED_NUMBERED去生成购销合同编号或者采购合同编号
        SysParameter sys = parameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        SysUser sysU = new SysUser(Long.valueOf(v.getVendeeId()), user.getPrsnl().getPrsnlId());
        if (sys.getParmVal().equals("T")) {
            String rtcNum = sysRefNumService.mainNum(sysU, "RTC_NUM");
            v.setPrcNum(rtcNum);
            v.setRtcNum(rtcNum);
        } else {
            String prcNum = sysRefNumService.viceNum(sysU, "PRC_NUM");
            String rtcNum = sysRefNumService.mainNum(sysU, "RTC_NUM");
            v.setPrcNum(prcNum);
            v.setRtcNum(rtcNum);
        }

        v = this.updatePrcVo(v, user);


        v.setEffective("F");
        v.setCancelled("F");
        v.setSuspended("F");
        v.setProgress("PG");

        //默认退购合同已生成
        v.setVdeInvd("T");
        v.setPrcGen("T");
        v.setSrFwdd("F");
        v.setAcBnd("F");

        v.setOprId(user.getPrsnl().getPrsnlId());

        if (v.getRtcDtlList() != null && v.getRtcDtlList().size() > 0) {
            v = rtcDtlService.insertByPrc(v);
        }

        prcMapper.insertByPrcVo(v);

        return rtcMapper.insertByRtcVo(v);
    }

    /**
     * @param v 新增，修改走得公共方法
     * @return
     */
    private PrcVo updatePrcVo(PrcVo v, SysUser user) {
        //判断供应商是否介入设置销售相关数据
        if(StringUtils.NullEmpty(v.getVdrInvd())){
            v.setVdrInvd("F");
        }
        if (v.getVdrInvd().equals("T")) {
            v.setSrcAutoGen("T");
        } else {
            v.setSrcAutoGen("F");
            v.setSrcAutoChk("F");
        }
//        //通过供应商id判断是否存在领域 来判定供应商是否介入
//        //查询此供应商是否存在活动领域
//        SysDomain sysDo = sysDomainMapper.selectByPrimaryKey(Long.valueOf(v.getVenderId()));
//        if (sysDo != null) {
//            if (sysDo.getDomainStatus().equals("A")) {
//                v.setVdrInvd("T");
//                v.setSrcAutoGen("T");
//            } else {
//                v.setVdrInvd("F");
//                v.setSrcAutoGen("F");
//                v.setSrcAutoChk("F");
//            }
//        } else {
//            v.setVdrInvd("F");
//            v.setSrcAutoGen("F");
//            v.setSrcAutoChk("F");
//        }
        v.setSrcGen("F");
        return v;
    }


    /**
     * 修改退购合同
     *
     * @param v
     * @param sysUser
     * @return
     */
    @Override
    public int updatePrc(PrcVo v, SysUser sysUser) {
        v.setOprId(sysUser.getPrsnl().getPrsnlId());

        v = this.updatePrcVo(v, sysUser);

        prcMapper.updateByPrcVo(v);
        return rtcMapper.updateByPrcVo(v);
    }

    /**
     * 删除退购合同
     *
     * @param v
     * @param sysUser
     * @return
     */
    @Override
    public int deletePrc(PrcVo v, SysUser sysUser) throws Exception {
        //删除prc表数据
        prcMapper.deletePrcVo(v.getVendeeId() + "", v.getPrcNum());

        v.setPrcGen("F");

        if (StringUtils.isNotEmpty(v.getStRtcNum())) {
            // 如果ST_RTC_NUM非空，调用ST_RTC_NUM对应退销合同的SalesReturnContractHome.detach方法，取消关联。
            v.setEndRtcNum("");
            srcService.relation(v.getStRtcNum(), new SysUser(Long.valueOf(v.getVendeeId()), sysUser.getPrsnl().getPrsnlId()));
        }

        //        如果SLC_AUTO_GEN = TRUE && SLC_GEN = TRUE，则调用SalesContractHome.remove，删除SLC。
        if (v.getSrcAutoGen().equals("T") && v.getSrcGen().equals("T")) {
            //调用销售合同接口 删除slc表数据
            v.setSrcNum(srcMapper.selectByRtcNum(v.getRtcNum()).getSrcNum());
            return srcService.deleteSrc(v, new SysUser(Long.valueOf(v.getVenderId()), sysUser.getPrsnl().getPrsnlId()));
        }

        //判断是否删除rtc表  否则就更新它 设置PRC_GEN = FALSE。
        if (v.getSrcGen().equals("F")) {
            rtcDtlMapper.deleteByRtcNum(v.getRtcNum());
            return rtcMapper.deleteByPrimaryKey(v.getRtcNum());
        }

        return 1;

    }

    //清除明细 更新退销合同
    @Override
    public int deletePrcDtl(String rtcNum, SysUser sysUser) {
        //删除退销明细
        //更新退销合同数据
        rtcDtlMapper.deleteByRtcNum(rtcNum);
        return rtcMapper.updateByRtcDtl(rtcNum);
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

        //查询供应商，采购商的会计组织id 存入购销合同
        vo.setVdrFsclUnitId(
                companyMapper.selectByPrimaryKey(Long.valueOf(vo.getVenderId())).getFsclUnitId());
        vo.setVdeFsclUnitId(
                companyMapper.selectByPrimaryKey(user.getDomain().getUnitId()).getFsclUnitId());
        if (vo.getSrcGen().equals("F") && vo.getSrcAutoGen().equals("T")) {
            vo.setSrcGen("T");
            srcService.insertByPrc(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
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
        //判断是否存在退销合同   有就删除
        if (vo.getSrcGen().equals("T")) {
            vo.setSrcGen("F");
            srcMapper.deleteByPrc(vo.getVenderId() + "", vo.getRtcNum());
        }
        return rtcMapper.updateBystate(vo);
    }

    /**
     * 单据审核
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int examine(PrcVo vo, SysUser user) throws Exception {
        //将状态改为已审核状态  并修改采购合同与销售合同审核状态
        if (vo.getProgress().equals("C") && vo.getSrcGen().equals("T")) {
            vo.setProgress("EK");
            vo.setEffective("F");
        } else {
            vo.setProgress("CK");
            vo.setEffective("T");
        }

        if (this.contract(vo)) {
            //合同明细
            String sql = " ,qty ,val";
            List<RtcDtlVo> dtlList = rtcDtlMapper.selectByBill(vo.getRtcNum(), sql);

            this.publicMethodT(vo, dtlList, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));

        }
        //监管往来
        if (purchaseService.judgeRtc(vo.getRtcNum()) && vo.getAccEnabled().equals("T") && vo.getRtnAccSite().equals("CK")
                && !vo.getVdeFsclUnitId().equals(vo.getVenderId()) && vo.getAccVal().compareTo(new BigDecimal("0")) != 0) {
            Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVendeeId()), Long.valueOf(vo.getVenderId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
            if (ca != null) {
                //授信
                caAccService.accredit(new CaAcc(0L, ca.getCaId(), "PRC", vo.getVendeeId(), vo.getDocNum()), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()), ca, vo.getAccVal(), new BigDecimal("0"), true);
            }
        }

        //创建采购商的出库任务
        if (this.contract(vo) && (StringUtils.isNotEmpty(vo.getSrcDocType()) ? !vo.getSrcDocType().equals("RGO") : true)
                && StringUtils.NullEmpty(vo.getStRtcNum()) && vo.getProgress().equals("CK")) {
            rtcMapper.updateBystate(vo);
            //出库任务
            purchaseUtils.outStock("PRC", vo.getRtcNum(), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }

        //将退购合同以及退销合同的审核状态信息补充
        prcMapper.updateByChkr(user.getPrsnl().getPrsnlId() + "", vo.getRtcNum());
        if (vo.getSrcGen().equals("T") && StringUtils.isNotEmpty(vo.getPrcNum())) {
            vo.setDocNum(srcMapper.selectByOnly(vo).getSrcNum());
            //通知退销合同审核事件
            return srcService.noticeExamine(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
//            srcMapper.updateByChkr(user.getPrsnl().getPrsnlId()+"",vo.getRtcNum());
        }

        return rtcMapper.updateBystate(vo);
    }

    /**
     * 退销合同通知退购合同审核事件
     *
     * @param vo
     * @return
     */
    @Override
    public int noticeExamine(PrcVo vo, SysUser user) throws Exception {
        if (vo.getPrcAutoChk().equals("T") && vo.getProgress().equals("RK")) {
            return this.examine(vo, user);
        } else {
            if (this.contract(vo) && vo.getProgress().equals("CK")) {
                //登记出库任务
                purchaseUtils.outStock("PRC", vo.getRtcNum(), user);
            }
        }
        if (vo.getProgress().equals("CK") && StringUtils.isNotEmpty(vo.getStUnitId() + "") &&
                parameterMapper.findById("SALES_RETURN_CONTRACT_AUTO_FORWARD").getParmVal().equals("T")) {
            //自动转退销并审核  预留

        }
        return rtcMapper.updateBystate(vo);
    }

    /**
     * 单据反审
     *
     * @param vo
     * @return
     */
    @Override
    public int reiterate(PrcVo vo, SysUser user) throws Exception {
        //判断重审后进度的状态
        if (vo.getProgress().equals("CK") && vo.getSrcGen().equals("T")) {
            vo.setProgress("RK");
        } else {
            vo.setProgress("C");
        }
        vo.setEffective("F");
        //公共方法
        this.publicMethod(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));

        prcMapper.updateByChkr(null, vo.getRtcNum());
        if (this.contract(vo)) {
            //删除出库
            warehDelivTaskService.delDelivTask(new WarehDelivTask(null, "PRC", vo.getVendeeId(), vo.getDocNum()),user);
        }
        //判断是否创建出入库任务  有的话就删除
        if (vo.getSrcGen().equals("T") && StringUtils.isNotEmpty(vo.getPrcNum())) {
            //查询退销合同号并赋值
            vo.setDocNum(srcMapper.selectByRtcNum(vo.getRtcNum()).getSrcNum());
            return srcService.noticeReiterate(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        }
        return rtcMapper.updateBystate(vo);
    }

    /**
     * 通知重审事件
     *
     * @param vo
     * @return
     */
    @Override
    public int noticeReiterate(PrcVo vo, SysUser user) throws Exception {
//        if (vo.getProgress().equals("EK") && StringUtils.isNotEmpty(vo.getStRtcNum()) &&
//                parameterMapper.findById("SALES_RETURN_CONTRACT_AUTO_FORWARD").getParmVal().equals("T")) {
//            //根据始发单据编号获取退销合同编号 。如果退销合同未作废，则作废退销合同
//        }
        if (vo.getPrcAutoChk().equals("T") && vo.getProgress().equals("EK")) {
            return this.reiterate(vo, user);
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
        if(vo.getSrcGen().equals("T")){
            this.srcSu(vo.getRtcNum(),vo.getSuspended());
        }
        return prcMapper.updateByPrcVo(vo);
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
        if(vo.getSrcGen().equals("T")){
            this.srcSu(vo.getRtcNum(),vo.getSuspended());
        }
        return prcMapper.updateByPrcVo(vo);
    }

    /**
     * 操作退销合同挂起
     * @param rtcNum
     * @param suspended
     */
    public void srcSu(String rtcNum, String suspended){
        Src src = srcMapper.selectByRtcNum(rtcNum);
        if(src!=null){
            src.setSuspended(suspended);
            srcMapper.updateByPrimaryKeySelective(src);
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
        this.doClose(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        if (vo.getSrcGen().equals("T")) {
            //调用退销合同退货方关闭通知
            srcService.vendeeClosed(srcMapper.selectByOnly(new PrcVo(vo.getRtcNum())), new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
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
        this.doReuse(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        if (vo.getSrcGen().equals("T")) {
            //调用退销合同退货方重用通知
            srcService.vendeeReused(srcMapper.selectByOnly(new PrcVo(vo.getRtcNum())), new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
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
        this.doAbolish(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        if (vo.getSrcGen().equals("T")) {
            //调用退销合同退货方作废通知
            srcService.vendeeAbolished(srcMapper.selectByOnly(new PrcVo(vo.getRtcNum())), new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        }
        return rtcMapper.updateBystate(vo);
    }

    /**
     * 作废事件
     *
     * @param vo
     * @param user
     */
    private void doAbolish(PrcVo vo, SysUser user) throws Exception {
        this.publicMethod(vo, user);
        if (this.contract(vo)) {
            //删除出库
            warehDelivTaskService.delDelivTask(new WarehDelivTask(null, "PRC", vo.getVendeeId(), vo.getPrcNum()),user);
        }
    }

    /**
     * 重用事件
     *
     * @param vo
     * @param user
     */
    private void doReuse(PrcVo vo, SysUser user) throws Exception {
        if (this.contract(vo)) {
            //合同明细
            String sql = " ,CASE WHEN qty-deliv_qty IS NULL THEN qty ELSE qty-deliv_qty END qty " +
                    " ,CASE WHEN val-deliv_val IS NULL THEN val ELSE val-deliv_val END val ";
            List<RtcDtlVo> dtlList = rtcDtlMapper.selectByBill(vo.getRtcNum(), sql);
            this.publicMethodT(vo, dtlList, user);
        }

        //监管往来
        if (purchaseService.judgeRtc(vo.getRtcNum()) && vo.getAccEnabled().equals("T") && vo.getRtnAccSite().equals("CK")
                && !vo.getVdeFsclUnitId().equals(vo.getVenderId()) && vo.getAccVal().compareTo(new BigDecimal("0")) != 0) {
            Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId())));
            if (ca != null) {
                BigDecimal debitValue = new BigDecimal("0");
                if (vo.getTtlVal().compareTo(debitValue) != 0 && vo.getTtlDelivVal().compareTo(debitValue) != 0 && vo.getAccVal().compareTo(debitValue) != 0) {
                    debitValue = (vo.getTtlVal().subtract(vo.getTtlDelivVal())).multiply(vo.getAccVal().divide(vo.getTtlVal()));
                }
                //授信
                caAccService.accredit(new CaAcc(0L, ca.getCaId(), "PRC", vo.getUnitId(), vo.getPrcNum()), user, ca, debitValue, new BigDecimal("0"), true);
            }
        }
    }

    /**
     * 关闭事件
     *
     * @param vo
     * @param user
     */
    private void doClose(PrcVo vo, SysUser user) throws Exception {
        this.publicMethod(vo, user);
        if (this.contract(vo)) {
            //删除出库
            warehDelivTaskService.delDelivTask(new WarehDelivTask(null, "PRC", vo.getVendeeId(), vo.getDocNum()),user);
        }
    }

    //删除
    @Override
    public int deleteByPrc(PrcVo vo) {
        return prcMapper.deleteByRtcNum(vo.getRtcNum());
    }

    /**
     * 取消关联
     *
     * @param rtcNum
     * @param user
     */
    @Override
    public void relation(String rtcNum, SysUser user) throws Exception {
        Rtc rtc = rtcMapper.selectByPrimaryKey(rtcNum);
        if (rtc != null) {
            if (rtc.getEffective().equals("T")) {
                //登记出库任务
                purchaseUtils.generate("PRC", rtcNum, user);
            }
        }
    }

    /**
     * 退销合同中确认生成退购合同
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertByPrc(PrcVo vo, SysUser user) {
        Prc prc = new Prc();
        //通过rtcType查找srcType
        PrcType prcType = prcTypeMapper.selectByRtcType(vo.getRtcType());
        if (prcType != null) {
            prc.setPrcType(prcType.getPrcType());
        }
        prc.setRtcNum(vo.getRtcNum());
        prc.setUnitId(vo.getVendeeId());
        prc.setPrBrdgMode(prcType.getPrBrdgMode());
        prc.setSuspended("F");
        prc.setAcReqd("F");
        prc.setAcBnd("F");
        prc.setSrFwdd("F");
        //生成销售编号
        //通过系统参数PS_DOCUMENT_UNIQUED_NUMBERED去生成购销合同编号或者采购合同编号
        SysParameter sys = parameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        if (sys.getParmVal().equals("T")) {
            prc.setPrcNum(vo.getRtcNum());
        } else {
            String prcNum = sysRefNumService.viceNum(user, "PRC_NUM");
            prc.setPrcNum(prcNum);
        }
        return prcMapper.insertSelective(prc);
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
        PrcVo prc = prcMapper.selectByOnly(vo);
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
                if (vo.getProgress().equals("PG") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("确认", "T", confirm));
                } else {
                    list.add(new OperationVo("确认", "F", confirm));
                }
            }
            if (("redo").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("C") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")
                        && StringUtils.NullEmpty(vo.getItmdRtcNum()) && StringUtils.NullEmpty(vo.getExecRtcNum())) {
                    list.add(new OperationVo("重做", "T", redo));
                } else {
                    list.add(new OperationVo("重做", "F", redo));
                }
            }
            if (("check").equals(pur.getOptionValue())) {
                if ((vo.getProgress().equals("C") || vo.getProgress().equals("RK")) && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("审核", "T", examine));
                } else {
                    list.add(new OperationVo("审核", "F", examine));
                }
            }
            if (("uncheck").equals(pur.getOptionValue())) {
                if ((vo.getProgress().equals("EK") || vo.getProgress().equals("CK")) && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F") && vo.getTasksInDeliv()==0 && vo.getTasksInRcv()==0
                        && dtlList.isEmpty()) {
                    list.add(new OperationVo("反审", "T", reiterate));
                } else {
                    list.add(new OperationVo("反审", "F", reiterate));
                }
            }
            if (("suspend").equals(pur.getOptionValue())) {
                if ((!vo.getProgress().equals("DD") || !vo.getProgress().equals("RD")) && vo.getSuspended().equals("F")
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
                        && vo.getCancelled().equals("F") && vo.getTasksInDeliv()==0
                        && vo.getTasksInRcv()==0) {
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

    //取消未决公共方法
    private int cancel(PrcVo vo, Map<String, Object> map) {
        String sql = " ,qty ";
        List<RtcDtlVo> dtlList = rtcDtlMapper.selectByBill(vo.getRtcNum(), sql);
        if (dtlList != null && dtlList.size() > 0) {
            map.put("docType", "PUC");
            map.put("warehId", vo.getVdeWarehId());
            map.put("dtlList", dtlList);
            return warehStkPgMapper.deleteByDtl(map);
        }
        return 0;
    }

    //判断不是居间合同
    @Override
    public boolean contract(PrcVo vo) {
        boolean flag = false;
        if (vo.getIsSrItmd().equals("F") && vo.getIsPrItmd().equals("F")) {
            flag = true;
        }
        return flag;
    }

    /**
     * 退销合同通知同构合同供应商关闭事件
     *
     * @param vo
     * @param user
     */
    @Override
    public void venderClosed(PrcVo vo, SysUser user) throws Exception {
        this.doClose(vo, user);
    }

    /**
     * 退销合同通知退购合同供应商重用事件
     *
     * @param vo
     * @param user
     * @throws Exception
     */
    @Override
    public void venderReused(PrcVo vo, SysUser user) throws Exception {
        this.doReuse(vo, user);
    }

    /**
     * 退销合同通知退购合同供应商作废事件
     *
     * @param vo
     * @param user
     */
    @Override
    public void venderAbolished(PrcVo vo, SysUser user) throws Exception {
        this.doAbolish(vo, user);
    }

    /**
     * 查询单条记录
     *
     * @param vo
     * @return
     */
    @Override
    public PrcVo selectByOnly(PrcVo vo) {
        return prcMapper.selectByOnly(vo);
    }

    //重审/关闭公共方法
    private void publicMethod(PrcVo vo, SysUser user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("unitId", vo.getVendeeId());
        map.put("docNum", vo.getDocNum());
        //非居间合同
        if (this.contract(vo)) {
            if (vo.getRtnaInvd().equals("T")) {
                Psa psa = psaMapper.selectByVdr(new Psa(vo.getVenderId(), vo.getVendeeId()));
                if (psa != null) {
                    if (psa.getRtnCtrl().equals("Q")) {
                        //按数量
                        String sql = " ,qty ,val";
                        List<RtcDtlVo> dtlList = rtcDtlMapper.selectByBill(vo.getRtcNum(), sql);
                        rtqQtaService.defreeze(psa, dtlList, vo, "PRC", map);
                    } else if (psa.getRtnCtrl().equals("V")) {
                        //按金额
                        rtvQtaService.defreeze(psa, vo, "PRC", map);
                    }
                }
            }

            //取消未决库存
            List<String> stkList = new ArrayList<>();
            stkList.add("BK");
            map.put("stkList", stkList);
            this.cancel(vo, map);
        }

        //监管往来
        if (purchaseService.judgeRtc(vo.getRtcNum()) && !vo.getVdeFsclUnitId().equals(vo.getVenderId())) {
            //取消授信
            caAccService.revoke(new CaAcc(0L, 0L, "PRC", vo.getVendeeId(), vo.getDocNum()), user);
        }
    }

    //审核/重用公共方法
    private void publicMethodT(PrcVo vo, List<RtcDtlVo> dtlList, SysUser user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("unitId", vo.getVendeeId());
        map.put("docNum", vo.getDocNum());
        //获取退货控制类别去冻结相关数据
        if (vo.getRtnaInvd().equals("T")) {
            Psa psa = psaMapper.selectByVdr(new Psa(vo.getVenderId(), vo.getVendeeId()));
            if (psa != null) {
                if (psa.getRtnCtrl().equals("Q")) {
                    //按数量
                    rtqQtaService.freeze(psa, dtlList, vo, "PRC", map, true);
                } else if (psa.getRtnCtrl().equals("V")) {
                    //按金额
                    rtvQtaService.freeze(psa, dtlList, vo, "PRC", map, true);
                }
            }
        }
        //登记未决  获取单据明细
        if (dtlList != null && dtlList.size() > 0) {
            map.put("stkType", "BK");
            map.put("docType", "PRC");
            map.put("warehId", vo.getVdeWarehId());
            map.put("dtlList", dtlList);
            warehStkPgMapper.insertByDtl(map);
        }
    }

    /**
     * 出入库明细查询
     *
     * @param vo
     * @return
     */
    @Override
    public PrcVo selectObject(PrcVo vo) {
        return prcMapper.selectObject(vo);
    }

    /**
     * 通过退货合同号查询固定单价
     *
     * @param rtcType
     * @return
     */
    @Override
    public Map<String, Object> getFixedPrice(String rtcType) {
        Map<String, Object> map = new HashMap<>();
        RtcType type = rtcTypeMapper.selectByPrimaryKey(rtcType);
        if (type != null) {
            map.put("fixedUnitPrice", type.getFixedUnitPrice());//是否固定单价
        }
        return map;
    }

    /**
     * 退购合同开始发货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @return
     */
    @Override
    public int startDeliver(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) {
        PrcVo vo = prcMapper.selectObject(new PrcVo(srcDocNum, "", srcDocUnitId + ""));
        if (vo != null) {
            if (vo.getProgress().equals("CK") && vo.getSuspended().equals("F")
                    && vo.getCancelled().equals("F")) {
                if (vo.getMultiImpl().equals("F")) {
                    vo.setProgress("DG");
                } else {
                    vo.setProgress("CK");
                }
                vo.setTasksInDeliv((org.springframework.util.StringUtils.isEmpty(vo.getTasksInDeliv()) ? vo.getTasksInDeliv() : 0) + 1);
                //非居间合同
                if (this.contract(vo) && vo.getMultiImpl().equals("F")) {
                    //撤销出库任务
                    warehDelivTaskService.delDelivTask(new WarehDelivTask(null, "PRC", vo.getVendeeId(), vo.getPrcNum()),user);
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
     * 退购合同终止发货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int stopDeliver(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        PrcVo vo = prcMapper.selectObject(new PrcVo(srcDocNum, "", srcDocUnitId + ""));
        if (vo != null) {
            if ((vo.getProgress().equals("DG") || (vo.getMultiImpl().equals("T") && vo.getProgress().equals("CK")
                    && vo.getTasksInDeliv() > 0)) && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                vo.setProgress("CK");
                vo.setTasksInDeliv(vo.getTasksInDeliv() - 1);
                //非居间合同
                if (this.contract(vo) && vo.getMultiImpl().equals("F")) {
                    //出库任务
                    purchaseUtils.outStock("PRC", vo.getRtcNum(), user);
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
     * 退购合同发货
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
    public int deliver(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        PrcVo vo = prcMapper.getPrcByStb(srcDocUnitId, srcDocNum, docUnitId, docNum, true);
        //单据信息
        Stb stb = stbService.getStbByPk(new Stb(docUnitId, docNum));
        if (vo != null) {
            if ((vo.getProgress().equals("DG") || (vo.getMultiImpl().equals("T") && vo.getProgress().equals("CK")))
                    && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                if (vo.getMultiImpl().equals("T")) {
                    vo.setProgress("CK");
                } else {
                    vo.setProgress("DD");
                }
                //通过sql语句查询更新明细
                List<RtcDtl> dtlList = null;
                if (vo.getSrcGen().equals("T")) {
                    dtlList = rtcDtlMapper.selectByStbDtl(vo.getRtcNum(), docUnitId, docNum, true, "PRC");
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
                    pucService.returnDeliver(vo.getPscNum(), docUnitId, docNum);
                }
                //非居间合同
                if (this.contract(vo)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("unitId", vo.getVendeeId());
                    map.put("docNum", vo.getDocNum());
                    //退货控制类别
                    if (vo.getRtnaInvd().equals("T")) {
                        Psa psa = psaMapper.selectByVde(new Psa(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId())));
                        if (psa != null) {

                            if (psa.getRtnCtrl().equals("Q")) {
                                List<RtcDtlVo> rtqList = rtcDtlMapper.selectByBillQty(docUnitId, docNum);
                                //按数量
                                if (vo.getMultiImpl().equals("T")) {
                                    //减少冻结
                                    rtqQtaService.freeze(psa, rtqList, vo, "PRC", map, false);
                                } else {
                                    //取消冻结
                                    rtqQtaService.defreeze(psa, rtqList, vo, "PRC", map);
                                }
                                rtqQtaService.consume(psa, rtqList, vo, "PRC", true);
                            } else if (psa.getRtnCtrl().equals("V")) {
                                List<RtcDtlVo> rtqList = rtcDtlMapper.selectByBillVal(docUnitId, docNum);
                                //按金额
                                if (vo.getMultiImpl().equals("T")) {
                                    //减少冻结
                                    rtvQtaService.freeze(psa, rtqList, vo, "PRC", map, false);
                                } else {
                                    //取消冻结
                                    rtvQtaService.defreeze(psa, vo, "PRC", map);
                                }
                                rtvQtaService.consume(psa, rtqList, vo, "PRC", true);
                            }
                        }
                    }
                    //登记未决
                    map.put("stkType", "BK");
                    map.put("docType", "PRC");
                    map.put("warehId", vo.getVdeWarehId());
                    if (vo.getMultiImpl().equals("T")) {
                        map.put("dtlList", stbDtlVo);
                        map.put("flag", "stb_j");
                        warehStkPgMapper.updateByNegDtl(map);
                    } else {
                        //删除
                        List<String> stkList = new ArrayList<>();
                        stkList.add("BK");
                        map.put("stkList", stkList);
                        map.put("dtlList", dtlList);
                        warehStkPgMapper.deleteByDtl(map);
                    }
                }

                //判断监管往来
                if (purchaseService.judgeRtc(vo.getRtcNum()) && vo.getAccEnabled().equals("T") && vo.getRtnAccSite().equals("CK")
                        && !vo.getVdeFsclUnitId().equals(vo.getVenderId())) {
                    Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVendeeId()), Long.valueOf(vo.getVenderId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                    if (ca != null) {
                        if (vo.getMultiImpl().equals("T")) {
                            //负数登记
                            BigDecimal debitValue = new BigDecimal("0");
                            if (vo.getTtlVal().compareTo(debitValue) != 0) {
                                debitValue = stb.getTtlVal().multiply(vo.getAccVal().divide(vo.getTtlVal()));
                            }
                            //授信
                            caAccService.accredit(new CaAcc(0L, ca.getCaId(), "PRC", vo.getVendeeId(), vo.getDocNum()), user, ca, vo.getAccVal(), new BigDecimal("0"), false);
                        } else {
                            //注销
                            caAccService.revoke(new CaAcc(0L, 0L, "PRC", vo.getVendeeId(), vo.getDocNum()), user);
                        }
                    }
                }

                vo.setTasksInDeliv(vo.getTasksInDeliv() - 1);
                if (vo.getSrcGen().equals("T")) {
                    //通知发货事件
                    rtcMapper.updateByPrcVo(vo);
                    srcService.vendeeDelivered(srcService.selectByOnly(new PrcVo(vo.getRtcNum())), stb, user);
                } else {
                    vo.setTtlRcvQty(vo.getTtlDelivQty());
                    vo.setTtlRcvBox(vo.getTtlDelivBox());
                    vo.setTtlRcvVal(vo.getTtlDelivVal());
                    vo.setTtlRcvTax(vo.getTtlDelivTax());
                    vo.setTtlRcvMkv(vo.getTtlDelivMkv());
                }
                //判断是否直接关闭
                int closeType = 0;
                //关闭当前销售合同
                SysParameter sysPa = parameterMapper.findById("RTC_AUTO_CLOSE_WHEN_EXPECTATION_REACHED");
                if (vo.getSrcGen().equals("F") && vo.getMultiImpl().equals("T") && sysPa.getParmVal().equals("T")
                        && vo.getTasksInRcv() == 0 && rtcDtlMapper.selectByOperation(vo.getRtcNum(), "deliv_qty < qty").isEmpty()) {
                    closeType = this.close(vo, user);
                    if (closeType == 1) {
                        //更新状态避免最后更新的时候状态不对
                        vo.setProgress("CL");
                    }
                }

                if (vo.getMultiImpl().equals("T") && closeType == 0) {
                    if (!rtcDtlMapper.selectByOperation(vo.getRtcNum(), " deliv_qty < qty ").isEmpty()) {
                        //撤销入库任务
                        warehDelivTaskMapper.deleteByBill(new WarehDelivTask(Long.valueOf(vo.getVendeeId() + ""), "PRC", Long.valueOf(vo.getUnitId() + ""), vo.getPrcNum()));
                    } else {
                        rtcMapper.updateByPrcVo(vo);
                        //登记出库任务
                        purchaseUtils.outStock("PRC", vo.getRtcNum(), user);
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
     * 退购合同取消发货
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
    public int reverseDeliver(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        PrcVo vo = prcMapper.getPrcByStb(srcDocUnitId, srcDocNum, docUnitId, docNum, false);
        //单据信息
        Stb stb = stbService.getStbByPk(new Stb(docUnitId, docNum));
        if (vo != null) {
            if ((vo.getProgress().equals("DD") || (vo.getMultiImpl().equals("T") && vo.getProgress().equals("CK")))
                    && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                if (vo.getMultiImpl().equals("T")) {
                    vo.setProgress("CK");
                } else {
                    vo.setProgress("DG");
                }
                //通过sql语句查询更新明细
                List<RtcDtl> dtlList = null;
                if (vo.getSrcGen().equals("T")) {
                    dtlList = rtcDtlMapper.selectByStbDtl(vo.getRtcNum(), docUnitId, docNum, false, "PRC");
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
                    pucService.reverseReturnDeliver(vo.getPscNum(), docUnitId, docNum);
                }
                //非居间合同
                if (this.contract(vo)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("unitId", vo.getVendeeId());
                    map.put("docNum", vo.getDocNum());
                    //退货控制类别
                    if (vo.getRtnaInvd().equals("T")) {
                        Psa psa = psaMapper.selectByVde(new Psa(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId())));
                        if (psa != null) {
                            List<RtcDtlVo> rtqList = null;
                            if (psa.getRtnCtrl().equals("Q")) {
                                //按数量
                                //增加冻结
                                if (vo.getMultiImpl().equals("T")) {
                                    rtqList = rtcDtlMapper.selectByBillQty(docUnitId, docNum);
                                } else {
                                    rtqList = rtcDtlMapper.selectByBill(vo.getRtcNum(), " ,( rd.qty - rd.deliv_qty ) AS qty  ");
                                }
                                rtqQtaService.freeze(psa, rtqList, vo, "PRC", map, true);
                                rtqQtaService.consume(psa, rtqList, vo, "PRC", false);
                            } else if (psa.getRtnCtrl().equals("V")) {
                                //按金额
                                //增加金额
                                if (vo.getMultiImpl().equals("T")) {
                                    rtqList = rtcDtlMapper.selectByBillQty(docUnitId, docNum);
                                } else {
                                    rtqList = rtcDtlMapper.selectByBill(vo.getRtcNum(), " ,( rd.val - rd.deliv_val ) AS val  ");
                                }
                                rtvQtaService.freeze(psa, rtqList, vo, "PRC", map, true);
                                rtvQtaService.consume(psa, rtqList, vo, "PRC", false);
                            }
                        }
                    }

                    //未决库存
                    //登记未决
                    map.put("stkType", "BK");
                    map.put("docType", "PRC");
                    map.put("warehId", vo.getVdeWarehId());
                    map.put("flag", "stb_a");
                    if (vo.getMultiImpl().equals("T")) {
                        map.put("dtlList", stbDtlVo);
                    } else {
                        map.put("dtlList", dtlList);
                    }
                    warehStkPgMapper.updateByNegDtl(map);
                }

                //判断监管往来
                if (purchaseService.judgeRtc(vo.getRtcNum()) && vo.getAccEnabled().equals("T") && vo.getRtnAccSite().equals("CK")
                        && !vo.getVdeFsclUnitId().equals(vo.getVenderId())) {
                    Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVendeeId()), Long.valueOf(vo.getVenderId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                    if (ca != null) {
                        BigDecimal debitValue = new BigDecimal("0");
                        if (vo.getMultiImpl().equals("T")) {
                            if (vo.getTtlVal().compareTo(debitValue) != 0) {
                                debitValue = stb.getTtlVal().multiply(vo.getAccVal().divide(vo.getTtlVal()));
                            }
                        } else {
                            debitValue = vo.getAccVal();
                        }
                        //授信
                        caAccService.accredit(new CaAcc(0L, ca.getCaId(), "PRC", vo.getVendeeId(), vo.getDocNum()), user, ca, vo.getAccVal(), new BigDecimal("0"), true);
                    }
                }
                vo.setTasksInDeliv(vo.getTasksInDeliv() + 1);
                if (vo.getSrcGen().equals("T")) {
                    //通知取消发货事件
                    srcService.vendeeReverseDelivered(srcService.selectByOnly(new PrcVo(vo.getRtcNum())), stb, user);
                }else{
                    vo.setTtlRcvQty(vo.getTtlDelivQty());
                    vo.setTtlRcvBox(vo.getTtlDelivBox());
                    vo.setTtlRcvVal(vo.getTtlDelivVal());
                    vo.setTtlRcvTax(vo.getTtlDelivTax());
                    vo.setTtlRcvMkv(vo.getTtlDelivMkv());
                }

                //判断是否登记出库任务
                if (vo.getMultiImpl().equals("T")) {
                    rtcMapper.updateByPrcVo(vo);
                    purchaseUtils.outStock("PRC", vo.getRtcNum(), user);
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
