package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.basic.*;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.mq.ven.Supplier;
import com.boyu.erp.platform.usercenter.entity.mq.ven.Unit;
import com.boyu.erp.platform.usercenter.entity.purchase.VdrAttr;
import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.basic.*;
import com.boyu.erp.platform.usercenter.mapper.purchase.VdrAttrMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.*;
import com.boyu.erp.platform.usercenter.service.base.RabbitTemplateSerivce;
import com.boyu.erp.platform.usercenter.service.base.SendSerivce;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.system.VendeeService;
import com.boyu.erp.platform.usercenter.service.system.VenderService;
import com.boyu.erp.platform.usercenter.utils.RandomStringUtils;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.FiledUtils;
import com.boyu.erp.platform.usercenter.utils.refttable.ReftClass;
import com.boyu.erp.platform.usercenter.vo.purchase.VdrSupplyVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VenderVo;
import com.boyu.erp.platform.usercenter.vo.sales.VendeeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Classname venderServiceimpl
 * @Description TODO
 * @Date 2019/6/26 16:22
 * @Created wz
 */
@Slf4j
@Service
@Transactional
public class VenderServiceimpl implements VenderService {

    public static final String YW = "ER_";

    private final String tableName = "VENDER";

    @Autowired
    private VenderMapper venderMapper;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private VendeeMapper vendeeMapper;
    @Autowired
    private PartnerMapper partnerMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private SysUnitMapper sysUnitMapper;
    @Autowired
    private SysUnitOwnerMapper sysUnitOwnerMapper;
    @Autowired
    private CaMapper caMapper;
    @Autowired
    private SysDomainMapper sysDomainMapper;
    @Autowired
    private SysDomainServiceImpl sysDomainServiceImpl;
    @Autowired
    private SysUnitClsfMapper sysUnitClsfMapper;
    @Autowired
    private VdrAttrMapper vdrAttrMapper;
    @Autowired
    private VendeeService vendeeService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private FiledUtils filedUtils;
    @Autowired
    private ReftClass reftClass;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private SendSerivce sendSerivce;

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<VenderVo> selectAll(Integer page, Integer size, VenderVo vo, SysUser user) throws Exception {

        PageInfo<VenderVo> pageInfo;
        //系统管理员
        if (false) {
            PageHelper.startPage(page, size);
            List<VenderVo> venderList = venderMapper.selectALL(vo);
            pageInfo = new PageInfo<>(venderList);
            pageInfo.setList(setList(venderList));

            return pageInfo;
        }
        //组织
        PageHelper.startPage(page, size);
        List<VenderVo> venderList = venderMapper.selectByUnit(vo);
        pageInfo = new PageInfo<>(venderList);
        pageInfo.setList(setList(venderList));
        return pageInfo;
    }

    /**
     * 给空值赋予初始值
     *
     * @param venderList
     * @return
     * @throws Exception
     */
    private List<VenderVo> setList(List<VenderVo> venderList) throws Exception {
        List<VenderVo> list = new ArrayList<>();
        for (VenderVo vo : venderList) {
            reftClass.setObject(vo);
            list.add(vo);
        }
        return list;
    }

    /**
     * 添加供应商
     *
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public int insertVender(VenderVo vo, SysUser user) throws Exception {

        //将当前操作员放入对象中
        vo.setOprId(user.getPrsnl().getPrsnlId() + "");
        if (StringUtils.NullEmpty(vo.getCtrlUnitId())) {
            vo.setCtrlUnitId(vo.getOwnerId() + "");
        }

        String bit = parameterMapper.findById("VENDER_CREAT_TABLE_FIELDS").getParmVal();

        filedUtils.getFlie(bit, (Object) vo);

        //当传过来的unitId是空时,就说明它是一个全新的数据，就走全新的创建流程
        //当传过来的unitId是有值的时候，去判断是否已经是供应商，是的话走修改流程，不是的话相应的去创建或修改数据
        if (vo.getUnitId() == null) {
            vo.setUnitId(purchaseService.addUnitId("UNIT_ID", user));
        } else {
            // 如果是走全新的就不用判断
            SysParameter par = parameterMapper.findById("PSA_SEPARATED_ALLOWED");
            if (par.getParmVal().equals("F")) {
                List<Vendee> vendeeList = vendeeMapper.findByVenderVo(vo);
                if (vendeeList != null && vendeeList.size() > 0) {
                    throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "当前组织已是采购商！");
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("vo", vo);

        //先存公司  值为1时推送消息
        int comType = this.updateCompany(map);

        //修改伙伴信息
        purchaseService.updatePartner(new Partner(vo.getUnitId(), vo.getOwnerId(), vo.getPcrLvl(),
                (StringUtils.isNotEmpty(vo.getDfltFwdrId()) ? Long.parseLong(vo.getDfltFwdrId()) : null), vo.getDfltDelivMthd(), vo.getManCond(), vo.getPtnrStatus()));

        //存供应商与组织
        this.updateOrAddVender(vo);

        if (sysUnitMapper.selectByPrimaryKey(vo.getUnitId()) == null) {
            String hier = "";
            if (!StringUtils.NullEmpty(user.getUnit().getUnitHierarchy())) {
                hier = user.getUnit().getUnitHierarchy()+ "|" ;
            }
            vo.setUnitHierarchy(hier + vo.getUnitName());
            vo.setUnitType("2");
            map.put("vo", vo);
            sysUnitMapper.insertByVd(map);
        } else {
            map.put("vo", vo);
            sysUnitMapper.updateByVd(map);
        }

        //创建关联数据
        int vdrType = 0;
        if (sysUnitClsfMapper.selectByPrimaryKey(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "VD")) == null) {
            vdrType = sysUnitClsfMapper.insertSysUnitClsf(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "VD"));
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
        purchaseService.account(vo.getUnitId(), vo.getOwnerId(), Long.valueOf(vo.getOprId()), "TEMPLET_VENDER_CODE", "C", user);
        //创建相对应得往来账户
        /*purchaseService.account(vo.getOwnerId(), vo.getUnitId(), Long.valueOf(vo.getOprId()), "TEMPLET_VENDEE_CODE", "C", user);*/
        //判断是否要创建领域
        SysDomain sysDomain = sysDomainMapper.selectByPrimaryKey(vo.getUnitId());
        //是否自动生成采购商
        SysParameter ve = parameterMapper.findById("VENDEE_AUTO_GENERATE_FROM_VENDER");
        if (sysDomain == null) {
            SysParameter vr = parameterMapper.findById("VENDER_AS_DOMAIN_BY_DEFAULT");
            if (vr.getParmVal().equals("T")) {
                //生成领域
                sysDomainServiceImpl.insertDomainUp(sysUnitMapper.selectByPrimaryKey(vo.getUnitId()), user);

                if (ve.getParmVal().equals("T")) {
                    //生成采购商
                    vendeeService.insertVender(vo, user);
                }
            }
        } else {
            if (ve.getParmVal().equals("T")) {
                //生成采购商
                vendeeService.insertVender(vo, user);
            }
        }
        MessageObject msg = null;
        if (vdrType == 1) {
            //推送消息
            msg = new MessageObject("unit.add", vo.getUnitId() + "_" + RandomStringUtils.crateUuid(6), new Unit(YW + vo.getUnitId(), "", vo.getUnitCode(), vo.getUnitName(), 4));
            sendSerivce.send("exh.master.up", "master.up", msg);
        }
        if (comType == 1) {
            //推送消息
            msg = new MessageObject("unit.add", vo.getUnitId() + "_" + RandomStringUtils.crateUuid(6), new Unit(YW + vo.getUnitId(), "", vo.getUnitCode(), vo.getUnitName(), 1));
            sendSerivce.send("exh.master.up", "master.up", msg);
        }
        return 1;
    }

    /**
     * 修改供应商
     *
     * @param vo
     * @return
     * @throws ServiceException
     */
    public int updateVender(VenderVo vo, SysUser user) throws ServiceException {

        //将当前操作员放入对象中
        vo.setOprId(user.getPrsnl().getPrsnlId() + "");

        //修改伙伴信息
        purchaseService.updatePartner(new Partner(vo.getUnitId(), vo.getOwnerId(), vo.getPcrLvl(),
                StringUtils.isNotEmpty(vo.getDfltFwdrId()) ? Long.parseLong(vo.getDfltFwdrId()) : null, vo.getDfltDelivMthd(), vo.getManCond(), vo.getPtnrStatus()));

        //修改公司,供应商,组织信息
        Map<String, Object> map = new HashMap<>();
        map.put("vo", vo);

        this.updateCompany(map);

        sysUnitMapper.updateByVd(map);

        //判断编号是否修改
        purchaseService.updateOwner(new SysUnitOwner(vo.getUnitId(), vo.getOwnerId(), vo.getUnitNum(), "", "F"));
        return venderMapper.updateByVender(vo);
    }

    /**
     * 删除供应商
     *
     * @param vo
     * @return
     * @throws ServiceException
     */
    public int deleteVender(VenderVo vo, SysUser user) throws ServiceException {
        //首先逻辑删除供应商
        vo.setVdrStatus("D");
        //只需删除供应商组织类型
        sysUnitClsfMapper.deleteSysUnitClsf(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "VD"));
        //删除供应商属性
        vdrAttrMapper.deleteByPrimaryKey(new VdrAttr(vo.getUnitId(), vo.getOwnerId()));
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
            sysUnitClsfMapper.deleteSysUnitClsf(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId(), "PT"));
            //删除伙伴
            partnerMapper.deleteByPrimaryKey(new Partner(vo.getUnitId(), vo.getOwnerId()));
            sysUnitOwnerMapper.updateUnitOwner(new SysUnitOwner(vo.getUnitId(), vo.getOwnerId(), "T"));
        }
//        sysList = sysUnitClsfMapper.selectByVoid(new SysUnitClsf(vo.getUnitId(), vo.getOwnerId()));
//        if (sysList.isEmpty()) {
        //删除属主关系
//           sysUnitOwnerMapper.updateUnitOwner(new SysUnitOwner(vo.getUnitId(), vo.getOwnerId(), "T"));
//        }
        return venderMapper.updateByVender(vo);
    }

    /**
     * 修改或新增公司
     */
    private int updateCompany(Map<String, Object> map) {
        int comType = 0;//默认是存在
        VenderVo vo = (VenderVo) map.get("vo");

        Company com = companyMapper.selectByPrimaryKey(vo.getUnitId());
        if (com != null) {
            companyMapper.updateByCom(map);
        } else {
            comType = companyMapper.insertByCom(map);
        }
        return comType;
    }

    /**
     * 修改或新增供应商
     */
    private void updateOrAddVender(VenderVo vo) {
        Vender vd = new Vender();
        vd.setVenderId(vo.getUnitId());
        vd.setOwnerId(vo.getOwnerId());
        Vender v = venderMapper.selectByPrimaryKey(vd);
        if (v != null) {
            venderMapper.updateByVender(vo);
        } else {
            venderMapper.insertVender(vo);
        }
    }


    private void updateVdrAttrUp(VdrAttr vdr) {
        VdrAttr vdrAttr = vdrAttrMapper.selectByPrimaryKey(vdr);
        if (vdrAttr == null) {
            vdrAttrMapper.insert(vdr);
        } else {
            vdrAttrMapper.updateByPrimaryKeySelective(vdr);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public VenderVo selectByCode(VenderVo vo) {
        //用供应商代码和当前组织id查询填充数据
        vo = venderMapper.selectByVender(vo);
        return vo;
    }

    /**
     * 供应商供货信息中分页显示供应商
     * @param page
     * @param size
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public PageInfo<VdrSupplyVo> selectAll(Integer page, Integer size, VdrSupplyVo vo, SysUser user) throws Exception {

        PageInfo<VdrSupplyVo> pageInfo;
        List<VdrSupplyVo> vdrList;
        //系统管理员
        if (false) {
            PageHelper.startPage(page, size);
            vdrList = venderMapper.selectVdrALL(vo);
            pageInfo = new PageInfo<>(vdrList);
            return pageInfo;
        }
        //组织
        PageHelper.startPage(page, size);
        vdrList = venderMapper.selectByVdrUnit(vo);
        pageInfo = new PageInfo<>(vdrList);
        return pageInfo;
    }

    /**
     * 采购商中添加供应商
     *
     * @param vo
     * @return
     */
    @Override
    public void insertVendee(VendeeVo vo, SysUser user) {
        Vender vd = venderMapper.selectByPrimaryKey(new Vender(vo.getOwnerId(), vo.getUnitId()));
        if (vd == null) {
            //添加采购商数据
            venderMapper.insertByVendee(vo);
        } else {
            if (!vd.getVdrStatus().equals("A")) {
                vd.setVdrStatus("A");
                vd.setUpdTime(new Date());
                venderMapper.updateByPrimaryKeySelective(vd);
            }
        }
        SysUnit unit = sysUnitMapper.selectByPrimaryKey(vo.getOwnerId());

        //判断组织是否存在 存在就更新  不存在就新增
        purchaseService.updateOwner(new SysUnitOwner(vo.getOwnerId(), vo.getUnitId(), unit.getUnitCode(), "", "F"));

        purchaseService.updatePartner(new Partner(vo.getOwnerId(), vo.getUnitId(), vo.getPcrLvl(),
                StringUtils.isNotEmpty(vo.getDfltFwdrId()) ? Long.parseLong(vo.getDfltFwdrId()) : null, vo.getDfltDelivMthd(), vo.getManCond(), vo.getPtnrStatus()));
        //创建关联数据
        int vdrType = 0;
        if (sysUnitClsfMapper.selectByPrimaryKey(new SysUnitClsf(vo.getOwnerId(), vo.getUnitId(), "VD")) == null) {
            vdrType = sysUnitClsfMapper.insertSysUnitClsf(new SysUnitClsf(vo.getOwnerId(), vo.getUnitId(), "VD"));
        }
        if (sysUnitClsfMapper.selectByPrimaryKey(new SysUnitClsf(vo.getOwnerId(), vo.getUnitId(), "PT")) == null) {
            sysUnitClsfMapper.insertSysUnitClsf(new SysUnitClsf(vo.getOwnerId(), vo.getUnitId(), "PT"));
        }
        if (sysUnitClsfMapper.selectByPrimaryKey(new SysUnitClsf(vo.getUnitId(), vo.getUnitId(), "VW")) == null) {
            sysUnitClsfMapper.insertSysUnitClsf(new SysUnitClsf(vo.getUnitId(), vo.getUnitId(), "VW"));
        }

        //调用私有方法创建默认往来账户
        purchaseService.accountBy(vo.getUnitId(), vo.getOwnerId(), Long.valueOf(vo.getOprId()), "TEMPLET_VENDER_CODE", "D", user);

        //推送消息
        if (vdrType == 1) {
            SysUnit ownerUnit = sysUnitMapper.selectByPrimaryKey(vo.getOwnerId());
            //推送消息
            MessageObject msg = new MessageObject("unit.add", ownerUnit.getUnitId() + "_" + RandomStringUtils.crateUuid(6), new Unit(YW + ownerUnit.getUnitId(), "", ownerUnit.getUnitCode(), ownerUnit.getUnitName(), 4));
            sendSerivce.send("exh.master.up", "master.up", msg);
        }
//        templateSerivce.send(new MessageObject("unit.add", new Unit(vo.getUnitId() + "", "", vo.getUnitCode(), vo.getUnitName(), 4)));

    }

}
