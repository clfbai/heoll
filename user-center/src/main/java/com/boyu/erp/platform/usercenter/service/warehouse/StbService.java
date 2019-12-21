package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.warehouse.GdnListVO;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbGdnVO;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 库存单接口
 * @author: ck
 * @create: 2019-06-20 15:36
 */
public interface StbService {

    int insert(GrnVo grnVo) throws ServiceException;

    Stb getStbByPk(Stb stb) throws ServiceException;

    int update(Stb stb) throws ServiceException;

    /**
     * 查询编号
     */
    List<Map<String, String>> getStbNum(Long unitId);

    /**
     * 生成库存单（PLAN2）
     *
     * @param stbList
     * @param alwaysNum 总单编号
     * @return
     */
    int insertStbByGdn(List<StbGdnVO> stbList, String alwaysNum) throws IllegalAccessException, IntrospectionException, InvocationTargetException;

    /**
     * 添加库存出库单
     *
     * @param stbGdnVO
     * @return
     */
    String insertStbGdn(StbGdnVO stbGdnVO, SysUser sysUser);

    /**
     * 修改出库库存单
     *
     * @param gdnListVO
     * @return
     */
    int updateStbGdnVO(GdnListVO gdnListVO);

    /**
     * 查询库存单子单编号
     *
     * @param stbNum
     * @return
     */
    List<String> querySonStbNum(String stbNum);

    /**
     * 删除STB
     *
     * @param sonStbNumList
     * @return
     */
    int delStbList(List<String> sonStbNumList);

    /**
     * 非总单删除单条库存
     *
     * @param stbNum
     * @return
     */
    int delAStb(String stbNum);

    /**
     * 修改总库存单信息
     *
     * @param stbGdnVO
     * @return
     * @author HHe
     */
    int updateTotStb(StbGdnVO stbGdnVO);

    /**
     * 查询库存单编号对应的子库存单
     *
     * @author HHe
     */
    List<String> queryStbIsTotal(String stbNum, Long ownerId);

    /**
     * 根据库存单查询出库库存单封装成GdnListVO
     *
     * @author HHe
     */
    GdnListVO queryGdnListVOByStbNum(String stbNum, Long ownerId);

    /**
     * 修改库存单
     *
     * @author HHe
     * @date 2019/9/24 16:24
     */
    int updateStbByNumAndId(Stb stb);

    /**
     * 添加库存单返回库存编号
     *
     * @author HHe
     * @date 2019/9/27 19:55
     */
    String insertStb(Stb stb, SysUser sysUser);

    /**
     * 删除库存表
     *
     * @author HHe
     * @date 2019/11/6 17:19
     */
    int delStb(Stb stb);

    /**
     * 根据原单查询出库单非作废数量
     *
     * @author HHe
     * @date 2019/11/21 14:34
     */
    Long queryCountByErc(Stb stb);





}
