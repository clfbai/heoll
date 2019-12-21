package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.basic.*;
import com.boyu.erp.platform.usercenter.entity.mq.Freeze;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.mq.ven.Supplier;
import com.boyu.erp.platform.usercenter.entity.mq.ven.Unit;
import com.boyu.erp.platform.usercenter.entity.sales.VdeAttr;
import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.*;
import com.boyu.erp.platform.usercenter.mapper.sales.VdeAttrMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.*;
import com.boyu.erp.platform.usercenter.service.base.RabbitTemplateSerivce;
import com.boyu.erp.platform.usercenter.service.base.SendSerivce;
import com.boyu.erp.platform.usercenter.service.common.impl.ButtonCommonService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.system.VendeeService;
import com.boyu.erp.platform.usercenter.service.system.VenderService;
import com.boyu.erp.platform.usercenter.utils.RandomStringUtils;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.FiledUtils;
import com.boyu.erp.platform.usercenter.utils.refttable.ReftClass;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VenderVo;
import com.boyu.erp.platform.usercenter.vo.sales.VendeeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Classname venderServiceimpl
 * @Description TODO
 * @Date 2019/6/26 16:22
 * @Created wz
 */
@Service
@Transactional
public class VendeeServiceimpl implements VendeeService {

    public static final String YW = "ER_";

    //退购合同模块按钮参数
    static final String parameter = "vdeButton";

    //冻结
    static final String freeze = "/sales/vendee/freeze";
    //解冻
    static final String defreeze = "/sales/vendee/defreeze";

    @Autowired
    private VendeeMapper vendeeMapper;
    @Autowired
    private SysUnitClsfMapper sysUnitClsfMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    private ReftClass reftClass;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private FiledUtils filedUtils;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private VenderMapper venderMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private SysUnitMapper sysUnitMapper;
    @Autowired
    private SysDomainMapper sysDomainMapper;
    @Autowired
    private SysDomainServiceImpl sysDomainServiceImpl;
    @Autowired
    private VenderService venderService;
    @Autowired
    private VdeAttrMapper vdeAttrMapper;
    @Autowired
    private CaMapper caMapper;
    @Autowired
    private PartnerMapper partnerMapper;
    @Autowired
    private SysUnitOwnerMapper sysUnitOwnerMapper;
    @Autowired
    private SendSerivce sendSerivce;
    @Autowired
    private ButtonCommonService buttonCommonService;

    /**
     * 供应商添加时通过参数判断添加采购商
     *
     * @param vo
     * @return
     * @throws ServiceException
     */
    @Override
    public void insertVender(VenderVo vo, SysUser user) throws ServiceException {
        //判断是否存在数据
        Vendee vd = vendeeMapper.selectByPrimaryKey(new Vendee(vo.getOwnerId(), vo.getUnitId()));

        if(vd==null){
            //添加采购商数据
            vendeeMapper.insertByVender(vo);
        }else{
            if(!vd.getVdeStatus().equals("A")){
                vd.setVdeStatus("A");
                vd.setUpdTime(new Date());
                vendeeMapper.updateByPrimaryKeySelective(vd);
            }
        }

        SysUnit unit = sysUnitMapper.selectByPrimaryKey(vo.getOwnerId());

        //判断组织是否存在 存在就更新  不存在就新增
        purchaseService.updateOwner(new SysUnitOwner(vo.getOwnerId(), vo.getUnitId(), unit.getUnitCode(), "", "F"));

        purchaseService.updatePartner(new Partner(vo.getOwnerId(), vo.getUnitId(), vo.getPcrLvl(),
                StringUtils.isNotEmpty(vo.getDfltFwdrId()) ? Long.parseLong(vo.getDfltFwdrId()) : null, vo.getDfltDelivMthd(), vo.getManCond(), vo.getPtnrStatus()));

        //创建关联数据
        int vdeType = 0;
        if (sysUnitClsfMapper.selectByPrimaryKey(new SysUnitClsf(vo.getOwnerId(), vo.getUnitId(), "VE")) == null) {
            vdeType = sysUnitClsfMapper.insertSysUnitClsf(new SysUnitClsf(vo.getOwnerId(), vo.getUnitId(), "VE"));
        }
        if (sysUnitClsfMapper.selectByPrimaryKey(new SysUnitClsf(vo.getOwnerId(), vo.getUnitId(), "PT")) == null) {
            sysUnitClsfMapper.insertSysUnitClsf(new SysUnitClsf(vo.getOwnerId(), vo.getUnitId(), "PT"));
        }
        if (sysUnitClsfMapper.selectByPrimaryKey(new SysUnitClsf(vo.getUnitId(), vo.getUnitId(), "VW")) == null) {
            sysUnitClsfMapper.insertSysUnitClsf(new SysUnitClsf(vo.getUnitId(), vo.getUnitId(), "VW"));
        }

        //调用私有方法创建默认往来账户
        purchaseService.accountBy(vo.getUnitId(), vo.getOwnerId(), Long.valueOf(vo.getOprId()), "TEMPLET_VENDEE_CODE", "C", user);

        if(vdeType==1){
            //推送消息
            Supplier su = new Supplier();
            su.setId(YW + unit.getUnitId());
            su.setCode(unit.getUnitCode());
            su.setName(unit.getUnitName());
            su.setUnitId(vo.getUnitId() + "");
            su.setPhone(unit.getTelNum());
            su.setFax(unit.getFaxNum());
            su.setRegion(unit.getProvince() + unit.getCity() + unit.getDistrict());
            su.setAddress(unit.getAddress());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            su.setCreateDate(sdf.format(new Date()));
            MessageObject msg = new MessageObject("franchisee.add", unit.getUnitId() + "_" + RandomStringUtils.crateUuid(6), su);
            sendSerivce.send("exh.master.up", "master.up", msg);
        }

    }

    /**
     * 分页查询采购商
     *
     * @param page
     * @param size
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<VendeeVo> selectAll(Integer page, Integer size, VendeeVo vo, SysUser user) throws Exception {

        PageInfo<VendeeVo> pageInfo;
        //系统管理员
        if (false) {
            PageHelper.startPage(page, size);
            List<VendeeVo> venderList = vendeeMapper.selectALL(vo);
            pageInfo = new PageInfo<>(venderList);
            pageInfo.setList(setList(venderList));
            return pageInfo;
        }
        //组织
        PageHelper.startPage(page, size);
        List<VendeeVo> venderList = vendeeMapper.selectByUnit(vo);
        pageInfo = new PageInfo<>(venderList);
        pageInfo.setList(setList(venderList));
        return pageInfo;
    }

    /**
     * 给null值赋予初始值
     *
     * @param vendeeList
     * @return
     * @throws Exception
     */
    private List<VendeeVo> setList(List<VendeeVo> vendeeList) throws Exception {
        List<VendeeVo> list = new ArrayList<>();
        for (VendeeVo vo : vendeeList) {
            reftClass.setObject(vo);
            list.add(vo);
        }
        return list;
    }

    /**
     * 添加采购商
     *
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int insertVendee(VendeeVo vo, SysUser user) throws Exception {

        //将当前操作员放入对象中
        vo.setOprId(user.getPrsnl().getPrsnlId() + "");

        if(StringUtils.NullEmpty(vo.getCtrlUnitId())){
            vo.setCtrlUnitId(vo.getOwnerId()+"");
        }

        String bit = parameterMapper.findById("VENDEE_CREAT_TABLE_FIELDS").getParmVal();

        filedUtils.getFlie(bit, (Object) vo);
        //当传过来的unitId是空时,就说明它是一个全新的数据，就走全新的创建流程
        //当传过来的unitId是有值的时候，去判断是否已经是供应商，是的话走修改流程，不是的话相应的去创建或修改数据
        if (vo.getUnitId() == null) {
            vo.setUnitId(purchaseService.addUnitId("UNIT_ID", user));
        } else {
            // 如果是走全新的就不用判断
            SysParameter par = parameterMapper.findById("PSA_SEPARATED_ALLOWED");
            if (par.getParmVal().equals("F")) {
                List<Vender> venderList = venderMapper.findByVendeeVo(vo);
                if (venderList != null && venderList.size() > 0) {
                    throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "当前组织已是供应商！");
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("vo", vo);

        //先存公司
        int comType = this.updateCompany(map);

        //修改伙伴信息
        purchaseService.updatePartner(new Partner(vo.getUnitId(), vo.getOwnerId(), vo.getPcrLvl(),
                StringUtils.isNotEmpty(vo.getDfltFwdrId()) ? Long.parseLong(vo.getDfltFwdrId()) : null, vo.getDfltDelivMthd(), vo.getManCond(), vo.getPtnrStatus()));

        //存供应商与组织
        this.updateOrAddVendee(vo);

        if (sysUnitMapper.selectByPrimaryKey(vo.getUnitId()) == null) {
            String hier = "";
            if(!StringUtils.NullEmpty(user.getUnit().getUnitHierarchy())){
                hier = user.getUnit().getUnitHierarchy()+ "|" ;
            }
            vo.setUnitHierarchy( hier + vo.getUnitName());
            vo.setUnitType("2");
            map.put("vo", vo);
            sysUnitMapper.insertByVd(map);
        } else {
            map.put("vo", vo);
            sysUnitMapper.updateByVd(map);
        }

        //创建关联数据
        int vdeType = 0;
        if (sysUnitClsfMapper.selectByPrimaryKey(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "VE")) == null) {
            vdeType = sysUnitClsfMapper.insertSysUnitClsf(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "VE"));
        }
        if (sysUnitClsfMapper.selectByPrimaryKey(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "PT")) == null) {
            sysUnitClsfMapper.insertSysUnitClsf(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "PT"));
        }
        if (sysUnitClsfMapper.selectByPrimaryKey(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "VW")) == null) {
            sysUnitClsfMapper.insertSysUnitClsf(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "VW"));
        }

        //判断组织是否存在 存在就更新  不存在就新增
        purchaseService.updateOwner(new SysUnitOwner(vo.getUnitId(), vo.getOwnerId(), vo.getUnitNum(), "", "F"));

        //调用私有方法创建默认往来账户
        purchaseService.account(vo.getUnitId(), vo.getOwnerId(), Long.valueOf(vo.getOprId()), "TEMPLET_VENDEE_CODE", "C", user);
        //创建相对应得往来账户
        /*purchaseService.account(vo.getOwnerId(),vo.getUnitId(),  Long.valueOf(vo.getOprId()), "TEMPLET_VENDER_CODE","D",user);*/
        //判断是否要创建领域

        SysDomain sysDomain = sysDomainMapper.selectByPrimaryKey(vo.getUnitId());

        SysParameter ve = parameterMapper.findById("VENDER_AUTO_GENERATE_FROM_VENDEE");
        if (sysDomain == null) {
            SysParameter vr = parameterMapper.findById("VENDEE_AS_DOMAIN_BY_DEFAULT");
            if (vr.getParmVal().equals("T")) {
                //生成领域
                sysDomainServiceImpl.insertDomainUp(sysUnitMapper.selectByPrimaryKey(vo.getUnitId()), user);
                if (ve.getParmVal().equals("T")) {
                        //生成供应商
                        venderService.insertVendee(vo, user);
                }
            }
        } else {
            if (ve.getParmVal().equals("T")) {
                    //生成供应商
                    venderService.insertVendee(vo, user);
            }
        }
        if (vdeType == 1) {
            //推送消息
            Supplier su = new Supplier();
            su.setId(YW + vo.getUnitId());
            su.setCode(vo.getUnitNum());
            su.setName(vo.getUnitName());
            su.setUnitId(vo.getOwnerId() + "");
            su.setPhone(vo.getTelNum());
            su.setFax(vo.getFaxNum());
            su.setRegion(vo.getProvince() + vo.getCity() + vo.getDistrict());
            su.setAddress(vo.getAddress());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            su.setCreateDate(sdf.format(new Date()));
            MessageObject msg = new MessageObject("franchisee.add", vo.getUnitId() + "_" + RandomStringUtils.crateUuid(6), su);
            sendSerivce.send("exh.master.up", "master.up", msg);
        }
        if (comType == 1) {
            //推送消息
            MessageObject msg = new MessageObject("unit.add", vo.getUnitId() + "_" + RandomStringUtils.crateUuid(6), new Unit(YW + vo.getUnitId(), "", vo.getUnitCode(), vo.getUnitName(), 1));
            sendSerivce.send("exh.master.up", "master.up", msg);
        }
        return 1;
    }

    /**
     * 修改采购商
     *
     * @param vo
     * @param user
     * @return
     */
    @Override
    public int updateVendee(VendeeVo vo, SysUser user) {

        //将当前操作员放入对象中
        vo.setOprId(user.getPrsnl().getPrsnlId() + "");

        //修改伙伴信息
        purchaseService.updatePartner(new Partner(vo.getUnitId(), vo.getOwnerId(), vo.getPcrLvl(),
                StringUtils.isNotEmpty(vo.getDfltFwdrId()) ? Long.parseLong(vo.getDfltFwdrId()) : null, vo.getDfltDelivMthd(), vo.getManCond(), vo.getPtnrStatus()));

        //先存公司
        Map<String, Object> map = new HashMap<>();
        map.put("vo", vo);

        this.updateCompany(map);

        sysUnitMapper.updateByVd(map);

        //判断编号是否修改
        purchaseService.updateOwner(new SysUnitOwner(vo.getUnitId(), vo.getOwnerId(), vo.getUnitNum(), "", "F"));
        return vendeeMapper.updateVendee(vo);
    }

    /**
     * 删除采购商
     *
     * @param vo
     * @return
     */
    @Override
    public int deleteVendee(VendeeVo vo, SysUser user) {
        //首先逻辑删除采购商
        vo.setVdeStatus("D");
        //只需删除采购商组织类型
        sysUnitClsfMapper.deleteSysUnitClsf(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "VE"));

        //删除供应商属性
        vdeAttrMapper.deleteByPrimaryKey(new VdeAttr(vo.getUnitId(), vo.getOwnerId()));
        //有一个判断采购商在当前是否还存在有效类型的操作
        List<SysUnitClsf> sysList = sysUnitClsfMapper.selectByUnitType(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId()));
        if (sysList.isEmpty()) {
            //查询不符合要求的数据 没有就走删除  有就报错  删除伙伴
            List<Ca> caList = caMapper.selectByDelete(new Ca(vo.getUnitId(), vo.getOwnerId()));
            if (caList.isEmpty()) {
                //逻辑删除
                caMapper.updateByPrimaryKeySelective(new Ca(vo.getUnitId(), vo.getOwnerId(), "D", user.getPrsnl().getPrsnlId()));

            } else {
                throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "无法删除已发帐往来账户！");
            }
            //删除伙伴
            sysUnitClsfMapper.deleteSysUnitClsf(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "PT"));
            partnerMapper.deleteByPrimaryKey(new Partner(vo.getUnitId(), vo.getOwnerId()));

            sysUnitOwnerMapper.updateUnitOwner(new SysUnitOwner(vo.getUnitId(), vo.getOwnerId(), "T"));
        }
//        sysList = sysUnitClsfMapper.selectByVoid(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId()));
//        if (sysList.isEmpty()) {
            //删除属主关系
//        sysUnitOwnerMapper.updateUnitOwner(new SysUnitOwner(vo.getUnitId(), vo.getOwnerId(), "T"));
//        }
        return vendeeMapper.updateVendee(vo);
    }

    /**
     * 采购商中通过组织代码或者组织id查询采购商页面数据
     */
    @Override
    @Transactional(readOnly = true)
    public VendeeVo selectByCode(VendeeVo vo) {
        //用供应商代码和当前组织id查询填充数据
        vo = vendeeMapper.selectByVendee(vo);
        return vo;
    }

    /**
     * 冻结采购商
     *
     * @param vo
     * @return
     */
    @Override
    public int freeze(VendeeVo vo) {
        //0代表默认未冻结  1代表冻结
        Freeze frz = new Freeze();
        frz.setId(YW + vo.getUnitId());
        frz.setFreeze(true);
        //推送消息
        MessageObject msg = new MessageObject("franchisee.freeze", vo.getUnitId() + "_" + RandomStringUtils.crateUuid(6), frz);
        sendSerivce.send("exh.master.up", "master.up", msg);
        //查询出所有门店进行冻结
        List<SysUnitClsf> list = sysUnitClsfMapper.selectByUnitType(new SysUnitClsf(vo.getUnitId(), "SH", "0"));
        if (CollectionUtils.isNotEmpty(list)) {
            //推送门店冻结消息
            list.stream().forEach(s -> sendSerivce.send("exh.master.up", "master.up",new MessageObject("shop.freeze", new Freeze(YW + s.getUnitId() + "", true))));
        }
        //更新冻结状态
        return sysUnitClsfMapper.updateByPrimaryKeySelective(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "VE", "1"));
    }

    /**
     * 解冻采购商
     *
     * @param vo
     * @return
     */
    @Override
    public int defreeze(VendeeVo vo) {
        //0代表默认未冻结  1代表冻结
        Freeze frz = new Freeze();
        frz.setId(YW + vo.getUnitId());
        frz.setFreeze(false);
        //推送消息
        MessageObject msg = new MessageObject("franchisee.freeze", vo.getUnitId() + "_" + RandomStringUtils.crateUuid(6), frz);
        sendSerivce.send("exh.master.up", "master.up", msg);
        //查询出所有门店进行冻结
        List<SysUnitClsf> list = sysUnitClsfMapper.selectByUnitType(new SysUnitClsf(vo.getUnitId(), "SH", "1"));
        if (CollectionUtils.isNotEmpty(list)) {
            //推送门店冻结消息
            list.stream().forEach(s -> sendSerivce.send("exh.master.up", "master.up",new MessageObject("shop.freeze", new Freeze(YW + s.getUnitId() + "", false))));
        }
        //更新冻结状态
        return sysUnitClsfMapper.updateByPrimaryKeySelective(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "VE", "0"));

    }

    /**
     * 查询vendee是否是属主的采购商
     *
     * @author HHe
     * @date 2019/10/19 11:31
     */
    @Override
    public Vendee queryVendeeByIdAndOwner(Vendee pVendee) {
        return vendeeMapper.queryVendeeByIdAndOwner(pVendee);
    }

    /**
     * 采购商购货信息中分页显示采购商
     * @param page
     * @param size
     * @param vo
     * @param sysUser
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<VdrSupplyVo> selectAll(Integer page, Integer size, VdrSupplyVo vo, SysUser sysUser) throws Exception {

        PageInfo<VdrSupplyVo> pageInfo;
        List<VdrSupplyVo> vdrList;
        PageHelper.startPage(page, size);
        vdrList = vendeeMapper.selectByVdeUnit(vo);
        pageInfo = new PageInfo<>(vdrList);
        return pageInfo;
    }

    /**
     * 采购商可操作状态
     *
     * @param sys
     * @return
     */
    @Override
    public List<OperationVo> initButtons(SysUnitClsf sys) {
        List<PurKeyValue> keyValues = buttonCommonService.initButton(parameter);
        SysUnitClsf clsf = sysUnitClsfMapper.selectByPrimaryKey(sys);
        if (clsf == null) {
            throw new ServiceException("403", "采购商异常！");
        }
        return creatButton(keyValues, clsf);
    }

    public List<OperationVo> creatButton(List<PurKeyValue> keyValues, SysUnitClsf clsf) {
        List<OperationVo> list = new ArrayList<>();
        for (PurKeyValue pur : keyValues) {
            if ("freeze".equals(pur.getOptionValue())) {
                if (clsf.getFrzType().equals(0)) {
                    list.add(new OperationVo("冻结", "T", freeze));
                } else {
                    list.add(new OperationVo("冻结", "F", freeze));
                }
            }
            if ("defreeze".equals(pur.getOptionValue())) {
                if (clsf.getFrzType().equals(1)) {
                    list.add(new OperationVo("解冻", "T", defreeze));
                } else {
                    list.add(new OperationVo("解冻", "F", defreeze));
                }
            }
        }
        return list;
    }


    /**
     * 修改或新增公司
     */
    private void updateOrAddVendee(VendeeVo vo) {
        Vendee vd = new Vendee();
        vd.setVendeeId(vo.getUnitId());
        vd.setOwnerId(vo.getOwnerId());
        Vendee v = vendeeMapper.selectByPrimaryKey(vd);
        if (v != null) {
            vendeeMapper.updateVendee(vo);
        } else {
            vendeeMapper.insertVendee(vo);
        }
    }

    /**
     * 修改或新增公司
     */
    private int updateCompany(Map<String, Object> map) {
        int comType = 0;
        VendeeVo vo = (VendeeVo) map.get("vo");

        Company com = companyMapper.selectByPrimaryKey(vo.getUnitId());

        if (com != null) {
            companyMapper.updateByCom(map);
        } else {
            comType = companyMapper.insertByCom(map);
        }
        return comType;
    }


}
