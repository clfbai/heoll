package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitOwner;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitOwnerMapper;
import com.boyu.erp.platform.usercenter.model.system.OwnerUnitModel;
import com.boyu.erp.platform.usercenter.service.system.SysUnitClsfSerivcer;
import com.boyu.erp.platform.usercenter.service.system.SysUnitOwnerSerivcer;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class SysUnitOwnerSerivcerImpl implements SysUnitOwnerSerivcer {
    @Autowired
    private SysUnitOwnerMapper unitOwnerMapper;
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private SysUnitClsfSerivcer sysUnitClsfSerivcer;

    @Override
    public int deleteUnitOwner(SysUnitOwner unitOwner) {
        return unitOwnerMapper.deleteUnitOwner(unitOwner);
    }

    @Override
    public int addSysUnitOwner(SysUnitOwner unitOwner) {
        return unitOwnerMapper.insertUnitOwner(unitOwner);
    }

    @Override
    public int updateSysUnitOwner(SysUnitOwner unitOwner) {
        return unitOwnerMapper.updateUnitOwner(unitOwner);
    }

    @Override
    public SysUnitOwner getUnitOwner(SysUnitOwner unitOwner) {
        return unitOwnerMapper.selectUnitOwner(unitOwner);
    }

    @Override
    public List<SysUnitOwner> getListUnitOwner(Long uitId) {
        return unitOwnerMapper.selectListUnitOwner(uitId);
    }

    /**
     * 将owner中的num和unit中name封装到集合中返回
     *
     * @author HHe
     * @date 2019/9/28 11:27
     */
    @Override
    public List<OwnerUnitModel> packNumAndName2List(List<OwnerUnitModel> ownerUnitModelList) {
        //sys_unit查询全部信息
        Set<Long> unitIds = new HashSet<>();
        for (OwnerUnitModel ownerUnitModel : ownerUnitModelList) {
//            if(ownerUnitModel.getUnitId()!=null){
            unitIds.add(ownerUnitModel.getUnitId());
//            }
        }
//        ownerUnitModelList.forEach(s->unitIds.add(s.getUnitId()));
        List<SysUnit> sysUnitList = sysUnitService.queryUnitByIds(unitIds);
        //sys_unit_owner查询num
        List<OwnerUnitModel> ownerUnitModels = unitOwnerMapper.queryListByOwnerAndUnit(ownerUnitModelList);
        //将数据封装到ownerUnitModelList中
        for (OwnerUnitModel ownerUnitModel : ownerUnitModelList) {
            for (SysUnit sysUnit : sysUnitList) {
                if (sysUnit.getUnitId().equals(ownerUnitModel.getUnitId())) {
                    BeanUtils.copyProperties(sysUnit, ownerUnitModel, "ownerId", "unitId", "unitNum");
                    break;
                }
            }
            for (OwnerUnitModel unitModel : ownerUnitModels) {
                if (unitModel.getUnitId().equals(ownerUnitModel.getUnitId()) && unitModel.getOwnerId().equals(ownerUnitModel.getOwnerId())) {
                    ownerUnitModel.setUnitNum(unitModel.getUnitNum());
                    break;
                }
            }
        }
        return ownerUnitModelList;
    }

    /**
     * 根据组织编号和组织Id查询
     *
     * @author HHe
     * @date 2019/9/29 14:49
     */
    @Override
    public SysUnitOwner queryObjByNumAndOwner(String unitNum, SysUser user) {
        return unitOwnerMapper.queryObjByNumAndOwner(unitNum, user.getDomain().getUnitId());
    }

    /**
     * 根据当前组织Id作为属主Id模糊查询编号
     *
     * @author HHe
     * @date 2019/10/6 15:15
     */
    @Override
    public List<SysUnitOwner> queryObjByDimNumAndOwner(SysUnitOwner sysUnitOwner) {
        return unitOwnerMapper.queryObjByDimNumAndOwner(sysUnitOwner.getUnitNum(), sysUnitOwner.getOwnerId());
    }

    /**
     * 根据编号查询全部对象集合
     *
     * @author HHe
     * @date 2019/10/12 11:48
     */
    @Override
    public Set<SysUnitOwner> queryObjByDimNum(SysUnitOwner sysUnitOwner) {
        return unitOwnerMapper.queryObjByDimNum(sysUnitOwner.getUnitNum());
    }

    /**
     * 根据编号和组织ID查询存在的组织Id集合
     *
     * @author HHe
     * @date 2019/10/12 11:05
     */
    @Override
    public List<Long> getNumAndOwner2Ids(String num, Long ownerId, String type) {
        //根据num查询相应Id
        List<Long> unitIds = new ArrayList<>();
        List<SysUnitOwner> sysUnitOwnerList = unitOwnerMapper.queryObjByDimNumAndOwner(num, ownerId);
        if (sysUnitOwnerList == null || sysUnitOwnerList.size() <= 0) {
            unitIds.add(-1L);
        } else {
            sysUnitOwnerList.forEach(s -> unitIds.add(s.getUnitId()));
        }
        List<Long> unitIdList = sysUnitClsfSerivcer.queryHaveInUnitIds(type, ownerId, unitIds);
        if (unitIdList == null || unitIdList.size() <= 0) {
            unitIdList.add(-1L);
        }
        return unitIdList;
    }

    /**
     * 根据编号查询出相关的unitid集合
     *
     * @author HHe
     * @date 2019/10/12 11:39
     */
    public List<Long> getNum2Ids(String num) {
        //根据num查询相应Id
        Set<SysUnitOwner> sysUnitOwnerList = unitOwnerMapper.queryObjByDimNum(num);
        List<Long> unitList = new ArrayList<>();
        sysUnitOwnerList.forEach(s -> unitList.add(s.getUnitId()));
        return unitList;
    }
}
