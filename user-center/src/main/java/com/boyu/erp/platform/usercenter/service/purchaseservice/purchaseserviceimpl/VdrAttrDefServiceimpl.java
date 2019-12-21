package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.purchase.VdrAttrDef;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.VdrAttrDefMapper;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.VdrAttrDefService;
import com.boyu.erp.platform.usercenter.vo.goods.ProdAttrDefVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @Classname BgServiceimpl
 * @Description TODO
 * @Date 2019/5/17 16:14
 * @Created wz
 * 供应商属性
 */
@Service
@Transactional
public class VdrAttrDefServiceimpl implements VdrAttrDefService {

    @Autowired
    private VdrAttrDefMapper vdrAttrDefMapper;
    @Autowired
    private SysCodeDtlService codeDtlService;

    public PageInfo<VdrAttrDef> selectAll(Integer page, Integer size, VdrAttrDef vdrAttrDef)throws ServiceException {
        PageHelper.startPage(page,size);
        List<VdrAttrDef> vdrAttrDefList = vdrAttrDefMapper.selectALL(vdrAttrDef);
        PageInfo<VdrAttrDef> pageInfo=new PageInfo<VdrAttrDef>(vdrAttrDefList);
        return pageInfo;
    }

    @Override
    public int insertVdrAttrDef(VdrAttrDef vdrAttrDef) throws ServiceException{

        //颜色编码不为空，且唯一
        String attrType=vdrAttrDef.getAttrType();

        if(StringUtils.isBlank(attrType))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"属性类别不允许为空");
        }

        //构建对象进行业务查询.
        VdrAttrDef  v=new VdrAttrDef();
        v.setAttrType(attrType);
        List<VdrAttrDef> resultList=this.vdrAttrDefMapper.selectALL(v);

        //判断是否存在，只有不存在才可以插入
        if(CollectionUtils.isNotEmpty(resultList))
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"属性类别已经被占用，请修改");
        }



        return vdrAttrDefMapper.insertVdrAttrDef(vdrAttrDef);
    }

    @Override
    public int updateVdrAttrDef(VdrAttrDef vdrAttrDef) throws ServiceException{

        if(vdrAttrDef.getAttrType()==null)
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"属性类别为空");
        }
        return vdrAttrDefMapper.updateVdrAttrDef(vdrAttrDef);
    }

    @Override
    public int deleteVdrAttrDef(VdrAttrDef vdrAttrDef) throws ServiceException
    {
        if(vdrAttrDef.getAttrType()==null)
        {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR,"属性类别为空");
        }
        return vdrAttrDefMapper.deleteVdrAttrDef(vdrAttrDef);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProdAttrDefVo> findByAll() throws ServiceException
    {
        List<ProdAttrDefVo> prodAttrDefVos = vdrAttrDefMapper.getAll();
        List<ProdAttrDefVo> vos = new ArrayList<>();
        for (ProdAttrDefVo vo : prodAttrDefVos) {
            SysCodeDtl dtl = new SysCodeDtl();
            dtl.setCodeType(vo.getEdtFml());
            vo.setCodeDtls(codeDtlService.getAll(dtl));
            vos.add(vo);
        }
        return vos;
    }
}
