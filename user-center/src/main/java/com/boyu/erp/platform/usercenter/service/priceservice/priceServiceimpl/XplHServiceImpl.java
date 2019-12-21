package com.boyu.erp.platform.usercenter.service.priceservice.priceServiceimpl;

import com.boyu.erp.platform.usercenter.entity.Price.Xpl;
import com.boyu.erp.platform.usercenter.entity.purchase.PpnScp;
import com.boyu.erp.platform.usercenter.entity.system.PcSynTask;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.Price.PpnDtlMapper;
import com.boyu.erp.platform.usercenter.mapper.Price.PpnMapper;
import com.boyu.erp.platform.usercenter.mapper.Price.XplHMapper;
import com.boyu.erp.platform.usercenter.mapper.Price.XplMapper;
import com.boyu.erp.platform.usercenter.mapper.purchase.PpnScpMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.PcSynTaskMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.service.priceservice.PpnService;
import com.boyu.erp.platform.usercenter.service.priceservice.XplHService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.vo.price.PpnDtlVo;
import com.boyu.erp.platform.usercenter.vo.price.PpnScpVo;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
import com.boyu.erp.platform.usercenter.vo.price.XplHVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: XplHServiceImpl
 * @description: 价格单历史服务层
 * @author: wz
 * @create: 2019-8-30 9:43
 */
@Slf4j
@Service
@Transactional
public class XplHServiceImpl implements XplHService {

    @Autowired
    private UsersService usersService;
    @Autowired
    private XplHMapper XplHMapper;

    /**
     * 价格单历史查询
     * @param vo
     * @param page
     * @param size
     * @param user
     * @return
     */
    @Override
    public PageInfo<XplHVo> selectAll(XplHVo vo, Integer page, Integer size, SysUser user) {
        List<XplHVo> list = null;
        /**
         * 系统管理员
         * */
        if (usersService.getAdmin(user) != null && usersService.getAdmin(user).getOprId() == -1L) {
            PageHelper.startPage(page, size);
            list = XplHMapper.selectALL(vo);
        } else {
            PageHelper.startPage(page, size);
            list = XplHMapper.selectByUnit(vo);
        }
        PageInfo<XplHVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
