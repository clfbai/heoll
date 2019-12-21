package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysPrsnl;
import com.boyu.erp.platform.usercenter.entity.system.SysPrsnlOwner;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysPrsnlOwnerMapper;
import com.boyu.erp.platform.usercenter.model.system.OwnerPrsnlModel;
import com.boyu.erp.platform.usercenter.service.system.SysPrsnlOwnerSerivce;
import com.boyu.erp.platform.usercenter.service.system.SysPrsnlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: boyu-platform
 * @description: 人员属主接口实现类
 * @author: clf
 * @create: 2019-05-20 11:38
 */
@Slf4j
@Service
@Transactional
public class SysPrsnlOwnerSerivceImpl implements SysPrsnlOwnerSerivce {
    @Autowired
    private SysPrsnlOwnerMapper prsnlOwnerMapper;
    @Autowired
    private SysPrsnlService sysPrsnlService;
    @Override
    @Transactional(readOnly = true)
    public SysPrsnlOwner findById(SysPrsnlOwner prsnlOwner) {
        return prsnlOwnerMapper.selectByPrimaryKey(prsnlOwner);
    }
    /**
     * 查询封装人员表和owner表集合
     * @author HHe
     * @date 2019/9/30 12:02
     */
    @Override
    public List<OwnerPrsnlModel> packNumAndName2List(List<OwnerPrsnlModel> ownerPrsnlModelList) {
        Set<Long> prsnlIds = new HashSet<>();
        for (OwnerPrsnlModel ownerPrsnlModel : ownerPrsnlModelList) {
            prsnlIds.add(ownerPrsnlModel.getPrsnlId());
        }
        List<SysPrsnl> sysPrsnls = sysPrsnlService.queryPrsnlByIds(prsnlIds);
        //sys_prsnl_owner查询num
        List<OwnerPrsnlModel> ownerPrsnlModels = prsnlOwnerMapper.queryListByOwnerAndPrsnl(ownerPrsnlModelList);
        //将数据封装到ownerUnitModelList中
        for (OwnerPrsnlModel ownerPrsnlModel : ownerPrsnlModelList) {
            for (SysPrsnl sysPrsnl : sysPrsnls) {
                if(sysPrsnl.getPrsnlId().equals(ownerPrsnlModel.getPrsnlId())){
                    BeanUtils.copyProperties(sysPrsnl,ownerPrsnlModel,"ownerId","prsnlId","prsnlNum");
                    break;
                }
            }
            for (OwnerPrsnlModel prsnlModel : ownerPrsnlModels) {
                if(prsnlModel.getPrsnlId().equals(ownerPrsnlModel.getPrsnlId()) && prsnlModel.getOwnerId().equals(ownerPrsnlModel.getOwnerId())){
                    ownerPrsnlModel.setPrsnlNum(prsnlModel.getPrsnlNum());
                    break;
                }
            }
        }
        return ownerPrsnlModelList;
//        //用子查询查询出对应owner和unid的对象；
//        ownerPrsnlModelList = prsnlOwnerMapper.queryListByOwnerAndPrsnl(ownerPrsnlModelList);
//        //将其中的人员Id封装成集合到人员表中查询详情；
//        Set<Long> prsnlIds = new TreeSet<>();
//        ownerPrsnlModelList.forEach(s->prsnlIds.add(s.getPrsnlId()));
//        //人员表查询集合对象
//        List<SysPrsnl> sysPrsnls = sysPrsnlService.queryPrsnlByIds(prsnlIds);
//        for (SysPrsnl sysPrsnl : sysPrsnls) {
//            for (OwnerPrsnlModel ownerPrsnlModel : ownerPrsnlModelList) {
//                if (sysPrsnl.getPrsnlId().equals(ownerPrsnlModel.getPrsnlId())){
//                    BeanUtils.copyProperties(sysPrsnl,ownerPrsnlModel,"ownerId","prsnlNum","prsnlId");
//                }
//            }
//        }
    }
}
