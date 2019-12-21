package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;




import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.controller.purchase.PurchaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PscType;
import com.boyu.erp.platform.usercenter.entity.purchase.PucType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.PscTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PucTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PucTypeService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.purchase.TypeUtils;
import com.boyu.erp.platform.usercenter.vo.purchase.PscAutoVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PucTypeVo;
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
 * @Classname PuaTypeServiceimpl
 * @Description TODO
 * @Date 2019/6/18 19:20
 * @Created wz
 */
@Service
@Transactional
public class PucTypeServiceimpl implements PucTypeService {

    @Autowired
    private PucTypeMapper pucTypeMapper;
    @Autowired
    private PscTypeMapper pscTypeMapper;
    @Autowired
    private TypeUtils typeUtils;
    @Autowired
    private SysParameterMapper parameterMapper;


    public PageInfo<PucTypeVo> selectAll(Integer page, Integer size, PucType pucType) throws ServiceException {
        PageHelper.startPage(page,size);
        List<PucTypeVo> pucTypeList = pucTypeMapper.selectALL(pucType);
        PageInfo<PucTypeVo> pageInfo=new PageInfo<PucTypeVo>(pucTypeList);
        return pageInfo;
    }

    @Override
    public int insertPucType(PucType pucType,SysUser user) throws Exception{
        //采购申请类别不为空，且唯一
        String puc = pucType.getPucType();
        String psc = pucType.getPscType();
        this.publicMethod(puc,psc);

        verificationKey(pucType);
        verificationVice(pucType);

        PscType pscType = pscTypeMapper.selectByPrimaryKey(psc);
        typeUtils.insertParameter(puc,pucType,pscType,user,"puc_type;vender_id;");
        return pucTypeMapper.insertPucType(pucType);
    }

    /**
     * 验证采购合同类别是否重复
     */
    public void verificationKey(PucType pucType) {
        List<PucType> _recordList = pucTypeMapper.listByPucType(pucType.getPucType(),"");
        if (PurchaseController.getLambda(pucType.getPucType(), _recordList.stream().map(PucType::getPucType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "采购合同类别已存在");
        }
    }

    /**
     * 验证购销合同类别是否重复
     */
    public void verificationVice(PucType pucType) {
        List<PucType> _recordList = pucTypeMapper.listByPscType(pucType.getPucType(),pucType.getPscType());
        if (PurchaseController.getLambda(pucType.getPscType(), _recordList.stream().map(PucType::getPscType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "购销合同类别已存在");
        }
    }

    @Override
    public int updatePucType(PucType pucType, SysUser user) throws Exception{

        //采购申请类别不为空，且唯一
        String puc = pucType.getPucType();
        String psc = pucType.getPscType();
        this.publicMethod(puc,psc);

        verificationVice(pucType);

        PscType pscType = pscTypeMapper.selectByPrimaryKey(psc);
        typeUtils.insertParameter(puc,pucType,pscType,user,"puc_type;vender_id;");

        return pucTypeMapper.updatePucType(pucType);
    }

    private void publicMethod(String puc,String psc){

        if(StringUtils.isBlank(puc))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"采购合同类别不允许为空");
        }
        if(StringUtils.isBlank(psc))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"购销合同类别不允许为空");
        }
    }

    @Override
    public int deletePucType(PucType pucType, SysUser user) throws ServiceException
    {
        if(pucType.getPucType()==null)
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"采购合同类别为空");
        }
        parameterMapper.updateByType(pucType.getPucType()+ ParameterString.CREAT_TABLE_FILEDS,pucType.getPucType()+ ParameterString.TABLE_NOT_UPDATE,user.getPrsnl().getPrsnlId()+"");

        return pucTypeMapper.deletePucType(pucType);
    }

    @Override
    public List<PurKeyValue> optionGet() {
        return pucTypeMapper.optionGet();
    }

    @Override
    public PscAutoVo selectByPscAuto(String pucType){
        return pucTypeMapper.selectByPscAuto(pucType);
    }

}
