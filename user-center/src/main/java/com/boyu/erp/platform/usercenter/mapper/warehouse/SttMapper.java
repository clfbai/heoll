package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.model.wareh.SttFilterModel;
import com.boyu.erp.platform.usercenter.vo.warehouse.SttVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SttMapper {

    int insert(Stt record);

    int insertSelective(Stt record);

    int updateByPrimaryKeySelective(Stt record);

    int updateByPrimaryKey(Stt record);

    /**
     * 盘点表根据筛选条件查询列表
     * @author HHe
     * @date 2019/9/10 20:11
     */
    List<SttVO> querySttListByFilterModel(SttFilterModel sttFilterModel);

    /**
     * 超管查所有
     * @author HHe
     * @date 2019/9/15 11:37
     */
    List<SttVO> selectAllInAdmin(SttFilterModel sttFilterModel);

    /**
     * 删除盘点表
     * @author HHe
     * @date 2019/9/16 11:49
     */
    int delSttByNumAndId(Stt stt);
    /**
     * 根据盘点表编号和组织id查询盘点表
     * @author HHe
     * @date 2019/9/18 10:07
     */
    Stt querySttByNumAndId(@Param("sttNum") String sttNum, @Param("unitId") Long unitId);
    /**
     * 重做盘点表
     * @author HHe
     * @date 2019/9/19 17:59
     */
    int reformStt(Stt reformStt);
    /**
     * 修改盘点表实际总数量
     * @author HHe
     * @date 2019/9/25 17:17
     */
    int updateTtlActQty(@Param("sttNum") String sttNum, @Param("unitId") Long unitId, @Param("num") BigDecimal num);
}