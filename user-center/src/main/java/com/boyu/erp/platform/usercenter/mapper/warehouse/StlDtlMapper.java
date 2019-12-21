package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.StlDtl;
import com.boyu.erp.platform.usercenter.model.wareh.StlModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.StlDtlVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StlDtlMapper {
    int insert(StlDtl record);

    int insertSelective(StlDtl record);
    /**
     * 添加盘点清单明细集合
     * @author HHe
     * @date 2019/9/23 15:05
     */
    int addStlDtlList(StlModel stlModel);
    /**
     * 删除盘点清单明细
     * @author HHe
     * @date 2019/9/23 15:47
     */
    int delStlDtlByNumAndId(StlModel stlModel);
    /**
     * 根据盘点清单编号和组织id查询明细集合
     * @author HHe
     * @date 2019/9/25 11:11
     */
    List<StlDtlVO> queryListByStlNumAndId(@Param("stlNum") String stlNum, @Param("unitId") Long unitId);
    /**
     * 根据盘点清单编号和组织id查询盘点清单明细集合
     * @author HHe
     * @date 2019/9/26 19:55
     */
    List<StlDtl> queryStlDtlListByStlNumListAndId(@Param("stlNumList") List<String> stlNumList, @Param("unitId") Long unitId);
}