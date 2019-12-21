package com.boyu.erp.platform.usercenter.service.collarUseservice.collarUseserviceimpl;

import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsWithBLOBs;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.goods.UnitProdCls;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.mapper.collarUse.ArqMapper;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdClsMapper;
import com.boyu.erp.platform.usercenter.mapper.goods.ProductMapper;
import com.boyu.erp.platform.usercenter.mapper.goods.UnitProdClsMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.model.purchase.ProdClsModel;
import com.boyu.erp.platform.usercenter.service.collarUseservice.ArqService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumDtlSerivce;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.table.TableService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.common.BaseScopeUtils;
import com.boyu.erp.platform.usercenter.utils.common.FiledUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProductCodeUtils;
import com.boyu.erp.platform.usercenter.utils.refttable.ReftClass;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.collarUse.ArqVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PscVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: ArqServerImpl
 * @description: 领用单
 * @author: wz
 * @create: 2019-8-23 12:19
 */
@Service
@Transactional
public class ArqServerImpl implements ArqService {

    @Autowired
    private ArqMapper arqMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private SysRefNumService sysRefNumService;

    @Override
    public PageInfo<ArqVo> selectAll(ArqVo vo, Integer page, Integer size, SysUser user) {
        List<ArqVo> list = null;
        /**
         * 系统管理员
         * */
        if (usersService.getAdmin(user) != null && usersService.getAdmin(user).getOprId() == -1L) {
            PageHelper.startPage(page, size);
            list = arqMapper.selectALL(vo);
        } else {
            PageHelper.startPage(page, size);
            list = arqMapper.selectByUnit(vo);
        }
        PageInfo<ArqVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 新增领用单
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertArq(ArqVo vo, SysUser user) {
        String arqNum = sysRefNumService.viceNum(user,"ARQ_NUM");
        vo.setArqNum(arqNum);
        vo.setUnitId(user.getDomain().getUnitId());
        vo.setOprId(user.getPrsnl().getPrsnlId());
        vo.setEffective("F");
        vo.setCancelled("F");
        vo.setSuspended("F");
        vo.setProgress("PG");
        return arqMapper.insertSelective(vo);
    }

    /**
     * 修改领用单
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int updateArq(ArqVo vo, SysUser user) {
        vo.setOprId(user.getPrsnl().getPrsnlId());
        return arqMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 删除领用单时 删除领用单明细
     * @param vo
     * @return
     */
    @Override
    public int deleteArq(ArqVo vo) {

        return arqMapper.deleteByArqVo(vo.getArqNum(),vo.getUnitId()+"");
    }


}
