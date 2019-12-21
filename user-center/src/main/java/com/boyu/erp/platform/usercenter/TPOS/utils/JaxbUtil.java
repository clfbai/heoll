package com.boyu.erp.platform.usercenter.TPOS.utils;

import com.boyu.erp.platform.usercenter.exception.ServiceException;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.rmi.ServerException;
import java.util.Collection;

//@Component
public class JaxbUtil {

    // 多线程安全的Context.
    private JAXBContext jaxbContext;

    /**
     * @param types 所有需要序列化的Root对象的类型.
     */
    public JaxbUtil(Class<?>... types) {
        try {
            jaxbContext = JAXBContext.newInstance(types);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

//    /**
//     * @param obj 所有需要序列化的Root对象的类型.
//     */
//    public void initjaxbContext(Object obj) throws RuntimeException, JAXBException {
//        this.jaxbContext = JAXBContext.newInstance(obj.getClass());
//    }

    /**
     * xml文件配置转换为对象
     *
     * @param xmlPath xml文件路径
     * @param clazz   java对象.Class
     * @return java对象
     * @throws JAXBException
     * @throws IOException
     */
    public Object xmlToBean(String xmlPath, Class<?> clazz) throws JAXBException, IOException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Object object = unmarshaller.unmarshal(new File(xmlPath));
        return object;
    }


    /**
     * Java Object->Xml.
     */
    public String toXml(Object root, String encoding) {
        try {
            StringWriter writer = new StringWriter();
            createMarshaller(encoding).marshal(root, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 去掉多余头部信息
     */
    public static String xmlCase(String xmlobj) {
        //这里后期可以更改为动态想要删除的头部信息
        String str = " standalone=\"yes\"";
        if (xmlobj.indexOf(str) > 0) {
            xmlobj = xmlobj.replaceAll(str, "");
        }
        return xmlobj;
    }


    /**
     * Java Object->Xml, 特别支持对Root Element是Collection的情形.
     */
    public String toXml(Collection root, String rootName, String encoding) throws ServerException, ServiceException {
        try {
            CollectionWrapper wrapper = new CollectionWrapper();
            wrapper.collection = root;
            JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(
                    new QName(rootName), CollectionWrapper.class, wrapper);
            StringWriter writer = new StringWriter();
            createMarshaller(encoding).marshal(wrapperElement, writer);

            return writer.toString();
        } catch (JAXBException e) {
            throw new ServiceException("403", "XMl转JAVABean异常信息：" + e);
        }
    }

    /**
     * Xml->Java Object.
     */
    @SuppressWarnings("unchecked")
    public <T> T fromXml(String xml) throws RuntimeException, JAXBException {
        StringReader reader = new StringReader(xml);
        return (T) createUnmarshaller().unmarshal(reader);
    }

    /**
     * Xml->Java Object, 支持大小写敏感或不敏感.
     */
    @SuppressWarnings("unchecked")
    public <T> T fromXml(String xml, boolean caseSensitive) {
        try {
            String fromXml = xml;
            if (!caseSensitive)
                fromXml = xml.toLowerCase();
            StringReader reader = new StringReader(fromXml);
            return (T) createUnmarshaller().unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建Marshaller, 设定encoding(可为Null).
     */
    public Marshaller createMarshaller(String encoding) {
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            if (null != encoding && !"".equals(encoding)) {
                marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            }
            return marshaller;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建UnMarshaller.
     */
    public Unmarshaller createUnmarshaller() {
        try {
            return jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 封装Root Element 是 Collection的情况.
     */
    public static class CollectionWrapper {
        @XmlAnyElement
        protected Collection collection;
    }


//    public static void main(String[] args) throws Exception {
//        EntryOrderModel userModel = new EntryOrderModel();
//        EntryOrderCreate es = new EntryOrderCreate();
//        OrderLineCreate orderLine = new OrderLineCreate();
//        OrderLineCreate o22 = new OrderLineCreate();
//        orderLine.setItemId("prod_id_1");
//        o22.setItemId("prod_id_22");
//        List<OrderLineCreate> lines = new ArrayList<>();
//        lines.add(orderLine);
//        lines.add(o22);
//        userModel.setEntryOrder(es);
//        OrderCrate lineCreate = new OrderCrate();
//        lineCreate.setOrderLine(lines);
//        userModel.setOrderLines(lineCreate);
//        JaxbUtil jaxbUtil = new JaxbUtil();
//        jaxbUtil.initjaxbContext(userModel);
//        String xmlobj = xmlCase(jaxbUtil.toXml(userModel, "utf-8"));
//
//        System.out.println(xmlobj);
//        String sign = DigestUtils.md5DigestAsHex(xmlobj.getBytes(Charset.forName("UTF-8")));
//        String ss = EncryptUtil.getMD5(xmlobj);
//        //String s = DigestUtils
//        System.out.println(ss);
//        System.out.println(sign);
//        //转码
//        String sd = URLEncoder.encode(DateUtil.dateToString(new Date()), "UTF-8");
//        System.out.println(sd);
//        //解码
//        String fullUrl = java.net.URLDecoder.decode("2019-10-17+14%3A26%3A39", "UTF-8");
//        System.out.println(fullUrl);
//        UserModel ml = new UserModel();
//        jaxbUtil.initjaxbContext(ml);
//        ml.setUnitId(1L);
//
//
//        String smm = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
//                "<request>\n" +
//                "    <unitId>100</unitId>\n" +
//                "    <userId>1</userId>\n" +
//                "</request>";
//        String fos = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><request><deliveryOrder><deliveryOrderCode>YGXX89925909</deliveryOrderCode><orderType>PTCK</orderType><warehouseCode>WO1</warehouseCode><logisticsCode>SF</logisticsCode><senderInfo/><receiverInfo><name>冯金林</name><mobile>13888888888</mobile><countryCode>中国</countryCode><province>江苏省</province><city>南京市</city><area>江宁区</area><detailAddress>建邺区</detailAddress></receiverInfo></deliveryOrder><orderLines><orderLine><orderLineNo>1</orderLineNo><ownerCode>lt</ownerCode><itemCode>01.01.001006091</itemCode><itemName>000001</itemName><inventoryType>ZP</inventoryType><planQty>3</planQty></orderLine><orderLine><orderLineNo>2</orderLineNo><ownerCode>lt</ownerCode><itemCode>01.01.001006091</itemCode><itemName>000002</itemName><inventoryType>ZP</inventoryType><planQty>3</planQty></orderLine></orderLines></request>";
//
//        String yy = jaxbUtil.toXml(ml, "utf-8");
//
//        System.out.println(yy);
//        Class<UserModel> kk = UserModel.class;
//
//        ;
//        jaxbUtil.initjaxbContext(UserModel.class.newInstance());
//        UserModel kks = jaxbUtil.fromXml(smm);
//        //kkk = jaxbUtil.fromXml(smm);
//
//        System.out.println(kks);
//
//    }


}