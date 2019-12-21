package com.boyu.erp.platform.usercenter.service.goodsservice.goodsserviceimpl;

import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.goods.ProdClsWithBLOBs;
import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.goods.UnitProdCls;
import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdClsMapper;
import com.boyu.erp.platform.usercenter.mapper.goods.ProductMapper;
import com.boyu.erp.platform.usercenter.mapper.goods.UnitProdClsMapper;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysParameterMapper;
import com.boyu.erp.platform.usercenter.model.purchase.ProdClsModel;
import com.boyu.erp.platform.usercenter.model.system.CommonWindowModel;
import com.boyu.erp.platform.usercenter.service.common.impl.ButtonCommonService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProdClsService;
import com.boyu.erp.platform.usercenter.service.goodsservice.ProductService;
import com.boyu.erp.platform.usercenter.service.system.BrandService;
import com.boyu.erp.platform.usercenter.service.system.SysRefNumDtlSerivce;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.service.table.TableService;
import com.boyu.erp.platform.usercenter.utils.common.FiledUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProductCodeUtils;
import com.boyu.erp.platform.usercenter.utils.refttable.ReftClass;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OperationVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProdClsVo;
import com.boyu.erp.platform.usercenter.vo.goods.ProductVo;
import com.boyu.erp.platform.usercenter.vo.purchase.DtlProdVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: boyu-platform
 * @description: 商品品种服务
 * @author: clf
 * @create: 2019-06-10 10:56
 */
@Slf4j
@Service
@Transactional
public class ProdClsServerImpl implements ProdClsService {
    //商品品种模块按钮参数
    final String parameter = "prodClsButton";
    //审核路径
    final String URL1 = "/product/cls/auditTypeProdCls";
    //反审路径
    final String URL2 = "/product/cls/notAuditTypeProdCls";
    //商品禁用
    final String URL3 = "/product/cls/Disable";
    //商品恢复
    final String URL4 = "/product/cls/Recovery";

    final String URL5 = "/product/cls/stkAdopte";
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCodeUtils productCodeUtils;
    @Autowired
    private TableService tableService;
    @Autowired
    private UsersService usersService;

    @Autowired
    private ProdClsMapper prodClsMapper;
    @Autowired
    private UnitProdClsMapper unitProdClsMapper;
    @Autowired
    private SysRefNumDtlSerivce refNumDtlSerivce;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SysParameterMapper parameterMapper;
    @Autowired
    private FiledUtils filedUtils;
    @Autowired
    private ButtonCommonService buttonCommonService;
    @Autowired
    private ReftClass reftClass;


    @Override
    public PageInfo<ProdClsVo> getProdClsList(Integer page, Integer size, ProdClsModel prodClsWithBLOBs, SysUser user) throws Exception {

        PageInfo<ProdClsVo> pageInfo = null;

        /**
         * 系统管理员默认查询所有
         * */
        if (usersService.getAdmin(user) != null && usersService.getAdmin(user).getOprId() == -1L) {
            PageHelper.startPage(page, size);
            List<ProdClsVo> resultList = prodClsMapper.selectList(prodClsWithBLOBs);
            pageInfo = new PageInfo<>(resultList);
            pageInfo.setList(setList(resultList));
            return pageInfo;
        }
        if (prodClsWithBLOBs.getUnitId() == null) {
            //没有选择组织信息默认取当前组织
            prodClsWithBLOBs.setUnitId(user.getOwnerId());
        }
        //组织管理员或用户
        PageHelper.startPage(page, size);
        List<ProdClsVo> resultList = prodClsMapper.selectGetList(prodClsWithBLOBs);
        pageInfo = new PageInfo<>(resultList);
        pageInfo.setList(setList(resultList));
        return pageInfo;
    }

    /**
     * 根据id查询品种
     *
     * @author HHe
     * @date 2019/8/27 14:05
     */
    @Override
    public ProdCls queryProdClsById(ProdCls prodCls2Id) {
        return prodClsMapper.selectByKey(prodCls2Id.getProdClsId());
    }

    /**
     * 根据品牌Id集合查询品种Id集合
     *
     * @author HHe
     * @date 2019/9/19 11:21
     */
    @Override
    public List<Long> queryProdClsIdByBrandIdList(List<Long> brandIdList) {
        return prodClsMapper.queryProdClsIdByBrandIdList(brandIdList);
    }

    /**
     * 根据分类Id集合查询品种Id集合
     *
     * @author HHe
     * @date 2019/9/19 15:15
     */
    @Override
    public List<Long> queryProdClsIdByProdCatIdList(List<String> prodCatIdList) {
        return prodClsMapper.queryProdClsIdByProdCatIdList(prodCatIdList);
    }

    /**
     * 根据品牌Id集合和分类Id集合查询品种Id集合
     *
     * @author HHe
     * @date 2019/9/19 15:54
     */
    @Override
    public List<Long> queryProdClsIdByProdCatIdListAndBrandIdList(List<String> prodCatIdList, List<Long> brandIdList) {
        return prodClsMapper.queryProdClsIdByProdCatIdListAndBrandIdList(prodCatIdList, brandIdList);
    }

    /**
     * 功能描述: 查询当前组织商品
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/26 19:24
     */
    @Override
    public PageInfo<ProdClsVo> getUnitProdClsList(Integer page, Integer size, ProdClsModel model, SysUser user) throws Exception {
        /**
         * 组织管理员默认查询当前组织所有商品
         * */
        if (usersService.getIsAdmin(user) || usersService.getIsLeader(user)) {
            log.info("管理员查询组织商品");
            model.setIsAdmin("");
            return getThisPageinfo(page, size, model);
        } else {
            //普通用户需要对应品牌权限查看商品
            model.setIsAdmin(CommonFainl.FALSE);
            //设置过滤集合为组织所有商品
            model.setShared("");
            //组织共享商品集合
            List<ProdClsModel> modes = new ArrayList(new HashSet(prodClsMapper.selectShared(model.getUnitId())));
            List<ProdClsModel> maxList = getUserProdClsMode(user, modes);
            if (CollectionUtils.isNotEmpty(maxList)) {
                if (maxList.size() == modes.size()) {
                    log.info("普通用户查询组织商品且拥有组织所有商品品牌权限");
                    //长度相等 直接查询共享组织商品信息不用遍历
                    model.setIsAdmin(null);
                    return getThisPageinfo(page, size, model);
                }
                log.info("普通用户查询当前组织商品拥有该组织部分商品品牌权限");
                model.setProdClsModels(maxList);
                return getThisPageinfo(page, size, model);
            }
        }
        return null;
    }

    private PageInfo<ProdClsVo> getThisPageinfo(Integer page, Integer size, ProdClsModel model) throws Exception {
        PageHelper.startPage(page, size);
        List<ProdClsVo> resultList = prodClsMapper.selectUnitProdClsAll(model);
        PageInfo<ProdClsVo> pageInfo = new PageInfo<>(resultList);
        pageInfo.setList(setList(resultList));
        return pageInfo;
    }

    /**
     * 功能描述: 查询其他组织共享商品需要用户有相应的品牌权限
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/26 19:35
     */
    @Override
    public PageInfo<ProdClsVo> getOtherUnitProdClsList(Integer page, Integer size, ProdClsModel model, SysUser sysUser) throws Exception {
        if (usersService.getIsAdmin(sysUser)) {
            //系统管理员直接拥有所有商品
            log.info("系统管理员查询其他组织共享商品");
            model.setIsAdmin(null);
            return getPageInfo(page, size, model);
        } else {
            //设置查询为非管理员
            model.setIsAdmin(CommonFainl.FALSE);
            //设置查询组织共享
            model.setShared(CommonFainl.TRUE);
            //组织共享商品集合
            List<ProdClsModel> modes = new ArrayList(new HashSet(prodClsMapper.selectShared(model.getUnitId())));
            List<ProdClsModel> maxList = getUserProdClsMode(sysUser, modes);
            if (CollectionUtils.isNotEmpty(maxList)) {
                if (maxList.size() == modes.size()) {
                    log.info("普通用户查询其他组织共享商品且拥有该组织所有共享商品品牌权限");
                    //长度相等 直接查询共享组织商品信息不用遍历
                    model.setIsAdmin(null);
                    return getPageInfo(page, size, model);
                }
                log.info("普通用户查询其他组织共享商品拥有该组织部分共享商品品牌权限");
                model.setProdClsModels(maxList);
                return getPageInfo(page, size, model);
            }
        }
        return null;
    }


    /**
     * 功能描述: 查询商品品种能否审核
     *
     * @param model
     * @return true 是 false 否
     * @auther: CLF
     * @date: 2019/9/27 15:54
     */
    @Override
    public boolean isAuditType(ProdClsModel model) {
        ProdCls cls = prodClsMapper.selectByKey(model.getProdClsId());
        if (cls == null) {
            return false;
        }
        if ("se".equalsIgnoreCase(cls.getAuditType()) && CommonFainl.USER_STATUS.equalsIgnoreCase(cls.getProdStatus())) {
            List<Product> list = productMapper.selectAuditType(model.getProdClsId());
            if (CollectionUtils.isNotEmpty(list)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 功能描述: 查询商品品种能否反审
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/27 16:45
     */
    @Override
    public boolean getNotAuditType(ProdClsModel m) {
        ProdCls cls = prodClsMapper.selectByKey(m.getProdClsId());
        if (cls == null) {
            return false;
        }
        if ("am".equalsIgnoreCase(cls.getAuditType())) {
            return true;
        }

        return false;
    }

    /**
     * 功能描述: 单纯修改商品品种表
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/27 20:48
     */
    @Override
    public int updateProductCls(ProdClsWithBLOBs cls, SysUser user) {
        cls.setUpdTime(new Date());
        cls.setUpdateBy(user.getUserId());
        return prodClsMapper.updateByPrimaryKeySelective(cls);
    }


    /**
     * 功能描述: 通过用户品牌权限和查询组织商品集合判断用户在查询组织能查询到些商品
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/27 10:23
     */
    private List<ProdClsModel> getUserProdClsMode(SysUser sysUser, List<ProdClsModel> modes) {
        //用户品牌权限获取商品品种集合
        List<ProdClsModel> list = brandService.userBrandOrProdCls(sysUser);
        //将用户通过品牌权限拥有商品集合list转set后转为list(去重)
        List<ProdClsModel> prodClsList = new ArrayList(new HashSet(list));
        if (CollectionUtils.isNotEmpty(modes) && CollectionUtils.isNotEmpty(list)) {
            //筛选用户能在查询组织查询哪些商品
            List<ProdClsModel> maxList = prodClsList.stream().filter(s -> modes.contains(s)).collect(Collectors.toList());
            return maxList;
        }
        return null;
    }


    public PageInfo<ProdClsVo> getPageInfo(Integer page, Integer size, ProdClsModel model) throws Exception {
        PageHelper.startPage(page, size);
        List<ProdClsVo> resultList = prodClsMapper.selectUnitProdClsOther(model);
        PageInfo<ProdClsVo> pageInfo = new PageInfo<>(resultList);
        pageInfo.setList(setList(resultList));
        return pageInfo;
    }


    /**
     * @program: ${PROJECT_NAME}
     * @description: 增加商品品种
     * 1.增加商品品种表
     * 2.增加组织商品品种表
     * 3. 修改编号表
     * @author: CLF
     */
    @Override
    public int instrProdCls(ProdClsVo prodClsVo, SysUser user) throws Exception {
        /**
         * 必填项
         * */
        String bit = parameterMapper.findById("PRODUCT_MANDATORY_FIELDS").getParmVal();
        filedUtils.getFlie(bit, (Object) prodClsVo);


        ProdClsWithBLOBs prodCls = new ProdClsWithBLOBs();
        Long max = refNumDtlSerivce.creatId("prod_cls_id");

        //PRODUCT_CLASS_CODING_RULE
        BeanUtils.copyProperties(prodClsVo, prodCls);
        prodCls.setSpecId(prodClsVo.getSpecId().intValue());
        prodCls.setIsAudit(0);
        prodCls.setCtrlUnitId(prodClsVo.getUnitId());
        setFile(user, prodCls, "add");
        prodCls.setProdClsId(max);
        prodClsVo.setProdClsId(max);


        /**
         * 1.增加商品品种表
         * */
        int a = prodClsMapper.insertSelective(prodCls);
        /**
         * 2.增加组织商品品种表
         * */
        UnitProdCls unitProdCls = new UnitProdCls();
        BeanUtils.copyProperties(prodClsVo, unitProdCls);
        unitProdCls.setProdClsId(prodCls.getProdClsId());
        a += unitProdClsMapper.insertSelectiveUnitProdCls(unitProdCls);
        /**
         * 3. 修改编号表
         * */
        SysRefNumDtl sysRefNum = new SysRefNumDtl();
        sysRefNum.setRefNumId("prod_cls_id");
        sysRefNum.setLastNum(max);
        a += refNumDtlSerivce.updateRefNumDtl(sysRefNum);


        /**
         * 新增 ： 增加商品明细
         *
         * */
        List<ProductVo> setList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(prodClsVo.getAddProduct())) {
            for (ProductVo vs : prodClsVo.getAddProduct()) {
                System.out.println("增加的商品品种Id: " + prodCls.getProdClsId());
                vs.setProdClsId(prodCls.getProdClsId());
                setList.add(vs);
            }
            List<Product> list = productCodeUtils.setProduct(setList, user);
            list.forEach(g -> productService.instrProduct(g, user));
        }

        return a;
    }

    @Override
    public int updateByProdCls(ProdCls cls) {
        ProdClsWithBLOBs record = new ProdClsWithBLOBs();
        BeanUtils.copyProperties(cls, record);
        return prodClsMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * @program:
     * @description: 修改商品品种
     * 1.修改商品品种表
     * 2.修改组织商品品种表
     * @author: CLF
     */
    @Override
    public int updateProdCls(ProdClsVo prodClsVo, SysUser user) {

        UnitProdCls unitProdCls = new UnitProdCls();
        BeanUtils.copyProperties(prodClsVo, unitProdCls);
        unitProdCls.setUnitId(user.getOwnerId());
        ProdClsWithBLOBs prodClsWithBLOBs = new ProdClsWithBLOBs();
        BeanUtils.copyProperties(prodClsVo, prodClsWithBLOBs);
        unitProdClsMapper.updateUnitProdClsSelective(unitProdCls);
        setFile(user, prodClsWithBLOBs, "update");
        return prodClsMapper.updateByPrimaryKeySelective(prodClsWithBLOBs);
    }

    /**
     * @program: ${PROJECT_NAME}
     * @description: 删除商品品种
     * 1.修改商品品种表（打标删除)
     * @author: CLF
     */
    @Override
    public int deleteprodCls(ProdClsVo prodClsVo, SysUser us) {
        ProdClsWithBLOBs p = new ProdClsWithBLOBs();
        p.setProdClsId(prodClsVo.getProdClsId());
        //设置删除为状态
        p.setProdStatus(CommonFainl.FILAN_STATUS);
        setFile(us, p, "delete");
        return prodClsMapper.updateByPrimaryKeySelective(p);
    }

    @Override
    public ProdCls findByProdCls(ProdCls p) {
        return prodClsMapper.selectByKey(p.getProdClsId());
    }


    public void setFile(SysUser user, ProdClsWithBLOBs prodCls, String str) {
        if (str.equals("add")) {
            prodCls.setIsDel(CommonFainl.BTYPESTATUS);
            prodCls.setCreateBy(user.getUserId());
            prodCls.setCreateTime(new Date());
        }
        if (str.equals("update")) {
            prodCls.setUpdTime(new Date());
            prodCls.setUpdateBy(user.getUserId());
        }
        if (str.equals("delete")) {
            prodCls.setUpdTime(new Date());
            prodCls.setUpdateBy(user.getUserId());
            prodCls.setIsDel(CommonFainl.FAILSTATUS);
        }
    }


    /**
     * 功能描述:  将为null 变为 ""
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/17 16:30
     */
    private List<ProdClsVo> setList(List<ProdClsVo> resultList) throws Exception {
        List<ProdClsVo> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(resultList))
            for (ProdClsVo vo : resultList) {
                reftClass.setObject(vo);
                list.add(vo);
            }
        return list;
    }


    /**
     * 功能描述: 商品通用弹窗
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/23 17:18
     */
    @Override
    public Object selectObject(CommonWindowModel model, SysUser user) {
        if ("prod_cls".equalsIgnoreCase(model.getSelectType())) {
            return prodClsMapper.selectObject(model);
        }
        return null;
    }

    /**
     * 功能描述: 初始化商品品种按钮
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/29 15:16
     */
    @Override
    public List<OperationVo> initButtons(ProdCls model) throws ServiceException {
        //  List<PurKeyValue> keyValues = buttonCommonService.initButton(parameter);
        ProdCls cls = prodClsMapper.selectByKey(model.getProdClsId());
        if (cls == null) {
            throw new ServiceException("403", "检查商品品种Id是否在数据库存在");
        }
        return creatButton(cls);
    }

    /**
     * 根据品种Id集合查询品种集合
     *
     * @author HHe
     * @date 2019/10/7 16:23
     */
    @Override
    public List<ProdCls> queryProdClsListByIds(Set<Long> prodClsIds) {
        return prodClsMapper.queryProdClsListByIds(prodClsIds);
    }

    /**
     * 功能描述: 查询商品品种能否禁用库存管理
     *
     * @param prodClsId
     * @return true (能)  false(不能)
     * @auther: CLF
     * @date: 2019/11/12 16:41
     */
    @Override
    public Boolean selectProdClsStkAdopte(Long prodClsId) {
        if(CollectionUtils.isEmpty(prodClsMapper.selectProdClsStkAdopte(prodClsId))){
            return true;
        }
        return false;
    }

    private List<OperationVo> creatButton(ProdCls cls) {
        List<OperationVo> list = new ArrayList<>();
        if ("am".equals(cls.getAuditType())) {
            OperationVo vo = new OperationVo("反审", URL2);
            list.add(vo);
            return list;
        }
        if ("se".equals(cls.getAuditType()) && "A".equalsIgnoreCase(cls.getProdStatus())) {
            OperationVo vo = new OperationVo("审核", URL1);
            OperationVo vo2 = new OperationVo("禁用", URL3);
            if (cls.getStkAdopted().equalsIgnoreCase(CommonFainl.FALSE)) {
                OperationVo vo4 = new OperationVo("启用库存管理", URL5);
                list.add(vo4);
            }
            if (cls.getStkAdopted().equalsIgnoreCase(CommonFainl.TRUE)) {
                OperationVo vo3 = new OperationVo("禁用库存管理", URL5);
                list.add(vo3);
            }
            list.add(vo);
            list.add(vo2);

            return list;
        }
        if ("se".equals(cls.getAuditType()) && "I".equalsIgnoreCase(cls.getProdStatus())) {
            OperationVo vo2 = new OperationVo("禁用恢复", URL4);
            list.add(vo2);
            return list;
        }
        return list;
    }


}
