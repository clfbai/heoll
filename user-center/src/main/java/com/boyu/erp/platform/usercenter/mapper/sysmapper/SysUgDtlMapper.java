package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysUg;
import com.boyu.erp.platform.usercenter.entity.system.SysUgDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysUgDtlMapper {
    int deleteByPrimaryKey(SysUgDtl key);

    int insert(SysUgDtl record);

    int insertSelective(SysUgDtl record);


    List<SysUgDtl> getUgDtl(SysUgDtl ugDtl);

    /**
     * 类描述: 批量增加分组明细
     *
     * @Description:
     * @auther: CLF
     * @date: 2019/8/8 14:48
     */
    int addUgDtlList(@Param("list") List<SysUgDtl> list);


    int deleteUgDtlList(@Param("list") List<SysUgDtl> deleteUgDtl);

    /**
     * 功能描述:  删除组织分组时删除明细
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 18:38
     */
    int deleteUg(Long ugId);

    /**
     * 功能描述: 通过组织Id查询有多少下级组织
     * (主要用来判断分组内组织能否授予用户)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/13 12:37
     */
    List<SysUnit> getUgList(Long unitId);

    /**
     * 功能描述: 增加分组明细会删除所有拥有该分组权限(简单处理防止权限越界)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/13 13:54
     */
    int deleteUgPriv(SysUg ug);
    /**
     *
     * 功能描述:
     *
     * @param: 判断分组是否授予给用户
     * @return:
     * @auther: CLF
     * @date: 2019/8/13 14:13
     */
    int  getCountUg(SysUg ug);
    /**
     * 根据组织分组Id集合查询成员集合
     * @author HHe
     * @date 2019/11/26 11:18
     */
    List<Long> queryMkrIdsByUgIds(@Param("unitUgIds") List<Long> unitUgIds);
}