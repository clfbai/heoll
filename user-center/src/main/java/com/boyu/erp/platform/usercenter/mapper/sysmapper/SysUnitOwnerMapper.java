package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysUnitOwner;
import com.boyu.erp.platform.usercenter.model.system.OwnerUnitModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SysUnitOwnerMapper {

    int deleteUnitOwner(SysUnitOwner key);

    int insertUnitOwner(SysUnitOwner record);

    SysUnitOwner selectUnitOwner(SysUnitOwner key);

    int updateUnitOwner(SysUnitOwner record);


     List<SysUnitOwner> selectListUnitOwner(Long uitId);

    /**
     * 查询当前组织是否已有编号的数据
     * @param key
     * @return
     */
    SysUnitOwner selectUnitOwnerByNum(SysUnitOwner key);
    /**
     * 将owner中的num和unit中name封装到集合中返回
     * @author HHe
     * @date 2019/9/28 11:30
     */
    List<OwnerUnitModel> queryListByOwnerAndUnit(@Param("ownerUnitModelList") List<OwnerUnitModel> ownerUnitModelList);
    /**
     * 根据组织编号和组织Id查询
     * @author HHe
     * @date 2019/9/29 14:50
     */
    SysUnitOwner queryObjByNumAndOwner(@Param("unitNum") String unitNum, @Param("ownerId") Long ownerId);
    /**
     * 根据当前组织Id作为属主Id模糊查询编号
     * @author HHe
     * @date 2019/10/6 15:17
     */
    List<SysUnitOwner> queryObjByDimNumAndOwner(@Param("warehNum") String warehNum, @Param("unitId") Long unitId);
    /**
     * 根据编号模糊查询全部对象集合
     * @author HHe
     * @date 2019/10/12 11:48
     */
    Set<SysUnitOwner> queryObjByDimNum(String unitNum);
}