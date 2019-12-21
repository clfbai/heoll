package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDmode;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRmode;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.GdnVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * WarehDmodeMapper继承基类
 */
@Repository
public interface WarehDmodeMapper{
    /**
     * 根绝仓库Id查询对应的出库方式列表
     * @param warehId
     * @return
     */
    List<OptionVo> queryWDListById(Long warehId);

    GdnVO getStbDelivModeMess(@Param("warehId") Long warehId, @Param("delivMode") String delivMode);

    /**
     * 根据仓库Id查询出库方式code和中文的map集合
     * @param warehId
     * @return
     */
    List<Map<String, String>> getdelivModeByWarehId(Long warehId);
    /**
     * 根据仓库编号查询出库方式
     * @author HHe
     * @date 2019/8/23 12:10
     */
    List<Map<String, String>> loadDelivModeByWarehCode(Long warehId);
    /**
     * 根据出库方式和仓库code查询出库方式
     * @author HHe
     * @date 2019/8/23 16:35
     */
    WarehDmode queryAppointrcvMess(@Param("warehId") Long warehId, @Param("delivMode") String delivMode);

    /**
     * 判断出库方式和数据类型是否对应
     * @author HHe
     * @date 2019/8/27 9:39
     */
    @Select("select COUNT(1) from wareh_dmode_bills where wareh_dmod_code and doc_type=#{srcDocType}")
    int judgeModeMapDocType(@Param("delivMode") String delivMode, @Param("srcDocType") String srcDocType);
    /**
     * 根据仓库Id和出库方式查询出库方式对象
     * @author HHe
     * @date 2019/8/31 10:12
     */
    WarehDmode queryDmodeByWarehIdMode(@Param("warehId") Long warehId, @Param("delivMode") String delivMode);

    /**
     * 查询原始单据类别下拉
     * @author HHe
     * @date 2019/11/15 15:54
     */
    List<OperationVo> queryDocPullDown(@Param("delivMode") String delivMode);
    /**
     * 查询固定单价
     * @author HHe
     * @date 2019/11/29 14:33
     */
    String queryFixedUnitPriceByObj(WarehDmode warehDmode);

    List<WarehRmode> selectWareh(Long warehId);

    void addRmodeList(@Param("list") List<WarehDmode> rmodes);
}