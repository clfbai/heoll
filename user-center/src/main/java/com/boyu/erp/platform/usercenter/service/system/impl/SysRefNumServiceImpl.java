package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNum;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysRefNumDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysRefNumMapper;
import com.boyu.erp.platform.usercenter.redis.redisutils.RedisUtils;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 编号服务
 * @author: clf
 * @create: 2019-05-14 19:26
 */
@Slf4j
@Service
@Transactional
public class SysRefNumServiceImpl implements SysRefNumService {
    @Autowired
    private SysRefNumMapper refNumMapper;
    @Autowired
    private SysRefNumDtlMapper refNumDtlMapper;
    @Autowired
    private SysRefNumDtlMapper sysRefNumDtlMapper;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysRefNum> getAll(Integer page, Integer size, SysRefNum refNum) {
        PageHelper.startPage(page, size);
        List<SysRefNum> list = refNumMapper.selectByPrimary(refNum);
        PageInfo<SysRefNum> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 修改编号
     */
    @Override
    public int updateRefNum(SysRefNum refNum) {
        //打标删除编号需打标删除对应编号明细
        if (refNum.getIsDel().equals(CommonFainl.FAILSTATUS) ) {
            SysRefNumDtl dtl = new SysRefNumDtl();
            dtl.setRefNumId(refNum.getRefNumId());
            List<SysRefNumDtl> list = refNumDtlMapper.selectByPrimary(dtl);
            if (list.size() > 0) {
                for (SysRefNumDtl dtls : list) {
                    dtls.setIsDel(CommonFainl.FAILSTATUS);
                    dtls.setUpdateBy(refNum.getUpdateBy());
                    dtls.setUpdateTime(new Date());
                    refNumDtlMapper.updateByPrimaryKeySelective(dtls);
                }
            }
        }
        return refNumMapper.updateByPrimaryKeySelective(refNum);
    }

    /**
     * 增加编号
     */
    @Override
    public int addRefNum(SysRefNum refNum) {
        return refNumMapper.insertSelective(refNum);
    }

    /**
     * 查询指定编号
     */
    @Override
    @Transactional(readOnly = true)
    public SysRefNum findByRef(SysRefNum refNum) {
        return refNumMapper.findByByRefNum(refNum);
    }

    /**先去查数据库，获取记录，并存入缓存（避免之前异常，导致缓存与数据库最新值不匹配的问题）
     *在更新编号表的时候更新redis缓存
     * 当一个请求完成之后更新编号表记录
     */
    @Override
    public String mainNum(SysUser use, String type) {
        String mainNum = "";
        SysRefNumDtl srnd = new SysRefNumDtl(type);
        SysRefNumDtl sys = sysRefNumDtlMapper.finbyId(srnd);

        SysRefNum sr = new SysRefNum(type);
        SysRefNum sn = refNumMapper.findByByRefNum(sr);
        if (sys != null) {
            if(sys.getPrefix()!=null){
                mainNum = num(sys.getPrefix(),sys.getLastNum() + sn.getStepSize(),sn.getToNum()+"");
            }else{
                mainNum = num("",sys.getLastNum() + sn.getStepSize(),sn.getToNum()+"");
            }

            //更新数据
            srnd = new SysRefNumDtl();
            srnd.setRefNumId(type);
            srnd.setLastNum(sys.getLastNum() + sn.getStepSize());
            srnd.setUpdateBy(use.getPrsnl().getPrsnlId());
            srnd.setUpdateTime(new Date());
            sysRefNumDtlMapper.updateByPrimaryKeySelective(srnd);
        } else {
            srnd.setUnitId(use.getDomain().getUnitId());
            srnd.setIsDel(Byte.valueOf("1"));
            srnd.setLastNum(1L);
            srnd.setCreateBy(use.getPrsnl().getPrsnlId());
            srnd.setCreateTime(new Date());
            sysRefNumDtlMapper.insertSelective(srnd);
            mainNum = num("",1L,sn.getToNum()+"");
        }

        return mainNum;
    }

    @Override
    public String viceNum(SysUser use, String type) {
        //首先去查有没有相应数据 ，有的话就增加，并返回。没有的话去创建
        String viceNum = "";
        SysRefNumDtl s = new SysRefNumDtl();
        s.setRefNumId(type);
        s.setUnitId(use.getDomain().getUnitId());

        SysRefNumDtl sr = sysRefNumDtlMapper.finbyId(s);
        SysRefNum sys = new SysRefNum();
        sys.setRefNumId(type);
        SysRefNum sn = refNumMapper.findByByRefNum(sys);
        if (sr != null) {
            //查出来的不管是不是无效  直接改成有效
            s.setIsDel(Byte.valueOf("1"));
            s.setLastNum(sr.getLastNum() + sn.getStepSize());
            s.setUpdateBy(use.getPrsnl().getPrsnlId());
            s.setUpdateTime(new Date());
            if(sr.getPrefix()!=null){
                viceNum = num(sr.getPrefix(),sr.getLastNum() + sn.getStepSize(),sn.getToNum()+"");

            }else {
                viceNum = num("",sr.getLastNum() + sn.getStepSize(),sn.getToNum()+"");

            }
            sysRefNumDtlMapper.updateByPrimaryKeySelective(s);
        } else {
            s.setUnitId(use.getDomain().getUnitId());
            s.setIsDel(Byte.valueOf("1"));
            s.setLastNum(1L);
            s.setCreateBy(use.getPrsnl().getPrsnlId());
            s.setCreateTime(new Date());
            sysRefNumDtlMapper.insertSelective(s);
            viceNum = num("",1L,sn.getToNum()+"");
        }
        return viceNum;
    }
    /**
     * 根据编号Id查询信息
     * @author HHe
     * @date 2019/9/19 20:13
     */
    @Override
    public SysRefNum querySysRefNumByNumId(String refNumId) {
        return refNumMapper.querySysRefNumByNumId(refNumId);
    }


    private String num(String preFix,Long num,String toNum){
        String value = "";
        //计算前缀的长度
        int preFixNum = 0;
        //计算要填充的长度
        int length = 0;
        //填充的字符
        String fillNum="";

        if(StringUtils.isNotEmpty(preFix)){
            preFixNum = preFix.length()*2;
        }
        length = toNum.length()-preFixNum-num.toString().length();
        if(length>0){
            for (int i=0;i<length;i++){
                fillNum+="0";
            }
            if(StringUtils.isNotEmpty(preFix)){
                value = preFix+fillNum+num;
            }else{
                value = fillNum+num;
            }

        }

        return value;
    }

}
