package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRmode;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.wareh.WarehRmodeMode;

import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 仓库入库方式服务
 * @author: ck
 * @create: 2019-06-20 15:36
 */
public interface WarehRmodeService {

    List<WarehRmode> selectWarehRomde(WarehRmode warehRmode) throws ServiceException;

    /**
     * @param: warehId 仓库Id ，rvcRmode入库方式
     * @return:
     * @auther: CLF
     * @date: 2019/11/8 10:24
     */
    WarehRmode selectWarehRmode(Long warehId, String rvcRmode);


    int updateWarehRomde(WarehRmodeMode warehRmodeMode) throws ServiceException;

    int insertWarehRomde(WarehRmode warehRmode) throws ServiceException;

    int deleteWarehRomde(WarehRmode warehRmode) throws ServiceException;

    /**
     * 功能描述: 变更操作查询
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/22 17:06
     */
    Map<String, String> changeFunctionList(Wareh wareh) throws ServiceException;

    /**
     * 功能描述: 修改仓库特殊值
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/22 17:21
     */
    int changeFunctionUpdate(Wareh warehRmode);


    /**
     * 新建仓库时，设置默认入库方式
     */
    void addDefaultWarehRmode(Long warehId);

}
