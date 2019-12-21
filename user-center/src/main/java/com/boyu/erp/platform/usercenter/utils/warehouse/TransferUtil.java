package com.boyu.erp.platform.usercenter.utils.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.vo.CommonDtl;
import com.boyu.erp.platform.usercenter.vo.purchase.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单外部调用接口
 *
 * @author HHe
 * @date 2019/11/29 20:59
 */
@Component
public class TransferUtil {
    @Autowired
    private PurchaseService purchaseService;
    /**
     * 通知原单已发货
     * @author HHe
     * @date 2019/11/30 10:33
     */
    public void sendTo(String type, String srcDocType,Long srcDocUnitId,String srcDocNum,Long unitId,String gdnNum, SysUser sysUser) throws Exception {
        //todo 通知原单已发货
        purchaseService.publicMethon(type, srcDocType, srcDocUnitId, srcDocNum, unitId, gdnNum, sysUser);
    }
    /**
     * 查询原单明细
     * @author HHe
     * @date 2019/11/30 15:07
     */
    public List<CommonDtl> getcommondtl(String srcDocType,String num) {
        return purchaseService.selectBillInfo(srcDocType, num);
    }
    /**
     * 查询原单
     * @author HHe
     * @date 2019/11/30 15:07
     */
    public Purchase selectBill(String taskDocType,String taskDocUnitId,String taskDocNum){
        return purchaseService.selectBill(taskDocType, taskDocUnitId, taskDocNum);
    }
    /**
     * 查询验证原单单条明细
     * @author HHe
     * @date 2019/12/7 9:45
     */
    public CommonDtl queryOneSrcDtl(String type,String num,Long prodId) {
        List<CommonDtl> commonDtls = purchaseService.selectBillInfo2(type, num, prodId);
        if(commonDtls==null||commonDtls.size()<=0){
            throw new ServiceException("201", "原单中没有该商品");
        }
        return commonDtls.get(0);
    }
}
