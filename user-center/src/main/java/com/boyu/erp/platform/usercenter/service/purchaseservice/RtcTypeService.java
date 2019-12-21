package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.purchase.RtcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.partner.RtcTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
 * @Classname RtcTypeService
 * @Description TODO
 * @Date 2019/6/20 09:56
 * @Created wz
 */
public interface RtcTypeService {

    /**
     * 退货合同类别下拉
     */
    List<PurKeyValue> optionGet();

    /**
     * 退货合同类别查询
     * @param page
     * @param size
     * @param rtcType
     * @return
     */
    PageInfo<RtcTypeVo> selectAll(Integer page, Integer size, RtcType rtcType);

    /**
     * 退货合同类别添加
     * @param rtcType
     * @param user
     * @return
     */
    int insertRtcType(RtcType rtcType, SysUser user);

    /**
     * 退货合同类别修改
     * @param rtcType
     * @param user
     * @return
     */
    int updateRtcType(RtcType rtcType, SysUser user) throws Exception;

    /**
     * 退货合同类别删除
     * @param rtcType
     * @return
     */
    int deleteRtcType(RtcType rtcType);
}
