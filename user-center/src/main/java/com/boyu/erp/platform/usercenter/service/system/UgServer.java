package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUg;
import com.boyu.erp.platform.usercenter.entity.system.SysUgDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.system.UgAndUgDtlModel;
import com.boyu.erp.platform.usercenter.vo.system.UgVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 类描述:  组织分组接口
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/8 11:04
 */
public interface UgServer {
    /**
     * 功能描述:  组织分组分页查询
     *
     * @param ug (过滤对象)
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 11:07
     */
    PageInfo<UgVo> getUg(Integer page, Integer size, SysUg ug, SysUser user);

    /**
     * 功能描述: 组织分组明细查询
     *
     * @param dtl (组织分组明细对象)
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 11:08
     */
    List<SysUgDtl> getUgDtl(SysUgDtl dtl, SysUser user);

    /**
     * 功能描述:  增加织分组
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 11:11
     */
    int addUg(SysUg ug, UgAndUgDtlModel list, SysUser user) throws ServiceException;

    /**
     * 功能描述:  修改织分组
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 11:11
     */
    int upadteUg(UgAndUgDtlModel model, SysUser user) throws ServiceException;

    /**
     * 功能描述:  删除组织分组
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 11:11
     */
    int deleteUg(SysUg ug, SysUser user) throws ServiceException;

    /**
     * 功能描述:  增加组织分组明细
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 11:11
     */
    int addUgDtl(SysUgDtl dtl, SysUser user) throws ServiceException;

    /**
     * 功能描述:  批量修改组织分组明细
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 11:11
     */
    int upadteUgDtl(UgAndUgDtlModel model, SysUser user) throws ServiceException;

    /**
     * 功能描述:  删除组织分组明细
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/8 11:11
     */
    int deleteUgDtl(SysUgDtl dtl, SysUser user) throws ServiceException;

    /**
     * 功能描述:  判断能否将分组授予与用户
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/13 12:12
     */
    Map<String, Object> seletUgUser(SysUser user, Long ugId);

    /**
     * 功能描述: 查询组织分组单条信息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/15 17:24
     */
    SysUg getUgId(SysUg ug);
}
