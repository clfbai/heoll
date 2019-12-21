package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.StbDtlMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.StbDtlService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.utils.warehouse.StbUtil;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbDtlVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 调拨单明细接口实现
 * @author: ck
 * @create: 2019-06-28 15:36
 */
@Slf4j
@Service
@Transactional
public class StbDtlServiceImpl implements StbDtlService {

    @Autowired
    private StbDtlMapper stbDtlMapper;
    @Autowired
    private StbUtil stbUtil;
    @Autowired
    private DelivUtil delivUtil;

    /**
     * 计量单位
     */
    private static final String CODE_UOM = "UOM";
    /**
     * 版型
     */
    private static final String CODE_EDITION = "EDITION";

    /**
     * 获取库存单明细列表
     *
     * @param page
     * @param size
     * @param stbDtl
     * @return
     * @throws ServiceException
     */
    @Override
    public PageInfo<StbDtlVo> getStbDtlList(Integer page, Integer size, StbDtl stbDtl) throws ServiceException {
        PageHelper.startPage(page, size);
        List<StbDtlVo> list = stbDtlMapper.getStbDtlList(stbDtl);
        PageInfo<StbDtlVo> pageInfo = new PageInfo<StbDtlVo>(list);
        return pageInfo;
    }

    /**
     * 插入库存单明细
     *
     * @param stbDtl
     * @return
     * @throws ServiceException
     */
    @Override
    public int insert(StbDtl stbDtl) throws ServiceException {
        int i = stbDtlMapper.insert(stbDtl);
        Stb stb = new Stb();
        stb.setUnitId(stbDtl.getUnitId());
        stb.setStbNum(stbDtl.getStbNum());
        stbUtil.reCalStbInfo(stb, "D");
        return i;
    }

    /**
     * 更新库存单明细
     *
     * @param stbDtl
     * @return
     * @throws ServiceException
     */
    @Override
    public int update(StbDtl stbDtl) throws ServiceException {
        int i = stbDtlMapper.updateStbDtl(stbDtl);
        Stb stb = new Stb();
        stb.setUnitId(stbDtl.getUnitId());
        stb.setStbNum(stbDtl.getStbNum());
        stbUtil.reCalStbInfo(stb, "D");
        return i;
    }

    /**
     * 删除单条明细
     *
     * @param stbDtl
     * @return
     * @throws ServiceException
     */
    @Override
    public int delete(StbDtl stbDtl) throws ServiceException {
        int i = stbDtlMapper.deleteByPrimaryKey(stbDtl);
        Stb stb = new Stb();
        stb.setUnitId(stbDtl.getUnitId());
        stb.setStbNum(stbDtl.getStbNum());
        stbUtil.reCalStbInfo(stb, "D");
        return i;
    }

    /**
     * 根据库存单号删除所有明细(删除库存单操作)
     *
     * @param stbDtl
     * @return
     * @throws ServiceException
     */
    @Override
    public int deleteAll(StbDtl stbDtl) throws ServiceException {
        return stbDtlMapper.deleteByPrimaryKey(stbDtl);
    }

    /**
     * 添加出库库存单
     *
     * @param stbDtlVos
     * @param alwaysNum
     * @return
     */
    @Override
    public int insertStbDtlVO(List<StbDtlVo> stbDtlVos, String alwaysNum, SysUser sysUser) {
        return stbDtlMapper.insertStbDtlVO(stbDtlVos, alwaysNum, sysUser.getDomain().getUnitId());
    }

    /**
     * 出库任务生成出库单，添加库存明细。
     *
     * @author HHe
     */
    @Override
    public int insrtStbDtl(List<StbDtlVo> stbDtlVos, String stbNum, SysUser sysUser) {
        return stbDtlMapper.insertStbDtl2CreateGdn(stbDtlVos, stbNum, sysUser.getDomain().getUnitId());
    }

    /**
     * 根据子库存单集合查询明细集合
     *
     * @author HHe
     */
    @Override
    public List<StbDtlVo> queryTotStbDtlsBySonStbNums(List<String> sonStbNums, Long ownerId) throws InvocationTargetException, NoSuchMethodException, IntrospectionException, NoSuchFieldException, IllegalAccessException {
        return packList(stbDtlMapper.queryTotStbDtlsBySonStbNums(sonStbNums, ownerId));
    }

    /**
     * 根据库存单编号查询明细集合
     *
     * @author HHe
     */
    @Override
    public List<StbDtlVo> queryStbDtlsByStbNums(String stbNum, Long ownerId) throws InvocationTargetException, NoSuchMethodException, IntrospectionException, NoSuchFieldException, IllegalAccessException {
        return packList(stbDtlMapper.queryStbDtlsByStbNums(stbNum, ownerId));
    }

    /**
     * 封装集合
     *
     * @author HHe
     * @date 2019/11/28 10:43
     */
    private List<StbDtlVo> packList(List<StbDtlVo> list) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("uom", "UOM");
        codeMap.put("edition", "EDITION");
        list = delivUtil.loadCPByCodeDtl(codeMap, list);
        return list;
    }


    /**
     * 根据用户输入数量计算库存明细数据（PLANA:用户不在前端控制台修改数据的情况下）
     *
     * @author HHe
     * @date 2019/8/28 20:22
     */
    @Override
    public StbDtlVo getDtlMess(StbDtlVo stbDtlVo, SysUser sysUser) {
        if (StringUtils.NullEmpty(stbDtlVo.getQty().toString()) || "0".equals(stbDtlVo.getQty().toString())) {
            throw new ServiceException("201", "请输入正确的数量");
        }
        //对输入的数量小数点后的数量进行四舍五入取整数位
        BigDecimal num = stbDtlVo.getQty().setScale(0, BigDecimal.ROUND_HALF_UP);
        if (num.toString().length() > 12) {
            throw new ServiceException("201", "\"数量\"的整数长度不能大于\"12\"");
        }
        //计算并赋值返回
        stbDtlVo.setQty(num);
        stbDtlVo.setVal(stbDtlVo.getUnitPrice().multiply(num));
        stbDtlVo.setMkv(stbDtlVo.getMkUnitPrice().multiply(num));
        stbDtlVo.setTax(stbDtlVo.getTaxRate().multiply(stbDtlVo.getVal()));
        stbDtlVo.setCost(stbDtlVo.getUnitCost().multiply(num));
        return stbDtlVo;
    }

    /**
     * 添加商品集合
     *
     * @author HHe
     * @date 2019/9/5 20:37
     */
    @Override
    public int insertStbDtlList(List<StbDtl> stbDtls) {
        return stbDtlMapper.insertStbDtlList(stbDtls);
    }

    /**
     * 添加库存明细集合
     *
     * @author HHe
     * @date 2019/10/14 15:43
     */
    @Override
    public int insertStbDtlListAndUnitId(List<StbDtl> stbDtlList, SysUser sysUser) {
        return stbDtlMapper.insertStbDtlListAndUnitId(stbDtlList, sysUser.getDomain().getUnitId());
    }

    /**
     * 根据出库单编号和组织Id查询出库单明细
     *
     * @author HHe
     * @date 2019/10/21 10:35
     */
    @Override
    public List<StbDtl> queryStbDtlsByNumAndUnit(String stbNum, Long unitId) {

        return  stbDtlMapper.queryStbDtlsByNumAndUnit(stbNum, unitId);
    }

    /**
     * 修改库存明细成本集合
     *
     * @param stbDtls 需要修改明细
     * @return 数据库操作数
     * @author HHe
     * @date 2019/10/26 9:45
     */
    @Override
    public int updateStbDtlCostList(List<StbDtl> stbDtls) {
        return stbDtlMapper.updateStbDtlCostList(stbDtls);
    }

    /**
     * 删除库存单明细
     *
     * @param stbDtl 组织Id、库存单编号
     * @return 数据库操作数
     * @author HHe
     * @date 2019/11/6 17:24
     */
    @Override
    public int delByNumAndId(StbDtl stbDtl) {
        return stbDtlMapper.delByNumAndId(stbDtl);
    }

    /**
     * 查询明细数量
     *
     * @param stbDtl 组织Id，库存单编号
     * @return 数量
     * @author HHe
     * @date 2019/12/2 11:54
     */
    @Override
    public Long queryDtlCountByObj(StbDtl stbDtl) {
        return stbDtlMapper.queryDtlCountByObj(stbDtl);
    }

    /**
     * 添加库存明细
     *
     * @param stbDtl 添加明细
     * @return 数据库操作数
     * @author HHe
     * @date 2019/12/2 17:32
     */
    @Override
    public int insertStbDtl(StbDtl stbDtl) {
        return stbDtlMapper.insertStbDtl(stbDtl);
    }

    /**
     * 修改明细
     *
     * @param stbDtl 修改对象
     * @return 数据库操作数
     * @author HHe
     * @date 2019/12/3 9:53
     */
    @Override
    public int updateStbDtl(StbDtl stbDtl) {
        return stbDtlMapper.updateStbDtl(stbDtl);
    }

    /**
     * 修改出库单明细
     *
     * @param stbDtlList 需修改集合
     * @return 数据库操作数
     * @author HHe
     * @date 2019/12/5 12:11
     */
    @Override
    public int updateStbDtlList(List<StbDtl> stbDtlList) {
        return stbDtlMapper.updateStbDtlList(stbDtlList);
    }

    /**
     * 测试用，需调wz接口
     *
     * @author HHe
     * @date 2019/8/27 17:09
     */
    private List<CommonDtl> queryDtlByTypeNum(String docType, String DocNum) {
        List<CommonDtl> commonDtls = new ArrayList<>();
        CommonDtl commonDtl = new CommonDtl();
        commonDtl.setProdId(22L);
        commonDtl.setDiscRate(new BigDecimal(1));
        commonDtl.setUnitPrice(new BigDecimal(5));
        commonDtl.setFnlPrice(new BigDecimal(5));
        commonDtl.setQty(new BigDecimal(20));
        commonDtl.setMkUnitPrice(new BigDecimal(15));
        commonDtl.setVal(new BigDecimal(100));
        commonDtl.setMkv(new BigDecimal(300));
        commonDtl.setTaxRate(new BigDecimal("0.17"));
        commonDtl.setTax(commonDtl.getVal().multiply(commonDtl.getTaxRate()));
        commonDtl.setRemarks("122333");
        commonDtls.add(commonDtl);
        return commonDtls;
    }

    /**
     * 获取库存单明细 无分页
     *
     * @param tfnDtl
     * @return
     * @throws ServiceException
     */
    @Override
    public List<StbDtlVo> getStbDtlList(StbDtl tfnDtl) throws ServiceException {
        return stbDtlMapper.getStbDtlList(tfnDtl);
    }

}
