package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehRmode;
import com.boyu.erp.platform.usercenter.model.wareh.WarehRmodeMode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述:
 *
 * @Description: 仓库入库方式
 * @auther: CLF
 * @date: 2019/10/22 14:12
 */

public interface WarehRmodeMapper {
    int deleteByPrimaryKey(WarehRmode key);

    int insertSelective(WarehRmode record);

    WarehRmode selectByPrimaryKey(WarehRmode key);

    List<WarehRmode> selectWareh(Long warehId);

    int updateWarehRmodeMode(WarehRmodeMode mode);

    int updateByPrimaryKeySelective(WarehRmode record);

    /**
     * 查询入库方式列表
     */
    List<WarehRmode> selectWarehRmode(WarehRmode rmode);

    int addRmodeList(@Param("list") List<WarehRmode> rmodes);
}