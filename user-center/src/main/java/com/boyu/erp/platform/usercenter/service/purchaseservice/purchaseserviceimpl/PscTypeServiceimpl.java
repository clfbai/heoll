package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.entity.purchase.PscType;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.entity.purchase.PucType;
import com.boyu.erp.platform.usercenter.entity.sales.SlcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.purchase.PscTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PucTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SlcTypeMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscTypeService;
import com.boyu.erp.platform.usercenter.utils.purchase.TypeUtils;
import com.boyu.erp.platform.usercenter.vo.partner.PscTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname PuiTypeServiceimpl
 * @Description TODO
 * @Date 2019/6/19 12:13
 * @Created wz
 */
@Service
@Transactional
public class PscTypeServiceimpl implements PscTypeService {

    @Autowired
    private PscTypeMapper pscTypeMapper;
    @Autowired
    private PucTypeMapper pucTypeMapper;
    @Autowired
    private SlcTypeMapper slcTypeMapper;
    @Autowired
    private TypeUtils typeUtils;

    @Override
    public List<PurKeyValue> optionGet() {
        return pscTypeMapper.optionGet();
    }

    /**
     * 查询购销合同
     * @param page
     * @param size
     * @param pscType
     * @return
     */
    @Override
    public PageInfo<PscTypeVo> selectAll(Integer page, Integer size, PscType pscType) {
        PageHelper.startPage(page, size);
        List<PscTypeVo> pscTypeList = pscTypeMapper.selectALL(pscType);
        PageInfo<PscTypeVo> pageInfo = new PageInfo<>(pscTypeList);
        return pageInfo;
    }

    /**
     * 添加购销合同
     * @param pscType
     * @param user
     * @return
     */
    @Override
    public int insertPscType(PscType pscType, SysUser user) {
        return pscTypeMapper.insertSelective(pscType);
    }

    /**
     * 更新购销合同
     * @param pscType
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int updatePscType(PscType pscType, SysUser user) throws Exception {
        PucType pucType = pucTypeMapper.selectByPscType(pscType.getPscType());
        if (pucType != null) {
            typeUtils.insertParameter(pucType.getPucType(), pucType, pscType, user, "puc_type;vender_id;");
        }
        SlcType slcType = slcTypeMapper.selecyByPscType(pscType.getPscType());
        if (slcType != null) {
            typeUtils.insertParameter(slcType.getSlcType(), slcType, pscType, user, "slc_type;vendee_id;");
        }
        return pscTypeMapper.updateByPrimaryKeySelective(pscType);
    }

    /**
     * 删除购销合同
     * @param pscType
     * @return
     */
    @Override
    public int deletePscType(PscType pscType) {
        return pscTypeMapper.deleteByPrimaryKey(pscType.getPscType());
    }

}
