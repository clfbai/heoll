package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.TPOS.model.EntryOrderModel;
import com.boyu.erp.platform.usercenter.TPOS.service.WmsErpGoDowmWarehServicer;
import com.boyu.erp.platform.usercenter.TPOS.service.WmsErpNumService;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Grn;
import com.boyu.erp.platform.usercenter.entity.warehouse.Money;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRmode;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.GrnMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehRcvTaskMapper;
import com.boyu.erp.platform.usercenter.model.wareh.GrnDtlModel;
import com.boyu.erp.platform.usercenter.model.wareh.WarehRcvTaskModel;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.warehouse.GrnService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehRcvTaskSerivce;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehRmodeService;
import com.boyu.erp.platform.usercenter.utils.DateUtil;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.warehouse.WarehRcvTaskUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehRcvTaskDtlVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehRcvTaskVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 入库任务服务
 * @author: CLF
 * @date: 2019/7/15 10:51
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class WarehRcvTaskSerivceImpl implements WarehRcvTaskSerivce {


    private String api = "entryorder.create";
    @Autowired
    private ParameterString parameterString;
    @Autowired
    private WarehRcvTaskUtils warehRcvTaskUtils;
    @Autowired
    private WmsErpGoDowmWarehServicer wmsErpGoDowmWarehServicer;
    @Autowired
    private WmsErpNumService wmsErpNumService;
    @Autowired
    private WarehRmodeService warehRmodeService;
    @Autowired
    private WarehRcvTaskMapper warehRcvTaskMapper;
    @Autowired
    private GrnMapper grnMapper;
    @Autowired
    private GrnService grnService;
    @Autowired
    private SysParameterMapper sysParameterMapper;
    @Autowired
    private PurchaseService purchaseService;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<WarehRcvTaskVo> getWarehRcvTask(Integer page, Integer size, WarehRcvTaskModel model, SysUser user) {
        PageInfo<WarehRcvTaskVo> pageInfo;
        PageHelper.startPage(page, size);
        List<WarehRcvTaskVo> list = warehRcvTaskMapper.getAllRcvTask(model);
        pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 功能描述:  入库任务明细查询
     * 这里是根据采购单、退购单等直接获取相应的明细 暂时待完善
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/24 17:01
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<WarehRcvTaskDtlVo> getWarehRcvTaskDtl(Integer page, Integer size, WarehRcvTaskModel vo, SysUser sessionSysUser) {
        return null;
    }

    /**
     * 功能描述: 增加入库任务1
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/8 9:56
     */
    @Override
    public int addWarehRcvTask(WarehRcvTask model, SysUser user) throws Exception {
        log.info("推送或生成的mode   " + model);
        model.setMultiImpl(CommonFainl.FALSE);
        model.setSuspended(CommonFainl.FALSE);
        model.setImplTimes(0L);
        model.setJoinTime(DateUtil.dateToString(new Date()));
        model.setSuspended(CommonFainl.FALSE);
        //判断是否有入库仓库
        if (model.getWarehId() == null) {
            model.setIsStb(CommonFainl.FALSE);
            return warehRcvTaskMapper.insertWarehRcvTask(model);
        }
        return commWarehTack(model, user);
    }


    /**
     * 功能描述:  入库任务点击生成入库单
     * 1. 获取原单据信息
     * 2. 查询入库字段默认分拣、验收等 核对信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/31 14:33
     */
    @Override
    public int WarehRcvTaskGrn(WarehRcvTask model, SysUser user) throws Exception {
        String grnNum = model.getGrnNum();
        //已经增加过预入库单
        if (StringUtils.isNotBlank(grnNum)) {
            //删除原单据
            grnService.delete(new Grn(model.getUnitId(), grnNum));
        }
        GrnDtlModel grnDtlModel = warehRcvTaskUtils.addModel(model, user, CommonFainl.TRUE);
        grnDtlModel.getGrn().setShowDoc(CommonFainl.TRUE);
        grnDtlModel.getGrn().setProgress("PG");
        model = grnDtlModel.getWarehRcvTask();
        model.setIsStb(CommonFainl.TRUE);
        model.setGrnNum(grnDtlModel.getGrn().getGrnNum());
        //判断是否存在C-WMS 关联单 存在删除单据 推送取消发货
        wmsErpNumService.deleteCwmsGrn(model, "入库单取消", user);
        //通知采购开始收货 这里没有判断单据类型因为目前所有任务来至于采购模块
        purchaseService.publicMethon("startReceive", model.getTaskDocType(), model.getTaskDocUnitId(),
                model.getTaskDocNum(), model.getUnitId(), model.getTaskDocNum(), user);
        return grnService.addGrn(grnDtlModel, user);
    }


    /**
     * 修改入库任务是否显示
     */
    @Override
    public int updateWarehRcvTask(WarehRcvTask warehRcvTask) {
        return warehRcvTaskMapper.updateWarehRcvTask(warehRcvTask);
    }

    /**
     * 功能描述: 前端修改入库仓库
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 11:34
     */
    @Override
    public int webUpdateWarehRcvTask(WarehRcvTask warehRcvTask, SysUser user) throws Exception {
        return commWarehTack(warehRcvTask, user);
    }

    /**
     * 功能描述: 撤销入库任务
     * 主要给后台 采购模块调用
     *
     * @param warehRcvTask
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 14:41
     */
    @Override
    public int deregisterWarehRcvTask(WarehRcvTask warehRcvTask, SysUser user) throws Exception {
        warehRcvTask = warehRcvTaskMapper.selectWarehRcvTask(warehRcvTask);
        if (warehRcvTask != null) {
            if (StringUtils.isNotBlank(warehRcvTask.getGrnNum())) {
                //已有入库单
                Grn grn = grnMapper.selectByPrimaryKey(new Grn(warehRcvTask.getUnitId(), warehRcvTask.getGrnNum()));
                if (grn != null) {
                    if (grn.getShowDoc().equalsIgnoreCase(CommonFainl.TRUE)) {
                        throw new ServiceException("403", "入库单已生效不能删除入库任务");
                    } else {
                        //删除所有原单据
                        grnService.delete(grn);
                    }
                }

                    wmsErpNumService.deleteCwmsGrn(warehRcvTask, "入库单取消", user);

            }
            //删除任务
            return warehRcvTaskMapper.deleteWarehRcvTask(warehRcvTask);
        }
        return 0;
    }

    /**
     * 功能描述:  根据组织和单据号 查询单据
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/1 17:49
     */
    @Override
    public WarehRcvTask selectWarehRcvTask(WarehRcvTask warehRcvTask) {
        return warehRcvTaskMapper.selectNum(warehRcvTask);
    }

    /**
     * 组合模式屏蔽字段
     */
    @Override
    public List<PurKeyValue> selectWarehRcvTaskShield(int group) throws ServiceException {
        SysParameter parameter = null;
        if (group == 1) {
            //发货方
            parameter = sysParameterMapper.findById("rcvTaskConsigner");
        }
        if (group == 2) {
            //发货仓库
            parameter = sysParameterMapper.findById("rcvWarehTaskConsigner");
        }
        if (group > 0) {
            if (parameter == null) {
                throw new ServiceException("403", "请检查入库任务屏蔽参数是否存在,参数Id:rcvTaskConsigner,参数Id:rcvWarehTaskConsigner.");
            }
            return creatList(parameter.getParmVal());
        }
        return new ArrayList<PurKeyValue>();
    }

    /**
     * 查询总金额总数量
     */
    @Override
    public Money selectWarehRcvTaskMoney(WarehRcvTaskModel model) {
        Money money = warehRcvTaskMapper.selectRcvTaskMoney(model);
        if (money == null) {
            money.setTtlQty("0");
            money.setTtlVal("0");
        }
        return money;
    }


    /**
     * 组合模式屏蔽字段
     */
    public List<PurKeyValue> creatList(String str) {
        List<PurKeyValue> list = new ArrayList<>();
        if (str.indexOf(",") > 0 && str.indexOf("=") > 0) {
            String[] s = str.split(",");
            for (String sys : s) {
                String[] key = sys.split("=");
                PurKeyValue keyValue = new PurKeyValue(key[0], key[1]);
                list.add(keyValue);
            }
        }
        return list;
    }

    /**
     * 入库任务生成入库单 公共方法
     */
    public int commWarehTack(WarehRcvTask model, SysUser user) throws Exception {
        //根据仓库入库方式判断是否推送入库单
        WarehRmode warehRmode = warehRmodeService.selectWarehRmode(model.getWarehId(), model.getRcvMode());
        if (warehRmode == null) {
            throw new ServiceException("403", "没有查询到仓库的入库方式");
        }
        //自动执行
        if (CommonFainl.TRUE.equals(warehRmode.getAutoExec())) {
            GrnDtlModel grnDtlModel = warehRcvTaskUtils.addModel(model, user, CommonFainl.TRUE);
            grnDtlModel.getGrn().setShowDoc(CommonFainl.TRUE);
            grnDtlModel.getGrn().setProgress("RD");
            model = grnDtlModel.getWarehRcvTask();
            model.setIsStb(CommonFainl.TRUE);
            grnService.addGrn(grnDtlModel, user);
            //入库
            grnService.warehouSing(model.getUnitId(), grnDtlModel.getGrn().getGrnNum(), user);
        } else {
            //仓库是否上传
            boolean boo = warehRcvTaskUtils.upWMS(model.getWarehId());
            //不自动执行且无需上传
            if (CommonFainl.FALSE.equals(warehRmode.getAutoExec()) && !boo) {
                model.setIsStb(CommonFainl.FALSE);
                return warehRcvTaskMapper.insertWarehRcvTask(model);
            }
            //不自动执行且需要上传
            if (CommonFainl.FALSE.equals(warehRmode.getAutoExec()) && boo) {
                GrnDtlModel grnDtlModel2 = warehRcvTaskUtils.addModel(model, user, CommonFainl.FALSE);
                grnDtlModel2.getGrn().setShowDoc(CommonFainl.FALSE);
                //生成对象，生成对象的内部会添加对应关系
                EntryOrderModel entryOrderModel = wmsErpGoDowmWarehServicer.createrEntryOrder(grnDtlModel2, user);
                //上传开关
                boolean cwms = parameterString.UploginCwms();
                log.info("cwms" + cwms);
                if (cwms) {
                    log.info("推送C-WMS平台: " + entryOrderModel);
                    //推送到C-WMS
                    wmsErpGoDowmWarehServicer.createEntryOrderPostURL(entryOrderModel, user);
                }
                //添加入库单、库存单、库存明细
                grnService.addGrn(grnDtlModel2, user);
                grnDtlModel2.getStb().setEffective(CommonFainl.FALSE);
                model = grnDtlModel2.getWarehRcvTask();
                //因为还没有入库单确认，看不到入库单
                model.setIsStb(CommonFainl.FALSE);
            }
        }
        return warehRcvTaskMapper.insertWarehRcvTask(model);
    }


}
