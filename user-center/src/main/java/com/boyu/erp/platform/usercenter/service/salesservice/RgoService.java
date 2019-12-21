package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @program: RgoService
 * @description: 调配单接口
 * @author: wz
 * @create: 2019-10-6 16:00
 */
public interface RgoService {

    /**
     * 查询调配单
     * @param vo
     * @param page
     * @param size
     * @param sysUser
     * @return
     * @throws Exception
     */
    public PageInfo<RgoVo> selectAll(RgoVo vo, Integer page, Integer size, SysUser sysUser) throws Exception;

    /**
     * 添加调配单
     * @param vo
     * @param user
     * @return
     */
    int insertRgo(RgoVo vo, SysUser user);

    /**
     * 修改调配单
     * @param vo
     * @param user
     * @return
     */
    int updateRgo(RgoVo vo, SysUser user);

    /**
     * 删除调配单
     * @param vo
     * @param user
     * @return
     */
    int deleteRgo(RgoVo vo, SysUser user);

    /**
     * 查询调配单是否固定单价
     * @param vo
     * @return
     */
    Map<String,Object> getFixedPrice(RgoVo vo);

    /**
     * 确认单据
     * @param vo
     * @param user
     * @return
     */
    int confirm(RgoVo vo, SysUser user);

    /**
     * 重做单据
     * @param vo
     * @param user
     * @return
     */
    int redo(RgoVo vo, SysUser user);

    /**
     * 审核单据
     * @return
     */
    int examine(RgoVo vo, SysUser user) throws Exception;

    /**
     * 重审单据
     * @param vo
     * @param user
     * @return
     */
    int reiterate(RgoVo vo, SysUser user) throws Exception;

    /**
     * 挂起单据
     * @param vo
     * @param user
     * @return
     */
    int hangUp(RgoVo vo, SysUser user);

    /**
     * 恢复单据
     * @param vo
     * @param user
     * @return
     */
    int recovery(RgoVo vo, SysUser user);

    /**
     * 作废单据
     * @param vo
     * @param user
     * @return
     */
    int toVoid(RgoVo vo, SysUser user) throws Exception;

    /**
     * 查询单条记录
     * @return
     */
    RgoVo selectByOnly(RgoVo vo);

    /**
     * 查询调配单可操作状态
     * @param vo
     * @return
     */
    List<OperationVo> initButtons(RgoVo vo);

    /**
     * 中转发货
     * @param vo
     * @return
     */
    int transferUnitDelivered(RgoVo vo);

    /**
     * 中转收货
     * @param vo
     * @return
     */
    int transferUnitReceived(RgoVo vo, SysUser user) throws Exception;

    /**
     * 取消中转发货
     * @param vo
     * @return
     */
    int transferUnitReversedDelivered(RgoVo vo);

    /**
     * 取消中转收货
     * @param vo
     * @return
     */
    int transferUnitReverseReceived(RgoVo vo, SysUser user);

    /**
     * 收货方审核
     * @param srcDocUnitId
     * @param srcDocNum
     * @return
     */
    int receivingUnitChecked(Long srcDocUnitId,String srcDocNum);

    /**
     * 收货方取消审核
     * @param srcDocUnitId
     * @param srcDocNum
     * @return
     */
    int receivingUnitUnchecked(Long srcDocUnitId,String srcDocNum);

    /**
     * 调入方收货
     * @param vo
     * @param stb
     * @return
     */
    int receivingUnitReceived(RgoVo vo, Stb stb);

    /**
     * 调入方取消收货
     * @param vo
     * @param stb
     * @return
     */
    int receivingUnitReverseReceived(RgoVo vo, Stb stb);

    /**
     * 调出方发货
     * @param vo
     * @param stb
     * @return
     */
    int deliveryUnitDelivered(RgoVo vo, Stb stb);

    /**
     * 调出方取消发货
     * @param vo
     * @param stb
     * @return
     */
    int deliveryUnitReverseDelivered(RgoVo vo, Stb stb);

    /**
     *  发货方审核
     * @param vo
     */
    int deliveryUnitChecked(RgoVo vo);
}
