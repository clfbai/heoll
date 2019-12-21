package com.boyu.erp.platform.usercenter.TPOS.service.impl;

import com.boyu.erp.platform.usercenter.TPOS.entity.WmsErpNum;
import com.boyu.erp.platform.usercenter.TPOS.mapper.WmsErpNumMapper;
import com.boyu.erp.platform.usercenter.TPOS.service.WmsErpGoDowmWarehServicer;
import com.boyu.erp.platform.usercenter.TPOS.service.WmsErpNumService;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRcvTask;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * erp和WMS关联关系服务
 *
 * @author HHe
 * @date 2019/11/5 11:58
 */
@Service
@Transactional
public class WmsErpNumServiceImpl implements WmsErpNumService {
    @Autowired
    private WmsErpNumMapper wmsErpNumMapper;
    @Autowired
    private WmsErpGoDowmWarehServicer wmsErpGoDowmWarehServicer;
    @Autowired
    private ParameterString parameterString;
    /**
     * 创建关联关系
     *
     * @param wmsErpNum 关联关系对象
     * @return 数据库操作数
     * @author HHe
     * @date 2019/11/5 12:03
     */
    @Override
    public int addRelation(WmsErpNum wmsErpNum) {
        return wmsErpNumMapper.insertSelective(wmsErpNum);
    }

    /**
     * 查询对应关系
     *
     * @param wmsErpNumP 信息对象
     * @return WmsErpNum 关系信息
     * @author HHe
     * @date 2019/11/5 12:22
     */
    @Override
    @Transactional(readOnly = true)
    public WmsErpNum queryRelation(WmsErpNum wmsErpNumP) {
        return wmsErpNumMapper.queryByBill(wmsErpNumP);
    }

    /**
     * 功能描述:  生成上传到C-WMS 订单Id
     *
     * @param warehRcvTask (入库任务对象)
     * @return:
     * @auther: CLF
     * @date: 2019/11/19 9:54
     */
    @Override
    public String createErpCwmsId(WarehRcvTask warehRcvTask) throws ServiceException {
        warehRcvTask.getUnitId();
        warehRcvTask.getTaskDocType();
        warehRcvTask.getTaskDocUnitId();
        warehRcvTask.getTaskDocNum();
        if (StringUtils.isBlank(warehRcvTask.getTaskDocType())) {
            throw new ServiceException("403", "入库任务类型为空");
        }
        if (warehRcvTask.getTaskDocUnitId() == null) {
            throw new ServiceException("403", "入库任务仓库属主组织Id为空");
        }
        if (StringUtils.isBlank(warehRcvTask.getTaskDocNum())) {
            throw new ServiceException("403", "入库任务编号为空");
        }
        if (warehRcvTask.getUnitId() == null) {
            throw new ServiceException("403", "入库任务属主组织Id为空");
        }
        //上传的库存单编号
        return warehRcvTask.getUnitId() + "_" + warehRcvTask.getTaskDocType() + "_" + warehRcvTask.getTaskDocUnitId() + "_" + warehRcvTask.getTaskDocNum();
    }

    /**
     * 删除关联关系
     *
     * @param wmsErpNum 删除的关联信息
     * @return 数据库操作数
     * @author HHe
     * @date 2019/11/6 17:44
     */
    @Override
    public int delRelation(WmsErpNum wmsErpNum) {
        return wmsErpNumMapper.delRelation(wmsErpNum);
    }
    /**
     * 根据入库任务查询是否存在关联C-WMS单据
     * 存在删除单据 并推送取消单据
     *
     * */
    @Override
    public int deleteCwmsGrn(WarehRcvTask model,String name, SysUser user) throws Exception {
        String wmsId =createErpCwmsId(model);
        WmsErpNum wmsErpNum = queryRelation(new WmsErpNum(wmsId));
        int a = 0;
        //需要推送取消订单的
        if (wmsErpNum != null) {
            //删除关联关系
            a = this.delRelation(wmsErpNum);
            //上传开关
            boolean cwms = parameterString.UploginCwms();
            if(cwms){
                //推送取消单据
                wmsErpGoDowmWarehServicer.createOrderCancelURL(
                        wmsErpGoDowmWarehServicer.createGrnCancelOrder(wmsId, model), name, user);
            }
        }
        return a;
    }
}
