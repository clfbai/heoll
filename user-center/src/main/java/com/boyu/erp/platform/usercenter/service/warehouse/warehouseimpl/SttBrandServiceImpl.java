package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttBrand;
import com.boyu.erp.platform.usercenter.mapper.warehouse.SttBrandMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.SttBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * 盘点表品牌
 * @author HHe
 * @date 2019/9/18 14:59
 */
@Service
@Transactional
public class SttBrandServiceImpl implements SttBrandService {
    @Autowired
    private SttBrandMapper sttBrandMapper;
    /**
     * 添加多条盘点品牌
     * @author HHe
     * @date 2019/9/18 14:59
     */
    @Override
    public int addSttBrandList(List<SttBrand> sttBrandList, String sttNum, SysUser sysUser) {
        return sttBrandMapper.addSttBrandList(sttBrandList, sttNum, sysUser.getDomain().getUnitId());
    }
    /**
     * 根据盘点表编号和组织Id删除盘点表品牌
     * @author HHe
     * @date 2019/9/18 16:26
     */
    @Override
    public int delSttBrandByNumAndUnitId(Stt stt, SysUser sysUser) {
        return sttBrandMapper.delSttBrandByNumAndUnitId(stt.getSttNum(),stt.getUnitId());
    }
    /**
     * 根据对象的非空值查询集合
     * @author HHe
     * @date 2019/9/19 10:10
     */
    @Override
    public List<SttBrand> querySttBrandByObj(SttBrand sttBrand) {
        return sttBrandMapper.querySttBrandByObj(sttBrand);
    }
}
