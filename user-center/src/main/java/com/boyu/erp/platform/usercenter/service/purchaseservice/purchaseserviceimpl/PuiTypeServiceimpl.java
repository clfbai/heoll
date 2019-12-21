package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.purchase.PuiType;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.PuiTypeMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PuiTypeService;
import com.boyu.erp.platform.usercenter.vo.purchase.PuiTypeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname PuiTypeServiceimpl
 * @Description TODO
 * @Date 2019/6/18 14:17
 * @Created wz
 */
@Service
@Transactional
public class PuiTypeServiceimpl implements PuiTypeService {

    @Autowired
    private PuiTypeMapper puiTypeMapper;

    @Override
    public PageInfo<PuiTypeVo> selectAll(Integer page, Integer size, PuiType puiType)throws ServiceException {
        PageHelper.startPage(page,size);
        List<PuiTypeVo> puiTypeList = puiTypeMapper.selectALL(puiType);
        PageInfo<PuiTypeVo> pageInfo=new PageInfo<PuiTypeVo>(puiTypeList);
        return pageInfo;
    }

    @Override
    public int insertPuiType(PuiType puiType) throws ServiceException{
        //采购意向类别不为空，且唯一
        String pui = puiType.getPuiType();
        String psi = puiType.getPsiType();
        this.publicMethod(pui,psi);
        PuiType p=new PuiType();
        p.setPuiType(pui);
        List<PuiTypeVo> resultList=this.puiTypeMapper.selectALL(p);

        //判断采购意向类别是否存在，只有不存在才可以插入
        if(CollectionUtils.isNotEmpty(resultList))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"采购意向类别已经被占用，请修改");
        }
        p=new PuiType();
        p.setPsiType(psi);
        resultList=this.puiTypeMapper.selectALL(p);
        //判断购销意向类别是否存在，只有不存在才可以插入
        if(CollectionUtils.isNotEmpty(resultList))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"购销意向类别已经被占用，请修改");
        }
        return puiTypeMapper.insertPuiType(puiType);
    }

    @Override
    public int updatePuiType(PuiType puiType) throws ServiceException{

        //采购意向类别不为空，且唯一
        String pui = puiType.getPuiType();
        String psi = puiType.getPsiType();
        this.publicMethod(pui,psi);

        PuiType p=new PuiType();
        p.setPsiType(psi);
        p.setPuiType(pui);
        List<PuiTypeVo> resultList=this.puiTypeMapper.selectOnePsi(p);
        //判断是否存在，只有不存在才可以插入
        if(CollectionUtils.isNotEmpty(resultList))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"购销意向类别已经被占用，请修改");
        }

        return puiTypeMapper.updatePuiType(puiType);
    }

    private void publicMethod(String pui,String psi){

        if(StringUtils.isBlank(pui))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"采购意向类别不允许为空");
        }
        if(StringUtils.isBlank(psi))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"购销意向类别不允许为空");
        }
    }

    @Override
    public int deletePuiType(PuiType puiType) throws ServiceException
    {
        if(puiType.getPuiType()==null)
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"采购意向类别为空");
        }
        return puiTypeMapper.deletePuiType(puiType);
    }
}
