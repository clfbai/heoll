package com.boyu.erp.platform.usercenter.service.shop.shopimpl;

import com.boyu.erp.platform.usercenter.entity.shop.CurrType;
import com.boyu.erp.platform.usercenter.mapper.shop.CurrTypeMapper;
import com.boyu.erp.platform.usercenter.service.shop.CurrTypeServicer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class CurrTypeServicerImpl implements CurrTypeServicer {
    @Autowired
    private CurrTypeMapper currTypeMapper;

    @Override
    public List<CurrType> getCurrTyrp(CurrType currType) {
        return currTypeMapper.getCurrTyrp(currType) ;
    }
}
