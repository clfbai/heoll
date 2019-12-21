package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStkX;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WarehStkXMapper {

    int insert(WarehStkX record);

    int insertSelective(WarehStkX record);

    int updateByPrimaryKeySelective(WarehStkX record);

    int updateByPrimaryKey(WarehStkX record);
    /**
     * 存储仓库库存快照
     * @author HHe
     * @date 2019/9/18 20:19
     */
    int addWarehStkXList(@Param("warehStkXList") List<WarehStkX> warehStkXList, @Param("snptTime") Timestamp snptTime);
    /**
     * 根据盘点时间和仓库Id删除所有快照
     * @author HHe
     * @date 2019/9/19 16:58
     */
    @Delete("delete from wareh_stk_x where snpt_time=#{snptTime} and wareh_id=#{warehId}")
    int delWarehStkXByTimeAndWarehId(@Param("snptTime") Timestamp snptTime, @Param("warehId") Long warehId);
    /**
     * 根据仓库id和快照时间查询仓库快照
     * @author HHe
     * @date 2019/9/26 19:44
     */
    List<WarehStkX> queryWarehStkXList(@Param("warehId") Long warehId, @Param("snptTime") Timestamp snptTime);
}