package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.goods.Color;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.ColorMapper;
import com.boyu.erp.platform.usercenter.service.system.ColorService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorMapper colorMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<Color> selectALL(Integer page, Integer size, Color color) throws ServiceException {
        PageHelper.startPage(page, size);
        List<Color> colors = colorMapper.selectALL(color);
        PageInfo<Color> pageInfo = new PageInfo<Color>(colors);
        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public Color getColorById(Color color) throws ServiceException {
        if (color.getColorId() == null) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "颜色编号为空");
        }
        log.info("caobima :"+color);
        return colorMapper.selectByPrimaryKey(color);
    }
   /**
    * 通过颜色名称和颜色代码查询颜色
    * */
    @Override
    @Transactional(readOnly = true)
    public Color getColor(Color color) throws ServiceException {
        return colorMapper.selectByPrimaryKey(color);
    }
    /**
     * 批量通过颜色代码及名称查询颜色
     */
    @Override
    public List<Color> getColorList(List<Color> coloList) {
        return colorMapper.getColorList(coloList);
    }
    /**
     * 根据Id集合查询Color集合
     * @author HHe
     * @date 2019/10/7 17:08
     */
    @Override
    public List<Color> queryColorListByIds(Set<Long> colorIds) {
        return colorMapper.queryColorListByIds(colorIds);
    }


    @Override
    public int insertColor(Color color) throws ServiceException {

        //颜色编码不为空，且唯一
        String colorCode = color.getColorCode();

        if (StringUtils.isBlank(colorCode)) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "颜色色码不允许为空");
        }

        //构建对象进行业务查询.
        Color c = new Color();
        c.setColorCode(colorCode);
        List<Color> resultList = this.colorMapper.selectALL(c);

        //判断是否存在，只有不存在才可以插入
        if (CollectionUtils.isNotEmpty(resultList)) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "颜色色码已经被占用，请修改");
        }
        return colorMapper.insertColor(color);
    }

    @Override
    public int updateColor(Color color) throws ServiceException {

        if (color.getColorId() == null) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "颜色编号为空");
        }
        return colorMapper.updateColor(color);
    }

    @Override
    public int deleteColor(Color color) throws ServiceException {
        if (color.getColorId() == null) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "颜色编号为空");
        }
        return colorMapper.deleteColor(color);
    }

    /**
     * 颜色下拉选择
     */
    @Override
    @Transactional(readOnly = true)
    public List<Color> getOpentin() {
        return colorMapper.getOpention();
    }
}