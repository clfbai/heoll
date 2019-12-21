package com.boyu.erp.platform.usercenter.utils.goods;

import com.boyu.erp.platform.usercenter.vo.OptionVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProdClsUtils {


    /* {
      { codeType: "EDITION" },//版型名
      { codeType: "GENDER" },//性别
      { codeType: "SUITE_PROP" },//套装属性
      { codeType: "MAIN_STYLE" },//形状
      { codeType: "ASSIS_STYLE" },//面料属性
      { codeType: "SUB_STYLE" },//排扣
      { codeType: "UOM" },//计量单位
      { codeType: "EDITION" },//罩杯
      { codeType: "shared" },//是否共享
      { codeType: "season" },//适销季节
      { codeType: "MKT_GRD" },//市场级别
      { codeType: "MKT_SORT" },//大类
      { codeType: "MKT_TYPE" },//特价方式
      { codeType: "SALES_CHNL" },//销售渠道
      { codeType: "PRC_LVL" },//价格档
      { codeType: "SALES_MODE" },//销售方式
      { codeType: "ASSIS_STYLE" },//面料
      { codeType: "CUP_IN_MTRL" },//里料
      { codeType: "MFR_BRAND_ID" },//厂商品牌
      { codeType: "PROD_STD" },//执行标准
      { codeType: "PROD_GRD" },//商品级别
      { codeType: "PROD_STYLE" },//产品风格
      { codeType: "PROD_LINE" },//存储仓库
      { codeType: "STORY_LINE" },//楼层
      { codeType: "STK_GRD" },//库存级别
      { codeType: "INNER_PCK" },//内包装
      { codeType: "PCK_TYPE" },//打包类型
*/


    private List<OptionVo> stkAdopted = null;
    private List<String> list = null;

    private Map<String, Object> map = null;

    /**
     * 功能描述:  返回储存字符串的集合 减少new
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/20 12:13
     */
    public List<String> getListString() {

        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }
        return list;
    }

    /**
     * 功能描述:  返回前台下拉的 map 内 是和否的下拉
     *
     * @return map
     * @param:
     * @auther: CLF
     * @date: 2019/7/20 12:06
     */
    public List<OptionVo> getList() {

        if (stkAdopted == null) {
            stkAdopted = new ArrayList<>();
        } else {
            //先将原来数据清除
            stkAdopted.clear();
            //在添加

        }
        stkAdopted.add(new OptionVo("是", "T"));
        stkAdopted.add(new OptionVo("否", "F"));
        return stkAdopted;
    }

    /**
     * 功能描述:  返回前台下拉的 map  减少一直 new map
     *
     * @return map
     * @param:
     * @auther: CLF
     * @date: 2019/7/20 12:03
     */
    public Map<String, Object> getMap() {
        if (map == null) {
            map = new HashMap<>();
        } else {
            map.clear();
        }
        return map;
    }

    /**
     * 功能描述:  返回 是否下拉
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/20 12:14
     */
    public Map<String, Object> getMapOffOn(List<String> list) {
        Map<String, Object> map = getMap();
        for (String s : list) {
            map.put(s, getList());
        }
        return map;
    }

}
