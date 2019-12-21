package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.sales.Slo;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoVo;
import com.boyu.erp.platform.usercenter.vo.sales.SloVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehSrcInfoVo;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface SloMapper {
    int deleteByPrimaryKey(Slo key);

    int insert(Slo record);

    int insertSelective(Slo record);

    Slo selectByPrimaryKey(Slo key);

    int updateByPrimaryKeySelective(Slo record);

    int updateByPrimaryKey(Slo record);

    int deleteByPuo(String unitId,String psoNum);

    Slo selectByPsoNum(String psoNum);

    //系统管理员查询
    List<PsoVo> selectALL(PsoVo vo);
    //组织查询
    List<PsoVo> selectByUnit(PsoVo vo);

    //更新审核信息
    int updateBychkr(String chkrId,String psoNum);

    //新增数据
    int insertByPuo(PsoVo vo);

    //更新数据
    int updateByPuo(PsoVo vo);

    //删除数据
    int deletePuo(String sloNum,String psoNum);

    //更新销售单数据
    int updateByPso(PsoVo vo);

    /**
     * 出入库明细查询
     * @param vo
     * @return
     */
    PsoVo selectObject(PsoVo vo);

    //查询单条记录
    PsoVo selectByOnly(PsoVo vo);

    /**
     * 出入库明细查询2
     * @param vo
     * @return
     */
    WarehSrcInfoVo selectByWareh(PsoVo vo);

    /**
     * 发货/取消发货时同步出库数据
     * @param srcDocUnitId
     * @param srcDocNum
     * @param docUnitId
     * @param docNum
     * @param flag
     * @return
     */
    PsoVo getPsoByStb(@Param("srcDocUnitId") Long srcDocUnitId, @Param("srcDocNum") String srcDocNum, @Param("docUnitId") Long docUnitId, @Param("docNum") String docNum, @Param("flag") boolean flag);
}