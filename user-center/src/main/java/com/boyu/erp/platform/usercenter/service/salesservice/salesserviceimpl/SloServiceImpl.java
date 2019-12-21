package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.basic.CaAcc;
import com.boyu.erp.platform.usercenter.entity.purchase.*;
import com.boyu.erp.platform.usercenter.entity.sales.Slo;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.CaMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.*;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SloMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehDelivTaskMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehRcvTaskMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehStkPgMapper;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.caservice.CaAccService;
import com.boyu.erp.platform.usercenter.service.common.impl.ButtonCommonService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.*;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcService;
import com.boyu.erp.platform.usercenter.service.salesservice.SloService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehDelivTaskService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.PurchaseUtils;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
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
 * @Classname SloServiceImpl
 * @Description TODO
 * @Date 2019/8/12 15:28
 * @Created by wz
 */
@Slf4j
@Service
@Transactional
public class SloServiceImpl implements SloService {

    //销售单模块按钮参数
    static final String parameter = "sloButton";

    //确认
    static final String confirm = "/sales/slo/confirm";
    //重做
    static final String redo = "/sales/slo/redo";
    //审核
    static final String examine = "/sales/slo/examine";
    //反审
    static final String reiterate = "/sales/slo/reiterate";
    //挂起
    static final String hangUp = "/sales/slo/hangUp";
    //恢复
    static final String recovery = "/sales/slo/recovery";
    //作废
    static final String toVoid = "/sales/slo/toVoid";
    //导入销售合同
    static final String importBill = "/sales/slo/importBill";

    @Autowired
    private ButtonCommonService buttonCommonService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysParameterMapper sysParameterMapper;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private SloMapper sloMapper;
    @Autowired
    private PsoDtlService psoDtlService;
    @Autowired
    private PscService pscService;
    @Autowired
    private PsoMapper psoMapper;
    @Autowired
    private PuoService puoService;
    @Autowired
    private PscMapper pscMapper;
    @Autowired
    private PuoMapper puoMapper;
    @Autowired
    private PurchaseUtils purchaseUtils;
    @Autowired
    private PsoDtlMapper psoDtlMapper;
    @Autowired
    private WarehDelivTaskMapper warehDelivTaskMapper;
    @Autowired
    private SlcMapper slcMapper;
    @Autowired
    private SlcService slcService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PucService pucService;
    @Autowired
    private WarehStkPgMapper warehStkPgMapper;
    @Autowired
    private CaMapper caMapper;
    @Autowired
    private CaAccService caAccService;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private WarehRcvTaskMapper warehRcvTaskMapper;
    @Autowired
    private PscDtlMapper pscDtlMapper;
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
     */
    @Override
    public PageInfo<PsoVo> selectAll(PsoVo vo, Integer page, Integer size, SysUser user) {
        List<PsoVo> list = null;
        /**
         * 系统管理员
         * */
        if (false) {
            PageHelper.startPage(page, size);
            list = sloMapper.selectALL(vo);
        } else {
            PageHelper.startPage(page, size);
            list = sloMapper.selectByUnit(vo);
        }
        PageInfo<PsoVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 采购单确认生成销售单
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertByPuo(PsoVo vo, SysUser user) {
        Slo slo = new Slo();
        slo.setUnitId(vo.getVenderId());
        slo.setSuspended("F");
        slo.setPsoNum(vo.getPsoNum());
        //通过系统参数PS_DOCUMENT_UNIQUED_NUMBERED去生成购销合同编号或者采购合同编号
        SysParameter sys = sysParameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        if (sys.getParmVal().equals("T")) {
            slo.setSloNum(vo.getPsoNum());
        } else {
            String slcNum = sysRefNumService.viceNum(user, "SLO_NUM");
            slo.setSloNum(slcNum);
        }
        return sloMapper.insertSelective(slo);
    }

    @Override
    public int deleteByPuo(PsoVo vo) {
        return sloMapper.deleteByPuo(vo.getVenderId() + "", vo.getPsoNum());
    }

    /**
     * 新增销售单
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertSlo(PsoVo vo, SysUser user) {
        //通过参数去判断采购单和购销单号是否一样
        SysParameter sys = sysParameterMapper.findById("PS_DOCUMENT_UNIQUED_NUMBERED");
        SysUser sysU = new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId());
        if (sys.getParmVal().equals("T")) {
            String mainNum = sysRefNumService.mainNum(sysU, "PSO_NUM");
            vo.setPsoNum(mainNum);
            vo.setSloNum(mainNum);
        } else {
            String mainNum = sysRefNumService.mainNum(sysU, "PSO_NUM");
            String viceNum = sysRefNumService.viceNum(sysU, "SLO_NUM");
            vo.setPsoNum(mainNum);
            vo.setSloNum(viceNum);
        }
        vo.setOprId(user.getPrsnl().getPrsnlId().intValue());
        //默认不生成采购单
        vo.setSloGen("T");
        vo.setPuoGen("F");
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
        pscService.updateTaskByS(vo.getPscNum(), "1");

        return sloMapper.insertByPuo(vo);
    }

    /**
     * 更新销售单
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int updateSlo(PsoVo vo, SysUser user) {
        //通过购销合同号判断是否更改，更改就修改原购销合同的任务数
        vo.setOprId((int) user.getPrsnl().getPrsnlId().intValue());
        //通过购销单号获取修改前数据
        Pso pso = psoMapper.selectByPrimaryKey(vo.getPsoNum());
        if (pso != null) {
            //相等就直接修改数据
            if (!pso.getPscNum().equals(vo.getPscNum())) {
                //不相等的话  调用方法  减掉并更新原采购单的任务数  在更新pso表
                pscService.updateTaskByS(vo.getPscNum(), "2");
            }
        }
        sloMapper.updateByPuo(vo);
        return psoMapper.updateByPso(vo);
    }

    /**
     * 删除销售单
     *
     * @param vo
     * @return
     */
    @Override
    public int deleteSlo(PsoVo vo) {
        //删除slo表  在根据pso中PUO_GEN的值判断是否删除还是更新pso表
        sloMapper.deletePuo(vo.getSloNum(), vo.getPsoNum());
        vo.setSloGen("F");

        pscService.updateTaskByS(vo.getPscNum(), "2");

        if (vo.getVdeInvd().equals("T") && vo.getPuoGen().equals("T")) {
            //删除销售单数据  预留
            vo.setPuoNum(puoMapper.selectByPsoNun(vo.getPsoNum()).getPuoNum());
            return puoService.deletePuo(vo);
        }
        if (vo.getPuoGen().equals("F")) {
            //删除pso表时 删除明细
            psoDtlMapper.deleteByPsoNum(vo.getPsoNum());
            //删除pso
            psoMapper.deleteByPrimaryKey(vo.getPsoNum());
        }
        return 1;
    }

    /**
     * 确认单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int confirm(PsoVo vo, SysUser user) {
        //将状态改为已确认
        vo.setProgress("C");

        vo.setOprId(user.getPrsnl().getPrsnlId().intValue());

        Psc psc = pscMapper.selectByPrimaryKey(vo.getPscNum());
        if (psc != null) {
            if (psc.getPucAutoGen().equals("T") && vo.getPuoGen().equals("F")) {
                vo.setPuoGen("T");
                //创建采购单
                puoService.insertByPuo(vo, user);
            }
        }

        return psoMapper.updateByState(vo);
    }

    /**
     * 重做单据
     *
     * @param vo
     * @return
     */
    @Override
    public int redo(PsoVo vo) {
        vo.setProgress("PG");
        Psc psc = pscMapper.selectByPrimaryKey(vo.getPscNum());
        if (vo.getPuoGen().equals("T") && psc.getPucAutoGen().equals("T")) {
            vo.setPuoGen("F");
            puoMapper.deleteByPso(null, vo.getPsoNum());
        }
        return psoMapper.updateByState(vo);
    }

    /**
     * 审核单据
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int examine(PsoVo vo, SysUser user) throws Exception {
        //将状态改为已审核状态  并修改采购合同与销售合同审核状态
        if (vo.getProgress().equals("C") && vo.getPuoGen().equals("T")) {
            vo.setProgress("RK");
            vo.setEffective("F");
        } else {
            vo.setProgress("CK");
            vo.setEffective("T");
        }
        //销售合同记录
        PscVo psc = slcMapper.selectByOnly(new PscVo(vo.getPscNum()));
        //调用采购合同分配接口
        List<CommonDtl> comList = psoDtlMapper.findByDiffSql(vo.getPsoNum(),"qty");
        slcService.admeasure(psc,comList);

        //重置
        SysUser sysU = new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId());
        //非居间合同
        if (this.contract(vo.getPscNum()) && vo.getPsoKind().equals("T")) {
            //判断监管往来
            if (purchaseService.judgePsc(vo.getPscNum()) && psc.getMfzEnabled().equals("T")
                    && psc.getPsMfzSite().equals("AD") && !psc.getVdrFsclUnitId().equals(psc.getVendeeId())) {
                //通过供应商id与采购商id查询是否有往来账户
                Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                if (ca != null) {
                    caAccService.freeze(new CaAcc(0L, 0L, "SLC", psc.getUnitId(), psc.getSlcNum()), sysU, ca, new BigDecimal("0"), vo.getTtlVal(),true);
                }
            }
            if (pucService.contract(psc)) {
                //登记未决
                Map<String, Object> map = new HashMap<>();
                List<PsoDtlVo> psoDtlList = psoDtlMapper.findByPuoNum(new PsoDtlVo(vo.getPsoNum()));
                if (psoDtlList != null && psoDtlList.size() > 0) {
                    map.put("stkType", "CM");
                    map.put("docType", "SLO");
                    map.put("warehId", vo.getDelivWarehId());
                    map.put("unitId", vo.getVenderId());
                    map.put("docNum", vo.getDocNum());
                    map.put("dtlList", psoDtlList);
                    warehStkPgMapper.insertByDtl(map);
                }

                //登记出库
                if (vo.getProgress().equals("CK") && StringUtils.NullEmpty(vo.getStPsoNum())) {
                    purchaseUtils.outStock("SLO", vo.getPsoNum(), sysU);
                }
            }
        }

        //添加销售单审核信息
        sloMapper.updateBychkr(user.getPrsnl().getPrsnlId() + "", vo.getPsoNum());

        if (vo.getPuoGen().equals("T") && StringUtils.isNotEmpty(vo.getSloNum())) {
            vo.setDocNum(puoMapper.selectByPsoNun(vo.getPsoNum()).getPuoNum());
            //调用采购单通知审核事件
            return puoService.noticeExamine(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }

        return psoMapper.updateByState(vo);
    }

    /**
     * 反审单据
     *
     * @param vo
     * @return
     */
    @Override
    public int reiterate(PsoVo vo, SysUser user) throws Exception {
        if (vo.getProgress().equals("CK") && vo.getPuoGen().equals("T")) {
            vo.setProgress("EK");
        } else {
            vo.setProgress("C");
        }
        vo.setEffective("F");

        //销售合同记录
        PscVo psc = slcMapper.selectByOnly(new PscVo(vo.getPscNum()));
        //调用销售合同取消分配接口
        List<CommonDtl> comList = psoDtlMapper.findByDiffSql(vo.getPsoNum(),"qty");
        slcService.reverseAdmeasure(psc, comList);

        //非居间合同
        if (this.contract(vo.getPscNum()) && vo.getPsoKind().equals("T")) {
            //判断监管往来
            if (purchaseService.judgePsc(vo.getPscNum()) && !psc.getVdrFsclUnitId().equals(psc.getVendeeId())) {
                purchaseService.superviseBySales(psc, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
            }
            if (pucService.contract(psc)) {
                //撤销未决登记
                Map<String, Object> map = new HashMap<>();
                List<String> stkList = new ArrayList<>();
                stkList.add("CM");
                map.put("stkList", stkList);
                String sql = " ,qty ";
                this.cancel(vo, map, sql);
                //撤销出库任务登记
                warehDelivTaskService.delDelivTask(new WarehDelivTask(null, "SLO", Long.valueOf(vo.getVenderId() + ""), vo.getDocNum()),user);
            }
        }

        //删除审核信息
        sloMapper.updateBychkr(null, vo.getPsoNum());
        if (vo.getPuoGen().equals("T") && StringUtils.isNotEmpty(vo.getSloNum())) {
            vo.setDocNum(puoMapper.selectByPsoNun(vo.getPsoNum()).getPuoNum());
            //通知采购单重审事件
            return puoService.noticeReiterate(vo, new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
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
        if(vo.getPuoGen().equals("T")){
            this.puoSu(vo.getPsoNum(),vo.getSuspended());
        }
        return sloMapper.updateByPso(vo);
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
        if(vo.getPuoGen().equals("T")){
            this.puoSu(vo.getPsoNum(),vo.getSuspended());
        }
        return sloMapper.updateByPso(vo);
    }

    /**
     * 操作采购单挂起
     * @param psoNum
     * @param suspended
     */
    public void puoSu(String psoNum, String suspended){
        Puo puo = puoMapper.selectByPsoNun(psoNum);
        if(puo!=null){
            puo.setSuspended(suspended);
            puoMapper.updateByPrimaryKeySelective(puo);
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
    public int toVoid(PsoVo vo, SysUser user) throws Exception {
        vo.setCancelled("T");
        this.doAbolish(vo, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
        if (vo.getPuoGen().equals("T")) {
            //调用销售单采购商作废通知
            puoService.venderAbolished(puoMapper.selectByOnly(new PsoVo(vo.getPsoNum())), new SysUser(Long.valueOf(vo.getVendeeId()), user.getPrsnl().getPrsnlId()));
        }
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
     * 采购单中通知销售单审核事件
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int noticeExamine(PsoVo vo, SysUser user) throws Exception {
        Psc psc = pscMapper.selectByPrimaryKey(vo.getPscNum());
        if (psc.getSlcAutoGen().equals("T") && vo.getProgress().equals("EK")) {
            return this.examine(vo, user);
        } else {
            if (psc.getIsPuItmd().equals("F") && psc.getIsSlItmd().equals("F") && vo.getProgress().equals("CK")
                    && StringUtils.NullEmpty(vo.getStPsoNum())) {
                //出库
                purchaseUtils.outStock("SLO", vo.getPsoNum(), user);
            }
        }

        return psoMapper.updateByState(vo);
    }

    /**
     * 采购单通知销售单反审事件
     *
     * @param vo
     * @return
     */
    @Override
    public int noticeReiterate(PsoVo vo, SysUser sysUser) throws Exception {
        Psc psc = pscMapper.selectByPrimaryKey(vo.getPscNum());
        if (psc.getSlcAutoChk().equals("T") && vo.getProgress().equals("RK")) {
            return this.reiterate(vo, sysUser);
        } else {
            if (psc.getIsSlItmd().equals("F") && psc.getIsPuItmd().equals("F")) {
                warehDelivTaskMapper.deleteByBill(new WarehDelivTask(Long.valueOf(vo.getVenderId() + ""), "SLO", Long.valueOf(vo.getVenderId() + ""), vo.getDocNum()));
            }
        }
        return psoMapper.updateByState(vo);
    }

    /**
     * 采购商作废事件
     *
     * @param vo
     * @param sysUser
     */
    @Override
    public void vendeeAbolished(PsoVo vo, SysUser sysUser) throws Exception {
        this.doAbolish(vo, sysUser);
    }

    /**
     * 作废方法
     *
     * @param vo
     * @param user
     */
    private void doAbolish(PsoVo vo, SysUser user) throws Exception {
        //销售合同记录
        PscVo psc = slcMapper.selectByOnly(new PscVo(vo.getPscNum()));
        //判断监管往来
        if (purchaseService.judgePsc(vo.getPscNum())) {
            if (!psc.getVdrFsclUnitId().equals(psc.getVendeeId())) {
                purchaseService.superviseBySales(psc, user);
            }
        }
        if (pucService.contract(psc)) {
            //取消未决登记
            Map<String, Object> map = new HashMap<>();
            List<String> stkList = new ArrayList<>();
            String sql = " ,qty ";
            stkList.add("CM");
            map.put("stkList", stkList);
            this.cancel(vo, map, sql);
            if (vo.getPsoKind().equals("T")) {
                warehDelivTaskMapper.deleteByBill(new WarehDelivTask(Long.valueOf(vo.getVenderId() + ""), "SLO", Long.valueOf(vo.getVenderId() + ""), vo.getDocNum()));
            }
        }
        //销售合同操作
        if (vo.getPsoKind().equals("T")) {
            if (vo.getProgress().equals("PG") || vo.getProgress().equals("C") || vo.getProgress().equals("RK")
                    || vo.getProgress().equals("EK") || vo.getProgress().equals("CK")) {

                if (vo.getProgress().equals("EK") || vo.getProgress().equals("CK")) {
                    //调用采购合同取消分配接口
                    List<CommonDtl> comList = psoDtlMapper.findByDiffSql(vo.getPsoNum(),"qty");
                    slcService.reverseAdmeasure(psc, comList);
                }
                pscService.updateTaskByS(vo.getPscNum(), "2");
            }
        }
    }

    /**
     * 取消未决公共方法
     *
     * @param vo
     * @param map
     * @param sql
     */
    private int cancel(PsoVo vo, Map<String, Object> map, String sql) {
        List<PsoDtl> dtlList = psoDtlMapper.selectByBill(vo.getPsoNum(), sql);
        if (dtlList != null && dtlList.size() > 0) {
            map.put("docType", "SLO");
            map.put("unitId", vo.getVenderId());
            map.put("docNum", vo.getDocNum());
            map.put("warehId", vo.getDelivWarehId());
            map.put("dtlList", dtlList);
            return warehStkPgMapper.deleteByDtl(map);
        }
        return 0;
    }

    /**
     * 销售单可操作状态
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
        return sloMapper.selectByOnly(vo);
    }


    public List<OperationVo> creatButton(List<PurKeyValue> keyValues, PsoVo vo) {
        List<OperationVo> list = new ArrayList<>();
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
                    list.add(new OperationVo("导入销售合同", "T", importBill));
                } else {
                    list.add(new OperationVo("导入销售合同", "F", importBill));
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
                if ((vo.getProgress().equals("C") || vo.getProgress().equals("EK")) && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("审核", "T", examine));
                } else {
                    list.add(new OperationVo("审核", "F", examine));
                }
            }
            if (("uncheck").equals(pur.getOptionValue())) {
                if ((vo.getProgress().equals("RK") || vo.getProgress().equals("CK")) && vo.getSuspended().equals("F")
                        && vo.getCancelled().equals("F")) {
                    list.add(new OperationVo("反审", "T", reiterate));
                } else {
                    list.add(new OperationVo("反审", "F", reiterate));
                }
            }
            if (("suspend").equals(pur.getOptionValue())) {
                if (((vo.getVdeInvd().equals("F") && vo.getProgress().equals("DD")) || (vo.getVdeInvd().equals("T") && vo.getProgress().equals("RD")))
                        && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")) {
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
                        || vo.getProgress().equals("CK")) && vo.getSuspended().equals("F") && vo.getCancelled().equals("F")
                        && StringUtils.NullEmpty(vo.getItmdPsoNum()) && StringUtils.NullEmpty(vo.getExecPsoNum())) {
                    list.add(new OperationVo("作废", "T", toVoid));
                } else {
                    list.add(new OperationVo("作废", "F", toVoid));
                }
            }
        }

        return list;
    }

    //判断是否居间单据
    private boolean contract(String pscNum) {
        return slcService.contract(slcMapper.selectByOnly(new PscVo(pscNum)));
    }

    /**
     * 出入库明细查询
     *
     * @param vo
     * @return
     */
    @Override
    public PsoVo selectObject(PsoVo vo) {
        return sloMapper.selectObject(vo);
    }

    /**
     * 销售单开始发货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @return
     */
    @Override
    public int startDeliver(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) {
        //查询购销单数据
        PsoVo vo = sloMapper.selectObject(new PsoVo("", srcDocNum, srcDocUnitId + ""));
        if (vo != null) {
            if (vo.getProgress().equals("CK") && vo.getPsoKind().equals("T") && vo.getSuspended().equals("F")
                    && vo.getCancelled().equals("F")) {
                vo.setProgress("DG");
                //非居间合同调用
                if (this.contract(vo.getPscNum())) {
                    //调用销售合同开始发货
                    PscVo pscVo = slcMapper.selectByOnly(new PscVo(vo.getPscNum()));
                    slcService.startDeliver(pscVo.getUnitId(), pscVo.getSlcNum(),null,null,null);
                    //删除出库任务登记
                    warehDelivTaskService.delDelivTask(new WarehDelivTask(null, "SLO", Long.valueOf(vo.getVenderId() + ""), vo.getDocNum()),user);
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
     * 销售单终止发货
     *
     * @param srcDocUnitId 原始单据组织id
     * @param srcDocNum    原始单据编号
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int stopDeliver(Long srcDocUnitId,String srcDocNum,Long docUnitId,String docNum, SysUser user) throws Exception {
        //查询购销单数据
        PsoVo vo = sloMapper.selectObject(new PsoVo("", srcDocNum, srcDocUnitId + ""));
        if (vo != null) {
            if(vo.getProgress().equals("DG")&&vo.getSuspended().equals("F")){
                vo.setProgress("CK");
                //非居间合同调用
                if (this.contract(vo.getPscNum())) {
                    //调用销售合同终止发货
                    PscVo pscVo = slcMapper.selectByOnly(new PscVo(vo.getPscNum()));
                    slcService.stopDeliver(pscVo.getUnitId(), pscVo.getSlcNum(),null,null,user);
                    if(StringUtils.NullEmpty(vo.getStPsoNum())){
                        //登记出库任务
                        purchaseUtils.outStock("SLO", vo.getPsoNum(), user);
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
     * 销售单发货
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
        //查询原始单据数据 将发货信息同步
        PsoVo vo = sloMapper.getPsoByStb(srcDocUnitId, srcDocNum, docUnitId, docNum, true);
        //销售合同记录
        PscVo psc = slcMapper.selectByOnly(new PscVo(vo.getPscNum()));
        if (vo != null) {
            if(vo.getProgress().equals("DG")&&vo.getSuspended().equals("F")&&vo.getCancelled().equals("F")){
                vo.setProgress("DD");
                //通过sql语句查询更新明细
                List<PsoDtl> dtlList = null;
                if (vo.getPuoGen().equals("T")) {
                    dtlList = psoDtlMapper.selectByStbDtl(vo.getPsoNum(), docUnitId, docNum, true, "SLO");
                } else {
                    dtlList = psoDtlMapper.findByStbDtl(vo.getPsoNum(), docUnitId, docNum, true);
                }
                if (dtlList != null && dtlList.size() > 0) {
                    psoDtlMapper.updateDtlList(dtlList);
                }

                //判断是否监管往来
                if (purchaseService.judgePsc(vo.getPscNum())&& !psc.getVdrFsclUnitId().equals(psc.getVendeeId())) {
                    purchaseService.superviseBySales(psc, new SysUser(Long.valueOf(vo.getVenderId()), user.getPrsnl().getPrsnlId()));
                }

                //非居间单据
                if (slcService.contract(psc)) {
                    //撤销未决登记
                    Map<String, Object> map = new HashMap<>();
                    List<String> stkList = new ArrayList<>();
                    stkList.add("CM");
                    map.put("stkList", stkList);
                    this.cancel(vo, map, "");
                }

                //如果单据和实际发货存在差异（判断单据明细的订单数和发货数是否一致）
                List<CommonDtl> comList = psoDtlMapper.findByDiff(vo.getPsoNum(),"deliv_qty");
                if(!comList.isEmpty()){
                    slcService.postAdmeasure(psc,comList,true);
                }

                //调用销售合同发货
                slcService.deliver(vo.getVenderId(),vo.getSlcNum(),docUnitId,docNum,user);

                if(vo.getPuoGen().equals("T")){
                    //通知采购单发货事件
                    puoService.venderDelivered(psc,puoService.selectByOnly(new PsoVo(vo.getPsoNum())),docUnitId,docNum,user);
                }else{
                    vo.setTtlRcvQty(vo.getTtlDelivQty());
                    vo.setTtlRcvBox(vo.getTtlDelivBox());
                    vo.setTtlRcvMkv(vo.getTtlDelivMkv());
                    vo.setTtlRcvTax(vo.getTtlDelivTax());
                    vo.setTtlRcvVal(vo.getTtlDelivVal());
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
     * 销售单取消发货
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
        //查询原始单据数据 将发货信息同步
        PsoVo vo = sloMapper.getPsoByStb(srcDocUnitId, srcDocNum, docUnitId, docNum, false);
        //销售合同记录
        PscVo psc = slcMapper.selectByOnly(new PscVo(vo.getPscNum()));
        if (vo != null) {
            if( (vo.getProgress().equals("DD")||(vo.getProgress().equals("RD")&&StringUtils.isNotEmpty(vo.getEndPsoNum())))
                &&vo.getSuspended().equals("F")&&vo.getCancelled().equals("F")){

                //判断是否调用通知取消发货事件
                if(vo.getPuoGen().equals("T")){
                    puoService.venderReverseDelivered(psc,puoService.selectByOnly(new PsoVo(vo.getPsoNum())),docUnitId,docNum,user);
                }
                //修改进度
                vo.setProgress("DG");

                slcService.reverseDeliver(psc.getUnitId(),psc.getDocNum(),docUnitId,docNum,user);

                //如果单据和实际发货存在差异（判断单据明细的订单数和发货数是否一致）
                List<CommonDtl> comList = psoDtlMapper.findByDiff(vo.getPsoNum(),"deliv_qty");
                if(!comList.isEmpty()){
                    slcService.postAdmeasure(psc,comList,false);
                }
                //判断监管往来
                if (purchaseService.judgePsc(vo.getPscNum()) && psc.getMfzEnabled().equals("T")
                        && psc.getPsMfzSite().equals("AD") && !psc.getVdrFsclUnitId().equals(psc.getVendeeId())) {
                    //通过供应商id与采购商id查询是否有往来账户
                    Ca ca = caMapper.selectByCaUnitId(new Ca(Long.valueOf(vo.getVenderId()), Long.valueOf(vo.getVendeeId()), null, parameterMapper.findById("PS_CA_TYPE").getParmVal()));
                    if (ca != null) {
                        caAccService.freeze(new CaAcc(0L, 0L, "SLO", vo.getUnitId(), vo.getDocNum()), user, ca, new BigDecimal("0"), vo.getTtlVal(),true);
                    }
                }
                //非居间单据
                if (this.contract(vo.getPscNum())) {
                    //登记未决登记
                    Map<String, Object> map = new HashMap<>();
                    map.put("stkType", "CM");
                    map.put("docType", "SLO");
                    map.put("warehId", vo.getDelivWarehId());
                    map.put("unitId", vo.getVenderId());
                    map.put("docNum", vo.getDocNum());
                    map.put("dtlList", psoDtlMapper.findByDiffSql(vo.getPsoNum(),"qty"));
                    map.put("reversed", true);
                    warehStkPgMapper.updateByAdmeasure(map);
                }

                //通过sql语句查询更新明细
                List<PsoDtl> dtlList = null;
                if (vo.getPuoGen().equals("T")) {
                    dtlList = psoDtlMapper.selectByStbDtl(vo.getPsoNum(), docUnitId, docNum, false, "SLO");
                } else {
                    dtlList = psoDtlMapper.findByStbDtl(vo.getPsoNum(), docUnitId, docNum, false);
                }
                if (dtlList != null && dtlList.size() > 0) {
                    psoDtlMapper.updateDtlList(dtlList);
                }

                if(vo.getPuoGen().equals("T")){
                    vo.setTtlRcvQty(vo.getTtlDelivQty());
                    vo.setTtlRcvBox(vo.getTtlDelivBox());
                    vo.setTtlRcvMkv(vo.getTtlDelivMkv());
                    vo.setTtlRcvTax(vo.getTtlDelivTax());
                    vo.setTtlRcvVal(vo.getTtlDelivVal());
                }

            } else {
                throw new ServiceException(JsonResultCode.FAILURE, "原始单据状态异常！");
            }
        } else {
            throw new ServiceException(JsonResultCode.FAILURE, "原始单据数据异常！");
        }
        return sloMapper.updateByPuo(vo);
    }
}
