package com.boyu.erp.platform.usercenter.utils.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.*;
import com.boyu.erp.platform.usercenter.entity.sales.Slc;
import com.boyu.erp.platform.usercenter.entity.sales.Slo;
import com.boyu.erp.platform.usercenter.entity.sales.Src;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.mapper.purchase.*;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SloMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SrcMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehDelivTaskService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehRcvTaskSerivce;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

/**
 * @program: boyu-platform_text
 * @description: 采购公共方法
 * @author: wz
 * @create: 2019-8-1 14:43
 */
@Slf4j
@Component
@Transactional
public class PurchaseUtils {

    @Autowired
    private PscMapper pscMapper;
    @Autowired
    private PucMapper pucMapper;
    @Autowired
    private WarehRcvTaskSerivce warehRcvTaskSerivce;
    @Autowired
    private SlcMapper slcMapper;
    @Autowired
    private WarehDelivTaskService warehDelivTaskService;
    @Autowired
    private SloMapper sloMapper;
    @Autowired
    private PsoMapper psoMapper;
    @Autowired
    private PuoMapper puoMapper;
    @Autowired
    private PrcMapper prcMapper;
    @Autowired
    private RtcMapper rtcMapper;
    @Autowired
    private SrcMapper srcMapper;

    /**
     * 登记入库任务
     * @param type
     * @param typeNum
     * @param user
     * @return
     */
    public int generate(String type, String typeNum, SysUser user)throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        WarehRcvTask task = new WarehRcvTask();
        if("PUC".equals(type)){
            Puc puc = pucMapper.selectByPscNum(typeNum);
            if(puc!=null){
                task.setRcvMode("PURC");
                Psc psc = pscMapper.selectByPrimaryKey(typeNum);
                task.setUnitId(psc.getVendeeId());
                task.setTaskDocUnitId(puc.getUnitId());
                task.setTaskDocNum(puc.getPucNum());
                task.setTaskDocDate(psc.getDocDate());
                if(StringUtils.isNotEmpty(psc.getVdeWarehId()+"")){
                    task.setWarehId(psc.getVdeWarehId());
                }
                task.setDelivUnitId(psc.getVenderId());
                if(StringUtils.isNotEmpty(psc.getVdrWarehId()+"")){
                    task.setDelivWarehId(psc.getVdrWarehId());
                }
                if(psc.getVdrInvd().equals("T")){
                    task.setTtlQty(psc.getTtlDelivQty().subtract(psc.getTtlRcvQty()).floatValue());
                    task.setTtlVal(psc.getTtlDelivVal().subtract(psc.getTtlRcvVal()).floatValue());
                }else{
                    task.setTtlQty(psc.getTtlQty().subtract(psc.getTtlRcvQty()).floatValue());
                    task.setTtlVal(psc.getTtlVal().subtract(psc.getTtlRcvVal()).floatValue());
                }
                task.setMultiImpl(psc.getMultiImpl());
                task.setRemarks(psc.getRemarks());
            }
        }else if("PUO".equals(type)){
            Puo puo = puoMapper.selectByPsoNun(typeNum);
            if(puo!=null){
                task.setRcvMode("PURC");
                Pso pso = psoMapper.selectByPrimaryKey(typeNum);
                Psc psc = pscMapper.selectByPrimaryKey(pso.getPscNum());
                task.setUnitId(psc.getVendeeId());
                task.setTaskDocUnitId(psc.getVendeeId());
                task.setTaskDocNum(puo.getPuoNum());
                task.setTaskDocDate(pso.getDocDate());
                if(StringUtils.isNotEmpty(pso.getRcvWarehId()+"")){
                    task.setWarehId(pso.getRcvWarehId().longValue());
                }
                task.setDelivUnitId(psc.getVenderId());
                if(StringUtils.isNotEmpty(pso.getDelivWarehId()+"")){
                    task.setDelivWarehId(pso.getDelivWarehId().longValue());
                }
                if(psc.getVdrInvd().equals("T")){
                    task.setTtlQty(pso.getTtlDelivQty().subtract(pso.getTtlRcvQty()).floatValue());
                    task.setTtlVal(pso.getTtlDelivVal().subtract(pso.getTtlDelivVal()).floatValue());
                }else{
                    task.setTtlQty(pso.getTtlQty().subtract(pso.getTtlRcvQty()).floatValue());
                    task.setTtlVal(pso.getTtlVal().subtract(pso.getTtlDelivVal()).floatValue());
                }
                task.setMultiImpl(psc.getMultiImpl());
                task.setRemarks(pso.getRemarks());
            }
        }else if("SRC".equals(type)){
            Src src = srcMapper.selectByRtcNum(typeNum);
            if(src!=null){
                task.setRcvMode("SLRT");
                Rtc rtc = rtcMapper.selectByPrimaryKey(typeNum);
                task.setUnitId(rtc.getVenderId());
                task.setTaskDocUnitId(src.getUnitId());
                task.setTaskDocNum(src.getSrcNum());
                task.setTaskDocDate(rtc.getDocDate());
                if(StringUtils.isNotEmpty(rtc.getVdeWarehId()+"")){
                    task.setWarehId(rtc.getVdeWarehId());
                }
                task.setDelivUnitId(rtc.getVendeeId());
                if(StringUtils.isNotEmpty(rtc.getVdrWarehId()+"")){
                    task.setDelivWarehId(rtc.getVdrWarehId());
                }
                if(rtc.getVdeInvd().equals("T")){
                    task.setTtlQty(rtc.getTtlDelivQty().subtract(rtc.getTtlRcvQty()).floatValue());
                    task.setTtlVal(rtc.getTtlDelivVal().subtract(rtc.getTtlRcvVal()).floatValue());
                }else{
                    task.setTtlQty(rtc.getTtlQty().subtract(rtc.getTtlRcvQty()).floatValue());
                    task.setTtlVal(rtc.getTtlVal().subtract(rtc.getTtlRcvVal()).floatValue());
                }
                task.setMultiImpl(rtc.getMultiImpl());
                task.setRemarks(rtc.getRemarks());
            }
        }
        task.setTaskDocType(type);
        task.setImplTimes(0L);
        return warehRcvTaskSerivce.addWarehRcvTask(task,user);
    }


    /**
     * 登记出库任务
     * @param type
     * @param typeNum
     * @param user
     * @return
     * @throws Exception
     */
    public int outStock (String type, String typeNum, SysUser user) throws Exception {
        WarehDelivTask task = new WarehDelivTask();
        if("SLC".equals(type)){
            Slc slc = slcMapper.selectByPscNum(typeNum);
            if(slc!=null){
                task.setDelivMode("SELL");
                Psc psc = pscMapper.selectByPrimaryKey(typeNum);
                task.setUnitId(psc.getVenderId());
                task.setTaskDocUnitId(slc.getUnitId().longValue());
                task.setTaskDocNum(slc.getSlcNum());
                task.setTaskDocDate(psc.getDocDate());
                if(StringUtils.isNotEmpty(psc.getVdrWarehId()+"")){
                    task.setWarehId(psc.getVdrWarehId());
                }
                task.setRcvUnitId(psc.getVendeeId());
                if(StringUtils.isNotEmpty(psc.getVdeWarehId()+"")){
                    task.setRcvWarehId(psc.getVdeWarehId());
                }
                task.setTtlQty(psc.getTtlQty().subtract(psc.getTtlDelivQty()).floatValue());
                task.setTtlVal(psc.getTtlVal().subtract(psc.getTtlDelivVal()).floatValue());
                task.setMultiImpl(psc.getMultiImpl());
                task.setRemarks(psc.getRemarks());
            }
        }else if("SLO".equals(type)){
            Slo slo = sloMapper.selectByPsoNum(typeNum);
            if(slo!=null){
                task.setDelivMode("SELL");
                Pso pso = psoMapper.selectByPrimaryKey(typeNum);
                Psc psc = pscMapper.selectByPrimaryKey(pso.getPscNum());
                task.setUnitId(psc.getVenderId());
                task.setTaskDocUnitId(slo.getUnitId());
                task.setTaskDocNum(slo.getSloNum());
                task.setTaskDocDate(pso.getDocDate());
                if(StringUtils.isNotEmpty(pso.getDelivWarehId()+"")){
                    task.setWarehId(pso.getDelivWarehId().longValue());
                }
                task.setRcvUnitId(psc.getVendeeId());
                if(StringUtils.isNotEmpty(pso.getRcvWarehId()+"")){
                    task.setRcvWarehId(pso.getRcvWarehId().longValue());
                }
                task.setTtlQty(pso.getTtlQty().floatValue());
                task.setTtlVal(pso.getTtlVal().floatValue());
                task.setMultiImpl(psc.getMultiImpl());
                task.setRemarks(pso.getRemarks());
            }
        }else if("PRC".equals(type)){
            Prc prc = prcMapper.selectByRtcNum(typeNum);
            if(prc!=null){
                task.setDelivMode("SELL");
                Rtc rtc = rtcMapper.selectByPrimaryKey(typeNum);
                task.setUnitId(rtc.getVendeeId());
                task.setTaskDocUnitId(prc.getUnitId());
                task.setTaskDocNum(prc.getPrcNum());
                task.setTaskDocDate(rtc.getDocDate());
                if(StringUtils.isNotEmpty(rtc.getVdeWarehId()+"")){
                    task.setWarehId(rtc.getVdeWarehId());
                }
                task.setRcvUnitId(rtc.getVenderId());
                if(StringUtils.isNotEmpty(rtc.getVdrWarehId()+"")){
                    task.setRcvWarehId(rtc.getVdrWarehId());
                }
                task.setTtlQty(rtc.getTtlQty().subtract(rtc.getTtlDelivQty()).floatValue());
                task.setTtlVal(rtc.getTtlVal().subtract(rtc.getTtlDelivVal()).floatValue());
                task.setMultiImpl(rtc.getMultiImpl());
                task.setRemarks(rtc.getRemarks());
            }
        }
        task.setTaskDocType(type);
        return warehDelivTaskService.addDelivTask(task,user);
    }



}
