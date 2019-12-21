package com.boyu.erp.platform.usercenter.service.caservice.caserviceimpl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.basic.AbMtrKey;
import com.boyu.erp.platform.usercenter.entity.basic.AbPf;
import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.AbMtrMapper;
import com.boyu.erp.platform.usercenter.mapper.basic.AbPfMapper;
import com.boyu.erp.platform.usercenter.mapper.basic.CaMapper;
import com.boyu.erp.platform.usercenter.service.caservice.CaService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.system.CaVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: CaAccServerImpl
 * @description: 往来账户
 * @author: wz
 * @create: 2019-9-10 10:19
 */
@Service
@Transactional
public class CaServerImpl implements CaService {

    @Autowired
    private UsersService usersService;
    @Autowired
    private CaMapper caMapper;
    @Autowired
    private DelivUtil delivUtil;
    @Autowired
    private SysRefNumService sysRefNumService;
    @Autowired
    private AbPfMapper abPfMapper;
    @Autowired
    private AbMtrMapper abMtrMapper;

    /**
     * 往来账户分页查询
     * @param vo
     * @param page
     * @param size
     * @param user
     * @return
     */
    @Override
    public PageInfo<CaVo> selectAll(CaVo vo, Integer page, Integer size, SysUser user) throws Exception {
        List<CaVo> list = null;
        /**
         * 系统管理员
         * */
        if (usersService.getAdmin(user) != null && usersService.getAdmin(user).getOprId() == -1L) {
            PageHelper.startPage(page, size);
            list = caMapper.selectALL(vo);
        } else {
            PageHelper.startPage(page, size);
            list = caMapper.selectByUnit(vo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("sys_prsnl",new String[]{"oprId"});
        map.put("sys_unit",new String[]{"caUnitId"});
        Map<String, Object> uMap = new HashMap<>();
        uMap.put("oprId","oprCode-prsnlCode");
        map.put("prsnlProp",uMap);
        list = delivUtil.loadCodeName2VO2(map, list);
        Map<String,Object> codeMap = new LinkedHashMap<>();
        codeMap.put("caType", "CA_TYPE");
        codeMap.put("balDir", "BAL_DIR");
        codeMap.put("bloMode", "BLO_MODE");
        codeMap.put("caStatus", "CA_STATUS");
        list = delivUtil.loadCPByCodeDtl(codeMap, list);
        PageInfo<CaVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 往来账户新增
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int insertCa(CaVo vo, SysUser user) {
        //查询是否存在数据
        if(caMapper.selectByCaUnitId(vo)!=null){
            throw new ServiceException(ResultCode.DATA_REPEAT, "存在相同数据！");
        }
        //判断中转方是否为空
        if(StringUtils.isNotEmpty(vo.getEndUnitId()+"")){
            this.verification(vo);
        }
        vo.setOprId(user.getPrsnl().getPrsnlId());
        //查询是否存在记录  不存在直接新增，存在更改状态
        Ca ca = caMapper.selectByCaUnitId(new Ca(vo.getUnitId(),vo.getCaUnitId()));
        if(ca==null){
            //获得ca_id
            String caId = sysRefNumService.viceNum(new SysUser(Long.valueOf(vo.getUnitId()+""), Long.valueOf(user.getPrsnl().getPrsnlId().intValue())), "CA_ID");
            vo.setCaId(Long.valueOf(caId));
            return caMapper.insertSelective(vo);
        }else{
            vo.setCaStatus("A");
            vo.setCaId(ca.getCaId());
            return caMapper.updateByPrimaryKeySelective(vo);
        }
    }

    /**
     * 往来账户修改
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int updateCa(CaVo vo, SysUser user) {
        //判断中转方是否为空
        if(StringUtils.isNotEmpty(vo.getEndUnitId()+"")){
            this.verification(vo);
        }
        vo.setOprId(user.getPrsnl().getPrsnlId());
        return caMapper.updateByPrimaryKeySelective(vo);
    }

    /**
     * 往来账户删除
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int deleteCa(CaVo vo, SysUser user) {
        vo.setCaStatus("D");//代表物理删除
        vo.setOprId(user.getPrsnl().getPrsnlId());
        return caMapper.updateByPrimaryKeySelective(vo);
    }
    /**
     * 查询往来账户
     * @author HHe
     * @date 2019/10/21 11:54
     */
    @Override
    public Ca queryCaByRcvAndUnit(Long fsclUnitId, Long rcvFsclUnitId,String caType) {
        return caMapper.queryCaByRcvAndUnit(fsclUnitId,rcvFsclUnitId,caType);
    }


    /**
     * 新增和修改时，中转方不为空的验证
     * @param vo
     */
    private void verification(CaVo vo) {
        //查询往来账户是否启用账套
        AbPf pf = abPfMapper.selectByPrimaryKey(vo.getCaUnitId());
        AbMtrKey mtr = abMtrMapper.selectByAbMtrKey(new AbMtrKey(vo.getUnitId(),vo.getCaUnitId()));
        if(mtr==null && pf!=null?!pf.getAbStatus().equals("N")&&!pf.getAbStatus().equals("C"):false ){
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "指定转送方时，往来组织必须在监管范围内!");
        }
    }
}
