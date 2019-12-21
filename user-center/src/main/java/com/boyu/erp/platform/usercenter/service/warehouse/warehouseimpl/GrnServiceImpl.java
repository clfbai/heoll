package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.TPOS.common.confirm.WarehSingConfirm;
import com.boyu.erp.platform.usercenter.TPOS.entity.WmsErpNum;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUserMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.GrnMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.StbBoxMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.StbDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.StbMapper;
import com.boyu.erp.platform.usercenter.model.batch.BatchModel;
import com.boyu.erp.platform.usercenter.model.batch.WarehBatchModel;
import com.boyu.erp.platform.usercenter.model.wareh.GrnDtlModel;
import com.boyu.erp.platform.usercenter.model.wareh.GrnModel;
import com.boyu.erp.platform.usercenter.model.wareh.StkCostModel;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.warehouse.*;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.StbUtil;
import com.boyu.erp.platform.usercenter.utils.warehouse.WarehSingUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnBaseDtlVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnGoodsDtl;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehSrcInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: boyu-platform
 * @description: 入库单接口实现
 * @author: ck
 * @create: 2019-07-01 15:36
 */
@Slf4j
@Service
@Transactional
public class GrnServiceImpl implements GrnService {
    @Autowired
    private GrnMapper grnMapper;
    @Autowired
    private StbMapper stbMapper;
    @Autowired
    private StbDtlMapper stbDtlMapper;
    @Autowired
    private StbBoxMapper stbBoxMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private StbUtil stbUtil;
    @Autowired
    private WarehSingUtils singUtils;
    @Autowired
    private StkCostService stkCostService;
    @Autowired
    private WarehCostService warehCostService;
    @Autowired
    private WarehStkService warehStkService;
    @Autowired
    private UnitBatchSerivce unitBatchSerivce;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private UnitBatchNumService unitBatchNumService;

    @Override
    public Grn selectGrn(Grn grn) {
        return grnMapper.selectByPrimaryKey(grn);
    }

    /**
     * 获取入库单列表
     *
     * @param page
     * @param size
     * @param grnVo
     * @return
     * @throws ServiceException
     */
    @Override
    public PageInfo<GrnVo> getGrnList(Integer page, Integer size, GrnModel grnVo) throws ServiceException {
        PageHelper.startPage(page, size);
        List<GrnVo> list = grnMapper.getGrnAll(grnVo);
        PageInfo<GrnVo> pageInfo = new PageInfo<GrnVo>(list);
        return pageInfo;
    }


    /**
     * 功能描述:  入库单 商品明细查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/30 15:22
     */
    @Override
    @Transactional(readOnly = true)
    public List<GrnGoodsDtl> getStbGoodsDtl(Grn grn) throws ServiceException {
        return grnMapper.getGoods(grn);
    }

    /**
     * 功能描述:  入库单 基本明细查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/30 15:22
     */
    @Override
    @Deprecated
    @Transactional(readOnly = true)
    public GrnBaseDtlVo getStbBaseDtl(Grn grn) throws ServiceException {
        return grnMapper.getStbBaseDtl(grn);
    }

    /**
     * 功能描述: 入库单页面增加入库单(手动填写 或者手动导入单据)
     *
     * @param model
     * @return:
     * @auther: CLF
     * @date: 2019/7/31 10:50
     */
    @Override
    public int addGrnDtl(GrnDtlModel model, SysUser user) throws ServiceException {

        if (CollectionUtils.isEmpty(model.getList())) {
            if (model.getGrn().getGrnNum() == null) {
                String grnNum = stbUtil.generateGrnNum(model.getWarehRcvTask().getUnitId());
            }
            int a;
            //增加入库单
            model.getGrn().setRcvTime(new Date());
            a = grnMapper.insertGrn(model.getGrn());
            //增加入库任务
            //单据日期去当前生成时间
            model.getStb().setDocDate(new Date());
            model.getStb().setOpTime(new Date());
            model.getStb().setOprId(user.getUserId());
            a += stbMapper.insertStb(model.getStb());
            //增加入库明细
            if (CollectionUtils.isNotEmpty(model.getStbDtls())) {
                model.getStbDtls().parallelStream().forEach(s -> stbDtlMapper.insertStbDtl(s));
            }
        }
        return 0;
    }

    /**
     * 功能描述:  通过入库单编号和组织Id 查询入库单
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/1 16:04
     */
    @Override
    public Grn getGrnId(Grn grnNum) {
        return grnMapper.getGrnById(grnNum);
    }

    /**
     * 功能描述: 单个入库单入库
     * 1.增加库存
     * 2.增加批次
     *
     * @param unitId 单据组织Id
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 15:42
     */

    @Override
    public int warehouSing(Long unitId, String grnNum, SysUser user) throws Exception {
        Grn key = new Grn();
        Stb stbKey = new Stb();
        stbKey.setUnitId(unitId);
        stbKey.setStbNum(grnNum);
        key.setUnitId(unitId);
        key.setGrnNum(grnNum);
        Grn grn = grnMapper.getGrnById(key);
        Stb stb = stbMapper.selectByPrimaryKey(stbKey);
        stb.setEffective(CommonFainl.TRUE);
        //获取是否全取反
        boolean b = false;
        //所有实际数量不为0的明细 批次需要
        List<StbDtl> stbDtlList = stbDtlMapper.queryStbDtlsByNumAndUnit(grnNum, unitId).stream().filter(t -> t.getQty().compareTo(new BigDecimal("0")) != 0).collect(Collectors.toList());
        //增加库存集合
        List<StbDtl> sts = this.grnCommon(grn, stb, stbDtlList, b);
        int a = 0;
        if (CollectionUtils.isNotEmpty(sts)) {
            List<WarehStk> warehStks = singUtils.isViefWarehCost(sts, stb.getWarehId());
            //修改库存
            log.info("入库时库存数量: " + warehStks);
            a = warehStkService.updateAddWarehStkList(warehStks);
        }
        //原始单据信息不为空
        WarehSrcInfoVo purchase = purchaseService.selectWarehSrc(stb.getSrcDocType(), stb.getUnitId() + "", stb.getSrcDocNum());
        WarehBatchModel warehBatchModel = this.crateWarehBatchModel(purchase, stbDtlList);
        List<BatchModel> batchModels = unitBatchSerivce.createBatch(warehBatchModel);
        //生成批次
        if (CollectionUtils.isNotEmpty(batchModels)) {
            for (BatchModel s : batchModels) {
                s.getUnitBatch().setDocNum(grnNum);
                log.info("{入库单生成的批次信息} " + s);
                log.info("{入库单生成 UnitBatch} " + s.getUnitBatch());
                unitBatchSerivce.addUnitBatch(s, "R", user);
            }
        }
        //修改库存单生效
        stbMapper.updateStb(stb);
        //退销合收货、采购合同收货
        if (grn.getRcvMode().equalsIgnoreCase("RTRT") || grn.getRcvMode().equalsIgnoreCase("SLRT") || grn.getRcvMode().equalsIgnoreCase("PURC")) {
            purchaseService.publicMethon("receive", stb.getSrcDocType(), stb.getSrcDocUnitId(), stb.getSrcDocNum(), stb.getUnitId(), stb.getStbNum(), user);
        }
        return a;
    }

    /**
     * 功能描述: 取消单个单据入库
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 19:30
     */
    @Override
    public int warehouReverse(Grn model, SysUser user) throws Exception {
        Stb stb = stbMapper.selectByPrimaryKey(new Stb(model.getUnitId(), model.getGrnNum()));
        Grn grn = grnMapper.selectByPrimaryKey(new Grn(model.getUnitId(), model.getGrnNum()));
        if (stb == null || grn == null) {
            throw new ServiceException("403", "入库单不能为空");
        }
        int a = 0;
        List<StbDtl> stbDtlList = new ArrayList<>();
        if (stb.getEffective().equalsIgnoreCase(CommonFainl.TRUE)) {
            //获取是否全取反
            boolean b = true;
            //所有实际数量不为0的明细 批次需要
            stbDtlList = stbDtlMapper.queryStbDtlsByNumAndUnit(model.getGrnNum(), model.getUnitId()).stream().filter(t -> t.getQty().compareTo(new BigDecimal("0")) != 0).collect(Collectors.toList());
            List<StbDtl> maxList = grnCommon(model, stb, stbDtlList, b);
            //变为负数
            maxList.stream().forEach(s -> s.setQty(s.getQty().negate()));
            //判断返回那些需要 修改那些需要增加的库存
            List<WarehStk> warehStks = singUtils.isViefWarehCost(maxList, stb.getWarehId());
            //修改库存
            a = warehStkService.updateAddWarehStkList(warehStks);
        }

        //最后通知取消收货
        grnReverseReceive(stb, grn, user);
        //批次还原
        if (CollectionUtils.isNotEmpty(stbDtlList)) {
            //原始单据信息不为空
            WarehSrcInfoVo purchase = purchaseService.selectWarehSrc(stb.getSrcDocType(), stb.getUnitId() + "", stb.getSrcDocNum());
            if (purchase == null) {
                throw new ServiceException("403", "原始单信息为空");
            }
            //供应商不介入
            if (purchase.getVenderId() == null) {
                purchase.setVenderId(grn.getUnitId());
            }
            log.info("供应商Id:" + purchase.getVenderId());
            UnitBatchNum num = new UnitBatchNum(grn.getUnitId(), grn.getGrnNum(), "R");
            num.setVendeeId(grn.getUnitId());
            num.setVenderId(purchase.getVenderId());
            //还原批次
            a += unitBatchNumService.reverseReceive(num, stbDtlList.stream().map(StbDtl::getProdId).collect(Collectors.toList()), user);
        }
        return a;
    }

    /**
     * C-WMS 入库单确认接口
     * 可以返回差异结果集
     */
    @Override
    public void warehSingConfirm(WarehSingConfirm warehSingConfirm) throws Exception {
        Map<String, Object> map = singUtils.isWarehSingConfirm(warehSingConfirm);
        //单据类型
        map.put("entryOrderType", warehSingConfirm.getEntryOrder().getEntryOrderType());
        //业务编码去重
        map.put("outBizCode", warehSingConfirm.getEntryOrder().getOutBizCode());
        map.put("totalOrderLines", warehSingConfirm.getEntryOrder().getTotalOrderLines());
        map.put("confirmType", warehSingConfirm.getEntryOrder().getConfirmType());
        //判断单据最终状态
        map.put("status", warehSingConfirm.getEntryOrder().getStatus());
        singUtils.confirmWarehSing(map);
        WmsErpNum wmsErp = (WmsErpNum) map.get("wmsErpNum");
        Grn grn = grnMapper.getGrnById(new Grn((Long) map.get("unitId"), wmsErp.getOriginNum()));
        Stb stb = stbMapper.selectByPrimaryKey(new Stb((Long) map.get("unitId"), wmsErp.getOriginNum()));
        List<StbDtl> stbDtls = (List<StbDtl>) map.get("updateStbDtl");
        stb.setEffective(CommonFainl.TRUE);
        grn.setProgress("TD");
        grnMapper.updateGrn(grn);
        stbMapper.updateStb(stb);
        stbDtls.forEach(s -> stbDtlMapper.updateStbDtl(s));
        //获取是否全取反  true 是
        boolean b = false;
        List<StbDtl> stbDtlList = stbDtls.stream().filter(t -> t.getQty().compareTo(new BigDecimal("0")) != 0).collect(Collectors.toList());
        //增加库存集合
        List<StbDtl> sts = this.grnCommon(grn, stb, stbDtlList, b);
        if (CollectionUtils.isNotEmpty(sts)) {
            List<WarehStk> warehStks = singUtils.isViefWarehCost(sts, stb.getWarehId());
            //修改库存
            warehStkService.updateAddWarehStkList(warehStks);
        }
        SysUser user = userMapper.selectUnitAdmin((Long) map.get("unitId"));
        if (user == null) {
            user = userMapper.selectUnitAdmin(1L);
        }
        log.info("{user}" + user.getUserId());
        //原始单据信息不为空
        WarehSrcInfoVo purchase = purchaseService.selectWarehSrc(stb.getSrcDocType(), stb.getUnitId() + "", stb.getSrcDocNum());
        WarehBatchModel warehBatchModel = crateWarehBatchModel(purchase, stbDtlList);
        List<BatchModel> batchModels = unitBatchSerivce.createBatch(warehBatchModel);
        //生成批次
        if (CollectionUtils.isNotEmpty(batchModels)) {
            for (BatchModel s : batchModels) {
                log.info("批次信息" + s);
                unitBatchSerivce.addUnitBatch(s, "R", user);
            }
        }
        log.info("：  确认完成");
    }

    @Override
    public Money selectMoney(GrnModel grnModel) {
        Money m = grnMapper.getGrnMoney(grnModel);
        if (m == null) {
            m = new Money();
            m.setTtlQty("0");
            m.setTtlVal("0");
        }
        return m;
    }

    /**
     * 入库任务调用
     */
    @Override
    public int addGrn(GrnDtlModel model, SysUser user) throws ServiceException {
        /**
         * 单据类别 这里无需判断 通过前台接口调用相应明细封装到同一个数据模型
         * PUO 采购单 获取采购单原单据信息
         * PUC 采购合同
         * */
        if (model.getStbDtls().isEmpty()) {
            throw new ServiceException("403", "请添加入库单明细");
        }
        log.info("生成入库单：" + model.getGrn());
        log.info("生成库存库单：" + model.getStb());
        log.info("生成库存库单明细：" + model.getStbDtls());
        int a = grnMapper.insertGrn(model.getGrn());
        a += stbMapper.insertStb(model.getStb());
        model.getStbDtls().stream().forEach(s -> stbDtlMapper.insertStbDtl(s));
        return a;
    }

    /**
     * 功能描述: 判断入库单基本及明细能否修改
     *
     * @param grn
     * @return
     * @auther: CLF
     * @date: 2019/11/19 16:05
     */
    @Override
    public Boolean isUpdateGrn(Grn grn) {
        //删除、修改、增加单据状态要求：progress IN (C,PG) && suspended = F && cancelled = F &&总库存单编号 ldgStbNum == NULL。
        Stb stb = new Stb();
        stb.setUnitId(grn.getUnitId());
        stb.setStbNum(grn.getGrnNum());
        stb = stbMapper.selectByPrimaryKey(stb);
        if (stb == null) {
            return false;
        }
        if (grn.getProgress().equalsIgnoreCase("C") || grn.getProgress().equalsIgnoreCase("Pg")
                && stb.getSuspended().equalsIgnoreCase(CommonFainl.FALSE) && StringUtils.NullEmpty(stb.getLdgStbNum())) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述: 入库单按钮初始化
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 17:43
     */
    @Override
    public List<OperationVo> initButtons(Grn model) {
        Grn grn = grnMapper.selectByPrimaryKey(model);
        Stb stb = stbMapper.selectByPrimaryKey(new Stb(model.getUnitId(), model.getGrnNum()));
        if (grn == null) {
            throw new ServiceException("403", "入库单不存在");
        }
        if (stb == null) {
            throw new ServiceException("403", "库存单不存在");
        }
        return creatGrnButton(grn, stb);
    }

    /**
     * 功能描述: 入库单改变单据状态
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 17:43
     */
    @Override
    public int changeStatuss(Grn model) {
        return grnMapper.updateGrn(model);
    }


    private List<OperationVo> creatGrnButton(Grn grn, Stb stb) {
        List<OperationVo> list = new ArrayList<>();
        //确认
        String url = "/warehouse/grn/confirm";
        //挂起
        String url2 = "/warehouse/grn/suspend";
        //作废
        String url3 = "/warehouse/grn/abolish";
        //重做
        String url4 = "/warehouse/grn/redo";
        //入库
        String url5 = "/warehouse/grn/receive";
        //取消入库
        String url6 = "/warehouse/grn/reverseReceive";
        //恢复
        String url7 = "/warehouse/grn/resume";
        //删除
        String url8 = "/warehouse/grn/deleteGrn";
        OperationVo vo1 = new OperationVo("确认", "F", url);
        OperationVo vo2 = new OperationVo("挂起", "F", url2);
        OperationVo vo3 = new OperationVo("作废", "F", url3);
        OperationVo vo4 = new OperationVo("重做", "F", url4);
        OperationVo vo5 = new OperationVo("入库", "F", url5);
        OperationVo vo6 = new OperationVo("取消入库", "F", url6);
        OperationVo vo7 = new OperationVo("恢复", "F", url7);
        // OperationVo vo8 = new OperationVo("删除", "F", url8);
        //以保存
        if ("pg".equalsIgnoreCase(grn.getProgress())) {
            vo1.setOperationStutas(CommonFainl.TRUE);
            vo2.setOperationStutas(CommonFainl.TRUE);
            vo3.setOperationStutas(CommonFainl.TRUE);
        }
        //确认
        if ("c".equalsIgnoreCase(grn.getProgress())) {
            vo2.setOperationStutas(CommonFainl.TRUE);
            vo3.setOperationStutas(CommonFainl.TRUE);
            vo4.setOperationStutas(CommonFainl.TRUE);
            vo5.setOperationStutas(CommonFainl.TRUE);
        }
        //入库
        if ("RD".equalsIgnoreCase(grn.getProgress())) {
            vo6.setOperationStutas(CommonFainl.TRUE);
        }
        //尚未挂起、已保存或确认状态下
        if ("F".equalsIgnoreCase(stb.getSuspended()) && ("pg".equalsIgnoreCase(grn.getProgress()) || "c".equalsIgnoreCase(grn.getProgress()))) {
            vo2.setOperationStutas(CommonFainl.TRUE);
            vo3.setOperationStutas(CommonFainl.TRUE);
            vo4.setOperationStutas(CommonFainl.TRUE);
        }
        //恢复
        if ("T".equalsIgnoreCase(stb.getSuspended())) {
            vo7.setOperationStutas(CommonFainl.TRUE);
            vo1.setOperationStutas(CommonFainl.FALSE);
            vo2.setOperationStutas(CommonFainl.FALSE);
            vo3.setOperationStutas(CommonFainl.FALSE);
            vo4.setOperationStutas(CommonFainl.FALSE);
            vo5.setOperationStutas(CommonFainl.FALSE);
            vo6.setOperationStutas(CommonFainl.FALSE);
        }
        list.add(vo2);
        list.add(vo1);
        list.add(vo3);
        list.add(vo4);
        list.add(vo5);
        list.add(vo6);
        list.add(vo7);
        //作废
        if ("T".equalsIgnoreCase(stb.getCancelled())) {
            list.stream().forEach(s -> s.setOperationStutas("F"));
        }
        return list;
    }


    /**
     * 删除入库单相关的库存单、库存单明细、库存原始单据等
     */
    @Override
    public int delete(Grn grn) throws ServiceException {
        //删除明细
        int i;
        StbDtl stbDtl = new StbDtl();
        stbDtl.setUnitId(grn.getUnitId());
        stbDtl.setStbNum(grn.getGrnNum());
        i = stbDtlMapper.deleteByPrimaryKey(stbDtl);
        //删除装箱
        StbBox stbBox = new StbBox();
        stbBox.setUnitId(grn.getUnitId());
        stbBox.setStbNum(grn.getGrnNum());
        i += stbBoxMapper.deleteByPrimaryKey(stbBox);
        //删除入库单
        i += grnMapper.deleteByPrimaryKey(grn);
        //删除库存单
        Stb stb = new Stb();
        stb.setUnitId(grn.getUnitId());
        stb.setStbNum(grn.getGrnNum());
        i += stbMapper.deleteByPrimaryKey(stb);
        return i;
    }

    @Override
    public int update(GrnVo grnVo) throws ServiceException {
        grnVo.setStbNum(grnVo.getGrnNum());
        //更新库存单
        stbMapper.updateByPrimaryKey(grnVo);
        Grn grn = new Grn();
        BeanUtils.copyProperties(grnVo, grn);
        return grnMapper.updateGrn(grn);
    }

    /**
     * 入库公共方法 取消入库时 一部分单据类型可以使用
     */
    public List<StbDtl> grnCommon(Grn grn, Stb stb, List<StbDtl> stbDtlList, Boolean b) {

        if (CollectionUtils.isNotEmpty(stbDtlList)) {
            //查询启用库存管理商品Id集合
            List<Long> prod = productService.selectStkAdopte(stbDtlList.stream().map(s -> s.getProdId()).collect(Collectors.toList()), CommonFainl.TRUE);
            List<StbDtl> filterStbdtl = stbDtlList;
            if (CollectionUtils.isNotEmpty(prod)) {
                //过滤出不需要计算库存和成本的商品
                filterStbdtl = stbDtlList.stream().filter(s -> prod.contains(s.getProdId())).collect(Collectors.toList());
                singUtils.wareRomde(grn, stb, filterStbdtl);
                //库存成本核算集合
                List<WarehCost> costs = singUtils.countWarehCost(stb.getWarehId(), grn.getUnitId(), filterStbdtl, b);
                //增加或修改
                warehCostService.addUpadteWarehCostList(costs);
                StkCostModel stkCostModel = new StkCostModel();
                stkCostModel.setWarehCosts(costs);
                stkCostModel.setStbDtls(stbDtlList);
                stkCostModel.setMode(grn.getRcvMode());
                //仓库ID
                stkCostModel.setWarehId(stb.getWarehId());
                //入库方式判断是否取反
                stkCostModel.setReversed(b);
                //组织成本核算对象
                List<StkCost> stkCosts = stkCostService.createStkCost(stkCostModel);
                log.info("修改的组织成本信息 :" + stkCosts);
                //增加或修改
                stkCostService.addUpdateStkcost(stkCosts, grn.getUnitId());
                return filterStbdtl;
            } else {
                return null;
            }
        }
        log.info("商品明细数量为0无需修改");
        return null;

    }


    public WarehBatchModel crateWarehBatchModel(WarehSrcInfoVo purchase, List<StbDtl> stbDtlList) {
        WarehBatchModel warehBatchModel = new WarehBatchModel();
        //log.info("原单据信息: " + purchase);
        if (purchase.getIntervene() == 1) {
            warehBatchModel.setIntervene(true);
        }
        if (purchase.getIntervene() == 0) {
            warehBatchModel.setIntervene(false);
        }
        warehBatchModel.setStbDtls(stbDtlList);
        //采购商
        warehBatchModel.setVendeeId(purchase.getRcvUnitId());
        //供应商
        warehBatchModel.setVenderId(purchase.getDelivUnitId());
        //入库单
        warehBatchModel.setType("R");
        log.info("{批次对象需要信息： }" + warehBatchModel);
        return warehBatchModel;
    }


    public void grnReverseReceive(Stb stb, Grn grn, SysUser user) throws Exception {
        //退销合同取消收货、采购合同取消收货
        if (grn.getRcvMode().equalsIgnoreCase("RTRT") || grn.getRcvMode().equalsIgnoreCase("SLRT") || grn.getRcvMode().equalsIgnoreCase("PURC")) {
            purchaseService.publicMethon("reverseReceive", stb.getSrcDocType(), stb.getSrcDocUnitId(), stb.getSrcDocNum(), stb.getUnitId(), stb.getStbNum(), user);
        }
    }


}
