package com.boyu.erp.platform.usercenter.service.shop.shopimpl;

import com.boyu.erp.platform.usercenter.entity.shop.ShopGpFml;
import com.boyu.erp.platform.usercenter.mapper.shop.ShopGpFmlMapper;
import com.boyu.erp.platform.usercenter.service.shop.ShopGpFmlSeriver;
import com.boyu.erp.platform.usercenter.vo.shop.ShopGpFmlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类描述: 门店普通促销公式服务
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/23 9:47
 */
@Slf4j
@Service
@Transactional
public class ShopGpFmlSeriverImpl implements ShopGpFmlSeriver {
    @Autowired
    private ShopGpFmlMapper shopGpFmlMapper;

    /**
     * 功能描述: 根据门店Id查询门店普通促销公式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/23 9:51
     */
    @Override
    public List<ShopGpFmlVo> getShopGpFml(ShopGpFml shopGpFml) {
        return shopGpFmlMapper.getShopGpFml(shopGpFml);
    }
}
