package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.Stt;
import com.boyu.erp.platform.usercenter.entity.warehouse.SttProdCls;
import com.boyu.erp.platform.usercenter.mapper.warehouse.SttProdClsMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.SttProdClsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * 商品品种
 * @author HHe
 * @date 2019/9/18 15:24
 */
@Service
@Transactional
public class SttProdClsServiceImpl implements SttProdClsService {
    @Autowired
    private SttProdClsMapper sttProdClsMapper;
    /**
     * 商品品种
     * @author HHe
     * @date 2019/9/18 15:25
     */
    @Override
    public int addSttProdClsList(List<SttProdCls> sttProdClsList, String sttNum, SysUser sysUser) {
        return sttProdClsMapper.addSttProdClsList(sttProdClsList, sttNum, sysUser.getDomain().getUnitId());
    }
    /**
     * 根据盘点表编号和组织Id删除商品品种
     * @author HHe
     * @date 2019/9/18 16:30
     */
    @Override
    public int delSttProdClsByNumAndUnitId(Stt stt, SysUser sysUser) {
        return sttProdClsMapper.delSttProdClsByNumAndUnitId(stt.getSttNum(),stt.getUnitId());
    }
    /**
     * 根据编号和组织Id查询品种集合
     * @author HHe
     * @date 2019/9/19 15:34
     */
    @Override
    public List<SttProdCls> querySttProdClsByObj(SttProdCls sttProdCls) {
        return sttProdClsMapper.querySttProdClsByObj(sttProdCls);
    }
}
