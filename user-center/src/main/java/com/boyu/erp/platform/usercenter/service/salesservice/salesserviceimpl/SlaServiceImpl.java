package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.entity.purchase.Pua;
import com.boyu.erp.platform.usercenter.entity.sales.Sla;
import com.boyu.erp.platform.usercenter.entity.system.SysDomain;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.*;
import com.boyu.erp.platform.usercenter.mapper.sales.SlaMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SlaTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysDomainMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.common.impl.ButtonCommonService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsxDtlService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PuaService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl.PuaServiceimpl;
import com.boyu.erp.platform.usercenter.service.salesservice.SlaService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname SlaServiceImpl
 * @Description TODO
 * @Date 2019/9/4 19:14
 * @Created by wz
 */
@Slf4j
@Service
@Transactional
public class SlaServiceImpl implements SlaService {
    //销售申请模块按钮参数
    static final String parameter = "slaButton";

    //确认
    static final String confirm = "/sales/sla/confirm";
    //重做
    static final String redo = "/sales/sla/redo";
    //审核
    static final String examine = "/sales/sla/examine";
    //反审
    static final String reiterate = "/sales/sla/reiterate";
    //挂起
    static final String hangUp = "/sales/sla/hangUp";
    //恢复
    static final String recovery = "/sales/sla/recovery";
    //作废
    static final String toVoid = "/sales/sla/toVoid";
    //关闭
    static final String close = "/sales/sla/close";
    //重用
    static final String reusing = "/sales/sla/reusing";

    @Autowired
    private UsersService usersService;
    @Autowired
    private SlaMapper slaMapper;
    @Autowired
    private SysParameterMapper sysParameterMapper;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private SysDomainMapper sysDomainMapper;
    @Autowired
    private PsxDtlService psxDtlService;
    @Autowired
    private PsxMapper psxMapper;
    @Autowired
    private PuaServiceimpl puaServiceimpl;
    @Autowired
    private PuaService puaService;
    @Autowired
    private PsxDtlRqdMapper psxDtlRqdMapper;
    @Autowired
    private PuaMapper puaMapper;
    @Autowired
    private SlaTypeMapper slaTypeMapper;
    @Autowired
    private PsxDtlMapper psxDtlMapper;
    @Autowired
    private ButtonCommonService buttonCommonService;

    /**
     * 分页查询
     *
     * @param sla
     * @param page
     * @param size
     * @param user
     * @return
     * @throws ServiceException
     */
    @Override
    public PageInfo<PsxVo> selectAll(PsxVo sla, Integer page, Integer size, SysUser user) throws ServiceException {

        List<PsxVo> puaList = null;
        /**
         * 系统管理员
         * */
        if (false) {
            PageHelper.startPage(page, size);
            puaList = slaMapper.selectALL(sla);
        } else {
            PageHelper.startPage(page, size);
            puaList = slaMapper.selectUserList(sla);
        }

        PageInfo<PsxVo> pageInfo = new PageInfo<PsxVo>(puaList);
        return pageInfo;
    }

    /**
     * 新增销售申请
     *
     * @param v
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int insertSla(PsxVo v, SysUser user) throws Exception {
        SysParameter sys = sysParameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        //生成编号时所需的用户数据
        SysUser sysU = new SysUser(Long.valueOf(v.getUnitId()), user.getPrsnl().getPrsnlId());
        if (sys.getParmVal().equals("T")) {
            String pscNum = sysRefNumService.mainNum(sysU, "PSX_NUM");
            v.setSlaNum(pscNum);
            v.setPsxNum(pscNum);
        } else {
            String slaNum = sysRefNumService.viceNum(sysU, "SLA_NUM");
            String psxNum = sysRefNumService.mainNum(sysU, "PSX_NUM");
            v.setSlaNum(slaNum);
            v.setPsxNum(psxNum);
        }

        v = this.updateSlaVo(v);

        v.setEffective("F");
        v.setCancelled("F");
        v.setSuspended("F");
        v.setProgress("PG");

        v.setSlaGen("T");
        v.setVdrInvd("T");//默认供应商介入

        v.setOprId(user.getPrsnl().getPrsnlId());

        if (v.getPsxDtlList() != null && v.getPsxDtlList().size() > 0) {
            v = psxDtlService.insertByPua(v);
        }

        slaMapper.insertBySlaVo(v);
        return psxMapper.insertByPsxVo(v);
    }

    /**
     * 修改销售申请
     *
     * @param v
     * @param sysUser
     * @return
     * @throws Exception
     */
    @Override
    public int updateSla(PsxVo v, SysUser sysUser) throws Exception {
        v.setOprId(sysUser.getPrsnl().getPrsnlId());

        v = this.updateSlaVo(v);

        //修改货期
        puaServiceimpl.updateRqdCtrl(v);

        slaMapper.updateBySlaVo(v);
        return psxMapper.updateByPuaVo(v);
    }

    /**
     * 删除销售申请
     *
     * @param v
     * @return
     */
    @Override
    public int deleteSla(PsxVo v) {
        //删除pua表数据
        slaMapper.deleteSlaVo(v.getSlaNum(), v.getVenderId() + "");
        v.setSlaGen("F");
        if (v.getPuaGen().equals("T")&&v.getPuaAutoGen().equals("T")) {
            //调用采购申请接口 删除表数据
            v.setPuaNum(puaMapper.selectByPsxNum(v.getPsxNum()).getPuaNum());
            return puaService.deletePua(v);
        }
        //判断是否删除psx表  否则就更新它 设置PUA_GEN = FALSE。
        if (v.getPuaGen().equals("F")) {
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
     * 采购申请中生成销售申请
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertByPua(PsxVo vo, SysUser user) {
        SysParameter sys = sysParameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        if (sys.getParmVal().equals("T")) {
            vo.setSlaNum(vo.getPsxNum());
        } else {
            String slaNum = sysRefNumService.viceNum(user, "SLA_NUM");
            vo.setSlaNum(slaNum);
        }
        //通过购销申请类别查询销售申请类别
        vo.setSlaType(slaTypeMapper.selectByPsxType(vo.getPsxType()) != null ? slaTypeMapper.selectByPsxType(vo.getPsxType()).getSlaType() : null);
        return slaMapper.insertByPua(vo);
    }

    /**
     * 确认单据
     *
     * @param vo
     * @param sysUser
     * @return
     */
    @Override
    public int confirm(PsxVo vo, SysUser sysUser) {
        vo.setProgress("C");
        vo.setOprId(sysUser.getPrsnl().getPrsnlId());
        if (vo.getPuaGen().equals("F") && vo.getPuaAutoGen().equals("T")) {
            vo.setPuaGen("T");
            //生成销售单
            puaService.insertBySla(vo, new SysUser(Long.valueOf(vo.getVendeeId()), sysUser.getPrsnl().getPrsnlId()));
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 重做单据
     *
     * @param vo
     * @param sysUser
     * @return
     */
    @Override
    public int redo(PsxVo vo, SysUser sysUser) {
        vo.setProgress("PG");
        vo.setOprId(sysUser.getPrsnl().getPrsnlId());
        if (vo.getPuaGen().equals("T") && vo.getPuaAutoGen().equals("T")) {
            vo.setPuaGen("F");
            puaMapper.deleteByPsxNum(vo.getPsxNum());
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 审核单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int examine(PsxVo vo, SysUser user) {
        if (vo.getProgress().equals("C") && vo.getPuaGen().equals("T")) {
            vo.setProgress("RK");
            vo.setEffective("F");
        } else {
            vo.setProgress("CK");
            vo.setEffective("T");
        }
        slaMapper.updateByChkr(user.getPrsnl().getPrsnlId() + "", vo.getPsxNum());
        if (vo.getPuaGen().equals("T") && StringUtils.isNotEmpty(vo.getSlaNum())) {
            return puaService.noticeExamine(vo, user);
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 通知审核事件
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int noticeExamine(PsxVo vo, SysUser user) {
        if (vo.getSlaAutoChk().equals("T") && vo.getProgress().equals("EK")) {
            return this.examine(vo, user);
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 重审单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int reiterate(PsxVo vo, SysUser user) {
        if (vo.getProgress().equals("CK") && vo.getPuaGen().equals("T")) {
            vo.setProgress("EK");
        } else {
            vo.setProgress("C");
        }
        vo.setEffective("F");
        slaMapper.updateByChkr(null, vo.getPsxNum());
        if (vo.getPuaGen().equals("T") && StringUtils.isNotEmpty(vo.getSlaNum())) {
            return puaService.noticeReiterate(vo, user);
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
        if (vo.getSlaAutoChk().equals("T") && vo.getProgress().equals("RK")) {
            this.reiterate(vo, user);
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
        if (vo.getPuaGen().equals("T")) {
            this.puaSu(vo.getPsxNum(),vo.getSuspended());
        }
        return slaMapper.updateBySlaVo(vo);
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
        if (vo.getPuaGen().equals("T")) {
            this.puaSu(vo.getPsxNum(),vo.getSuspended());
        }
        return slaMapper.updateBySlaVo(vo);
    }

    /**
     * 操作采购申请挂起
     * @param psxNum
     */
    public void puaSu(String psxNum,String suspended) {
        Pua pua = puaMapper.selectByPsxNum(psxNum);
        if (pua != null) {
            pua.setSuspended(suspended);
            puaMapper.updateByPrimaryKey(pua);
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
        if (vo.getPuaGen().equals("T")) {
            //通知采购申请作废
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
        if (vo.getPuaGen().equals("T")) {
            //通知采购申请关闭
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
        if (vo.getPuaGen().equals("T")) {
            //通知采购申请重用
        }
        return psxMapper.updateByPsxVo(vo);
    }

    /**
     * 更新字段公共方法
     *
     * @param v
     * @return
     */
    private PsxVo updateSlaVo(PsxVo v) {
        if(StringUtils.NullEmpty(v.getVdeInvd())){
            v.setVdeInvd("F");
        }
        //判断采购商是否介入设置采购相关数据
        if(v.getVdeInvd().equals("T")){
            v.setPuaAutoGen("T");
        }else{
            v.setPuaAutoGen("F");
            v.setPuaAutoChk("F");
        }
//        //通过采购商id判断是否存在领域 来判定采购商是否介入
//        //查询此采购商是否存在活动领域
//        SysDomain sysDo = sysDomainMapper.selectByPrimaryKey(Long.valueOf(v.getVendeeId()));
//        if (sysDo != null) {
//            if (sysDo.getDomainStatus().equals("A")) {
//                v.setVdeInvd("T");
//                v.setPuaAutoGen("T");
//            } else {
//                v.setVdeInvd("F");
//                v.setPuaAutoGen("F");
//                v.setPuaAutoChk("F");
//            }
//        } else {
//            v.setVdeInvd("F");
//            v.setPuaAutoGen("F");
//            v.setPuaAutoChk("F");
//        }
        v.setPuaGen("F");
        return v;
    }

    /**
     * 查询销售申请可操作状态
     *
     * @param vo
     * @return
     */
    @Override
    public List<OperationVo> initButtons(PsxVo vo) {
        List<PurKeyValue> keyValues = buttonCommonService.initButton(parameter);
        PsxVo psx = slaMapper.selectByOnly(vo);
        if (psx==null) {
            throw new ServiceException("403", "单据异常！");
        }
        return creatButton(keyValues, psx);
    }

    /**
     * 查询单条
     * @param vo
     * @return
     */
    @Override
    public PsxVo selectByOnly(PsxVo vo) {
        return slaMapper.selectByOnly(vo);
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
                if ((vo.getProgress().equals("C") || vo.getProgress().equals("EK")) && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("审核", "T", examine));
                } else {
                    list.add(new OperationVo("审核", "F", examine));
                }
            }
            if (("uncheck").equals(pur.getOptionValue())) {
                if ((vo.getProgress().equals("CK") || vo.getProgress().equals("RK")) && vo.getSuspended().equals("F")
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
