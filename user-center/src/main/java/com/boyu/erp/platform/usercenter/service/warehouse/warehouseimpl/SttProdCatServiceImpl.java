package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttProdCat;
import com.boyu.erp.platform.usercenter.mapper.warehouse.SttProdCatMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.SttProdCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * 商品分类
 * @author HHe
 * @date 2019/9/18 15:15
 */
@Service
@Transactional
public class SttProdCatServiceImpl implements SttProdCatService {
    @Autowired
    private SttProdCatMapper sttProdCatMapper;
    /**
     * 添加清点表商品分类集合
     * @author HHe
     * @date 2019/9/18 15:16
     */
    @Override
    public int addSttProdCatList(List<SttProdCat> sttProdCatList, String sttNum, SysUser sysUser) {
        return sttProdCatMapper.addSttProdCatList(sttProdCatList,sttNum,sysUser.getDomain().getUnitId());
    }
    /**
     * 根据盘点表编号和组织Id删除商品分类
     * @author HHe
     * @date 2019/9/18 16:32
     */
    @Override
    public int delSttProdCatByNumAndUnitId(Stt stt, SysUser sysUser) {
        return sttProdCatMapper.delSttProdCatByNumAndUnitId(stt.getSttNum(),stt.getUnitId());
    }
    /**
     * 根据不为空属性查询盘点表商品分类集合
     * @author HHe
     * @date 2019/9/19 12:28
     */
    @Override
    public List<SttProdCat> querySttProdCatByObj(SttProdCat sttProdCat) {
        return sttProdCatMapper.querySttProdCatByObj(sttProdCat);
    }
}
