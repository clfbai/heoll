package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.model.wareh.WarehOptinModel;
import com.boyu.erp.platform.usercenter.model.wareh.WarehPopUpFilterModel;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehPopUpVO;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehUnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WarehMapper {
    int deleteWarehId(Long warehId);

    int insertWareh(Wareh record);

    Wareh selectByWarehId(Long warehId);

    Wareh selectWarehCode(@Param("warehCode") String warehCode);

    int updateByWareh(Wareh record);

    List<WarehVo> getUintWareh(WarehVo w);

    /**
     * 功能描述: 仓库组织下拉
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/15 17:16
     */
    List<WarehUnitOptionVo> getUintOption(WarehUnitOptionVo vo);

    /**
     * 功能描述: 选择组织后查询当前组织的仓库
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/30 17:11
     */
    List<WarehUnitOptionVo> getOwnerIdOption(WarehUnitOptionVo vo);


    /**
     * 功能描述: 公司组织下拉
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/15 17:16
     */
    List<WarehUnitOptionVo> getCompanyOption(WarehUnitOptionVo vo);

    List<OptionVo> getOptionVo(Long warehId);


    /**
     * 功能描述: 仓库代码弹窗
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/15 17:16
     */
    List<WarehUnitOptionVo> getWarehCodeOption(WarehOptinModel model);

    /**
     * 判断仓库是否可用（PLAN2）
     *
     * @author HHe
     * @date 2019/8/25 18:01
     */
    UnitOptionVo judgewarehavailable1(@Param("warehCode") String warehCode, @Param("unitId") Long unitId);

    /**
     * 判断仓库是否可用
     *
     * @author HHe
     * @date 2019/8/26 11:25
     */
    WarehVo judgewarehavailable(@Param("warehId") Long warehId, @Param("unitId") Long unitId);

    /**
     * 仓库弹窗
     *
     * @author HHe
     * @date 2019/9/15 14:51
     */
    List<WarehPopUpVO> warehPopUp(WarehPopUpFilterModel warehPopUpFilterModel);

    /**
     * 根据编号查询仓库信息
     *
     * @author HHe
     * @date 2019/9/17 11:03
     */
    Wareh queryWarehById(Long warehId);

    /**
     * 根据组织id查询当前组织仓库是否存在
     *
     * @author HHe
     * @date 2019/9/24 10:42
     */
    Wareh querywarehByUnitId(@Param("warehId") Long warehId, @Param("ownerId") Long ownerId);

    /**
     * 根据仓库Id和ownerId判断仓库是否可用
     *
     * @author HHe
     * @date 2019/9/29 15:09
     */
    Wareh querywarehByUnitIdInA(@Param("warehId") Long warehId, @Param("unitId") Long unitId);

    /**
     * 判断组织Id是否存在仓库中，并且返回仓库存在的
     *
     * @author HHe
     * @date 2019/10/6 16:01
     */
    List<Long> judgeReWarehIds(@Param("warehIds") List<Long> warehIds, @Param("unitId") Long unitId);

    /**
     * 功能描述:  发货方弹窗查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/18 15:46
     */
    List<WarehUnitOptionVo> getDelivOption(WarehUnitOptionVo vo);
}