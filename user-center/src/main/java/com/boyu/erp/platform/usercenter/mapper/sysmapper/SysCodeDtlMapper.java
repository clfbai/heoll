package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtlKey;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.boyu.erp.platform.usercenter.vo.system.SysCodeDtlPgVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 代码明细
 */
public interface SysCodeDtlMapper {
    int deleteByPrimaryKey(SysCodeDtlKey key);

    int deleteCodeType(SysCodeDtlKey key);

    /**
     * 查询代码明细(不查询打标删除的代码)
     */
    List<SysCodeDtl> selectAll(SysCodeDtl dtl);

    /**
     * 查询代码明细初始化(查询所包含删除代码 ，下拉初始化 )
     */
    List<SysCodeDtl> selectAllIsDel(SysCodeDtl dtl);

    int insertSelective(SysCodeDtl record);

    SysCodeDtl selectByPrimaryKey(SysCodeDtlKey key);

    /**
     * 通过 codeType 和 code  修改代码明细
     */
    int updateByPrimaryKeySelective(SysCodeDtl record);

    List<PurKeyValue> optionGet(String type);


    List<SysCodeDtlPgVO> selectTCDByType(String[] strings);

    /**
     * 根据类型字符串生成map
     *
     * @param str
     * @return
     * @author HHe
     */
    List<Map<String, String>> getCodeDtlMap(String str);

    /**
     * 出库原始单据类型
     *
     * @return
     */
    List<Map<String, String>> getGdnSrcDocType();

    List<Map<String, String>> queryPullDownListByTypeAndCodes(@Param("type") String type, @Param("codes") String[] codes);
    /**
     * 根据code和type查询
     * @author HHe
     * @date 2019/8/28 9:47
     */
    SysCodeDtl queryCodeDtlByCodeType(@Param("code") String code, @Param("type") String type);
    /**
     * 根据codetype集合查询对应类型的对象集合
     * @author HHe
     * @date 2019/9/24 20:27
     */
    List<SysCodeDtl> queryCodeDtlByTypeSet(@Param("codetypeSet") Set<String> codetypeSet);
    /**
     * 根据code集合和type查询集合
     * @author HHe
     * @date 2019/10/9 14:25
     */
    List<SysCodeDtl> queryCodeDtlByCodesType(@Param("funCodeList") List<String> funCodeList, @Param("type") String type);
}