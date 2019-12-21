package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUnitOwner;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.system.OwnerUnitModel;

import java.util.List;
import java.util.Set;

public interface SysUnitOwnerSerivcer {
    int deleteUnitOwner(SysUnitOwner unitOwner);

    int addSysUnitOwner(SysUnitOwner unitOwner);

    int updateSysUnitOwner(SysUnitOwner unitOwner);

    SysUnitOwner getUnitOwner(SysUnitOwner unitOwner);

    List<SysUnitOwner> getListUnitOwner(Long uitId);
    /**
     * 将owner中的num和unit中name封装到集合中返回
     * @author HHe
     * @date 2019/9/28 11:26
     */
    List<OwnerUnitModel> packNumAndName2List(List<OwnerUnitModel> ownerUnitModelList);
    /**
     * 根据组织编号和组织Id查询
     * @author HHe
     * @date 2019/9/29 14:48
     */
    SysUnitOwner queryObjByNumAndOwner(String unitNum, SysUser user);
    /**
     * 根据当前组织Id作为属主Id模糊查询编号
     * @author HHe
     * @date 2019/10/6 15:13
     */
    List<SysUnitOwner> queryObjByDimNumAndOwner(SysUnitOwner sysUnitOwner);
    /**
     * 根据编号查询全部对象集合
     * @author HHe
     * @date 2019/10/12 11:47
     */
    Set<SysUnitOwner> queryObjByDimNum(SysUnitOwner sysUnitOwner);
    /**
     * 根据编号和组织ID查询存在的组织Id集合
     *
     * @author HHe
     * @date 2019/10/12 11:05
     */
    List<Long> getNumAndOwner2Ids(String num, Long ownerId, String type);

    List<Long> getNum2Ids(String num);
}
