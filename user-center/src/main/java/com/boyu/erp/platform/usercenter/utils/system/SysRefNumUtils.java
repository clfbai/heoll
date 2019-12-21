package com.boyu.erp.platform.usercenter.utils.system;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNum;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumDtlSerivce;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.utils.common.BaseDateUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.UnitBatchUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: boyu-platform_text
 * @description: 编号工具类
 * @author: clf
 * @create: 2019-07-05 15:13
 */
@Slf4j
@Component
public class SysRefNumUtils {
    @Autowired
    private SysRefNumDtlSerivce refNumDtlSerivce;
    @Autowired
    private SysRefNumService refNumService;
    @Autowired
    private UnitBatchUtils unitBatchUtils;

    /**
     * 商品序号增加编号
     *
     * @Param :u 商品批次号
     * @Param :unitId 组织Id
     * 1.先查询是否存在组织编号明细
     * 2.存在根据增量修改
     * 3.不存在增加编号明细
     */
    public int instrSysRefNum(String str, SysUser user) throws Exception {
        int a = 0;
        if (getRefNum(str) == null) {
            a = addRefNum(str, user);
        }
        return a;
    }

    /**
     * 商品序号增加编号明细
     *
     * @Param :refNumId 编号Id
     * @Param :unitId 组织Id
     * 1.先查询是否存在组织编号明细
     * 2.存在根据增量修改
     * 3.不存在增加编号明细
     */
    public int instrSysRefNumDtl(String refNumId, Long unitId, SysUser user) throws Exception {

        if (getRefNumDtl(refNumId, unitId) == null) {
            return addRefNumDtl(refNumId, unitId, user);
        }

        return updateRefNumDtl(refNumId, unitId, user);

    }

    /**
     * 查看批次号
     */
    public SysRefNum getRefNum(String str) {
        SysRefNum refNum = new SysRefNum();
        refNum.setRefNumId(str);
        return refNumService.findByRef(refNum);
    }


    /**
     * 查看批次号明细
     */
    public SysRefNumDtl getRefNumDtl(String str, Long unitId) {
        SysRefNumDtl refNumDtl = new SysRefNumDtl();
        refNumDtl.setRefNumId(str);
        refNumDtl.setUnitId(unitId);
        return refNumDtlSerivce.findById(refNumDtl);
    }

    /**
     * 增加批次编号
     *
     * @Param : 这里 str 指定是商品Id 的批次号（可以是任何编号)
     */
    public int addRefNum(String str, SysUser user) throws Exception {
        SysRefNum refNum = new SysRefNum();
        refNum.setRefNumId(str);
        refNum.setDescription("增加商品批次号序号");
        //起始编号和增量默认都是1 只设置结束编号
        refNum.setToNum(8999999999999999999L);
        BaseDateUtils.setBeasDate(refNum, user, CommonFainl.ADD);
        return refNumService.addRefNum(refNum);
    }

    /**
     * 增加批次编号明细
     *
     * @Param : 这里 str 指定是商品Id生成的唯一编号 （可以是任何编号)
     */
    public int addRefNumDtl(String str, Long unitId, SysUser user) throws Exception {
        SysRefNumDtl refNumDtl = new SysRefNumDtl();
        refNumDtl.setRefNumId(str);
        refNumDtl.setUnitId(unitId);
        refNumDtl.setLastNum(getRefNum(str).getFromNum() + getRefNum(str).getStepSize());
        //起始编号和增量默认都是1 只设置结束编号
        BaseDateUtils.setBeasDate(refNumDtl, user, CommonFainl.ADD);
        return refNumDtlSerivce.addRefNumDtl(refNumDtl);
    }

    /**
     * 修改编号明细(修改最新编号)
     *
     * @Param : 这里 str 指定是商品Id生成的唯一编号 （可以是任何编号)
     */
    public int updateRefNumDtl(String str, Long unitId, SysUser user) throws Exception {
        SysRefNumDtl refNumDtl = getRefNumDtl(str, unitId);
        //修改最新编号
        refNumDtl.setLastNum(refNumDtl.getLastNum() + getRefNum(str).getStepSize());
        //起始编号和增量默认都是1 只设置结束编号
        BaseDateUtils.setBeasDate(refNumDtl, user, CommonFainl.UPDATE);
        return refNumDtlSerivce.updateRefNumDtl(refNumDtl);
    }

    /**
     * 查询最新编号
     */
    public Long getLastNum(String str, Long unitId) {
        SysRefNumDtl refNumDtl = getRefNumDtl(str, unitId);
        return refNumDtlSerivce.selectLum(refNumDtl);
    }

}
