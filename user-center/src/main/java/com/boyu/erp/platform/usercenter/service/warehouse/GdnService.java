package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.TPOS.entity.DhOrdTask;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Gdn;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.model.wareh.*;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.GdnModelVO;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbDtlVo;
import com.github.pagehelper.PageInfo;

import javax.xml.bind.JAXBException;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface GdnService {


    /**
     * 根据出库方式初始化出库单
     * @return
     */
    WarehDmodeModel initGdnByDelivMode(Long warehId,String delivMode);

    /**
     * 查询列表
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    PageInfo<GdnModelVO> queryStbListByCon(GdnFilterModel gdnFilterModel, Integer page, Integer size, SysUser sysUser) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException;
    /**
     * 修改出库单
     * @param
     * @param sysUser
     * @return
     */
    int updateGdn(GdnModel gdnModel, SysUser sysUser);
    /**
     * 出库单下拉
     * @return
     */
    Map<String, Object> gdnPullDown(Long sUnitId,SysUser sysUser);

    /**
     * 删除出库单
     * @param gdnModel
     * @param sysUser
     * @return
     */
    int delGdn(GdnModel gdnModel, SysUser sysUser);

    /**
     * 出库单页面添加出库单;
     * @author HHe
     */
    String pageAddGdn(GdnModel gdnModel, SysUser sysUser);



    /**
     * 根据仓库编号加载出库方式
     * @author HHe
     */
    Map<String,Object> loadDelivModeByWarehCode(Long warehId);

    /**
     * 根据库存单编号查询库存明细集合
     * @author HHe
     * @date 2019/8/24 10:27
     */
    List<StbDtlVo> queryStbDtlList(GdnFilterModel gdnFilterModel, SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException;

    /**
     * 出库单功能列表展示
     * @author HHe
     * @date 2019/10/15 15:21
     */
    List<OperationVo> queryFunList(GdnModel gdnModel);
    /**
     * 确认
     * @author HHe
     * @date 2019/10/15 17:18
     */
    int confirm(GdnModel gdnModel,SysUser sysUser);
    /**
     * 重做
     * @author HHe
     * @date 2019/10/15 17:20
     */
    int redo(GdnModel gdnModel,SysUser sysUser);
    /**
     * 挂起
     * @author HHe
     * @date 2019/10/15 17:20
     */
    int suspend(GdnModel gdnModel,SysUser sysUser);
    /**
     * 恢复
     * @author HHe
     * @date 2019/10/15 17:20
     */
    int resume(GdnModel gdnModel,SysUser sysUser);
    /**
     * 作废
     * @author HHe
     * @date 2019/10/15 17:20
     */
    int abolish(GdnModel gdnModel,SysUser sysUser) throws Exception;
    /**
     * 出库
     * @author HHe
     * @date 2019/10/15 17:21
     */
    int deliver(GdnModel gdnModel,SysUser sysUser) throws Exception;
    /**
     * 取消出库
     * @author HHe
     * @date 2019/10/15 17:21
     */
    int reverseDeliver(GdnModel gdnModel, SysUser sysUser) throws Exception ;

    /**
     * 修改出库单
     * @param gdn 需要修改的对象数据
     * @return 数据库从操作数
     * @author HHe
     * @date 2019/10/29 11:22
     */
    void asyncUpdateGdn(Gdn gdn);

    /**
     * 删除出库出库单以及关联的库存和明细
     * @param gdnModel 出库单、库存单、库存明细
     * @return
     * @author HHe
     * @date 2019/11/6 12:20
     */
    int delGdnAndDtl(GdnModel gdnModel, SysUser sysUser);

    /**
     * 查询列表总金额、总数量；
     * @param gdnFilterModel 筛选条件
     * @return ttl_qty（总数量）、ttl_val（总金额）
     * @author HHe
     * @date 2019/11/18 17:50
     */
    Map<String,Object> getListTotal(GdnFilterModel gdnFilterModel);
    /**
     * 查询出库单
     * @param stbNum 库存单编号
     * @param unitId 组织Id
     * @return
     * @author HHe
     * @date 2019/11/28 20:50
     */
    GdnStbModel queryGdnModelByNumAndId(String stbNum, Long unitId);
    /**
     * 判断商品是否能添加、删除
     * @author HHe
     * @date 2019/11/29 16:09
     */
    Map<String,Object> judgeDelAndUpd(GdnModel gdnModel);
    /**
     * 添加出库单明细（单条）
     * @author HHe
     * @date 2019/11/29 17:19
     */
    int addGdnOneStbDtl(StbDtl stbDtl);
    /**
     * 修改出库单明细（单条）
     * @param stbDtl 商品明细
     * @return
     * @author HHe
     * @date 2019/12/2 18:00
     */
    int updateGdnOneStbDtl(StbDtl stbDtl);
    /**
     * 删除出库单明细（单条）
     * @param gdnStbDtlModel 组织Id、库存单编号、商品Id
     * @return
     * @author HHe
     * @date 2019/12/3 10:26
     */
    int delGdnOneStbDtl(GdnStbDtlModel gdnStbDtlModel);
    /**
     * 根据商品代码查询出库单页面商品相关信息
     * @author HHe
     * @date 2019/8/27 11:53
     */
    StbDtlVo queryProVoByProCode(StbDtlVo stbDtlVo,SysUser sysUser);
    /**
     * WMS确认单据（异步）
     * @param dhOrdTask 下发任务记录
     * @param xmlObj WMS确认出库信息
     * @author HHe
     * @date 2019/12/4 21:26
     */
    void wmsConfirmGdn(DhOrdTask dhOrdTask, String xmlObj) throws JAXBException;
//    /**
//     * 出库单明细弹窗
//     * @param goodsPopupFilterModel 出库单筛选信息
//     * @return 商品弹窗分页集合
//     * @author HHe
//     * @date 2019/12/5 19:28
//     */
//    PageInfo<DtlProdVo> dtlPopup(Integer page, Integer size, GoodsPopupFilterModel goodsPopupFilterModel);
}
