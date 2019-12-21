package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;




import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.controller.purchase.PurchaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsxTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PuaTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PuaTypeService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.purchase.TypeUtils;
import com.boyu.erp.platform.usercenter.vo.purchase.PuaTypeVo;
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
 * @Classname PuaTypeServiceimpl
 * @Description TODO
 * @Date 2019/6/18 19:20
 * @Created wz
 */
@Slf4j
@Service
@Transactional
public class PuaTypeServiceimpl implements PuaTypeService {

    @Autowired
    private PuaTypeMapper puaTypeMapper;
    @Autowired
    private PsxTypeMapper psxTypeMapper;
    @Autowired
    private TypeUtils typeUtils;
    @Autowired
    private SysParameterMapper parameterMapper;


    public PageInfo<PuaTypeVo> selectAll(Integer page, Integer size, PuaTypeVo vo) throws ServiceException {
        PageHelper.startPage(page,size);
        List<PuaTypeVo> puaTypeList = puaTypeMapper.selectALL(vo);
        PageInfo<PuaTypeVo> pageInfo=new PageInfo<PuaTypeVo>(puaTypeList);
        return pageInfo;
    }

    @Override
    public int insertPuaType(PuaTypeVo vo, SysUser user) throws Exception{
        //采购申请类别不为空，且唯一
        String pua = vo.getPuaType();
        String psx = vo.getPsxType();
        this.publicMethod(pua,psx);

        verificationKey(vo);
        verificationVice(vo);

        PsxType psxType = psxTypeMapper.selectByPrimaryKey(psx);
        typeUtils.insertParameter(pua,vo,psxType,user,"pua_type;");

        return puaTypeMapper.insertPuaType(vo);
    }

    /**
     * 验证是否重复
     */
    public void verificationKey(PuaType puaType) {
        List<PuaType> _recordList = puaTypeMapper.listByPuaType(puaType.getPuaType(),"");
        if (PurchaseController.getLambda(puaType.getPuaType(), _recordList.stream().map(PuaType::getPuaType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "采购申请类别已存在");
        }
    }

    /**
     * 验证是否重复
     */
    public void verificationVice(PuaType puaType) {
        List<PuaType> _recordList = puaTypeMapper.listByPsxType(puaType.getPuaType(),puaType.getPsxType());
        if (PurchaseController.getLambda(puaType.getPsxType(), _recordList.stream().map(PuaType::getPsxType).collect(Collectors.toList()))) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "购销申请类别已存在");
        }
    }

    /**
     * 修改
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int updatePuaType(PuaTypeVo vo, SysUser user) throws Exception{

        //采购申请类别不为空，且唯一
        String pua = vo.getPuaType();
        String psx = vo.getPsxType();
        this.publicMethod(pua,psx);

        verificationVice(vo);

        PsxType psxType = psxTypeMapper.selectByPrimaryKey(psx);
        typeUtils.insertParameter(pua,vo,psxType,user,"pua_type;");

        return puaTypeMapper.updatePuaType(vo);
    }

    private void publicMethod(String pua,String psx){

        if(StringUtils.isBlank(pua))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"采购申请类别不允许为空");
        }
        if(StringUtils.isBlank(psx))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"购销申请类别不允许为空");
        }
    }

    /**
     * 删除
     * @param vo
     * @param user
     * @return
     * @throws ServiceException
     */
    @Override
    public int deletePuaType(PuaTypeVo vo, SysUser user) throws ServiceException
    {
        if(vo.getPuaType()==null)
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"采购申请类别为空");
        }
        //修改参数状态
        parameterMapper.updateByType(vo.getPuaType()+ParameterString.CREAT_TABLE_FILEDS,vo.getPuaType()+ParameterString.TABLE_NOT_UPDATE,user.getPrsnl().getPrsnlId()+"");

        return puaTypeMapper.deletePuaType(vo);
    }

    @Override
    public List<PurKeyValue> optionGet() {
        return puaTypeMapper.optionGet();
    }

    @Override
    public PsxType selectByPsxType(PuaType puaType) {
        PuaType pua = puaTypeMapper.selectByPuaType(puaType.getPuaType());
        return psxTypeMapper.selectByPrimaryKey(pua.getPsxType());
    }
}
