package com.boyu.erp.platform.usercenter.service.salesservice.salesserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.controller.purchase.PurchaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.sales.SlaType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsxTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SlaTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.salesservice.SlaTypeService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.purchase.TypeUtils;
import com.boyu.erp.platform.usercenter.vo.sales.SlaTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: boyu-platform
 * @description: 销售申请类别接口实现
 * @author: wz
 * @create: 2019-9-4 15:36
 */@Slf4j
@Service
@Transactional
public class SlaTypeServiceImpl implements SlaTypeService {

     @Autowired
     private SlaTypeMapper slaTypeMapper;
     @Autowired
     private PsxTypeMapper psxTypeMapper;
    @Autowired
    private TypeUtils typeUtils;
    @Autowired
    private SysParameterMapper parameterMapper;

    @Override
    public List<PurKeyValue> optionGet() {
        return slaTypeMapper.optionGet();
    }

    @Override
    public PsxType selectByPsxType(SlaType type) {
        SlaType sla = slaTypeMapper.selectByPrimaryKey(type.getSlaType());
        return psxTypeMapper.selectByPrimaryKey(sla.getPsxType());
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @param slaType
     * @return
     */
    @Override
    public PageInfo<SlaTypeVo> selectAll(Integer page, Integer size, SlaTypeVo slaType) throws ServiceException {
        PageHelper.startPage(page,size);
        List<SlaTypeVo> slaTypeList = slaTypeMapper.selectALL(slaType);
        PageInfo<SlaTypeVo> pageInfo=new PageInfo<SlaTypeVo>(slaTypeList);
        return pageInfo;
    }

    /**
     * 新增
     * @param slaType
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int insertSlaType(SlaType slaType, SysUser user) throws Exception {
        //采购申请类别不为空，且唯一
        String pua = slaType.getSlaType();
        String psx = slaType.getPsxType();
        this.publicMethod(pua,psx);

        verificationKey(slaType);
        verificationVice(slaType);

        PsxType psxType = psxTypeMapper.selectByPrimaryKey(psx);
        typeUtils.insertParameter(pua,slaType,psxType,user,"sla_type;");

        return slaTypeMapper.insertSelective(slaType);
    }

    private void publicMethod(String pua,String psx){

        if(StringUtils.isBlank(pua))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"销售申请类别不允许为空");
        }
        if(StringUtils.isBlank(psx))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"购销申请类别不允许为空");
        }
    }

    /**
     * 验证是否重复
     */
    public void verificationKey(SlaType slaType) {
        List<SlaType> _recordList = slaTypeMapper.listBySlaType(slaType.getSlaType(),"");
        if (PurchaseController.getLambda(slaType.getSlaType(), _recordList.stream().map(SlaType::getSlaType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "采购申请类别已存在");
        }
    }

    /**
     * 验证是否重复
     */
    public void verificationVice(SlaType slaType) {
        List<SlaType> _recordList = slaTypeMapper.listByPsxType(slaType.getSlaType(),slaType.getPsxType());
        if (PurchaseController.getLambda(slaType.getPsxType(), _recordList.stream().map(SlaType::getPsxType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "购销申请类别已存在");
        }
    }

    /**
     * 修改
     * @param slaType
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int updateSlaType(SlaType slaType, SysUser user) throws Exception{

        //采购申请类别不为空，且唯一
        String sla = slaType.getSlaType();
        String psx = slaType.getPsxType();
        this.publicMethod(sla,psx);

        verificationVice(slaType);

        PsxType psxType = psxTypeMapper.selectByPrimaryKey(psx);
        typeUtils.insertParameter(sla,slaType,psxType,user,"sla_type;");

        return slaTypeMapper.updateByPrimaryKeySelective(slaType);
    }

    /**
     * 删除
     * @param slaType
     * @param user
     * @return
     * @throws ServiceException
     */
    @Override
    public int deleteSlaType(SlaType slaType, SysUser user) throws ServiceException
    {
        //修改参数状态
        parameterMapper.updateByType(slaType.getSlaType()+ParameterString.CREAT_TABLE_FILEDS,slaType.getSlaType()+ParameterString.TABLE_NOT_UPDATE,user.getPrsnl().getPrsnlId()+"");

        return slaTypeMapper.deleteByPrimaryKey(slaType.getSlaType());
    }


}
