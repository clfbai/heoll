package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.mq.order.OrderItems;
import com.boyu.erp.platform.usercenter.entity.mq.order.RgoOrderItems;
import com.boyu.erp.platform.usercenter.entity.sales.RgoDtl;
import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RgoDtlMapper {
    int deleteByPrimaryKey(RgoDtl record);

    int deleteByRgo(RgoDtl record);

    int insert(RgoDtl record);

    int insertSelective(RgoDtl record);

    RgoDtl selectByPrimaryKey(RgoDtl record);

    int updateByPrimaryKeySelective(RgoDtl record);

    int updateByPrimaryKey(RgoDtl record);

    /**
     * 清除明细时查询是否有数据
     * @param vo
     * @return
     */
    List<RgoDtl> selectByRgo(RgoVo vo);

    /**
     * 通过调配单号查询明细
     * @param vo
     * @return
     */
    List<RgoDtlVo> selectByRgoDtl(RgoDtlVo vo);

    /**
     * 查询最后一条明细记录
     * @param record
     * @return
     */
    RgoDtl findByRgoDtl(RgoDtl record);

    //批量新增明细
    int insertList(List<RgoDtlVo> list);

    //批量更新明细
    int updateList(List<RgoDtlVo> list);

    //批量删除明细
    int deleteList(List<RgoDtlVo> list);

    //推送消息需要的商品集合
    List<RgoOrderItems> selectByOrderItems(String unitId, String rgoNum);

    //更新调入方明细数据
    int updateByStbToRcv(@Param("rgoUnitId") Long rgoUnitId,@Param("rgoNum") String rgoNum,@Param("stbUnitId") Long stbUnitId,@Param("stbNum") String stbNum,@Param("flag") boolean flag);

    //更新调出方明细数据
    int updateByStbToDeliv(@Param("rgoUnitId") Long rgoUnitId,@Param("rgoNum") String rgoNum,@Param("stbUnitId") Long stbUnitId,@Param("stbNum") String stbNum,@Param("flag") boolean flag);

}