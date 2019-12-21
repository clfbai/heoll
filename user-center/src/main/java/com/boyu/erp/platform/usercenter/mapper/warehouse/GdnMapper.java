package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Gdn;
import com.boyu.erp.platform.usercenter.model.wareh.GdnFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.GdnModel;
import com.boyu.erp.platform.usercenter.model.wareh.GdnStbModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GdnMapper {
    int deleteGdn(Gdn key);

    int insertGdn(Gdn record);

    int insertGdnVOGDN(GdnVO gdnVO);

    Gdn selectGdn(Gdn key);

    int updateGdn(Gdn record);


    GdnVO initGdnByDelivMode(GdnVO vo);

    /**
     * 修改出库单信息
     * @param gdnListVO
     * @return
     */
    int updateGdnVO(GdnListVO gdnListVO);

    /**
     * 根据库存单编号删除出库单
     * @param stbNum
     * @return
     */
    int delGdn(String stbNum);

    /**
     * 查询出库单详情
     * @param stbNum
     * @return
     */
    GdnListVO queryGdnDetails(@Param("stbNum") String stbNum, @Param("unitId") Long unitId);
    /**
     * 根据出库单编号查询出库单信息
     * @author HHe
     * @date 2019/9/6 20:18
     */
    Gdn getGdnByGdnNum(String gdnNum);

    Long queryDelivWarehIdBySrcDocMess(@Param("srcDocType") String srcDocType, @Param("srcDocNum") String srcDocNum, @Param("srcDocUnitId") Long srcDocUnitId, @Param("rcvUnitId") Long rcvUnitId);

    Long queryRcvWarehIdBySrcDocMess(@Param("srcDocType") String srcDocType, @Param("srcDocNum") String srcDocNum, @Param("srcDocUnitId") Long srcDocUnitId, @Param("delivUnitId") Long delivUnitId);

    /**
     * 添加出库单
     * @author HHe
     * @date 2019/10/14 15:21
     */
    int insertGdnModel2Gdn(GdnModel gdnModel);
    /**
     * 根据出库单编号和组织Id查询出库单
     * @author HHe
     * @date 2019/10/15 15:26
     */
    Gdn queryGdnByNumAndUnit(@Param("gdnNum") String gdnNum, @Param("unitId") Long unitId);
    /**
     * 根据编号和组织Id查询出库单和对应库存单信息
     * @author HHe
     * @date 2019/10/15 15:39
     */
    GdnStbModel queryGdnStbByNumAndUnit(@Param("stbNum") String stbNum, @Param("unitId") Long unitId);
    /**
     * 查询出库单列表
     * @param gdnFilterModel 页面筛选对象
     * @return 出库单、库存单列表
     * @author HHe
     * @date 2019/11/12 9:54
     */
    List<GdnModelVO> queryGdnStbListByFtr(GdnFilterModel gdnFilterModel);
    /**
     * 根据筛选条件查询总数量、总金额
     * @author HHe
     * @date 2019/11/18 17:52
     */
    Map<String, Object> getListTotal(GdnFilterModel gdnFilterModel);
    /**
     * 查询出库库存单
     * @param
     * @return
     * @author HHe
     * @date 2019/12/5 14:32
     */
    GdnStbModel queryGdnStbByObj(GdnStbModel gdnStbModel);
}