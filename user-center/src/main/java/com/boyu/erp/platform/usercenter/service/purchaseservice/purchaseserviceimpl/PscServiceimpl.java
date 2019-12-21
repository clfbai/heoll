package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.entity.purchase.Psc;
import com.boyu.erp.platform.usercenter.entity.purchase.Puc;
import com.boyu.erp.platform.usercenter.mapper.purchase.PscMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PucMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscService;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname PucServiceimpl
 * @Description TODO
 * @Date 2019/6/18 19:20
 * @Created wz
 */
@Slf4j
@Service
@Transactional
public class PscServiceimpl implements PscService {

    @Autowired
    private PucMapper pucMapper;
    @Autowired
    private SlcMapper slcMapper;
    @Autowired
    private PscMapper pscMapper;

    /**
     * 采购
     * @param pscNum
     * @param type
     * @return
     */
    @Override
    public int updateTaskByP(String pscNum,String type) {
        List<PscVo> voList =  pucMapper.selectALL(new PscVo(pscNum));
        if(!voList.isEmpty()){
            PscVo psc = voList.get(0);
            if(psc.getImplByIns().equals("T")&&(psc.getProgress().equals("CK")||psc.getProgress().equals("DD"))
                    &&psc.getCancelled().equals("F")&&psc.getSuspended().equals("F")){
                if("1".equals(type)){
                    return pscMapper.updateTaskAdd(pscNum);
                }else{
                    return pscMapper.updateTaskReduce(pscNum);
                }
            }
        }

        return 0;
    }

    /**
     * 销售
     * @param pscNum
     * @param type
     * @return
     */
    @Override
    public int updateTaskByS(String pscNum, String type) {
        List<PscVo> voList =  slcMapper.selectALL(new PscVo(pscNum));
        if(!voList.isEmpty()){
            PscVo psc = voList.get(0);
            if(psc.getImplByIns().equals("T")&&(psc.getProgress().equals("CK")||psc.getProgress().equals("DD"))
                    &&psc.getCancelled().equals("F")&&psc.getSuspended().equals("F")){
                if("1".equals(type)){
                    return pscMapper.updateTaskAdd(pscNum);
                }else{
                    return pscMapper.updateTaskReduce(pscNum);
                }
            }
        }

        return 0;
    }


}
