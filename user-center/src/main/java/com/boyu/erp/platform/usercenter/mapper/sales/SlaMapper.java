package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.sales.Sla;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;

import java.util.List;

public interface SlaMapper {
    int deleteByPrimaryKey(Sla key);

    int insert(Sla record);

    //添加销售申请
    int insertBySlaVo(PsxVo record);

    //采购申请添加销售申请
    int insertByPua(PsxVo vo);

    Sla selectByPrimaryKey(Sla key);

    int updateBySlaVo(PsxVo record);

    int updateByPrimaryKey(Sla record);

    /**
     * 系统管理员查询
     * @param sla
     * @return
     */
    List<PsxVo> selectALL(PsxVo sla);

    /**
     * 组织/用户查询
     * @param sla
     * @return
     */
    List<PsxVo> selectUserList(PsxVo sla);

    //删除销售申请
    int deleteSlaVo(String slaNum,String venderId);

    //采购申请中删除销售申请
    int deleteByPsxNum(String psxNum);

    //更新审核信息
    int updateByChkr(String chkrId,String psxNum);

    //查询单条数据
    PsxVo selectByOnly(PsxVo vo);

    //查询单条数据
    Sla selectByPsxNum(String psxNum);

    /**
     * 销售合同中导入时查询相关数据
     * @return
     */
    PsxVo selectObject(PscVo vo);
}