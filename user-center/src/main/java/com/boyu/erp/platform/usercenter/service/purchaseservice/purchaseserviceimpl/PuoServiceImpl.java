package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.entity.basic.TranDiffEvt;
import com.boyu.erp.platform.usercenter.entity.purchase.Psc;
import com.boyu.erp.platform.usercenter.entity.purchase.Pso;
import com.boyu.erp.platform.usercenter.entity.purchase.PsoDtl;
import com.boyu.erp.platform.usercenter.entity.purchase.Puo;
import com.boyu.erp.platform.usercenter.entity.sales.Slc;
import com.boyu.erp.platform.usercenter.entity.sales.Slo;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.CaMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.*;
import com.boyu.erp.platform.usercenter.mapper.sales.SloMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehDelivTaskMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehRcvTaskMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehStkPgMapper;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.caservice.CaAccService;
import com.boyu.erp.platform.usercenter.service.caservice.TranDiffEvtService;
import com.boyu.erp.platform.usercenter.service.common.impl.ButtonCommonService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.*;
import com.boyu.erp.platform.usercenter.service.salesservice.SloService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.warehouse.*;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.PurchaseUtils;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.*;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbDtlVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname PuoServiceimpl
 * @Description TODO
 * @Date 2019/7/17 18:13
 * @Created wz
 */
@Service
@Transactional
public class PuoServiceImpl implements PuoService {

    //采购单模块按钮参数
    static final String parameter = "puoButton";

    //确认
    static final String confirm = "/purchase/puo/confirm";
    //重做
    static final String redo = "/purchase/puo/redo";
    //审核
    static final String examine = "/purchase/puo/examine";
    //反审
    static final String reiterate = "/purchase/puo/reiterate";
    //挂起
    static final String hangUp = "/purchase/puo/hangUp";
    //恢复
    static final String recovery = "/purchase/puo/recovery";
    //作废
    static final String toVoid = "/purchase/puo/toVoid";
    //结束入库
    static final String end = "/purchase/puo/closeGoods";
    //重启入库
    static final String restart = "/purchase/puo/restartGoods";
    //导入采购合同
    static final String importBill = "/purchase/puo/importBill";

    @Autowired
    private UsersService usersService;
    @Autowired
    private PuoMapper puoMapper;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private PsoMapper psoMapper;
    @Autowired
    private PscService pscService;
    @Autowired
    private PsoDtlMapper psoDtlMapper;
    @Autowired
    private PsoDtlService psoDtlService;
    @Autowired
    private PscMapper pscMapper;
    @Autowired
    private SloService sloService;
    @Autowired
    private SloMapper sloMapper;
    @Autowired
    private PurchaseUtils purchaseUtils;
    @Autowired
    private WarehDelivTaskMapper warehDelivTaskMapper;
    @Autowired
    private WarehRcvTaskMapper warehRcvTaskMapper;
    @Autowired
    private PucMapper pucMapper;
    @Autowired
    private PucService pucService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private CaMapper caMapper;
    @Autowired
    private CaAccService caAccService;
    @Autowired
    private WarehStkPgMapper warehStkPgMapper;
    @Autowired
    private TranDiffEvtService tranDiffEvtService;
    @Autowired
    private GdnExternalCiteService gdnExternalCiteService;
    @Autowired
    private ButtonCommonService buttonCommonService;
    @Autowired
    private PscDtlMapper pscDtlMapper;
    @Autowired
    private WarehRcvTaskSerivce warehRcvTaskSerivce;
    @Autowired
    private StbService stbService;
    @Autowired
    private StbDtlService stbDtlService;

    @Override
    public PageInfo<PsoVo> selectAll(PsoVo vo, Integer page, Integer size, SysUser user) {
        List<PsoVo> list = null;
        /**
         * 系统管理员
         * */
        if (false) {
            PageHelper.startPage(page, size);
            list = puoMapper.selectALL(vo);
        } else {
            PageHelper.startPage(page, size);
            list = puoMapper.selectByUnit(vo);
        }
        PageInfo<PsoVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int insertPuo(PsoVo vo, SysUser sysUser) {
        //通过参数去判断采购单和购销单号是否一样
        SysParameter sys = parameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        SysUser sysU = new SysUser(Long.valueOf(vo.getVendeeId()), sysUser.getPrsnl().getPrsnlId());
        if (sys.getParmVal().equals("T")) {
            String mainNum = sysRefNumService.mainNum(sysU, "PSO_NUM");
            vo.setPsoNum(mainNum);
            vo.setPuoNum(mainNum);
        } else {
            String mainNum = sysRefNumService.mainNum(sysU, "PSO_NUM");
            String viceNum = sysRefNumService.viceNum(sysU, "PUO_NUM");
            vo.setPsoNum(mainNum);
            vo.setPuoNum(viceNum);
        }
        vo.setOprId(sysUser.getPrsnl().getPrsnlId().intValue());
        //默认不生成销售单
        vo.setPuoGen("T");
        vo.setSloGen("F");
        vo.setPsoKind("T");
        //新增时默认状态
        vo.setEffective("F");
        vo.setCancelled("F");
        vo.setProgress("PG");
        vo.setSuspended("F");

        if (vo.getPsoDtlList() != null && vo.getPsoDtlList().size() > 0) {
            vo = psoDtlService.insertByPso(vo);
        }

        psoMapper.insertByPso(vo);
        pscService.updateTaskByP(vo.getPscNum(), "1");
        return puoMapper.insertByPso(vo);
    }

    @Override
    public int updatePuo(PsoVo vo, SysUser sysUser) {
        //通过购销合同号判断是否更改，更改就修改原购销合同的任务数
        vo.setOprId((int) sysUser.getPrsnl().getPrsnlId().intValue());
        //通过购销单号获取修改前数据
        Pso pso = psoMapper.selectByPrimaryKey(vo.getPsoNum());
        if (pso != null) {
            //相等就直接修改数据
            if (!pso.getPscNum().equals(vo.getPscNum())) {
                //不相等的话  调用方法  减掉并更新原采购单的任务数  在更新pso表
                pscService.updateTaskByP(pso.getPscNum(), "2");
            }
        }
        puoMapper.updateByPso(vo);
        return psoMapper.updateByPso(vo);
    }

    @Override
    public int deletePuo(PsoVo vo) {
        //删除puo表  在根据pso中SLO_GEN的值判断是否删除还是更新pso表
        puoMapper.deleteByPuo(vo.getPuoNum(), vo.getPsoNum());
        vo.setPuoGen("F");

        pscService.updateTaskByP(vo.getPscNum(), "2");

        if (vo.getVdrInvd().equals("T") && vo.getSloGen().equals("T")) {
            //删除销售单数据  预留
            vo.setSloNum(sloMapper.selectByPsoNum(vo.getPsoNum()).getSloNum());
            return sloService.deleteSlo(vo);
        }
        if (vo.getSloGen().equals("F")) {
            //删除pso表时 删除明细
            psoDtlMapper.deleteByPsoNum(vo.getPsoNum());
            //删除pso
            psoMapper.deleteByPrimaryKey(vo.getPsoNum());
        }
        return 1;
    }

    /**
     * 更换相关数据 清空明细
     *
     * @param psoNum
     * @return
     */
    @Override
    public int deleteByPsoDtl(String psoNum) {
        //通过购销单号获取修改前数据
        Pso pso = psoMapper.selectByPrimaryKey(psoNum);
        //删除购销单明细、
        psoDtlMapper.deleteByPsoNum(psoNum);

        pso.setTtlQty(new BigDecimal("0"));
        pso.setTtlBox(0);
        pso.setTtlVal(new BigDecimal("0"));
        pso.setTtlTax(new BigDecimal("0"));
        pso.setTtlMkv(new BigDecimal("0"));
        return psoMapper.updateByPrimaryKeySelective(pso);
    }

    /**
     * 采购单中确认单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int confirm(PsoVo vo, SysUser user) {
        //将状态改为已确认
        vo.setProgress("C");

        Psc psc = pscMapper.selectByPrimaryKey(vo.getPscNum());
        if (psc != null) {
            if (psc.getSlcAutoGen().equals("T") && vo.getSloGen().equals("F")) {
                vo.setSloGen("T");
                //创建销售单
                sloService.insertByPuo(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
            }
        }

        return psoMapper.updateByState(vo);
    }

    /**
     * 采购单中重做单据
     *
     * @param vo
     * @return
     */
    @Override
    public int redo(PsoVo vo) {
        vo.setProgress("PG");
        //判断是否删除销售单
        Psc psc = pscMapper.selectByPrimaryKey(vo.getPscNum());
        if (vo.getSloGen().equals("T") && psc.getSlcAutoGen().equals("T")) {
            vo.setSloGen("F");
            sloMapper.deleteByPuo(null, vo.getPsoNum());
        }
        return psoMapper.updateByState(vo);
    }

    /**
     * 采购单中审核单据
     *
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int examine(PsoVo vo, SysUser user) throws Exception {
        //将状态改为已审核状态  并修改采购合同与销售合同审核状态
        if (vo.getProgress().equals("C") && vo.getSloGen().equals("T")) {
            vo.setProgress("EK");
            vo.setEffective("F");
        } else {
            vo.setProgress("CK");
            vo.setEffective("T");
        }
        //采购合同记录
        PscVo psc = pucMapper.selectByOnly(new PscVo(vo.getPscNum()));
        //调用采购合同分配接口
        pucService.admeasure(psc);

        //重置
        SysUser sysU = new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId());
        //非居间合同
        if (this.contract(vo.getPscNum()) && vo.getPsoKind().equals("T")) {
            //判断监管往来
            if (purchaseService.judgePsc(vo.getPscNum()) && psc.getMfzEnabled().equals("T")
                    && psc.getPsMfzSite().equals("AD") && !psc.getVdeFsclUnitId().equals(psc.getVenderId())) {
                //通过供应商id与采购商id查询是否有往来账户
                Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVendeeId()), Long.valueOf(vo.getVenderId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                if (ca != null) {
                    caAccService.freeze(new CaAcc(null, null, "PUC", psc.getUnitId(), psc.getPucNum()), sysU, ca, vo.getTtlVal(), new BigDecimal("0"), true);
                }
            }
            if (pucService.contract(psc)) {
                //登记未决
                Map<String, Object> map = new HashMap<>();
                List<PsoDtlVo> psoDtlList = psoDtlMapper.findByPuoNum(new PsoDtlVo(vo.getPsoNum()));
                if (psoDtlList != null && psoDtlList.size() > 0) {
                    map.put("stkType", "EP");
                    map.put("docType", "PUO");
                    map.put("warehId", vo.getRcvWarehId());
                    map.put("unitId", vo.getVendeeId());
                    map.put("docNum", vo.getDocNum());
                    map.put("dtlList", psoDtlList);
                    warehStkPgMapper.insertByDtl(map);
                }
                //登记入库
                if (vo.getProgress().equals("CK") && vo.getVdrInvd().equals("F")) {
                    //入库
                    purchaseUtils.generate("PUO", vo.getPsoNum(), sysU);
                }
            }
        }

        //添加采购单审核信息
        puoMapper.updateBychkr(user.getPrsnl().getPrsnlId() + "", vo.getPsoNum());

        if (vo.getSloGen().equals("T")) {
            if(StringUtils.isNotEmpty(vo.getPuoNum())){
                vo.setDocNum(sloMapper.selectByPsoNum(vo.getPsoNum()).getSloNum());
                //通知销售单审核事件
                return sloService.noticeExamine(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
            }else{
                //出库
                purchaseUtils.outStock("SLO", vo.getPsoNum(), new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
            }
        }
        return psoMapper.updateByState(vo);
    }

    /**
     * 采购单中反审单据
     *
     * @param vo
     * @return
     */
    @Override
    public int reiterate(PsoVo vo, SysUser user) throws Exception {
        //判断重审后进度的状态
        if (vo.getProgress().equals("CK") && vo.getSloGen().equals("T")) {
            vo.setProgress("RK");
        } else {
            vo.setProgress("C");
        }
        vo.setEffective("F");

        //采购合同记录
        PscVo psc = pucMapper.selectByOnly(new PscVo(vo.getPscNum()));
        //调用采购合同取消分配接口
        pucService.reverseAdmeasure(psc);
        //非居间合同
        if (this.contract(vo.getPscNum()) && vo.getPsoKind().equals("T")) {
            //判断监管往来
            if (purchaseService.judgePsc(vo.getPscNum()) && !psc.getVdeFsclUnitId().equals(psc.getVenderId())) {
                purchaseService.supervise(psc, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
            }
            if (pucService.contract(psc)) {
                //撤销未决登记
                Map<String, Object> map = new HashMap<>();
                List<String> stkList = new ArrayList<>();
                stkList.add("EP");
                map.put("stkList", stkList);
                String sql = " ,qty ";
                this.cancel(vo, map, sql);
                //撤销入库任务登记
                if (psc.getVdrInvd().equals("F")) {
                    warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUO", vo.getVendeeId(), vo.getDocNum()), user);
                }
            }
        }

        puoMapper.updateBychkr(null, vo.getPsoNum());

        //通知销售单重审事件
        if (vo.getSloGen().equals("T") && StringUtils.isNotEmpty(vo.getPuoNum())) {
            //查询销售单编号并赋值
            vo.setDocNum(sloMapper.selectByPsoNum(vo.getPsoNum()).getSloNum());
            return sloService.noticeReiterate(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        }
        return psoMapper.updateByState(vo);
    }

    /**
     * 挂起单据
     *
     * @param vo
     * @param sysUser
     * @return
     */
    @Override
    public int hangUp(PsoVo vo, SysUser sysUser) {
        vo.setSuspended("T");
        if(vo.getSloGen().equals("T")){
            this.sloSu(vo.getPsoNum(),vo.getSuspended());
        }
        return puoMapper.updateByPso(vo);
    }

    /**
     * 恢复单据
     *
     * @param vo
     * @param sysUser
     * @return
     */
    @Override
    public int recovery(PsoVo vo, SysUser sysUser) {
        vo.setSuspended("F");
        if(vo.getSloGen().equals("T")){
            this.sloSu(vo.getPsoNum(),vo.getSuspended());
        }
        return puoMapper.updateByPso(vo);
    }

    /**
     * 操作销售单挂起
     * @param psoNum
     * @param suspended
     */
    public void sloSu(String psoNum, String suspended){
        Slo slo = sloMapper.selectByPsoNum(psoNum);
        if(slo!=null){
            slo.setSuspended(suspended);
            sloMapper.updateByPrimaryKeySelective(slo);
        }
    }

    /**
     * 作废单据
     *
     * @param vo
     * @param sysUse
     * @return
     */
    @Override
    public int toVoid(PsoVo vo, SysUser sysUse) throws Exception {
        vo.setCancelled("T");
        this.doAbolish(vo, new SysUser(Long.valueOf(vo.getVendeeId()), sysUse.getPrsnl().getPrsnlId()));
        if (vo.getSloGen().equals("T")) {
            //调用销售单采购商作废通知
            sloService.vendeeAbolished(sloMapper.selectByOnly(new PsoVo(vo.getPsoNum())), new SysUser(Long.valueOf(vo.getVenderId()), sysUse.getPrsnl().getPrsnlId()));
        }
        return psoMapper.updateByState(vo);
    }

    //结束入库
    @Override
    public int closeGoods(PsoVo vo, SysUser user) throws Exception {
        vo.setProgress("RD");
        //采购合同记录
        PscVo psc = pucMapper.selectByOnly(new PscVo(vo.getPscNum()));
        //判断是否居间合同
        if (pucService.contract(psc)) {
            //删除入库任务
            warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUO", vo.getVendeeId(), vo.getDocNum()), user);
            //取消未决登记
            Map<String, Object> map = new HashMap<>();
            List<String> stkList = new ArrayList<>();
            if (vo.getVdrInvd().equals("T")) {
                stkList.add("IT");
            } else {
                stkList.add("EP");
                //单据和到货数量存在差异
                String sql = "rcv_qty";
                //查询采购单明细
                List<CommonDtl> dtlList = psoDtlMapper.findByDiff(vo.getPsoNum(), sql);
                if (!dtlList.isEmpty()) {
                    pucService.postAdmeasure(psc, dtlList, true);
                }
            }
            map.put("stkList", stkList);
            String sql = " ,qty ";
            this.cancel(vo, map, sql);
            //登记收发差异
            if (vo.getSloGen().equals("T")) {
                //查询销售单信息
                Slo slo = sloMapper.selectByPsoNum(vo.getPsoNum());
                //添加收发差异  判断是否有出库仓库与入库仓库
                if (StringUtils.NullEmpty(vo.getDelivWarehId() + "")) {
                    Long vdeWareh = gdnExternalCiteService.queryDelivWarehIdBySrcDocMess("SLO", slo.getSloNum(), Long.valueOf(slo.getUnitId()), Long.valueOf(vo.getVendeeId()));
                    vo.setDelivWarehId(vdeWareh.intValue());
                }
                //入库未查
                if (StringUtils.NullEmpty(vo.getRcvWarehId() + "")) {
                    Long vdeWareh = gdnExternalCiteService.queryRcvWarehIdBySrcDocMess("PUO", vo.getPuoNum(), Long.valueOf(vo.getUnitId()), Long.valueOf(vo.getVenderId()));
                    vo.setDelivWarehId(vdeWareh.intValue());
                }
                //调用收发差异登记
                tranDiffEvtService.register(new TranDiffEvt(Long.valueOf(slo.getUnitId()), "SLO", slo.getSloNum(), vo.getVendeeId(), "PUO", vo.getDocNum(), Long.valueOf(vo.getDelivWarehId()), Long.valueOf(vo.getRcvWarehId()), "R"));

            }
        }
        //判断是否监管往来
        if (purchaseService.judgePsc(vo.getPscNum()) && !psc.getVdeFsclUnitId().equals(psc.getVenderId())) {
            purchaseService.supervise(psc, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }
        //采购合同终止分配
        pscService.updateTaskByP(vo.getPscNum(), "2");

        return psoMapper.updateByState(vo);
    }

    //重启入库
    @Override
    public int restartGoods(PsoVo vo, SysUser user) throws Exception {
        if (vo.getVdrInvd().equals("F")) {
            vo.setProgress("CK");
        } else {
            vo.setProgress("DD");
        }
        //采购合同记录
        PscVo psc = pucMapper.selectByOnly(new PscVo(vo.getPscNum()));
        //判断是否居间合同
        if (pucService.contract(psc)) {
            //撤销收发差异
            if (vo.getSloGen().equals("T")) {
                //查询销售单信息
                Slo slo = sloMapper.selectByPsoNum(vo.getPsoNum());
                //调用取消收发差异登记
                tranDiffEvtService.deregister(new TranDiffEvt(Long.valueOf(slo.getUnitId()), "SLO", slo.getSloNum(), vo.getVendeeId(), "PUO", vo.getDocNum()));
            }
            Map<String, Object> map = new HashMap<>();
            List<String> stkList = new ArrayList<>();
            String sql = "";
            if (vo.getVdrInvd().equals("T")) {
                sql = " ,CASE WHEN IFNULL(deliv_qty,0)-IFNULL(rcv_qty,0) IS NULL THEN IFNULL(deliv_qty,0) ELSE IFNULL(deliv_qty,0)-IFNULL(rcv_qty,0) END qty ";
                stkList.add("IT");
            } else {
                sql = " ,CASE WHEN IFNULL(qty,0)-IFNULL(rcv_qty,0) IS NULL THEN IFNULL(qty,0) ELSE IFNULL(qty,0)-IFNULL(rcv_qty,0) END qty ";
                stkList.add("EP");

                //单据和到货数量存在差异
                List<CommonDtl> dtlList = psoDtlMapper.findByDiff(vo.getPsoNum(), "rcv_qty");
                if (!dtlList.isEmpty()) {
                    pucService.postAdmeasure(psc, dtlList, false);
                }
            }
            map.put("stkList", stkList);
            this.cancel(vo, map, sql);

            //登记入库任务
            if (vo.getPsoKind().equals("T")) {
                purchaseUtils.generate("PUO", vo.getPsoNum(), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
            }
        }

        //判断是否监管往来
        if (purchaseService.judgePsc(vo.getPscNum()) && psc.getMfzEnabled().equals("T")
                && psc.getPsMfzSite().equals("AD") && !psc.getVdeFsclUnitId().equals(psc.getVenderId())) {
            //计算冻结金额
            BigDecimal debitValue = vo.getTtlVal().subtract(vo.getTtlRcvVal());
            //通过是否有往来账户
            Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVendeeId()), Long.valueOf(vo.getVenderId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
            if (ca != null) {
                caAccService.freeze(new CaAcc(null, null, "PUC", psc.getUnitId(), psc.getPucNum()), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()), ca, debitValue, new BigDecimal("0"), true);
            }
        }

        //采购合同开始分配
        pscService.updateTaskByP(vo.getPscNum(), "1");

        return psoMapper.updateByState(vo);
    }

    /**
     * 导入合同明细
     *
     * @param vo
     * @return
     */
    @Override
    public Map<String, Object> importBill(PsoVo vo) {
        Map<String, Object> map = new HashMap<>();
        List<PscDtlVo> list = pscDtlMapper.importBill(vo.getPscNum());
        map.put("psoDtlList", list);
        return map;
    }

    /**
     * 导入更新
     *
     * @param vo
     * @return
     */
    @Override
    public int importUpdate(PsoVo vo) {
        //删除之前明细
        this.deleteByPsoDtl(vo.getPsoNum());
        PsoDtlVo dtlVo = new PsoDtlVo();
        dtlVo.setPsoNum(vo.getPsoNum());
        dtlVo.setList(vo.getPsoDtlList());
        return psoDtlService.insertPsoDtl(dtlVo);
    }

    /**
     * 作废私有方法
     *
     * @param vo
     * @param user
     */
    private void doAbolish(PsoVo vo, SysUser user) throws Exception {
        if (vo.getPsoKind().equals("T")) {
            //采购合同记录
            PscVo psc = pucMapper.selectByOnly(new PscVo(vo.getPscNum()));
            //判断监管往来
            if (purchaseService.judgePsc(vo.getPscNum())) {
                if (!psc.getVdeFsclUnitId().equals(psc.getVenderId())) {
                    purchaseService.supervise(psc, user);
                }
            }
            if (pucService.contract(psc)) {
                //取消未决登记
                Map<String, Object> map = new HashMap<>();
                List<String> stkList = new ArrayList<>();
                String sql = " ,qty ";
                stkList.add("EP");
                map.put("stkList", stkList);
                this.cancel(vo, map, sql);

                //撤销入库任务登记
                warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUO", vo.getVendeeId(), vo.getDocNum()), user);
            }
            //采购合同操作
            if (vo.getProgress().equals("PG") || vo.getProgress().equals("C") || vo.getProgress().equals("RK")
                    || vo.getProgress().equals("EK") || vo.getProgress().equals("CK")) {

                if (vo.getProgress().equals("RK") || vo.getProgress().equals("CK")) {
                    //调用采购合同取消分配接口
                    pucService.reverseAdmeasure(psc);
                }
                pscService.updateTaskByP(vo.getPscNum(), "2");
            }
        }
    }

    //销售单通知采购单审核事件
    @Override
    public int noticeExamine(PsoVo vo, SysUser user) throws Exception {
        Psc psc = pscMapper.selectByPrimaryKey(vo.getPscNum());
        if (psc.getPucAutoGen().equals("T") && vo.getProgress().equals("RK")) {
            return this.examine(vo, user);
        }
        return psoMapper.updateByState(vo);
    }

    //销售单通知采购单重审事件
    @Override
    public int noticeReiterate(PsoVo vo, SysUser user) throws Exception {
        Psc psc = pscMapper.selectByPrimaryKey(vo.getPscNum());
        if (psc.getSlcAutoChk().equals("T") && vo.getProgress().equals("RK")) {
            return this.reiterate(vo, user);
        } else {
            if (psc.getIsSlItmd().equals("F") && psc.getIsPuItmd().equals("F") && psc.getImplByIns().equals("F")) {
                Slo slo = sloMapper.selectByPsoNum(vo.getPsoNum());
                if (slo != null) {
                    warehDelivTaskMapper.deleteByBill(new WarehDelivTask(Long.valueOf(vo.getVendeeId() + ""), "SLO", Long.valueOf(slo.getUnitId() + ""), slo.getSloNum()));
                }
            }
        }
        return psoMapper.updateByState(vo);
    }

    @Override
    public int deleteByPuo(PsoVo vo) {
        return puoMapper.deleteByPso(vo.getVendeeId() + "", vo.getPsoNum());
    }

    //销售单生成采购单
    @Override
    public int insertByPuo(PsoVo vo, SysUser user) {
        Puo puo = new Puo();
        puo.setUnitId(vo.getVendeeId());
        puo.setSuspended("F");
        puo.setPsoNum(vo.getPsoNum());
        //通过系统参数PS_DOCUMENT_UNIQUED_NUMBERED去生成购销合同编号或者采购合同编号
        SysParameter sys = parameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        if (sys.getParmVal().equals("T")) {
            puo.setPuoNum(vo.getPsoNum());
        } else {
            String pucNum = sysRefNumService.viceNum(user, "PUO_NUM");
            puo.setPuoNum(pucNum);
        }
        return puoMapper.insertSelective(puo);
    }

    /**
     * 关闭单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int close(PsoVo vo, SysUser user) {
        vo.setProgress("CL");
        vo.setOprId(user.getPrsnl().getPrsnlId().intValue());
        if (vo.getSloGen().equals("T")) {
            //通知销售申请关闭事件
        }
        return psoMapper.updateByState(vo);
    }

    /**
     * 重用单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int reusing(PsoVo vo, SysUser user) {
        vo.setProgress("CK");
        vo.setOprId(user.getPrsnl().getPrsnlId().intValue());
        if (vo.getSloGen().equals("T")) {
            //通知销售申请重用事件
        }
        return psoMapper.updateByState(vo);
    }

    /**
     * 销售单通知采购单供应商作废事件
     *
     * @param vo
     * @param user
     */
    @Override
    public void venderAbolished(PsoVo vo, SysUser user) throws Exception {
        this.doAbolish(vo, user);
    }

    /**
     * 查询采购单可操作状态
     *
     * @param vo
     * @return
     */
    @Override
    public List<OperationVo> initButtons(PsoVo vo) {
        List<PurKeyValue> keyValues = buttonCommonService.initButton(parameter);
        PsoVo pso = puoMapper.selectByOnly(vo);
        if (pso == null) {
            throw new ServiceException("403", "单据异常！");
        }
        return creatButton(keyValues, pso);
    }

    /**
     * 查询单条记录
     *
     * @param vo
     * @return
     */
    @Override
    public PsoVo selectByOnly(PsoVo vo) {
        return puoMapper.selectByOnly(vo);
    }


    public List<OperationVo> creatButton(List<PurKeyValue> keyValues, PsoVo vo) {
        List<OperationVo> list = new ArrayList<>();
        for (PurKeyValue pur : keyValues) {
            List<PsoDtl> dtlList = psoDtlMapper.selectByOperation(vo.getPsoNum(), " ((deliv_qty!= null OR deliv_qty!=0) OR (rcv_qty!= null OR rcv_qty!=0)) ");
            if (("confirm").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("PG") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("确认", "T", confirm));
                } else {
                    list.add(new OperationVo("确认", "F", confirm));
                }
            }
            if (("importBill").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("PG") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("导入采购合同", "T", importBill));
                } else {
                    list.add(new OperationVo("导入采购合同", "F", importBill));
                }
            }
            if (("redo").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("C") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")
                        && StringUtils.NullEmpty(vo.getItmdPsoNum()) && StringUtils.NullEmpty(vo.getExecPsoNum())) {
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
                        && vo.getCancelled().equals("F") && dtlList.isEmpty()) {
                    list.add(new OperationVo("反审", "T", reiterate));
                } else {
                    list.add(new OperationVo("反审", "F", reiterate));
                }
            }
            if (("suspend").equals(pur.getOptionValue())) {
                if (!vo.getProgress().equals("RD") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
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
                        || vo.getProgress().equals("CK")) && vo.getSuspended().equals("F") && vo.getCancelled().equals("F") && dtlList.isEmpty()
                        && StringUtils.NullEmpty(vo.getItmdPsoNum()) && StringUtils.NullEmpty(vo.getExecPsoNum())) {
                    list.add(new OperationVo("作废", "T", toVoid));
                } else {
                    list.add(new OperationVo("作废", "F", toVoid));
                }
            }
            if (("end").equals(pur.getOptionValue())) {
                if (((vo.getVdrInvd().equals("F") && vo.getProgress().equals("CK")) || vo.getProgress().equals("DD"))
                        && vo.getPsoKind().equals("T") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("结束收货", "T", end));
                } else {
                    list.add(new OperationVo("结束收货", "F", end));
                }
            }
            if (("restart").equals(pur.getOptionValue())) {
                if (vo.getProgress().equals("RD") && vo.getPsoKind().equals("T") && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("重启收货", "T", restart));
                } else {
                    list.add(new OperationVo("重启收货", "F", restart));
                }
            }
        }

        return list;
    }

    //取消未决公共方法
    private void cancel(PsoVo vo, Map<String, Object> map, String sql) {
        List<PsoDtl> dtlList = psoDtlMapper.selectByBill(vo.getPsoNum(), sql);
        if (dtlList != null && dtlList.size() > 0) {
            map.put("docType", "PUO");
            map.put("unitId", vo.getVendeeId());
            map.put("docNum", vo.getDocNum());
            map.put("warehId", vo.getRcvWarehId());
            map.put("dtlList", dtlList);
            warehStkPgMapper.deleteByDtl(map);
        }
    }

    /**
     * 出入库明细查询
     *
     * @param vo
     * @return
     */
    @Override
    public PsoVo selectObject(PsoVo vo) {
        return puoMapper.selectObject(vo);
    }

    /**
     * 供货方发货通知
     *
     * @param vo
     * @param docUnitId
     * @param docNum
     * @return
     */
    @Override
    public void venderDelivered(PscVo psc, PsoVo vo, Long docUnitId, String docNum, SysUser user) throws Exception {
        //判断非居间  任务性质
        if (pucService.contract(psc) && vo.getPsoKind().equals("T")) {
            //取消库存登记 预期
            Map<String, Object> map = new HashMap<>();
            List<String> stkList = new ArrayList<>();
            stkList.add("EP");
            map.put("stkList", stkList);
            String sql = " ,qty ";
            this.cancel(vo, map, sql);
            //登记未决 在途  本次数量
            map.put("docType", "PUO");
            map.put("unitId", vo.getVendeeId());
            map.put("docNum", vo.getDocNum());
            map.put("warehId", vo.getRcvWarehId());
            map.put("stkType", "IT");
            map.put("stbUnitId", docUnitId);
            map.put("stbNum", docNum);
            warehStkPgMapper.increaseStkPg(map);
            //判断是否存在差异，
            List<CommonDtl> dtlList = psoDtlMapper.findByDiff(vo.getPsoNum(), "deliv_qty");
            if (!dtlList.isEmpty()) {
                pucService.postAdmeasure(psc, dtlList, true);
            }
            //登记入库任务
            purchaseUtils.generate("PUO", vo.getPsoNum(), user);
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
        }
    }

    /**
     * 供货方取消发货通知
     *
     * @param vo
     * @param docUnitId
     * @param docNum
     * @param user
     */
    @Override
    public void venderReverseDelivered(PscVo psc, PsoVo vo, Long docUnitId, String docNum, SysUser user) throws Exception {
        //判断非居间  任务性质
        if (pucService.contract(psc) && vo.getPsoKind().equals("T")) {
            //减少采购合同相关库存
            List<CommonDtl> dtlList = psoDtlMapper.findByDiff(vo.getPsoNum(), "deliv_qty");
            if (!dtlList.isEmpty()) {
                pucService.postAdmeasure(psc, dtlList, false);
            }
            //取消未决库存  在途
            Map<String, Object> map = new HashMap<>();
            map.put("docType", "PUO");
            map.put("unitId", vo.getVendeeId());
            map.put("docNum", vo.getDocNum());
            map.put("warehId", vo.getRcvWarehId());
            map.put("stkType", "IT");
            map.put("stbUnitId", docUnitId);
            map.put("stbNum", docNum);
            warehStkPgMapper.deleteStkPg(map);
            //登记未决库存  预期
            map.put("stkType", "EP");
            map.put("dtlList", psoDtlMapper.findByDiffSql(vo.getPsoNum(), "qty"));
            warehStkPgMapper.insertByDtl(map);
            //撤销入库
            warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUO", vo.getVendeeId(), vo.getDocNum()), user);
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
        }
    }

    /**
     * 采购单开始收货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @param docUnitId
     * @param docNum
     * @param user
     * @return
     */
    @Override
    public int startReceive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        //查询购销单数据
        PsoVo vo = puoMapper.selectObject(new PsoVo(srcDocNum, "", srcDocUnitId + ""));
        if (vo != null) {
            if (((vo.getVdrInvd().equals("F") && vo.getProgress().equals("CK")) || vo.getProgress().equals("DD"))
                    && vo.getPsoKind().equals("T") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                vo.setProgress("RG");
                PscVo psc = pucMapper.selectByOnly(new PscVo(vo.getPscNum()));
                //判断非居间
                if (pucService.contract(psc)) {
                    //通知采购合同开始收货
                    pucService.startReceive(psc.getUnitId(), psc.getDocNum(), docUnitId, docNum, user);
                    //撤销入库任务
                    warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUO", vo.getVendeeId(), vo.getDocNum()), user);
                }
            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
        return psoMapper.updateByPso(vo);
    }

    /**
     * 采购单终止收货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @param docUnitId
     * @param docNum
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int stopReceive(Long srcDocUnitId, String srcDocNum, Long docUnitId, String docNum, SysUser user) throws Exception {
        //查询购销单数据
        PsoVo vo = puoMapper.selectObject(new PsoVo(srcDocNum, "", srcDocUnitId + ""));
        if (vo != null) {
            if (vo.getProgress().equals("RG") && vo.getSuspended().equals("F")) {
                if (vo.getVdrInvd().equals("T")) {
                    vo.setProgress("DD");
                } else {
                    vo.setProgress("CK");
                }
                PscVo psc = pucMapper.selectByOnly(new PscVo(vo.getPscNum()));
                //判断非居间
                if (pucService.contract(psc)) {
                    //通知采购合同开始收货
                    pucService.stopReceive(psc.getUnitId(), psc.getDocNum(), docUnitId, docNum, user);
                    if (vo.getPsoKind().equals("T")) {
                        //登记入库
                        purchaseUtils.generate("PUO", vo.getPsoNum(), user);
                    }
                }
            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
        return psoMapper.updateByPso(vo);
    }

    /**
     * 采购单收货
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
        //查询原始单据数据 将发货信息同步
        PsoVo vo = puoMapper.getPsoByStb(srcDocUnitId, srcDocNum, docUnitId, docNum, true);
        //单据信息
        Stb stb = stbService.getStbByPk(new Stb(docUnitId, docNum));
        //采购合同记录
        PscVo psc = pucMapper.selectByOnly(new PscVo(vo.getPscNum()));
        if (vo != null) {
            if (vo.getProgress().equals("RG") && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
                vo.setProgress("RD");
                //更新收货信息
                List<PsoDtl> dtlList = null;
                if (vo.getPuoGen().equals("T")) {
                    dtlList = psoDtlMapper.selectByStbDtl(vo.getPsoNum(), docUnitId, docNum, true, "PUO");
                } else {
                    dtlList = psoDtlMapper.findByStbDtl(vo.getPsoNum(), docUnitId, docNum, true);
                }
                if (dtlList != null && dtlList.size() > 0) {
                    psoDtlMapper.updateDtlList(dtlList);
                }
                //判断是否继续收货  0 停止  1 继续
                int judge = 0;
                SysParameter sysPa = parameterMapper.findById("PURCHASE_ORDER_SDMR_ENABLED");
                if (sysPa == null) {
                    throw new ServiceException(JsonResultCode.FAILURE, "系统参数PURCHASE_ORDER_SDMR_ENABLED为空！");
                }
                String sql = "";
                if (vo.getVdrInvd().equals("T")) {
                    sql = " IFNULL(rcv_qty,0) < IFNULL(deliv_qty,0) ";
                } else {
                    sql = " IFNULL(rcv_qty,0) < IFNULL(qty,0) ";
                }
                if (sysPa.getParmVal().equals("T") && !psoDtlMapper.findByDiff(vo.getPsoNum(), sql).isEmpty()) {
                    judge = 1;
                }
                //判断监管往来
                if (purchaseService.judgePsc(vo.getPscNum()) && psc.getMfzEnabled().equals("T")
                        && psc.getPsMfzSite().equals("AD") && !psc.getVdeFsclUnitId().equals(psc.getVenderId())) {
                    //通过供应商id与采购商id查询是否有往来账户
                    Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVendeeId()), Long.valueOf(vo.getVenderId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                    if (ca != null) {
                        //继续收货 负数登记冻结 否则解冻
                        if (judge == 1) {
                            caAccService.freeze(new CaAcc(null, null, "PUC", psc.getUnitId(), psc.getPucNum()), user, ca, new BigDecimal("0").subtract(stb.getTtlVal()), new BigDecimal("0"), false);
                        } else {
                            //解冻
                            purchaseService.supervise(psc, user);
                        }
                    }
                }
                //判断居间 登记未决
                if (pucService.contract(psc)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("docType", "PUO");
                    map.put("warehId", vo.getRcvWarehId());
                    map.put("unitId", vo.getVendeeId());
                    map.put("docNum", vo.getDocNum());
                    //设置库存类别
                    if (vo.getVdrInvd().equals("T")) {
                        map.put("stkType", "IT");
                    } else {
                        map.put("stkType", "EP");
                        //单据数与到货数存在差异
                        List<CommonDtl> comList = psoDtlMapper.findByDiff(vo.getPsoNum(), "rcv_qty");
                        if (!comList.isEmpty()) {
                            pucService.postAdmeasure(psc, comList, true);
                        }
                    }
                    //继续收货 减少  否则注销
                    if (judge == 1) {
                        //采用本地数量
                        List<StbDtlVo> stbDtlVo = stbDtlService.getStbDtlList(new StbDtl(docUnitId, docNum));
                        map.put("dtlList", stbDtlVo);
                        map.put("reversed", false);
                        warehStkPgMapper.updateByAdmeasure(map);
                    } else {
                        List<String> stkList = new ArrayList<>();
                        stkList.add(map.get("stkType").toString());
                        map.put("stkList", stkList);
                        this.cancel(vo, map, " ,qty ");
                    }
                }
                //调用采购合同收货
                pucService.receive(psc.getUnitId(), psc.getDocNum(), docUnitId, docNum, user);

                if (vo.getSloGen().equals("F")) {
                    vo.setTtlDelivBox(vo.getTtlRcvBox());
                    vo.setTtlDelivTax(vo.getTtlRcvTax());
                    vo.setTtlDelivMkv(vo.getTtlRcvMkv());
                    vo.setTtlDelivQty(vo.getTtlRcvQty());
                    vo.setTtlDelivQty(vo.getTtlRcvQty());
                } else {
                    //判断是否登记收发差异
                    if (pucService.contract(psc) && judge == 0 && !psoDtlMapper.selectByOperation(vo.getPsoNum(), " IFNULL( pDtl.deliv_qty, 0 ) <> IFNULL( pDtl.rcv_qty, 0 ) ").isEmpty()) {
                        //查询出销售合同号
                        Slo slo = sloMapper.selectByPsoNum(vo.getPsoNum());
                        //添加收发差异  判断是否有出库仓库与入库仓库
                        if (StringUtils.NullEmpty(vo.getDelivWarehId() + "")) {
                            Long vdeWareh = gdnExternalCiteService.queryDelivWarehIdBySrcDocMess("SLO", slo.getSloNum(), slo.getUnitId(), Long.valueOf(vo.getVendeeId()));
                            vo.setDelivWarehId(vdeWareh.intValue());
                        }
                        //入库未查
                        if (StringUtils.NullEmpty(vo.getRcvWarehId() + "")) {
                            Long vdeWareh = gdnExternalCiteService.queryRcvWarehIdBySrcDocMess("PUO", vo.getPuoNum(), vo.getUnitId(), Long.valueOf(vo.getVenderId()));
                            vo.setRcvWarehId(vdeWareh.intValue());
                        }
                        tranDiffEvtService.register(new TranDiffEvt(slo.getUnitId(), "SLO", slo.getSloNum(), vo.getUnitId(), "PUO", vo.getDocNum(), Long.valueOf(vo.getDelivWarehId()), Long.valueOf(vo.getRcvWarehId()), "R"));
                    }
                }
                //继续收货 更改状态
                if (judge == 1) {
                    if (vo.getVdrInvd().equals("F")) {
                        vo.setProgress("CK");
                    } else {
                        vo.setProgress("DD");
                    }
                    pucService.startAdmeasure(psc);
                }
                if (pucService.contract(psc) && judge == 1 && vo.getPsoKind().equals("T")) {
                    //登记入库任务
                    purchaseUtils.generate("PUO", vo.getPsoNum(), user);
                }
            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
        return sloMapper.updateByPuo(vo);
    }

    /**
     * 采购单取消收货
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
        //查询原始单据数据 将发货信息同步
        PsoVo vo = puoMapper.getPsoByStb(srcDocUnitId, srcDocNum, docUnitId, docNum, false);
        //单据信息
        Stb stb = stbService.getStbByPk(new Stb(docUnitId, docNum));
        //采购合同记录
        PscVo psc = pucMapper.selectByOnly(new PscVo(vo.getPscNum()));
        if (vo != null) {
            SysParameter sysPa = parameterMapper.findById("PURCHASE_ORDER_SDMR_ENABLED");
            if (sysPa == null) {
                throw new ServiceException(JsonResultCode.FAILURE, "系统参数PURCHASE_ORDER_SDMR_ENABLED为空！");
            }
            if( (vo.getProgress().equals("RD")||(sysPa.getParmVal().equals("T")&&(vo.getProgress().equals("DD")||(vo.getVdrInvd().equals("F")
                &&vo.getProgress().equals("CK")))))&&vo.getSuspended().equals("F")&&vo.getCancelled().equals("F")){
                //初始状态
                String oldProgress = vo.getProgress();
                //更新状态
                vo.setProgress("RG");
                //调用采购合同取消收货
                pucService.reverseReceive(psc.getUnitId(),psc.getDocNum(),docUnitId,docNum,user);
                //获取当前单据与到货差异  预存
                List<CommonDtl> comList = psoDtlMapper.findByDiff(vo.getPsoNum(),"rcv_qty");
                //更新收货信息
                List<PsoDtl> dtlList = null;
                if (vo.getPuoGen().equals("T")) {
                    dtlList = psoDtlMapper.selectByStbDtl(vo.getPsoNum(), docUnitId, docNum, false, "PUO");
                } else {
                    dtlList = psoDtlMapper.findByStbDtl(vo.getPsoNum(), docUnitId, docNum, false);
                }
                if (dtlList != null && dtlList.size() > 0) {
                    psoDtlMapper.updateDtlList(dtlList);
                }
                //判断监管往来
                if (purchaseService.judgePsc(vo.getPscNum()) && psc.getMfzEnabled().equals("T")
                        && psc.getPsMfzSite().equals("AD") && !psc.getVdeFsclUnitId().equals(psc.getVenderId())) {
                    //通过供应商id与采购商id查询是否有往来账户
                    Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVendeeId()), Long.valueOf(vo.getVenderId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                    if (ca != null) {
                        //继续收货 负数登记冻结 否则解冻
                        BigDecimal debitValue = new BigDecimal("0");
                        if (oldProgress.equals("RD")) {
                            debitValue = vo.getTtlVal().subtract(vo.getTtlRcvVal());
                        } else {
                            debitValue = stb.getTtlVal();
                        }
                        caAccService.freeze(new CaAcc(null, null, "PUC", psc.getUnitId(), psc.getPucNum()), user, ca, debitValue, new BigDecimal("0"), true);
                    }
                }
                //判断居间 登记未决
                if (pucService.contract(psc)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("docType", "PUO");
                    map.put("warehId", vo.getRcvWarehId());
                    map.put("unitId", vo.getVendeeId());
                    map.put("docNum", vo.getDocNum());
                    //设置库存类别
                    String sql = "";
                    List<CommonDtl> diffList = null;
                    if (vo.getVdrInvd().equals("T")) {
                        map.put("stkType", "IT");
                        diffList = psoDtlMapper.findByDiffSql(vo.getPsoNum(), "IFNULL(deliv_qty,0)-IFNULL(rcv_qty,0) as qty ");
                    } else {
                        diffList = psoDtlMapper.findByDiffSql(vo.getPsoNum(), "IFNULL(qty,0)-IFNULL(rcv_qty,0) as qty ");
                        map.put("stkType", "EP");
                        //后天分配调整
                        if(!comList.isEmpty()&&oldProgress.equals("RD")){
                            pucService.postAdmeasure(psc,comList,false);
                        }
                    }
                    //继续收货 减少  否则注销
                    if (oldProgress.equals("RD")) {
                        map.put("dtlList",diffList);
                    } else {
                        //采用本地数量
                        List<StbDtlVo> stbDtlVo = stbDtlService.getStbDtlList(new StbDtl(docUnitId, docNum));
                        map.put("dtlList",stbDtlVo);
                    }
                    warehStkPgMapper.updateByAdmeasure(map);
                }
                if(vo.getSloGen().equals("F")){
                    vo.setTtlDelivBox(vo.getTtlRcvBox());
                    vo.setTtlDelivTax(vo.getTtlRcvTax());
                    vo.setTtlDelivMkv(vo.getTtlRcvMkv());
                    vo.setTtlDelivQty(vo.getTtlRcvQty());
                    vo.setTtlDelivQty(vo.getTtlRcvQty());
                }else{
                    //撤销收发差异
                    if (pucService.contract(psc)){
                        //查询出销售合同号
                        Slo slo = sloMapper.selectByPsoNum(vo.getPsoNum());
                        //删除收发差异
                        tranDiffEvtService.deregister(new TranDiffEvt(slo.getUnitId(), "SLO", slo.getSloNum(), vo.getUnitId(), "PUO", vo.getPuoNum()));
                    }
                }
                //终止分配
                if(!oldProgress.equals("RD")){
                    pucService.stopAdmeasure(psc);
                    //判断是否撤销入库任务
                    if (pucService.contract(psc)){
                        warehRcvTaskSerivce.deregisterWarehRcvTask(new WarehRcvTask(null, "PUO", vo.getVendeeId(), vo.getDocNum()), user);
                    }
                }

            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
        return sloMapper.updateByPuo(vo);
    }

    //判断是否居间单据
    private boolean contract(String pscNum) {
        return pucService.contract(pucMapper.selectByOnly(new PscVo(pscNum)));
    }
}
