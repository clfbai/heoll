package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.ExceptionRequestCwms;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.system.ExceptionRequestCwmsMapper;
import com.boyu.erp.platform.usercenter.service.system.ExceptionRequestCwmsService;
import com.boyu.erp.platform.usercenter.utils.common.InterfaceLogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExceptionRequestCwmsServiceImpl implements ExceptionRequestCwmsService {
    @Autowired
    private ExceptionRequestCwmsMapper exceptionRequestCwmsMapper;
    @Autowired
    private InterfaceLogUtils logUtils;

    @Override
    public int addExceptionRequestCwms(ExceptionRequestCwms exceptionRequestCwms) {
        return exceptionRequestCwmsMapper.insertSelective(exceptionRequestCwms);
    }

    @Override
    public ExceptionRequestCwms selectKey(ExceptionRequestCwms exceptionRequestCwms) {
        return exceptionRequestCwmsMapper.selectByPrimaryKey(exceptionRequestCwms.getId());
    }

    /**
     * 功能描述:通过Http请求再次发送的(C-WMS平台记录的异常信息再次发请求时)
     * <p>
     * 1.查询重发对象是否存在，修改执行此数
     * 2.根据Http请求返回值 的code 判断当前记录是否执行成功 ，成功修改进度
     * 还要添加一个查询执行过的消息不再执行
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 10:27
     */
    @Override
    public int updateExcepCwms(ExceptionRequestCwms exceptionRequestCwms) throws ServiceException {
            return exceptionRequestCwmsMapper.updateByPrimary(exceptionRequestCwms);

    }

    /**
     * 根据UUID删除消息记录
     */
    @Override
    public int deleteMessage(ExceptionRequestCwms excMessgae) throws ServiceException {
        logUtils.isVerify(excMessgae.getUuid(), excMessgae.getExchange(), excMessgae.getRoutingKey());
        return exceptionRequestCwmsMapper.deleteByMessage(excMessgae.getUuid());
    }

    /**
     * 增加消息记录
     */
    @Override
    public int addMessage(ExceptionRequestCwms excMessgae) throws ServiceException {
        logUtils.isVerify(excMessgae.getUuid(), excMessgae.getExchange(), excMessgae.getRoutingKey());
        return exceptionRequestCwmsMapper.insertSelective(excMessgae);
    }

    /**
     * 功能描述: 根据UUID修改消息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/15 20:39
     */
    @Override
    public int updateMessage(ExceptionRequestCwms excMessgae) throws ServiceException {
        logUtils.isVerify(excMessgae.getUuid(), excMessgae.getExchange(), excMessgae.getRoutingKey());
        return exceptionRequestCwmsMapper.updateMessage(excMessgae);
    }

    /**
     * 功能描述: 根据UUID查询消息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/15 20:47
     */
    @Override
    public ExceptionRequestCwms selectExcepMessage(ExceptionRequestCwms excMessgae) {
        return exceptionRequestCwmsMapper.selectExceptionRequestCwms(excMessgae.getUuid());
    }
}
