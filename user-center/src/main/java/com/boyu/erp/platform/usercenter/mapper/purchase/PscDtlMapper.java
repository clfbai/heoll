package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.mq.order.OrderItemsToB;
import com.boyu.erp.platform.usercenter.entity.purchase.PscDtl;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.ProdClsDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PscDtlMapper {
    int deleteByPrimaryKey(PscDtl key);

    int deleteByPscNum(String pscNum);

    int insert(PscDtl record);

    PscDtl selectByPrimaryKey(PscDtl key);

    int updateByPrimaryKeySelective(PscDtl record);

    int updateByPrimaryKey(PscDtl record);

    //通过单号查询明细数据
    List<PscDtlVo> findByPscNum(PscDtlVo vo);

    /**
     * 采购单与销售单导入明细需要的书库
     * @param pscNum
     * @return
     */
    List<PscDtlVo> importBill(String pscNum);

    int insertByPscNum(PscDtlVo p);

    PscDtl selectByPscDtl(String pscNum);

    int updateByPsc(PscDtlVo p);

    PscDtl selectByPscAndCat(String pscNum, String prodCatId);

    /**
     * 更新货期
     * @param map
     * @return
     */
    int updateByRqdCtrl(Map<String, Object> map);

    /**
     * 新增明细集合
     *
     * @return
     */
    int insertList(List<PscDtlVo> list);

    /**
     * 删除明细集合
     *
     * @return
     */
    int deleteList(List<PscDtlVo> list);

    /**
     * 更新明细集合
     *
     * @param list
     * @return
     */
    int updateList(List<PscDtlVo> list);

    //查询添加未决需要的集合
    List<PscDtl> selectByBill(String pscNum, String sql);

    //通过自定义sql查询
    List<PscDtl> selectByOperation(String pscNum, String sql);

    //推送消息需要的商品集合
    List<OrderItemsToB> selectByOrderItems(String pscNum);

    /**
     * 查询出生成单据所需的明细数据
     * @param vo
     * @return
     */
    List<PscDtlVo> selectDtlByRgo(RgoVo vo);

    /**
     * 查询集合>更新集合(发货)
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @param flag
     * @return
     */
    List<PscDtl> selectByStbDtl(@Param("pscNum") String pscNum,@Param("docUnitId") Long docUnitId,@Param("docNum") String docNum,@Param("flag") boolean flag,@Param("type") String type);

    /**
     * 查询集合>更新集合(收发货)
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @param flag
     * @return
     */
    List<PscDtl> findByStbDtl(@Param("pscNum") String pscNum,@Param("docUnitId") Long docUnitId,@Param("docNum") String docNum,@Param("flag") boolean flag);


    /**
     * 更新明细集合
     * @param list
     * @return
     */
    int updateDtlList(List<PscDtl> list);

    //查询出商品品种/发货数量集合
    List<ProdClsDtlVo> selectByBillQty(@Param("docUnitId") Long docUnitId,@Param("docNum") String docNum);

    //查询出商品品种/发货数量集合
    List<ProdClsDtlVo> selectByBillVal(@Param("docUnitId") Long docUnitId,@Param("docNum") String docNum);

    //发货时查询是否有商品的出库数量>入库数量
    List<ProdClsDtlVo> selectByDelivOrRcv(String pscNum);

    //取消发货时查询是否有商品的出库数量>入库数量
    List<ProdClsDtlVo> selectDtlByStb(@Param("pscNum") String pscNum,@Param("docUnitId") Long docUnitId,@Param("docNum") String docNum);

    /**
     * 生成出入库单时明细
     * @param map
     * @return
     */
    List<CommonDtl> findByDtlInfo(Map<String, Object> map);

    /**
     * 分配
     * @param pscNum
     * @param sql
     * @return
     */
    List<CommonDtl> findByDiff(@Param("pscNum") String pscNum ,@Param("sql") String sql);

    /**
     * 采购单中选择商品后判断返回
     * @param vo
     * @return
     */
    List<PscDtlVo> getJudgeDtlByPuo(PscDtlVo vo);

    /**
     * 销售单中选择商品后判断返回
     * @param vo
     * @return
     */
    List<PscDtlVo> getJudgeDtlBySlo(PscDtlVo vo);

    /**
     * 采购合同中判断是否继续收货
     * @param pscNum
     * @return
     */
    List<PscDtlVo> judge(@Param("pscNum") String pscNum);


    /**
     * 采购合同收货时判断收发货数量
     * @param pscNum
     * @param sql
     * @return
     */
    List<PscDtl> findByQtyEqRcv(@Param("pscNum") String pscNum,@Param("sql") String sql);

    /**
     * 退购时判断是否有异常商品信息
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @return
     */
    List<PscDtl> getException(@Param("pscNum") String pscNum,@Param("docUnitId") Long docUnitId,@Param("docNum") String docNum,@Param("sql") String sql);

    /**
     * 更新退购信息
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @param flag
     * @return
     */
    int updateByStbToRp(@Param("pscNum") String pscNum, @Param("docUnitId") Long docUnitId, @Param("docNum") String docNum, @Param("flag") boolean flag);

    /**
     * 更新退销信息
     * @param pscNum
     * @param docUnitId
     * @param docNum
     * @param flag
     * @return
     */
    int updateByStbToRs(@Param("pscNum") String pscNum,@Param("docUnitId") Long docUnitId,@Param("docNum") String docNum,@Param("flag") boolean flag);

    /**
     * 2B订单中查询存储时所需明细数据
     * @param items
     * @return
     */
    List<PscDtlVo> findByOrderItem(@Param("items") List<OrderItemsToB> items);
}