package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.TPOS.entity.CancelOrder;
import com.boyu.erp.platform.usercenter.TPOS.entity.DhOrdTaskU;
import com.boyu.erp.platform.usercenter.TPOS.entity.WmsErpNum;
import com.boyu.erp.platform.usercenter.TPOS.entity.Worker;
import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.DeliveryOrder;
import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.OrderLine;
import com.boyu.erp.platform.usercenter.TPOS.entity.delivery.OrderLines;
import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.TPOS.model.DeliveryOrderModel;
import com.boyu.erp.platform.usercenter.TPOS.service.DhOrdTaskUService;
import com.boyu.erp.platform.usercenter.TPOS.service.RequestTPOService;
import com.boyu.erp.platform.usercenter.TPOS.service.WmsErpNumService;
import com.boyu.erp.platform.usercenter.TPOS.service.WmsWarehMessService;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDmode;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehDelivTaskMapper;
import com.boyu.erp.platform.usercenter.model.wareh.GdnModel;
import com.boyu.erp.platform.usercenter.model.wareh.WarehDelivTaskFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.WarehDelivTaskModel;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.*;
import com.boyu.erp.platform.usercenter.service.warehouse.*;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.utils.warehouse.DeliveryDefineUtil;
import com.boyu.erp.platform.usercenter.utils.warehouse.TransferUtil;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.purchase.Purchase;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehDelivTaskVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service("warehDelivTaskService")
@Transactional
public class WarehDelivTaskServiceImpl implements WarehDelivTaskService {
    @Autowired
    private WarehDelivTaskMapper warehDelivTaskMapper;
    @Autowired
    private UsersService userService;
    @Autowired
    private GdnService gdnService;
    @Autowired
    private DelivUtil delivUtil;
    @Autowired
    private WarehService warehService;
    @Autowired
    private WarehDmodeService warehDmodeService;
    @Autowired
    private StbDtlService stbDtlService;
    @Autowired
    private SysUgService sysUgService;
    @Autowired
    private SysUnitOwnerSerivcer sysUnitOwnerSerivcer;
    @Autowired
    private WarehUgService warehUgService;
    @Autowired
    private WarehUgDtlService warehUgDtlService;
    @Autowired
    private WmsWarehMessService wmsWarehMessService;
    @Autowired
    private RequestTPOService requestTPOService;
    @Autowired
    private WmsErpNumService wmsErpNumService;
    @Autowired
    private SysParameterService sysParameterService;
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private ProductService productService;
    @Autowired
    private DhOrdTaskUService dhOrdTaskUService;
    @Autowired
    private StbService stbService;
    @Autowired
    private SysUgDtlService sysUgDtlService;
    @Autowired
    private TransferUtil transferUtil;
    @Autowired
    private WarehDelivTaskExternalCiteService warehDelivTaskExternalCiteService;
    @Autowired
    private GdnExternalCiteService gdnExternalCiteService;
    @Autowired
    private AsyncService asyncService;

    /**
     * 原单开始发货
     */
    private static final String START_DELIVER = "startDeliver";
    /**
     * Field
     * 使用WMS单据推送仓库参数
     */
    private static final String USE_WMS_WAREH_IDS = "USE_WMS_WAREH_IDS";
    private static final String APP_KEY = "open1stccj5746b";
    /**
     * 接口用户
     */
    private static final String IDINF_OPR_ID = "2121676";

    /**
     * 初始化出库任务下拉
     *
     * @return
     */
    @Override
    public Map<String, Object> delivTaskPullDown(Long sUnitId, SysUser sysUser) {
        Map<String, String> pMap = new HashMap<>();
        pMap.put("delivMode", "DELIV_MODE");
        pMap.put("suspended", "BOOLEA");
        Map<String, Object> map = delivUtil.getCodeDtlMap2(pMap);
        if (sUnitId == null) {
            sUnitId = sysUser.getDomain().getUnitId();
        }
        map.put("sysUgIds", sysUgService.getSysUgMapByTypeOwner("WD", sUnitId));
        map.put("group", Arrays.asList(new OptionVo("收货方", "1"), new OptionVo("收货仓库", "2")));
        return map;
    }


    /**
     * 条件查询出库任务列表
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<WarehDelivTaskVO> selectAll(WarehDelivTaskFilterModel warehDelivTaskFilterModel, Integer page, Integer size, SysUser sysUser) throws InvocationTargetException, NoSuchMethodException, IntrospectionException, NoSuchFieldException, IllegalAccessException {
        //封装前置条件查询
        warehDelivTaskFilterModel = packQueryList(warehDelivTaskFilterModel);
        //根据num和组织Id查询
        List<WarehDelivTaskVO> list;
        PageHelper.startPage(page, size);
        list = warehDelivTaskMapper.selectDelivByUnitId(warehDelivTaskFilterModel);
        list = this.packList(list);
        PageInfo<WarehDelivTaskVO> info = new PageInfo<>(list);
        return info;
    }

    /**
     * 添加出库任务
     *
     * @param delivTask
     * @param sysUser
     * @return
     * @throws ServiceException
     */
    @Override
    public int addDelivTask(WarehDelivTask delivTask, SysUser sysUser) throws Exception {
        int i = 0;
        boolean autoExec = false;
        //发货仓库有可能为空的情况
        if (delivTask.getWarehId() != null) {
            //出库方式(判断自动执行)
            WarehDmode warehDmode = warehDmodeService.queryDmodeByWarehIdMode(delivTask.getWarehId(), delivTask.getDelivMode());
            if (warehDmode == null) {
                throw new ServiceException("201", "仓库没有出库方式");
            }
            autoExec = "T".equals(warehDmode.getAutoExec().toUpperCase()) ? true : false;
        }

        //是否生成预执行出库单
        String pg = "NG";
        //判断自动执行
        if (autoExec) {
            //生成任务
            delivTask.setImplTimes(0L);
            delivTask.setSuspended("F");
            delivTask.setProgress(pg);
            i += warehDelivTaskMapper.insertUpdateTask(delivTask);
            //生成出库单，出库
            WarehDelivTaskModel warehDelivTaskModel = new WarehDelivTaskModel();
            warehDelivTaskModel.setsUnitId(delivTask.getUnitId());
            warehDelivTaskModel.setWarehDelivTaskList(Arrays.asList(delivTask));
            String docNum = this.addGdn4Task1(warehDelivTaskModel, sysUser);
            //出库
            GdnModel gdnModel = new GdnModel();
            gdnModel.setGdnNum(docNum);
            gdnModel.setUnitId(delivTask.getUnitId());
            i += gdnService.deliver(gdnModel, sysUser);
        } else {
            //创建出库任务，判断原始单据类型判断推送创建发货单还是出库单
            //判断发货仓库是否是使用WMS
            SysParameter wmsWarehP = sysParameterService.findByParameter(USE_WMS_WAREH_IDS);
            if (wmsWarehP == null) {
                throw new ServiceException("201", "参数配置有误");
            }
            //使用WMS的仓库集合
            String[] wmsWarehs = wmsWarehP.getParmVal().split(",");
            if (Arrays.asList(wmsWarehs).contains(delivTask.getWarehId())) {
                //出库任务已生成出库单
                pg = "YG";
                //封装出库单信息
                GdnModel gdnModel = packDelivTask2StbGdn(delivTask);
                gdnModel.setShowDoc("F");
                //查询原单明细集合
                List<StbDtl> stbDtls = packStbDtl(delivTask.getTaskDocType(), gdnModel.getCntrNum(), delivTask.getUnitId(), gdnModel.getStbNum());
                gdnModel.setStbDtlList(stbDtls);
                //预生成出库单
                String docNum = gdnExternalCiteService.addGdnAndDtl(gdnModel, sysUser);
                //使用wms系统
                //请求原始单据信息
                if ("RLB".equals(delivTask.getTaskDocType())) {
                    //发送发货单

                } else {
                    //封装出库单信息
                    DeliveryOrderModel deliveryOrderModel = packWMSDeliver(gdnModel);
                    //封装请求信息
                    CwmsUrlParamModel cwmsUrlParamModel = packCwmsUrlParam("stockout.create");
                    //封装上传信息
                    DhOrdTaskU dhOrdTaskU = new DhOrdTaskU();
                    dhOrdTaskU.setHandleType("stockout.create");
                    //执行状态
                    dhOrdTaskU.setExecuteStatus("requesting");
                    //单据类别
                    dhOrdTaskU.setDocType(gdnModel.getSrcDocType());
                    //单据组织ID
                    dhOrdTaskU.setDocUnitId(gdnModel.getSrcDocUnitId());
                    //单据编号
                    dhOrdTaskU.setDocNum(gdnModel.getSrcDocNum());
                    //登记时间
                    dhOrdTaskU.setRegTime(new Date());
                    //异步调用WMS上传（上传中异步登记任务）；
                    asyncService.uploadingOrder(dhOrdTaskU, deliveryOrderModel, cwmsUrlParamModel);
                }
            }
            //登记出库任务
            delivTask.setJoinTime(new Timestamp(System.currentTimeMillis()));
            Long implTimes = 0L;
            delivTask.setImplTimes(implTimes);
            delivTask.setSuspended("F");
            delivTask.setProgress(pg);
            i += warehDelivTaskMapper.insertUpdateTask(delivTask);
        }
        return i;
    }

    /**
     * Assist
     * 封装库存明细
     *
     * @author HHe
     * @date 2019/11/7 16:33
     */
    private List<StbDtl> packStbDtl(String taskDocType, String dtlNum, Long unitId, String stbNum) {
        List<CommonDtl> commonDtls = transferUtil.getcommondtl(taskDocType, dtlNum);
        if (commonDtls == null || commonDtls.size() <= 0) {
            return null;
        }
        List<StbDtl> stbDtls = new ArrayList<>();
        for (CommonDtl commonDtl : commonDtls) {
            StbDtl stbDtl = new StbDtl();
            BeanUtils.copyProperties(commonDtl, stbDtl);
            stbDtl.setUnitId(unitId);
            stbDtl.setExpdQty(commonDtl.getQty());
            stbDtl.setStbNum(stbNum);
            stbDtls.add(stbDtl);
        }
        return stbDtls;
    }

    /**
     * Assist
     * 封装wms出库单
     *
     * @author HHe
     * @date 2019/11/4 15:35
     */
    private DeliveryOrderModel packWMSDeliver(GdnModel gdnModel) {
        Date now = new Date();
        DeliveryOrderModel deliveryOrderModel = new DeliveryOrderModel();
        //出库单
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        //出库单明细
        OrderLines orderLines = new OrderLines();
        //出库单号
        String deliveryOrderCode = gdnModel.getSrcDocUnitId() + "_" + gdnModel.getSrcDocType() + "_" + gdnModel.getUnitId() + "_" + gdnModel.getSrcDocNum();
        deliveryOrder.setDeliveryOrderCode(deliveryOrderCode);
        //出库单类型
        deliveryOrder.setOrderType("B2BCK");
        //出库单创建时间
        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH-mm-ss");
        String nowDate = sdf.format(now);
        deliveryOrder.setCreateTime(nowDate);
        //物流公司编码
        deliveryOrder.setLogisticsCode("OTHER");
        //所有相关的组织Id
        Set<Long> unitIds = new HashSet<>();
        unitIds.add(gdnModel.getRcvUnitId());
        unitIds.add(gdnModel.getRcvWarehId());
        unitIds.add(gdnModel.getWarehId());
        unitIds.add(gdnModel.getSrcDocUnitId());
        //所有相关的组织对象
        List<SysUnit> sysUnits = sysUnitService.queryUnitByIds(unitIds);
        //收货人信息
        Worker receiverInfo = new Worker();
        //货主组织代码
        String ownerCode = "";
        for (SysUnit sysUnit : sysUnits) {
            if (sysUnit.getUnitId().equals(gdnModel.getRcvUnitId())) {
                //收货方公司/加盟商code
                receiverInfo.setCompany(sysUnit.getUnitCode());
            }
            if (sysUnit.getUnitId().equals(gdnModel.getRcvWarehId())) {
                //收货方仓库/门店code
                receiverInfo.setName(sysUnit.getUnitCode());
            }
            if (sysUnit.getUnitId().equals(gdnModel.getWarehId())) {
                //发货仓库Code
                deliveryOrder.setWarehouseCode(sysUnit.getUnitCode());
            }
            if (sysUnit.getUnitId().equals(gdnModel.getSrcDocUnitId())) {
                //商品属主Code
                ownerCode = sysUnit.getUnitCode();
            }
        }
        //备注
        deliveryOrder.setRemark(gdnModel.getRemarks());
        //库存明细
        List<StbDtl> stbDtlList = gdnModel.getStbDtlList();
        List<OrderLine> orderLineList = new ArrayList<>();
        List<Long> prodIds = new ArrayList<>();
        for (StbDtl stbDtl : stbDtlList) {
            OrderLine orderLine = new OrderLine();
            orderLine.setOwnerCode(ownerCode);
            orderLine.setProdId(stbDtl.getProdId());
            orderLine.setPlanQty(stbDtl.getExpdQty().intValue());
            orderLine.setPrice(stbDtl.getUnitPrice());
            orderLineList.add(orderLine);
            prodIds.add(stbDtl.getProdId());
        }
        //查询商品代码
        List<Product> products = productService.queryProductsByProdIds(prodIds);
        //封装商品代码
        for (OrderLine orderLine : orderLineList) {
            for (Product product : products) {
                if (orderLine.getProdId().equals(product.getProdId())) {
                    orderLine.setItemCode(product.getProdCode());
                    break;
                }
            }
        }
        orderLines.setOrderLine(orderLineList);
        deliveryOrderModel.setDeliveryOrder(deliveryOrder);
        deliveryOrderModel.setOrderLines(orderLines);
        return deliveryOrderModel;
    }

    /**
     * Assist
     * 封装请求URL信息
     *
     * @author HHe
     * @date 2019/11/7 11:15
     */
    private CwmsUrlParamModel packCwmsUrlParam(String method) {
        CwmsUrlParamModel cwmsUrlParamModel = new CwmsUrlParamModel();
        cwmsUrlParamModel.setMethod(method);
        cwmsUrlParamModel.setV("1.0");
        cwmsUrlParamModel.setAppKey(APP_KEY);
        cwmsUrlParamModel.setCustomerid(IDINF_OPR_ID);
        cwmsUrlParamModel.setSignMethod("md5");
        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH-mm-ss");
        String timestamp = sdf.format(new Date());
        cwmsUrlParamModel.setTimestamp(timestamp);
        cwmsUrlParamModel.setFormat("xml");
        return cwmsUrlParamModel;
    }

    /**
     * 查询列表中的总数量、总金额
     *
     * @param warehDelivTaskFilterModel 查询的筛选条件
     * @teturn tot_val（总金额）、tot_qty（总数量）MAP
     * @author HHe
     * @date 2019/11/18 17:17
     */
    @Override
    public Map<String, Object> getListTotal(WarehDelivTaskFilterModel warehDelivTaskFilterModel) {
        //封装前置前置条件
        warehDelivTaskFilterModel = packQueryList(warehDelivTaskFilterModel);
        Map<String, Object> map = warehDelivTaskMapper.getListTotal(warehDelivTaskFilterModel);
        if (map == null) {
            map.put("ttlQty", 0);
            map.put("ttlVal", 0);
        }
        return map;
    }

    /**
     * 修改出库任务
     *
     * @author HHe
     * @date 2019/12/2 16:28
     */
    @Override
    public int updateDelivTask(WarehDelivTask delivTask) {
        return warehDelivTaskMapper.updateDelivTask(delivTask);
    }


    /**
     * 封装查询列表前置操作
     *
     * @author HHe
     * @date 2019/11/26 20:15
     */
    private WarehDelivTaskFilterModel packQueryList(WarehDelivTaskFilterModel warehDelivTaskFilterModel) {
        //根据组织Id和仓库编号查询出仓库Id集合set到筛选对象中；
        if (warehDelivTaskFilterModel.getWarehNum() != null && !"".equals(warehDelivTaskFilterModel.getWarehNum())) {
            warehDelivTaskFilterModel.setWarehIdList(sysUnitOwnerSerivcer.getNumAndOwner2Ids(warehDelivTaskFilterModel.getWarehNum(), warehDelivTaskFilterModel.getSUnitId(), "WH"));
        }
        //收货方Id需要查询SYS_UNIT_CLSF表传组织Id和供应商code：VE查询出供应商Id
        if (warehDelivTaskFilterModel.getRcvUnitNum() != null && !"".equals(warehDelivTaskFilterModel.getRcvUnitNum())) {
            warehDelivTaskFilterModel.setRcvUnitIdList(sysUnitOwnerSerivcer.getNumAndOwner2Ids(warehDelivTaskFilterModel.getRcvUnitNum(), warehDelivTaskFilterModel.getSUnitId(), "VE"));
        }
        //收货方仓库查询直接根据num查询出unitId集合
        if (warehDelivTaskFilterModel.getRcvWarehNum() != null && !"".equals(warehDelivTaskFilterModel.getRcvWarehNum())) {
            warehDelivTaskFilterModel.setRcvWarehIdList(sysUnitOwnerSerivcer.getNum2Ids(warehDelivTaskFilterModel.getRcvWarehNum()));
        }
        /*
        分组查询功能：
        1、分组默认为空白，如果为空白，不查询分组信息查询当前信息；
        2、选择了组织分组则加载组织分组的全部，若选择了仓库分组则加载仓库分组的全部
         */
        if (warehDelivTaskFilterModel.getUnitUgIds() != null && warehDelivTaskFilterModel.getUnitUgIds().size() > 0) {
            List<Long> warehIds = new ArrayList<>();
            //查询组织分组的组织集合
            if (warehDelivTaskFilterModel.getUnitUgIds() != null && warehDelivTaskFilterModel.getUnitUgIds().size() > 0) {
                if (warehDelivTaskFilterModel.getWarehUgIds() != null && warehDelivTaskFilterModel.getWarehUgIds().size() > 0) {
                    warehIds = warehUgDtlService.queryWarehIdsByWarehUgIds(warehDelivTaskFilterModel.getWarehUgIds());
                } else {
                    //查询组织分组对应的仓库分组
                    List<Long> warehUgIds = warehUgService.queryWarehUgIdsBySysUgId(warehDelivTaskFilterModel.getUnitUgIds());
                    //根据查询出来的所有仓库分组查询组织分组
                    warehIds = warehUgDtlService.queryWarehIdsByWarehUgIds(warehUgIds);
                }
            }
            if (warehIds.size() <= 0) {
                warehIds.add(-1L);
            }
            warehDelivTaskFilterModel.setUgWarehIds(warehIds);
        }
        return warehDelivTaskFilterModel;
    }

    /**
     * 取消出库任务
     *
     * @author HHe
     * @date 2019/11/4 19:23
     */
    @Override
    public int delDelivTask(WarehDelivTask delivTaskP, SysUser sysUser) {
        //查询任务
        WarehDelivTask delivTask = warehDelivTaskMapper.queryDelivTaskByBill(delivTaskP);
        if (delivTask == null) {
//            throw new ServiceException("201", "任务信息错误");
            //todo 采购反审的时候，如果销售不是自动审核，会去删除出库任务，当对应销售反审的时候，也会去删除调用取消接口，此时直接返回；
            return 0;
        }
        //判断发货仓库是否是使用WMS
        SysParameter wmsWarehP = sysParameterService.findByParameter(USE_WMS_WAREH_IDS);
        if (wmsWarehP == null) {
            throw new ServiceException("201", "参数配置有误");
        }
        //使用WMS的仓库集合
        String[] wmsWarehs = wmsWarehP.getParmVal().split(",");
        if (Arrays.asList(wmsWarehs).contains(delivTask.getWarehId())) {
            //删除预生成出库单

            //WMS取消单据对象
            //查询对应关系中的对应编号
            String taskDocNum = delivTask.getTaskDocUnitId() + "_" + delivTask.getTaskDocType() + "_" + delivTask.getUnitId() + "_" + delivTask.getTaskDocNum();
            WmsErpNum wmsErpNumP = new WmsErpNum();
            wmsErpNumP.setTaskDocNum(taskDocNum);
            WmsErpNum wmsErpNum = wmsErpNumService.queryRelation(wmsErpNumP);
            GdnModel gdnModel = new GdnModel();
            gdnModel.setStbNum(wmsErpNum.getOriginNum());
            gdnModel.setUnitId(delivTaskP.getUnitId());
            //删除预生成出库单的所有信息
            gdnService.delGdnAndDtl(gdnModel, sysUser);
            //删除关联关系
            wmsErpNumService.delRelation(wmsErpNum);
            //取消WMS单据推送对象
            CancelOrder cancelOrder = new CancelOrder();

            if (DeliveryDefineUtil.RETAIL_ORDER.equals(delivTask.getTaskDocType())) {
                cancelOrder.setOrderType("QTCK");
            } else {

            }

        }
        //删除出库任务
        return warehDelivTaskMapper.deleteByBill(delivTask);
    }

    /**
     * 出库任务生成出库单（PLAN2）【待完善】【最终效果】
     * 备注：不并单，不判断字段，直接循环生成；
     *
     * @author HHe
     * @date 2019/8/30 20:18
     */
    @Override
    public String addGdn4Task1(WarehDelivTaskModel warehDelivTaskModel, SysUser sysUser) throws Exception {
        //查询出库任务
        List<WarehDelivTask> delivTasks = warehDelivTaskMapper.queryDelivTaskByList(warehDelivTaskModel.getWarehDelivTaskList(), warehDelivTaskModel.getsUnitId());
        if (delivTasks == null || delivTasks.size() <= 0) {
            throw new ServiceException("201", "请选择正确的出库任务");
        }
//        List<WarehDelivTask> delivTasks = warehDelivTaskModel.getWarehDelivTaskList();
        //查看是否有没有输入仓库发货仓库的任务
        for (WarehDelivTask delivTask : delivTasks) {
            if (delivTask.getWarehId() == null) {
                if (warehDelivTaskModel.getDefaultWarehId() == null) {
                    throw new ServiceException("201", "请输入缺省仓库编号");
                }
                delivTask.setWarehId(warehDelivTaskModel.getDefaultWarehId());
            }
        }

        //生成第一条出库单
        WarehDelivTask warehDelivTask = delivTasks.get(0);
        //封装出库单
        GdnModel totStb = this.packDelivTask2StbGdn(warehDelivTask);
        //需要添加的明细集合
        List<StbDtl> stbDtls;
        //出库单集合
//        List<GdnModel> gdnModelList = new ArrayList<>();
        //判断是否多条
        //todo 通知原单发货中(长远需要集合，目前功能单条) 原单调用取消出库任务，删除出库任务、预生成出库单；
//            try {
        transferUtil.sendTo(DeliveryDefineUtil.START_DELIVER, warehDelivTask.getTaskDocType(), warehDelivTask.getTaskDocUnitId(), warehDelivTask.getTaskDocNum(), null, null, null);
//            } catch (Exception e) {
//                throw new ServiceException("201", "通知原单正在发货异常");
//            }
        if (delivTasks.size() > 1) {
            //1、遍历出库任务生成出库单对象封装集合
            //todo 需要王喆提供多条查询接口
            //2、修改totStb总数量、总金额、总税款等数据
            //3、将totStb封装进gdnModelList
            throw new ServiceException("201", "多条出库任务生成功能暂未开放，请选择单条生成");
        } else {
            //判断出库任务是否已生成出库单（已生成则修改出库单显示为T，不新增出库单）
//            if (DeliveryDefineUtil.YES_GENERATE.equals(warehDelivTask.getProgress())) {
//                //修改已生成出库单可显示
//
//            } else {
                //只生成一张出库单
                gdnExternalCiteService.addGdnAndDtl(totStb, sysUser);
                //生成第一条库存单额的明细
                //封装明细
                stbDtls = packStbDtl(warehDelivTask.getTaskDocType(), totStb.getCntrNum(), warehDelivTask.getUnitId(), totStb.getStbNum());
                //修改出库任务执行次数
                if ("T".equals(warehDelivTask.getMultiImpl())) {
                    //多次执行查询用原单和非撤销查询生成的出库单数量
                    warehDelivTaskExternalCiteService.renewalTaskImplTimes(warehDelivTask);
                }
//            }
        }
        //添加库存单明细
        if (stbDtls != null && stbDtls.size() > 0) {
            stbDtlService.insertStbDtlList(stbDtls);
        }
//        purchaseService.publicMethon(START_DELIVER, warehDelivTask.getTaskDocType(), warehDelivTask.getTaskDocUnitId(), warehDelivTask.getTaskDocNum(), null, null, null);
        return totStb.getStbNum();
    }

    /**
     * 封装集合
     *
     * @author HHe
     * @date 2019/10/13 11:58
     */
    public List<WarehDelivTaskVO> packList(List<WarehDelivTaskVO> warehDelivTaskVOs) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Map<String, Object> map = new HashMap<>();
        map.put("sys_unit", new String[]{"warehId", "rcvUnitId"});
        warehDelivTaskVOs = delivUtil.loadCodeName2VO2(map, warehDelivTaskVOs);
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("multiImpl", "BOOLEA");
        codeMap.put("suspended", "BOOLEA");
        codeMap.put("delivMode", "DELIV_MODE");
        codeMap.put("taskDocType", "DOC_TYPE");
        warehDelivTaskVOs = delivUtil.loadCPByCodeDtl(codeMap, warehDelivTaskVOs);
        return warehDelivTaskVOs;
    }

    /**
     * assistAPI
     * 封装出库任务到出库单、库存单
     */
    private GdnModel packDelivTask2StbGdn(WarehDelivTask delivTask) {
        //生成编号
        SysRefNumDtl refNumDtl = new SysRefNumDtl();
        refNumDtl.setRefNumId("STB_NUM");
        refNumDtl.setUnitId(delivTask.getUnitId());
        String docNum = delivUtil.createDocNum(refNumDtl);
        GdnModel gdnModel = new GdnModel();
        //出库单编号
        gdnModel.setGdnNum(docNum);
        //出库单编号
        gdnModel.setStbNum(docNum);
        //组织id
        gdnModel.setUnitId(delivTask.getUnitId());
        WarehDmode warehDmode = warehDmodeService.queryDmodeByWarehIdMode(delivTask.getWarehId(), delivTask.getDelivMode());
        if (warehDmode == null) {
            throw new ServiceException("201", "获取出库方式信息失败");
        }
        //即时结算("t","f")○
        gdnModel.setInstStl(warehDmode.getInstStl());
        //预定装箱("t","f")○
        gdnModel.setBoxSchd(warehDmode.getBoxSchd());
        //启用装箱("t","f")○
        gdnModel.setBoxReqd(warehDmode.getBoxReqd());
        //请求wareh，将数据封装入
        Wareh wareh = warehService.queryWarehByWarehId(delivTask.getWarehId());
        if (wareh == null) {
            throw new ServiceException("201", "获取仓库信息失败");
        }
        //货位管理("t","f")○
        gdnModel.setLocAdopted(wareh.getLocAdopted());
        //货位ID
        if (wareh.getDelivLocId() != null && !"".equals(wareh.getDelivLocId())) {
            gdnModel.setLocId(wareh.getDelivLocId().longValue());
        }
        //仓库Id○
        gdnModel.setWarehId(delivTask.getWarehId());
        //单据日期○
        gdnModel.setDocDate(delivTask.getTaskDocDate());
        //原始单据类别○
        gdnModel.setSrcDocType(delivTask.getTaskDocType());
        //单据组织id○
        gdnModel.setSrcDocUnitId(delivTask.getTaskDocUnitId());
        //原始单据编号○
        gdnModel.setSrcDocNum(delivTask.getTaskDocNum());
        //收货方Id
        gdnModel.setRcvUnitId(delivTask.getRcvUnitId());
        //收货方仓库Id
        gdnModel.setRcvWarehId(delivTask.getRcvWarehId());
        //判断任务单据类别调用不同的接口
//        Purchase purchase = purchaseService.selectBill(delivTask.getTaskDocType(), delivTask.getTaskDocUnitId().toString(), delivTask.getTaskDocNum());
        Purchase purchase = transferUtil.selectBill(delivTask.getTaskDocType(), delivTask.getTaskDocUnitId().toString(), delivTask.getTaskDocNum());
        if (purchase == null) {
            throw new ServiceException("201", "获取原单信息失败");
        }
        //合同号（此处如果原单是销售和同存购销单号）
        gdnModel.setCntrNum(purchase.getDtlNum());
        //启用配码○
        gdnModel.setBxiEnabled(purchase.getBxiEnabled());
        //预期总数量○
        gdnModel.setTtlExpdQty(purchase.getTtlQty());
        //预期总箱数○
        gdnModel.setTtlExpdBox(new BigDecimal(purchase.getTtlBox()));
        //总数量(总数量等于原始单据总数量)○
        gdnModel.setTtlQty(purchase.getTtlQty());
        //总箱数(同总数量一样)○
        gdnModel.setTtlBox(new BigDecimal(purchase.getTtlBox()));
        //总金额○
        gdnModel.setTtlVal(purchase.getTtlVal());
        //总税款○
        gdnModel.setTtlTax(purchase.getTtlTax());
        //总市值○
        gdnModel.setTtlMkv(purchase.getTtlMkv());
        //桥接方式○
        gdnModel.setBrdgMode(purchase.getBrdgMode());
        //中转方id○
        if (purchase.getTranUnitId() != null && !"".equals(purchase.getTranUnitId())) {
            gdnModel.setTranUnitId(Long.valueOf(purchase.getTranUnitId()));
        }
        //出入库类别（D）○
        gdnModel.setDrType("D");
        //约定会计日期○
        gdnModel.setFsclDateAptd("F");
        //改变成本○
        gdnModel.setCostChg("F");
        //已生效○
        gdnModel.setEffective("F");
        //挂起○
        gdnModel.setSuspended("F");
        //撤销("t","f")○
        gdnModel.setCancelled("F");
        //已冲单("t","f")○
        gdnModel.setReversed("F");
        //是否冲单("t","f")○
        gdnModel.setIsRev("F");
        //出库方式
        gdnModel.setDelivMode(delivTask.getDelivMode());

        //备注
        gdnModel.setRemarks(delivTask.getRemarks());
        //明细集合
//        stbGdnVO.setStbDtlVos(purchase.getStbDtlVos());
        return gdnModel;
    }

}
