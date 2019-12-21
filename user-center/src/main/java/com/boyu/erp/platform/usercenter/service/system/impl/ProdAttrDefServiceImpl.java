package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.goods.ProdAttrDef;
import com.boyu.erp.platform.usercenter.entity.system.SysCode;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdAttrDefMapper;
import com.boyu.erp.platform.usercenter.model.goods.ProdAttrDefModel;
import com.boyu.erp.platform.usercenter.service.system.ProdAttrDefService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeService;
import com.boyu.erp.platform.usercenter.utils.common.BaseDateUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.goods.ProdAttrDefVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProdAttrDefServiceImpl implements ProdAttrDefService {

    @Autowired
    private ProdAttrDefMapper prodAttrDefMapper;

    @Autowired
    private SysCodeDtlService codeDtlService;

    @Autowired
    private SysCodeService codeService;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<ProdAttrDef> selectAll(Integer page, Integer size, ProdAttrDef record) {
        PageHelper.startPage(page, size);
        List<ProdAttrDef> prodAttrDefs = prodAttrDefMapper.selectAll(record);
        PageInfo<ProdAttrDef> prodAttrDefPageInfo = new PageInfo<>(prodAttrDefs);
        return prodAttrDefPageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public ProdAttrDef selectByPrimaryKey(ProdAttrDef prodAttrDef) {
        return prodAttrDefMapper.selectByPrimaryKey(prodAttrDef);
    }

    /**
     * 添加自定义属性
     * 1. 添加自定义属性
     * 2. 判断是否添加 sys_code
     */
    @Override
    public int insertProdAttrDef(ProdAttrDefModel prodAttrDefModel, SysUser user) throws Exception {
        int s = updateAttrDef(prodAttrDefModel, user, CommonFainl.ADD);
        ProdAttrDef prodAttrDef = new ProdAttrDef();
        BeanUtils.copyProperties(prodAttrDefModel, prodAttrDef);
        return s + prodAttrDefMapper.insertSelective(prodAttrDef);
    }


    /**
     * 修改自定义属性
     * 1. 修改自定义属性
     * 2. 判断是删除 sys_code和sys_code_dtl表
     */
    @Override
    public int updateProdAttrDef(ProdAttrDefModel prodAttrDefModel, SysUser user) throws Exception {
        ProdAttrDef prodAttrDef = new ProdAttrDef();
        BeanUtils.copyProperties(prodAttrDefModel, prodAttrDef);

        int a = updateAttrDef(prodAttrDefModel, user, CommonFainl.UPDATE);
        if (StringUtils.isNotBlank(prodAttrDefModel.getUpdateisOpetion())) {
            prodAttrDef.setIsOpetion(prodAttrDefModel.getUpdateisOpetion());
        }
        return a + prodAttrDefMapper.updateByPrimaryKeySelective(prodAttrDef);
    }

    @Override
    public int deleteProdAttrDef(ProdAttrDefModel prodAttrDefModel, SysUser user) throws Exception {
        int a = updateAttrDef(prodAttrDefModel, user, CommonFainl.DELETE);
        ProdAttrDef prodAttrDef = new ProdAttrDef();
        prodAttrDef.setAttrType(prodAttrDefModel.getAttrType());
        return a + prodAttrDefMapper.deleteByPrimaryKey(prodAttrDef);
    }

    /**
     * 查询所有自定义属性 属性类别对应属性名称
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProdAttrDefVo> getAttrAndName() {
        List<ProdAttrDefVo> prodAttrDefVos = prodAttrDefMapper.getAllProdAttrDef();
        List<ProdAttrDefVo> vos = new ArrayList<>();
        for (ProdAttrDefVo vo : prodAttrDefVos) {
            if (vo.getAttrType().equalsIgnoreCase("UPLOAD_HM")) {
                vo.setAmend("T");
            } else {
                vo.setAmend("F");
            }
            SysCodeDtl dtl = new SysCodeDtl();
            dtl.setCodeType(vo.getAttrType());
            vo.setCodeDtls(codeDtlService.getAll(dtl));
            vos.add(vo);
        }
        return vos;
    }


    public int updateAttrDef(ProdAttrDefModel prodAttrDefModel, SysUser user, String str) throws Exception {
        int a = 0;
        if (CommonFainl.ADD.equalsIgnoreCase(str) && CommonFainl.TRUE.equalsIgnoreCase(prodAttrDefModel.getIsOpetion()) ||
                CommonFainl.UPDATE.equalsIgnoreCase(str) && CommonFainl.TRUE.equalsIgnoreCase(prodAttrDefModel.getUpdateisOpetion())
                        && CommonFainl.FALSE.equalsIgnoreCase(prodAttrDefModel.getIsOpetion())) {
            SysCode code = new SysCode();
            code.setCodeType(prodAttrDefModel.getAttrType());
            code.setDescription(prodAttrDefModel.getAttrName());
            BaseDateUtils.setBeasDate(code, user, CommonFainl.ADD);
            a = codeService.insertSysCode(code);
        }
        if (CommonFainl.UPDATE.equalsIgnoreCase(str)
                && CommonFainl.TRUE.equalsIgnoreCase(prodAttrDefModel.getIsOpetion())
                && CommonFainl.FALSE.equalsIgnoreCase(prodAttrDefModel.getUpdateisOpetion()) || CommonFainl.DELETE.equalsIgnoreCase(str)) {
            /**
             * 将下拉改为非下拉 或者删除自定义属性
             * */
            SysCode code = new SysCode();
            SysCodeDtl codeDtl = new SysCodeDtl();
            codeDtl.setCodeType(prodAttrDefModel.getAttrType());
            codeDtl.setIsDel(CommonFainl.FAILSTATUS);
            code.setCodeType(prodAttrDefModel.getAttrType());
            code.setIsDel(CommonFainl.FAILSTATUS);
            a += codeService.updateSysCode(code);
            //将原本的删除改为打标删除
            //a = codeService.deleteByPrimaryKey(code);
            //a += codeDtlService.deleteCodeType(codeDtl);
        }

        return a;
    }
}
