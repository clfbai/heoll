package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.SttProdCat;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 商品分类
 * @author HHe
 * @date 2019/9/18 15:17
 */
@Repository
public interface SttProdCatMapper {
    int deleteByPrimaryKey(SttProdCat key);

    int insert(SttProdCat record);

    int insertSelective(SttProdCat record);
    /**
     * 添加商品分类集合
     * @author HHe
     * @date 2019/9/18 15:17
     */
    int addSttProdCatList(@Param("sttProdCatList") List<SttProdCat> sttProdCatList, @Param("sttNum") String sttNum, @Param("unitId") Long unitId);
    /**
     * 根据盘点表编号和组织Id删除商品分类
     * @author HHe
     * @date 2019/9/18 16:33
     */
    @Delete("delete from stt_prod_cat where stt_num=#{sttNum} and unit_id=#{unitId}")
    int delSttProdCatByNumAndUnitId(@Param("sttNum") String sttNum, @Param("unitId") Long unitId);
    /**
     * 根据不为空属性查询盘点表商品分类集合
     * @author HHe
     * @date 2019/9/19 12:29
     */
    List<SttProdCat> querySttProdCatByObj(SttProdCat sttProdCat);
}