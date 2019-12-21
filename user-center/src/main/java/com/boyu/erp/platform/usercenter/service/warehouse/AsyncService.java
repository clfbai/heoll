package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.TPOS.entity.DhOrdTaskU;
import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;

import java.util.List;

/**
 * 线程池异步处理接口
 *
 * @author HHe
 * @date 2019/9/27 9:40
 */
public interface AsyncService {
    /**
     * 盘点表过账损益（异步）
     *
     * @author HHe
     * @date 2019/9/26 19:34
     */
    void warehSttPAL(Stt stt, SysUser sysUser);

    /**
     * 出库单出库异步处理
     *
     * @author HHe
     * @date 2019/10/28 11:23
     */
    void gdnDeliver(List<CountCostModel> countCostModels, Long warehId,Long fsclUnitId);

    /**
     * 修改库存数量和仓库数量
     *
     * @param list       修改信息（共用）
     * @param costGrpId  成本组Id（组织成本）
     * @param warehId    仓库Id（仓库成本用）
     * @param fsclUnitId 会计组织Id（共用）
     * @author HHe
     * @date 2019/11/22 11:14
     */
    void updateCost(List<CountCostModel> list, Long costGrpId, Long warehId, Long fsclUnitId);
    /**
     * 上传单据信息
     * @param obj 请求体
     * @param cwmsUrlParamModel 请求信息
     * @author HHe
     * @date 2019/12/4 10:13
     */
    void uploadingOrder(DhOrdTaskU dhOrdTaskU,Object obj, CwmsUrlParamModel cwmsUrlParamModel);
}
