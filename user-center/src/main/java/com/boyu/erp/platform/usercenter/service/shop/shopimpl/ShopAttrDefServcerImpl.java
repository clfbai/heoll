package com.boyu.erp.platform.usercenter.service.shop.shopimpl;

import com.boyu.erp.platform.usercenter.entity.shop.ShopAttrDef;
import com.boyu.erp.platform.usercenter.entity.system.SysCode;
import com.boyu.erp.platform.usercenter.entity.system.SysCodeDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.shop.ShopAttrDefMapper;
import com.boyu.erp.platform.usercenter.mapper.shop.ShopAttrMapper;
import com.boyu.erp.platform.usercenter.model.shop.ShopAttrDefModel;
import com.boyu.erp.platform.usercenter.service.shop.ShopAttrDefServicer;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.shop.ShopAttrDefVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ShopAttrDefServcerImpl implements ShopAttrDefServicer {
    @Autowired
    private ShopAttrDefMapper shopAttrDefMapper;
    @Autowired
    private ShopAttrMapper shopAttrMapper;
    @Autowired
    private SysCodeService codeService;
    @Autowired
    private SysCodeDtlService codeDtlService;

    @Override
    public PageInfo<ShopAttrDef> getShopAttr(Integer page, Integer size, ShopAttrDef shopAttrDef) throws ServiceException {
        PageHelper.startPage(page, size);
        List<ShopAttrDef> list = shopAttrDefMapper.getShopAttrDef(shopAttrDef);
        PageInfo<ShopAttrDef> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 功能描述: 增加门店定义属性
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 16:35
     */
    @Override
    public int addShopAttrDef(ShopAttrDef shopAttrdef) {

        SysCode code = new SysCode();
        code.setCodeType(shopAttrdef.getCodeType());
        code.setIsDel(CommonFainl.BTYPESTATUS);
        if (codeService.getSysCode(code) == null) {
            code.setDescription(shopAttrdef.getAttrName());
            codeService.insertSysCode(code);
        } else {
            codeService.updateSysCode(code);
        }
        return shopAttrDefMapper.insertSelective(shopAttrdef);
    }

    /**
     * 功能描述:
     *
     * @param: 删除门店定义属性
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 16:36
     */
    @Override
    public int deleteShopAttrDef(ShopAttrDef shopAttrdef) {
        SysCode code = new SysCode();
        code.setIsDel(CommonFainl.FAILSTATUS);
        codeService.updateSysCode(code);
        return shopAttrDefMapper.deleteByPrimaryKey(shopAttrdef.getAttrType());
    }

    /**
     * 功能描述: 修改门店定义属性(主键不能修改 attrType,和codeType 下拉值)
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/20 16:37
     */
    @Override
    public int updateShopAttrDef(ShopAttrDefModel model) {
        int a = 0;
        if (CommonFainl.ADD.equalsIgnoreCase(model.getAttrDeftype())) {
            a += this.addShopAttrDef(model);
        }
        if (CommonFainl.DELETE.equalsIgnoreCase(model.getAttrDeftype())) {
            a += this.deleteShopAttrDef(model);
        }
        if (CommonFainl.UPDATE.equalsIgnoreCase(model.getAttrDeftype())) {
            a += shopAttrDefMapper.updateByPrimaryKeySelective(model);
        }
        return a;
    }


    /**
     * 功能描述: 门店自定义属性名称和其下拉值集合
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/28 15:31
     */
    @Override
    public List<ShopAttrDefVo> getShopAttrName() {
        List<ShopAttrDefVo> models = new ArrayList<>();
        List<ShopAttrDef> list = shopAttrDefMapper.selectByPrimaryKey("");
        for (ShopAttrDef def : list) {
            ShopAttrDefVo model = new ShopAttrDefVo();
            BeanUtils.copyProperties(def, model);
            if (StringUtils.NullEmpty(def.getCodeType())) {
                model.setList(new ArrayList<>());
            } else {
                SysCodeDtl dtl = new SysCodeDtl();
                dtl.setCodeType(def.getCodeType());
                model.setList(codeDtlService.getAll(dtl));
            }
            models.add(model);
        }
        return models;
    }
}
