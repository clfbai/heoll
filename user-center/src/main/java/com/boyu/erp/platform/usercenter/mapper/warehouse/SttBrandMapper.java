package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttBrand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SttBrandMapper {
    int deleteByPrimaryKey(SttBrand key);

    int insert(SttBrand record);

    int insertSelective(SttBrand record);
    /**
     * 添加盘点品牌明细集合
     * @author HHe
     * @date 2019/9/18 15:03
     */
    int addSttBrandList(@Param("sttBrandList") List<SttBrand> sttBrandList, @Param("sttNum") String sttNum, @Param("unitId") Long unitId);
    /**
     * 根据盘点表编号和组织Id删除盘点表品牌
     * @author HHe
     * @date 2019/9/18 16:26
     */
    @Delete("delete from stt_brand where stt_num=#{sttNum} and unit_id=#{unitId}")
    int delSttBrandByNumAndUnitId(@Param("sttNum") String sttNum, @Param("unitId") Long unitId);
    /**
     * 根据对象的非空值查询集合
     * @author HHe
     * @date 2019/9/19 10:10
     */
    List<SttBrand> querySttBrandByObj(SttBrand sttBrand);
}