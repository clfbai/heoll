package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysPrsnl;
import com.boyu.erp.platform.usercenter.model.option.PrsnlOptionModel;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.model.system.SysPrsnlModel;
import com.boyu.erp.platform.usercenter.vo.system.SysPrsnlPgVO;
import com.boyu.erp.platform.usercenter.vo.system.SysPrsnlVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;


public interface SysPrsnlMapper {

    List<SysPrsnl> selectAll(SysPrsnl record);

    SysPrsnl selectByPrimaryKey(String prsnlId);

    SysPrsnl selectByPrsnlCode(String prsnlCode);

    /**
     * 添加人员
     *
     * @param record
     * @return
     */
    int insertPrsnl(SysPrsnl record);

    int updateByPrimaryKeySelective(SysPrsnl record);

    /**
     * 功能描述:  管理员查询用户
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 11:20
     */
    List<SysPrsnlVo> selectPrsnlAndUser(SysPrsnlVo record);

    /**
     * 仅做删除组织查询人员
     */
    int deletePrsnlAndUser(SysPrsnlVo sysPrsnlVo);


    public SysPrsnl selectById(long id);

    /**
     * 根据id 查询prsnl和user数据
     */
    public SysPrsnlVo selectPrsnlAndUserByid(SysPrsnlVo record);


    /**
     * 查询设计人员
     * (同时也是人员弹窗)
     */
    List<PrsnlOptionModel> findByDesign(SysPrsnl s);


    List<SysPrsnlPgVO> selectAllICN();

    /**
     * 查询人员（通用 可组织筛选）
     */

    List<PrsnlOptionModel> getOwnerPrsnl(SysPrsnlModel prsnl);
   /**
    *
    * 功能描述: 用户装入人员查询(可组织筛选)
    *
    *
    * @param:
    * @return:
    * @auther: CLF
    * @date: 2019/8/27 17:29
    */
    List<SysPrsnlVo> selectPrsnlUserList(SysPrsnlVo record);
    /**
     * 根据Id查询用户信息
     * @author HHe
     * @date 2019/9/12 10:39
     */
    List<SysPrsnl> queryPrsnlByIds(@Param("sysPrsnls") Set<Long> sysPrsnls);
    /**
     *
     * 功能描述: 通用弹窗
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/23 19:44
     */
    SysPrsnl selectObject(CommonWindowModel model);
}