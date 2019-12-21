package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.vo.warehouse.GdnListVO;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbGdnVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface StbMapper {
    int deleteByPrimaryKey(Stb key);

    int insert(GrnVo grnVo);

   // int insertGdn(StbGdnVO stbGdnVO);

    int insertStb(Stb stb);

    int updateStb(Stb stb);

    Stb selectByPrimaryKey(Stb key);

    int updateByPrimaryKey(Stb record);

    List<Map<String, String>> getNum(Long unitId);

    /**
     * 添加出库库存单
     * @param stbGdnVO
     * @return
     */
    int insertStbGdn(StbGdnVO stbGdnVO);

    /**
     * 修改库存单
     * @param gdnListVO
     * @return
     */
    int updateStbGdnVO(GdnListVO gdnListVO);

    /**
     * 根据总库存单查询子库存单编号集合
     * @param stbNum
     * @return
     */
    List<String> querySonStbNum(String stbNum);

    /**
     * 删除STB
     * @param sonStbNumList
     * @return
     */
    int delStb(List<String> sonStbNumList);

    /**
     * 删除单条库存
     * @param stbNum
     * @return
     */
    int delAStb(String stbNum);

    /**
     * 修改总库存单信息
     * @param stbGdnVO
     * @return
     * @author HHe
     */
    int updateTotStb(StbGdnVO stbGdnVO);
    /**
     * 查询库存单编号对应的子库存单
     * @author HHe
     */
    List<String> queryStbIsTotal(@Param("stbNum") String stbNum, @Param("ownerId") Long ownerId);
    /**
     * 根据库存单查询出库库存单封装成GdnListVO
     * @author HHe
     */
    GdnListVO queryGdnListVOByStbNum(@Param("stbNum") String stbNum, @Param("ownerId") Long ownerId);
    /**
     * 根据原单查询出库单非作废数量
     * @author HHe
     * @date 2019/11/21 14:34
     */
    Long queryCountByErc(Stb stb);



    /**
     * 根据库存单ID查询stb详情
     * @param stbNum
     * @return
     */
//    GdnListVO queryDetailsByStbNum(String stbNum);

}