package com.boyu.erp.platform.usercenter.service.shop.shopimpl;

import com.boyu.erp.platform.usercenter.mapper.shop.ShopSpFmlMapper;
import com.boyu.erp.platform.usercenter.service.shop.ShopSpFmlServicer;
import com.boyu.erp.platform.usercenter.vo.shop.ShopSpFmlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ShopSpFmlServicerImpl implements ShopSpFmlServicer {
    @Autowired
    private ShopSpFmlMapper shopSpFmlMapper;

    /**
     * 功能描述: 查询门店扣点公式
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/23 10:40
     */
    @Override
    public List<ShopSpFmlVo> getShopSpFml(ShopSpFmlVo s) {
        return shopSpFmlMapper.getShopSpFml(s);
    }
}
