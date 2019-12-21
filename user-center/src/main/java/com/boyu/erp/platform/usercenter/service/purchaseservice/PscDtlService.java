package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;

import java.text.ParseException;
import java.util.List;

/**
 * @Classname PscDtlService
 * @Description TODO
 * @Date 2019/7/8 12:17
 * @Created wz
 */
public interface PscDtlService {

    //查询
    public List<PscDtlVo> findByPscNum(PscDtlVo vo);

    //新增
    int insertPscDtl(PscDtlVo vo, SysUser sysUser) throws Exception;

    //修改
    int updatePscDtl(PscDtlVo vo, SysUser sysUser);

    //删除
    int deletePscDtl(PscDtlVo vo, SysUser sysUser);

    //更换类别以及供应商时删除明细
    int deletePscDtl(List<PscDtlVo> vo, SysUser sysUser);

    //新增采购合同时，增加明细
    PscVo insertByPsc(PscVo v, SysUser user) throws Exception;

    /**
     * 删除采购合同的时候如果删除购销就删除明细
     * @param pscNum
     * @param sysUser
     */
    void deleteByPuc(String pscNum, SysUser sysUser);

    /**
     * 采购单中选择商品后判断返回
     * @param vo
     * @return
     */
    List<PscDtlVo> getJudgeDtlByPuo(PscDtlVo vo);

    /**
     * 销售单中选择商品后判断返回
     * @param vo
     * @return
     */
    List<PscDtlVo> getJudgeDtlBySlo(PscDtlVo vo);

    /**
     * 采购合同中判断是否继续收货
     * @param pscNum
     * @return
     */
    List<PscDtlVo> judge(String pscNum);

}
