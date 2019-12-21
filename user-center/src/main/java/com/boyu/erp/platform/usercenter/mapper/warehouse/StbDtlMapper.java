package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbDtlVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StbDtlMapper {
    int deleteByPrimaryKey(StbDtl key);

    int insert(StbDtl record);

    int insertStbDtl(StbDtl dtl);

    int updateStbDtl(StbDtl stbDtl);

    StbDtl selectByPrimaryKey(StbDtl key);

    int updateByPrimaryKey(StbDtl record);

    List<StbDtlVo> getStbDtlList(StbDtl record);

    int insertStbDtlVO(@Param("stbDtlVos") List<StbDtlVo> stbDtlVos, @Param("alwaysNum") String alwaysNum, @Param("unitId") Long unitId);

    /**
     * 出库任务生成出库单，添加库存明细。
     *
     * @author HHe
     */
    int insertStbDtl2CreateGdn(@Param("stbDtlVos") List<StbDtlVo> stbDtlVos, @Param("stbNum") String stbNum, @Param("unitId") Long unitId);

    /**
     * 根据子库存单集合查询明细集合
     *
     * @author HHe
     */
    List<StbDtlVo> queryTotStbDtlsBySonStbNums(@Param("sonStbNums") List<String> sonStbNums, @Param("ownerId") Long ownerId);

    /**
     * 根据库存单编号查询明细集合
     *
     * @author HHe
     */
    List<StbDtlVo> queryStbDtlsByStbNums(@Param("stbNum") String stbNum, @Param("ownerId") Long ownerId);

    /**
     * 添加商品集合
     *
     * @author HHe
     * @date 2019/9/5 20:37
     */
    int insertStbDtlList(List<StbDtl> stbDtls);

    /**
     * 添加库存明细集合
     *
     * @author HHe
     * @date 2019/10/14 15:44
     */
    int insertStbDtlListAndUnitId(@Param("stbDtlList") List<StbDtl> stbDtlList, @Param("unitId") Long unitId);

    /**
     * 根据出库单编号和组织Id查询出库单明细
     *
     * @author HHe
     * @date 2019/10/21 10:36
     */
    List<StbDtl> queryStbDtlsByNumAndUnit(@Param("stbNum") String stbNum, @Param("unitId") Long unitId);

    /**
     * 修改库存明细成本集合
     *
     * @author HHe
     * @date 2019/10/26 9:46
     */
    int updateStbDtlCostList(@Param("stbDtls") List<StbDtl> stbDtls);

    /**
     * @param pscNum
     * @param unitId
     * @param stbNum
     * @return
     */
    List<StbDtl> selectDiff(@Param("pscNum") String pscNum, @Param("unitId") Long unitId, @Param("stbNum") String stbNum);

    /**
     * 根据组织Id和库存单编号删除相应明细
     *
     * @author HHe
     * @date 2019/11/6 17:31
     */
    int delByNumAndId(StbDtl stbDtl);

    /**
     * 查询对应库存单明细的条数
     *
     * @author HHe
     * @date 2019/12/2 11:57
     */
    Long queryDtlCountByObj(StbDtl stbDtl);

    /**
     * 修改出库单明细
     *
     * @param stbDtlList 需修改集合
     * @return 数据库操作数
     * @author HHe
     * @date 2019/12/5 12:13
     */
    int updateStbDtlList(@Param("stbDtlList") List<StbDtl> stbDtlList);

    /**
     *
     * 功能描述: 通过商品Id
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/12/5 17:49
     */
    List<StbDtl> selectProdId(@Param("list") List<Long> prodId, @Param("unitId") Long unitId);
}