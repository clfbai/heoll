package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.goods.Color;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

/**
 * 颜色服务
 */
public interface ColorService {

    /**
     * 分页显示颜色
     *
     * @param page
     * @param size
     * @param color
     * @return
     * @throws ServiceException
     */
    public PageInfo<Color> selectALL(Integer page, Integer size, Color color) throws ServiceException;

    /**
     * 获取单个颜色对象
     *
     * @param color
     * @return
     * @throws ServiceException
     */
    public Color getColorById(Color color) throws ServiceException;

    /**
     * 增加颜色
     *
     * @param color
     * @return
     * @throws ServiceException
     */
    public int insertColor(Color color) throws ServiceException;

    /**
     * 修改颜色
     *
     * @param color
     * @return
     * @throws ServiceException
     */
    public int updateColor(Color color) throws ServiceException;

    /**
     * 删除颜色
     *
     * @param color
     * @return
     * @throws ServiceException
     */
    public int deleteColor(Color color) throws ServiceException;

    /**
     * 颜色下拉选择
     */
    List<Color> getOpentin();


    /**
     * 通过颜色代码及名称查询颜色
     */
    Color getColor(Color color);

    /**
     * 批量通过颜色代码及名称查询颜色
     */
    List<Color> getColorList(List<Color> coloList);
    /**
     * 根据Id集合查询Color集合
     * @author HHe
     * @date 2019/10/7 17:08
     */
    List<Color> queryColorListByIds(Set<Long> colorIds);
}