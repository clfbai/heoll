package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysRefNumDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stb;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.warehouse.StbMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.StbService;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.warehouse.GdnListVO;
import com.boyu.erp.platform.usercenter.vo.warehouse.GrnVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.StbGdnVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 入库单接口实现
 * @author: ck
 * @create: 2019-07-01 15:36
 */
@Slf4j
@Service
@Transactional
public class StbServiceImpl implements StbService {
    @Autowired
    private StbMapper stbMapper;
    @Autowired
    private DelivUtil delivUtil;

    /**
     * 插入入库单相关库存单
     *
     * @param grnVo
     * @return
     * @throws ServiceException
     */
    @Override
    public int insert(GrnVo grnVo) throws ServiceException {
        return stbMapper.insert(grnVo);
    }

    @Override
    public Stb getStbByPk(Stb stb) throws ServiceException {
        return stbMapper.selectByPrimaryKey(stb);
    }

    @Override
    public int update(Stb stb) throws ServiceException {
        return stbMapper.updateByPrimaryKey(stb);
    }

    /**
     * 查询入库单编号
     */
    @Override
    public List<Map<String, String>> getStbNum(Long unitId) {
        return stbMapper.getNum(unitId);
    }

    /**
     * 生成出库库存单（PLAN2）
     * @param stbList
     * @param alwaysNum 总单编号
     * @return
     */
    @Override
    public int insertStbByGdn(List<StbGdnVO> stbList, String alwaysNum) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        int i = 0;
        if (stbList.size() > 1) {
            StbGdnVO alwayObj = this.getcfobj(stbList,alwaysNum,"D");
        } else {
            //单条生成
            StbGdnVO stbGdnVO = stbList.get(0);
            stbGdnVO.setStbNum(alwaysNum);
//            i = stbService.insertStbGdn(stbGdnVO);
            //单条出库单的明细生成
//            i += stbDtlService.insertStbDtlVO(stbGdnVO.getStbDtlVos(), alwaysNum);
        }
        return i;
    }
    /**
     * 多条任务添加库存单时（PLAN2）
     * @param stbList
     * @param alwaysNum
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     *
     */
    private StbGdnVO getcfobj(List<StbGdnVO> stbList,String alwaysNum,String drType) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        //判断有没有相同属性值，注入到对象
        StbGdnVO stbGdnVO1 = null;
        for (StbGdnVO gdnVO : stbList) {
            if (stbGdnVO1 == null) {
                stbGdnVO1 = gdnVO;
                continue;
            }
            //返回对象的所有属性
            Field[] fields = gdnVO.getClass().getDeclaredFields();
            for (Field field : fields) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(),StbGdnVO.class);
                Method rM = pd.getReadMethod();//获得读方法
                Method wM = pd.getWriteMethod();//写方法
                Object mess = rM.invoke(gdnVO);
                if(mess!=null &&!rM.invoke(gdnVO).equals(rM.invoke(stbGdnVO1))){
                    wM.invoke(stbGdnVO1,(Object) null);
                }
            }
            gdnVO.setLdgStbNum(alwaysNum);
            gdnVO.setDrType(drType);
            //添加子单
            stbMapper.insertStbGdn(gdnVO);
        }
        stbGdnVO1.setLdgStbNum(null);
        //添加总单
        stbMapper.insertStbGdn(stbGdnVO1);
        return stbGdnVO1;
    }

    /**
     * 添加库存出库单
     * @param stbGdnVO
     * @return 库存单编号
     */
    @Override
    public String insertStbGdn(StbGdnVO stbGdnVO, SysUser sysUser) {
        //设置组织id、库存单编号
        stbGdnVO.setUnitId(sysUser.getDomain().getUnitId());
        //生成编号
        SysRefNumDtl refNumDtl = new SysRefNumDtl();
        refNumDtl.setRefNumId("STB_NUM");
        refNumDtl.setUnitId(sysUser.getDomain().getUnitId());
        String num = delivUtil.createDocNum(refNumDtl);
        stbGdnVO.setStbNum(num);
        //操作人id、操作时间
        stbGdnVO.setOprId(sysUser.getOprId());
        stbGdnVO.setOpTime(new Timestamp(System.currentTimeMillis()));
        int i = stbMapper.insertStbGdn(stbGdnVO);
        if(i<1){
            throw new ServiceException("201", "添加出库库存单失败");
        }
        return num;
    }

    /**
     * 修改出库库存单
     * @param gdnListVO
     * @return
     */
    @Override
    public int updateStbGdnVO(GdnListVO gdnListVO) {
        return stbMapper.updateStbGdnVO(gdnListVO);
    }

    /**
     * 根据总单编号查询子单编号集合
     * @param stbNum
     * @return
     */
    @Override
    public List<String> querySonStbNum(String stbNum) {
        return stbMapper.querySonStbNum(stbNum);
    }
    /**
     * 删除STB
     * @param sonStbNumList
     * @return
     */
    @Override
    public int delStbList(List<String> sonStbNumList) {
        return stbMapper.delStb(sonStbNumList);
    }

    /**
     * 删除单条库存
     * @param stbNum
     * @return
     */
    @Override
    public int delAStb(String stbNum) {
        return stbMapper.delAStb(stbNum);
    }
    /**
     * 修改总库存单信息
     * @param stbGdnVO
     * @return
     * @author HHe
     */
    @Override
    public int updateTotStb(StbGdnVO stbGdnVO) {
        return stbMapper.updateTotStb(stbGdnVO);
    }

    /**
     * 查询库存单编号对应的子库存单
     * @author HHe
     */
    @Override
    public List<String> queryStbIsTotal(String stbNum,Long ownerId) {
        return stbMapper.queryStbIsTotal(stbNum,ownerId);
    }

    /**
     * 根据库存单查询出库库存单封装成GdnListVO
     * @author HHe
     */
    @Override
    public GdnListVO queryGdnListVOByStbNum(String stbNum,Long ownerId) {
        return stbMapper.queryGdnListVOByStbNum(stbNum,ownerId);
    }
    /**
     * 修改库存单
     * @author HHe
     * @date 2019/9/24 16:24
     */
    @Override
    public int updateStbByNumAndId(Stb stb) {
        return stbMapper.updateStb(stb);
    }
    /**
     * 添加库存单返回库存编号
     * @author HHe
     * @date 2019/9/27 19:55
     */
    @Override
    public String insertStb(Stb stb,SysUser sysUser) {
        if(stb.getStbNum()==null||"".equals(stb.getStbNum())){
            SysRefNumDtl refNumDtl = new SysRefNumDtl();
            refNumDtl.setUnitId(stb.getUnitId());
            refNumDtl.setRefNumId("STB_NUM");
            String docNum = delivUtil.createDocNum(refNumDtl);
            stb.setStbNum(docNum);
        }
        stbMapper.insertStb(stb);
        return stb.getStbNum();
    }
    /**
     * 删除库存表
     * @author HHe
     * @date 2019/11/6 17:19
     */
    @Override
    public int delStb(Stb stb) {
        return stbMapper.deleteByPrimaryKey(stb);
    }
    /**
     * 根据原单查询出库单非作废数量
     * @author HHe
     * @date 2019/11/21 14:34
     */
    @Override
    public Long queryCountByErc(Stb stb) {
        return stbMapper.queryCountByErc(stb);
    }





}