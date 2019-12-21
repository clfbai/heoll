package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtlKey;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.system.SysCodeDtlPgVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Classname SysCodeDtlService
 * @Description TODO
 * @Date 2019/5/7 18:06
 * @Created by js
 */
public interface SysCodeDtlService {

    int deleteByPrimaryKey(SysCodeDtl key);

    PageInfo<SysCodeDtl> selectAll(Integer page, Integer size, SysCodeDtl dtl);

    //不分页
    List<SysCodeDtl> getAll(SysCodeDtl dtl);

    int insertSelective(SysCodeDtl record);
    /**
     * 查询某一个CodeDtl
     */
    SysCodeDtl selectByPrimaryKey(SysCodeDtlKey key);

    int updateByPrimaryKeySelective(SysCodeDtl record);

    List<SysCodeDtl> selectAllNoPage(SysCodeDtl dtl);

    List<PurKeyValue> optionGet(String type);

    /**
     * 物理删除 一类code
     */
    int deleteCodeType(SysCodeDtl key);

    /**
     * 通过code数组返回type、code、description集合
     * @param strings
     * @return
     */
    List<SysCodeDtlPgVO> selectTCDByType(String[] strings);

    /**
     * 根据数组中的类型返回map集合
     * 字段：code、description
     * @param str
     * @author HHe
     */
    List<Map<String, String>> getCodeDtlMap(String str);

    /**
     * 查询出库单原始单据类别
     * @return
     */
    List<Map<String, String>> getGdnSrcDocType();

    /**
     * 根据类型和指定code查询下拉
     * @author HHe
     */
    List<Map<String, String>> queryPullDownListByTypeAndCodes(String s, String[] strings);
    /**
     * 根据code和type查询
     * @author HHe
     * @date 2019/8/28 9:47
     */
    SysCodeDtl queryCodeDtlByCodeType(String code, String type);
    /**
     * 根据codetype集合查询对应类型的对象集合
     * @author HHe
     * @date 2019/9/24 20:26
     */
    List<SysCodeDtl> queryCodeDtlByTypeSet(Set<String> codetype);
    /**
     * 根据code集合和type查询集合
     * @author HHe
     * @date 2019/10/9 14:24
     */
    List<SysCodeDtl> queryCodeDtlByCodesType(List<String> funCodeList, String type);
}
