package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtlKey;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysCodeDtlMapper;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.system.SysCodeDtlPgVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Classname SysCodeDtlServiceImpl
 * @Description TODO
 * @Date 2019/5/7 18:07
 * @Created by js
 */
@Service
@Transactional
public class SysCodeDtlServiceImpl implements SysCodeDtlService {

    @Autowired
    private SysCodeDtlMapper sysCodeDtlMapper;

    @Override
    public int deleteByPrimaryKey(SysCodeDtl key) {
        return sysCodeDtlMapper.updateByPrimaryKeySelective(key);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<SysCodeDtl> selectAll(Integer page, Integer size, SysCodeDtl dtl) {
        PageHelper.startPage(page, size);
        List<SysCodeDtl> sysCodeDtls = sysCodeDtlMapper.selectAll(dtl);
        PageInfo<SysCodeDtl> pageInfo = new PageInfo<>(sysCodeDtls);
        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysCodeDtl> getAll(SysCodeDtl dtl) {
        return sysCodeDtlMapper.selectAll(dtl);
    }

    @Override
    public int insertSelective(SysCodeDtl record) {
        return sysCodeDtlMapper.insertSelective(record);
    }

    @Override
    @Transactional(readOnly = true)
    public SysCodeDtl selectByPrimaryKey(SysCodeDtlKey key) {
        return sysCodeDtlMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(SysCodeDtl record) {
        return sysCodeDtlMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysCodeDtl> selectAllNoPage(SysCodeDtl dtl) {
        return sysCodeDtlMapper.selectAll(dtl);
    }


    @Override
    public List<PurKeyValue> optionGet(String type) {
        return sysCodeDtlMapper.optionGet(type);
    }

    /**
     * 物理删除 一类code
     */
    @Override
    public int deleteCodeType(SysCodeDtl key) {
        return sysCodeDtlMapper.deleteCodeType(key);
    }

    /**
     * 通过code数组返回type、code、description集合
     * @param strings
     * @return
     */
    @Override
    public List<SysCodeDtlPgVO> selectTCDByType(String[] strings) {
        return sysCodeDtlMapper.selectTCDByType(strings);
    }

    /**
     * 根据数组中的类型返回map集合
     * 字段：code、description
     * @param str
     * @return
     * @author HHe
     */
    @Override
    public List<Map<String, String>> getCodeDtlMap(String str) {
        return sysCodeDtlMapper.getCodeDtlMap(str);
    }

    /**
     * 查询出库单原始单据类别
     * @return
     */
    @Override
    public List<Map<String, String>> getGdnSrcDocType() {
        return sysCodeDtlMapper.getGdnSrcDocType();
    }
    /**
     * 根据类型和指定code查询下拉
     * @author HHe
     */
    @Override
    public List<Map<String, String>> queryPullDownListByTypeAndCodes(String type, String[] codes) {
        return sysCodeDtlMapper.queryPullDownListByTypeAndCodes(type,codes);
    }
    /**
     * 根据code和type查询
     * @author HHe
     * @date 2019/8/28 9:47
     */
    @Override
    public SysCodeDtl queryCodeDtlByCodeType(String code, String type) {
        return sysCodeDtlMapper.queryCodeDtlByCodeType(code, type);
    }
    /**
     * 根据codetype集合查询对应类型的对象集合
     * @author HHe
     * @date 2019/9/24 20:26
     */
    @Override
    public List<SysCodeDtl> queryCodeDtlByTypeSet(Set<String> codetype) {
        return sysCodeDtlMapper.queryCodeDtlByTypeSet(codetype);
    }
    /**
     * 根据code集合和type查询集合
     * @author HHe
     * @date 2019/10/9 14:24
     */
    @Override
    public List<SysCodeDtl> queryCodeDtlByCodesType(List<String> funCodeList, String type) {
        return sysCodeDtlMapper.queryCodeDtlByCodesType(funCodeList,type);
    }


}
