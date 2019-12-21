package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PscDtl;
import com.boyu.erp.platform.usercenter.entity.purchase.PsoDtl;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoDtlVo;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

public interface PsoDtlMapper {
    int deleteByPrimaryKey(PsoDtl key);

    int insert(PsoDtl record);

    int insertSelective(PsoDtl record);

    PsoDtl selectByPrimaryKey(PsoDtl key);

    int updateByPrimaryKeySelective(PsoDtl record);

    int updateByPrimaryKey(PsoDtl record);

    /**
     * 通过购销单号查询明细集合
     * @param vo
     * @return
     */
    List<PsoDtlVo> findByPuoNum(PsoDtlVo vo);

    /**
     * 查询出最后一个行号的记录
     * @param psoNum
     * @return
     */
    PsoDtl selectByDesc(String psoNum);

    /**
     * 添加单条明细
     * @param p
     * @return
     */
    int insertByPsoNum(PsoDtlVo p);

    /**
     * 更新单条明细
     * @param p
     * @return
     */
    int updateByPsoNum(PsoDtlVo p);

    /**
     * 删除单条明细
     * @param psoNum
     * @return
     */
    int deleteByPsoNum(String psoNum);

    /**
     * 添加明细集合
     * @param list
     * @return
     */
    int insertList(List<PsoDtlVo> list);

    /**
     * 删除明细集合
     * @param list
     * @return
     */
    int deleteList(List<PsoDtlVo> list);

    /**
     * 更新明细集合
     * @param list
     * @return
     */
    int updateList(List<PsoDtlVo> list);

    //查询添加未决需要的集合
    List<PsoDtl> selectByBill(String psoNum, String sql);

    //通过自定义sql查询
    List<PsoDtl> selectByOperation(String psoNum, String sql);

    /**
     * 查询差异集合
     * @param psoNum
     * @return
     */
    List<CommonDtl> findByDiff(@Param("psoNum") String psoNum,@Param("sql") String sql);

    /**
     * 通过sql查询差异集合
     * @param psoNum
     * @return
     */
    List<CommonDtl> findByDiffSql(@Param("psoNum") String psoNum,@Param("sql") String sql);

    /**
     * 查询集合>更新集合(发货)
     * @param psoNum
     * @param docUnitId
     * @param docNum
     * @param flag
     * @return
     */
    List<PsoDtl> selectByStbDtl(@Param("psoNum") String psoNum, @Param("docUnitId") Long docUnitId, @Param("docNum") String docNum, @Param("flag") boolean flag,@Param("type") String type);

    /**
     * 查询集合>更新集合(收发货)
     * @param psoNum
     * @param docUnitId
     * @param docNum
     * @param flag
     * @return
     */
    List<PsoDtl> findByStbDtl(@Param("psoNum") String psoNum,@Param("docUnitId") Long docUnitId,@Param("docNum") String docNum,@Param("flag") boolean flag);

    /**
     * 更新明细集合
     * @param list
     * @return
     */
    int updateDtlList(List<PsoDtl> list);

    /**
     * 生成出入库单时明细
     * @param map
     * @return
     */
    List<CommonDtl> findByDtlInfo(Map<String, Object> map);

    /**
     * 供货方取消发货通知时，计算采购合同相关库存数量
     * @return
     */
    List<CommonDtl> findByStbToVdrRe(@Param("psoNum") String psoNum,@Param("stbUnitId") Long stbUnitId,@Param("stbNum") String stbNum);
}