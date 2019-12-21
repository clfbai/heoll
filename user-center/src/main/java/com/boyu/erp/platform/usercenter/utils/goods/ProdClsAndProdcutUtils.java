package com.boyu.erp.platform.usercenter.utils.goods;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.goods.ProdCls;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.goods.ProdClsMapper;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.common.BaseDateUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @program: boyu-platform_text
 * @description: 商品和商品品种工具类
 * @author: clf
 * @create: 2019-06-19 17:32
 */
@Slf4j
@Component
public class ProdClsAndProdcutUtils {
    @Autowired
    private SysParameterService parameterService;

    @Autowired
    private ProdClsMapper prodClsMapper;

    /**
     * 根据参数生成 商品品种代码（prod_cls_code)
     */
    public String creatPrcoClsId(ProdCls cls, SysUser u) throws Exception {

        if (parameterService.findByParameter(ParameterString.PROC_CLS) == null) {
            SysParameter parameter = new SysParameter();
            parameter.setParmId(ParameterString.PROC_CLS);
            /**
             * 手动添加规则
             * */
            parameter.setDescription("BRAND_ID+YEAR2+SEASON+AUTO_NUM=BRAND_ID:YEAR_VAL:SEASON:@[0]3");
            BaseDateUtils.setBeasDate(parameter, u, CommonFainl.ADD);
            parameterService.insertParameter(parameter);
        }

        /**
         * 品牌Id
         * */
        String brandId = "";
        //年份
        String year = "";
        //月份
        String season = "";
        //自增数 默认取 1
        Integer num = 1;

        String str = parameterService.findByParameter(ParameterString.PROC_CLS).getParmVal();

        //补全长度字符
        String zuNum = str.substring(str.length() - 1, str.length());

        //补全字符 (现在只能用0补全)
        String an = str.substring(str.length() - 3, str.length() - 2);

        //补全长度
        Integer size = Integer.parseInt(zuNum);
        //品种最大自增数
        Integer maxNum = power(10, size);

        String[] split = str.split("=");
        String one = split[0];
        String two = split[1];
        split = one.split("\\+");

        String[] strs = two.split(":");
        if (split.length != strs.length) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "参数设置有误");
        }


        /**
         *    BRAND_ID+YEAR2+SEASON+AUTO_NUM=BRAND_ID:YEAR_VAL:SEASON@[0]3
         *   分割后第一个是品牌Id 后一个需包含前一个
         *   @[0]3
         *   第二个数组  和的字符串  可能会拼接  @[0]3代表以 0填充前缀 最大长度为 3  例如  1 则为 001 但是  22 则为 022
         *
         *   现在为了方便起见 ：  BRAND_ID+YEAR2+加自增数 @[0]3  以[] 内为填充
         * */

        if (strs[0].indexOf(split[0]) < 0) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "参数值设置有误");
        }

        brandId = String.valueOf(cls.getBrandId());
        String yearsub = String.valueOf(cls.getYearVal());
        /**
         * 两位年份
         * */
        if (split[1].indexOf("2") > 0) {
            year = yearsub.substring(yearsub.length() - 2, yearsub.length());
        }
        /**
         * 一位年份
         * */
        if (split[1].indexOf("1") > 0) {
            year = yearsub.substring(yearsub.length() - 1, yearsub.length());
        }

        if ("SEASON".equals(split[2])) {
            season = cls.getSeason();
        }
        if (split.length <= 3) {
            //不拼接月份时  设置增量
            if (split[2].indexOf("AUTO") < 0) {
                throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "商品品种代码参数值设置错误");
            }
        }

        if (brandId.length() == 1) {
            brandId = "0" + brandId;
        }
        String pj = "";
        if (season == null || "".equals(season)) {
            /**
             * 不启用月份参数 (01.02.)代码类型
             * */
            pj = brandId + "." + year + ".";
        } else {
            /**
             * 启用月份参数 (01021) 代码类型
             * */
            pj = brandId + year + season;
        }
        cls.setProdClsCode(pj);
        List<ProdCls> p = prodClsMapper.getCode(cls);
        List<Integer> list = this.getInt(p, size);
        String cao = "";
        str = pj;
        if (list != null && list.size() > 0) {
            //集合排序
            Collections.sort(list);
            Integer in = list.get(list.size() - 1);
            //最大值判断
            if (in > maxNum - num) {
                throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "同一品牌商品商品品种代码已到达上限,请更改最大长度");
            }
            for (int i = 1; i <= size; i++) {
                if ((in + "").length() == i && (in + "").length() != size) {
                    for (int j = 0; j < size - i; j++) {
                        an += an;
                    }
                }
                if ((in + "").length() == size) {
                    an = "";
                }
            }
            cao = an + (in + num) + "";
            str = str + cao;
        } else {
            //如果第一个自动补全
            String bus = "";
            for (int i = 1; i < size; i++) {
                bus += an;
            }
            str = str + bus + "1";
        }
        ProdCls c = new ProdCls();
        c.setProdClsCode(str);
        if (prodClsMapper.findByCode(c) != null) {
            log.info("商品品种代码:" + str + "已存在");
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "商品品种代码:" + str + "已存在");
        }
        log.info("生成商品品种代码为:   " + str);
        return str;
    }

    /**
     * 功能描述: 批量生产商品品种代码
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/16 19:42
     */
    public Set<String> getCode(List<ProdCls> list, SysUser user) throws Exception {
        int ran = (int) (Math.random() * (list.size() - 1) + 1);
        List<String> getList = new ArrayList<>();
        ProdCls c = list.get(ran);
        String yearsub = String.valueOf(c.getYearVal());
        String brandId = String.valueOf(c.getBrandId());
        //2位年份
        String year = yearsub.substring(yearsub.length() - 2, yearsub.length());
        if (brandId.length() == 1) {
            brandId = "0" + brandId;
        }
        String pj = brandId + year + c.getSeason();
        getList.add(pj);
        c.setProdClsCode(pj);
        List<ProdCls> p = prodClsMapper.getCode(c);
        List<Integer> getInt = this.getInt(p, 4);
        //不存在相同商品Code
        if (getInt.size() <= 0) {
            for (int a = 0; a < list.size(); a++) {
                for (ProdCls cls : list) {
                    String sg = pj + a;
                    cls.setProdClsCode(sg);
                }
            }
        } else {
            Collections.sort(getInt);
            int i = getInt.get(getInt.size() - 1);
            for (int a = 0; a < list.size(); a++) {
                for (ProdCls cls : list) {
                    int s = a + i;
                    String sg = pj + s;
                    cls.setProdClsCode(sg);
                }
            }
        }
        return null;


    }

    /**
     * 功能描述: 获取增量整数
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/8/16 20:01
     */
    public List<Integer> getInt(List<ProdCls> p, int size) {
        List<Integer> list = new ArrayList<>();
        String cao = "";
        if (p != null && p.size() > 0) {
            for (ProdCls s : p) {
                //取品种代码增量集合
                list.add(Integer.parseInt(s.getProdClsCode().substring(s.getProdClsCode().length() - size, s.getProdClsCode().length())));
            }
        }
        return list;
    }


    /**
     * 整数的n次方
     */
    public int power(int a, int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return a;
        } else {
            return a * power(a, n - 1);
        }
    }

}
