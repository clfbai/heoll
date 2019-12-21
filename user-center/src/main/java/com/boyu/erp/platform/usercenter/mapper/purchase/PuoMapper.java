package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.Puo;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehSrcInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PuoMapper {
    int deleteByPrimaryKey(Puo key);

    int insert(Puo record);

    int insertSelective(Puo record);

    Puo selectByPrimaryKey(Puo key);

    int updateByPrimaryKeySelective(Puo record);

    int updateByPrimaryKey(Puo record);

    //系统管理员查询
    List<PsoVo> selectALL(PsoVo vo);
    //组织查询
    List<PsoVo> selectByUnit(PsoVo vo);

    //新增
    int insertByPso(PsoVo vo);

    //修改
    int updateByPso(PsoVo vo);

    //删除
    int deleteByPuo(String puoNum,String psoNum);

    Puo selectByPsoNun(String psoNum);

    int updateBychkr(String chkrId,String psoNum);

    int deleteByPso(String unitId,String psoNum);

    //查询单条
    PsoVo selectByOnly(PsoVo vo);

    /**
     * 出入库明细查询
     * @param vo
     * @return
     */
    PsoVo selectObject(PsoVo vo);

    /**
     * 出入库明细查询2
     * @param vo
     * @return
     */
    WarehSrcInfoVo selectByWareh(PsoVo vo);

    /**
     * 出入库明细查询3
     * @param vo
     * @return
     */
    WarehSrcInfoVo findByWareh(PsoVo vo);

    /**
     * 收货/取消收货时同步出库数据
     * @param srcDocUnitId
     * @param srcDocNum
     * @param docUnitId
     * @param docNum
     * @param flag
     * @return
     */
    PsoVo getPsoByStb(@Param("srcDocUnitId") Long srcDocUnitId, @Param("srcDocNum") String srcDocNum, @Param("docUnitId") Long docUnitId, @Param("docNum") String docNum, @Param("flag") boolean flag);

}