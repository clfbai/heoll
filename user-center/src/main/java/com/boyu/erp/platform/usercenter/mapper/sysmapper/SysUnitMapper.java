package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.model.system.UnitScopeModel;
import com.boyu.erp.platform.usercenter.vo.DomainAndUnitVo;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.system.SysUnitPgVO;
import com.boyu.erp.platform.usercenter.vo.system.UnitDomainVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysUnitMapper {

    public List<DomainAndUnitVo> selectAll(SysUnit unit);

    public SysUnit selectByPrimaryKey(long unitId);

    public int updateByPrimaryKeySelective(SysUnit record);

    public int insertSelective(SysUnit record);

    public List<SysUnit> selectCodeAndName(SysUnit unit);

    /**
     * 通过层级关系查询下属组织
     */
    List<SysUnit> findHierarchy(SysUnit unit);

    /**
     * 通过层级关系查询下属组织
     */
    List<SysUnit> findHierarchyByType(String unitType,String unitId,String unitHierarchy);

    /**
     * 通过组织Id查询下属组织
     */
    List<SysUnit> getUnitId(Long unitId);


    int countDomainCode(String unitCode);

    /**
     * 通过组织Id 查询组织管理员用户
     */
    SysUser selectByunitAdmin(long unitId);

    /**
     * 购销协议弹窗
     *
     * @param vo
     * @return
     */
    List<UnitOptionVo> selectByOption(UnitOptionVo vo);

    /**
     * 采购商/供应商代码弹窗
     *
     * @param vo
     * @return
     */
    List<UnitOptionVo> selectByOptionCode(UnitOptionVo vo);

    /**
     * 购销协议中输入编号查询相关数据
     * @param vo
     * @return
     */
    UnitOptionVo findOptionByNum(UnitOptionVo vo);

    /**
     * 采购协议弹窗
     * 系统管理员
     *
     * @param vo
     * @return
     */
    List<UnitOptionVo> selectByAll(UnitOptionVo vo);

    /**
     * 供应商弹窗
     *
     * @param
     * @return
     */
    List<UnitOptionVo> selectByOptionAll(UnitOptionVo vo);

    /**
     * 供应商查询
     */
    List<SysUnit> findAll(SysUnit unit);

    /**
     * 厂商、设计方查询
     */
    List<SysUnit> getAll(SysUnit unit);

    /**
     * 供应商/采购商页面中添加
     */
    int insertByVd(Map<String, Object> map);

    /**
     * 通过组织代码查询组织
     *
     * @param unitCode
     * @return
     */
    SysUnit selectByUnitCode(String unitCode);

    /**
     * 供应商/采购商页面中修改
     */
    int updateByVd(Map<String, Object> map);

    /**
     * 采购合同弹窗 不需要权限范围
     *
     * @param vo
     * @return
     */
    List<UnitOptionVo> selectByPucOption(UnitOptionVo vo);

    /**
     * 功能描述: 查询系统内所有的组织(过滤的组织)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/9 14:40
     */
    List<UnitDomainVo> getUnitScope(UnitDomainVo vo);

    /**
     * 功能描述: 查询当前用户组织信息范围
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/10 11:29
     */
    List<UnitDomainVo> getSaveScope(UnitScopeModel model);

    List<SysUnitPgVO> selectAllICN();

    /**
     * 功能描述: 通过层级查找组织
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/12 11:02
     */
    SysUnit getHierarchy(String str);


    /**
     * 功能描述: 根据组织Id查询是否是系统组织
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/14 15:24
     */
    SysUnit getAdminUnit(Long unitId);

    /**
     * 功能描述: 根据属主Id查询组织
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/21 12:09
     */
    List<SysUnit> findHierarchyUnit(SysUnit unit);
    /**
     * 判断仓库编号是否可用
     * @author HHe
     * @date 2019/8/24 10:04
     */
    UnitOptionVo judgewarehavailable(@Param("warehCode") String warehCode, @Param("ownerId") Long ownerId);
    /**
     * 根据code判断组织是否存在
     * @author HHe
     * @date 2019/8/26 11:02
     */
    SysUnit queryUnitByCode(String warehCode);
   /**
    *
    * 功能描述: 查询属主Id下组织弹窗(通用)
    *
    * @param:
    * @return:
    * @auther: CLF
    * @date: 2019/8/27 10:27
    */
    List<UnitOptionVo> selectUnitOption(SysUnit unit);

    /**
     * 查询组织集合的下级采购商
     * @param map
     * @return
     */
    List<SysUnit> findByHierarchyList(Map<String,Object> map);
    /**
     * 根据code模糊查询出对象集合
     * @author HHe
     * @date 2019/9/11 10:23
     */
    List<SysUnit> queryUnitByConCatCode(@Param("warehCode") String warehCode);
    /**
     * 根据id查询集合
     * @author HHe
     * @date 2019/9/11 14:52
     */
    List<SysUnit> queryUnitByIds(@Param("sysUnitIds") Set<Long> sysUnitIds);
    /**
     * 模糊查询组织
     * @author HHe
     * @date 2019/9/15 15:37
     */
    List<SysUnit> queryUnitLikeProp(SysUnit sysUnit);
   /**
    *
    * 功能描述: 通用弹窗查询
    *
    * @param:
    * @return:
    * @auther: CLF
    * @date: 2019/9/23 14:47
    */
   SysUnit selectObject(CommonWindowModel model);

    /**
     * 往来账户输入编号查询
     * @param model
     * @return
     */
   SysUnit selectCaObject(CommonWindowModel model);

    /**
     * 购销协议中输入编号查询
     * @param model
     * @return
     */
    SysUnit selectPsaObject(CommonWindowModel model);

    /**
     * 超级管理员往来账户查询
     * @param vo
     * @return
     */
    List<UnitOptionVo> selectByCaAll(UnitOptionVo vo);

    /**
     * 用户查询
     * @param vo
     * @return
     */
    List<UnitOptionVo> selectByCaOption(UnitOptionVo vo);

    /**
     * 通过供应商和单据类别查询期货仓或现货仓
     * @param orderType
     * @param ownerId
     * @return
     */
    List<SysUnit> findWarehByOrder(@Param("orderType") int orderType,@Param("ownerId") String ownerId);

    /**
     * 通过商品id去查询商品的存储仓库
     * @param prodId
     * @return
     */
    Long fidnByProdLine(@Param("prodId") String prodId);
}