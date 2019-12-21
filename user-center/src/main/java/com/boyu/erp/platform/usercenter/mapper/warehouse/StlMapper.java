package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Stl;
import com.boyu.erp.platform.usercenter.model.wareh.StlFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.StlModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.StlVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StlMapper {
    int insert(Stl record);

    int insertSelective(Stl record);
    /**
     * 根据筛选条件查询盘点清单列表
     * @author HHe
     * @date 2019/9/23 11:37
     */
    List<StlVO> queryStlListByFilter(@Param("stlFilterModel") StlFilterModel stlFilterModel, @Param("unitId") Long unitId);

    /**
     * 修改盘点清单
     * @author HHe
     * @date 2019/9/23 15:38
     */
    int updateStl(@Param("stlModel") StlModel stlModel, @Param("unitId") Long unitId);
    /**
     * 删除盘点清单
     * @author HHe
     * @date 2019/9/23 16:28
     */
    int delStl(@Param("stlNum") String stlNum, @Param("unitId") Long unitId);
    /**
     * 根据盘点清单编号和组织id查询盘点清单详情
     * @author HHe
     * @date 2019/9/25 9:50
     */
    Stl queryStlByStlNumAndId(@Param("stlNum") String stlNum, @Param("unitId") Long unitId);
    /**
     * 根据盘点表编号和组织id查询盘点清单编号集合
     * @author HHe
     * @date 2019/9/26 19:50
     */
    List<String> queryStlNumListBySttNumAndId(@Param("sttNum") String sttNum, @Param("unitId") Long unitId);
}