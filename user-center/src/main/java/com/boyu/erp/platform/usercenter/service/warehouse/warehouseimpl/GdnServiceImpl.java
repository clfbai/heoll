package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.TPOS.entity.DhOrdTask;
import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.Item;
import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.Package;
import com.boyu.erp.platform.usercenter.TPOS.model.DeliveryOrderConfirmModel;
import com.boyu.erp.platform.usercenter.TPOS.service.DhOrdTaskService;
import com.boyu.erp.platform.usercenter.TPOS.utils.JaxbUtil;
import com.boyu.erp.platform.usercenter.entity.Spec;
import com.boyu.erp.platform.usercenter.entity.basic.AbPf;
import com.boyu.erp.platform.usercenter.entity.basic.Vendee;
import com.boyu.erp.platform.usercenter.entity.goods.Color;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.entity.warehouse.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.GdnMapper;
import com.boyu.erp.platform.usercenter.model.batch.BatchModel;
import com.boyu.erp.platform.usercenter.model.batch.WarehBatchModel;
import com.boyu.erp.platform.usercenter.model.wareh.*;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.*;
import com.boyu.erp.platform.usercenter.service.warehouse.*;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.utils.warehouse.DeliveryDefineUtil;
import com.boyu.erp.platform.usercenter.utils.warehouse.TransferUtil;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.GdnModelVO;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbDtlVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import static java.math.BigDecimal.ONE;

/**
 * 出库单服务
 *
 * @author HHe
 * @date 2019/12/3 14:05
 */
@Slf4j
@Service
@Transactional
public class GdnServiceImpl implements GdnService {
    @Autowired
    private GdnMapper gdnMapper;
    @Autowired
    private StbService stbService;
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private DelivUtil delivUtil;
    @Autowired
    private WarehDmodeService warehDmodeService;
    @Autowired
    private StbDtlService stbDtlService;
    @Autowired
    private WarehService warehService;
    @Autowired
    private CostDmodeService costDmodeService;
    @Autowired
    private VendeeService vendeeService;
    @Autowired
    private TransferUtil transferUtil;
    @Autowired
    @Lazy
    private AsyncService asyncService;
    @Autowired
    private SysParameterService sysParameterService;
    @Autowired
    private CostGrpService costGrpService;
    @Autowired
    private StkCostService stkCostService;
    @Autowired
    private AbPfService abPfService;
    @Autowired
    private WarehStkService warehStkService;
    @Autowired
    private DelivDiffEvtService delivDiffEvtService;
    @Autowired
    private UnitBatchSerivce unitBatchSerivce;
    @Autowired
    private SysUgService sysUgService;
    @Autowired
    private SysUnitOwnerSerivcer sysUnitOwnerSerivcer;
    @Autowired
    private WarehUgDtlService warehUgDtlService;
    @Autowired
    private WarehUgService warehUgService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProdClsService prodClsService;
    @Autowired
    private SpecService specService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private WarehDelivTaskExternalCiteService warehDelivTaskExternalCiteService;
    @Autowired
    private GdnExternalCiteService gdnExternalCiteService;
    @Autowired
    private DhOrdTaskService dhOrdTaskService;
//    @Autowired
//    private GoodsPopupService goodsPopupService;

    /**
     * 根据出库方式初始化出库单
     *
     * @return
     * @throws ServiceException
     */
    @Transactional(readOnly = true)
    @Override
    public WarehDmodeModel initGdnByDelivMode(Long warehId, String delivMode) throws ServiceException {
        WarehDmode warehDmode = warehDmodeService.queryDmodeByWarehIdMode(warehId, delivMode);
        if (warehDmode == null) {
            throw new ServiceException("201", "出库方式信息有误！");
        }
        WarehDmodeModel warehDmodeModel = new WarehDmodeModel();
        BeanUtils.copyProperties(warehDmode, warehDmodeModel);
        return warehDmodeModel;
    }

    /**
     * 查询出库单列表
     *
     * @param
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<GdnModelVO> queryStbListByCon(GdnFilterModel gdnFilterModel, Integer page, Integer size, SysUser sysUser) throws InvocationTargetException, NoSuchMethodException, IntrospectionException, NoSuchFieldException, IllegalAccessException {
        gdnFilterModel = packQueryList(gdnFilterModel);
        //出库库存单集合
        PageHelper.startPage(page, size);
        List<GdnModelVO> list = gdnMapper.queryGdnStbListByFtr(gdnFilterModel);
        //封装vo
        list = packList(list);
        PageInfo<GdnModelVO> info = new PageInfo<>(list);
        return info;
    }

    /**
     * 封装查询列表前置
     *
     * @author HHe
     * @date 2019/11/26 20:48
     */
    private GdnFilterModel packQueryList(GdnFilterModel gdnFilterModel) {
        if (gdnFilterModel.getWarehNum() != null && !"".equals(gdnFilterModel.getWarehNum())) {
            gdnFilterModel.setWarehIdList(sysUnitOwnerSerivcer.getNumAndOwner2Ids(gdnFilterModel.getWarehNum(), gdnFilterModel.getsUnitId(), DeliveryDefineUtil.WAREH_TYPE));
        }
        //收货方Id需要查询SYS_UNIT_CLSF表传组织Id和供应商code：VE查询出供应商Id
        if (gdnFilterModel.getRcvUnitNum() != null && !"".equals(gdnFilterModel.getRcvUnitNum())) {
            gdnFilterModel.setRcvUnitIdList(sysUnitOwnerSerivcer.getNumAndOwner2Ids(gdnFilterModel.getRcvUnitNum(), gdnFilterModel.getsUnitId(), DeliveryDefineUtil.VENDOR_TYPE));
        }
        //收货方仓库查询直接根据num查询出unitId集合
        if (gdnFilterModel.getRcvWarehNum() != null && !"".equals(gdnFilterModel.getRcvWarehNum())) {
            gdnFilterModel.setRcvWarehIdList(sysUnitOwnerSerivcer.getNum2Ids(gdnFilterModel.getRcvWarehNum()));
        }
        /*
        分组查询功能：
        1、分组默认为空白，如果为空白，不查询分组信息查询当前信息；
        2、选择了组织分组则加载组织分组的全部，若选择了仓库分组则加载仓库分组的全部
         */
        if (gdnFilterModel.getUnitUgIds() != null && gdnFilterModel.getUnitUgIds().size() > 0) {
            List<Long> warehIds = new ArrayList<>();
            //查询组织分组的组织集合
            if (gdnFilterModel.getUnitUgIds() != null && gdnFilterModel.getUnitUgIds().size() > 0) {
                if (gdnFilterModel.getWarehUgIds() != null && gdnFilterModel.getWarehUgIds().size() > 0) {
                    warehIds = warehUgDtlService.queryWarehIdsByWarehUgIds(gdnFilterModel.getWarehUgIds());
                } else {
                    //查询组织分组对应的仓库分组
                    List<Long> warehUgIds = warehUgService.queryWarehUgIdsBySysUgId(gdnFilterModel.getUnitUgIds());
                    //根据查询出来的所有仓库分组查询组织分组
                    warehIds = warehUgDtlService.queryWarehIdsByWarehUgIds(warehUgIds);
                }
            }
            if (warehIds.size() <= 0) {
                warehIds.add(-1L);
            }
            gdnFilterModel.setUgWarehIds(warehIds);
        }
        return gdnFilterModel;
    }

    /**
     * 封装出库单集合
     *
     * @author HHe
     * @date 2019/11/12 11:52
     */
    private List<GdnModelVO> packList(List<GdnModelVO> list) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Map<String, Object> map = new HashMap<>();
        map.put("sys_prsnl", new String[]{"oprId", "ftchrId", "dlvrId", "cngnrId"});
        map.put("sys_unit", new String[]{"warehId", "fsclUnitId", "tranUnitId", "rcvUnitId", "rcvWarehId", "rcvFsclUnitId", "endUnitId", "endWarehId"});
        Map<String, String> uMap = new HashMap<>();
        uMap.put("fsclUnitId", "fsclUnitCode-unitCode");
        uMap.put("rcvFsclUnitId", "rcvFsclUnitCode-unitCode");
        map.put("unitProp", uMap);
        list = delivUtil.loadCodeName2VO2(map, list);
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("locAdopted", "BOOLEA");
        codeMap.put("brdgMode", "BRDG_MODE");
        codeMap.put("bxiEnabled", "BOOLEA");
        codeMap.put("boxReqd", "BOOLEA");
        codeMap.put("effective", "BOOLEA");
        codeMap.put("suspended", "BOOLEA");
        codeMap.put("cancelled", "BOOLEA");
        codeMap.put("delivMode", "DELIV_MODE");
        codeMap.put("fsclDelivMode", "DELIV_MODE");
        codeMap.put("delivMthd", "DELIV_MTHD");
        codeMap.put("fsclDateAptd", "BOOLEA");
        codeMap.put("pickReqd", "BOOLEA");
        codeMap.put("rckReqd", "BOOLEA");
        codeMap.put("boxSchd", "BOOLEA");
        codeMap.put("instStl", "BOOLEA");
        codeMap.put("vehReqd", "BOOLEA");
        codeMap.put("cnsnReqd", "BOOLEA");
        codeMap.put("progress", "GDN_PROG");
        codeMap.put("uidAdopted", "BOOLEA");
        list = delivUtil.loadCPByCodeDtl(codeMap, list);
        return list;
    }




    /**
     * 出库单下拉
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> gdnPullDown(Long sUnitId, SysUser sysUser) {
        Map<String, String> map = new LinkedHashMap<>();
        //进度
        map.put("progress", "GDN_PROG");
        //送货方式
        map.put("delivMthd", "DELIV_MTHD");
        //桥接方式
//        map.put("brdgMode", "BRDG_MODE");
        //出库方式
        map.put("delivMode", "DELIV_MODE");
        //会计出库方式
//        map.put("fsclDelivMode", "DELIV_MODE");
        Map<String, Object> codeDtlMap = delivUtil.getCodeDtlMap1(map);
        String[] strings = {"fsclDateAptd", "instStl", "vehReqd", "cnsnReqd", "bxiEnabled", "boxReqd", "pickReqd", "rckReqd", "boxSchd"};
        codeDtlMap = delivUtil.getCodeDtlMap4Boolea(codeDtlMap, strings);
        //原始单据类型
        codeDtlMap.put("srcDocType", sysCodeDtlService.getGdnSrcDocType());
        if (sUnitId == null) {
            sUnitId = sysUser.getDomain().getUnitId();
        }
        codeDtlMap.put("sysUgIds", sysUgService.getSysUgMapByTypeOwner("WD", sUnitId));
        codeDtlMap.put("dateType", Arrays.asList(new OptionVo[]{new OptionVo("业务", DeliveryDefineUtil.fie.doc.toString()), new OptionVo("会计", DeliveryDefineUtil.fie.fscl.toString())}));
        return codeDtlMap;
    }

    /**
     * 删除出库单
     *
     * @param gdnModel
     * @param sysUser
     * @return
     */
    @Override
    public int delGdn(GdnModel gdnModel, SysUser sysUser) {
        int s;
        GdnStbModel gdnStbModel = judgeHaveProp(gdnModel);
        //判断状态
        if (gdnStbModel.getLdgStbNum() != null || !DelivUtil.equalsHave(gdnStbModel.getProgress(), "PG", "CN") || !"F".equals(gdnStbModel.getSuspended()) || !"F".equals(gdnStbModel.getCancelled())) {
            throw new ServiceException("201", "出库单【" + gdnModel.getStbNum() + "】不允许被删除");
        }
        Gdn gdn = new Gdn();
        Stb stb = new Stb();
        BeanUtils.copyProperties(gdnStbModel, gdn);
        BeanUtils.copyProperties(gdnStbModel, stb);
        //删除出库单
        s = gdnMapper.deleteGdn(gdn);
        //删除库存单
        s += stbService.delStb(stb);
        //删除明细
        StbDtl stbDtl = new StbDtl();
        stbDtl.setUnitId(gdnStbModel.getUnitId());
        stbDtl.setStbNum(gdnStbModel.getStbNum());
        s += stbDtlService.delByNumAndId(stbDtl);
        return s;
    }



    /**
     * 出库单页面添加出库单;
     *
     * @author HHe
     */
    @Override
    public String pageAddGdn(GdnModel gdnModelP, SysUser sysUser) {
        String num="";
        //判定能否添加
        addGdnFlowCon(gdnModelP);
        GdnModel gdnModel = packAddUpdMess(gdnModelP);
        gdnModel.setWarehId(gdnModelP.getWarehId());
        gdnModel.setDelivMode(gdnModelP.getDelivMode());
        gdnModel.setGdnNum(null);
        gdnModel.setStbNum(null);
        //组织Id
        gdnModel.setUnitId(gdnModelP.getsUnitId());
        //货位管理
        gdnModel.setLocAdopted(gdnModelP.getLocAdopted());
        //预期总数量
        gdnModel.setTtlExpdQty(BigDecimal.ZERO);
        //总数量
        gdnModel.setTtlQty(BigDecimal.ZERO);
        //总金额
        gdnModel.setTtlVal(BigDecimal.ZERO);
        //总税款
        gdnModel.setTtlTax(BigDecimal.ZERO);
        //桥接方式
        gdnModel.setBrdgMode("D");
        //判断是否改变成本
        CostDmode costDmode = costDmodeService.getCostDmodeByUnitMode(gdnModel.getUnitId(), gdnModelP.getDelivMode());
        if(costDmode==null){
            gdnModel.setCostChg("F");
        }else{
            gdnModel.setCostChg("T");
        }
        try {
            num = gdnExternalCiteService.addGdnAndDtl(gdnModel, sysUser);
        } catch (Exception e) {
            throw new ServiceException("201", "请检查输入重新添加");
        }
        return num;
    }
    /**
     * 修改出库单
     *
     * @param
     * @param sysUser
     * @return
     */
    @Override
    public int updateGdn(GdnModel gdnModelP, SysUser sysUser) {
        //判定
        addGdnFlowCon(gdnModelP);
        //查询能否修改
        GdnStbModel gdnStbModel = judgeHaveProp(gdnModelP);
        if (!"PG".equals(gdnStbModel.getProgress().toUpperCase()) || !"F".equals(gdnStbModel.getSuspended()) || !"F".equals(gdnStbModel.getCancelled())) {
            throw new ServiceException(JsonResultCode.FAILURE, "出库单状态不可修改");
        }
        GdnModel gdnModel = packAddUpdMess(gdnModelP);
        gdnModel.setUnitId(gdnStbModel.getUnitId());
        gdnModel.setGdnNum(gdnStbModel.getGdnNum());
        gdnModel.setStbNum(gdnStbModel.getStbNum());
        gdnModel.setOprId(sysUser.getPrsnl().getPrsnlId());
        gdnModel.setOpTime(new Date());
        gdnModel.setProgress(null);
        //修改库存单
        Gdn gdn = new Gdn();
        Stb stb = new Stb();
        BeanUtils.copyProperties(gdnModel, gdn);
        BeanUtils.copyProperties(gdnModel, stb);
        int i = 0;
        try {
            i += stbService.updateStbByNumAndId(stb);
            i += gdnMapper.updateGdn(gdn);
        } catch (Exception e) {
            throw new ServiceException(JsonResultCode.FAILURE, "输入信息不完整，请检查后重试");
        }
        return i;
    }

    /**
     * 页面添加出库单流程控制
     *
     * @author HHe
     */
    private void addGdnFlowCon(GdnModel gdnModel) {
        //其他出库方式出库单无需指定收货方收货仓库
        boolean otherDelivMode =!DeliveryDefineUtil.DELIV_MODE.OTHR.toString().equals(gdnModel.getDelivMode());
        //判断仓库编号
        if (gdnModel.getWarehId() == null) {
            throw new ServiceException("201", "仓库编号不可为空");
        }
        if (StringUtils.NullEmpty(gdnModel.getDelivMode())) {
            throw new ServiceException("201", "请选择出库方式");
        }
        //会计日期
        if(DeliveryDefineUtil.BOOLEAN_STR_T.equals(gdnModel.getFsclDateAptd())){
            if(gdnModel.getFsclDate()==null){
                throw new ServiceException("201", "请选择会计日期");
            }
        }
        //根据出库方式和仓库查询是否需要指定收货方和收货方仓库
        WarehDmode warehDmode = warehDmodeService.queryAppointrcvMess(gdnModel.getWarehId(), gdnModel.getDelivMode());
        if(warehDmode==null){
            throw new ServiceException("201", "仓库没有出库方式");
        }
        if ("T".equals(warehDmode.getRcvUnitReqd())&&otherDelivMode) {
            if (gdnModel.getRcvUnitId() == null) {
                throw new ServiceException("201", "收货方不可为空");
            }
        }
        if ("T".equals(warehDmode.getRcvWarehReqd())&&otherDelivMode) {
            if (gdnModel.getRcvWarehId() == null) {
                throw new ServiceException("201", "收获方仓库不可为空");
            }
        }
        //除了其他、内购销售/差异补单都需要原始单据
        if (!"OTHR".equals(gdnModel.getDelivMode()) && !"DISL".equals(gdnModel.getDelivMode())) {
            if (StringUtils.NullEmpty(gdnModel.getSrcDocType())) {
                throw new ServiceException("201", "原始单据类别不可为空");
            }
            if (StringUtils.NullEmpty(gdnModel.getSrcDocNum())) {
                throw new ServiceException("201", "原始单据编号不可为空");
            }
        }
        //判断原始单据类别是否对应出库方式
//        int n = warehDmodeservice.judgeModeMapDocType(gdnListVO.getDelivMode(), gdnListVO.getSrcDocType());
//        if (n == 0) {
//            throw new ServiceException("201", "单据类别不是有效状态");
//        }
    }

    /**
     * 根据仓库Id加载出库方式和仓库信息
     *
     * @author HHe
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> loadDelivModeByWarehCode(Long warehId) {
        Map<String, Object> map = new HashMap<>();
        //出库方式
//        List<Map<String, String>> delivMode = warehDmodeService.loadDelivModeByWarehCode(warehId);
        map.put("delivMode", Arrays.asList(new OptionVo("其他","OTHR")));
        //仓库名称
        Wareh wareh = warehService.queryWarehByWarehId(warehId);
        if (wareh == null) {
            throw new ServiceException("201", "该仓库不可选，请重选仓库");
        }
        Set<Long> unitIds = new HashSet<>();
        unitIds.add(warehId);
        unitIds.add(wareh.getFsclUnitId());
        List<SysUnit> sysUnits = sysUnitService.queryUnitByIds(unitIds);
        for (SysUnit sysUnit : sysUnits) {
            if (warehId.equals(sysUnit.getUnitId())) {
                map.put("warehName", sysUnit.getUnitName());
            }
            if (sysUnit.getUnitId().equals(wareh.getFsclUnitId())) {
                map.put("fsclUnitCode", sysUnit.getUnitCode());
                map.put("fsclUnitName", sysUnit.getUnitName());
            }
        }
        return map;
    }

    /**
     * 根据库存单编号查询库存明细集合
     *
     * @author HHe
     * @date 2019/8/24 10:27
     */
    @Override
    @Transactional(readOnly = true)
    public List<StbDtlVo> queryStbDtlList(GdnFilterModel gdnFilterModel, SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        //判断是不是总库存单，总库存单明细需要,查询子库存单的明细集合展示
        List<String> sonStbNums = stbService.queryStbIsTotal(gdnFilterModel.getStbNum(), gdnFilterModel.getUnitId());
        List<StbDtlVo> stbDtlVos;
        if (sonStbNums.size() > 0) {
            //总库存单,查询所有子库存单中的明细
            stbDtlVos = stbDtlService.queryTotStbDtlsBySonStbNums(sonStbNums, gdnFilterModel.getUnitId());
        } else {
            stbDtlVos = stbDtlService.queryStbDtlsByStbNums(gdnFilterModel.getStbNum(), gdnFilterModel.getUnitId());
        }
        return stbDtlVos;
    }


    /**
     * 出库单功能列表展示
     *
     * @author HHe
     * @date 2019/10/15 15:21
     */
    @Override
    public List<OperationVo> queryFunList(GdnModel gdnModel) {
        String url = "/warehouse/gdn/gdnfunction";
        GdnStbModel gdnStbModel = judgeHaveProp(gdnModel);
        return delivUtil.judgeFunction(url, "GDN", gdnStbModel.getCancelled(), gdnStbModel.getSuspended(), gdnStbModel.getProgress());
    }

    /**
     * Assist
     * 判断出库单存不存在
     *
     * @author HHe
     * @date 2019/10/15 17:39
     */
    private GdnStbModel judgeHaveProp(GdnModel gdnModel) {
        if(StringUtils.NullEmpty(gdnModel.getGdnNum())&&StringUtils.isNotEmpty(gdnModel.getStbNum())){
            gdnModel.setGdnNum(gdnModel.getStbNum());
        }
        GdnStbModel gdnStbModel = gdnMapper.queryGdnStbByNumAndUnit(gdnModel.getGdnNum(), gdnModel.getUnitId());
        if (gdnStbModel == null) {
            throw new ServiceException("201", "单据信息异常");
        }
        return gdnStbModel;
    }

    /**
     * 确认
     *
     * @author HHe
     * @date 2019/10/15 17:28
     */
    @Override
    public int confirm(GdnModel gdnModel, SysUser sysUser) {
        //查询出库单是否存在
        GdnStbModel gdnStbModel = judgeHaveProp(gdnModel);
        if (!"PG".equals(gdnStbModel.getProgress()) || !"F".equals(gdnStbModel.getSuspended()) || !"F".equals(gdnStbModel.getCancelled()) || StringUtils.isNotEmpty(gdnStbModel.getLdgStbNum())) {
            throw new ServiceException("201", "出库单状态不可确认");
        }
        Long unitId = gdnStbModel.getUnitId();
        String gdnNum = gdnStbModel.getGdnNum();
        //修改总单状态同时修改子单状态
        Gdn gdn = new Gdn();
        gdn.setGdnNum(gdnNum);
        gdn.setUnitId(unitId);
        gdn.setProgress("CN");
        //多条出库单暂未开发；暂留

        return gdnMapper.updateGdn(gdn);
    }

    /**
     * 重做
     *
     * @author HHe
     * @date 2019/10/15 17:50
     */
    @Override
    public int redo(GdnModel gdnModel, SysUser sysUser) {
        GdnStbModel gdnStbModel = judgeHaveProp(gdnModel);
        if (!"CN".equals(gdnStbModel.getProgress()) || !"F".equals(gdnStbModel.getSuspended()) || !"F".equals(gdnStbModel.getCancelled()) || StringUtils.isNotEmpty(gdnStbModel.getLdgStbNum())) {
            throw new ServiceException("201", "出库单状态不可重做");
        }
        Gdn gdn = new Gdn();
        gdn.setGdnNum(gdnStbModel.getGdnNum());
        gdn.setUnitId(gdnStbModel.getUnitId());
        gdn.setProgress("PG");
        return gdnMapper.updateGdn(gdn);
    }

    /**
     * 挂起
     *
     * @author HHe
     * @date 2019/10/15 17:50
     */
    @Override
    public int suspend(GdnModel gdnModel, SysUser sysUser) {
        GdnStbModel gdnStbModel = judgeHaveProp(gdnModel);
        boolean b = (!"CN".equals(gdnStbModel.getProgress()) && !"PG".equals(gdnStbModel.getProgress())) || !"F".equals(gdnStbModel.getSuspended()) || !"F".equals(gdnStbModel.getCancelled()) || StringUtils.isNotEmpty(gdnStbModel.getLdgStbNum());
        if (b) {
            throw new ServiceException("201", "出库单状态不可挂起");
        }
        Stb stb = new Stb();
        stb.setStbNum(gdnStbModel.getStbNum());
        stb.setUnitId(gdnStbModel.getUnitId());
        stb.setSuspended("T");
        return stbService.updateStbByNumAndId(stb);
    }

    /**
     * 恢复
     *
     * @author HHe
     * @date 2019/10/15 17:50
     */
    @Override
    public int resume(GdnModel gdnModel, SysUser sysUser) {
        GdnStbModel gdnStbModel = judgeHaveProp(gdnModel);
        if (!"T".equals(gdnStbModel.getSuspended()) || !"F".equals(gdnStbModel.getCancelled()) || StringUtils.isNotEmpty(gdnStbModel.getLdgStbNum())) {
            throw new ServiceException("201", "出库单状态不可恢复");
        }
        Stb stb = new Stb();
        stb.setStbNum(gdnStbModel.getStbNum());
        stb.setUnitId(gdnStbModel.getUnitId());
        stb.setSuspended("F");
        return stbService.updateStbByNumAndId(stb);
    }

    /**
     * 作废
     *
     * @author HHe
     * @date 2019/10/15 17:50
     */
    @Override
    public int abolish(GdnModel gdnModel, SysUser sysUser) {
        GdnStbModel gdnStbModel = judgeHaveProp(gdnModel);
        //判断状态
        boolean b = !DelivUtil.equalsHave(gdnStbModel.getProgress(), "PG", "CN") || !"F".equals(gdnStbModel.getSuspended()) || !"F".equals(gdnStbModel.getCancelled()) || (gdnStbModel.getLdgStbNum() != null || "".equals(gdnStbModel.getLdgStbNum()));
        if (b) {
            throw new ServiceException("201", "出库单状态不可作废");
        }
        //修改状态
        Stb stb = new Stb();
        stb.setStbNum(gdnStbModel.getStbNum());
        stb.setUnitId(gdnStbModel.getUnitId());
        stb.setCancelled("T");
        //更新出库任务执行次数
        WarehDelivTask warehDelivTask = new WarehDelivTask();
        warehDelivTask.setTaskDocUnitId(gdnStbModel.getSrcDocUnitId());
        warehDelivTask.setTaskDocNum(gdnStbModel.getSrcDocNum());
        warehDelivTask.setTaskDocType(gdnStbModel.getSrcDocType());
        warehDelivTask.setUnitId(gdnStbModel.getUnitId());
        warehDelivTaskExternalCiteService.renewalTaskImplTimes(warehDelivTask);
        //通知wz取消发货
        try {
            transferUtil.sendTo(DeliveryDefineUtil.STOP_DELIVER, gdnStbModel.getSrcDocType(), gdnStbModel.getSrcDocUnitId(), gdnStbModel.getSrcDocNum(), gdnStbModel.getUnitId(), gdnStbModel.getGdnNum(), sysUser);
        } catch (Exception e) {
            throw new ServiceException("201", "通知原单发货异常");
        }
        return stbService.updateStbByNumAndId(stb);
    }

    /**
     * 出库
     *
     * @author HHe
     * @date 2019/10/15 17:50
     */
    @Override
    public int deliver(GdnModel gdnModel, SysUser sysUser) throws Exception {
        int i = 0;
        GdnStbModel gdnStbModel = judgeHaveProp(gdnModel);
        //状态拦截
        if (!"CN".equals(gdnStbModel.getProgress().toUpperCase()) || !"F".equals(gdnStbModel.getSuspended()) || !"F".equals(gdnStbModel.getCancelled())) {
            throw new ServiceException("201", "出库单状态不可出库");
        }
        Date nowDate = new Date();
        //设置会计日期
        if ("F".equals(gdnStbModel.getFsclDateAptd())) {
            gdnStbModel.setFsclDate(nowDate);
        }
        //查询成本组和会计组织
        Wareh wareh = warehService.querywarehByUnitId(gdnStbModel.getWarehId(), gdnStbModel.getUnitId());
        if (wareh == null) {
            throw new ServiceException("201", "仓库信息不存在");
        }
        gdnStbModel.setCostGrpId(wareh.getCostGrpId());
        gdnStbModel.setFsclUnitId(wareh.getFsclUnitId());
        //设置收货方会计组织Id
        if (gdnStbModel.getRcvWarehId() == null) {
            gdnStbModel.setRcvFsclUnitId(gdnStbModel.getRcvUnitId());
        } else {
            Wareh rcvWareh = warehService.querywarehByUnitId(gdnStbModel.getRcvWarehId(), gdnStbModel.getRcvUnitId());
            gdnStbModel.setRcvFsclUnitId(rcvWareh.getFsclUnitId());
        }
        //设置会计组织出库方式
        //出库方式
        String delivModeCode = gdnStbModel.getDelivMode();
        //会计组织出库方式
        gdnStbModel.setFsclDelivMode(getFsclDelivMode(gdnStbModel.getDelivMode(), gdnStbModel.getRcvFsclUnitId(), gdnStbModel.getFsclUnitId()));
        //单据为已生效单据
        gdnStbModel.setEffective("T");
        //设置挂账仓库Id
        gdnStbModel.setHldnWarehId(getHldnWarehId(gdnStbModel.getWarehId(), gdnStbModel.getFsclUnitId(), gdnStbModel.getRcvWarehId()));
        //登记挂账仓库成本组Id
        CostGrp hldnCostGrp = costGrpService.queryCostGrpByUnitId(gdnStbModel.getHldnWarehId());
        gdnStbModel.setHldnCostGrpId(hldnCostGrp.getCostGrpId());
        //判断总单处理,处理其中步骤后续跳过
        if (judgeTotDoc(gdnStbModel.getStbNum(), gdnStbModel.getUnitId())) {

            //todo 总单操作
        }
        //判断出库方式差异
        judgeDiffCtrl(gdnStbModel.getWarehId(), delivModeCode, gdnStbModel.getTtlQty(), gdnStbModel.getTtlExpdQty());

        //获取明细集合
        List<StbDtl> stbDtls = stbDtlService.queryStbDtlsByNumAndUnit(gdnStbModel.getStbNum(), gdnStbModel.getUnitId());
        //核算成本开关
        boolean costB = false;
        //判断负库存开关
        boolean negStkB = false;
        //登记差异开关
        boolean diffB = false;
        //判断批次开关
        boolean batcheB = false;
        Set<Long> prodIds = new HashSet<>();
        //判断负库存；
        List<WarehStk> warehProdStkList = new ArrayList<>();
        if ("F".equals(wareh.getNegStk())) {
            if (stbDtls != null && stbDtls.size() > 0) {
                //遍历出商品到仓库库存中查询出数量进行比对

                stbDtls.forEach(s -> prodIds.add(s.getProdId()));
                //查询对应仓库数量进行比对
                warehProdStkList = warehStkService.queryWarehStkByProdsAndWareh(prodIds, gdnStbModel.getWarehId());
                negStkB = true;
            }
        }
        //判断账套和会计日期合法性
        AbPf abPf = abPfService.precheck(gdnStbModel.getFsclDate(), gdnStbModel.getFsclUnitId());
        List<CountCostModel> countCostModels = new ArrayList<>();
        if (abPf != null && "T".equals(wareh.getStkAdopted())) {
            Boolean b;
            //核算成本
            if (gdnStbModel.getHldnCostGrpId() != null && gdnStbModel.getCostGrpId().equals(gdnStbModel.getHldnCostGrpId())) {
                b = false;
            } else if (gdnStbModel.getHldnCostGrpId() != null && !gdnStbModel.getCostGrpId().equals(gdnStbModel.getHldnCostGrpId())) {
                b = true;
            } else {
                //不符合以上判断则判断出库方式是否是影响成本出库方式
                CostDmode costDmo = costDmodeService.getCostDmodeByUnitMode(gdnStbModel.getUnitId(), gdnStbModel.getDelivMode());
                if (costDmo == null) {
                    b = false;
                } else {
                    b = true;
                }
            }
            if (stbDtls != null && stbDtls.size() > 0) {
                StkCostModel stkCostModel = new StkCostModel();
                stkCostModel.setCostChanged(b);
                stkCostModel.setReversed(true);
                stkCostModel.setDrType(gdnStbModel.getDrType());
                stkCostModel.setFsclUnitId(gdnStbModel.getFsclUnitId());
                stkCostModel.setMode(gdnStbModel.getDelivMode());
                stkCostModel.setWarehId(gdnStbModel.getWarehId());
                stkCostModel.setStbDtls(stbDtls);
                stkCostModel.setCostGrpId(gdnStbModel.getCostGrpId());
                //仓库和组织方数据
                //todo 核算成本
                countCostModels = stkCostService.instantCalculate(stkCostModel);
                costB = true;
            }
        }
        List<UnitBatch> unitBatches = new ArrayList<>();
        //判断批次
        if (prodIds != null && prodIds.size() > 0) {
            //查询批次
            unitBatches = unitBatchSerivce.queryBatchsByProdIdsAndUnit(prodIds, gdnStbModel.getUnitId());
            batcheB = true;
        }
        //总成本
        //库存数量
//        List<WarehStk> warehStkList = new ArrayList<>();
        BigDecimal totCost = new BigDecimal("0");
        for (StbDtl stbDtl : stbDtls) {
            //负库存
            if (negStkB) {
                for (WarehStk warehStk : warehProdStkList) {
                    if (stbDtl.getProdId().equals(warehStk.getProdId()) & warehStk.getStkOnHand().subtract(stbDtl.getQty()).signum() == -1) {
                        Product product = productService.queryProductById(stbDtl.getProdId());
                        throw new ServiceException("201", "商品【" + product.getProdCode() + "】库存不足");
                    }
                }
            }
            //成本
            if (costB) {
                for (CountCostModel countCostModel : countCostModels) {
                    if (countCostModel.getProdId().equals(stbDtl.getProdId())) {
                        stbDtl.setUnitCost(countCostModel.getUnitCost());
                        stbDtl.setCost(countCostModel.getUnitCost().multiply(stbDtl.getQty()));
                        totCost = totCost.add(countCostModel.getUnitCost().multiply(stbDtl.getQty()));
                        //TODO 仓库成本出库单未修改字段。命名：单位成本2
                        break;
                    }
                }
            }
            if (batcheB) {
                for (UnitBatch unitBatch : unitBatches) {
                    if (unitBatch.getProdId().equals(stbDtl.getProdId())) {
                        boolean b = new BigDecimal(unitBatch.getSurplusQuantity()).subtract(stbDtl.getQty()).signum() < 0 ? true : false;
                        if (b) {
                            Product product = productService.queryProductById(stbDtl.getProdId());
                            throw new ServiceException(JsonResultCode.FAILURE, "商品【" + product.getProdCode() + "】批次数量不足");
                        }
                    }
                }
            }
            //登记出库差异
            if (!diffB && !stbDtl.getExpdQty().equals(stbDtl.getQty())) {
                //比较额度，登记差异
                //TODO 调用查询额度接口判断是否超额，超额报错
                diffB = true;
            }
        }
        //设置出库单总成本
        gdnStbModel.setTtlCost(totCost);
        if (stbDtls != null && stbDtls.size() > 0) {
            //添加明细成本字段
            stbDtlService.updateStbDtlCostList(stbDtls);
        }
        if (diffB) {
            //登记出库差异事件
            delivDiffEvtService.register(gdnStbModel.getUnitId(), gdnStbModel.getGdnNum());
        }
        //全部成功修改出库单信息
        Gdn gdn = new Gdn();
        Stb stb = new Stb();
        BeanUtils.copyProperties(gdnStbModel, gdn);
        BeanUtils.copyProperties(gdnStbModel, stb);
        gdn.setProgress("DD");
        gdnMapper.updateGdn(gdn);
        stbService.updateStbByNumAndId(stb);
        //todo 添加批次
        //只有作为零售单据时，才需要添加批次信息，采购商为-1
        if ("RLB".equals(gdnStbModel.getSrcDocType())) {
            List<BatchModel> batchModels = unitBatchSerivce.createBatch(new WarehBatchModel(-1L, gdnStbModel.getUnitId(), true, "D", stbDtls));
            if (CollectionUtils.isNotEmpty(batchModels)) {
                for (BatchModel s : batchModels) {
                    log.info("批次信息" + s);
                    s.getUnitBatch().setDocNum(gdn.getGdnNum());
                    unitBatchSerivce.addUnitBatch(s, "D", sysUser);
                }
            }
        }
        //todo 通知原单已发货
//        try {
            transferUtil.sendTo(DeliveryDefineUtil.DELIVER, gdnStbModel.getSrcDocType(), gdnStbModel.getSrcDocUnitId(), gdnStbModel.getSrcDocNum(), gdnStbModel.getUnitId(), gdnStbModel.getGdnNum(), sysUser);
//        } catch (Exception e) {
//            throw new ServiceException("201", "通知原单出库单状态异常");
//        }
        //调用异步处理出库后续流程
        asyncService.gdnDeliver(countCostModels, gdnStbModel.getWarehId(), gdnStbModel.getFsclUnitId());
        return i;
    }

    /**
     * Assist
     * 根据出库方式判断差异
     *
     * @author HHe
     * @date 2019/11/21 17:57
     */
    private void judgeDiffCtrl(Long warehId, String delivModeCode, BigDecimal ttlQty, BigDecimal ttlExpdQty) {
        //根据出库方式判断差异
        WarehDmode warehDmode = warehDmodeService.queryDmodeByWarehIdMode(warehId, delivModeCode);
        if ("EQ".equals(warehDmode.getDiffCtrl())) {
            //等于
            if (!ttlQty.equals(ttlExpdQty)) {
                throw new ServiceException("201", "实际数量不等于预期数量");
            }
        } else if ("NG".equals(warehDmode.getDiffCtrl())) {
            //不大于;
            if (ttlQty.compareTo(ttlExpdQty) == 1) {
                throw new ServiceException("201", "实际数量大于预期数量");
            }
        } else if ("NL".equals(warehDmode.getDiffCtrl())) {
            //不小于；
            if (ttlQty.compareTo(ttlExpdQty) == -1) {
                throw new ServiceException("201", "实际数量小于预期数量");
            }
        }
    }

    /**
     * Assist
     * 判断封装挂账仓库
     *
     * @author HHe
     * @date 2019/11/21 17:44
     */
    private Long getHldnWarehId(Long warehId, Long fsclUnitId, Long rcvWarehId) {
        Long hldnWarehId;
        SysParameter transferStockHolderP = sysParameterService.findByParameter(DeliveryDefineUtil.TRANSFER_STOCK_HOLDER);
        if (transferStockHolderP == null) {
            hldnWarehId = fsclUnitId;
        } else {
            String hldnParmVal = transferStockHolderP.getParmVal();
            if ("D".equals(hldnParmVal)) {
                hldnWarehId = warehId;
            } else if ("R".equals(hldnParmVal)) {
                hldnWarehId = rcvWarehId != null ? rcvWarehId : fsclUnitId;
            } else {
                hldnWarehId = fsclUnitId;
            }
        }
        return hldnWarehId;
    }

    /**
     * Assist
     * 判断封装会计组织出库方式
     *
     * @author HHe
     * @date 2019/11/21 17:16
     */
    private String getFsclDelivMode(String delivModeCode, Long rcvFsclUnitId, Long fsclUnitId) {
        String fsclDelivMode = "";
        if (DelivUtil.equalsHave(delivModeCode, DeliveryDefineUtil.SELL, DeliveryDefineUtil.PURCHASE_RETURN, DeliveryDefineUtil.DIRECT_SELL, DeliveryDefineUtil.DIRECT_PURCHASE_RETURN) && fsclUnitId.equals(rcvFsclUnitId)) {
            fsclDelivMode = DeliveryDefineUtil.TRANSFER;
        } else if (DelivUtil.equalsHave(delivModeCode, "ITOD", "ITRT", "TRAN") && !fsclUnitId.equals(rcvFsclUnitId)) {
            //TODO
            fsclDelivMode = judgePeding(fsclUnitId, rcvFsclUnitId);
        } else if ("RETL".equals(delivModeCode) && rcvFsclUnitId != null && !fsclUnitId.equals(rcvFsclUnitId)) {
            fsclDelivMode = "SELL";
        } else if (DelivUtil.equalsHave(delivModeCode, "CMSL", "CDPR")) {
            fsclDelivMode = "TRAN";
        } else if ("DIPR".equals(delivModeCode)) {
            fsclDelivMode = "PURT";
        } else if ("DISL".equals(delivModeCode)) {
            fsclDelivMode = "SELL";
        } else if (DelivUtil.equalsHave(delivModeCode, "ITOD", "ITRT")) {
            fsclDelivMode = "TRAN";
        } else {
            fsclDelivMode = delivModeCode;
        }
        return fsclDelivMode;
    }

    /**
     * Assist
     * 会计出库方式待定判断
     *
     * @author HHe
     * @date 2019/10/19 10:50
     */
    private String judgePeding(Long fsclUnitId, Long rcvFsclUnitId) {
        //判断收货方会计组织Id是发货方会计组织ID的采购商
        Vendee pVendee = new Vendee();
        pVendee.setOwnerId(fsclUnitId);
        pVendee.setVendeeId(rcvFsclUnitId);
        Vendee vendee = vendeeService.queryVendeeByIdAndOwner(pVendee);
        if (vendee != null) {
            return "SELL";
        }
        return "PURT";
    }

    /**
     * Assist
     * 判断总单
     *
     * @author HHe
     * @date 2019/10/19 12:10
     */
    public boolean judgeTotDoc(String docNum, Long unitId) {
        //查询其他表中ldg_stb_num是否包含docNum
        List<String> childDocs = stbService.queryStbIsTotal(docNum, unitId);
        if (childDocs != null && childDocs.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 取消出库
     *
     * @author HHe
     * @date 2019/10/15 17:51
     */
    @Override
    public int reverseDeliver(GdnModel gdnModel, SysUser sysUser) {
        GdnStbModel gdnStbModel = judgeHaveProp(gdnModel);
        //判断状态
        if (!"DD".equals(gdnStbModel.getProgress()) || !"F".equals(gdnStbModel.getSuspended()) || !"F".equals(gdnStbModel.getCancelled())) {
            throw new ServiceException("201", "出库单状态不能取消出库");
        }
        int i = 0;
        if (DelivUtil.equalsHave(gdnStbModel.getDelivMode(), DeliveryDefineUtil.INTERNAL_ORDER, DeliveryDefineUtil.INTERNAL_RETURN, DeliveryDefineUtil.TRANSFER) && !gdnStbModel.getFsclUnitId().equals(gdnStbModel.getRcvFsclUnitId())) {
            gdnStbModel.setFsclDelivMode(DeliveryDefineUtil.TRANSFER);
        }
        //原生效标记
//        String effective = gdnStbModel.getEffective();
        //判断约定会计日期
        if (DeliveryDefineUtil.BOOLEAN_STR_T.equals(gdnStbModel.getFsclDateAptd())) {
            gdnStbModel.setFsclDate(null);
        }
        //todo总单走总单流程，暂时未启用
        if (gdnStbModel.getLdgStbNum() != null && "".equals(gdnStbModel.getLdgStbNum())) {
            throw new ServiceException("201", "总出库单功能暂未开放，请重新选择");
        }
        //获取明细集合
        List<StbDtl> stbDtls = stbDtlService.queryStbDtlsByNumAndUnit(gdnStbModel.getStbNum(), gdnStbModel.getUnitId());
        //撤销发货差异事件
        delivDiffEvtService.revocation(gdnStbModel.getUnitId(), gdnStbModel.getGdnNum());
        //查询仓库信息
        Wareh wareh = warehService.queryWarehByWarehId(gdnStbModel.getWarehId());
        //核算成本开关
//        boolean costB = false;
        //判断库存管理
        AbPf abPf = null;
        try {
            abPf = abPfService.precheck(gdnStbModel.getFsclDate(), gdnStbModel.getFsclUnitId());
        } catch (ParseException e) {
            throw new ServiceException("201", "账套异常");
        }
        List<CountCostModel> countCostModels = new ArrayList<>();
        if (abPf != null && "T".equals(wareh.getStkAdopted())) {
            Boolean b;
            //核算成本
            if (gdnStbModel.getHldnCostGrpId() != null && gdnStbModel.getCostGrpId().equals(gdnStbModel.getHldnCostGrpId())) {
                b = false;
            } else if (gdnStbModel.getHldnCostGrpId() != null && !gdnStbModel.getCostGrpId().equals(gdnStbModel.getHldnCostGrpId())) {
                b = true;
            } else {
                //不符合以上判断则判断出库方式是否是影响成本出库方式
                CostDmode costDmo = costDmodeService.getCostDmodeByUnitMode(gdnStbModel.getUnitId(), gdnStbModel.getDelivMode());
                if (costDmo == null) {
                    b = false;
                } else {
                    b = true;
                }
            }
            if (stbDtls != null && stbDtls.size() > 0) {
                StkCostModel stkCostModel = new StkCostModel();
                stkCostModel.setCostChanged(b);
                stkCostModel.setReversed(false);
                stkCostModel.setDrType(gdnStbModel.getDrType());
                stkCostModel.setFsclUnitId(gdnStbModel.getFsclUnitId());
                stkCostModel.setMode(gdnStbModel.getDelivMode());
                stkCostModel.setWarehId(gdnStbModel.getWarehId());
                stkCostModel.setStbDtls(stbDtls);
                stkCostModel.setCostGrpId(gdnStbModel.getCostGrpId());
                //仓库和组织方数据
                //todo 核算成本
                countCostModels = stkCostService.instantCalculate(stkCostModel);
            }
        }
        //全部成功修改出库单信息
        Gdn gdn = new Gdn();
        Stb stb = new Stb();
        BeanUtils.copyProperties(gdnStbModel, gdn);
        BeanUtils.copyProperties(gdnStbModel, stb);
        gdn.setProgress("CN");
        i += gdnMapper.updateGdn(gdn);
        i += stbService.updateStbByNumAndId(stb);
        //todo 通知原单已发货
        try {
            transferUtil.sendTo(DeliveryDefineUtil.REVERSE_DELIVER, gdnStbModel.getSrcDocType(), gdnStbModel.getSrcDocUnitId(), gdnStbModel.getSrcDocNum(), gdnStbModel.getUnitId(), gdnStbModel.getGdnNum(), sysUser);
        } catch (Exception e) {
            throw new ServiceException("201", "通知原单出库状态异常");
        }
        //调用异步处理出库后续流程
        asyncService.gdnDeliver(countCostModels, gdnStbModel.getWarehId(), gdnStbModel.getFsclUnitId());
        return i;
    }

    /**
     * 修改出库单
     *
     * @param gdn 需要修改的对象数据
     * @return 数据库从操作数
     * @author HHe
     * @date 2019/10/29 11:22
     */
    @Async
    @Override
    public void asyncUpdateGdn(Gdn gdn) {
        gdnMapper.updateGdn(gdn);
    }

    /**
     * 删除出库出库单以及关联的库存和明细
     *
     * @param gdnModel 出库单、库存单
     * @return
     * @author HHe
     * @date 2019/11/6 12:20
     */
    @Override
    public int delGdnAndDtl(GdnModel gdnModel, SysUser sysUser) {
        int i = 0;
        Gdn gdn = new Gdn();
        gdn.setGdnNum(gdnModel.getGdnNum());
        gdn.setUnitId(gdnModel.getUnitId());
        Stb stb = new Stb();
        stb.setStbNum(gdnModel.getGdnNum());
        stb.setUnitId(gdnModel.getUnitId());
        StbDtl stbDtl = new StbDtl();
        stbDtl.setUnitId(gdnModel.getUnitId());
        stbDtl.setStbNum(gdnModel.getStbNum());
        //1、删除出库单
        i += gdnMapper.deleteGdn(gdn);
        //2、删除库存单
        i += stbService.delStb(stb);
        //3、删除明细
        i += stbDtlService.delByNumAndId(stbDtl);
        return i;
    }

    /**
     * 查询列表总金额、总数量；
     *
     * @param gdnFilterModel 筛选条件
     * @return ttl_qty（总数量）、ttl_val（总金额）
     * @author HHe
     * @date 2019/11/18 17:50
     */
    @Override
    public Map<String, Object> getListTotal(GdnFilterModel gdnFilterModel) {
        Map<String, Object> map = gdnMapper.getListTotal(gdnFilterModel);
        if (map == null) {
            map.put("ttlQty", 0);
            map.put("ttlVal", 0);
        }
        return map;
    }

    /**
     * 查询出库单
     *
     * @param stbNum 库存单编号
     * @param unitId 组织Id
     * @return
     * @author HHe
     * @date 2019/11/28 20:50
     */
    @Override
    public GdnStbModel queryGdnModelByNumAndId(String stbNum, Long unitId) {
        return gdnMapper.queryGdnStbByNumAndUnit(stbNum, unitId);
    }

    /**
     * 判断商品是否能添加、删除
     *
     * @author HHe
     * @date 2019/11/29 16:09
     */
    @Override
    public Map<String, Object> judgeDelAndUpd(GdnModel gdnModel) {
        GdnStbModel gdnStbModel = judgeHaveProp(gdnModel);
        int update = 0;
        int delete = 0;
        if ("PG".equals(gdnStbModel.getProgress()) && "F".equals(gdnStbModel.getSuspended()) && "F".equals(gdnStbModel.getCancelled())) {
            update = 1;
        }
        if (DelivUtil.equalsHave(gdnStbModel.getProgress(), "PG", "CN") && "F".equals(gdnStbModel.getSuspended()) && "F".equals(gdnStbModel.getCancelled())) {
            delete = 1;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("update", update);
        map.put("delete", delete);
        return map;
    }

    /**
     * 添加一条出库单明细
     *
     * @param stbDtl 组织Id，商品Id，库存单编号，数量，单价，折率，税率
     * @author HHe
     * @date 2019/11/29 17:19
     */
    @Override
    public int addGdnOneStbDtl(StbDtl stbDtl) {
        //②添加/修改明细对象计算封装
        stbDtl = packCountStbDtl(stbDtl);
        //添加一条出库单明细
        int i = 0;
        try {
            i = stbDtlService.insertStbDtl(stbDtl);
        } catch (Exception e) {
            throw new ServiceException("201", "不可添加列表中已存在商品");
        }
        return i;
    }

    /**
     * 修改出库单明细
     *
     * @param stbDtl 商品Id
     * @return
     * @author HHe
     * @date 2019/12/2 18:00
     */
    @Override
    public int updateGdnOneStbDtl(StbDtl stbDtl) {
        stbDtl = packCountStbDtl(stbDtl);
        return stbDtlService.updateStbDtl(stbDtl);
    }

    /**
     * 删除出库单明细（单条）
     *
     * @param gdnStbDtlModel 组织Id、库存单编号、商品Id
     * @return
     * @author HHe
     * @date 2019/12/3 10:26
     */
    @Override
    public int delGdnOneStbDtl(GdnStbDtlModel gdnStbDtlModel) {
        judgeOptDtl(gdnStbDtlModel.getUnitId(), gdnStbDtlModel.getStbNum());
        StbDtl stbDtl = new StbDtl();
        stbDtl.setUnitId(gdnStbDtlModel.getUnitId());
        stbDtl.setStbNum(gdnStbDtlModel.getStbNum());
        stbDtl.setProdId(gdnStbDtlModel.getProdId());
        return stbDtlService.deleteAll(stbDtl);
    }

    /**
     * 根据商品代码查询出库单页面商品相关信息
     *
     * @param :购销单编号、退货编号等
     * @author HHe
     * @date 2019/8/27 11:53
     */
    @Override
    public StbDtlVo queryProVoByProCode(StbDtlVo stbDtlVo, SysUser sysUser) {
//        StbDtlVo stbDtlVo = new StbDtlVo();
        String proCode = stbDtlVo.getProdCode();
        Product product = productService.queryProVoByProCode(proCode);
        //查询出库单
        if (product == null) {
            throw new ServiceException("201", "商品【" + proCode + "】不存在");
        }
        GdnStbModel gdnStbModel = gdnMapper.queryGdnStbByNumAndUnit(stbDtlVo.getStbNum(), stbDtlVo.getUnitId());
        ProdCls prodCls2Id = new ProdCls();
        prodCls2Id.setProdClsId(product.getProdClsId());
        ProdCls prodCls = prodClsService.queryProdClsById(prodCls2Id);
        if (StringUtils.isNotEmpty(gdnStbModel.getSrcDocType()) && StringUtils.isNotEmpty(gdnStbModel.getCntrNum())) {
            //有原始单据
//            CommonDtl commonDtl = null;
//            List<CommonDtl> commonDtls = transferUtil.getcommondtl(gdnStbModel.getSrcDocType(), gdnStbModel.getCntrNum());
//            if(commonDtls==null||commonDtls.size()<=0){
//                throw new ServiceException("201", "原单无明细数据");
//            }
//            for (CommonDtl dtl : commonDtls) {
//                if (product.getProdId().equals(dtl.getProdId())) {
//                    commonDtl = dtl;
//                    break;
//                }
//            }
//            if (commonDtl == null) {
//                throw new ServiceException("201", "原始单据中不存在商品" + proCode);
//            }

            CommonDtl commonDtl = transferUtil.queryOneSrcDtl(gdnStbModel.getSrcDocType(), gdnStbModel.getCntrNum(),product.getProdId());
            BeanUtils.copyProperties(commonDtl, stbDtlVo, "qty", "val", "mkv", "tax", "remarks", "lineNum", "rowNum");
            stbDtlVo.setExpdQty(commonDtl.getQty());
        } else {
            //无原始单据单价取吊牌价,折率取1，税率给0
            stbDtlVo.setUnitPrice(prodCls.getLstPrice());
            stbDtlVo.setMkUnitPrice(prodCls.getLstPrice());
            stbDtlVo.setDiscRate(new BigDecimal("1"));
            stbDtlVo.setFnlPrice(stbDtlVo.getUnitPrice().multiply(stbDtlVo.getDiscRate()));
            stbDtlVo.setTaxRate(new BigDecimal("0"));
        }
        stbDtlVo.setProdId(product.getProdId());
        stbDtlVo.setProdClsId(prodCls.getProdClsId());
        stbDtlVo.setProdCode(product.getProdCode());
        stbDtlVo.setProdName(prodCls.getProdName());
        stbDtlVo.setInputCode(prodCls.getInputCode());
        //根据code和type查询sysCodeDtl对象
        SysCodeDtl sysCodeDtluom = sysCodeDtlService.queryCodeDtlByCodeType(prodCls.getUom(), DeliveryDefineUtil.CODE_UOM);
        stbDtlVo.setUom(prodCls.getUom());
        stbDtlVo.setUomCp(sysCodeDtluom.getDescription());
        SysUnit sysUnit = sysUnitService.queryUnitByCode(prodCls.getProdLine());
        if (sysUnit != null) {
            stbDtlVo.setProdLine(sysUnit.getUnitName());
        }
        //预定义货位
        stbDtlVo.setSampleNum(prodCls.getSampleNum());
        Color color2Id = new Color();
        color2Id.setColorId(product.getColorId());
        Color color = colorService.getColorById(color2Id);
        if (color != null) {
            stbDtlVo.setColorName(color.getColorName());
        }
        //版型名
        stbDtlVo.setEdition(prodCls.getEdition());
        SysCodeDtl sysCodeDtledition = sysCodeDtlService.queryCodeDtlByCodeType(prodCls.getEdition(), DeliveryDefineUtil.CODE_EDITION);
        if (sysCodeDtledition != null) {
            stbDtlVo.setEditionCp(sysCodeDtledition.getDescription());
        }
        Spec spec2Id = new Spec();
        spec2Id.setSpecId(prodCls.getSpecId().longValue());
        Spec spec = specService.getSpecById(spec2Id);
        if (spec != null) {
            stbDtlVo.setSpecName(spec.getSpecName());
        }
        return stbDtlVo;
    }

    /**
     * WMS确认单据（异步）
     *
     * @param dhOrdTask 下发任务记录
     * @param objXml    WMS确认出库信息
     * @author HHe
     * @date 2019/12/4 21:26
     */
    @Async
    @Override
    public void wmsConfirmGdn(DhOrdTask dhOrdTask, String objXml) {
        JaxbUtil jaxbUtil = new JaxbUtil(DeliveryOrderConfirmModel.class);
        DeliveryOrderConfirmModel deliveryOrderConfirmModel;
        String errMess = "";
        String code;
        try {
            deliveryOrderConfirmModel = jaxbUtil.fromXml(objXml);
            //源单所在领域ID+源单类型+发货仓所在领域id+源单单号
            String[] srcMess = deliveryOrderConfirmModel.getDeliveryOrder().getDeliveryOrderCode().split("_");
            GdnStbModel gdnStbModel = new GdnStbModel();
            gdnStbModel.setSrcDocUnitId(Long.valueOf(srcMess[0]));
            gdnStbModel.setSrcDocType(srcMess[1]);
            gdnStbModel.setUnitId(Long.valueOf(srcMess[2]));
            gdnStbModel.setSrcDocNum(srcMess[3]);
            gdnStbModel = gdnMapper.queryGdnStbByObj(gdnStbModel);
            List<Package> pack = deliveryOrderConfirmModel.getPackages().getPack();
            List<StbDtlVo> stbDtlVos = new ArrayList<>();
            Set<String> prodCodes = new HashSet<>();
            for (Package aPackage : pack) {
                for (Item item : aPackage.getItems().getItem()) {
                    prodCodes.add(item.getItemCode());
                    boolean b = true;
                    if (stbDtlVos.size() <= 0) {
                        stbDtlVos.add(packDtlVo(item.getItemCode(),new BigDecimal(item.getQuantity())));
                        continue;
                    }
                    for (int i = 0; i < stbDtlVos.size(); i++) {
                        if (stbDtlVos.get(i).getProdCode().equals(item.getItemCode())) {
                            stbDtlVos.get(i).setQty(stbDtlVos.get(i).getQty().add(new BigDecimal(item.getQuantity())));
                            b = false;
                        }
                    }
                    if (b) {
                        stbDtlVos.add(packDtlVo(item.getItemCode(),new BigDecimal(item.getQuantity())));
                    }
                }
            }
            //查询商品Id
            List<Product> products= productService.queryProductListByCodes(prodCodes);
            //匹配
            List<StbDtl> stbDtlList = new ArrayList<>();
            for (StbDtlVo stbDtlVo : stbDtlVos) {
                for (Product product : products) {
                    if(stbDtlVo.getProdCode().equals(product.getProdCode())){
                        StbDtl stbDtl = new StbDtl();
                        stbDtl.setProdId(product.getProdId());
                        stbDtl.setQty(stbDtlVo.getQty());
                        stbDtl.setStbNum(gdnStbModel.getStbNum());
                        stbDtl.setUnitId(gdnStbModel.getUnitId());
                        stbDtlList.add(stbDtl);
                    }
                }
            }
            //修改库存单明细
            stbDtlService.updateStbDtlList(stbDtlList);
            //出库单直接走完确认+出库流程
            GdnModel gdnModel = new GdnModel();
            gdnModel.setGdnNum(gdnStbModel.getGdnNum());
            gdnModel.setUnitId(gdnStbModel.getUnitId());
            //通知原单发货中
            transferUtil.sendTo(DeliveryDefineUtil.START_DELIVER, gdnStbModel.getSrcDocType(), gdnStbModel.getSrcDocUnitId(), gdnStbModel.getSrcDocNum(), null, null, null);
            //确认
            this.confirm(gdnModel,new SysUser(gdnStbModel.getUnitId(),8888L));
            //出库
            this.deliver(gdnModel,new SysUser(gdnStbModel.getUnitId(),8888L));
            Gdn gdn = new Gdn();
            gdn.setGdnNum(gdnStbModel.getGdnNum());
            gdn.setUnitId(gdnStbModel.getUnitId());
            gdn.setShowDoc(DeliveryDefineUtil.BOOLEAN_STR_T);
            //修改可以显示
            gdnMapper.updateGdn(gdn);
            code = DeliveryDefineUtil.executeStatus.success.toString();
        } catch (Exception e) {
            log.info("登记异常信息" + e.getMessage());
            errMess = e.getMessage();
            code = DeliveryDefineUtil.executeStatus.failure.toString();
        }
        //修改下发任务
        dhOrdTask.setErrMsg(errMess);
        dhOrdTask.setExecuteStatus(code);
        dhOrdTaskService.updateTask(dhOrdTask);
    }

    /**
     * 封装库存单明细
     * @author HHe
     * @date 2019/12/5 20:59
     */
    private StbDtlVo packDtlVo(String code,BigDecimal qty){
        StbDtlVo stbDtlVo = new StbDtlVo();
        stbDtlVo.setProdCode(code);
        stbDtlVo.setQty(qty);
        return stbDtlVo;
    }
//    /**
//     * 出库单明细弹窗
//     * @param goodsPopupFilterModel 出库单筛选信息
//     * @return 商品弹窗分页集合
//     * @author HHe
//     * @date 2019/12/5 19:28
//     */
//    @Override
//    public PageInfo<DtlProdVo> dtlPopup(Integer page, Integer size,GoodsPopupFilterModel goodsPopupFilterModel) {
//        //1.判断组织是否是总部，总部：在unit_prod_cls中组织的品种去查询商品；非总部：所有共享
//        //查询总部
//        SysUnit sysUnit = sysUnitService.selectByPrimaryKey(goodsPopupFilterModel.getsUnitId());
//        if(sysUnit==null){
//            throw new ServiceException("201", "组织不可用");
//        }
//        List<DtlProdVo> dtlProdVos = new ArrayList<>();
//        if(DeliveryDefineUtil.HQ.equals(sysUnit.getUnitType())){
//            //查询在unit_prod_cls中组织的品种去查询商品s
//            PageHelper.startPage(page, size);
//            dtlProdVos = goodsPopupService.queryHQSupplyProdPopUp(goodsPopupFilterModel);
//        }else{
//            //所有共享
//            PageHelper.startPage(page, size);
//        }
//        PageInfo<DtlProdVo> info = new PageInfo<>(dtlProdVos);
//        return info;
//    }




    /**
     * Assist
     * 一、根据商品Id查询商品品种信息
     *
     * @author HHe
     * @date 2019/12/3 9:26
     */
    private ProdCls queryClsByProdId(Long prodId) {
        Product product = productService.queryProductById(prodId);
        ProdCls prodCls = new ProdCls();
        prodCls.setProdClsId(product.getProdClsId());
        return prodClsService.queryProdClsById(prodCls);
    }

    /**
     * Assist
     * 二、添加/修改明细对象计算封装
     *
     * @author HHe
     * @date 2019/12/3 9:48
     */
    private StbDtl packCountStbDtl(StbDtl stbDtl) {
        //判断明细能否操作
        judgeOptDtl(stbDtl.getUnitId(), stbDtl.getStbNum());
        //行号
        stbDtl.setLineNum(stbDtlService.queryDtlCountByObj(stbDtl) + 1L);
        //查询商品的信息：市场单价：品种->挂牌价
        ProdCls prodCls = queryClsByProdId(stbDtl.getProdId());
        //市场单价
        BigDecimal mkUnitPrice = prodCls.getLstPrice();
        stbDtl.setMkUnitPrice(mkUnitPrice);
        //折后价
        BigDecimal fnlPrice = stbDtl.getUnitPrice().multiply(stbDtl.getDiscRate()).setScale(4, BigDecimal.ROUND_HALF_UP);
        stbDtl.setFnlPrice(fnlPrice);
        //预期数量
        stbDtl.setExpdQty(stbDtl.getQty());
        //金额
        stbDtl.setVal(stbDtl.getQty().multiply(fnlPrice));
        //税款
        stbDtl.setTax(stbDtl.getVal().divide(stbDtl.getTaxRate().add(ONE)).multiply(stbDtl.getTaxRate()));
        //市值
        stbDtl.setMkv(mkUnitPrice.multiply(stbDtl.getQty()));
        return stbDtl;
    }

    /**
     * Assist
     * 三、判断能否操作明细
     *
     * @author HHe
     * @date 2019/12/3 10:12
     */
    private void judgeOptDtl(Long unitId, String stbNum) {
        //判断出库单
        GdnModel gdnModel = new GdnModel();
        gdnModel.setUnitId(unitId);
        gdnModel.setGdnNum(stbNum);
        GdnStbModel gdnStbModel = judgeHaveProp(gdnModel);
        if (!"PG".equals(gdnStbModel.getProgress().toUpperCase()) || !"F".equals(gdnStbModel.getSuspended()) || !"F".equals(gdnStbModel.getCancelled())) {
            throw new ServiceException("201", "出库单状态不可操作明细");
        }
    }
    /**
     * Assist
     * 四、封装添加修改可添加、可修改字段（防脏）
     * @author HHe
     * @date 2019/12/6 11:43
     */
    private GdnModel packAddUpdMess(GdnModel gdnModelP){
        GdnModel gdnModel = new GdnModel();
        //约定会计日期
        gdnModel.setFsclDateAptd(judgeNullFie(gdnModelP.getFsclDateAptd(),"约定会计日期"));
        //会计日期
        gdnModel.setFsclDate(gdnModelP.getFsclDate());
        //即时结算
        gdnModel.setInstStl(judgeNullFie(gdnModelP.getInstStl(),"即时结算"));
        //送货方式
        gdnModel.setDelivMthd(gdnModelP.getDelivMthd());
        //邮政编码
        gdnModel.setDelivPstd(gdnModelP.getDelivPstd());
        //送货地址
        gdnModel.setDelivAddr(gdnModelP.getDelivAddr());
        //启用派车
        gdnModel.setVehReqd(judgeNullFie(gdnModelP.getVehReqd(),"启用派车"));
        //启用托运
        gdnModel.setCnsnReqd(judgeNullFie(gdnModelP.getCnsnReqd(),"启用托运"));
        //启用配码
        gdnModel.setBxiEnabled(judgeNullFie(gdnModelP.getBxiEnabled(),"启用配码"));
        //启用装箱
        gdnModel.setBoxReqd(judgeNullFie(gdnModelP.getBoxReqd(),"启用装箱"));
        //启用分拣
        gdnModel.setPickReqd(judgeNullFie(gdnModelP.getPickReqd(),"启用分拣"));
        //启用复核
        gdnModel.setRckReqd(judgeNullFie(gdnModelP.getRckReqd(),"启用复核"));
        //预定装箱
        gdnModel.setBoxSchd(judgeNullFie(gdnModelP.getBoxSchd(),"预定装箱"));
        return gdnModel;
    }
    /**
     * 检查字段是否为空
     * @author HHe
     * @date 2019/12/7 10:57
     */
    public <T> T judgeNullFie(T obj,String hint){
        if(obj==null||"".equals(obj)){
            throw new ServiceException("201", hint+"不可为空");
        }
        return obj;
    }
}
