package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.StlDtl;
import com.boyu.erp.platform.usercenter.mapper.warehouse.StlDtlMapper;
import com.boyu.erp.platform.usercenter.model.wareh.StlModel;
import com.boyu.erp.platform.usercenter.service.warehouse.StlDtlService;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.warehouse.StlDtlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
/**
 * 盘点清单明细服务
 * @author HHe
 * @date 2019/9/23 15:03
 */
@Service
public class StlDtlServiceImpl implements StlDtlService {
    @Autowired
    private StlDtlMapper stlDtlMapper;
    @Autowired
    private DelivUtil delivUtil;
    /**
     * 添加盘点清单明细集合
     * @author HHe
     * @date 2019/9/23 15:05
     */
    @Override
    public int addStlDtlList(StlModel stlModel, SysUser sysUser) {
        return stlDtlMapper.addStlDtlList(stlModel);
    }
    /**
     * 删除盘点清单明细
     * @author HHe
     * @date 2019/9/23 15:45
     */
    @Override
    public int delStlDtlByNumAndId(StlModel stlModel, SysUser sysUser) {
        return stlDtlMapper.delStlDtlByNumAndId(stlModel);
    }
    /**
     * 根据盘点清单编号和组织id查询明细集合
     * @author HHe
     * @date 2019/9/25 11:10
     */
    @Override
    public List<StlDtlVO> queryListByStlNumAndId(StlDtl stlDtl,SysUser sysUser) throws NoSuchMethodException, IntrospectionException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        List<StlDtlVO> stlDtlVOList = stlDtlMapper.queryListByStlNumAndId(stlDtl.getStlNum(), stlDtl.getUnitId());
        //根据商品Id查询其所有信息；工具类
        return delivUtil.queryProdDetailsByProdIdList(stlDtlVOList,new HashMap<>());
//        stlDtlVOList;
    }
    /**
     * 根据盘点清单编号和组织id查询盘点清单明细集合
     * @author HHe
     * @date 2019/9/26 19:55
     */
    @Override
    public List<StlDtl> queryStlDtlListByStlNumListAndId(List<String> stlNumList, Long unitId) {
        return stlDtlMapper.queryStlDtlListByStlNumListAndId(stlNumList,unitId);
    }
}
