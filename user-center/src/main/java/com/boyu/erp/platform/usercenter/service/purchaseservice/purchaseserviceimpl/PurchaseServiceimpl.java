package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boyu.erp.platform.usercenter.components.SpringContextUtil;
import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.basic.*;
import com.boyu.erp.platform.usercenter.entity.purchase.Psa;
import com.boyu.erp.platform.usercenter.entity.purchase.Psc;
import com.boyu.erp.platform.usercenter.entity.purchase.Rtc;
import com.boyu.erp.platform.usercenter.entity.sales.Slc;
import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.CaMapper;
import com.boyu.erp.platform.usercenter.mapper.basic.CompanyMapper;
import com.boyu.erp.platform.usercenter.mapper.basic.PartnerMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.*;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SloMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SrcMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitOwnerMapper;
import com.boyu.erp.platform.usercenter.model.system.CommonPopupModel;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.caservice.CaAccService;
import com.boyu.erp.platform.usercenter.service.caservice.TranDiffEvtService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.UnitProdClsService;
import com.boyu.erp.platform.usercenter.service.system.*;
import com.boyu.erp.platform.usercenter.service.warehouse.GdnExternalCiteService;
import com.boyu.erp.platform.usercenter.utils.SpringUtils;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.purchase.*;
import com.boyu.erp.platform.usercenter.vo.sales.ProdClsDtlVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehSrcInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Classname PurchaseServiceimpl
 * @Description TODO
 * @Date 2019/8/22 17:36
 * @Created wz
 */
@Slf4j
@Service
@Transactional
public class PurchaseServiceimpl implements PurchaseService {

    @Autowired
    SpringContextUtil springContextUtil;
    @Autowired
    private SlcMapper slcMapper;
    @Autowired
    private SloMapper sloMapper;
    @Autowired
    private PrcMapper prcMapper;
    @Autowired
    private PscDtlMapper pscDtlMapper;
    @Autowired
    private PsoDtlMapper psoDtlMapper;
    @Autowired
    private RtcDtlMapper rtcDtlMapper;
    @Autowired
    private SysRefNumDtlSerivce sysRefNumDtlSerivce;
    @Autowired
    private PartnerMapper partnerMapper;
    @Autowired
    private SysUnitOwnerMapper sysUnitOwnerMapper;
    @Autowired
    private CaMapper caMapper;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private SysUnitMapper sysUnitMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private PscMapper pscMapper;
    @Autowired
    private CaAccService caAccService;
    @Autowired
    private RtcMapper rtcMapper;
    @Autowired
    private PucMapper pucMapper;
    @Autowired
    private PuoMapper puoMapper;
    @Autowired
    private SrcMapper srcMapper;
    @Autowired
    private RtqQtaService rtqQtaService;
    @Autowired
    private RtvQtaService rtvQtaService;
    @Autowired
    private TranDiffEvtService tranDiffEvtService;
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private UnitProdClsService UnitProdClsService;
    @Autowired
    private GdnExternalCiteService gdnExternalCiteService;
    @Autowired
    private FieldUtils fieldUtils;

    /**
     * 通过类型，原始单据组织id，原始单据号查询 原始单据信息
     *
     * @param type
     * @param unitId
     * @param num
     * @return
     */
    @Override
    public Purchase selectBill(String type, String unitId, String num) {
        if (("PUC").equals(type)) {
            PscVo psc = pucMapper.selectObject(new PscVo(num, "", unitId));
            if (psc != null) {
                return psc;
            }
        } else if (("PUO").equals(type)) {
            PsoVo pso = puoMapper.selectObject(new PsoVo(num, "", unitId));
            if (pso != null) {
                return pso;
            }
        } else if (("SRC").equals(type)) {
            PrcVo prc = srcMapper.selectObject(new PrcVo("", num, unitId));
            if (prc != null) {
                return prc;
            }
        } else if (("SLC").equals(type)) {
            PscVo psc = slcMapper.selectObject(new PscVo("", num, unitId));
            if (psc != null) {
                return psc;
            }
        } else if (("SLO").equals(type)) {
            PsoVo pso = sloMapper.selectObject(new PsoVo("", num, unitId));
            if (pso != null) {
                return pso;
            }
        } else if (("PRC").equals(type)) {
            PrcVo prc = prcMapper.selectObject(new PrcVo(num, "", unitId));
            if (prc != null) {
                return prc;
            }
        }
        return null;
    }

    /**
     * 2
     * 通过类型，原始单据组织id，原始单据号查询 原始单据信息
     *
     * @param type
     * @param unitId
     * @param num
     * @return
     */
//    @Override
//    public WarehSrcInfoVo selectWarehSrc(String type, String unitId, String num) {
//        WarehSrcInfoVo info = null;
//        if (("PUC").equals(type)) {
//            info = pucMapper.selectByWareh(new PscVo(num, "", unitId));
//        } else if (("PUO").equals(type)) {
//            info = puoMapper.selectByWareh(new PsoVo(num, "", unitId));
//        } else if (("SRC").equals(type)) {
//            info = srcMapper.selectByWareh(new PrcVo("", num, unitId));
//        } else if (("SLC").equals(type)) {
//            info = slcMapper.selectByWareh(new PscVo("", num, unitId));
//        } else if (("SLO").equals(type)) {
//            info = sloMapper.selectByWareh(new PsoVo("", num, unitId));
//        } else if (("PRC").equals(type)) {
//            info = prcMapper.selectByWareh(new PrcVo(num, "", unitId));
//        }
//        return info;
//    }

    /**
     * 3
     * 通过类型，原始单据组织id，原始单据号查询 原始单据信息(最新)
     *
     * @param type
     * @param unitId
     * @param num
     * @return
     */
    @Override
    public WarehSrcInfoVo selectWarehSrc(String type, String unitId, String num) {
        WarehSrcInfoVo info = null;
        if (("PUC").equals(type)) {
            info = pucMapper.findByWareh(new PscVo(num, "", unitId));
        } else if (("PUO").equals(type)) {
            info = puoMapper.findByWareh(new PsoVo(num, "", unitId));
        } else if (("SRC").equals(type)) {
            info = srcMapper.findByWareh(new PrcVo("", num, unitId));
        } else if (("SLC").equals(type)) {
            info = slcMapper.findByWareh(new PscVo("", num, unitId));
        } else if (("SLO").equals(type)) {
            info = sloMapper.selectByWareh(new PsoVo("", num, unitId));
        } else if (("PRC").equals(type)) {
            info = prcMapper.findByWareh(new PrcVo(num, "", unitId));
        }
        return info;
    }

    /**
     * 查询明细相关数据
     *
     * @param type 单据类别
     * @param num  单据明细编号
     * @return
     */
    @Override
    public List<CommonDtl> selectBillInfo(String type, String num) {
        List<CommonDtl> dtlList = null;
        if (("SLC").equals(type) || ("PUC").equals(type)) {
            List<PscDtlVo> list = pscDtlMapper.findByPscNum(new PscDtlVo(num));
            if (list != null && list.size() > 0) {
                dtlList = new ArrayList<>();
                dtlList.addAll(list);
            }
        } else if (("SLO").equals(type) || ("PUO").equals(type)) {
            List<PsoDtlVo> list = psoDtlMapper.findByPuoNum(new PsoDtlVo(num));
            if (list != null && list.size() > 0) {
                dtlList = new ArrayList<>();
                dtlList.addAll(list);
            }
        } else if (("PRC").equals(type) || ("SRC").equals(type)) {
            List<RtcDtlVo> list = rtcDtlMapper.findByRtcNum(new RtcDtlVo(num));
            if (list != null && list.size() > 0) {
                dtlList = new ArrayList<>();
                dtlList.addAll(list);
            }
        }
        return dtlList;
    }

    /**
     * 查询明细相关数据
     *
     * @param type 单据类别
     * @param num  单据明细编号
     * @param num  出入库单据中重新选择明细时的商品（存在选择多条）没有就传null
     * @return
     */
    @Override
    public List<CommonDtl> selectBillInfo2(String type, String num, Long prodId) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("num", num);
        map.put("prodId", prodId);
        List<CommonDtl> dtlList = null;
        if (("SLC").equals(type) || ("PUC").equals(type)) {
            dtlList = pscDtlMapper.findByDtlInfo(map);
        } else if (("SLO").equals(type) || ("PUO").equals(type)) {
            dtlList = psoDtlMapper.findByDtlInfo(map);
        } else if (("PRC").equals(type) || ("SRC").equals(type)) {
            dtlList = rtcDtlMapper.findByDtlInfo(map);
        }
        return dtlList;
    }

    /**
     * 新增采购商，供应商时，获取unit_id
     */
    @Override
    public Long addUnitId(String refNumId, SysUser user) {
        Long num = 0L;
        //查询编号生成unit_id
        SysRefNumDtl srnd = new SysRefNumDtl();
        srnd.setRefNumId("UNIT_ID");
        SysRefNumDtl sys = sysRefNumDtlSerivce.getLastStep(srnd);
        num = sys.getLastNum() + sys.getStepSize();
        //更新数据
        srnd = new SysRefNumDtl();
        srnd.setRefNumId("UNIT_ID");
        srnd.setLastNum(num);
        srnd.setUpdateBy(user.getPrsnl().getPrsnlId());
        srnd.setUpdateTime(new Date());
        sysRefNumDtlSerivce.updateRefNumDtl(srnd);

        return num;
    }

    //新增或修改伙伴信息
    @Override
    public void updatePartner(Partner partner) {
        Partner partnerByKey = partnerMapper.selectByPrimaryKey(partner);
        //判断是否存在伙伴 存在就修改 不存在就新增
        if (partnerByKey != null) {
            partnerMapper.updateByPrimaryKeySelective(partner);
        } else {
            partnerMapper.insertSelective(partner);
        }
    }

    /**
     * 属主是否修改
     */
    public void updateOwner(SysUnitOwner sysUnitOwner) {
        SysUnitOwner owner = sysUnitOwnerMapper.selectUnitOwner(sysUnitOwner);
        //查询当前组织下是否存在相同编号 未删除记录
        SysUnitOwner unitOwner = sysUnitOwnerMapper.selectUnitOwnerByNum(sysUnitOwner);
        if (unitOwner != null) {
            if (unitOwner.getDeleted().equals("T")) {
                if (owner != null) {
                    if (owner.getUnitId().equals(unitOwner.getUnitId())) {
                        sysUnitOwnerMapper.updateUnitOwner(sysUnitOwner);
                    } else {
                        sysUnitOwnerMapper.deleteUnitOwner(unitOwner);
                        sysUnitOwnerMapper.updateUnitOwner(sysUnitOwner);
                    }
                } else {
                    sysUnitOwnerMapper.deleteUnitOwner(unitOwner);
                    sysUnitOwnerMapper.updateUnitOwner(sysUnitOwner);
                }
            } else {
                if (!unitOwner.getUnitId().equals(sysUnitOwner.getUnitId())) {
                    throw new ServiceException(ResultCode.DATA_REPEAT, "当前组织中存在重复编号，无法添加!");
                } else {
                    sysUnitOwnerMapper.updateUnitOwner(sysUnitOwner);
                }
            }
        } else {
            if (owner != null) {
                sysUnitOwnerMapper.updateUnitOwner(sysUnitOwner);
            } else {
                sysUnitOwnerMapper.insertUnitOwner(sysUnitOwner);
            }
        }
    }

    /**
     * 私有方法 创建默认往来账户
     */
    public void account(Long unitId, Long ownerId, Long oprId, String templetCode, String balDir, SysUser user) {
        //首先判断FSCL_UNIT_ID是不是UNIT_ID
        Company com = companyMapper.selectByPrimaryKey(unitId);
        if (com.getFsclUnitId() != null) {
            if (com.getFsclUnitId().equals(unitId)) {
                SysParameter doMain = parameterMapper.findById("TEMPLET_DOMAIN_ID");
                SysParameter code = parameterMapper.findById(templetCode);
                if (doMain != null && code != null) {
                    SysParameter caAuto = parameterMapper.findById("CA_AUTO_GENERATE");
                    //判断是否自动生成往来账户
                    if (caAuto.getParmVal().equals("T")) {
                        //首先判断是否已存在该账户的往来账户
                        Ca ca = caMapper.selectByCaUnitId(new Ca(ownerId, unitId));
                        if (ca == null) {
                            //查询模板组织
                            SysUnit sysUnit = sysUnitMapper.selectByUnitCode(code.getParmVal());
                            if (sysUnit != null) {
                                String caId = sysRefNumService.viceNum(new SysUser(ownerId, user.getPrsnl().getPrsnlId()), "CA_ID");
                                SysRefNumDtl s = new SysRefNumDtl();
                                s.setLastNum(Long.valueOf(caId));
                                s.setIsDel(Byte.valueOf("1"));
                                s.setUpdateBy(oprId);
                                s.setUpdateTime(new Date());
                                if (!caId.equals("1")) {
                                    sysRefNumDtlSerivce.updateRefNumDtl(s);
                                } else {
                                    sysRefNumDtlSerivce.addRefNumDtl(s);
                                }


                                ca = new Ca();
                                ca.setUnitId(sysUnit.getUnitId());
                                ca.setCaId(Long.parseLong("1"));

                                Ca caKey = caMapper.selectByPrimaryKey(ca);

                                caKey.setUnitId(ownerId);
                                caKey.setCaId(Long.valueOf(caId));
                                caKey.setCaUnitId(unitId);
                                caKey.setCaType(parameterMapper.findById("PS_CA_TYPE").getParmVal());
                                caKey.setBalDir(balDir);
                                caKey.setBloMode("A");
                                caKey.setOprId(oprId);
                                caKey.setUpdTime(new Date());
                                caMapper.insertSelective(caKey);

                            }
                        } else {
                            if (ca.getBalDir().equals(balDir)) {
                                ca.setBalDir("B");
                            }
                            ca.setCaStatus("A");
                            caMapper.updateByPrimaryKeySelective(ca);
                        }
                    }
                }
            }
        }
    }

    /**
     * 私有方法 创建默认往来账户
     */
    public void accountBy(Long unitId, Long ownerId, Long oprId, String templetCode, String balDir, SysUser user) {
        //首先判断FSCL_UNIT_ID是不是UNIT_ID
        Company com = companyMapper.selectByPrimaryKey(unitId);
        if (com.getFsclUnitId() != null) {
            if (com.getFsclUnitId().equals(unitId)) {
                SysParameter doMain = parameterMapper.findById("TEMPLET_DOMAIN_ID");
                SysParameter code = parameterMapper.findById(templetCode);
                if (doMain != null && code != null) {
                    SysParameter caAuto = parameterMapper.findById("CA_AUTO_GENERATE");
                    //判断是否自动生成往来账户
                    if (caAuto.getParmVal().equals("T")) {
                        //首先判断是否已存在该账户的往来账户
                        Ca ca = caMapper.selectByCaUnitId(new Ca(unitId, ownerId));
                        if (ca == null) {
                            //查询模板组织
                            SysUnit sysUnit = sysUnitMapper.selectByUnitCode(code.getParmVal());
                            if (sysUnit != null) {
                                String caId = sysRefNumService.viceNum(new SysUser(unitId, user.getPrsnl().getPrsnlId()), "CA_ID");
                                SysRefNumDtl s = new SysRefNumDtl();
                                s.setLastNum(Long.valueOf(caId));
                                s.setIsDel(Byte.valueOf("1"));
                                s.setUpdateBy(oprId);
                                s.setUpdateTime(new Date());
                                if (!caId.equals("1")) {
                                    sysRefNumDtlSerivce.updateRefNumDtl(s);
                                } else {
                                    sysRefNumDtlSerivce.addRefNumDtl(s);
                                }


                                ca = new Ca();
                                ca.setUnitId(sysUnit.getUnitId());
                                ca.setCaId(Long.parseLong("1"));

                                Ca caKey = caMapper.selectByPrimaryKey(ca);

                                caKey.setUnitId(unitId);
                                caKey.setCaId(Long.valueOf(caId));
                                caKey.setCaUnitId(ownerId);
                                caKey.setCaType(parameterMapper.findById("PS_CA_TYPE").getParmVal());
                                caKey.setBalDir(balDir);
                                caKey.setBloMode("A");
                                caKey.setOprId(oprId);
                                caKey.setUpdTime(new Date());
                                caMapper.insertSelective(caKey);

                            }
                        } else {
                            if (ca.getBalDir().equals(balDir)) {
                                ca.setBalDir("B");
                            }
                            ca.setCaStatus("A");
                            caMapper.updateByPrimaryKeySelective(ca);
                        }
                    }
                }
            }
        }
    }

    //用购销合同编号判断是否监管往来
    @Override
    public boolean judgePsc(String pscNum) {
        boolean flag = false;
        SysParameter sysPar = parameterMapper.findById("INTERMEDIATE_PS_MONEY_CONTROLLER");
        Psc psc = pscMapper.selectByPrimaryKey(pscNum);
        if (psc != null) {
            if (psc.getIsPuItmd().equals("T") || psc.getIsSlItmd().equals("T")) {
                //居间合同
                if (sysPar.getParmVal().equals("I")) {
                    flag = true;
                }
            } else if (psc.getIsPuItmd().equals("F") && psc.getIsSlItmd().equals("F")
                    && StringUtils.isNotEmpty(psc.getItmdPscNum())) {
                //执行合同
                if (sysPar.getParmVal().equals("E")) {
                    flag = true;
                }
            } else if (psc.getIsPuItmd().equals("F") && psc.getIsSlItmd().equals("F")
                    && StringUtils.NullEmpty(psc.getItmdPscNum())) {
                flag = true;
            }
        }
        return flag;
    }

    //用退货合同编号判断是否监管往来
    @Override
    public boolean judgeRtc(String rtcNum) {
        boolean flag = false;
        SysParameter sysPar = parameterMapper.findById("INTERMEDIATE_PS_MONEY_CONTROLLER");
        Rtc rtc = rtcMapper.selectByPrimaryKey(rtcNum);
        if (rtc != null) {
            if (rtc.getIsSrItmd().equals("T") || rtc.getIsPrItmd().equals("T")) {
                //居间合同
                if (sysPar.getParmVal().equals("I")) {
                    flag = true;
                }
            } else if (rtc.getIsPrItmd().equals("F") && rtc.getIsSrItmd().equals("F")
                    && StringUtils.isNotEmpty(rtc.getItmdRtcNum())) {
                //执行合同
                if (sysPar.getParmVal().equals("E")) {
                    flag = true;
                }
            } else if (rtc.getIsPrItmd().equals("F") && rtc.getIsSrItmd().equals("F")
                    && StringUtils.NullEmpty(rtc.getItmdRtcNum())) {
                flag = true;
            }
        }
        return flag;
    }

    //监管往来简单公共方法(采购)
    @Override
    public void supervise(PscVo vo, SysUser user) throws Exception {
        //通过供应商id与采购商id查询是否有往来账户

        Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVendeeId()), Long.valueOf(vo.getVenderId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
        if (ca != null) {
            caAccService.defreeze(new CaAcc(ca.getUnitId(), ca.getCaId(), "PUC", ca.getUnitId(), vo.getDocNum()), user);
        }
    }

    //监管往来简单公共方法(销售)
    @Override
    public void superviseBySales(PscVo vo, SysUser user) throws Exception {
        //通过供应商id与采购商id查询是否有往来账户
        Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
        if (ca != null) {
            caAccService.defreeze(new CaAcc(ca.getUnitId(), ca.getCaId(), "SLC", ca.getUnitId(), vo.getDocNum()), user);
        }
    }

    /**
     * 收发货中累计数量/金额
     *
     * @param psa
     * @param docUnitId
     * @param docNum
     */
    @Override
    public void pscAccumulate(Psa psa, Long docUnitId, String docNum, boolean flag) {
        if (psa != null) {
            if (psa.getRtnCtrl().equals("Q")) {
                //按数量
                List<ProdClsDtlVo> clsDtlList = pscDtlMapper.selectByBillQty(docUnitId, docNum);
                rtqQtaService.accumulate(psa, clsDtlList, flag);
            } else if (psa.getRtnCtrl().equals("V")) {
                //按数量
                List<ProdClsDtlVo> clsDtlList = pscDtlMapper.selectByBillVal(docUnitId, docNum);
                rtvQtaService.accumulate(psa, clsDtlList, flag);
            }
        }
    }

    /**
     * 采购合同中登记收发差异事件
     *
     * @param vo
     */
    @Override
    public void register(PscVo vo) {
        //查询出销售合同号
        Slc slc = slcMapper.selectByPscNum(vo.getPscNum());
        //添加收发差异  判断是否有出库仓库与入库仓库
        if (StringUtils.NullEmpty(vo.getVdrWarehId())) {
            Long vdrWareh = gdnExternalCiteService.queryDelivWarehIdBySrcDocMess("SLC", slc.getSlcNum(), Long.valueOf(slc.getUnitId()), Long.valueOf(vo.getVendeeId()));
            vo.setVdrWarehId(vdrWareh + "");
        }
        //入库未查
        if (StringUtils.NullEmpty(vo.getVdeWarehId())) {
            Long vdeWareh = gdnExternalCiteService.queryRcvWarehIdBySrcDocMess("PUC", vo.getPucNum(), Long.valueOf(vo.getUnitId()), Long.valueOf(vo.getVenderId()));
            vo.setVdeWarehId(vdeWareh + "");
        }
        tranDiffEvtService.register(new TranDiffEvt(Long.valueOf(slc.getUnitId()), "SLC", slc.getSlcNum(), vo.getUnitId(), "PUC", vo.getDocNum(), Long.valueOf(vo.getVdrWarehId()), Long.valueOf(vo.getVdeWarehId()), "R"));

    }

    /**
     * 判断输入得原始合同是否正确
     *
     * @param src
     * @return
     */
    @Override
    public int findByNum(Psc src) {
        return pscMapper.findBySrcDoc(src.getSrcDocType(), src.getSrcDocUnitId(), src.getSrcDocNum());
    }

    /**
     * 判断输入得购销合同是否正确
     *
     * @param src
     * @return
     */
    @Override
    public Psc findByPscNum(Psc src) {
        return pscMapper.findByPscNum(src.getSrcDocType(), src.getSrcDocUnitId(), src.getSrcDocNum());
    }

    /**
     * 通过输入当前组织判断是否是总部
     * @param unitId
     * @return
     */
    @Override
    public int getHeadByUnitId(Long unitId) {
        SysParameter sysPa = parameterMapper.findById("IS_HEADQUARTERS");
        if(sysPa==null){
            throw new ServiceException(JsonResultCode.FAILURE, "找不到参数（IS_HEADQUARTERS）");
        }
        //判断值不为空
        if(StringUtils.isNotEmpty(sysPa.getParmVal())){
            //解析字符串并判断传入组织是否总部
            String val[] = sysPa.getParmVal().split(";");
            for (String ss:val) {
                if(ss.equals(unitId.toString())){
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * 公共方法
     *
     * @param methonName   调用方法名
     * @param srcDocType
     * @param srcDocUnitId
     * @param srcDocNum
     * @param docUnitId
     * @param docNum
     * @param user
     * @throws Exception
     */
    @Override
    public void publicMethon(String methonName, String srcDocType, Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        //解析srcDocType
        try {
            String objName = this.analysis(srcDocType);
            if (StringUtils.isNotEmpty(objName)) {
                Object bean = SpringUtils.getBean(objName);
                //获取方法返回类型
                Method method1 = null;
                Method[] methods = bean.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    if (methonName.equals(method.getName())) {
                        method1 = method;
                        break;
                    }
                }
                if (method1 == null) {
                    throw new ServiceException(JsonResultCode.FAILURE, "找不到请求方法！");
                }
                method1.invoke(bean, srcDocUnitId, srcDocNum, docUnitId, docNum, user);
            }
        } catch (InvocationTargetException  ex) {
            ex.printStackTrace();
            Throwable targetException = ex.getTargetException();
            throw new ServiceException(JsonResultCode.FAILURE, targetException.getMessage());
        }
    }

    //解析获取service名称
    private String analysis(String srcDocType) {
        String objName = "";
        if (StringUtils.isNotEmpty(srcDocType)) {
            objName = srcDocType.toLowerCase();
            objName += "ServiceImpl";
        }
        return objName;
    }

    /**
     * 输入查询公共接口
     *
     * @param model
     * @return
     */
    @Override
    public Object getObject(CommonPopupModel model, SysUser user) {
        Object obj = null;
        //json字符串转json对象
        JSONObject json = JSONObject.parseObject(model.getJson());
        switch (model.getSelectType()) {
            case "UNIT":
                //生成对象
                UnitOptionVo unit = JSON.toJavaObject(json, UnitOptionVo.class);
                obj = sysUnitService.findOptionByNum(unit);
                break;
            case "PROD":
                BillDtlProdVo prod = JSON.toJavaObject(json, BillDtlProdVo.class);
                obj = UnitProdClsService.findByBillProdCode(prod, user);
                break;
            case "SRC":
                Psc src = JSON.toJavaObject(json, Psc.class);
                int sum = this.findByNum(src);
                if (sum != 0) {
                    obj = src;
                }
                break;
            default:
                break;
        }
        return obj;
    }

    /**
     * 采购商/供应商不可修改字段
     * @param type
     * @param methoyType
     * @return
     */
    @Override
    public List<TableFile> updateNotFile(String type, String methoyType) {
        List<TableFile> at = new ArrayList<>();
        List<String> list = fieldUtils.isNotUpdateWareh(type);
        if(list!=null){
            TableFile t = new TableFile();
            if(methoyType.equals("update")){
                t.setTableFlie("unitCode");
                at.add(t);
            }
            for (String li : list) {
                t = new TableFile();
                t.setTableFlie(li);
                at.add(t);
            }
        }
        return at;
    }
}
