package com.boyu.erp.platform.usercenter.mapper;

import com.boyu.erp.platform.usercenter.entity.goods.Color;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ColorMapper {

    public List<Color> selectALL(Color color);

    public Color selectByPrimaryKey(Color color);

    public int insertColor(Color color);

    public int updateColor(Color color);

    public int deleteColor(Color color);

   List<Color>  getOpention();
    /**
     *
     * 功能描述: 批量通过颜色代码及名称查询颜色
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/16 9:45
     */
    List<Color> getColorList(@Param("list") List<Color> list);
    /**
     *  根据颜色id集合查询颜色集合
     * @author HHe
     * @date 2019/10/7 17:17
     */
    List<Color> queryColorListByIds(@Param("colorIds") Set<Long> colorIds);
}