package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.entity.basic.TranDiffEvt;
import com.boyu.erp.platform.usercenter.entity.purchase.*;
import com.boyu.erp.platform.usercenter.entity.sales.Slc;
import com.boyu.erp.platform.usercenter.entity.shop.Shop;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.CaMapper;
import com.boyu.erp.platform.usercenter.mapper.basic.CompanyMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.*;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcMapper;
import com.boyu.erp.platform.usercenter.mapper.shop.ShopMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysDomainMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehRcvTaskMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehStkPgMapper;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.caservice.CaAccService;
import com.boyu.erp.platform.usercenter.service.caservice.TranDiffEvtService;
import com.boyu.erp.platform.usercenter.service.common.impl.ButtonCommonService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscDtlService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PucService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcService;
import com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl.SlcServiceImpl;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.warehouse.GdnService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.StbService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehRcvTaskSerivce;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.PurchaseUtils;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.OptionByPsaVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Classname PucServiceimpl
 * @Description TODO
 * @Date 2019/6/18 19:20
 * @Created wz
 */
@Slf4j
@Service
@Transactional
public class PucServiceImpl implements PucService {
    //采购合同模块按钮参数
    static final String parameter = "pucButton";

    //确认
    static final String confirm = "/purchase/puc/confirm";
    //重做
    static final String redo = "/purchase/puc/redo";
    //审核
    static final String examine = "/purchase/puc/examine";
    //反审
    static final String reiterate = "/purchase/puc/reiterate";
    //挂起
    static final String hangUp = "/purchase/puc/hangUp";
    //恢复
    static final String recovery = "/purchase/puc/recovery";
    //作废
    static final String toVoid = "/purchase/puc/toVoid";
    //关闭
    static final String close = "/purchase/puc/close";
    //重用
    static final String reusing = "/purchase/puc/reusing";
    //导入
    static final String importBill = "/purchase/puc/importBill";

    @Autowired
    private UsersService usersService;
    @Autowired
    private PucMapper pucMapper;
    @Autowired
    private PscMapper pscMapper;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private SysDomainMapper sysDomainMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private PscDtlRqdMapper pscDtlRqdMapper;
    @Autowired
    private PscDtlMapper pscDtlMapper;
    @Autowired
    private PscDtlService pscDtlService;
    @Autowired
    private PscChgDtlRqdMapper pscChgDtlRqdMapper;
    @Autowired
    private PscChgMapper pscChgMapper;
    @Autowired
    private PurchaseUtils purchaseUtils;
    @Autowired
    private SlcService slcService;
    @Autowired
    private SlcMapper slcMapper;
    @Autowired
    private PucTypeMapper pucTypeMapper;
    @Autowired
    private WarehStkPgMapper warehStkPgMapper;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private CaMapper caMapper;
    @Autowired
    private CaAccService caAccService;
    @Autowired
    private TranDiffEvtService tranDiffEvtService;
    @Autowired
    private PuaMapper puaMapper;
    @Autowired
    private PscTypeMapper pscTypeMapper;
    @Autowired
    private ButtonCommonService buttonCommonService;
    @Autowired
    private PsaMapper psaMapper;
    @Autowired
    private StbDtlService stbDtlService;
    @Autowired
    private StbService stbService;
    @Autowired
    private WarehRcvTaskSerivce warehRcvTaskSerivce;
    @Autowired
    private SlcServiceImpl slcServiceImpl;


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
    public PageInfo<PscVo> selectAll(PscVo vo, Integer page, Integer size, SysUser user) throws Exception {
        List<PscVo> list = null;
        /**
         * 系统管理员
         * */
        if (false) {
            PageHelper.startPage(page, size);
            list = pucMapper.selectALL(vo);
        } else {
            PageHelper.startPage(page, size);
            list = pucMapper.selectByUnit(vo);
        }
        PageInfo<PscVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 添加采购合同
     *
     * @param v
     * @return
     */
    public int insertPuc(PscVo v, SysUser user) throws Exception {
        //通过系统参数PS_DOCUMENT_UNIQUED_NUMBERED去生成购销合同编号或者采购合同编号
        SysParameter sys = parameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        SysUser sysU = new SysUser(Long.valueOf(v.getUnitId()), user.getPrsnl().getPrsnlId());
        if (sys.getParmVal().equals("T")) {
            String pscNum = sysRefNumService.mainNum(sysU, "PSC_NUM");
            v.setPucNum(pscNum);
            v.setPscNum(pscNum);
        } else {
            String pucNum = sysRefNumService.viceNum(sysU, "PUC_NUM");
            String pscNum = sysRefNumService.mainNum(sysU, "PSC_NUM");
            v.setPucNum(pucNum);
            v.setPscNum(pscNum);
        }
        //判断bas_psc_num是否非空 非空就清除原始单据的数据
        if (v.getBasePscNum() != null && !v.getBasePscNum().equals("")) {
            v.setSrcDocNum("");
            v.setSrcDocUnitId(null);
            v.setSrcDocNum("");
        }

        v.setSlCtrl("I");
        v.setSlFwdd("F");

        //添加删除更新部分
        v = this.updatePscVo(v);

        v.setEffective("F");
        v.setCancelled("F");
        v.setRenewed("F");
        v.setSuspended("F");
        v.setProgress("PG");

        v.setPucGen("T");//默认采购合同已生成
        v.setPucAutoGen("T");
        v.setVdeInvd("T");//默认采购商介入

        v.setOprId(user.getPrsnl().getPrsnlId() + "");

        if (v.getPscDtlList() != null && v.getPscDtlList().size() > 0) {
            v = pscDtlService.insertByPsc(v, user);
            PscType pscType = pscTypeMapper.selectByPrimaryKey(v.getPscType());
            //计算保证金以及定金
            if (v.getGmEnabled().equals("T")) {
                v.setGuaMon(pscType.getGmRate().multiply(v.getTtlVal()));
            }
            v.setDeposit(pscType.getDpstRate().multiply(v.getTtlVal()));
        }

        //保存数据
        pscMapper.insertByPscVo(v);

        return pucMapper.insertByPscVo(v);
    }

    //公共方法
    private PscVo updatePscVo(PscVo v) {
        //判断供应商是否介入设置销售相关数据
        if (StringUtils.NullEmpty(v.getVdrInvd())) {
            v.setVdrInvd("F");
        }
        if (v.getVdrInvd().equals("T")) {
            v.setSlcAutoGen("T");
        } else {
            v.setSlcAutoGen("F");
            v.setSlcAutoChk("F");
        }
//        SysDomain sysDo = sysDomainMapper.selectByPrimaryKey(Long.valueOf(v.getVenderId()));
//        if (sysDo != null) {
//            if (sysDo.getDomainStatus().equals("A")) {
//                v.setVdrInvd("T");
//                v.setSlcAutoGen("T");
//            } else {
//                v.setVdrInvd("F");
//                v.setSlcAutoGen("F");
//                v.setSlcAutoChk("F");
//            }
//        } else {
//            v.setVdrInvd("F");
//            v.setSlcAutoGen("F");
//            v.setSlcAutoChk("F");
//        }
        v.setSlcGen("F");
        return v;
    }

    /**
     * 修改采购合同
     *
     * @param v
     * @return
     */
    public int updatePuc(PscVo v, SysUser user) throws Exception {

        v.setOprId(user.getPrsnl().getPrsnlId() + "");
        //判断bas_psc_num是否非空 非空就清除原始单据的数据
        if (v.getBasePscNum() != null && !v.getBasePscNum().equals("")) {
            v.setSrcDocNum("");
            v.setSrcDocUnitId(null);
            v.setSrcDocNum("");
        }
        //添加删除更新部分
        v = this.updatePscVo(v);

        this.updateRqdCtrl(v, user);
        System.err.println(v.getExblRate() + "++++++++");
        pscMapper.updateByPscVo(v);
        return pucMapper.updateByPscVo(v);
    }


    /**
     * 更新货期及货期控制方法
     *
     * @param v
     */
    public void updateRqdCtrl(PscVo v, SysUser sysUser) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> map = new HashMap<>();
        //先创建修改得数据
        PscChg pscChg = pscChgMapper.selectByPscDtl(v.getPscNum());
        // 添加或修改购销合同变更PSC_CHG
        if (pscChg != null) {
            pscChg.setRcdNum(pscChg.getRcdNum() + 1);
        } else {
            pscChg = new PscChg();
            pscChg.setPscNum(v.getPscNum());
            pscChg.setRcdNum(1L);
        }
        pscChg.setOprId(sysUser.getPrsnl().getPrsnlId());
        pscChg.setOpTime(new Date());
        pscChgMapper.insertSelective(pscChg);

        if (!v.getRqdCtrlOld().equals(v.getRqdCtrl())) {
            //如果货期控制改了，清除之前的货期值，新增或修改货期值
            if (v.getReqdDate() != null) {
                if (v.getRqdCtrlOld().equals("SG")) {
                    if (v.getRqdCtrl().equals("PD")) {
                        map.put("reqdDate", v.getReqdDate());
                        map.put("pscNum", v.getPscNum());
                        pscDtlMapper.updateByRqdCtrl(map);
                    } else if (v.getRqdCtrl().equals("FR")) {
                        this.updateRqd(v, pscChg.getRcdNum());
                    }
                } else if (v.getRqdCtrlOld().equals("PD")) {
                    //直接清除明细中货期
                    map.put("reqdDate", null);
                    map.put("pscNum", v.getPscNum());
                    pscDtlMapper.updateByRqdCtrl(map);

                    if (v.getRqdCtrl().equals("FR")) {
                        this.updateRqd(v, pscChg.getRcdNum());
                    }
                } else if (v.getRqdCtrlOld().equals("FR")) {
                    //直接删除货期数据
                    this.deletePscDtlRqd(v.getPscNum(), pscChg.getRcdNum());

                    if (v.getRqdCtrl().equals("PD")) {
                        map.put("reqdDate", v.getReqdDate());
                        map.put("pscNum", v.getPscNum());
                        pscDtlMapper.updateByRqdCtrl(map);
                    }
                }
            }
        } else {
            //判断是否修改货期
            if (v.getReqdType().equals("1")) {
                //如果货期改了,根据货期控制修改货期值
                if (v.getRqdCtrl().equals("PD")) {
                    map.put("reqdDate", v.getReqdDate());
                    map.put("pscNum", v.getPscNum());
                    pscDtlMapper.updateByRqdCtrl(map);
                } else if (v.getRqdCtrl().equals("FR")) {
                    PscDtlRqd pdr = new PscDtlRqd();
                    pdr.setPscNum(v.getPscNum());
                    pdr.setReqdDate(sdf.parse(v.getReqdDate()));
                    pscDtlRqdMapper.updateByReqdDate(pdr);
                }
            }
        }
    }

    //修改货期下拉数据时删除相关数据
    private void deletePscDtlRqd(String pscNum, Long rcdNum) {
        List<PscDtlRqd> list = pscDtlRqdMapper.selectByPscNum(pscNum);
        if (list != null) {
            for (PscDtlRqd pdr : list) {
                PscChgDtlRqd pscChgDtlRqd = new PscChgDtlRqd();
                pscChgDtlRqd.setRcdNum(rcdNum);
                pscChgDtlRqd.setAdjQty(new BigDecimal("0").subtract(pdr.getQty()));
                pscChgDtlRqd.setReqdDate(pdr.getReqdDate());
                pscChgDtlRqd.setProdId(pdr.getProdId());
                pscChgDtlRqd.setPscNum(pdr.getPscNum());
                pscChgDtlRqdMapper.insertSelective(pscChgDtlRqd);
            }
        }
        pscDtlRqdMapper.deleteByRqdCtrl(pscNum);
    }

    //添加货期数据
    private void updateRqd(PscVo v, Long rcdNum) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        PscDtlVo vo = new PscDtlVo();
        vo.setPscNum(v.getPscNum());
        List<PscDtlVo> list = pscDtlService.findByPscNum(vo);
        if (list != null) {
            for (PscDtlVo p : list) {
                PscDtlRqd pdr = new PscDtlRqd();
                pdr.setPscNum(p.getPscNum());
                pdr.setProdId(p.getProdId());
                pdr.setQty(p.getQty());
                pdr.setReqdDate(sdf.parse(v.getReqdDate()));
                pscDtlRqdMapper.insertSelective(pdr);
                PscChgDtlRqd pscChgDtlRqd = new PscChgDtlRqd();
                pscChgDtlRqd.setPscNum(p.getPscNum());
                pscChgDtlRqd.setProdId(p.getProdId());
                pscChgDtlRqd.setAdjQty(p.getQty());
                pscChgDtlRqd.setReqdDate(sdf.parse(v.getReqdDate()));
                pscChgDtlRqd.setRcdNum(rcdNum);
                pscChgDtlRqdMapper.insertSelective(pscChgDtlRqd);
            }
        }
    }


    /**
     * 删除采购合同
     *
     * @param v
     * @return
     */
    public int deletePuc(PscVo v, SysUser sysUser) throws Exception {
        //删除puc表数据
        pucMapper.deletePscVo(v.getVendeeId(), v.getPucNum());
        v.setPucGen("F");

        if (StringUtils.isNotEmpty(v.getEndPscNum())) {
            // 如果END_PSC_NUM非空，调用END_PSC_NUM对应销售合同的SalesContractHome.detach方法，取消关联。
            v.setEndPscNum("");

            slcService.relation(v.getEndPscNum(), new SysUser(Long.valueOf(v.getVenderId()), sysUser.getPrsnl().getPrsnlId()));
        }

        //如果SLC_AUTO_GEN = TRUE && SLC_GEN = TRUE，则调用SalesContractHome.remove，删除SLC。
        //这种情况下，表示当前单据处于已确认未审核状态
        if (v.getSlcAutoGen().equals("T") && v.getSlcGen().equals("T")) {
            //调用销售合同接口 删除slc表数据
            v.setSlcNum(slcMapper.selectByPscNum(v.getPscNum()).getSlcNum());
            return slcService.deleteSlc(v, new SysUser(Long.valueOf(v.getVenderId()), sysUser.getPrsnl().getPrsnlId()));
        }
        //判断是否删除psc表  否则就更新它 设置PUC_GEN = FALSE。
        if (v.getSlcGen().equals("F")) {
            //删除购销协议表的时候 删除明细
            pscDtlService.deleteByPuc(v.getPscNum(), sysUser);
            return pscMapper.deleteByPrimaryKey(v.getPscNum());
        }
        return 1;
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
            if (psc.getImplByIns().equals("F") && psc.getVdrInvd().equals("F")
                    && psc.getEffective().equals("T")) {
                //登记入库任务
                purchaseUtils.generate("PUC", pscNum, user);
            }
        }
    }

    /**
     * 采购合同中确认单据
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
                companyMapper.selectByPrimaryKey(user.getDomain().getUnitId()).getFsclUnitId() + "");

        //确认的时候生成
        if (vo.getSlcGen().equals("F") && vo.getSlcAutoGen().equals("T")) {
            vo.setSlcGen("T");
            slcService.insertByPuc(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        }

        return pscMapper.updateByPscVo(vo);
    }

    /**
     * 采购合同中重做单据
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
        if (vo.getSlcGen().equals("T") && vo.getSlcAutoGen().equals("T")) {
            vo.setSlcGen("F");
            //删除销售合同
            slcMapper.deleteByPscNum(vo.getPscNum());
        }
        return pscMapper.updateByState(vo);
    }

    /**
     * 采购合同中审核单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int examine(PscVo vo, SysUser user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //将状态改为已审核状态  并修改采购合同与销售合同审核状态
        if (vo.getProgress().equals("C") && vo.getSlcGen().equals("T")) {
            vo.setProgress("EK");
            vo.setEffective("F");
        } else {
            vo.setProgress("CK");
            vo.setEffective("T");
        }
        //如果原始单据非空 并且原始单据组织ID等于采购商id
        //因为此处只有采购申请   而采购申请可生成多张  此处逻辑暂时作废
        /*if (StringUtils.isNotEmpty(vo.getSrcDocNum()) && vo.getVendeeId().equals(vo.getSrcDocUnitId() + "")) {
            //查询原始单据数据
            List<PsxVo> psxList = puaMapper.selectUserList(new PsxVo(Long.valueOf(vo.getSrcDocUnitId()), vo.getSrcDocNum()));
            if (psxList != null && psxList.size() > 0) {
                //调用采购申请关闭
                puaService.close(psxList.get(0), user);
            }
        }*/

        //非居间合同  登记未决
        if (this.contract(vo)) {
            //获取单据明细
            List<PscDtlVo> dtlList = pscDtlMapper.findByPscNum(new PscDtlVo(vo.getPscNum()));
            if (dtlList != null && dtlList.size() > 0) {
                if (StringUtils.isNotEmpty(vo.getVdeWarehId())) {
                    //判断收货仓库是否是门店
                    Shop shop = shopMapper.selectByShop(new Shop(Long.valueOf(vo.getVendeeId()), Long.valueOf(vo.getVdeWarehId())));
                    if (shop != null) {
                        //判断库存合法性  预留

                    }
                    if (StringUtils.isNotEmpty(vo.getVdeWarehId())) {
                        map.put("stkType", "EP");
                        map.put("docType", "PUC");
                        map.put("unitId", vo.getVendeeId());
                        map.put("docNum", vo.getDocNum());
                        map.put("warehId", vo.getVdeWarehId());
                        map.put("dtlList", dtlList);
                        warehStkPgMapper.insertByDtl(map);
                    }
                }
            }
        }

        //判断是否监管往来
        if (purchaseService.judgePsc(vo.getPscNum()) && !vo.getVdeFsclUnitId().equals(vo.getVenderId())) {
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
                Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVendeeId()), Long.valueOf(vo.getVenderId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                if (ca != null) {
                    caAccService.freeze(new CaAcc(null, 0L, "PUC", ca.getUnitId(), vo.getDocNum()), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()), ca, debitValue, new BigDecimal("0"), true);
                }
            }
        }

        //将销售合同以及采购合同中的审核信息补充
        //当供应商不介入的时候去判断是否创建入库任务
        //介入的时候只创建出库任务
        //入库任务等供应商确认出库之后才会生成
        pucMapper.updateByChkr(user.getPrsnl().getPrsnlId() + "", vo.getPscNum());

        if (this.contract(vo) && vo.getImplByIns().equals("F") && vo.getVdrInvd().equals("F") && vo.getProgress().equals("CK") && (StringUtils.NullEmpty(vo.getSrcDocType()) ? true : !vo.getSrcDocType().equals("RGO"))
                && StringUtils.NullEmpty(vo.getBasePscNum())) {
            pscMapper.updateByPscVo(vo);
            purchaseUtils.generate("PUC", vo.getPscNum(), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }
        //判断是否生成了销售合同
        if (vo.getSlcGen().equals("T")) {
            if (StringUtils.isNotEmpty(vo.getPucNum())) {
                vo.setDocNum(slcMapper.selectByPscNum(vo.getPscNum()).getSlcNum());
                //调用销售合同的通知审核事件
                return slcService.noticeExamine(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
            } else {
                pscMapper.updateByState(vo);
                purchaseUtils.outStock("SLC", vo.getPscNum(), new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
            }
        }
        return pscMapper.updateByPscVo(vo);
    }

    /**
     * 采购合同中反审单据
     *
     * @param vo
     * @return
     */
    @Override
    public int reiterate(PscVo vo, SysUser user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //判断重审后进度的状态
        if (vo.getProgress().equals("CK") && vo.getSlcGen().equals("T")) {
            vo.setProgress("RK");
        } else {
            vo.setProgress("C");
        }
        vo.setEffective("F");

        //如果原始单据非空 并且原始单据组织ID等于采购商id
        //审核时不对申请进行操作，此处逻辑暂时作废
        /*if (StringUtils.isNotEmpty(vo.getSrcDocNum()) && vo.getVendeeId().equals(vo.getSrcDocUnitId() + "")) {
            //查询原始单据数据
            List<PsxVo> psxList = puaMapper.selectUserList(new PsxVo(Long.valueOf(vo.getSrcDocUnitId()), vo.getSrcDocNum()));
            if (psxList != null && psxList.size() > 0) {
                //调用采购申请重用
                puaService.reusing(psxList.get(0), user);
            }
        }*/

        //非居间合同  取消未决
        if (this.contract(vo)) {
            List<String> stkList = new ArrayList<>();
            stkList.add("EP");
            map.put("stkList", stkList);
            this.cancel(vo, map);
            //判断是否撤销入库任务
            if (vo.getImplByIns().equals("F") && StringUtils.NullEmpty(vo.getBasePscNum())) {
                //撤销出入库任务登记
                warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUC", Long.valueOf(vo.getVendeeId()), vo.getDocNum()), user);
            }
        }

        //判断是否监管往来
        if (purchaseService.judgePsc(vo.getPscNum()) && !vo.getVdeFsclUnitId().equals(vo.getVenderId())) {
            purchaseService.supervise(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }

        //更新采购合同得审核信息数据
        pucMapper.updateByChkr(null, vo.getPscNum());

        if (vo.getSlcGen().equals("T")) {
            //查出销售合同号更换docNum值
            vo.setDocNum(slcMapper.selectByPscNum(vo.getPscNum()).getSlcNum());
            //通知销售合同反审
            return slcService.noticeReiterate(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        }

        return pscMapper.updateByState(vo);
    }

    /**
     * 根据购销合同号删除采购合同
     *
     * @param vo
     * @return
     */
    @Override
    public int deleteByPuc(PscVo vo) {
        return pucMapper.deleteByPscNum(vo.getPscNum());
    }

    /**
     * 销售合同新增采购合同
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertByPuc(PscVo vo, SysUser user) {
        Puc puc = new Puc();
        //通过pscType获取pucType
        PucType pucType = pucTypeMapper.selectByPscType(vo.getPscType());
        if (pucType != null) {
            puc.setPucType(pucType.getPucType());
            puc.setPuBrdgMode(pucType.getPuBrdgMode());
        }
        puc.setSuspended("F");
        puc.setSlCtrl("I");
        puc.setSlFwdd("F");
        puc.setAcBnd("F");
        puc.setAcReqd("F");
        puc.setPscNum(vo.getPscNum());
        puc.setUnitId(Long.valueOf(vo.getVendeeId()));
        //生成销售编号
        //通过系统参数PS_DOCUMENT_UNIQUED_NUMBERED去生成购销合同编号或者采购合同编号
        SysParameter sys = parameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        if (sys.getParmVal().equals("T")) {
            puc.setPucNum(vo.getPscNum());
        } else {
            String pucNum = sysRefNumService.viceNum(user, "PUC_NUM");
            puc.setPucNum(pucNum);
        }
        return pucMapper.insertSelective(puc);
    }

    /**
     * 通知销售合同审核事件
     *
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int noticeExamine(PscVo vo, SysUser user) throws Exception {
        if (vo.getPucAutoChk().equals("T") && vo.getProgress().equals("RK")) {
            return this.examine(vo, user);
        }
        return pscMapper.updateByState(vo);
    }

    /**
     * 通知销售合同反审事件
     *
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int noticeReiterate(PscVo vo, SysUser user) throws Exception {
        if (vo.getPucAutoChk().equals("T") && vo.getProgress().equals("EK")) {
            return this.reiterate(vo, user);
        }
        return pscMapper.updateByState(vo);
    }

    /**
     * 采购合同中关闭
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int close(PscVo vo, SysUser user) throws Exception {
        vo.setProgress("CL");
        this.doClose(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        if (vo.getSlcGen().equals("T")) {
            //调用销售合同采购商关闭事件
            slcService.vendeeClosed(slcMapper.selectByOnly(vo), new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        }
        return pscMapper.updateByState(vo);
    }

    /**
     * 采购合同中重用
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int reusing(PscVo vo, SysUser user) throws Exception {
        vo.setProgress("CK");
        this.doReuse(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        if (vo.getSlcGen().equals("T")) {
            //调用销售合同采购商关闭事件
            slcService.vendeeReused(slcMapper.selectByOnly(vo), new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        }
        return pscMapper.updateByState(vo);
    }

    /**
     * 采购合同挂起单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int hangUp(PscVo vo, SysUser user) {
        vo.setSuspended("T");
        if (vo.getSlcGen().equals("T")) {
            this.slcSu(vo.getPscNum(), vo.getSuspended());
        }
        return pucMapper.updateByPscVo(vo);
    }

    /**
     * 采购合同恢复单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int recovery(PscVo vo, SysUser user) {
        vo.setSuspended("F");
        if (vo.getSlcGen().equals("T")) {
            this.slcSu(vo.getPscNum(), vo.getSuspended());
        }
        return pucMapper.updateByPscVo(vo);
    }

    /**
     * 操作销售合同挂起
     *
     * @param pscNum
     */
    public void slcSu(String pscNum, String suspended) {
        Slc slc = slcMapper.selectByPscNum(pscNum);
        if (slc != null) {
            slc.setSuspended(suspended);
            slcMapper.updateByPrimaryKeySelective(slc);
        }
    }

    /**
     * 采购合同作废单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int toVoid(PscVo vo, SysUser user) throws Exception {
        vo.setCancelled("T");
        this.doAbolish(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        if (vo.getSlcGen().equals("T")) {
            //调用销售合同中采购商通知作废事件
            slcService.vendeeAbolished(slcMapper.selectByOnly(vo), new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        }
        //预留  一张采购申请可能对应多张采购合同
        /*if (vo.getProgress().equals("CK") && StringUtils.isNotEmpty(vo.getSrcDocNum())
                && vo.getVendeeId().equals(vo.getSrcDocUnitId())) {
            //调用采购申请重用功能
            puaService.reusing(puaMapper.selectUserList(new PsxVo(vo.getSrcDocUnitId(), vo.getSrcDocNum())).get(0), user);
        }*/
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
        PsxVo psx = puaMapper.selectObject(vo);
        if (psx != null) {
            OptionByPsaVo psa = psaMapper.selectByPsaByVde(new OptionByPsaVo(psx.getVenderId(), Long.valueOf(vo.getVendeeId())));
            if (psa != null) {
                map.put("pscNum", vo.getPscNum());
                map.put("pscType", vo.getPscType());
                map.put("vendeeId", vo.getVendeeId());
                map.put("venderId", psx.getVenderId());
                map.put("psaCtlr", psa.getPsaCtlr());
                map.put("vdrWarehId", psx.getDelivWarehId());
                map.put("vdeWarehId", psx.getRcvWarehId());
                map.put("pscDtlList", psx.getPsxDtlList());
                map.put("vdrInvd", psa.getInte());
            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据的供应商与采购商不存在购销协议！");
            }
        }
        return map;
    }

    /**
     * 采购合同/销售合同中导入更新
     *
     * @param vo
     * @return
     */
    @Override
    public int importUpdate(PscVo vo, SysUser user) throws Exception {
        //清除之前明细数据
        pscDtlService.deleteByPuc(vo.getPscNum(), user);
        PscDtlVo dtlVo = new PscDtlVo(vo.getPscNum(), vo.getPscDtlList());
        pscDtlService.insertPscDtl(dtlVo, user);
        Psc psc = pscMapper.selectByPrimaryKey(vo.getPscNum());
        if(!org.springframework.util.StringUtils.isEmpty(vo.getVenderId())){
            psc.setVenderId(Long.valueOf(vo.getVenderId()));
        }
        if(!org.springframework.util.StringUtils.isEmpty(vo.getVdrWarehId())){
            psc.setVdrWarehId(Long.valueOf(vo.getVdrWarehId()));
        }
        if(!org.springframework.util.StringUtils.isEmpty(vo.getVdeWarehId())){
            psc.setVdeWarehId(Long.valueOf(vo.getVdeWarehId()));
        }
        if (StringUtils.isNotEmpty(vo.getVdrInvd())) {
            vo = this.updatePscVo(vo);
            psc.setVdrInvd(vo.getVdrInvd());
            psc.setSlcAutoGen(vo.getSlcAutoGen());
            psc.setSlcAutoChk(vo.getSlcAutoChk());
            psc.setSlcGen(vo.getSlcGen());
        } else if (StringUtils.isNotEmpty(vo.getVdeInvd())) {
            vo = slcServiceImpl.updateSlcVo(vo);
            psc.setVdeInvd(vo.getVdeInvd());
            psc.setPucAutoGen(vo.getPucAutoGen());
            psc.setPucAutoChk(vo.getPucAutoChk());
            psc.setPucGen(vo.getPucGen());
        }
        return pscMapper.updateByPrimaryKeySelective(psc);
    }

    /**
     * 采购合同分配接口
     *
     * @param vo
     * @return
     */
    @Override
    public void admeasure(PscVo vo) {
        if (vo.getImplByIns().equals("T") && vo.getProgress().equals("CK")
                && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
            List<CommonDtl> dtlList = pscDtlMapper.findByDiff(vo.getPscNum(), "");
            if (!dtlList.isEmpty()) {
                this.doAdmeasure(vo, dtlList, false);
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
        }
    }

    /**
     * 采购合同取消分配接口
     *
     * @param vo
     * @return
     */
    @Override
    public void reverseAdmeasure(PscVo vo) {
        if (vo.getImplByIns().equals("T") && vo.getProgress().equals("CK")
                && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
            List<CommonDtl> dtlList = pscDtlMapper.findByDiff(vo.getPscNum(), "");
            if (!dtlList.isEmpty()) {
                this.doAdmeasure(vo, dtlList, true);
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
        }
    }

    /**
     * 采购合同后天配货调整
     *
     * @param vo
     * @param dtlList
     * @param reversed
     * @return
     */
    @Override
    public void postAdmeasure(PscVo vo, List<CommonDtl> dtlList, boolean reversed) {
        if (vo.getImplByIns().equals("T") && (vo.getProgress().equals("CK")
                || vo.getProgress().equals("DD")) && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
            this.doAdmeasure(vo, dtlList, reversed);
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
        }
    }

    /**
     * 采购合同配货事件
     * 因为采购单中不能添加采购合同没有明细
     *
     * @param vo
     * @param reversed
     * @return
     */
    private void doAdmeasure(PscVo vo, List<CommonDtl> dtlList, boolean reversed) {
        if (this.contract(vo)) {
            Map<String, Object> map = new HashMap<>();
            //登记未决库存
            map.put("vo", vo);
            map.put("dtlList", dtlList);
            map.put("reversed", reversed);
            map.put("stkType", "EP");
            map.put("warehId", vo.getVdeWarehId());
            map.put("docType", "PUC");
            warehStkPgMapper.updateByAdmeasure(map);
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
        }
    }

    /**
     * 采购合同中作废事件
     *
     * @param vo
     * @param user
     */
    private void doAbolish(PscVo vo, SysUser user) throws Exception {
        if (!vo.getProgress().equals("PG") || !vo.getProgress().equals("C")
                || !vo.getProgress().equals("EK")) {
            Map<String, Object> map = new HashMap<>();
            //取消未决库存登记（预期库存/在途库存）
            List<String> stkList = new ArrayList<>();
            stkList.add("EP");
            map.put("stkList", stkList);
            this.cancel(vo, map);
            //监管往来
            boolean flag = purchaseService.judgePsc(vo.getPscNum());
            if (flag && !vo.getVdeFsclUnitId().equals(vo.getVenderId())) {
                purchaseService.supervise(vo, user);
            }

            //撤销入库任务登记
            if (vo.getImplByIns().equals("F")) {
                //删除入库
                warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUC", Long.valueOf(vo.getVendeeId()), vo.getDocNum()), user);
            }
        }
    }

    /**
     * 采购合同中重用事件
     *
     * @param vo
     * @param user
     */
    private void doReuse(PscVo vo, SysUser user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //非居间合同  登记未决
        if (this.contract(vo)) {
            //获取单据明细
            if (vo.getVdrInvd().equals("T") && vo.getImplByIns().equals("F")) {
                String sql = " ,CASE WHEN qty-deliv_qty IS NULL THEN qty ELSE qty-deliv_qty END qty ";
                List<PscDtl> dtlList = pscDtlMapper.selectByBill(vo.getPscNum(), sql);
                if (dtlList != null && dtlList.size() > 0) {
                    warehStk(vo, map, dtlList);
                    sql = " ,CASE WHEN deliv_qty-rcv_qty IS NULL THEN deliv_qty ELSE deliv_qty-rcv_qty END qty  ";
                    dtlList = pscDtlMapper.selectByBill(vo.getPscNum(), sql);
                    map.put("dtlList", dtlList);
                    map.put("stkType", "IT");
                    warehStkPgMapper.insertByDtl(map);
                }
            } else {
                String sql = " ,CASE WHEN qty-rcv_qty IS NULL THEN qty ELSE qty-rcv_qty END qty ";
                List<PscDtl> dtlList = pscDtlMapper.selectByBill(vo.getPscNum(), sql);
                if (dtlList != null && dtlList.size() > 0) {
                    warehStk(vo, map, dtlList);
                }
            }
        }

        //判断是否监管往来
        if (purchaseService.judgePsc(vo.getPscNum()) && !vo.getVdeFsclUnitId().equals(vo.getVenderId())) {
            //计算冻结金额
            BigDecimal debitValue = new BigDecimal("0");
            if (vo.getMfzEnabled().equals("T") && vo.getPsMfzSite().equals("CK") && vo.getDeposit().compareTo(debitValue) == 1
                    && vo.getTtlVal().compareTo(debitValue) == 1) {
                debitValue = (vo.getTtlVal().subtract(vo.getTtlRcvVal())).multiply(vo.getDeposit().divide(vo.getTtlVal()));
            }
            if (vo.getGmEnabled().equals("T")) {
                debitValue = debitValue.add(vo.getGuaMon());
            }
            //通过是否有往来账户
            Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVendeeId()), Long.valueOf(vo.getVenderId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
            if (ca != null) {
                caAccService.freeze(new CaAcc(null, null, "PUC", Long.valueOf(vo.getVendeeId()), vo.getDocNum()), user, ca, debitValue, new BigDecimal("0"), true);
            }
        }

        //撤销收发差异登记
        if (this.contract(vo) && vo.getSlcGen().equals("T")
                && vo.getImplByIns().equals("F")) {
            //查询出销售合同号
            Slc slc = slcMapper.selectByPscNum(vo.getPscNum());
            //删除收发差异
            tranDiffEvtService.deregister(new TranDiffEvt(Long.valueOf(slc.getUnitId()), "SLC", slc.getSlcNum(), vo.getUnitId(), "PUC", vo.getPucNum()));

        }

        //登记入库任务
        if (this.contract(vo) && StringUtils.NullEmpty(vo.getEndPscNum())
                && vo.getImplByIns().equals("F")) {
            purchaseUtils.generate("PUC", vo.getPscNum(), user);
        }

//        //调用销售合同重用方法
//        if(this.contract(vo)&&StringUtils.isNotEmpty(vo.getEndPscNum())
//                &&slcMapper.selectALL(new PscVo(vo.getPscNum())).get(0).getProgress().equals("CL")){
//            slcService.reusing(slcMapper.selectALL(new PscVo(vo.getPscNum())).get(0),user);
//        }

    }

    private void warehStk(PscVo vo, Map<String, Object> map, List<PscDtl> dtlList) {
        map.put("docType", "PUC");
        map.put("unitId", vo.getVendeeId());
        map.put("docNum", vo.getDocNum());
        map.put("warehId", vo.getVdeWarehId());
        map.put("dtlList", dtlList);
        map.put("stkType", "EP");
        warehStkPgMapper.insertByDtl(map);
    }

    //关闭私有方法
    private void doClose(PscVo vo, SysUser user) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //取消未决库存登记（预期库存/在途库存）
        //获取单据明细
        List<String> stkList = new ArrayList<>();
        stkList.add("EP");
        stkList.add("IT");
        map.put("stkList", stkList);
        this.cancel(vo, map);
        //撤销入库任务登记
        if (vo.getImplByIns().equals("F")) {
            //删除入库
            warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUC", Long.valueOf(vo.getVendeeId()), vo.getDocNum()), user);
            //判断是否登记收发差异
            if (vo.getSlcGen().equals("T") && (StringUtils.isNotEmpty(vo.getTtlDelivQty().toString()) ? vo.getTtlDelivQty() : new BigDecimal("0")).compareTo((StringUtils.isNotEmpty(vo.getTtlRcvQty().toString()) ? vo.getTtlRcvQty() : new BigDecimal("0"))) != 0) {
                purchaseService.register(vo);
            }
        }
        //监管往来
        boolean flag = purchaseService.judgePsc(vo.getPscNum());
        if (flag && !vo.getVdeFsclUnitId().equals(vo.getVenderId())) {
            purchaseService.supervise(vo, user);
        }
    }


    //取消未决公共方法
    private int cancel(PscVo vo, Map<String, Object> map) {
        String sql = " , qty ";
        List<PscDtl> dtlList = pscDtlMapper.selectByBill(vo.getPscNum(), sql);
        if (dtlList != null && dtlList.size() > 0) {
            map.put("docType", "PUC");
            map.put("unitId", vo.getVendeeId());
            map.put("docNum", vo.getDocNum());
            map.put("warehId", vo.getVdeWarehId());
            map.put("dtlList", dtlList);
            return warehStkPgMapper.deleteByDtl(map);
        }
        return 0;
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

    /**
     * 通过购销合同号查询超额比例
     *
     * @param pscNum
     * @return
     */
    @Override
    public Map<String, Object> getExbl(String pscNum) {
        Map<String, Object> map = new HashMap<>();
        Psc psc = pscMapper.selectByPrimaryKey(pscNum);
        if (psc != null) {
            map.put("exblRate", psc.getExblRate());//超额比例
//            map.put("splEnabled",psc.getSplEnabled());//是否允许增补商品
            return map;
        }
        return null;
    }

    /**
     * 通过购销合同号查询固定单价
     *
     * @param pscType
     * @return
     */
    @Override
    public Map<String, Object> getFixedPrice(String pscType, String pscNum) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.NullEmpty(pscType)) {
            Psc psc = pscMapper.selectByPrimaryKey(pscNum);
            if (psc != null) {
                PscType type = pscTypeMapper.selectByPrimaryKey(psc.getPscType());
                if (type != null) {
                    map.put("fixedUnitPrice", type.getFixedUnitPrice());//是否固定单价
                    return map;
                }
            }
        } else {
            PscType type = pscTypeMapper.selectByPrimaryKey(pscType);
            if (type != null) {
                map.put("fixedUnitPrice", type.getFixedUnitPrice());//是否固定单价
                return map;
            }
        }

        return null;
    }


    /**
     * 销售合同关闭通知采购合同供应商关闭事件
     *
     * @param vo
     * @param user
     */
    @Override
    public void venderClosed(PscVo vo, SysUser user) throws Exception {
        this.doClose(vo, user);
    }

    /**
     * 销售合同重用通知采购合同供应商重用事件
     *
     * @param vo
     * @param user
     */
    @Override
    public void venderReused(PscVo vo, SysUser user) throws Exception {
        this.reusing(vo, user);
    }

    /**
     * 销售合同通知采购合同供应商作废事件
     *
     * @param vo
     * @param user
     */
    @Override
    public void venderAbolished(PscVo vo, SysUser user) throws Exception {
        this.doAbolish(vo, user);
    }

    /**
     * 查询单条
     *
     * @param vo
     * @return
     */
    @Override
    public PscVo selectByOnly(PscVo vo) {
        return pucMapper.selectByOnly(vo);
    }

    /**
     * 查询采购合同可操作状态
     *
     * @param vo
     * @return
     */
    @Override
    public List<OperationVo> initButtons(PscVo vo) {
        List<PurKeyValue> keyValues = buttonCommonService.initButton(parameter);
        PscVo psc = pucMapper.selectByOnly(vo);
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
                        && StringUtils.NullEmpty(vo.getItmdPscNum()) && StringUtils.NullEmpty(vo.getExecPscNum())) {
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
                        && vo.getCancelled().equals("F") && vo.getTasksInDeliv() == 0 && vo.getTasksInRcv() == 0
                        && vo.getRenewed().equals("F") && vo.getSlFwdd().equals("F") && dtlList.isEmpty()) {
                    list.add(new OperationVo("反审", "T", reiterate));
                } else {
                    list.add(new OperationVo("反审", "F", reiterate));
                }
            }
            if (("suspend").equals(pur.getOptionValue())) {
                if (!vo.getProgress().equals("CL") && !vo.getProgress().equals("RD") && vo.getSuspended().equals("F")
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
                        && vo.getSlFwdd().equals("F") && vo.getTasksInDeliv() == 0 && vo.getTasksInRcv() == 0 && dtlList.isEmpty()
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
     * @return
     */
    @Override
    public PscVo selectObject(PscVo vo) {
        return pucMapper.selectObject(vo);
    }

    /**
     * 供货方发货通知
     *
     * @param vo
     * @param docUnitId 库存单组织id
     * @param docNum    库存单编号
     * @return
     */
    @Override
    public void venderDelivered(PscVo vo, Long docUnitId, String docNum, SysUser user) throws Exception {
        if (this.contract(vo) && vo.getImplByIns().equals("F")) {
            Map<String, Object> map = new HashMap<>();
            map.put("docType", "PUC");
            map.put("unitId", vo.getUnitId());
            map.put("docNum", vo.getPucNum());
            map.put("warehId", vo.getVdeWarehId());
            map.put("stbUnitId", docUnitId);
            map.put("stbNum", docNum);
            //库存登记
            if (vo.getMultiImpl().equals("T")) {
                //减少预期未决库存
                map.put("stkType", "EP");
                warehStkPgMapper.reduceStkPg(map);
            } else {
                //取消预期未决库存
                map.put("stkType", "EP");
                warehStkPgMapper.deleteStkPg(map);
            }
            //增加在途库存
            map.put("stkType", "IT");
            warehStkPgMapper.increaseStkPg(map);
            List<ProdClsDtlVo> VOdTL = pscDtlMapper.selectByDelivOrRcv(vo.getPscNum());
            if (vo.getMultiImpl().equals("F") || (vo.getMultiImpl().equals("T") && !pscDtlMapper.selectByDelivOrRcv(vo.getPscNum()).isEmpty())
                    || vo.getEndPscNum() != null) {
                //登记入库
                purchaseUtils.generate("PUC", vo.getPscNum(), user);
            }
        }
    }

    /**
     * 供货方取消发货
     *
     * @param vo
     * @param docUnitId 库存单组织id
     * @param docNum    库存单编号
     * @param user
     * @return
     */
    @Override
    public void venderReverseDelivered(PscVo vo, Long docUnitId, String docNum, SysUser user) throws Exception {
        if (this.contract(vo) && vo.getImplByIns().equals("F")) {
            Puc puc = pucMapper.selectByPscNum(vo.getPscNum());
            Map<String, Object> map = new HashMap<>();
            map.put("docType", "PUC");
            map.put("unitId", puc.getUnitId());
            map.put("docNum", puc.getPucNum());
            map.put("warehId", vo.getVdeWarehId());
            map.put("stbUnitId", docUnitId);
            map.put("stbNum", docNum);
            //库存登记
            if (vo.getMultiImpl().equals("T")) {
                //增加预期库存
                map.put("stkType", "EP");
                warehStkPgMapper.increaseStkPg(map);
                //减少在途未决库存
                map.put("stkType", "IT");
                warehStkPgMapper.reduceStkPg(map);
            } else {
                //减少在途未决库存
                map.put("stkType", "IT");
                warehStkPgMapper.deleteStkPg(map);
            }
            //判断是否撤销入库任务
            if (vo.getMultiImpl().equals("F") || (vo.getMultiImpl().equals("T") && !pscDtlMapper.selectDtlByStb(vo.getPscNum(), docUnitId, docNum).isEmpty()
            )) {
                //撤销出入库任务登记
                warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUC", puc.getUnitId(), puc.getPucNum()), user);
            }
        }
    }

    /**
     * 采购合同开始收货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @return
     */
    @Override
    public int startReceive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        PscVo vo = pucMapper.selectObject(new PscVo(srcDocNum, "", srcDocUnitId + ""));
        if (vo != null) {
            if ((vo.getProgress().equals("CK") || vo.getProgress().equals("DD")) && vo.getSuspended().equals("F")
                    && vo.getCancelled().equals("F")) {
                if (vo.getMultiImpl().equals("F") && vo.getImplByIns().equals("F")) {
                    vo.setProgress("RG");
                } else {
                    vo.setProgress("CK");
                }
                //收货任务数+1
                if (vo.getImplByIns().equals("F")) {
                    vo.setTasksInRcv((org.springframework.util.StringUtils.isEmpty(vo.getTasksInRcv()) ? vo.getTasksInRcv() : 0) + 1);
                }
                //非居间合同
                if (this.contract(vo) && vo.getMultiImpl().equals("F") && vo.getImplByIns().equals("F")) {
                    //撤销出入库任务登记
                    //warehRcvTaskMapper.deleteWarehRcvTask(new WarehRcvTask(null, "PUC", Long.valueOf(vo.getVendeeId()), vo.getDocNum()));
                    warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUC", Long.valueOf(vo.getVendeeId()), vo.getDocNum()), user);
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
     * 采购合同终止收货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int stopReceive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        PscVo vo = pucMapper.selectObject(new PscVo(srcDocNum, "", srcDocUnitId + ""));
        if (vo != null) {
            if ((vo.getProgress().equals("RG") || ((vo.getMultiImpl().equals("T") || vo.getImplByIns().equals("T"))
                    && vo.getProgress().equals("CK"))) && vo.getSuspended().equals("F")) {
                if (vo.getVdrInvd().equals("T") && vo.getImplByIns().equals("F") && vo.getMultiImpl().equals("F")) {
                    vo.setProgress("DD");
                } else {
                    vo.setProgress("CK");
                }
                //收货任务-1
                if (vo.getImplByIns().equals("F")) {
                    vo.setTasksInRcv((org.springframework.util.StringUtils.isEmpty(vo.getTasksInRcv()) ? vo.getTasksInRcv() : 0) - 1);
                }
                //登记入库任务
                if (this.contract(vo) && vo.getImplByIns().equals("F") && StringUtils.isNotEmpty(vo.getEndPscNum())) {
                    purchaseUtils.generate("PUC", vo.getPscNum(), user);
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
     * 采购合同收货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @param docUnitId    入库单组织id
     * @param docNum       入库单据编号
     * @param user         用户
     * @return
     * @throws Exception
     */
    @Override
    public int receive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        //查询原始单据数据 将收货信息同步
        PscVo vo = pucMapper.getPucByStb(srcDocUnitId, srcDocNum, docUnitId, docNum, true);
        //单据信息
        Stb stb = stbService.getStbByPk(new Stb(docUnitId, docNum));
        if (vo != null) {
            if ((vo.getProgress().equals("RG") || ((vo.getMultiImpl().equals("T") || vo.getImplByIns().equals("T"))
                    && vo.getProgress().equals("CK"))) && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                if (vo.getMultiImpl().equals("T") || vo.getImplByIns().equals("T")) {
                    vo.setProgress("CK");
                } else {
                    vo.setProgress("RD");
                }

                List<PscDtl> dtlList = null;
                //通过sql语句查询更新明细
                if (vo.getSlcGen().equals("T")) {
                    dtlList = pscDtlMapper.selectByStbDtl(vo.getPscNum(), docUnitId, docNum, true, "PUC");
                } else {
                    dtlList = pscDtlMapper.findByStbDtl(vo.getPscNum(), docUnitId, docNum, true);
                }
                if (dtlList != null && dtlList.size() > 0) {
                    pscDtlMapper.updateDtlList(dtlList);
                }
                //判断是否继续收货
                int judge = 0; //0 不继续收货  1 继续收货
                SysParameter sys = parameterMapper.findById("PURCHASE_CONTRACT_SDMR_ENABLED");
                if (sys.getParmVal().equals("T")) {
                    if (vo.getMultiImpl().equals("F") && pscDtlService.judge(vo.getPscNum()).isEmpty()) {
                        judge = 1;
                    }
                }

                //查询stb明细
                List<StbDtlVo> stbDtlVo = stbDtlService.getStbDtlList(new StbDtl(docUnitId, docNum));

                //非居间合同
                if (this.contract(vo)) {
                    //查询销售协议获取退货控制类别
                    Psa psa = psaMapper.selectByVdr(new Psa(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId())));
                    purchaseService.pscAccumulate(psa, docUnitId, docNum, true);
                    //登记未决库存
                    if (vo.getImplByIns().equals("F")) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("docType", "PUC");
                        map.put("unitId", vo.getVendeeId());
                        map.put("docNum", vo.getDocNum());
                        map.put("warehId", vo.getVdeWarehId());

                        //供应商介入就是在途  否则就是预期
                        if (vo.getVdrInvd().equals("T")) {
                            map.put("stkType", "IT");
                        } else {
                            map.put("stkType", "EP");
                        }
                        //多次执行 or 继续收货
                        if (vo.getMultiImpl().equals("T") || judge == 1) {
                            map.put("dtlList", stbDtlVo);
                            //负数登记
                            map.put("flag", "stb_j");
                            warehStkPgMapper.updateByNegDtl(map);
                        } else {
                            //注销
                            List<String> stkList = new ArrayList<>();
                            stkList.add(map.get("stkType").toString());
                            map.put("stkList", stkList);
                            map.put("dtlList", dtlList);
                            warehStkPgMapper.deleteByDtl(map);
                        }
                    }
                }
                //判断是否监管往来操作
                if (purchaseService.judgePsc(vo.getPscNum()) && !vo.getVdeFsclUnitId().equals(vo.getVenderId())) {
                    //多次执行 or 继续收货
                    if (vo.getMultiImpl().equals("T") || judge == 1) {
                        BigDecimal debitValue = new BigDecimal("0");
                        if (vo.getMfzEnabled().equals("T") && vo.getPsMfzSite().equals("CK") && vo.getDeposit().compareTo(debitValue) == 1
                                && vo.getTtlVal().compareTo(debitValue) == 1) {
                            debitValue = debitValue.subtract(stb.getTtlVal()).multiply(vo.getDeposit().divide(vo.getTtlVal()));
                        }
                        //冻结金额非0 冻结往来账户余额
                        if (debitValue.compareTo(new BigDecimal("0")) != 0) {
                            //通过供应商id与采购商id查询是否有往来账户
                            Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                            if (ca != null) {
                                caAccService.freeze(new CaAcc(null, 0L, "PUC", ca.getUnitId(), vo.getDocNum()), user, ca, debitValue, new BigDecimal("0"), false);
                            }
                        }
                    }
                    //注销往来账余额冻结
                    if (vo.getMultiImpl().equals("F") && judge == 0) {
                        purchaseService.superviseBySales(vo, user);
                    }
                }

                vo.setTasksInRcv(vo.getTasksInRcv() - 1);
                if (vo.getSlcGen().equals("T")) {
                    //通知收货事件
                    pscMapper.updateByPscVo(vo);
                    slcService.vendeeReceived(vo, stb);
                } else {
                    vo.setTtlDelivQty(vo.getTtlRcvQty());
                    vo.setTtlDelivBox(vo.getTtlRcvBox());
                    vo.setTtlDelivVal(vo.getTtlRcvVal());
                    vo.setTtlDelivTax(vo.getTtlRcvTax());
                    vo.setTtlDelivMkv(vo.getTtlRcvMkv());
                }
                //判断是否关闭采购合同
                int close = 0;//判断是否自动关闭
                SysParameter sysPa = parameterMapper.findById("PSC_AUTO_CLOSE_WHEN_EXPECTATION_REACHED");
                if ((vo.getImplByIns().equals("T") || vo.getMultiImpl().equals("T")) && vo.getTasksInDeliv().equals(0)
                        && vo.getTasksInRcv().equals(0) && (sysPa.getParmVal().equals("T") && pscDtlMapper.findByQtyEqRcv(vo.getPscNum(), " pDtl.qty > IFNULL( pDtl.rcv_qty, 0 ) ").isEmpty()
                        || vo.getMultiImpl().equals("F"))) {
                    close = 1;
                    this.close(vo, user);
                }

                if (this.contract(vo)) {
                    //判断是否登记收发差异
                    if (vo.getSlcGen().equals("T") && vo.getImplByIns().equals("F") && vo.getMultiImpl().equals("F")
                            && judge == 0 && pscDtlMapper.findByQtyEqRcv(vo.getPscNum(), " IFNULL( pDtl.deliv_qty, 0 ) <> IFNULL( pDtl.rcv_qty, 0 ) ").isEmpty()) {
                        purchaseService.register(vo);
                    }
                    //判断撤销还是登记入库任务
                    if (vo.getImplByIns().equals("F") && vo.getMultiImpl().equals("T") && StringUtils.NullEmpty(vo.getEndPscNum())
                            && close == 0) {
                        //供应商介入并且不存在发货数大于到货数记录
                        if (pscDtlMapper.findByQtyEqRcv(vo.getPscNum(), " IFNULL( pDtl.deliv_qty, 0 ) > IFNULL( pDtl.rcv_qty, 0 ) ").isEmpty()) {
                            //撤销入库任务登记
                            warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUC", Long.valueOf(vo.getVendeeId()), vo.getDocNum()), user);
                        } else {
                            pscMapper.updateByPscVo(vo);
                            //登记入库任务
                            purchaseUtils.generate("PUC", vo.getPscNum(), user);
                        }
                    }

                    if (vo.getImplByIns().equals("F") && vo.getMultiImpl().equals("F") && StringUtils.NullEmpty(vo.getEndPscNum())
                            && judge == 1) {
                        if (vo.getVdrInvd().equals("T")) {
                            vo.setProgress("CK");
                        } else {
                            vo.setProgress("DD");
                        }
                        pscMapper.updateByPscVo(vo);
                        //登记入库任务
                        purchaseUtils.generate("PUC", vo.getPscNum(), user);
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
     * 采购合同取消收货
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
        //查询原始单据数据 将收货信息同步
        PscVo vo = pucMapper.getPucByStb(srcDocUnitId, srcDocNum, docUnitId, docNum, false);
        //单据信息
        Stb stb = stbService.getStbByPk(new Stb(docUnitId, docNum));
        SysParameter sysPa = parameterMapper.findById("PURCHASE_CONTRACT_SDMR_ENABLED");
        if (vo != null) {
            if (((vo.getProgress().equals("RD") || ((vo.getMultiImpl().equals("T") || vo.getImplByIns().equals("T"))
                    && vo.getProgress().equals("CK"))) || sysPa.getParmVal().equals("T")
                ) && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                //&& (vo.getProgress().equals("DD") || vo.getProgress().equals("CK"))
                String oldProg = vo.getProgress();
                if (vo.getMultiImpl().equals("T") || vo.getImplByIns().equals("T")) {
                    vo.setProgress("CK");
                } else {
                    vo.setProgress("RG");
                }

                //通过sql语句查询更新明细
                List<PscDtl> dtlList = null;
                if (vo.getSlcGen().equals("T")) {
                    dtlList = pscDtlMapper.selectByStbDtl(vo.getPscNum(), docUnitId, docNum, false, "PUC");
                } else {
                    dtlList = pscDtlMapper.findByStbDtl(vo.getPscNum(), docUnitId, docNum, false);
                }
                if (dtlList != null && dtlList.size() > 0) {
                    pscDtlMapper.updateDtlList(dtlList);
                }

                //非居间合同
                if (this.contract(vo)) {
                    //查询销售协议获取退货控制类别
                    Psa psa = psaMapper.selectByVdr(new Psa(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId())));
                    purchaseService.pscAccumulate(psa, docUnitId, docNum, false);
                    //判断是否登记未决
                    if (vo.getImplByIns().equals("F")) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("docType", "PUC");
                        map.put("unitId", vo.getVendeeId());
                        map.put("docNum", vo.getDocNum());
                        map.put("warehId", vo.getVdeWarehId());
                        if (vo.getVdrInvd().equals("T")) {
                            map.put("stkType", "IT");
                        } else {
                            map.put("stkType", "BK");
                        }
                        //查询stb明细
                        List<StbDtlVo> stbDtlVo = stbDtlService.getStbDtlList(new StbDtl(docUnitId, docNum));
                        if (vo.getMultiImpl().equals("T")) {
                            //本次数量
                            map.put("flag", "stb_a");
                            map.put("dtlList", stbDtlVo);
                        } else {
                            //订单数量
                            map.put("flag", "psc_vdr_a");
                            if (vo.getVdrInvd().equals("T")) {
                                map.put("vdrInvd", 1);
                            } else {
                                map.put("vdrInvd", 0);
                            }
                            map.put("dtlList", stbDtlVo);
                        }
                        warehStkPgMapper.updateByNegDtl(map);
                    }
                }

                //判断是否监管往来操作
                if (purchaseService.judgePsc(vo.getPscNum()) && !vo.getVdeFsclUnitId().equals(vo.getVenderId())) {
                    BigDecimal debitValue = new BigDecimal("0");
                    if (vo.getMultiImpl().equals("T") || !oldProg.equals("RD")) {
                        if (vo.getMfzEnabled().equals("T") && vo.getPsMfzSite().equals("CK") && vo.getDeposit().compareTo(debitValue) == 1
                                && vo.getTtlVal().compareTo(debitValue) != 0) {
                            debitValue = debitValue.subtract(stb.getTtlVal()).multiply(vo.getDeposit().divide(vo.getTtlVal()));
                        }
                    } else {
                        if (vo.getMfzEnabled().equals("T") && vo.getPsMfzSite().equals("CK")) {
                            debitValue = vo.getDeposit();
                        }
                        if (vo.getGmEnabled().equals("T")) {
                            debitValue = debitValue.add(vo.getGuaMon());
                        }
                    }
                    //冻结金额非0
                    if (debitValue.compareTo(new BigDecimal("0")) != 0) {
                        //通过供应商id与采购商id查询是否有往来账户
                        Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                        if (ca != null) {
                            caAccService.freeze(new CaAcc(0L, 0L, "PUC", ca.getUnitId(), vo.getDocNum()), user, ca, debitValue, new BigDecimal("0"), true);
                        }
                    }
                }

                vo.setTasksInRcv(org.springframework.util.StringUtils.isEmpty(vo.getTasksInRcv()) ? 0 : vo.getTasksInRcv() + 1);
                if (vo.getSlcGen().equals("F")) {
                    vo.setTtlDelivQty(vo.getTtlRcvQty());
                    vo.setTtlDelivBox(vo.getTtlRcvBox());
                    vo.setTtlDelivVal(vo.getTtlRcvVal());
                    vo.setTtlDelivTax(vo.getTtlRcvTax());
                    vo.setTtlDelivMkv(vo.getTtlRcvMkv());
                } else {
                    slcService.vendeeReverseReceived(vo, stb);
                }

                if (this.contract(vo)) {
                    //判断是否撤销收发差异登记
                    if (vo.getSlcGen().equals("T") && vo.getImplByIns().equals("F") && vo.getMultiImpl().equals("F")) {
                        //查询出退购合同号
                        Slc slc = slcMapper.selectByPscNum(vo.getPscNum());
                        //删除收发差异
                        tranDiffEvtService.deregister(new TranDiffEvt(Long.valueOf(slc.getUnitId()), "SLC", slc.getSlcNum(), vo.getUnitId(), "PUC", vo.getPucNum()));
                    }

                    //判断登记入库任务
                    if (vo.getImplByIns().equals("F") && vo.getMultiImpl().equals("T") && StringUtils.NullEmpty(vo.getEndPscNum())
                            && vo.getVdrInvd().equals("T") && pscDtlMapper.findByQtyEqRcv(vo.getPscNum(), " IFNULL( pDtl.deliv_qty, 0 ) > IFNULL( pDtl.rcv_qty, 0 ) ").isEmpty()) {
                        //登记入库任务
                        purchaseUtils.generate("PUC", vo.getPscNum(), user);
                    }
                    //判断撤销入库任务
                    if (vo.getImplByIns().equals("F") && vo.getMultiImpl().equals("F") && StringUtils.NullEmpty(vo.getEndPscNum())
                            && !oldProg.equals("RD")) {
                        //撤销出入库任务登记
                        warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUC", Long.valueOf(vo.getVendeeId()), vo.getDocNum()), user);
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
     * 退货发货
     *
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    @Override
    public int returnDeliver(String pscNum, Long docUnitId, String docNum) {
        //商品不存在且商品的到货数量小于退购数量  报异常
        List<PscDtl> list = pscDtlMapper.getException(pscNum, docUnitId, docNum, " pDtl.rcv_qty < (IFNULL( pDtl.rp_qty, 0 ) + sDTL.qty) ");
        if (list != null && list.size() > 0) {
            throw new ServiceException(JsonResultCode.FAILURE, "退购商品超出限制！");
        }
        //累计退购信息到明细中
        pscDtlMapper.updateByStbToRp(pscNum, docUnitId, docNum, true);
        return pscMapper.updateByStbToRp(pscNum, docUnitId, docNum, true);
    }

    /**
     * 退货取消发货
     *
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    @Override
    public int reverseReturnDeliver(String pscNum, Long docUnitId, String docNum) {
        //累计退购信息到明细中
        pscDtlMapper.updateByStbToRp(pscNum, docUnitId, docNum, false);
        return pscMapper.updateByStbToRp(pscNum, docUnitId, docNum, false);
    }

    /**
     * 开始分配
     *
     * @param vo
     */
    @Override
    public void startAdmeasure(PscVo vo) {
        if (vo.getImplByIns().equals("T") && (vo.getProgress().equals("CK") || vo.getProgress().equals("DD")) && vo.getSuspended().equals("F")
                && vo.getCancelled().equals("F")) {
            pscMapper.updateTaskAdd(vo.getPscNum());
        }
    }

    /**
     * 终止分配
     *
     * @param vo
     */
    @Override
    public void stopAdmeasure(PscVo vo) {
        if (vo.getImplByIns().equals("T") && (vo.getProgress().equals("CK") || vo.getProgress().equals("DD")) && vo.getSuspended().equals("F")
                && vo.getCancelled().equals("F")) {
            pscMapper.updateTaskReduce(vo.getPscNum());
        }
    }
}
