package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbDtl;
import com.boyu.erp.platform.usercenter.entity.warehouse.StkCost;
import com.boyu.erp.platform.usercenter.mapper.goods.ProductMapper;
import com.boyu.erp.platform.usercenter.mapper.warehouse.StbDtlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述:
 *
 * @Description 异步类
 * @auther: CLF
 * @date: 2019/12/6 10:13
 */
@Async
@Slf4j
@Component
public class AsynchronizationUtils {
    @Autowired
    private StbDtlMapper stbDtlMapper;
    @Autowired
    private ProductMapper productMapper;

    /**
     * 功能描述: 异步修改所有入库单明细商品的成本
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/12/5 17:34
     */

    public void stkCostUpdateStbDtl(List<StkCost> list, Long unitId) {
        if (CollectionUtils.isNotEmpty(list)) {
            //所有商品品种
            List<Long> prodClsIds = list.stream().map(StkCost::getProdClsId).collect(Collectors.toList());
            //所有商品
            List<Product> prodIds = productMapper.selectProdClsIdList(prodClsIds);
            List<StbDtl> stbDtls = stbDtlMapper.selectProdId(prodIds.stream().map(Product::getProdId).collect(Collectors.toList()), unitId);

            List<StbDtl> max = new ArrayList<>();
            for (StkCost stkCost : list) {
                for (StbDtl dtl : stbDtls) {
                    for (Product p : prodIds) {
                        if (dtl.getProdId().equals(p.getProdId()) && p.getProdClsId().equals(stkCost.getProdClsId())) {
                            dtl.setUnitCost(stkCost.getUnitCost());
                            dtl.setCost(stkCost.getUnitCost().multiply(dtl.getQty()));
                            max.add(dtl);
                        }
                    }
                }
            }
            log.info("{异步修改 }" + max);
            stbDtlMapper.updateStbDtlCostList(max);
        } else {
            log.info("无需修改成本");
        }
    }

    /**
     * 功能描述: 异步是如果有返回值时返回值可能为 null
     * 因此返回类型只能是简单包装类
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/12/6 10:13
     */
    public Integer TestAsyn(int u) {
        System.out.println("{as }" + u);
        return u;
    }

}
