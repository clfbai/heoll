package com.boyu.erp.platform.usercenter.service.shop.shopimpl;

import com.boyu.erp.platform.usercenter.mapper.shop.ShopRentValMapper;
import com.boyu.erp.platform.usercenter.service.shop.ShopRentValSerivcer;
import com.boyu.erp.platform.usercenter.vo.shop.ShopRentValVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ShopRentValSerivcerImpl implements ShopRentValSerivcer {
    @Autowired
    private ShopRentValMapper shopRentValMapper;

    /**
     * 功能描述:  查询门店租金
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/23 10:30
     */
    @Override
    public List<ShopRentValVo> getShopRentVal(ShopRentValVo shopRentVal) {
        return shopRentValMapper.getShopRentVal(shopRentVal);
    }
}
