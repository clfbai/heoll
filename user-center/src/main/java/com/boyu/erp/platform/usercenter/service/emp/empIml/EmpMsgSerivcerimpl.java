package com.boyu.erp.platform.usercenter.service.emp.empIml;

import com.boyu.erp.platform.usercenter.entity.mq.Freeze;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.mq.emp.EmpMsg;
import com.boyu.erp.platform.usercenter.entity.shop.ShopEmp;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.employye.EmployeeMapper;
import com.boyu.erp.platform.usercenter.service.emp.EmpMsgSerivcer;
import com.boyu.erp.platform.usercenter.service.system.impl.VendeeServiceimpl;
import com.boyu.erp.platform.usercenter.utils.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类描述:  导购消息服务
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/10/10 16:20
 */
@Slf4j
@Service
@Transactional
public class EmpMsgSerivcerimpl implements EmpMsgSerivcer {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public MessageObject getMsgEmp(ShopEmp empKey) throws ServiceException {
        log.info("[]" + empKey);
        EmpMsg msg = employeeMapper.selectEmpMsg(empKey);
        if (msg == null) {
            throw new ServiceException("403", "没有查询到员工请检查参数");
        }
        String province = msg.getProvince() == null ? "" : msg.getProvince();
        String city = msg.getCity() == null ? "" : msg.getCity();
        String district = msg.getDistrict() == null ? "" : msg.getDistrict();
        msg.setRegion(district + city + province);
        msg.setId(VendeeServiceimpl.YW + empKey.getEmplId());
        msg.setShopId(VendeeServiceimpl.YW + msg.getShop());
        MessageObject messageObject = new MessageObject("sales.add", msg);
        messageObject.setUuid(RandomStringUtils.crateUuid(6) + "_" + msg.getId());
        messageObject.setRequestMessage("增加导购");
        return messageObject;
    }

    /**
     * 功能描述: 冻结导购消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/10 16:25
     */
    @Override
    public MessageObject getFreezeEmp(ShopEmp empKey) {
        Freeze freeze = new Freeze(VendeeServiceimpl.YW + empKey.getEmplId(), true);
        MessageObject messageObject = new MessageObject("sales.freeze", freeze);
        messageObject.setRequestMessage("冻结导购");
        messageObject.setUuid(RandomStringUtils.crateUuid(6) + "_" + VendeeServiceimpl.YW + empKey.getEmplId());
        return messageObject;
    }
}
