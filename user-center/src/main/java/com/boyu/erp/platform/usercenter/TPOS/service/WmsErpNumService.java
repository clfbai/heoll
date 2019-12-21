package com.boyu.erp.platform.usercenter.TPOS.service;

import com.boyu.erp.platform.usercenter.TPOS.entity.WmsErpNum;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;

/**
 * erp和WMS 入库出库单据关联关系接口
 *
 * @author HHe
 * @date 2019/11/5 11:59
 */
public interface WmsErpNumService {
    /**
     * 创建关联关系
     *
     * @param wmsErpNum 关联关系对象
     * @return 数据库操作数
     * @author HHe
     * @date 2019/11/5 12:03
     */
    int addRelation(WmsErpNum wmsErpNum);

    /**
     * 查询对应关系
     *
     * @param wmsErpNumP 信息对象
     * @return WmsErpNum 关系信息
     * @author HHe
     * @date 2019/11/5 12:22
     */
    WmsErpNum queryRelation(WmsErpNum wmsErpNumP);

    /**
     * 功能描述:  生成上传到C-WMS 订单Id
     *
     * @param warehRcvTask (入库任务对象)
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 9:54
     */
    String createErpCwmsId(WarehRcvTask warehRcvTask) throws ServiceException;

    /**
     * 删除关联关系
     *
     * @param wmsErpNum 删除的关联信息
     * @return 数据库操作数
     * @author HHe
     * @date 2019/11/6 17:44
     */
    int delRelation(WmsErpNum wmsErpNum);


    /**
     *
     */

    int deleteCwmsGrn(WarehRcvTask model, String name, SysUser user) throws Exception;
}
