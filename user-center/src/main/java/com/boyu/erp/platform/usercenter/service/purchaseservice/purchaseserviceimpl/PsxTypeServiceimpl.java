package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;



import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.entity.sales.SlaType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.purchase.PsxTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PuaTypeMapper;
import com.boyu.erp.platform.usercenter.mapper.sales.SlaTypeMapper;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsxTypeService;
import com.boyu.erp.platform.usercenter.utils.purchase.TypeUtils;
import com.boyu.erp.platform.usercenter.vo.partner.PsxTypeVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PuaTypeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname PsxTypeServiceimpl
 * @Description TODO
 * @Date 2019/6/18 20:05
 * @Created wz
 */
@Service
@Transactional
public class PsxTypeServiceimpl implements PsxTypeService {

    @Autowired
    private PsxTypeMapper psxTypeMapper;
    @Autowired
    private PuaTypeMapper puaTypeMapper;
    @Autowired
    private SlaTypeMapper slaTypeMapper;
    @Autowired
    private TypeUtils typeUtils;

    @Override
    public List<PsxType> optionGet() {
        return psxTypeMapper.optionList();
    }

    /**
     * 分页查询购销申请
     * @param page
     * @param size
     * @param PsxType
     * @return
     */
    @Override
    public PageInfo<PsxTypeVo> selectAll(Integer page, Integer size, PsxType PsxType) {
        PageHelper.startPage(page,size);
        List<PsxTypeVo> psxTypeList = psxTypeMapper.selectALL(PsxType);
        PageInfo<PsxTypeVo> pageInfo=new PageInfo<>(psxTypeList);
        return pageInfo;
    }

    @Override
    public int insertPsxType(PsxType psxType, SysUser user) {
        return psxTypeMapper.insertSelective(psxType);
    }

    /**
     * 修改购销申请的时候，修改系统参数（采购申请/销售申请）
     * @param psxType
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int updatePsxType(PsxType psxType, SysUser user) throws Exception {
        PuaType puaType = puaTypeMapper.selectByPsxType(psxType.getPsxType());
        if(puaType!=null){
            typeUtils.insertParameter(puaType.getPuaType(),puaType,psxType,user,"pua_type;");
        }
        SlaType slaType = slaTypeMapper.selectByPsxType(psxType.getPsxType());
        if(slaType!=null){
            typeUtils.insertParameter(slaType.getSlaType(),slaType,psxType,user,"sla_type;");
        }
        return psxTypeMapper.updateByPrimaryKeySelective(psxType);
    }

    /**
     * 删除购销申请类别
     * @param psxType
     * @return
     */
    @Override
    public int deletePsxType(PsxType psxType) {
        return psxTypeMapper.deleteByPrimaryKey(psxType.getPsxType());
    }


}
