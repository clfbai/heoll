package com.boyu.erp.platform.usercenter.service.caservice.caserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.basic.TranDiffEvt;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.TranDiffEvtMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.caservice.TranDiffEvtService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @program: TranDiffEvtServerImpl
 * @description: 收发差异
 * @author: wz
 * @create: 2019-9-12 10:19
 */
@Service
@Transactional
public class TranDiffEvtServerImpl implements TranDiffEvtService {

    @Autowired
    private TranDiffEvtMapper tranDiffEvtMapper;
    @Autowired
    private SysParameterMapper parameterMapper;

    //收发差异登记
    @Override
    public int register(TranDiffEvt tran) {
        SysParameter sysPar = parameterMapper.findById("TRANSIT_DIFFERENCE_ALLOWED");
        if(sysPar!=null && sysPar.getParmVal().equals("F")){
            throw new ServiceException(ResultCode.DB_FAILURE, "当前不允许存在收发差异！");
        }
        sysPar = parameterMapper.findById("TRANSIT_DIFFERENCE_EVENT_ENABLED");
        if(sysPar!=null && sysPar.getParmVal().equals("T")){
            tran.setJoinTime(new Date());
            tran.setProgress("PG");
            return tranDiffEvtMapper.insertSelective(tran);
        }
        return 0;
    }

    /**
     * 取消收发差异登记
     * @param tran
     * @return
     */
    @Override
    public int deregister(TranDiffEvt tran) {
        int type = 0;
        TranDiffEvt tranDiff = new TranDiffEvt(tran.getDelivUnitId(),tran.getDelivDocType(),tran.getDelivDocNum(),null,null,null);
        TranDiffEvt evt = tranDiffEvtMapper.selectByError(tranDiff);
        if(evt!=null){
            throw new ServiceException(ResultCode.DB_FAILURE, "当前状态下的收发差异无法注销！");
        }else{
            type += tranDiffEvtMapper.deleteByPrimaryKey(tranDiff);
        }
        tranDiff = new TranDiffEvt(null,null,null,tran.getRcvUnitId(),tran.getRcvDocType(),tran.getRcvDocNum());
        evt = tranDiffEvtMapper.selectByError(tranDiff);
        if(evt!=null){
            throw new ServiceException(ResultCode.DB_FAILURE, "当前状态下的收发差异无法注销！");
        }else{
            type+=tranDiffEvtMapper.deleteByPrimaryKey(tranDiff);
        }
        return type;
    }

}
