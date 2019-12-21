package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stl;
import com.boyu.erp.platform.usercenter.entity.warehouse.StlDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.StlMapper;
import com.boyu.erp.platform.usercenter.model.wareh.StlFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.StlModel;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.warehouse.StlDtlService;
import com.boyu.erp.platform.usercenter.service.warehouse.StlService;
import com.boyu.erp.platform.usercenter.service.warehouse.SttService;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.warehouse.StlDtlVO;
import com.boyu.erp.platform.usercenter.vo.warehouse.StlVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * 盘点清单服务
 * @author HHe
 * @date 2019/9/23 10:55
 */
@Service
@Transactional
public class StlServiceImpl implements StlService {
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private StlMapper stlMapper;
    @Autowired
    private DelivUtil delivUtil;
    @Autowired
    private StlDtlService stlDtlService;
    @Autowired
    private WarehService warehService;
    @Autowired
    private SttService sttService;
    /**
     * 根据筛选条件查询盘点清单列表
     * @author HHe
     * @date 2019/9/23 11:20
     */
    @Override
    public PageInfo<StlVO> queryList(Integer page, Integer size, StlFilterModel stlFilterModel, SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        if(stlFilterModel.getWarehNum()!=null && !"".equals(stlFilterModel.getWarehNum())){
            //根据组织Id和仓库Num到仓库查询是否有仓库,没有则返回-1
            List<Long> warehIds = warehService.judgeReWarehIds(stlFilterModel.getWarehNum(), stlFilterModel.getUnitId());
            stlFilterModel.setWarehIds(warehIds);
        }
        List<StlVO> stlVOList = new ArrayList<>();
        PageHelper.startPage(page, size);
        if(false){
            //超级管理员
        }else{
            stlVOList = stlMapper.queryStlListByFilter(stlFilterModel,sysUser.getDomain().getUnitId());
        }
        //工具类封装字段
        stlVOList = this.packFields(stlVOList);
        PageInfo<StlVO> info = new PageInfo<>(stlVOList);
        return info;
    }

    /**
     * 添加盘点清单
     * @author HHe
     * @date 2019/9/23 14:29
     */
    @Override
    public int addStl(StlModel stlModel, SysUser sysUser) {
        Long unitId = stlModel.getUnitId();
        stlModel = this.insertControl(stlModel,sysUser);
        //生成编号
        SysRefNumDtl refNumDtl = new SysRefNumDtl();
        refNumDtl.setRefNumId("STL_NUM");
        refNumDtl.setUnitId(stlModel.getUnitId());
        String docNum = delivUtil.createDocNum(refNumDtl);
        stlModel.setStlNum(docNum);
        stlModel.setDocDate(new Date());
        //计算总数量
        BigDecimal ttlQtl = new BigDecimal("0");
        for (StlDtl stlDtl: stlModel.getStlDtlList()) {
            ttlQtl = ttlQtl.add(stlDtl.getQty());
        }
        stlModel.setTtlQty(ttlQtl);
        //进度
        stlModel.setProgress("PG");
        //添加明细
        stlDtlService.addStlDtlList(stlModel,sysUser);
        return stlMapper.insertSelective(stlModel);
    }

    /**
     * 修改盘点清单
     * @author HHe
     * @date 2019/9/23 15:23
     */
    @Override
    public int updateStl(StlModel stlModel, SysUser sysUser) {
        //判断进度，"确认"不可修改
        Stl stl = stlMapper.queryStlByStlNumAndId(stlModel.getStlNum(), sysUser.getDomain().getUnitId());
        if(!"PG".equals(stl.getProgress())||!"F".equals(stl.getSuspended())){
            throw new ServiceException("201","盘点清单状态不可修改");
        }
        stlModel = this.insertControl(stlModel,sysUser);
        //删除相关明细，再添加；
        stlDtlService.delStlDtlByNumAndId(stlModel,sysUser);
        stlDtlService.addStlDtlList(stlModel,sysUser);
        BigDecimal ttlQtl = new BigDecimal("0");
        for (StlDtl stlDtl: stlModel.getStlDtlList()) {
            ttlQtl = ttlQtl.add(stlDtl.getQty());
        }
        stlModel.setTtlQty(ttlQtl);
        return stlMapper.updateStl(stlModel,sysUser.getDomain().getUnitId());
    }

    /**
     * 添加、修改盘点清单流程控制
     * @author HHe
     * @date 2019/9/24 10:03
     */
    public StlModel insertControl(StlModel stlModel,SysUser sysUser){
        //判断仓库是否为空
        if(stlModel.getWarehId()==null){
            throw new ServiceException("201", "请选择仓库");
        }
        if(StringUtils.NullEmpty(stlModel.getStkForm())){
            stlModel.setStkForm("BN");
        }
        stlModel.setOprId(sysUser.getPrsnl().getPrsnlId());
        stlModel.setOpTime(new Timestamp(System.currentTimeMillis()));
        return stlModel;
    }

    /**
     * 删除盘点明细
     * @author HHe
     * @date 2019/9/23 16:43
     */
    @Override
    public int delStl(StlModel stlModel, SysUser sysUser) {
        //删除相关明细
        stlDtlService.delStlDtlByNumAndId(stlModel,sysUser);
        return stlMapper.delStl(stlModel.getStlNum(),stlModel.getUnitId());
    }
    /**
     * 查询盘点清单详情
     * @author HHe
     * @date 2019/9/25 9:48
     */
    @Override
    public StlVO queryDetails(Stl pStl, SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Stl stl = stlMapper.queryStlByStlNumAndId(pStl.getStlNum(),pStl.getUnitId());
        if(stl==null){
            throw new ServiceException("201","单据信息错误");
        }
        StlVO stlVO = new StlVO();
        BeanUtils.copyProperties(stl,stlVO);
        stlVO = this.packFields(Arrays.asList(new StlVO[]{stlVO})).get(0);
        StlDtl stlDtl = new StlDtl();
        stlDtl.setStlNum(stl.getStlNum());
        stlDtl.setUnitId(stl.getUnitId());
        List<StlDtlVO> stlDtlVOList = stlDtlService.queryListByStlNumAndId(stlDtl, sysUser);
        stlDtlVOList = delivUtil.queryProdDetailsByProdIdList(stlDtlVOList, new HashMap<>());
        stlVO.setStlDtlVOList(stlDtlVOList);
        return stlVO;
    }

    /**
     * Assist
     * 封装字段
     *
     * @author HHe
     * @date 2019/10/8 15:19
     */
    public List<StlVO> packFields(List<StlVO> stlVOList) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Map<String, Object> map = new HashMap<>();
        map.put("sys_prsnl",new String[]{"oprId","stkrId"});
        map.put("sys_unit",new String[]{"warehId"});
        stlVOList = delivUtil.loadCodeName2VO2(map, stlVOList);
        Map<String,Object> codeMap = new LinkedHashMap<>();
        codeMap.put("locAdopted", "BOOLEA");
        codeMap.put("stkForm", "STK_FORM");
        codeMap.put("progress", "STL_PROG");
        codeMap.put("suspended", "BOOLEA");
        stlVOList = delivUtil.loadCPByCodeDtl(codeMap, stlVOList);
        return stlVOList;
    }
    /**
     * 确认盘点清单
     * @author HHe
     * @date 2019/9/25 11:47
     */
    @Override
    public int atterStl(Stl stl, SysUser sysUser,String type) {
        Stl dbStl = stlMapper.queryStlByStlNumAndId(stl.getStlNum(), sysUser.getDomain().getUnitId());
        StlModel stlModel = new StlModel();
        if(dbStl==null){
            throw new ServiceException("201", "盘点清单不存在");
        }
        BigDecimal num = new BigDecimal("0");
        if("confirm".equals(type)){
            //确认
            if(StringUtils.NullEmpty(stl.getSttNum())){
                throw new ServiceException("201", "未绑定盘点表不可确认");
            }
            //查询盘点表是否存在
            Stt stt = sttService.querySttByNumAndId(stl.getSttNum(), sysUser);
            if(stt==null){
                throw new ServiceException("201", "盘点表不存在");
            }
            if(!"PG".equals(dbStl.getProgress())||!"F".equals(dbStl.getSuspended())){
                throw new ServiceException("201", "盘点清单状态不可修改");
            }
            stlModel.setProgress("CN");
            //将总数量加到盘点表的实际总数量
            num = stl.getTtlQty();
        }else if("redo".equals(type)){
            //重做
            if(!"CN".equals(dbStl.getProgress())||!"F".equals(dbStl.getSuspended())){
                throw new ServiceException("201", "盘点清单状态不可修改");
            }
            stlModel.setProgress("PG");
            //将总数量减去盘点表的实际总数量
            num = stl.getTtlQty().negate();
        }
        sttService.updateTtlActQty(stl.getSttNum(),sysUser,num);
        stlModel.setStlNum(stl.getStlNum());
        return stlMapper.updateStl(stlModel,sysUser.getDomain().getUnitId());
    }
    /**
     * 根据盘点表编号判断盘点清单集合
     * @author HHe
     * @date 2019/9/26 15:07
     */
    @Override
    public List<Stl> queryListBySttNumAndId(String sttNum, SysUser sysUser) {
        return null;
    }
    /**
     * 根据盘点表编号和组织id查询盘点清单编号集合
     * @author HHe
     * @date 2019/9/26 19:51
     */
    @Override
    public List<String> queryStlNumListBySttNumAndId(String sttNum, Long unitId) {
        return stlMapper.queryStlNumListBySttNumAndId(sttNum,unitId);
    }
    /**
     * 加载页面下拉
     * @author HHe
     * @date 2019/10/8 16:46
     */
    @Override
    public Map<String, Object> loadStlPullDown() {
        Map<String, String> fMap = new HashMap<>();
        fMap.put("progress","STL_PROG");
        fMap.put("stkForm","STK_FORM");
        return delivUtil.getCodeDtlMap2(fMap);
    }
}
