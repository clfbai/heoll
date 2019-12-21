package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;




import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.controller.purchase.PurchaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PrcType;
import com.boyu.erp.platform.usercenter.entity.purchase.RtcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.PrcTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.RtcTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PrcTypeService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.purchase.TypeUtils;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcAutoVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname PrcTypeServiceimpl
 * @Description TODO
 * @Date 2019/6/20 10:05
 * @Created wz
 */
@Service
@Transactional
public class PrcTypeServiceimpl implements PrcTypeService {

    @Autowired
    private PrcTypeMapper prcTypeMapper;
    @Autowired
    private RtcTypeMapper rtcTypeMapper;
    @Autowired
    private TypeUtils typeUtils;
    @Autowired
    private SysParameterMapper parameterMapper;


    public PageInfo<PrcTypeVo> selectAll(Integer page, Integer size, PrcType prcType) throws ServiceException {
        PageHelper.startPage(page,size);
        List<PrcTypeVo> prcTypeList = prcTypeMapper.selectALL(prcType);
        PageInfo<PrcTypeVo> pageInfo=new PageInfo<PrcTypeVo>(prcTypeList);
        return pageInfo;
    }

    @Override
    public int insertPrcType(PrcType prcType, SysUser user) throws Exception{
        //采购申请类别不为空，且唯一
        String prc = prcType.getPrcType();
        String rtc = prcType.getRtcType();
        this.publicMethod(prc,rtc);

        verificationKey(prcType);
        verificationVice(prcType);

        RtcType rtcType = rtcTypeMapper.selectByPrimaryKey(rtc);
        typeUtils.insertParameter(prc,prcType,rtcType,user,"prc_type;vender_id;");
        return prcTypeMapper.insertPrcType(prcType);
    }

    /**
     * 验证是否重复
     */
    public void verificationKey(PrcType prcType) {
        List<PrcType> _recordList = prcTypeMapper.listByPrcType(prcType.getPrcType(),"");
        if (PurchaseController.getLambda(prcType.getPrcType(), _recordList.stream().map(PrcType::getPrcType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "退购合同类别已存在");
        }
    }

    /**
     * 验证是否重复
     */
    public void verificationVice(PrcType prcType) {
        List<PrcType> _recordList = prcTypeMapper.listByRtcType(prcType.getPrcType(),prcType.getRtcType());
        if (PurchaseController.getLambda(prcType.getRtcType(), _recordList.stream().map(PrcType::getRtcType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "退货合同类别已存在");
        }
    }

    @Override
    public int updatePrcType(PrcType prcType, SysUser user) throws Exception{

        //采购申请类别不为空，且唯一
        String prc = prcType.getPrcType();
        String rtc = prcType.getRtcType();
        this.publicMethod(prc,rtc);

        verificationVice(prcType);

        RtcType rtcType = rtcTypeMapper.selectByPrimaryKey(rtc);
        typeUtils.insertParameter(prc,prcType,rtcType,user,"prc_type;vender_id;");

        return prcTypeMapper.updatePrcType(prcType);
    }

    private void publicMethod(String prc,String rtc){

        if(StringUtils.isBlank(prc))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"退购合同类别不允许为空");
        }
        if(StringUtils.isBlank(rtc))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"退货合同类别不允许为空");
        }
    }

    @Override
    public int deletePrcType(PrcType prcType, SysUser user) throws ServiceException
    {
        if(prcType.getPrcType()==null)
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"退购合同类别为空");
        }
        //修改参数状态
        parameterMapper.updateByType(prcType.getPrcType()+ParameterString.CREAT_TABLE_FILEDS,prcType.getPrcType()+ParameterString.TABLE_NOT_UPDATE,user.getPrsnl().getPrsnlId()+"");

        return prcTypeMapper.deletePrcType(prcType);
    }

    @Override
    public List<PurKeyValue> optionGet() {
        return prcTypeMapper.optionGet();
    }


    @Override
    public RtcAutoVo selectByPrcAuto(String prcType) {
        return prcTypeMapper.selectByRtcAuto(prcType);
    }
}
