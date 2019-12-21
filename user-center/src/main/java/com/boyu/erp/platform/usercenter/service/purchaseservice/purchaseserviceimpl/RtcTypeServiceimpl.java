package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.entity.purchase.PrcType;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.purchase.RtcType;
import com.boyu.erp.platform.usercenter.entity.sales.SrcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.purchase.PrcTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.RtcTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SrcTypeMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.RtcTypeService;
import com.boyu.erp.platform.usercenter.utils.purchase.TypeUtils;
import com.boyu.erp.platform.usercenter.vo.partner.RtcTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname RtcTypeServiceimpl
 * @Description TODO
 * @Date 2019/6/20 10:43
 * @Created wz
 */
@Service
@Transactional
public class RtcTypeServiceimpl implements RtcTypeService {

    @Autowired
    private RtcTypeMapper rtcTypeMapper;
    @Autowired
    private PrcTypeMapper prcTypeMapper;
    @Autowired
    private SrcTypeMapper srcTypeMapper;
    @Autowired
    private TypeUtils typeUtils;

    @Override
    public List<PurKeyValue> optionGet() {
        return rtcTypeMapper.optionGet();
    }

    /**
     * 退货合同类别查询
     * @param page
     * @param size
     * @param rtcType
     * @return
     */
    @Override
    public PageInfo<RtcTypeVo> selectAll(Integer page, Integer size, RtcType rtcType) {
        PageHelper.startPage(page,size);
        List<RtcTypeVo> rtcTypeList = rtcTypeMapper.selectALL(rtcType);
        PageInfo<RtcTypeVo> pageInfo=new PageInfo<>(rtcTypeList);
        return pageInfo;
    }

    /**
     * 退货合同类别添加
     * @param rtcType
     * @param user
     * @return
     */
    @Override
    public int insertRtcType(RtcType rtcType, SysUser user) {
        return rtcTypeMapper.insertSelective(rtcType);
    }

    /**
     * 退货合同类别修改
     * @param rtcType
     * @param user
     * @return
     */
    @Override
    public int updateRtcType(RtcType rtcType, SysUser user) throws Exception {
        PrcType prcType = prcTypeMapper.selectByRtcType(rtcType.getRtcType());
        if(prcType!=null){
            typeUtils.insertParameter(prcType.getPrcType(),prcType,rtcType,user,"prc_type;vender_id;");
        }
        SrcType srcType = srcTypeMapper.selectByRtcType(rtcType.getRtcType());
        if(srcType!=null){
            typeUtils.insertParameter(srcType.getSrcType(),srcType,rtcType,user,"src_type;vendee_id;");
        }
        return rtcTypeMapper.updateByPrimaryKeySelective(rtcType);
    }

    @Override
    public int deleteRtcType(RtcType rtcType) {
        return rtcTypeMapper.deleteByPrimaryKey(rtcType.getRtcType());
    }

}
