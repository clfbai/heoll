package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.SttMapper;
import com.boyu.erp.platform.usercenter.model.wareh.SttFilterModel;
import com.boyu.erp.platform.usercenter.model.wareh.SttModel;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitOwnerSerivcer;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.service.warehouse.*;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.SttVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * 盘点服务
 * @author HHe
 * @date 2019/9/10 20:05
 */
@Service
@Transactional
public class SttServiceImpl implements SttService {
    @Autowired
    private SttMapper sttMapper;
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private DelivUtil delivUtil;
    @Autowired
    private WarehService warehService;
    @Autowired
    private SttBrandService sttBrandService;
    @Autowired
    private SttProdCatService sttProdCatService;
    @Autowired
    private SttProdClsService sttProdClsService;
    @Autowired
    private WarehStkService warehStkService;
    @Autowired
    private WarehStkXService warehStkXService;
    @Autowired
    private ProdClsService prodClsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StlService stlService;
    @Autowired
    private StlDtlService stlDtlService;
    @Autowired
    @Lazy
    private AsyncService asyncService;
    @Autowired
    private SysUnitOwnerSerivcer sysUnitOwnerSerivcer;
    /**
     * 盘点表根据筛选条件查询列表
     * @author HHe
     * @date 2019/9/10 20:11
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<SttVO> querySttListByFilterModel(SttFilterModel sttFilterModel, Integer page,Integer size,SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        List<SttVO> sttVOS;
        if(sttFilterModel.getWarehNum()!=null&&!"".equals(sttFilterModel.getWarehCode())){
            List<Long> warehIds = warehService.judgeReWarehIds(sttFilterModel.getWarehNum(),sttFilterModel.getUnitId());
            sttFilterModel.setWarehIds(warehIds);
        }
        PageHelper.startPage(page,size);
        //判断超级管理员
        if(false){
            //超管，查所有
            sttMapper.selectAllInAdmin(sttFilterModel);
        }else{
            //非超管
            sttVOS = sttMapper.querySttListByFilterModel(sttFilterModel);
        }
        sttVOS = this.packMap(sttVOS);
        PageInfo<SttVO> info = new PageInfo<>(sttVOS);
        return info;
    }

    /**
     * 加载盘点单下拉列表
     * @author HHe
     * @date 2019/9/12 15:03
     */
    @Override
    public Map<String, Object> loadSttPullDown() {
        Map<String,String> fMap = new LinkedHashMap<>();
        fMap.put("progress","STT_PROG");
        fMap.put("sttProdScp","STT_PROD_SCP");
        fMap.put("sttAreaScp", "STT_AREA_SCP");
        Map<String, Object> codeDtlMap = delivUtil.getCodeDtlMap1(fMap);
        String[] bk = {"locAdopted","astAdopted","bstAdopted","isFrml","effective","suspended","cancelled"};
        codeDtlMap = delivUtil.getCodeDtlMap4Boolea(codeDtlMap,bk);
        return codeDtlMap;
    }

    /**
     * 添加盘点表
     * @author HHe
     * @date 2019/9/16 11:24
     */
    @Override
    public int addStt(SttModel sttModel, SysUser sysUser) {
        Stt stt = this.sttControl(sttModel, sysUser);
        stt.setUnitId(sysUser.getDomain().getUnitId());
        stt.setEffective("F");
        stt.setProgress("PG");
        stt.setSuspended("F");
        stt.setCancelled("F");
        stt.setTtlExpdQty(new BigDecimal("0"));
        stt.setTtlExpdBox(0);
        stt.setTtlActQty(new BigDecimal("0"));
        stt.setTtlActBox(0);
        //编号
        SysRefNumDtl refNumDtl = new SysRefNumDtl();
        refNumDtl.setUnitId(sysUser.getDomain().getUnitId());
        refNumDtl.setRefNumId("STT_NUM");
        String sttNum = delivUtil.createDocNum(refNumDtl);
        stt.setSttNum(sttNum);
        //单据日期
        stt.setDocDate(new Date());
        //根据商品范围添加明细
        sttModel.setSttNum(sttNum);
        this.addSttProdScope(sttModel, sysUser);
        return sttMapper.insertSelective(stt);
    }

    /**
     * 修改盘点表
     * @author HHe
     * @date 2019/9/16 11:42
     */
    @Override
    public int updateStt(SttModel sttModel,SysUser sysUser) {
        //查询详情
        Stt stt1 = sttMapper.querySttByNumAndId(sttModel.getSttNum(),sttModel.getUnitId());
        if(!"PG".equals(stt1.getProgress())||!"F".equals(stt1.getSuspended())||!"F".equals(stt1.getCancelled())){
            throw new ServiceException("201", "状态不可修改");
        }
        Stt stt = this.sttControl(sttModel,sysUser);
        stt.setUnitId(sttModel.getUnitId());
        //删除所有明细
        this.delSttProdScopeDtlByNumAndId(sttModel,sysUser);
        if(!"A".equals(stt.getSttProdScp())){
            this.addSttProdScope(sttModel,sysUser);
        }
        return sttMapper.updateByPrimaryKeySelective(stt);
    }

    /**
     * 删除盘点表
     * @author HHe
     * @date 2019/9/16 11:46
     */
    @Override
    public int delStt(Stt pStt,SysUser sysUser) {
        Stt stt = sttMapper.querySttByNumAndId(pStt.getSttNum(), pStt.getUnitId());
        if((!"PG".equals(stt.getProgress())&&!"CN".equals(stt.getProgress()))||!"F".equals(stt.getSuspended())||!"F".equals(stt.getCancelled())){
            throw new ServiceException("201", "状态不可删除");
        }
        this.delSttProdScopeDtlByNumAndId(pStt,sysUser);
        return sttMapper.delSttByNumAndId(stt);
    }



    /**
     * 输入仓库编号判断合法，并加载信息
     * @author HHe
     * @date 2019/9/17 12:01
     */
    @Override
    @Transactional(readOnly = true)
    public SttVO loadWarehMessByCode(String warehCode, SysUser sysUser) {
        SysUnit sysUnit = sysUnitService.queryUnitByCode(warehCode);
        if(sysUnit==null){
            throw new ServiceException("201", "仓库编号不合法");
        }
        Wareh wareh = warehService.queryWarehByWarehId(sysUnit.getUnitId());
        SysUnit fsclUnit = sysUnitService.selectByPrimaryKey(wareh.getFsclUnitId());
        SttVO sttVO = new SttVO();
        sttVO.setWarehCp(sysUnit.getUnitName());
        sttVO.setFsclUnitCode(fsclUnit.getUnitCode());
        sttVO.setFsclUnitCp(fsclUnit.getUnitName());
        return sttVO;
    }
//    /**
//     * 确认盘点表
//     * @author HHe
//     * @date 2019/9/18 10:03
//     */
//    @Override
//    public int affirmStt(String sttNum, SysUser sysUser) {
//        Stt stt = sttMapper.querySttByNumAndId(sttNum,sysUser.getDomain().getUnitId());
//        if(stt==null){
//            throw new ServiceException("201", "单据不存在");
//        }
//        //判断状态
//        if(!"PG".equals(stt.getProgress())){
//            throw new ServiceException("201", "单据状态不可确认");
//        }
////        snptTimeAndexpdQty = new Stt();
//        List<WarehStk> warehStkList = null;
//        //生成库存快照
//        Long warehId = null;
//        if("A".equals(stt.getSttProdScp())){
//            //查询所有
//            warehStkList = warehStkService.queryWarehStkListByWarehId(stt.getWarehId());
//        }else if("B".equals(stt.getSttProdScp())){
//            List<SttBrand> sttBrands = sttBrandService.querySttBrandByObj(new SttBrand(sysUser.getDomain().getUnitId(), sttNum, null));
//            List<Long> brandIdList = new ArrayList<>();
//            sttBrands.forEach(s->brandIdList.add(s.getBrandId()));
//            List<Long> prodClsIdList = prodClsService.queryProdClsIdByBrandIdList(brandIdList);
//            warehStkList = this.queryWarehStkByProdIdList(prodClsIdList,warehId);
//        }else if("C".equals(stt.getSttProdScp())){
//            List<SttProdCat> sttProdCatList = sttProdCatService.querySttProdCatByObj(new SttProdCat(sysUser.getDomain().getUnitId(),sttNum,null));
//            List<String> prodCatIdList = new ArrayList<>();
//            sttProdCatList.forEach(s -> prodCatIdList.add(s.getProdCatId()));
//            List<Long> prodClsIdList = prodClsService.queryProdClsIdByProdCatIdList(prodCatIdList);
//            warehStkList = this.queryWarehStkByProdIdList(prodClsIdList,warehId);
//        }else if("P".equals(stt.getSttProdScp())){
//            List<SttProdCls> sttProdClsList = sttProdClsService.querySttProdClsByObj(new SttProdCls(sysUser.getDomain().getUnitId(),sttNum,null));
//            List<Long> prodClsIdList = new ArrayList<>();
//            sttProdClsList.forEach(s -> prodClsIdList.add(s.getProdClsId()));
//            warehStkList = this.queryWarehStkByProdIdList(prodClsIdList,warehId);
//        }else if("X".equals(stt.getSttProdScp())){
//            List<SttBrand> sttBrands = sttBrandService.querySttBrandByObj(new SttBrand(sysUser.getDomain().getUnitId(), sttNum, null));
//            List<Long> brandIdList = new ArrayList<>();
//            sttBrands.forEach(s->brandIdList.add(s.getBrandId()));
//            List<SttProdCat> sttProdCatList = sttProdCatService.querySttProdCatByObj(new SttProdCat(sysUser.getDomain().getUnitId(),sttNum,null));
//            List<String> prodCatIdList = new ArrayList<>();
//            sttProdCatList.forEach(s -> prodCatIdList.add(s.getProdCatId()));
//            List<Long> prodClsIdList = prodClsService.queryProdClsIdByProdCatIdListAndBrandIdList(prodCatIdList,brandIdList);
//            warehStkList = this.queryWarehStkByProdIdList(prodClsIdList,warehId);
//        }else{
//            throw new ServiceException("201", "error");
//        }
//        Stt snptTimeAndexpdQty = this.shootWarehStkX(warehStkList);
//        snptTimeAndexpdQty.setSttNum(sttNum);
//        snptTimeAndexpdQty.setUnitId(sysUser.getDomain().getUnitId());
//        snptTimeAndexpdQty.setProgress("CN");
//        return sttMapper.updateByPrimaryKeySelective(snptTimeAndexpdQty);
//    }

    /**
     * 重做盘点表
     * @author HHe
     * @date 2019/9/19 16:41
     */
    @Override
    public int reformStt(String sttNum, SysUser sysUser) {
        Stt stt = sttMapper.querySttByNumAndId(sttNum,sysUser.getDomain().getUnitId());
        if(stt==null){
            throw new ServiceException("201", "单据不存在");
        }
        //判断状态
        if(!"CN".equals(stt.getProgress())){
            throw new ServiceException("201", "单据状态不可重做");
        }
        //根据盘点时间和仓库Id删除所有快照
        warehStkXService.delWarehStkXByTimeAndWarehId(stt.getSnptTime(),stt.getWarehId());
        //修改快照时间
        Stt reformStt = new Stt();
        reformStt.setUnitId(sysUser.getDomain().getUnitId());
        reformStt.setSttNum(sttNum);
        reformStt.setSnptTime(null);
        reformStt.setProgress("PG");
        return sttMapper.reformStt(reformStt);
    }

    /**
     * 查询盘点表详情
     * @author HHe
     * @date 2019/9/20 17:28
     */
    @Override
    public SttVO queryDetails(Stt pStt, SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        //1.查询stt明细并且调用vo封装类；2.根据stt中范围信息查询对应的明细封装到sttvo的明细集合中
        Stt stt = sttMapper.querySttByNumAndId(pStt.getSttNum(),pStt.getUnitId());
        if(stt==null){
            throw new ServiceException("201", "单据信息错误");
        }
        SttVO sttVO = new SttVO();
        BeanUtils.copyProperties(stt,sttVO);
        List<SttVO> sttVOList = new ArrayList<>();
        sttVOList.add(sttVO);
        sttVO = this.packMap(sttVOList).get(0);
        if("B".equals(sttVO.getSttProdScp())){
            List<SttBrand> sttBrands = sttBrandService.querySttBrandByObj(new SttBrand(pStt.getUnitId(), pStt.getSttNum(), null));
            sttVO.setSttBrandList(sttBrands);
        }else if("C".equals(sttVO.getSttProdScp())){
            List<SttProdCat> sttProdCatList = sttProdCatService.querySttProdCatByObj(new SttProdCat(pStt.getUnitId(),pStt.getSttNum(),null));
            sttVO.setSttProdCatList(sttProdCatList);
        }else if("P".equals(sttVO.getSttProdScp())){
            List<SttProdCls> sttProdClsList = sttProdClsService.querySttProdClsByObj(new SttProdCls(pStt.getUnitId(),pStt.getSttNum(),null));
            sttVO.setSttProdClsList(sttProdClsList);
        }else if("X".equals(sttVO.getSttProdScp())){
            List<SttBrand> sttBrands = sttBrandService.querySttBrandByObj(new SttBrand(pStt.getUnitId(), pStt.getSttNum(), null));
            List<SttProdCat> sttProdCatList = sttProdCatService.querySttProdCatByObj(new SttProdCat(pStt.getUnitId(),pStt.getSttNum(),null));
            sttVO.setSttBrandList(sttBrands);
            sttVO.setSttProdCatList(sttProdCatList);
        }
        return sttVO;
    }

    /**
     * 根据盘点表编号和组织Id查询盘点表
     * @author HHe
     * @date 2019/9/25 11:54
     */
    @Override
    public Stt querySttByNumAndId(String sttNum, SysUser sysUser) {
        return sttMapper.querySttByNumAndId(sttNum,sysUser.getDomain().getUnitId());
    }

    /**
     * 修改盘点表实际总数量
     * @author HHe
     * @date 2019/9/25 17:15
     */
    @Override
    public int updateTtlActQty(String sttNum, SysUser sysUser, BigDecimal num) {
        return sttMapper.updateTtlActQty(sttNum,sysUser.getDomain().getUnitId(),num);
    }
    /**
     * 修改盘点表状态
     * @author HHe
     * @date 2019/9/26 14:38
     */
    @Override
    public int atterSttStatus(String sttNum,String type,SysUser sysUser) {
        Stt stt = sttMapper.querySttByNumAndId(sttNum,sysUser.getDomain().getUnitId());
        if(stt==null){
            throw new ServiceException("201", "单据不存在");
        }
        Stt reformStt = new Stt();
        reformStt.setSttNum(sttNum);
        reformStt.setUnitId(sysUser.getDomain().getUnitId());
        if("check".equals(type)){
            //审核
            //判断盘点清单中对应盘点表编号的单据是否都已确认
            List<Stl> stlList = stlService.queryListBySttNumAndId(sttNum,sysUser);
            for (Stl stl : stlList) {
                if(!"CN".equals(stl.getProgress())){
                    throw new ServiceException("201", "盘点表中盘点清单未确认");
                }
            }
            //判断状态
            if(!"CN".equals(stt.getProgress())||!"F".equals(stt.getSuspended())||!"F".equals(stt.getCancelled())){
                throw new ServiceException("201","状态不可审核");
            }
            //修改状态
            reformStt.setProgress("CK");
            reformStt.setEffective("T");
        }else if("post".equals(type)){
            //过账
            //判断状态
            if(!"CK".equals(stt.getProgress())||!"F".equals(stt.getSuspended())||!"F".equals(stt.getCancelled())){
                throw new ServiceException("201","状态不可过账");
            }
            //生成库存损益，在生成时修改库存数量（异步），修改数据库状态为中间状态，在生成损益方法完成修改为过账状态
            reformStt.setProgress("PI");
            asyncService.warehSttPAL(stt,sysUser);
        }
        return sttMapper.reformStt(reformStt);
    }
    /**
     * 功能列表展示
     * @author HHe
     * @date 2019/10/14 20:06
     */
    @Override
    public List<OperationVo> queryFunList(Stt pStt) {
        String url = "/warehouse/stt/sanfunction";
        Stt stt = sttMapper.querySttByNumAndId(pStt.getSttNum(), pStt.getUnitId());
        if(stt==null){
            throw new ServiceException("201","单据信息错误");
        }
        return delivUtil.judgeFunction(url,"STT",stt.getCancelled(),stt.getSuspended(),stt.getProgress());
    }
    /**
     * 确认
     * @author HHe
     * @date 2019/10/15 10:49
     */
    @Override
    public int confirm(SttModel sttModel) {
        Stt stt = this.judgeHaveStt(sttModel);
        if(!"PG".equals(stt.getProgress())||!"F".equals(stt.getSuspended())||!"F".equals(stt.getCancelled())){
            throw new ServiceException("201", "单据状态不可确认");
        }
        List<WarehStk> warehStkList;
        //生成库存快照
        Long warehId = null;
        Long unitId = sttModel.getUnitId();
        String sttNum = sttModel.getSttNum();
        if("A".equals(stt.getSttProdScp())){
            //查询所有
            warehStkList = warehStkService.queryWarehStkListByWarehId(stt.getWarehId());
        }else if("B".equals(stt.getSttProdScp())){
            List<SttBrand> sttBrands = sttBrandService.querySttBrandByObj(new SttBrand(unitId, sttNum, null));
            List<Long> brandIdList = new ArrayList<>();
            sttBrands.forEach(s->brandIdList.add(s.getBrandId()));
            List<Long> prodClsIdList = prodClsService.queryProdClsIdByBrandIdList(brandIdList);
            warehStkList = this.queryWarehStkByProdIdList(prodClsIdList,warehId);
        }else if("C".equals(stt.getSttProdScp())){
            List<SttProdCat> sttProdCatList = sttProdCatService.querySttProdCatByObj(new SttProdCat(unitId,sttNum,null));
            List<String> prodCatIdList = new ArrayList<>();
            sttProdCatList.forEach(s -> prodCatIdList.add(s.getProdCatId()));
            List<Long> prodClsIdList = prodClsService.queryProdClsIdByProdCatIdList(prodCatIdList);
            warehStkList = this.queryWarehStkByProdIdList(prodClsIdList,warehId);
        }else if("P".equals(stt.getSttProdScp())){
            List<SttProdCls> sttProdClsList = sttProdClsService.querySttProdClsByObj(new SttProdCls(unitId,sttNum,null));
            List<Long> prodClsIdList = new ArrayList<>();
            sttProdClsList.forEach(s -> prodClsIdList.add(s.getProdClsId()));
            warehStkList = this.queryWarehStkByProdIdList(prodClsIdList,warehId);
        }else if("X".equals(stt.getSttProdScp())){
            List<SttBrand> sttBrands = sttBrandService.querySttBrandByObj(new SttBrand(unitId, sttNum, null));
            List<Long> brandIdList = new ArrayList<>();
            sttBrands.forEach(s->brandIdList.add(s.getBrandId()));
            List<SttProdCat> sttProdCatList = sttProdCatService.querySttProdCatByObj(new SttProdCat(unitId,sttNum,null));
            List<String> prodCatIdList = new ArrayList<>();
            sttProdCatList.forEach(s -> prodCatIdList.add(s.getProdCatId()));
            List<Long> prodClsIdList = prodClsService.queryProdClsIdByProdCatIdListAndBrandIdList(prodCatIdList,brandIdList);
            warehStkList = this.queryWarehStkByProdIdList(prodClsIdList,warehId);
        }else{
            throw new ServiceException("201", "error");
        }
        Stt snptTimeAndexpdQty = this.shootWarehStkX(warehStkList);
        snptTimeAndexpdQty.setSttNum(sttNum);
        snptTimeAndexpdQty.setUnitId(unitId);
        snptTimeAndexpdQty.setProgress("CN");
        return sttMapper.updateByPrimaryKeySelective(snptTimeAndexpdQty);
    }

    /**
     * 重做
     * @author HHe
     * @date 2019/10/15 11:17
     */
    @Override
    public int redo(SttModel sttModel) {
        Stt stt = this.judgeHaveStt(sttModel);
        //判断状态
        if(!"CN".equals(stt.getProgress())||!"F".equals(stt.getSuspended())||!"F".equals(stt.getCancelled())){
            throw new ServiceException("201", "单据状态不可重做");
        }
        String sttNum = sttModel.getSttNum();
        Long unitId = sttModel.getUnitId();
        //根据盘点时间和仓库Id删除所有快照
        warehStkXService.delWarehStkXByTimeAndWarehId(stt.getSnptTime(),stt.getWarehId());
        //修改快照时间
        Stt reformStt = new Stt();
        reformStt.setUnitId(unitId);
        reformStt.setSttNum(sttNum);
        reformStt.setSnptTime(null);
        reformStt.setProgress("PG");
        return sttMapper.reformStt(reformStt);
    }

    @Override
    public int check(SttModel sttModel) {
        return 0;
    }

    @Override
    public int uncheck(SttModel sttModel) {
        return 0;
    }

    @Override
    public int suspend(SttModel sttModel) {
        return 0;
    }

    @Override
    public int resume(SttModel sttModel) {
        return 0;
    }

    @Override
    public int abolish(SttModel sttModel) {
        return 0;
    }
    /**
     * 过账
     * @author HHe
     * @date 2019/10/19 14:32
     */
    @Override
    public int post(SttModel sttModel) {
        return 0;
    }
    /**
     * Assist
     * 判断单据是否存在，存在返回
     * @author HHe
     * @date 2019/10/15 11:02
     */
    public Stt judgeHaveStt(SttModel sttModel){
        Stt stt = sttMapper.querySttByNumAndId(sttModel.getSttNum(),sttModel.getUnitId());
        if(stt==null){
            throw new ServiceException("201", "单据信息异常");
        }
        return stt;
    }
    /**
     * Assist
     * 封装vo类对应的map
     * @author HHe
     * @date 2019/9/25 19:17
     */
    public List<SttVO> packMap(List<SttVO> vos) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Map<String, Object> map = new HashMap<>();
        map.put("sys_prsnl",new String[]{"oprId","chkrId","acckId"});
        map.put("sys_unit",new String[]{"warehId","fsclUnitId"});
        Map<String, String> uMap = new HashMap<>();
        uMap.put("fsclUnitId","fsclUnitCode-unitCode");
//        Map<String, String> pMap = new HashMap<>();
//        pMap.put("fsclUnitId","fsclUnitCode-unitCode");
        map.put("unitProp",uMap);
//        map.put("prsnlProp",pMap);
        List<SttVO> sttVOS = delivUtil.loadCodeName2VO2(map, vos);
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("locAdopted", "BOOLEA");
        codeMap.put("astAdopted", "BOOLEA");
        codeMap.put("bstAdopted", "BOOLEA");
        codeMap.put("sttAreaScp", "STT_AREA_SCP");
        codeMap.put("sttProdScp", "STT_PROD_SCP");
        codeMap.put("isFrml", "BOOLEA");
        codeMap.put("effective", "BOOLEA");
        codeMap.put("progress", "STT_PROG");
        codeMap.put("suspended", "BOOLEA");
        codeMap.put("cancelled", "BOOLEA");
        sttVOS = delivUtil.loadCPByCodeDtl(codeMap,sttVOS);
        return sttVOS;
    }
    /**
     * Assist
     * 根据商品品种Id集合和仓库id查询
     * @author HHe
     * @date 2019/9/19 16:15
     */
    public List<WarehStk> queryWarehStkByProdIdList(List<Long> prodClsIdList,Long warehId){
        List<Long> prodIdList = productService.queryProdIdByProdClsIdList(prodClsIdList);
        return warehStkService.queryWarehStkListByProdIdList(warehId,prodIdList);
    }
    /**
     * Assist
     * 添加仓库库存快照
     * @author HHe
     * @date 2019/9/18 19:25
     */
    public Stt shootWarehStkX(List<WarehStk> warehStkList){
        BigDecimal ttlQty = new BigDecimal("0");
        WarehStkX warehStkX;
        List<WarehStkX> warehStkXList = new ArrayList<>();
        for (WarehStk stk : warehStkList) {
            warehStkX = new WarehStkX();
            warehStkX.setProdId(stk.getProdId());
            warehStkX.setStkOnHand(stk.getStkOnHand());
            warehStkX.setWarehId(stk.getWarehId());
            warehStkXList.add(warehStkX);
            ttlQty = ttlQty.add(stk.getStkOnHand());
        }
        Timestamp nowDate = new Timestamp(System.currentTimeMillis());
        //存储快照
        warehStkXService.addWarehStkXList(warehStkXList,nowDate);
        Stt stt = new Stt();
        stt.setSnptTime(nowDate);
        stt.setTtlExpdQty(ttlQty);
        return stt;
    }

    /**
     * Assist
     * 根据盘点表范围添加明细
     * @author HHe
     * @date 2019/9/18 15:48
     */
    public int addSttProdScope(SttModel sttModel,SysUser sysUser){
        int i = 0;
        if("B".equals(sttModel.getSttProdScp())){
            if(sttModel.getSttBrandList()==null||sttModel.getSttBrandList().size()<=0){
                throw new ServiceException("201", "品牌不可为空");
            }
            i+=sttBrandService.addSttBrandList(sttModel.getSttBrandList(),sttModel.getSttNum(),sysUser);
        }else if("C".equals(sttModel.getSttProdScp())){
            if(sttModel.getSttProdCatList()==null||sttModel.getSttProdCatList().size()<=0){
                throw new ServiceException("201", "商品分类不可为空");
            }
            i+=sttProdCatService.addSttProdCatList(sttModel.getSttProdCatList(),sttModel.getSttNum(),sysUser);
        }else if("P".equals(sttModel.getSttProdScp())){
            if(sttModel.getSttProdClsList()==null||sttModel.getSttProdClsList().size()<=0){
                throw new ServiceException("201", "商品品种不可为空");
            }
            i+=sttProdClsService.addSttProdClsList(sttModel.getSttProdClsList(), sttModel.getSttNum(), sysUser);
        }else if("X".equals(sttModel.getSttProdScp())){
            if(sttModel.getSttBrandList()==null||sttModel.getSttBrandList().size()<=0){
                throw new ServiceException("201", "品牌不可为空");
            }
            if(sttModel.getSttProdCatList()==null||sttModel.getSttProdCatList().size()<=0){
                throw new ServiceException("201", "商品分类不可为空");
            }
            i+=sttProdCatService.addSttProdCatList(sttModel.getSttProdCatList(),sttModel.getSttNum(),sysUser);
            i+=sttBrandService.addSttBrandList(sttModel.getSttBrandList(),sttModel.getSttNum(),sysUser);
        }
        return i;
    }

    /**
     * Assist
     * 根据盘点表编号和组织Id删除所有商品范围明细
     * @author HHe
     * @date 2019/9/18 15:59
     */
    public int delSttProdScopeDtlByNumAndId(Stt stt,SysUser sysUser){
        int i = 0;
        i+=sttBrandService.delSttBrandByNumAndUnitId(stt,sysUser);
        i+=sttProdClsService.delSttProdClsByNumAndUnitId(stt,sysUser);
        i+=sttProdCatService.delSttProdCatByNumAndUnitId(stt,sysUser);
        return i;
    }
    /**
     * Assist
     * 添加/修改盘点表前的流程控制
     * @author HHe
     * @date 2019/9/17 10:44
     */
    public Stt sttControl(SttModel sttModel,SysUser sysUser) {
        Wareh wareh = delivUtil.judgeWareh(sttModel.getWarehCode(), sysUser);
        Stt stt = new Stt();
        BeanUtils.copyProperties(sttModel,stt);
        stt.setWarehId(wareh.getWarehId());
        stt.setLocAdopted(wareh.getLocAdopted());
        stt.setAstAdopted(wareh.getAstAdopted());
        stt.setBstAdopted(wareh.getBstAdopted());
        stt.setFsclUnitId(wareh.getFsclUnitId());
        if(StringUtils.NullEmpty(sttModel.getSttAreaScp())){
            stt.setSttAreaScp("A");
        }else{
            stt.setSttAreaScp(sttModel.getSttAreaScp());
        }
        if(StringUtils.NullEmpty(sttModel.getSttProdScp())){
            stt.setSttProdScp("A");
        }else{
            stt.setSttProdScp(sttModel.getSttProdScp());
        }
        if(StringUtils.NullEmpty(sttModel.getIsFrml())){
            stt.setIsFrml("T");
        }else{
            stt.setIsFrml(sttModel.getIsFrml());
        }
        if(sttModel.getFsclDate()==null){
            stt.setFsclDate(new Date());
        }
        stt.setOprId(sysUser.getPrsnl().getPrsnlId());
        stt.setOpTime(new Timestamp(System.currentTimeMillis()));
        return stt;
    }
}
