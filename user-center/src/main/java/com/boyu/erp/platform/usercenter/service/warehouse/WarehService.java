package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.WarehOptinModel;
import com.boyu.erp.platform.usercenter.model.wareh.WarehPopUpFilterModel;
import com.boyu.erp.platform.usercenter.service.base.BaseService;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehPopUpVO;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehUnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehVo;
import com.github.pagehelper.PageInfo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @program: boyu-platform_text
 * @description: 仓库接口
 * @author: clf
 * @create: 2019-06-26 12:32
 */
public interface WarehService extends BaseService {
    PageInfo<WarehVo> selectWareh(Integer page, Integer size, WarehVo WarehVo, SysUser user);

    /**
     * 功能描述:  仓库模块新增仓库
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/5 11:14
     */
    int instrWareh(WarehVo warehVo, SysUser user) throws ServiceException;

    int udpateWareh(WarehVo warehVo, SysUser user) throws Exception;

    /**
     * 逻辑删除
     */
    int deleteWareH(WarehVo warehVo, SysUser user) throws Exception;


    Wareh selectByWarehId(Long warehId);


    /**
     * 根据参数设置仓库库存管理等值
     */
    Wareh setParamWareh(Wareh wareh);

    /**
     * 根据参数设置仓库库存管理等值
     */
    List<String> getUpdateFile();

    /**
     * 功能描述: 仓库代码弹窗
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/15 17:16
     */
    PageInfo<WarehUnitOptionVo> getOptinCode(Integer page, Integer size, WarehOptinModel model, SysUser sessionSysUser);

    /**
     * 功能描述: 开设领域增加仓库
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/5 11:27
     */
    int addDomainWareh(SysUnit unit, SysUser user);

    /**
     * 判断仓库是否可用
     *
     * @author HHe
     * @date 2019/8/26 11:25
     */
    WarehVo judgewarehavailable(Long warehId, Long unitId);

    /**
     * 判断仓库是否可用（PLAN2）
     *
     * @author HHe
     * @date 2019/8/25 18:01
     */
    UnitOptionVo judgewarehavailable1(String warehCode, SysUser sysUser);

    /**
     * 仓库弹窗
     *
     * @author HHe
     * @date 2019/9/15 14:51
     */
    List<WarehPopUpVO> warehPopUp(WarehPopUpFilterModel warehPopUpFilterModel, SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException;

    /**
     * 根据编号查询仓库信息
     *
     * @author HHe
     * @date 2019/9/17 11:02
     */
    Wareh queryWarehByWarehId(Long warehId);

    /**
     * 根据组织id查询当前组织仓库是否存在
     *
     * @author HHe
     * @date 2019/9/24 10:34
     */
    Wareh querywarehByUnitId(Long warehId, Long ownerId);

    /**
     * 判断组织Id是否存在仓库中，并且返回仓库存在的
     *
     * @author HHe
     * @date 2019/10/6 16:00
     */
    List<Long> judgeReWarehIds(String warehNum, Long unitId);

    /**
     * 判断组织的仓库中是否存在这些仓库并将其返回
     *
     * @author HHe
     * @date 2019/10/7 12:27
     */
    List<Long> getSomeWarehId(List<Long> warehIds, Long unitId);

    /**
     * 功能描述: 仓库功能按钮初始化
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/21 17:12
     */
    List<PurKeyValue> initButtons(ProdCls model);
}
