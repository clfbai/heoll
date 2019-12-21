package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.model.wareh.SttFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.SttModel;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.SttVO;
import com.github.pagehelper.PageInfo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 盘点服务
 * @author HHe
 * @date 2019/9/10 20:05
 */
public interface SttService {
    /**
     * 盘点表根据筛选条件查询列表
     * @author HHe
     * @date 2019/9/10 20:11
     */
    PageInfo<SttVO> querySttListByFilterModel(SttFilterModel sttFilterModel,Integer page,Integer size, SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, InvocationTargetException, NoSuchFieldException;
    /**
     * 加载盘点单下拉列表
     * @author HHe
     * @date 2019/9/12 15:03
     */
    Map<String,Object> loadSttPullDown();

    /**
     * 添加盘点表
     * @author HHe
     * @date 2019/9/16 11:24
     */
    int addStt(SttModel sttModel, SysUser sysUser);

    /**
     * 修改盘点表
     * @author HHe
     * @date 2019/9/16 11:42
     */
    int updateStt(SttModel sttModel,SysUser sysUser);

    /**
     * 删除盘点表
     * @author HHe
     * @date 2019/9/16 11:42
     */
    int delStt(Stt stt,SysUser sysUser);

    /**
     * 输入仓库编号判断合法，并加载信息
     * @author HHe
     * @date 2019/9/17 12:01
     */
    SttVO loadWarehMessByCode(String warehCode, SysUser sysUser);
//    /**
//     * 确认盘点表
//     * @author HHe
//     * @date 2019/9/18 10:03
//     */
//    int affirmStt(String sttNum, SysUser sysUser);
    /**
     * 重做盘点表
     * @author HHe
     * @date 2019/9/19 16:40
     */
    int reformStt(String sttNum, SysUser sysUser);
    /**
     * 查询盘点表详情
     * @author HHe
     * @date 2019/9/20 17:24
     */
    SttVO queryDetails(Stt stt, SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException;
    /**
     * 根据盘点表编号和组织Id查询盘点表
     * @author HHe
     * @date 2019/9/25 11:53
     */
    Stt querySttByNumAndId(String sttNum, SysUser sysUser);
    /**
     * 修改盘点表实际总数量
     * @author HHe
     * @date 2019/9/25 17:14
     */
    int updateTtlActQty(String sttNum, SysUser sysUser, BigDecimal num);
    /**
     * 修改盘点表状态
     * @author HHe
     * @date 2019/9/26 14:37
     */
    int atterSttStatus(String sttNum,String type,SysUser sysUser);
    /**
     * 功能列表展示
     * @author HHe
     * @date 2019/10/14 20:06
     */
    List<OperationVo> queryFunList(Stt stt);

    /**
     * 确认
     * @author HHe
     * @date 2019/10/15 10:45
     */
    int confirm(SttModel sttModel);
    /**
     * 重做
     * @author HHe
     * @date 2019/10/15 10:45
     */
    int redo(SttModel sttModel);

    /**
     * 审核
     * @author HHe
     * @date 2019/10/15 10:45
     */
    int check(SttModel sttModel);
    /**
     * 反审
     * @author HHe
     * @date 2019/10/15 10:45
     */
    int uncheck(SttModel sttModel);
    /**
     * 挂起
     * @author HHe
     * @date 2019/10/15 10:45
     */
    int suspend(SttModel sttModel);
    /**
     * 恢复
     * @author HHe
     * @date 2019/10/15 10:45
     */
    int resume(SttModel sttModel);
    /**
     * 作废
     * @author HHe
     * @date 2019/10/15 10:45
     */
    int abolish(SttModel sttModel);

    /**
     * 过账
     * @author HHe
     * @date 2019/10/15 10:45
     */
    int post(SttModel sttModel);
}
