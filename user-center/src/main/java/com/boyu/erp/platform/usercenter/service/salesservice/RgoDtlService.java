package com.boyu.erp.platform.usercenter.service.salesservice;

import com.boyu.erp.platform.usercenter.entity.sales.Rgo;
import com.boyu.erp.platform.usercenter.entity.sales.RgoDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.sales.RgoDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoProdDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: RgoDtlService
 * @description: 调配单明细接口
 * @author: wz
 * @create: 2019-10-7 10:35
 */
public interface RgoDtlService {

    /**
     * 查询调配单明细所有数据
     * @param vo
     * @return
     */
    List<RgoDtl> selectByRgo(RgoVo vo);

    /**
     * 清除明细并更新主表数据
     * @param list
     * @return
     */
    int deleteRgoDtl(List<RgoDtl> list);

    /**
     * 通过调配单号与组织id查询明细
     * @param vo
     * @return
     */
    List<RgoDtlVo> selectByRgoDtl(RgoDtlVo vo);

    /**
     * 添加调配单明细
     * @param vo
     * @return
     */
    int insertRgoDtl(RgoDtlVo vo);

    /**
     * 修改调配单明细
     * @param vo
     * @return
     */
    int updateRgoDtl(RgoDtlVo vo);

    /**
     * 删除调配单明细
     * @param vo
     * @return
     */
    int deleteRgoDtl(RgoDtlVo vo);

    /**
     * 新增主表时同步添加明细记录
     * @param vo
     * @return
     */
    RgoVo insertByRgo(RgoVo vo);

    /**
     * 选择商品后查询相关数据
     * @param vo
     * @return
     */
    RgoDtlVo updateDtl(RgoProdDtlVo vo);
}
