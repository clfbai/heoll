package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.entity.purchase.Psx;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxDtlRqd;
import com.boyu.erp.platform.usercenter.entity.sales.Sla;
import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.*;
import com.boyu.erp.platform.usercenter.mapper.sales.SlaMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysDomainMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.common.impl.ButtonCommonService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsxDtlService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PuaService;
import com.boyu.erp.platform.usercenter.service.salesservice.SlaService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname PpnScpServiceimpl
 * @Description TODO
 * @Date 2019/7/10 18:13
 * @Created wz
 */
@Service
@Transactional
public class PuaServiceimpl implements PuaService {
    //采购申请模块按钮参数
    static final String parameter = "puaButton";

    //确认
    static final String confirm = "/purchase/pua/confirm";
    //重做
    static final String redo = "/purchase/pua/redo";
    //审核
    static final String examine = "/purchase/pua/examine";
    //反审
    static final String reiterate = "/purchase/pua/reiterate";
    //挂起
    static final String hangUp = "/purchase/pua/hangUp";
    //恢复
    static final String recovery = "/purchase/pua/recovery";
    //作废
    static final String toVoid = "/purchase/pua/toVoid";
    //关闭
    static final String close = "/purchase/pua/close";
    //重用
    static final String reusing = "/purchase/pua/reusing";

    @Autowired
    private UsersService usersService;
    @Autowired
    private PuaMapper puaMapper;
    @Autowired
    private SysParameterMapper sysParameterMapper;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private SysDomainMapper sysDomainMapper;
    @Autowired
    private PsxMapper psxMapper;
    @Autowired
    private PsxDtlRqdMapper psxDtlRqdMapper;
    @Autowired
    private PsxDtlMapper psxDtlMapper;
    @Autowired
    private PsxDtlService psxDtlService;
    @Autowired
    private SlaMapper slaMapper;
    @Autowired
    private SlaService slaService;
    @Autowired
    private PuaTypeMapper puaTypeMapper;
    @Autowired
    private ButtonCommonService buttonCommonService;

    /**
     * 分页查询
     *
     * @param pua
     * @param page
     * @param size
     * @param user
     * @return
     * @throws ServiceException
     */
    @Override
    public PageInfo<PsxVo> selectAll(PsxVo pua, Integer page, Integer size, SysUser user) throws ServiceException {

        List<PsxVo> puaList = null;
        /**
         * 系统管理员
         * */
        if (false) {
            PageHelper.startPage(page, size);
            puaList = puaMapper.selectALL(pua);
        } else {
            PageHelper.startPage(page, size);
            puaList = puaMapper.selectUserList(pua);
        }

        PageInfo<PsxVo> pageInfo = new PageInfo<PsxVo>(puaList);
        return pageInfo;
    }

    /**
     * 新增
     *
     * @param v
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int insertPua(PsxVo v, SysUser user) throws Exception {
        SysParameter sys = sysParameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        //生成编号时所需的用户数据
        SysUser sysU = new SysUser(Long.valueOf(v.getUnitId()), user.getPrsnl().getPrsnlId());
        if (sys.getParmVal().equals("T")) {
            String pscNum = sysRefNumService.mainNum(sysU, "PSX_NUM");
            v.setPuaNum(pscNum);
            v.setPsxNum(pscNum);
        } else {
            String puaNum = sysRefNumService.viceNum(sysU, "PUA_NUM");
            String psxNum = sysRefNumService.mainNum(sysU, "PSX_NUM");
            v.setPuaNum(puaNum);
            v.setPsxNum(psxNum);
        }

        v = this.updatePuaVo(v);

        v.setEffective("F");
        v.setCancelled("F");
        v.setSuspended("F");
        v.setProgress("PG");

        v.setPuaGen("T");
        v.setVdeInvd("T");//默认采购商介入

        v.setOprId(user.getPrsnl().getPrsnlId());

        if (v.getPsxDtlList() != null && v.getPsxDtlList().size() > 0) {
            v = psxDtlService.insertByPua(v);
        }

        puaMapper.insertByPuaVo(v);
        return psxMapper.insertByPsxVo(v);
    }

    /**
     * 新增删除设置得公共方法
     *
     * @param v
     * @return
     */
    private PsxVo updatePuaVo(PsxVo v) {
        if (StringUtils.NullEmpty(v.getVdrInvd())) {
            v.setVdrInvd("F");
        }
        //判断供应商是否介入 设置销售相关数据
        if (v.getVdrInvd().equals("T")) {
            v.setSlaAutoGen("T");
        } else {
            v.setSlaAutoGen("F");
            v.setSlaAutoChk("F");
        }
//        SysDomain sysDo = sysDomainMapper.selectByPrimaryKey(Long.valueOf(v.getVenderId()));
//        if (sysDo != null) {
//            if (sysDo.getDomainStatus().equals("A")) {
//                v.setVdrInvd("T");
//                v.setSlaAutoGen("T");
//            } else {
//                v.setVdrInvd("F");
//                v.setSlaAutoGen("F");
//                v.setSlaAutoChk("F");
//            }
//        } else {
//            v.setVdrInvd("F");
//            v.setSlaAutoGen("F");
//            v.setSlaAutoChk("F");
//        }
        v.setSlaGen("F");
        return v;
    }

    /**
     * 修改
     *
     * @param v
     * @param sysUser
     * @return
     * @throws Exception
     */
    @Override
    public int updatePua(PsxVo v, SysUser sysUser) throws Exception {
        v.setOprId(sysUser.getPrsnl().getPrsnlId());

        v = this.updatePuaVo(v);
        //修改货期
        this.updateRqdCtrl(v);

        puaMapper.updateByPuaVo(v);
        return psxMapper.updateByPuaVo(v);
    }

    /**
     * 更新货期及货期控制方法
     *
     * @param v
     */
    public void updateRqdCtrl(PsxVo v) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<>();
        if (!v.getRqdCtrlOld().equals(v.getRqdCtrl())) {
            //如果货期控制改了，清除之前的货期值，新增或修改货期值
            if (v.getReqdDate() != null) {
                if (v.getRqdCtrlOld().equals("SG")) {
                    if (v.getRqdCtrl().equals("PD")) {
                        map.put("reqdDate", v.getReqdDate());
                        map.put("psxNum", v.getPsxNum());
                        psxDtlMapper.updateByRqdCtrl(map);
                    } else if (v.getRqdCtrl().equals("FR")) {
                        this.updateRqd(v);
                    }
                } else if (v.getRqdCtrlOld().equals("PD")) {
                    //直接清除明细中货期
                    map.put("reqdDate", null);
                    map.put("psxNum", v.getPsxNum());
                    psxDtlMapper.updateByRqdCtrl(map);

                    if (v.getRqdCtrl().equals("FR")) {
                        this.updateRqd(v);
                    }
                } else if (v.getRqdCtrlOld().equals("FR")) {
                    //直接删除货期数据
                    psxDtlRqdMapper.deleteByRqdCtrl(v.getPsxNum());

                    if (v.getRqdCtrl().equals("PD")) {
                        map.put("reqdDate", v.getReqdDate());
                        map.put("psxNum", v.getPsxNum());
                        psxDtlMapper.updateByRqdCtrl(map);
                    }
                }
            }
        } else {
            //判断是否修改货期
            if (v.getReqdType().equals("1")) {
                //如果货期改了,根据货期控制修改货期值
                if (v.getRqdCtrl().equals("PD")) {
                    map.put("reqdDate", v.getReqdDate());
                    map.put("psxNum", v.getPsxNum());
                    psxDtlMapper.updateByRqdCtrl(map);
                } else if (v.getRqdCtrl().equals("FR")) {
                    PsxDtlRqd pdr = new PsxDtlRqd();
                    pdr.setPsxNum(v.getPsxNum());
                    pdr.setReqdDate(sdf.parse(v.getReqdDate()));
                    psxDtlRqdMapper.updateByReqdDate(pdr);
                }
            }
        }

    }

    private void updateRqd(PsxVo v) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PsxDtlVo vo = new PsxDtlVo();
        vo.setPsxNum(v.getPsxNum());
        List<PsxDtlVo> list = psxDtlService.findByPsxNum(vo);
        if (list != null) {
            for (PsxDtlVo p : list) {
                PsxDtlRqd pdr = new PsxDtlRqd();
                pdr.setPsxNum(p.getPsxNum());
                pdr.setProdId(p.getProdId());
                pdr.setQty(p.getQty());
                pdr.setReqdDate(sdf.parse(v.getReqdDate()));
                psxDtlRqdMapper.insertSelective(pdr);
            }
        }
    }

    /**
     * 删除
     *
     * @param v
     * @return
     */
    @Override
    public int deletePua(PsxVo v) {
        //删除pua表数据
        puaMapper.deletePuaVo(v.getPuaNum(), v.getVendeeId() + "");
        v.setPuaGen("F");
        if (v.getSlaGen().equals("T") && v.getSlaAutoGen().equals("T")) {
            //调用销售申请接口 删除sla表数据
            v.setSlaNum(slaMapper.selectByPsxNum(v.getPsxNum()).getSlaNum());
            return slaService.deleteSla(v);
        }
        //判断是否删除psc表  否则就更新它 设置SLA_GEN = FALSE。
        if (v.getSlaGen().equals("F")) {
            //删除货期表
            if (v.getRqdCtrl().equals("FR")) {
                psxDtlRqdMapper.deleteByRqdCtrl(v.getPsxNum());
            }
            psxDtlMapper.deleteByPsxNum(v.getPsxNum());
            return psxMapper.deleteByPrimaryKey(v.getPsxNum());
        }
        return 1;
    }

    /**
     * 删除明细
     *
     * @param psxNum
     * @return
     */
    @Override
    public int deletePsxDtl(String psxNum) {
        //查询购销单数据
        //删除购销明细
        //更新购销单数据
        Psx psx = psxMapper.selectByPrimaryKey(psxNum);
        if (psx.getRqdCtrl().equals("FR")) {
            psxDtlRqdMapper.deleteByRqdCtrl(psxNum);
        }
        psxDtlMapper.deleteByPsxNum(psxNum);
        psx.setTtlQty(new BigDecimal("0"));
        psx.setTtlBox(0L);
        psx.setTtlMkv(new BigDecimal("0"));
        psx.setTtlVal(new BigDecimal("0"));
        return psxMapper.updateByPrimaryKeySelective(psx);
    }

    /**
     * 单据确认
     *
     * @param vo
     * @param sysUser
     * @return
     */
    @Override
    public int confirm(PsxVo vo, SysUser sysUser) {
        vo.setProgress("C");
        vo.setOprId(sysUser.getPrsnl().getPrsnlId());
        if (vo.getSlaGen().equals("F") && vo.getSlaAutoGen().equals("T")) {
            vo.setSlaGen("T");
            //生成销售单
            slaService.insertByPua(vo, new SysUser(Long.valueOf(vo.getVenderId()), sysUser.getPrsnl().getPrsnlId()));
        }
        return psxMapper.updateByPsxVo(vo);
    }

    //销售申请中生成采购申请
    @Override
    public int insertBySla(PsxVo vo, SysUser user) {
        SysParameter sys = sysParameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        if (sys.getParmVal().equals("T")) {
            vo.setPuaNum(vo.getPsxNum());
        } else {
            String puaNum = sysRefNumService.viceNum(user, "PUA_NUM");
            vo.setPuaNum(puaNum);
        }
        //通过购销申请类别查询销售申请类别
        vo.setSlaType(puaTypeMapper.selectByPsxType(vo.getPsxType()) != null ? puaTypeMapper.selectByPsxType(vo.getPsxType()).getPuaType() : null);
        return puaMapper.insertBySla(vo);
    }

    /**
     * 单据重做
     *
     * @param vo
     * @param sysUser
     * @return
     */
    @Override
    public int redo(PsxVo vo, SysUser sysUser) {
        vo.setProgress("PG");
        vo.setOprId(sysUser.getPrsnl().getPrsnlId());
        if (vo.getSlaGen().equals("T") && vo.getSlaAutoGen().equals("T")) {
            vo.setSlaGen("F");
            slaMapper.deleteByPsxNum(vo.getPsxNum());
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 单据审核
     *
     * @param vo
     * @param sysUser
     * @return
     */
    @Override
    public int examine(PsxVo vo, SysUser sysUser) {
        if (vo.getProgress().equals("C") && vo.getSlaGen().equals("T")) {
            vo.setProgress("EK");
            vo.setEffective("F");
        } else {
            vo.setProgress("CK");
            vo.setEffective("T");
        }
        vo.setOprId(sysUser.getPrsnl().getPrsnlId());
        puaMapper.updateByChkr(sysUser.getPrsnl().getPrsnlId() + "", vo.getPsxNum());
        if (vo.getSlaGen().equals("T") && StringUtils.isNotEmpty(vo.getPuaNum())) {
            return slaService.noticeExamine(vo, sysUser);
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 通知审核事件
     *
     * @param vo
     * @param sysUser
     * @return
     */
    @Override
    public int noticeExamine(PsxVo vo, SysUser sysUser) {
        if (vo.getPuaAutoChk().equals("T") && vo.getProgress().equals("RK")) {
            return this.examine(vo, sysUser);
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 单据重审
     *
     * @param vo
     * @param sysUser
     * @return
     */
    @Override
    public int reiterate(PsxVo vo, SysUser sysUser) {
        if (vo.getProgress().equals("CK") && vo.getSlaGen().equals("T")) {
            vo.setProgress("RK");
        } else {
            vo.setProgress("C");
        }
        vo.setEffective("F");
        vo.setOprId(sysUser.getPrsnl().getPrsnlId());
        puaMapper.updateByChkr(null, vo.getPsxNum());
        if (vo.getSlaGen().equals("T") && StringUtils.isNotEmpty(vo.getPuaNum())) {
            return slaService.noticeReiterate(vo, sysUser);
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 通知重审事件
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int noticeReiterate(PsxVo vo, SysUser user) {
        if (vo.getPuaAutoChk().equals("T") && vo.getProgress().equals("EK")) {
            return this.reiterate(vo, user);
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 挂起单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int hangUp(PsxVo vo, SysUser user) {
        vo.setSuspended("T");
        if (vo.getSlaGen().equals("T")) {
            this.slaSu(vo.getPsxNum(),vo.getSuspended());
        }
        return puaMapper.updateByPuaVo(vo);
    }

    /**
     * 恢复单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int recovery(PsxVo vo, SysUser user) {
        vo.setSuspended("F");
        if (vo.getSlaGen().equals("T")) {
            this.slaSu(vo.getPsxNum(),vo.getSuspended());
        }
        return puaMapper.updateByPuaVo(vo);
    }

    /**
     * 操作销售申请挂起
     * @param psxNum
     */
    public void slaSu(String psxNum,String suspended) {
        Sla sla = slaMapper.selectByPsxNum(psxNum);
        if (sla != null) {
            sla.setSuspended(suspended);
            slaMapper.updateByPrimaryKey(sla);
        }
    }

    /**
     * 作废单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int toVoid(PsxVo vo, SysUser user) {
        vo.setCancelled("T");
        if (vo.getSlaGen().equals("T")) {
            //通知销售申请作废
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 关闭单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int close(PsxVo vo, SysUser user) {
        vo.setProgress("CL");
        if (vo.getSlaGen().equals("T")) {
            //通知销售申请关闭
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 重用单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int reusing(PsxVo vo, SysUser user) {
        vo.setProgress("CK");
        if (vo.getSlaGen().equals("T")) {
            //通知销售申请重用
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 查询采购申请可操作状态
     *
     * @param vo
     * @return
     */
    @Override
    public List<OperationVo> initButtons(PsxVo vo) {
        List<PurKeyValue> keyValues = buttonCommonService.initButton(parameter);
        PsxVo psx = puaMapper.selectByOnly(vo);
        if (psx == null) {
            throw new ServiceException("403", "单据异常！");
        }
        return creatButton(keyValues, psx);
    }

    /**
     * 查询单条
     *
     * @param vo
     * @return
     */
    @Override
    public PsxVo selectByOnly(PsxVo vo) {
        return puaMapper.selectByOnly(vo);
    }

    public List<OperationVo> creatButton(List<PurKeyValue> keyValues, PsxVo vo) {
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
                if ((vo.getProgress().equals("C") || vo.getProgress().equals("RK")) && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("审核", "T", examine));
                } else {
                    list.add(new OperationVo("审核", "F", examine));
                }
            }
            if (("uncheck").equals(pur.getOptionValue())) {
                if ((vo.getProgress().equals("CK") || vo.getProgress().equals("EK")) && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F")) {
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
                if ((vo.getProgress().equals("PG") || vo.getProgress().equals("C") || vo.getProgress().equals("RK") || vo.getProgress().equals("EK")
                        || vo.getProgress().equals("CK")) && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("作废", "T", toVoid));
                } else {
                    list.add(new OperationVo("作废", "F", toVoid));
                }
            }
            if (("close").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("CK") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
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
        }
        return list;
    }


}
