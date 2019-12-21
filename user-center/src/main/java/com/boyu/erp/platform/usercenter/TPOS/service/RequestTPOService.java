package com.boyu.erp.platform.usercenter.TPOS.service;

import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;

/**
 * 通用请求C-WMS接口
 *
 * @author HHe
 * @date 2019/9/10 10:08
 */
public interface RequestTPOService {

    /**
     * ERP->WMS公共请求
     * 需要调用jaxbUtil吧对象转换成xml的String
     * JaxbUtil jaxbUtil = new JaxbUtil(Object.class);
     * String objXml = jaxbUtil.toXml(obj, "utf-8");
     *
     * @author HHe
     * @date 2019/9/10 16:41
     */
    <T> T requestCWMS2XMLStr(CwmsUrlParamModel cwmsUrlParamModel, Object objXml, Class<T> responseType) throws Exception;


    /**
     * 功能描述: 根生成URL
     *
     * @param cwmsUrlParamModel (发送整体对象)
     * @param uuid              (唯一键必传去重)
     * @return:
     * @auther: CLF
     * @date: 2019/11/8 15:50
     */
    <T> T createCwmsURL(CwmsUrlParamModel cwmsUrlParamModel, String uuid, Class<T> responseType) throws Exception;

    /**
     * 将javaBean 转为 XML String
     */
    String createObjXml(Object object) throws Exception;

    /**
     * 将XML String 转为  javaBean
     */
    <T> T  createStringXml(String xml, Class t) throws Exception;

}
