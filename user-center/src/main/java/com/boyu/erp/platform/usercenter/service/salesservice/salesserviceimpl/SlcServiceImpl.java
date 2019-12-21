package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.mq.order.OrderAdd;
import com.boyu.erp.platform.usercenter.entity.mq.order.OrderItemsToB;
import com.boyu.erp.platform.usercenter.entity.purchase.*;
import com.boyu.erp.platform.usercenter.entity.sales.RgoType;
import com.boyu.erp.platform.usercenter.entity.sales.Slc;
import com.boyu.erp.platform.usercenter.entity.sales.SlcType;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.CaMapper;
import com.boyu.erp.platform.usercenter.mapper.basic.CompanyMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.*;
import com.boyu.erp.platform.usercenter.mapper.sales.SlaMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysDomainMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehDelivTaskMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehStkPgMapper;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.base.RabbitTemplateSerivce;
import com.boyu.erp.platform.usercenter.service.base.SendSerivce;
import com.boyu.erp.platform.usercenter.service.caservice.CaAccService;
import com.boyu.erp.platform.usercenter.service.common.impl.ButtonCommonService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscDtlService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PucService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl.PucServiceImpl;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoService;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcService;
import com.boyu.erp.platform.usercenter.service.system.RtqQtaService;
import com.boyu.erp.platform.usercenter.service.system.RtvQtaService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehDelivTaskService;
import com.boyu.erp.platform.usercenter.utils.RandomStringUtils;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.PurchaseUtils;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SlcServiceImpl
 * @Description TODO
 * @Date 2019/8/9 11:14
 * @Created by wz
 */
@Slf4j
@Service
@Transactional
public class SlcServiceImpl implements SlcService {
    static final String YW = "ER_";

    //销售合同模块按钮参数
    static final String parameter = "slcButton";

    //确认
    static final String confirm = "/sales/slc/confirm";
    //重做
    static final String redo = "/sales/slc/redo";
    //审核
    static final String examine = "/sales/slc/examine";
    //反审
    static final String reiterate = "/sales/slc/reiterate";
    //挂起
    static final String hangUp = "/sales/slc/hangUp";
    //恢复
    static final String recovery = "/sales/slc/recovery";
    //作废
    static final String toVoid = "/sales/slc/toVoid";
    //关闭
    static final String close = "/sales/slc/close";
    //重用
    static final String reusing = "/sales/slc/reusing";
    //导入原始单据
    static final String importBill = "/sales/slc/importBill";

    @Autowired
    private UsersService usersService;
    @Autowired
    private PscDtlMapper pscDtlMapper;
    @Autowired
    private SlcMapper slcMapper;
    @Autowired
    private SlcTypeMapper slcTypeMapper;
    @Autowired
    private SysParameterMapper sysParameterMapper;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private PscDtlService pscDtlService;
    @Autowired
    private PscMapper pscMapper;
    @Autowired
    private PucServiceImpl pucServiceimpl;
    @Autowired
    private PucService pucService;
    @Autowired
    private PurchaseUtils purchaseUtils;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private PucMapper pucMapper;
    @Autowired
    private WarehDelivTaskMapper warehDelivTaskMapper;
    @Autowired
    private WarehStkPgMapper warehStkPgMapper;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private CaMapper caMapper;
    @Autowired
    private CaAccService caAccService;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private SendSerivce sendSerivce;
    @Autowired
    private ButtonCommonService buttonCommonService;
    @Autowired
    private SlaMapper slaMapper;
    @Autowired
    private PsaMapper psaMapper;
    @Autowired
    private StbService stbService;
    @Autowired
    private RgoService rgoService;
    @Autowired
    private StbDtlService stbDtlService;
    @Autowired
    private SysDomainMapper sysDomainMapper;
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
    public PageInfo<PscVo> selectAll(PscVo vo, Integer page, Integer size, SysUser user) throws Exception {
        List<PscVo> list = null;
        /**
         * 系统管理员
         * */
        if (false) {
            PageHelper.startPage(page, size);
            list = slcMapper.selectALL(vo);
        } else {
            PageHelper.startPage(page, size);
            list = slcMapper.selectByUnit(vo);
        }
        PageInfo<PscVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 采购合同中新增销售合同
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertByPuc(PscVo vo, SysUser user) {
        Slc slc = new Slc();
        //通过pscType获取slcType
        SlcType slcType = slcTypeMapper.selecyByPscType(vo.getPscType());
        if (slcType != null) {
            slc.setSlcType(slcType.getSlcType());
            slc.setSlBrdgMode(slcType.getSlBrdgMode());
        }
        slc.setSuspended("F");
        slc.setPuCtrl("I");
        slc.setPuFwdd("F");
        slc.setAfBnd("F");
        slc.setAfReqd("F");
        slc.setPscNum(vo.getPscNum());
        slc.setUnitId(Integer.valueOf(vo.getVenderId()));
        //生成销售编号
        //通过系统参数PS_DOCUMENT_UNIQUED_NUMBERED去生成购销合同编号或者采购合同编号
        SysParameter sys = sysParameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        if (sys.getParmVal().equals("T")) {
            slc.setSlcNum(vo.getPscNum());
        } else {
            String slcNum = sysRefNumService.viceNum(user, "SLC_NUM");
            slc.setSlcNum(slcNum);
        }
        return slcMapper.insertSelective(slc);
    }

    @Override
    public int deleteByPuc(PscVo vo) {
        return slcMapper.deleteByPscNum(vo.getPscNum());
    }

    /**
     * 新建销售合同
     *
     * @param v
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public String insertSlc(PscVo v, SysUser user) throws Exception {
        //通过系统参数PS_DOCUMENT_UNIQUED_NUMBERED去生成购销合同编号或者采购合同编号
        SysParameter sys = sysParameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        SysUser sysU = new SysUser(Long.valueOf(v.getUnitId()), user.getPrsnl().getPrsnlId());
        if (sys.getParmVal().equals("T")) {
            String pscNum = sysRefNumService.mainNum(sysU, "PSC_NUM");
            v.setSlcNum(pscNum);
            v.setPscNum(pscNum);
        } else {
            String slcNum = sysRefNumService.viceNum(sysU, "SLC_NUM");
            String pscNum = sysRefNumService.mainNum(sysU, "PSC_NUM");
            v.setSlcNum(slcNum);
            v.setPscNum(pscNum);
        }
        //判断bas_psc_num是否非空 非空就清除原始单据的数据
        if (v.getBasePscNum() != null && !v.getBasePscNum().equals("")) {
            v.setSrcDocNum("");
            v.setSrcDocUnitId(null);
            v.setSrcDocNum("");
        }

        v.setPuCtrl("I");
        v.setPuFwdd("F");
        //更新采购商状态
        v = this.updateSlcVo(v);

        v.setEffective("F");
        v.setCancelled("F");
        v.setRenewed("F");
        v.setSuspended("F");
        v.setProgress("PG");

        v.setSlcGen("T");//默认销售合同已生成
        v.setSlcAutoGen("T");
        v.setVdrInvd("T");//默认供应商介入

        v.setOprId(user.getPrsnl().getPrsnlId() + "");

        if (v.getPscDtlList() != null && v.getPscDtlList().size() > 0) {
            v = pscDtlService.insertByPsc(v, user);
        }

        //保存数据
        pscMapper.insertByPscVo(v);

        slcMapper.insertByPscVo(v);

        return v.getPscNum();
    }

    /**
     * 修改销售合同
     *
     * @param v
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int updateSlc(PscVo v, SysUser user) throws Exception {
        v.setOprId(user.getPrsnl().getPrsnlId() + "");
        //判断bas_psc_num是否非空 非空就清除原始单据的数据
        if (v.getBasePscNum() != null && !v.getBasePscNum().equals("")) {
            v.setSrcDocNum("");
            v.setSrcDocUnitId(null);
            v.setSrcDocNum("");
        }
        //更新采购商状态
        v = this.updateSlcVo(v);

        pucServiceimpl.updateRqdCtrl(v, user);

        pscMapper.updateByPscVo(v);
        return slcMapper.updateByPscVo(v);
    }

    /**
     * 删除销售合同
     *
     * @param
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int deleteSlc(PscVo v, SysUser user) throws Exception {
        //删除slc表数据
        slcMapper.deletePscVo(v.getVenderId(), v.getSlcNum());
        v.setSlcGen("F");

        if (StringUtils.isNotEmpty(v.getStPscNum())) {
            // 如果END_PSC_NUM非空，调用END_PSC_NUM对应销售合同的SalesContractHome.detach方法，取消关联。
            v.setStPscNum("");
            pucService.relation(v.getStPscNum(), new SysUser(Long.valueOf(v.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }

        //如果如果PUC_AUTO_GEN = TRUE && PUC_GEN = TRUE
        //这种情况下，表示当前单据处于已确认未审核状态
        if (v.getPucAutoGen().equals("T") && v.getPucGen().equals("T")) {
            //调用采购合同接口 删除puc表数据
            v.setPucNum(pucMapper.selectByPscNum(v.getPscNum()).getPucNum());
            return pucService.deletePuc(v, new SysUser(Long.valueOf(v.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }

        //判断是否删除psc表
        if (v.getPucGen().equals("F")) {
            //删除购销协议表的时候 删除明细
            pscDtlService.deleteByPuc(v.getPscNum(), user);
            return pscMapper.deleteByPrimaryKey(v.getPscNum());
        }
        return 1;
    }

    /**
     * 通过采购商是否介入 修改相关数据
     * @param v
     * @return
     */
    public PscVo updateSlcVo(PscVo v) {
        if (StringUtils.NullEmpty(v.getVdeInvd())) {
            v.setVdeInvd("F");
        }
        //判断采购商是否介入设置采购相关数据
        if (v.getVdeInvd().equals("T")) {
            v.setPucAutoGen("T");
        } else {
            v.setPucAutoGen("F");
            v.setPucAutoChk("F");
        }
//        //通过采购商id判断是否存在领域 来判定采购商是否介入
//        //查询此采购商是否存在活动领域
//        SysDomain sysDo = sysDomainMapper.selectByPrimaryKey(Long.valueOf(v.getVendeeId()));
//        if (sysDo != null) {
//            if (sysDo.getDomainStatus().equals("A")) {
//                v.setVdeInvd("T");
//                v.setPucAutoGen("T");
//            } else {
//                v.setVdeInvd("F");
//                v.setPucAutoGen("F");
//                v.setPucAutoChk("F");
//            }
//        } else {
//            v.setVdeInvd("F");
//            v.setPucAutoGen("F");
//            v.setPucAutoChk("F");
//        }
        v.setPucGen("F");
        return v;
    }

    /**
     * 取消相应关联
     *
     * @param pscNum
     * @return
     */
    @Override
    public void relation(String pscNum, SysUser user) throws Exception {
        //首先清除PSC.ST_PSC_NUM。
        Psc psc = pscMapper.selectByPrimaryKey(pscNum);
        if (psc != null) {
            if (psc.getEffective().equals("T")) {
                //登记出库任务
                purchaseUtils.outStock("SLC", pscNum, user);
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
    public int confirm(PscVo vo, SysUser user) {
        //将状态改为已确认
        vo.setProgress("C");

        vo.setOprId(user.getPrsnl().getPrsnlId() + "");
        //查询供应商，采购商的会计组织id 存入购销合同
        vo.setVdrFsclUnitId(
                companyMapper.selectByPrimaryKey(Long.valueOf(vo.getVenderId())).getFsclUnitId() + "");
        vo.setVdeFsclUnitId(
                companyMapper.selectByPrimaryKey(Long.valueOf(vo.getVendeeId())).getFsclUnitId() + "");

        //确认的时候生成
        if (vo.getPucGen().equals("F") && vo.getPucAutoGen().equals("T")) {
            vo.setPucGen("T");
            pucService.insertByPuc(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }

        return pscMapper.updateByState(vo);
    }

    /**
     * 重做单据
     *
     * @param vo
     * @return
     */
    @Override
    public int redo(PscVo vo) {
        vo.setProgress("PG");
        vo.setVdeFsclUnitId(null);
        vo.setVdrFsclUnitId(null);
        vo.setTtlExpdRwd(null);
        if (vo.getPucGen().equals("T") && vo.getPucAutoGen().equals("T")) {
            vo.setPucGen("F");
            //删除销售合同
            pucMapper.deleteByPscNum(vo.getPscNum());
        }
        return pscMapper.updateByState(vo);
    }

    /**
     * 审核单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int examine(PscVo vo, SysUser user, int orderType) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //将状态改为已审核状态  并修改采购合同与销售合同审核状态
        if (vo.getProgress().equals("C") && vo.getPucGen().equals("T")) {
            vo.setProgress("RK");
            vo.setEffective("F");
        } else {
            vo.setProgress("CK");
            if (StringUtils.NullEmpty(vo.getBasePscNum())) {
                vo.setEffective("T");
            } else {
                vo.setEffective("F");
            }
        }

        //可能存在原始单据为调配单
        if (StringUtils.isNotEmpty(vo.getSrcDocNum()) && vo.getSrcDocUnitId().equals(vo.getVenderId())) {
            if (vo.getProgress().equals("CK")) {
                rgoService.receivingUnitChecked(vo.getSrcDocUnitId(), vo.getSrcDocNum());
            }
        }

        //非居间合同  登记未决
        if (this.contract(vo)) {
            if (StringUtils.isNotEmpty(vo.getVdrWarehId())) {
                //获取单据明细
                List<PscDtlVo> dtlList = pscDtlMapper.findByPscNum(new PscDtlVo(vo.getPscNum()));
                if (dtlList != null && dtlList.size() > 0) {
                    map.put("stkType", "BK");
                    map.put("docType", "SLC");
                    map.put("unitId", vo.getVenderId());
                    map.put("docNum", vo.getDocNum());
                    map.put("warehId", vo.getVdrWarehId());
                    map.put("dtlList", dtlList);
                    warehStkPgMapper.insertByDtl(map);
                }
            }
        }

        //判断是否监管往来
        if (purchaseService.judgePsc(vo.getPscNum()) && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
            BigDecimal debitValue = new BigDecimal("0");
            if (vo.getMfzEnabled().equals("T") && vo.getPsMfzSite().equals("CK") && vo.getDeposit().compareTo(new BigDecimal("0")) == 1) {
                debitValue = debitValue.add(vo.getDeposit());
            }
            if (vo.getGmEnabled().equals("T")) {
                debitValue = debitValue.add(vo.getGuaMon());
            }
            //冻结金额非0 冻结往来账户余额
            if (debitValue.compareTo(new BigDecimal("0")) == 1) {
                //通过供应商id与采购商id查询是否有往来账户
                Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                if (ca != null) {
                    caAccService.freeze(new CaAcc(0L, 0L, "SLC", Long.valueOf(vo.getVenderId()), vo.getDocNum()), new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()), ca, new BigDecimal("0"), debitValue, true);
                }
            }
        }
        //将销售合同以及采购合同中的审核信息补充
        //当供应商不介入的时候去判断是否创建入库任务
        //介入的时候只创建出库任务
        //入库任务等供应商确认出库之后才会生成
        slcMapper.updateByChkr(user.getPrsnl().getPrsnlId() + "", vo.getPscNum());

        if (this.contract(vo) && vo.getProgress().equals("CK") && vo.getImplByIns().equals("F") && (StringUtils.isNotEmpty(vo.getSrcDocType()) ? !vo.getSrcDocType().equals("RGO") : true)
                && StringUtils.NullEmpty(vo.getStPscNum()) && StringUtils.NullEmpty(vo.getBasePscNum())) {
            pscMapper.updateByState(vo);
            purchaseUtils.outStock("SLC", vo.getPscNum(), new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        }
        //判断是否生成了采购合同
        if (vo.getPucGen().equals("T") && StringUtils.isNotEmpty(vo.getSlcNum())) {
            //调用采购合同的通知审核事件
            vo.setDocNum(pucMapper.selectByPscNum(vo.getPscNum()).getPucNum());
            int type = pucService.noticeExamine(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
            //推送消息
            if(orderType==1){
                //            this.sned(vo);
            }
            return type;
        }
        //推送消息
        if(orderType==1){
            //            this.sned(vo);
        }
        return pscMapper.updateByState(vo);
    }

    //销售合同审核时推送消息+
    private void sned(PscVo vo) {
        OrderAdd add = new OrderAdd();
        add.setId(YW + vo.getSlcNum());
        if(StringUtils.isNotEmpty(vo.getVdeWarehId())){
            add.setShopId(YW + vo.getVdeWarehId());
        }
        add.setOrderSource(2);
        add.setOrderType(1);
        //供货商
        add.setSupplierId(YW + vo.getVdrWarehId());
        List<OrderItemsToB> items = pscDtlMapper.selectByOrderItems(vo.getPscNum());
        add.setItems(items);
        MessageObject msg = new MessageObject("bb.order.add", vo.getPscNum(), add);
        sendSerivce.send("exh.order.bb.up", "order.bb.up", msg);
    }

    /**
     * 销售反审
     *
     * @param vo
     * @return
     */
    @Override
    public int reiterate(PscVo vo, SysUser user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (vo.getProgress().equals("CK") && vo.getPucGen().equals("T")) {
            vo.setProgress("EK");
        } else {
            vo.setProgress("C");
        }
        vo.setEffective("F");

        //非居间合同  取消未决
        if (this.contract(vo)) {
            List<String> stkList = new ArrayList<>();
            stkList.add("BK");
            map.put("stkList", stkList);
            this.cancel(vo, map);
            //判断是否撤销出库任务
            if (vo.getImplByIns().equals("F") && StringUtils.NullEmpty(vo.getBasePscNum())) {
                //撤销出库任务登记
                warehDelivTaskService.delDelivTask(new WarehDelivTask(null, "SLC", Long.valueOf(vo.getVenderId() + ""), vo.getDocNum()), user);
            }
        }

        //判断是否监管往来
        if (purchaseService.judgePsc(vo.getPscNum()) && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
            purchaseService.superviseBySales(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        }

        //删除销售审核信息
        slcMapper.updateByChkr(null, vo.getPscNum());

        if (vo.getPucGen().equals("T") && StringUtils.isNotEmpty(vo.getSlcNum())) {
            //查出采购合同号赋值
            vo.setDocNum(pucMapper.selectByPscNum(vo.getPscNum()).getPucNum());
            return pucService.noticeReiterate(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }

        return pscMapper.updateByState(vo);
    }

    /**
     * 销售合同关闭单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int close(PscVo vo, SysUser user) throws Exception {
        vo.setProgress("CL");
        this.doClose(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        if (vo.getSlcGen().equals("T")) {
            //调用销售合同采购商关闭事件
            pucService.venderClosed(pucMapper.selectByOnly(vo), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }
        return pscMapper.updateByState(vo);
    }

    /**
     * 通知审核事件
     *
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int noticeExamine(PscVo vo, SysUser user) throws Exception {
        //判断原始单据是否调配单
        if ((StringUtils.isNotEmpty(vo.getSrcDocType()) ? vo.getSrcDocType().equals("RGO") : false) && (StringUtils.isNotEmpty(vo.getSrcDocUnitId() + "") ? vo.getSrcDocUnitId().equals(vo.getVenderId()) : false)
                && vo.getProgress().equals("CK")) {
            rgoService.receivingUnitChecked(vo.getSrcDocUnitId(), vo.getSrcDocNum());
        }
        if (vo.getSlcAutoChk().equals("T") && vo.getProgress().equals("EK")) {
            //调用审核
            return this.examine(vo, user, 1);
        } else {
            if (vo.getIsPuItmd().equals("F") && vo.getIsSlItmd().equals("F")
                    && vo.getImplByIns().equals("F") && vo.getProgress().equals("CK") &&
                    (StringUtils.isNotEmpty(vo.getSrcDocType()) ? !vo.getSrcDocType().equals("RGO") : true) && StringUtils.NullEmpty(vo.getStPscNum())
                    && StringUtils.NullEmpty(vo.getBasePscNum())) {
                //登记出库任务
                purchaseUtils.outStock("SLC", vo.getPscNum(), user);
            }
        }
        return pscMapper.updateByState(vo);
    }

    /**
     * 通知反审事件
     *
     * @param vo
     * @param sysUser
     * @return
     * @throws Exception
     */
    @Override
    public int noticeReiterate(PscVo vo, SysUser sysUser) throws Exception {
        if (vo.getSlcAutoChk().equals("T") && vo.getProgress().equals("RK")) {
            return this.reiterate(vo, sysUser);
        } else if (vo.getIsPuItmd().equals("F") && vo.getIsSlItmd().equals("F") &&
                vo.getImplByIns().equals("F")) {
            warehDelivTaskService.delDelivTask(new WarehDelivTask(null, "SLC", Long.valueOf(vo.getVenderId() + ""), vo.getDocNum()), sysUser);
        }
        Psc psc = pscMapper.selectByPrimaryKey(vo.getPscNum());
        //判断原始单据是否调配单 并且审核前状态为CK
        if ((StringUtils.isNotEmpty(vo.getSrcDocType()) ? vo.getSrcDocType().equals("RGO") : false) && (StringUtils.isNotEmpty(vo.getSrcDocUnitId() + "") ? vo.getSrcDocUnitId().equals(vo.getVenderId()) : false)
                && psc.getProgress().equals("CK")) {
            rgoService.receivingUnitUnchecked(vo.getSrcDocUnitId(), vo.getSrcDocNum());
        }
        return pscMapper.updateByState(vo);
    }

    /**
     * 采购商关闭事件
     *
     * @param vo
     * @param user
     */
    @Override
    public void vendeeClosed(PscVo vo, SysUser user) throws Exception {
        //调用销售合同关闭事件
        this.doClose(vo, user);
    }

    /**
     * 销售合同重用单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int reusing(PscVo vo, SysUser user) throws Exception {
        vo.setProgress("CK");
        this.doReuse(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        if (vo.getSlcGen().equals("T")) {
            //调用销售合同采购商关闭事件
            pucService.venderReused(pucMapper.selectByOnly(vo), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }
        return pscMapper.updateByState(vo);
    }

    /**
     * 销售合同挂起单据
     *
     * @param vo
     * @param sysUser
     * @return
     */
    @Override
    public int hangUp(PscVo vo, SysUser sysUser) {
        vo.setSuspended("T");
        if (vo.getPucGen().equals("T")) {
            this.pucSu(vo.getPscNum(), vo.getSuspended());
        }
        return slcMapper.updateByPscVo(vo);
    }

    /**
     * 销售合同恢复单据
     *
     * @param vo
     * @param sysUser
     * @return
     */
    @Override
    public int recovery(PscVo vo, SysUser sysUser) {
        vo.setSuspended("F");
        if (vo.getPucGen().equals("T")) {
            this.pucSu(vo.getPscNum(), vo.getSuspended());
        }
        return slcMapper.updateByPscVo(vo);
    }

    /**
     * 操作采购合同挂起
     *
     * @param pscNum
     * @param suspended
     */
    public void pucSu(String pscNum, String suspended) {
        Puc puc = pucMapper.selectByPscNum(pscNum);
        if (puc != null) {
            puc.setSuspended(suspended);
            pucMapper.updateByPrimaryKeySelective(puc);
        }
    }

    /**
     * 销售合同作废单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int toVoid(PscVo vo, SysUser user) throws Exception {
        vo.setCancelled("T");
        this.doAbolish(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        if (vo.getSlcGen().equals("T")) {
            //调用销售合同中采购商通知作废事件
            pucService.venderAbolished(pucMapper.selectByOnly(vo), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }
        return pscMapper.updateByState(vo);
    }

    /**
     * 采购合同中导入原始单据
     *
     * @param vo
     * @return
     */
    @Override
    public Map<String, Object> importBill(PscVo vo) {
        Map<String, Object> map = new HashMap<>();
        PsxVo psx = slaMapper.selectObject(vo);
        if (psx != null) {
            OptionByPsaVo psa = psaMapper.selectByPsaByVdr(new OptionByPsaVo(psx.getVenderId(), Long.valueOf(psx.getVendeeId())));
            if(psa!=null) {
                map.put("pscNum", vo.getPscNum());
                map.put("pscType", vo.getPscType());
                map.put("vendeeId", psx.getVendeeId());
                map.put("venderId", psx.getVenderId());
                map.put("psaCtlr", psa.getPsaCtlr());
                map.put("vdeInvd", psa.getInte());
                map.put("vdrWarehId", psx.getDelivWarehId());
                map.put("vdeWarehId", psx.getRcvWarehId());
                map.put("pscDtlList", psx.getPsxDtlList());
            }else{
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据的供应商与采购商不存在购销协议！");
            }
        }
        return map;
    }

    /**
     * 采购商通知重用事件
     *
     * @param vo
     * @param user
     */
    @Override
    public void vendeeReused(PscVo vo, SysUser user) throws Exception {
        //调用销售合同重用事件
        this.doReuse(vo, user);
    }

    /**
     * 采购商作废事件
     *
     * @param vo
     * @param user
     */
    @Override
    public void vendeeAbolished(PscVo vo, SysUser user) throws Exception {
        this.doAbolish(vo, user);
    }

    /**
     * 销售合同作废事件
     *
     * @param vo
     * @param user
     */
    public void doAbolish(PscVo vo, SysUser user) throws Exception {
        //判断是否存在调配单的原单
        if ((StringUtils.isNotEmpty(vo.getSrcDocType()) ? vo.getSrcDocType().equals("RGO") : false) && (StringUtils.isNotEmpty(vo.getSrcDocUnitId() + "") ? vo.getSrcDocUnitId().equals(vo.getVenderId()) : false)) {
            //存在就判断调配单未作废
            RgoVo rgo = rgoService.selectByOnly(new RgoVo(vo.getSrcDocUnitId(), vo.getSrcDocNum()));
            if (rgo != null) {
                if (rgo.getCancelled().equals("F")) {
                    throw new ServiceException(JsonResultCode.FAILURE, "当前单据不允许作废");
                }
            }
        }

        if (!vo.getProgress().equals("PG") || !vo.getProgress().equals("C")
                || !vo.getProgress().equals("EK")) {
            Map<String, Object> map = new HashMap<>();
            //取消未决库存登记（预期库存/在途库存）
            List<String> stkList = new ArrayList<>();
            stkList.add("BK");
            map.put("stkList", stkList);
            this.cancel(vo, map);
            //监管往来
            if (purchaseService.judgePsc(vo.getPscNum()) && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
                purchaseService.superviseBySales(vo, user);
            }
            //撤销出库任务登记
            if (vo.getImplByIns().equals("F")) {
                warehDelivTaskService.delDelivTask(new WarehDelivTask(null, "SLC", Long.valueOf(vo.getVenderId() + ""), vo.getDocNum()), user);
            }
        }
    }

    /**
     * 销售合同重用事件
     *
     * @param vo
     * @param user
     */
    private void doReuse(PscVo vo, SysUser user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (this.contract(vo)) {
            String sql = " ,CASE WHEN qty-deliv_qty IS NULL THEN qty ELSE qty-deliv_qty END qty ";
            List<PscDtl> dtlList = pscDtlMapper.selectByBill(vo.getPscNum(), sql);
            if (dtlList != null && dtlList.size() > 0) {
                map.put("docType", "SLC");
                map.put("unitId", vo.getVenderId());
                map.put("docNum", vo.getDocNum());
                map.put("warehId", vo.getVdrWarehId());
                map.put("dtlList", dtlList);
                map.put("stkType", "BK");
                warehStkPgMapper.insertByDtl(map);
            }

            if (vo.getImplByIns().equals("F") && StringUtils.NullEmpty(vo.getStPscNum())) {
                purchaseUtils.outStock("SLC", vo.getPscNum(), user);
            }
        }
        //判断是否监管往来
        if (purchaseService.judgePsc(vo.getPscNum()) && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
            //计算冻结金额
            BigDecimal creditValue = new BigDecimal("0");
            if (vo.getMfzEnabled().equals("T") && vo.getPsMfzSite().equals("CK") && vo.getDeposit().compareTo(creditValue) == 1
                    && vo.getTtlVal().compareTo(creditValue) != 0) {
                creditValue = (vo.getTtlVal().subtract(vo.getTtlDelivVal())).multiply(vo.getDeposit().divide(vo.getTtlVal()));
            }
            if (vo.getGmEnabled().equals("T")) {
                creditValue = creditValue.add(vo.getGuaMon());
            }
            //通过供应商id与采购商id查询是否有往来账户
            Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId())));
            if (ca != null) {
                caAccService.freeze(new CaAcc(0L, 0L, "SLC", Long.valueOf(vo.getVenderId()), vo.getDocNum()), user, ca, new BigDecimal("0"), creditValue, true);
            }
        }

    }

    /**
     * 销售合同关闭事件
     *
     * @param vo
     * @param user
     */
    private void doClose(PscVo vo, SysUser user) throws Exception {
        if (this.contract(vo)) {
            Map<String, Object> map = new HashMap<>();
            //取消未决库存登记（预期库存/在途库存）
            List<String> stkList = new ArrayList<>();
            stkList.add("BK");
            map.put("stkList", stkList);
            this.cancel(vo, map);
        }
        //判断是否监管往来
        boolean flag = purchaseService.judgePsc(vo.getPscNum());
        if (flag && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
            purchaseService.superviseBySales(vo, user);
        }
        //判断是否撤销出库任务
        if (this.contract(vo) && vo.getImplByIns().equals("F")) {
            warehDelivTaskService.delDelivTask(new WarehDelivTask(null, "SLC", Long.valueOf(vo.getVenderId() + ""), vo.getDocNum()), user);
        }

    }

    //判断不是居间合同
    @Override
    public boolean contract(PscVo vo) {
        boolean flag = false;
        if (vo.getIsSlItmd().equals("F") && vo.getIsPuItmd().equals("F")) {
            flag = true;
        }
        return flag;
    }

    //取消未决公共方法
    private void cancel(PscVo vo, Map<String, Object> map) {
        String sql = " , qty ";
        List<PscDtl> dtlList = pscDtlMapper.selectByBill(vo.getPscNum(), sql);
        if (dtlList != null && dtlList.size() > 0) {
            map.put("docType", "SLC");
            map.put("unitId", vo.getVenderId());
            map.put("docNum", vo.getDocNum());
            map.put("warehId", vo.getVdrWarehId());
            map.put("dtlList", dtlList);
            warehStkPgMapper.deleteByDtl(map);
        }
    }

    /**
     * 销售合同分配接口
     *
     * @param vo
     * @param comList
     */
    @Override
    public void admeasure(PscVo vo, List<CommonDtl> comList) {
        if (vo.getImplByIns().equals("T") && vo.getProgress().equals("CK")
                && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
            this.doAdmeasure(vo, comList, false);
        }
    }

    /**
     * 销售合同取消分配接口
     *
     * @param vo
     * @param comList
     * @return
     */
    @Override
    public void reverseAdmeasure(PscVo vo, List<CommonDtl> comList) {
        if (vo.getImplByIns().equals("T") && vo.getProgress().equals("CK")
                && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
            this.doAdmeasure(vo, comList, true);
        }
    }

    /**
     * 调配单审核时生成销售合同  并确认以及审核
     *
     * @param vo
     * @param user
     */
    @Override
    public String creatSlcByRgo(RgoVo vo, SysUser user, RgoType rgoType) throws Exception {
        //新增单据
        PscVo psc = this.creatSlc(vo, user, rgoType);
        //确认单据
        this.confirm(psc, user);
        //判断是否审核单据
        if (vo.getDestUnitInvd().equals("F") || vo.getDestUnitInvd().equals(vo.getPucAutoGen())) {
            this.examine(slcMapper.selectALL(new PscVo(psc.getPscNum())).get(0), user, 1);
        }
        return psc.getPscNum();
    }

    /**
     * 查询单条
     *
     * @param vo
     * @return
     */
    @Override
    public PscVo selectByOnly(PscVo vo) {
        return slcMapper.selectByOnly(vo);
    }

    /**
     * 调配单审核时 生成销售合同 并确认 审核
     *
     * @param vo
     * @param user
     * @return
     */
    private PscVo creatSlc(RgoVo vo, SysUser user, RgoType rgoType) throws Exception {
        //初始化对象并赋值
        PscVo psc = new PscVo();

        psc.setVdeInvd(vo.getDestUnitInvd());
        psc.setPucAutoGen(vo.getPucAutoGen());
        psc.setPucAutoChk(vo.getPucAutoChk());
        psc.setSlcAutoChk("F");
        psc.setDrDiffJgd(vo.getDrDiffJgd());
        psc.setImplByIns("F");
        psc.setMultiImpl("F");
        psc.setIsSpot("T");
        psc.setPrcSite("CT");
        psc.setRqdCtrl("FR");
        psc.setSplEnabled("F");
        psc.setPlanCtrl("F");
        psc.setIsCms("F");
        psc.setMfzEnabled("T");
        psc.setPsMfzSite("CK");
        psc.setDeposit(vo.getTtlDestVal());
        psc.setGmEnabled("F");
        //默认没设置销售合同类别
        psc.setSlBrdgMode("D");
        psc.setAfReqd("F");
        psc.setAfBnd("F");
        psc.setPuCtrl("I");
        psc.setPuFwdd("F");
        psc.setIsPuItmd("F");
        psc.setSlcAutoGen("F");
        psc.setBxiEnabled("F");

        psc.setUnitId(vo.getUnitId());
        //通过系统参数PS_DOCUMENT_UNIQUED_NUMBERED去生成购销合同编号或者采购合同编号
        SysParameter sys = sysParameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        SysUser sysU = new SysUser(Long.valueOf(vo.getUnitId()), user.getPrsnl().getPrsnlId());
        if (sys.getParmVal().equals("T")) {
            String pscNum = sysRefNumService.mainNum(sysU, "PSC_NUM");
            psc.setSlcNum(pscNum);
            psc.setPscNum(pscNum);
        } else {
            psc.setSlcNum(sysRefNumService.viceNum(sysU, "SLC_NUM"));
            psc.setPscNum(sysRefNumService.mainNum(sysU, "PSC_NUM"));
        }

        //判断调配单中是否有销售类别
        if (StringUtils.isNotEmpty(rgoType.getSlcType())) {
            psc.setSlcType(rgoType.getSlcType());
            //查询退货合同类别  不为空时修改字段属性值
            PscAutoVo pscVo = slcTypeMapper.selectByPscAuto(rgoType.getSlcType());
            //存在退销协议 更新数据
            if (pscVo != null) {
                psc.setPscType(pscVo.getPscType());
                //更新字段值
                psc.setIsSpot(pscVo.getIsSpot());
                psc.setPrcSite(pscVo.getPrcSite());
                psc.setRqdCtrl(pscVo.getRqdCtrl());
                psc.setSplEnabled(pscVo.getSplEnabled());
                psc.setIsCms(pscVo.getIsCms());
                psc.setMfzEnabled(pscVo.getMfzEnabled());
                psc.setPsMfzSite(pscVo.getPsMfzSite());
                psc.setGmEnabled(pscVo.getGmEnabled());
                //默认没设置销售合同类别
                psc.setSlBrdgMode(pscVo.getSlBrdgMode());
                psc.setAfReqd(pscVo.getAfReqd());
                psc.setIsPuItmd(pscVo.getIsPuItmd());
                psc.setSlcAutoGen(pscVo.getSlcAutoGen());
                psc.setPlanCtrl(pscVo.getPlanCtrl());
                psc.setBxiEnabled(pscVo.getBxiEnabled());
            }
        }
        psc.setVendeeId(vo.getDestUnitId() + "");
        psc.setVdeWarehId(vo.getRcvWarehId() + "");
        psc.setPucGen("F");
        psc.setVenderId(vo.getUnitId() + "");
        //中转方不为空就作为供应商仓库
        if (StringUtils.isNotEmpty(vo.getTranWarehId() + "")) {
            psc.setVdrWarehId(vo.getTranWarehId() + "");
        }
        psc.setVdrInvd("T");
        psc.setSlcGen("T");
        psc.setIsSlItmd("F");
        psc.setSrcDocType("RGO");
        psc.setSrcDocUnitId(vo.getUnitId());
        psc.setSrcDocNum(vo.getRgoNum());
        psc.setTtlQty(vo.getTtlQty());
        psc.setTtlBox(vo.getTtlBox());
        psc.setTtlVal(vo.getTtlDestVal());
        psc.setTtlTax(vo.getTtlDestTax());
        psc.setTtlMkv(vo.getTtlDestMkv());
        psc.setOprId(user.getPrsnl().getPrsnlId() + "");
        psc.setEffective("F");
        psc.setCancelled("F");
        psc.setRenewed("F");
        psc.setSuspended("F");
        psc.setProgress("PG");
        psc.setRemarks("调配业务。调出方【" + vo.getSrcUnitNum() + "】" + vo.getSrcUnitCp() + ",【" + vo.getDelivWarehNum() + "】" + vo.getDelivWarehCp()
                + ";调入方【" + vo.getDestUnitNum() + "】" + vo.getDestUnitCp() + ",【" + vo.getRcvWarehNum() + "】" + vo.getRcvWarehCp() + "。");

        slcMapper.insertByPscVo(psc);
        pscMapper.insertByPscVo(psc);
        //生成明细
        pscDtlService.insertByPsc(new PscVo(psc.getPscNum(), psc.getRqdCtrl(), pscDtlMapper.selectDtlByRgo(vo)), user);
        return psc;
    }

    /**
     * 销售合同配货事件
     *
     * @param vo
     * @param comList
     * @param flag
     * @return
     */
    private void doAdmeasure(PscVo vo, List<CommonDtl> comList, boolean flag) {
        if (this.contract(vo)) {
            Map<String, Object> map = new HashMap<>();
            //修改未决库存
            map.put("vo", vo);
            map.put("dtlList", comList);
            map.put("reversed", flag);
            map.put("stkType", "BK");
            map.put("warehId", vo.getVdrWarehId());
            map.put("docType", "SLC");
            warehStkPgMapper.updateByAdmeasure(map);
        }
    }

    /**
     * 查询合同可操作状态
     *
     * @param vo
     * @return
     */
    @Override
    public List<OperationVo> initButtons(PscVo vo) {
        List<PurKeyValue> keyValues = buttonCommonService.initButton(parameter);
        PscVo psc = slcMapper.selectByOnly(vo);
        if (psc == null) {
            throw new ServiceException("403", "单据异常！");
        }
        return creatButton(keyValues, psc);
    }

    public List<OperationVo> creatButton(List<PurKeyValue> keyValues, PscVo vo) {
        List<OperationVo> list = new ArrayList<>();
        List<PscDtl> dtlList = pscDtlMapper.selectByOperation(vo.getPscNum(), " ((deliv_qty!= null OR deliv_qty!=0) OR (rcv_qty!= null OR rcv_qty!=0)) ");
        //各操作判断
        for (PurKeyValue pur : keyValues) {
            if (("confirm").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("PG") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("确认", "T", confirm));
                } else {
                    list.add(new OperationVo("确认", "F", confirm));
                }
            }
            if (("importBill").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("PG") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("导入原始单据", "T", importBill));
                } else {
                    list.add(new OperationVo("导入原始单据", "F", importBill));
                }
            }
            if (("redo").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("C") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")
                        && StringUtils.NullEmpty(vo.getItmdPscNum()) && StringUtils.NullEmpty(vo.getExecPscNum())
                        && StringUtils.NullEmpty(vo.getStPscNum()) && StringUtils.NullEmpty(vo.getEndPscNum())) {
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
                        && vo.getRenewed().equals("F") && vo.getPuFwdd().equals("F") && dtlList.isEmpty()) {
                    list.add(new OperationVo("反审", "T", reiterate));
                } else {
                    list.add(new OperationVo("反审", "F", reiterate));
                }
            }
            if (("suspend").equals(pur.getOptionValue())) {
                if (!vo.getProgress().equals("CL") && ((vo.getVdeInvd().equals("F") && !vo.getProgress().equals("DD"))
                        || (vo.getVdeInvd().equals("T") && !vo.getProgress().equals("RD"))) && vo.getSuspended().equals("F")
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
            if (("abolish").equals(pur.getOptionValue())) {
                if ((vo.getProgress().equals("PG") || vo.getProgress().equals("C") || vo.getProgress().equals("RK") || vo.getProgress().equals("EK")
                        || vo.getProgress().equals("CK")) && vo.getSuspended().equals("F") && vo.getCancelled().equals("F") && vo.getRenewed().equals("F")
                        && vo.getPuFwdd().equals("F") && vo.getTasksInDeliv() == 0 && vo.getTasksInRcv() == 0 && dtlList.isEmpty()
                        && StringUtils.NullEmpty(vo.getItmdPscNum()) && StringUtils.NullEmpty(vo.getExecPscNum()) && StringUtils.NullEmpty(vo.getBasePscNum())) {
                    list.add(new OperationVo("作废", "T", toVoid));
                } else {
                    list.add(new OperationVo("作废", "F", toVoid));
                }
            }
            if (("close").equals(pur.getOptionValue())) {
                if ((vo.getMultiImpl().equals("T") || vo.getImplByIns().equals("T")) && vo.getProgress().equals("CK")
                        && vo.getSuspended().equals("F") && vo.getCancelled().equals("F") & vo.getTasksInDeliv() == 0
                        && vo.getTasksInRcv() == 0 && StringUtils.NullEmpty(vo.getBasePscNum())) {
                    list.add(new OperationVo("关闭", "T", close));
                } else {
                    list.add(new OperationVo("关闭", "F", close));
                }
            }
            if (("reuse").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("CL") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")
                        && StringUtils.NullEmpty(vo.getBasePscNum())) {
                    list.add(new OperationVo("重用", "T", reusing));
                } else {
                    list.add(new OperationVo("重用", "F", reusing));
                }
            }
        }
        return list;
    }

    /**
     * 出入库明细查询
     *
     * @param vo
     * @return
     */
    @Override
    public PscVo selectObject(PscVo vo) {
        return slcMapper.selectObject(vo);
    }

    /**
     * 销售合同开始发货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @return
     */
    @Override
    public int startDeliver(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) {
        PscVo vo = slcMapper.selectObject(new PscVo("", srcDocNum, srcDocUnitId + ""));
        if (vo != null) {
            //状态判断
            if (vo.getProgress().equals("CK") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {

                if (vo.getImplByIns().equals("F")) {
                    vo.setTasksInDeliv(vo.getTasksInDeliv() + 1);
                    if (vo.getMultiImpl().equals("F")) {
                        vo.setProgress("DG");
                    } else {
                        vo.setProgress("CK");
                    }
                }
                //非居间合同
                if (this.contract(vo)) {
                    if (vo.getImplByIns().equals("F") && vo.getMultiImpl().equals("F")) {
                        //撤销出库任务登记
                        warehDelivTaskService.delDelivTask(new WarehDelivTask(null, "SLC", Long.valueOf(vo.getVenderId() + ""), vo.getSlcNum()), user);
                    }
                }
                return pscMapper.updateByPscVo(vo);
            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
    }

    /**
     * 销售合同终止发货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @return
     */
    @Override
    public int stopDeliver(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        PscVo vo = slcMapper.selectObject(new PscVo("", srcDocNum, srcDocUnitId + ""));
        if (vo != null) {
            if ((vo.getProgress().equals("DG") || ((vo.getMultiImpl().equals("T") || vo.getImplByIns().equals("T")) && vo.getProgress().equals("CK")))
                    && vo.getSuspended().equals("F")) {
                vo.setProgress("CK");
                if (vo.getImplByIns().equals("F")) {
                    vo.setTasksInDeliv(vo.getTasksInDeliv() - 1);
                }
                //非居间合同
                if (this.contract(vo)) {
                    if (vo.getImplByIns().equals("F") && vo.getMultiImpl().equals("F")
                            && StringUtils.NullEmpty(vo.getStPscNum())) {
                        //登记出库任务
                        purchaseUtils.outStock("SLC", vo.getPscNum(), user);
                    }
                }
            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
        return pscMapper.updateByPscVo(vo);
    }

    /**
     * 销售合同发货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @param docUnitId    出库单组织id
     * @param docNum       出库单据编号
     * @return
     */
    @Override
    public int deliver(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        //查询原始单据数据 将发货信息同步
        PscVo vo = slcMapper.getSlcByStb(srcDocUnitId, srcDocNum, docUnitId, docNum, true);
        //单据信息
        Stb stb = stbService.getStbByPk(new Stb(docUnitId, docNum));
        if (vo != null) {
            Map<String, Object> map = new HashMap<>();
            if ((vo.getProgress().equals("DG") || ((vo.getMultiImpl().equals("T") || vo.getImplByIns().equals("T"))
                    && vo.getProgress().equals("CK"))) && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                if (vo.getMultiImpl().equals("T") || vo.getImplByIns().equals("T")) {
                    vo.setProgress("CK");
                } else {
                    vo.setProgress("DD");
                }
                //通过sql语句查询更新明细
                List<PscDtl> dtlList = null;
                if (vo.getPucGen().equals("T")) {
                    dtlList = pscDtlMapper.selectByStbDtl(vo.getPscNum(), docUnitId, docNum, true, "SLC");
                } else {
                    dtlList = pscDtlMapper.findByStbDtl(vo.getPscNum(), docUnitId, docNum, true);
                }
                if (dtlList != null && dtlList.size() > 0) {
                    pscDtlMapper.updateDtlList(dtlList);
                }
                List<PscDtlVo> listss = pscDtlMapper.findByPscNum(new PscDtlVo(vo.getPscNum()));
                //查询stb明细
                List<StbDtlVo> stbDtlVo = stbDtlService.getStbDtlList(new StbDtl(docUnitId, docNum));

                //查询出需要明细数据
                String sql = " , deliv_qty as qty ";
                List<PscDtl> pscDtlList = pscDtlMapper.selectByBill(vo.getPscNum(), sql);

                //非居间合同
                if (this.contract(vo)) {
                    //查询销售协议获取退货控制类别
                    Psa psa = psaMapper.selectByVde(new Psa(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId())));
                    purchaseService.pscAccumulate(psa, docUnitId, docNum, true);
                    //登记未决
                    if (vo.getImplByIns().equals("F")) {
                        map.put("docType", "SLC");
                        map.put("unitId", vo.getVenderId());
                        map.put("docNum", vo.getDocNum());
                        map.put("warehId", vo.getVdrWarehId());
                        if (vo.getMultiImpl().equals("T")) {
                            map.put("dtlList", stbDtlVo);
                            //负数登记
                            map.put("stkType", "BK");
                            map.put("flag", "stb_j");
                            if (!stbDtlVo.isEmpty()) {
                                warehStkPgMapper.updateByNegDtl(map);
                            }
                        } else {
                            //删除
                            List<String> stkList = new ArrayList<>();
                            stkList.add("BK");
                            map.put("stkList", stkList);
                            map.put("dtlList", dtlList);
                            warehStkPgMapper.deleteByDtl(map);
                        }
                    }
                }
                //判断是否监管往来操作
                if (purchaseService.judgePsc(vo.getPscNum()) && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
                    //多次执行就冻结 否则 注销冻结
                    if (vo.getMultiImpl().equals("T")) {
                        BigDecimal creditValue = new BigDecimal("0");
                        if (vo.getMfzEnabled().equals("T") && vo.getPsMfzSite().equals("CK") && vo.getDeposit().compareTo(new BigDecimal("0")) == 1
                                && vo.getTtlVal().compareTo(new BigDecimal("0")) != 0) {
                            creditValue = (creditValue.subtract(stb.getTtlVal())).multiply(vo.getDeposit().divide(vo.getTtlVal())).setScale(4, BigDecimal.ROUND_HALF_UP);
                        }
                        //冻结金额非0 冻结往来账户余额
                        if (creditValue.compareTo(new BigDecimal("0")) != 0) {
                            //通过供应商id与采购商id查询是否有往来账户
                            Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                            if (ca != null) {
                                caAccService.freeze(new CaAcc(0L, 0L, "SLC", Long.valueOf(vo.getVenderId()), vo.getDocNum()), user, ca, creditValue, new BigDecimal("0"), false);
                            }
                        }
                    } else {
                        //注销冻结金额
                        purchaseService.superviseBySales(vo, user);
                    }
                }

                vo.setTasksInDeliv(vo.getTasksInDeliv() - 1);
                if (StringUtils.isNotEmpty(vo.getSrcDocType())) {
                    if (vo.getSrcDocType().equals("RGO") && vo.getSrcDocUnitId().equals(vo.getVenderId())) {
                        //中转发货
                        rgoService.transferUnitDelivered(rgoService.selectByOnly(new RgoVo(vo.getSrcDocUnitId(), vo.getSrcDocNum())));
                    }
                }
                if (vo.getPucGen().equals("T")) {
                    //通知发货事件
                    pscMapper.updateByPscVo(vo);
                    pucService.venderDelivered(pucService.selectByOnly(new PscVo(vo.getPscNum())), docUnitId, docNum, user);
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
                if (vo.getPucGen().equals("F") && (vo.getImplByIns().equals("T") || vo.getMultiImpl().equals("T"))
                        && vo.getTasksInRcv().equals(0) && (parameterMapper.findById("PSC_AUTO_CLOSE_WHEN_EXPECTATION_REACHED").getParmVal().equals("T")
                        && pscDtlMapper.selectByOperation(vo.getPscNum(), " deliv_qty < qty ").isEmpty() || vo.getMultiImpl().equals("F"))) {
                    closeType = this.close(vo, user);
                    if (closeType == 1) {
                        //更新状态避免最后更新的时候状态不对
                        vo.setProgress("CL");
                    }
                }
                //多次执行
                if (this.contract(vo) && vo.getImplByIns().equals("F") && vo.getMultiImpl().equals("T")
                        && closeType == 0) {
                    //判断是否全部发货
                    if (pscDtlMapper.selectByOperation(vo.getPscNum(), "deliv_qty < qty").isEmpty()) {
                        //撤销出库任务登记
                        warehDelivTaskService.delDelivTask(new WarehDelivTask(null, "SLC", Long.valueOf(vo.getVenderId() + ""), vo.getSlcNum()), user);
                    } else {
                        pscMapper.updateByPscVo(vo);
                        //重新登记出库任务
                        purchaseUtils.outStock("SLC", vo.getPscNum(), user);
                    }
                }
            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
        return pscMapper.updateByPscVo(vo);
    }

    /**
     * 销售合同取消发货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @param docUnitId    出库单组织id
     * @param docNum       出库单据编号
     * @param user         用户
     * @return
     */
    @Override
    public int reverseDeliver(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        //查询原始单据数据 将发货信息同步
        PscVo vo = slcMapper.getSlcByStb(srcDocUnitId, srcDocNum, docUnitId, docNum, false);
        //单据信息
        Stb stb = stbService.getStbByPk(new Stb(docUnitId, docNum));
        if (vo != null) {
            Map<String, Object> map = new HashMap<>();
            if ((vo.getProgress().equals("DD") || (vo.getProgress().equals("RD") && StringUtils.isNotEmpty(vo.getEndPscNum())) ||
                    ((vo.getMultiImpl().equals("T") || vo.getImplByIns().equals("T")) && vo.getProgress().equals("CK"))) &&
                    vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                //判断是否通知采购合同取消发货事件
                if (vo.getPucGen().equals("T")) {
                    pucService.venderReverseDelivered(vo, docUnitId, docNum, user);
                }
                //修改状态
                if (vo.getMultiImpl().equals("T") || vo.getImplByIns().equals("T")) {
                    vo.setProgress("CK");
                } else {
                    vo.setProgress("DG");
                }
                //通过sql语句查询更新明细
                List<PscDtl> dtlList = null;
                if (vo.getPucGen().equals("T")) {
                    dtlList = pscDtlMapper.selectByStbDtl(vo.getPscNum(), docUnitId, docNum, false, "SLC");
                } else {
                    dtlList = pscDtlMapper.findByStbDtl(vo.getPscNum(), docUnitId, docNum, false);
                }
                if (dtlList != null && dtlList.size() > 0) {
                    pscDtlMapper.updateDtlList(dtlList);
                }
                //非居间合同
                if (this.contract(vo)) {
                    //查询销售协议获取退货控制类别
                    Psa psa = psaMapper.selectByVde(new Psa(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId())));
                    purchaseService.pscAccumulate(psa, docUnitId, docNum, false);
                    //登记未决
                    if (vo.getImplByIns().equals("F")) {
                        map.put("docType", "SLC");
                        map.put("unitId", vo.getVenderId());
                        map.put("docNum", vo.getDocNum());
                        map.put("warehId", vo.getVdrWarehId());
                        map.put("stkType", "BK");
                        //查询stb明细
                        List<StbDtlVo> stbDtlVo = stbDtlService.getStbDtlList(new StbDtl(docUnitId, docNum));

                        if (vo.getMultiImpl().equals("T")) {
                            //本次数量
                            map.put("flag", "stb_a");
                            map.put("dtlList", stbDtlVo);
                        } else {
                            //单据数量
                            map.put("dtlList", dtlList);
                            map.put("flag", "psc_a");
                        }
                        warehStkPgMapper.updateByNegDtl(map);
                    }
                }
                //判断是否监管往来操作
                if (purchaseService.judgePsc(vo.getPscNum()) && !vo.getVdrFsclUnitId().equals(vo.getVendeeId())) {
                    BigDecimal creditValue = new BigDecimal("0");
                    if (vo.getMultiImpl().equals("T")) {
                        //登记冻结金额
                        if (vo.getMfzEnabled().equals("T") && vo.getPsMfzSite().equals("CK") && vo.getTtlVal().compareTo(creditValue) != 0 &&
                                vo.getDeposit().compareTo(creditValue) != 0) {
                            creditValue = (stb.getTtlVal()).multiply(vo.getDeposit().divide(vo.getTtlVal())).setScale(4, BigDecimal.ROUND_HALF_UP);
                        }
                    } else {
                        //重新登记冻结金额
                        if (vo.getMfzEnabled().equals("T") && vo.getPsMfzSite().equals("CK") && vo.getTtlVal().compareTo(creditValue) != 0) {
                            creditValue = (vo.getTtlVal().subtract(vo.getTtlDelivVal())).multiply(vo.getDeposit().divide(vo.getTtlVal())).setScale(4, BigDecimal.ROUND_HALF_UP);
                        }
                        if (vo.getGmEnabled().equals("T")) {
                            creditValue = creditValue.add(vo.getGuaMon());
                        }
                    }
                    //冻结金额非0 冻结往来账户余额
                    if (creditValue.compareTo(new BigDecimal("0")) != 0) {
                        //通过供应商id与采购商id查询是否有往来账户
                        Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                        if (ca != null) {
                            caAccService.freeze(new CaAcc(0L, 0L, "SLC", Long.valueOf(vo.getVenderId()), vo.getDocNum()), user, ca, creditValue, new BigDecimal("0"), true);
                        }
                    }
                }
                vo.setTasksInDeliv(vo.getTasksInDeliv() + 1);
                if (vo.getPucGen().equals("F")) {
                    vo.setTtlRcvQty(vo.getTtlDelivQty());
                    vo.setTtlRcvBox(vo.getTtlDelivBox());
                    vo.setTtlRcvVal(vo.getTtlDelivVal());
                    vo.setTtlRcvTax(vo.getTtlDelivTax());
                    vo.setTtlRcvMkv(vo.getTtlDelivMkv());
                }
                //判断是否重新登记出库任务
                if (this.contract(vo) && vo.getImplByIns().equals("F") && vo.getMultiImpl().equals("T")) {
                    //登记出库任务
                    purchaseUtils.outStock("SLC", vo.getPscNum(), user);
                }
                if (StringUtils.isNotEmpty(vo.getSrcDocType())) {
                    if (vo.getSrcDocType().equals("RGO") && vo.getSrcDocUnitId().equals(vo.getVenderId())) {
                        //取消中转发货
                        rgoService.transferUnitReversedDelivered(rgoService.selectByOnly(new RgoVo(vo.getSrcDocUnitId(), vo.getSrcDocNum())));
                    }
                }
            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
        return pscMapper.updateByPscVo(vo);
    }

    /**
     * 购货方收货通知
     *
     * @param vo
     * @param stb
     * @return
     */
    @Override
    public void vendeeReceived(PscVo vo, Stb stb) {
        //原始单据是调配单
        if (vo.getSrcDocType().equals("RGO") && vo.getSrcDocUnitId().equals(vo.getVenderId())) {
            //调入方收货
            rgoService.receivingUnitReceived(rgoService.selectByOnly(new RgoVo(vo.getSrcDocUnitId(), vo.getSrcDocNum())), stb);
        }
    }

    /**
     * 购货方取消收货通知
     *
     * @param vo
     * @param stb
     * @return
     */
    @Override
    public void vendeeReverseReceived(PscVo vo, Stb stb) {
        //原始单据是调配单
        if (vo.getSrcDocType().equals("RGO") && vo.getSrcDocUnitId().equals(vo.getVenderId())) {
            //调入方取消收货
            rgoService.receivingUnitReverseReceived(rgoService.selectByOnly(new RgoVo(vo.getSrcDocUnitId(), vo.getSrcDocNum())), stb);
        }
    }

    /**
     * 退货收货
     *
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    @Override
    public int returnReceive(String pscNum, Long docUnitId, String docNum) {
        //商品不存在且商品的到货数量小于退购数量  报异常
        List<PscDtl> list = pscDtlMapper.getException(pscNum, docUnitId, docNum, "  pDtl.deliv_qty < (IFNULL( pDtl.rs_qty, 0 ) + sDTL.qty)  ");
        if (list != null && list.size() > 0) {
            throw new ServiceException(JsonResultCode.FAILURE, "退销商品超出限制！");
        }
        //累计退购信息到明细中
        pscDtlMapper.updateByStbToRs(pscNum, docUnitId, docNum, true);
        return pscMapper.updateByStbToRs(pscNum, docUnitId, docNum, true);
    }

    /**
     * 取消退货收货
     *
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    @Override
    public int reverseReturnReceive(String pscNum, Long docUnitId, String docNum) {
        //累计退购信息到明细中
        pscDtlMapper.updateByStbToRs(pscNum, docUnitId, docNum, false);
        return pscMapper.updateByStbToRs(pscNum, docUnitId, docNum, false);
    }

    /**
     * 后天配货调整
     *
     * @param vo
     * @param comList
     */
    @Override
    public void postAdmeasure(PscVo vo, List<CommonDtl> comList, boolean flag) {
        if (vo.getImplByIns().equals("T") && vo.getProgress().equals("CK") && vo.getSuspended().equals("F")
                && vo.getCancelled().equals("F")) {
            //调用配货接口
            this.doAdmeasure(vo, comList, flag);
        }
    }

}
