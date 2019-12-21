package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.Pua;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;

import java.util.List;

public interface PuaMapper {
    int deleteByPrimaryKey(Pua key);

    int insert(Pua record);

    int insertByPuaVo(PsxVo record);

    Pua selectByPrimaryKey(Pua key);

    int updateByPuaVo(PsxVo record);

    int updateByPrimaryKey(Pua record);

    /**
     * 系统管理员查询
     * @param pua
     * @return
     */
    List<PsxVo> selectALL(PsxVo pua);

    /**
     * 组织/用户查询
     * @param pua
     * @return
     */
    List<PsxVo> selectUserList(PsxVo pua);

    /**
     * 删除采购申请
     * @param puaNum
     * @param vendeeId
     * @return
     */
    int deletePuaVo(String puaNum,String vendeeId);

    //销售申请中删除采购申请
    int deleteByPsxNum(String psxNum);

    //更新审核信息
    int updateByChkr(String chkrId, String psxNum);

    //销售申请中生成采购申请
    int insertBySla(PsxVo vo);

    //查询单条数据
    PsxVo selectByOnly(PsxVo vo);

    //查询采购申请记录
    Pua selectByPsxNum(String psxNum);

    /**
     * 采购合同中导入时查询相关数据
     * @return
     */
    PsxVo selectObject(PscVo vo);
}