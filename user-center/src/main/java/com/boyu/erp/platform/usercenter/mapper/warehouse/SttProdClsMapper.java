package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.SttProdCls;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SttProdClsMapper {
    int deleteByPrimaryKey(SttProdCls key);

    int insert(SttProdCls record);

    int insertSelective(SttProdCls record);
    /**
     * 添加商品品种集合
     * @author HHe
     * @date 2019/9/18 15:27
     */
    int addSttProdClsList(@Param("sttProdClsList") List<SttProdCls> sttProdClsList, @Param("sttNum") String sttNum, @Param("unitId") Long unitId);
    /**
     * 根据盘点表编号和组织Id删除商品品种
     * @author HHe
     * @date 2019/9/18 16:31
     */
    @Delete("delete from stt_prod_cls where stt_num=#{sttNum} and unit_id=#{unitId}")
    int delSttProdClsByNumAndUnitId(@Param("sttNum") String sttNum, @Param("unitId") Long unitId);
    /**
     * 根据编号和组织Id查询品种集合
     * @author HHe
     * @date 2019/9/19 15:34
     */
    List<SttProdCls> querySttProdClsByObj(SttProdCls sttProdCls);
}