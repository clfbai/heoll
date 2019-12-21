package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbDtlVo;
import com.github.pagehelper.PageInfo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 退销合同类别接口
 * @author: ck
 * @create: 2019-06-20 15:36
 */
public interface StbDtlService {

    public PageInfo<StbDtlVo> getStbDtlList(Integer page, Integer size, StbDtl tfnDtl) throws ServiceException;

    public int insert(StbDtl stbDtl) throws ServiceException;

    public List<StbDtlVo> getStbDtlList(StbDtl tfnDtl) throws ServiceException;

    public int update(StbDtl stbDtl) throws ServiceException;

    public int delete(StbDtl stbDtl) throws ServiceException;

    public int deleteAll(StbDtl stbDtl) throws ServiceException;

    int insertStbDtlVO(List<StbDtlVo> stbDtlVos, String alwaysNum,SysUser sysUser);

    /**
     * 出库任务生成出库单，添加库存明细。
     * @author HHe
     */
    int insrtStbDtl(List<StbDtlVo> stbDtlVos, String stbNum, SysUser sysUser);

    /**
     * 根据子库存单编号集合查询明细集合
     * @author HHe
     */
    List<StbDtlVo> queryTotStbDtlsBySonStbNums(List<String> sonStbNums,Long ownerId) throws InvocationTargetException, NoSuchMethodException, IntrospectionException, NoSuchFieldException, IllegalAccessException;

    /**
     * 根据库存单编号查询明细集合
     * @author HHe
     */
    List<StbDtlVo> queryStbDtlsByStbNums(String stbNum,Long ownerId) throws InvocationTargetException, NoSuchMethodException, IntrospectionException, NoSuchFieldException, IllegalAccessException;


    /**
     * 根据用户输入数量计算库存明细数据
     * @author HHe
     * @date 2019/8/28 20:22
     */
    StbDtlVo getDtlMess(StbDtlVo stbDtlVo, SysUser sysUser);

    /**
     * 添加商品集合
     * @author HHe
     * @date 2019/9/5 20:37
     */
    int insertStbDtlList(List<StbDtl> stbDtls);
    /**
     * 添加库存明细集合
     * @author HHe
     * @date 2019/10/14 15:42
     */
    int insertStbDtlListAndUnitId(List<StbDtl> stbDtlList, SysUser sysUser);
    /**
     * 根据出库单编号和组织Id查询出库单明细
     * @author HHe
     * @date 2019/10/21 10:35
     */
    List<StbDtl> queryStbDtlsByNumAndUnit(String stbNum, Long unitId);
    /**
     * 修改库存明细成本集合
     * @param stbDtls 需要修改明细
     * @return 数据库操作数
     * @author HHe
     * @date 2019/10/26 9:45
     */
    int updateStbDtlCostList(List<StbDtl> stbDtls);
    /**
     * 删除库存单明细
     * @param stbDtl 组织Id、库存单编号
     * @return 数据库操作数
     * @author HHe
     * @date 2019/11/6 17:24
     */
    int delByNumAndId(StbDtl stbDtl);
    /**
     * 查询明细数量
     * @param stbDtl 组织Id，库存单编号
     * @return 数量
     * @author HHe
     * @date 2019/12/2 11:54
     */
    Long queryDtlCountByObj(StbDtl stbDtl);
    /**
     * 添加库存明细
     * @param stbDtl 添加明细
     * @return 数据库操作数
     * @author HHe
     * @date 2019/12/2 17:32
     */
    int insertStbDtl(StbDtl stbDtl);
    /**
     * 修改明细
     * @param stbDtl 修改对象
     * @return 数据库操作数
     * @author HHe
     * @date 2019/12/3 9:53
     */
    int updateStbDtl(StbDtl stbDtl);
    /**
     * 修改出库单明细
     * @param stbDtlList 需修改集合
     * @return 数据库操作数
     * @author HHe
     * @date 2019/12/5 12:11
     */
    int updateStbDtlList(List<StbDtl> stbDtlList);
}
