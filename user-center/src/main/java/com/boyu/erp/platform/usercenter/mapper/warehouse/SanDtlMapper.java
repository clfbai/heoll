package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.SanDtl;
import com.boyu.erp.platform.usercenter.vo.warehouse.SanDtlVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanDtlMapper {
    int insert(SanDtl record);

    int insertSelective(SanDtl record);

    int updateByPrimaryKeySelective(SanDtl record);

    int updateByPrimaryKey(SanDtl record);
    /**
     * 根据对象中的值查询集合
     * @author HHe
     * @date 2019/10/7 11:27
     */
    List<SanDtlVO> queryListBySanDtl(@Param("sanDtl") SanDtl sanDtl, @Param("unitId") Long unitId);
    /**
     * 添加库存调整表明细集合
     * @author HHe
     * @date 2019/10/7 12:03
     */
    int addSanDtlByList(@Param("sanDtlList") List<SanDtl> sanDtlList, @Param("unitId") Long unitId);
    /**
     * 删除明细
     * @author HHe
     * @date 2019/10/7 14:30
     */
    int delSanDtlBySanDtl(@Param("sanDtl") SanDtl sanDtl, @Param("unitId") Long unitId);
}