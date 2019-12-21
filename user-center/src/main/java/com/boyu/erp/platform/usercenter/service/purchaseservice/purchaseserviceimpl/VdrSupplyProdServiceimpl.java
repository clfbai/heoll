package com.boyu.erp.platform.usercenter.service.purchaseservice.purchaseserviceimpl;


import com.boyu.erp.platform.usercenter.entity.purchase.VdrSupplyProd;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.purchase.VdrSupplyProdMapper;
import com.boyu.erp.platform.usercenter.model.purchase.VdrSupplyProdModel;
import com.boyu.erp.platform.usercenter.service.purchaseservice.VdrSupplyProdService;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyProdVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname Transactional
 * basic class VdrSupplyProdServiceimpl
 * @Description TODO
 * @Date 2019/8/5 17:31
 * @Created wz
 */
@Slf4j
@Service
@Transactional
public class VdrSupplyProdServiceimpl implements VdrSupplyProdService {

    @Autowired
    private VdrSupplyProdMapper vdrSupplyProdMapper;

    /**
     * 供应商供货信息明细查询
     * @param page
     * @param size
     * @param vo
     * @return
     */
    @Override
    public PageInfo<VdrSupplyProdVo> selectAllByVdr(Integer page, Integer size, VdrSupplyProdVo vo) {
        PageInfo<VdrSupplyProdVo> pageInfo;
        List<VdrSupplyProdVo> vdrList ;
        PageHelper.startPage(page, size);
        vdrList = vdrSupplyProdMapper.selectAll(vo);
        pageInfo = new PageInfo<>(vdrList);
        return pageInfo;
    }

    @Override
    public int addOrDelete(VdrSupplyProdModel vo,SysUser user) {
        vo.setOprId(user.getPrsnl().getPrsnlId());
        int type = 0;
        if(vo.getAdd()!=null && vo.getAdd().size()>0){
            type = vdrSupplyProdMapper.add(vo);
        }
        if(vo.getDelete()!=null && vo.getDelete().size()>0){
            type =vdrSupplyProdMapper.delete(vo);
        }
        return type;
    }

    @Override
    public VdrSupplyProd selectByVer(String venderId, String vendeeId, String prodId) {
        Map<String,Object> map = new HashMap<>();
        map.put("venderId",venderId);
        map.put("vendeeId",vendeeId);
        map.put("prodId",prodId);
        return vdrSupplyProdMapper.selectByVer(map);
    }

    /**
     * 采购商购货信息明细查询
     * @param page
     * @param size
     * @param vo
     * @return
     */
    @Override
    public PageInfo<VdrSupplyProdVo> selectAllByVde(Integer page, Integer size, VdrSupplyProdVo vo) {
        PageInfo<VdrSupplyProdVo> pageInfo;
        List<VdrSupplyProdVo> vdrList ;
        PageHelper.startPage(page, size);
        vdrList = vdrSupplyProdMapper.selectAll(vo);
        pageInfo = new PageInfo<>(vdrList);
        return pageInfo;
    }

}
