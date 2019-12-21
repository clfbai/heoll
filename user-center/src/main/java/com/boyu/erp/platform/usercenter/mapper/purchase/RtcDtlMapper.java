package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.mq.order.SrcOrderItems;
import com.boyu.erp.platform.usercenter.entity.purchase.PscDtl;
import com.boyu.erp.platform.usercenter.entity.purchase.PsoDtl;
import com.boyu.erp.platform.usercenter.entity.purchase.RtcDtl;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.ProdClsDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RtcDtlMapper {
    int deleteByPrimaryKey(RtcDtl key);

    int insert(RtcDtl record);

    int insertSelective(RtcDtl record);

    RtcDtl selectByPrimaryKey(RtcDtl key);

    int updateByPrimaryKeySelective(RtcDtl record);

    int updateByPrimaryKey(RtcDtl record);

    /**
     * 通过退货合同号查询明细
     * @param vo
     * @return
     */
    List<RtcDtlVo> findByRtcNum(RtcDtlVo vo);

    /**
     * 查询退货合同中最后一个行号的数据
     * @param rtcNum
     * @return
     */
    RtcDtl selectByDesc(String rtcNum);

    int insertByRtcNum(RtcDtlVo vo);

    int updateByRtcNum(RtcDtlVo vo);

    int deleteByRtcNum(String rtcNum);

    /**
     *  批量新增明细
     * @param list
     * @return
     */
    int insertList(List<RtcDtlVo> list);

    /**
     * 批量删除明细
     * @param list
     * @return
     */
    int deleteList(List<RtcDtlVo> list);

    /**
     * 批量更新
     * @param list
     * @return
     */
    int updateList(List<RtcDtlVo> list);

    /**
     * 根据自定义sql查询
     * @param rtcNum
     * @param sql
     * @return
     */
    List<RtcDtl> selectByOperation(String rtcNum, String sql);

    //查询添加未决需要的集合
    List<RtcDtlVo> selectByBill(String rtcNum, String sql);

    /**
     * 查询出生成单据所需的明细数据
     * @param vo
     * @return
     */
    List<RtcDtlVo> selectDtlByRgo(RgoVo vo);

    /**
     * 生成出入库单时明细
     * @param map
     * @return
     */
    List<CommonDtl> findByDtlInfo(Map<String, Object> map);

    /**
     * 查询导入退购合同明细
     * @param v
     * @return
     */
    List<RtcDtlVo> importBill(PrcVo v);

    /**
     * 查询集合>更新集合(发货)
     * @param rtcNum
     * @param docUnitId
     * @param docNum
     * @param flag
     * @return
     */
    List<RtcDtl> selectByStbDtl(@Param("rtcNum") String rtcNum, @Param("docUnitId") Long docUnitId, @Param("docNum") String docNum, @Param("flag") boolean flag, @Param("type") String type);

    /**
     * 查询集合>更新集合(收发货)
     * @param rtcNum
     * @param docUnitId
     * @param docNum
     * @param flag
     * @return
     */
    List<RtcDtl> findByStbDtl(@Param("rtcNum") String rtcNum, @Param("docUnitId") Long docUnitId, @Param("docNum") String docNum, @Param("flag") boolean flag);

    /**
     * 更新明细集合
     * @param list
     * @return
     */
    int updateDtlList(List<RtcDtl> list);

    //查询出商品品种/发货数量集合
    List<RtcDtlVo> selectByBillQty(@Param("docUnitId") Long docUnitId, @Param("docNum") String docNum);

    //查询出商品品种/发货数量集合
    List<RtcDtlVo> selectByBillVal(@Param("docUnitId") Long docUnitId,@Param("docNum") String docNum);

    //查询推送消息需要的商品明细数据
    List<SrcOrderItems> selectByOrderItems(@Param("rtcNum") String rtcNum);

    /**
     * 2B订单中查询存储时所需明细数据
     * @param items
     * @return
     */
    List<RtcDtlVo> findByOrderItem(@Param("items") List<SrcOrderItems> items);
}