package com.boyu.erp.platform.usercenter.service.collarUseservice.collarUseserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.controller.purchase.PurchaseController;
import com.boyu.erp.platform.usercenter.entity.collarUse.ArqType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.collarUse.ArqTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.collarUseservice.ArqTypeService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.purchase.TypeUtils;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: ArqTypeServerImpl
 * @description: 领用单类别
 * @author: wz
 * @create: 2019-8-23 12:19
 */
@Service
@Transactional
public class ArqTypeServerImpl implements ArqTypeService {

    @Autowired
    private ArqTypeMapper arqTypeMapper;
    @Autowired
    private TypeUtils typeUtils;
    @Autowired
    private SysParameterMapper parameterMapper;

    /**
     * 查询领用单类别
     * @param page
     * @param size
     * @param arqType
     * @return
     */
    @Override
    public PageInfo<ArqType> selectAll(Integer page, Integer size, ArqType arqType) {
        PageHelper.startPage(page,size);
        List<ArqType> arqTypeList = arqTypeMapper.selectALL(arqType);
        PageInfo<ArqType> pageInfo=new PageInfo<>(arqTypeList);
        return pageInfo;
    }

    /**
     * 添加领用单类别
     * @param arqType
     * @return
     */
    @Override
    public int insertArqType(ArqType arqType, SysUser user) throws Exception {
        //验证领用单类别是否重复
        verificationKey(arqType);

        typeUtils.insertParameter(arqType.getArqType(),arqType,user,"arq_type;");

        return arqTypeMapper.insertSelective(arqType);
    }

    @Override
    public int updateArqType(ArqType arqType, SysUser user) throws Exception {
        typeUtils.insertParameter(arqType.getArqType(),arqType,user,"arq_type;");
        return arqTypeMapper.updateByPrimaryKeySelective(arqType);
    }

    @Override
    public int deleteArqType(ArqType arqType, SysUser user) {
        //修改参数状态
        parameterMapper.updateByType(arqType.getArqType()+ParameterString.CREAT_TABLE_FILEDS,arqType.getArqType()+ParameterString.TABLE_NOT_UPDATE,user.getPrsnl().getPrsnlId()+"");
        return arqTypeMapper.deleteByPrimaryKey(arqType.getArqType());
    }

    /**
     * 领用单中下拉
     * @return
     */
    @Override
    public List<PurKeyValue> optionGet() {
        return arqTypeMapper.optionGet();
    }

    /**
     * 验证领用单类别是否重复
     */
    public void verificationKey(ArqType arqType) {
        List<ArqType> _recordList = arqTypeMapper.listByArqType(arqType.getArqType());
        if (PurchaseController.getLambda(arqType.getArqType(), _recordList.stream().map(ArqType::getArqType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "领用单类别已存在");
        }
    }
}
