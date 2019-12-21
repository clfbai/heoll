package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.San;
import com.boyu.erp.platform.usercenter.model.wareh.SanFilterModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.SanVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanMapper {
    int insert(San record);

    int insertSelective(San record);

    int updateByPrimaryKeySelective(San record);

    int updateByPrimaryKey(San record);
    /**
     * 根据筛选条件查询vo集合
     * @author HHe
     * @date 2019/10/6 17:21
     */
    List<SanVO> queryList(@Param("sanFilterModel") SanFilterModel sanFilterModel, @Param("unitId") Long unitId);
    /**
     * 根据库存调整表和组织编号查询库存调整表
     * @author HHe
     * @date 2019/10/7 10:44
     */
    San querySanByNumAndUnit(@Param("sanNum") String sanNum, @Param("unitId") Long unitId);
    /**
     * 删除库存调整表
     * @author HHe
     * @date 2019/10/7 14:34
     */
    int delSan(@Param("san") San san, @Param("unitId") Long unitId);
    /**
     * 查询详情
     * @author HHe
     * @date 2019/10/7 14:58
     */
    San querySanByProp(@Param("san") San san, @Param("unitId") Long unitId);
}