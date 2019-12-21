package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.controller.purchase.PurchaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PscType;
import com.boyu.erp.platform.usercenter.entity.sales.SlcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.PscTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcTypeService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.purchase.TypeUtils;
import com.boyu.erp.platform.usercenter.vo.purchase.PscAutoVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: boyu-platform
 * @description: 销售合同类别接口实现
 * @author: ck
 * @create: 2019-06-20 15:36
 */
@Slf4j
@Service
@Transactional
public class SlcTypeServiceImpl implements SlcTypeService {

    @Autowired
    private SlcTypeMapper slcTypeMapper;
    @Autowired
    private PscTypeMapper pscTypeMapper;
    @Autowired
    private TypeUtils typeUtils;
    @Autowired
    private SysParameterMapper parameterMapper;

    @Override
    public int deleteBySlcType(SlcType slcType,SysUser user) throws ServiceException {
        parameterMapper.updateByType(slcType.getSlcType()+ ParameterString.CREAT_TABLE_FILEDS,slcType.getSlcType()+ ParameterString.TABLE_NOT_UPDATE,user.getPrsnl().getPrsnlId()+"");
        return slcTypeMapper.deleteBySlcType(slcType);
    }

    @Override
    public int insert(SlcType slcType, SysUser user) throws Exception {
        verificationKey(slcType);
        verificationVice(slcType);
        PscType pscType = pscTypeMapper.selectByPrimaryKey(slcType.getPscType());
        typeUtils.insertParameter(slcType.getSlcType(),slcType,pscType,user,"slc_type;vendee_id;");
        return slcTypeMapper.insert(slcType);
    }

    @Override
    public int updateBySlcType(SlcType slcType, SysUser user) throws Exception {
        verificationVice(slcType);
        PscType pscType = pscTypeMapper.selectByPrimaryKey(slcType.getPscType());
        typeUtils.insertParameter(slcType.getSlcType(),slcType,pscType,user,"slc_type;vendee_id;");
        return slcTypeMapper.updateBySlcType(slcType);
    }

    @Override
    public PageInfo<SlcType> getSlcTypeList(Integer page, Integer size, SlcType slcType) throws ServiceException {
        PageHelper.startPage(page, size);
        List<SlcType> list = slcTypeMapper.getSlcTypeList(slcType);
        PageInfo<SlcType> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 验证销售合同类别是否重复
     */
    public void verificationKey(SlcType record) {
        List<SlcType> _recordList = null;
        _recordList = slcTypeMapper.listBySlcType(record.getSlcType(),"");
        if (PurchaseController.getLambda(record.getSlcType(), _recordList.stream().map(SlcType::getSlcType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "销售合同类别已存在");
        }
    }
    /**
     * 验证购销合同类别是否重复
     */
    public void verificationVice(SlcType record) {
        List<SlcType> _recordList = slcTypeMapper.listByPscType(record.getSlcType(),record.getPscType());
        if (PurchaseController.getLambda(record.getPscType(), _recordList.stream().map(SlcType::getPscType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "购销合同类别已存在");
        }
    }

    @Override
    public List<PurKeyValue> optionGet() {
        return slcTypeMapper.optionGet();
    }


    @Override
    public PscAutoVo selectByPscAuto(String slcType){
        return slcTypeMapper.selectByPscAuto(slcType);
    }
}
