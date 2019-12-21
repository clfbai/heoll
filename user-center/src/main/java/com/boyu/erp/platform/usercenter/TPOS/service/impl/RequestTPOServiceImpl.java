package com.boyu.erp.platform.usercenter.TPOS.service.impl;

import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.TPOS.service.RequestTPOService;
import com.boyu.erp.platform.usercenter.TPOS.utils.JaxbUtil;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.utils.DateUtil;
import com.boyu.erp.platform.usercenter.utils.common.InterfaceLogUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * 通用请求C-WMS接口
 *
 * @author HHe
 * @date 2019/9/10 10:08
 */
@Slf4j
@Service
public class RequestTPOServiceImpl implements RequestTPOService {
//    @Autowired
//    private JaxbUtil jaxbUtil;
    @Autowired
    private InterfaceLogUtils logUtils;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${C-WMS_ADDR}")
    private String CWMSADDR;

    public static final String ENCODING = "UTF-8";

    /**
     * 公共请求
     * 需要调用jaxbUtil吧对象转换成xml的String
     * JaxbUtil jaxbUtil = new JaxbUtil(Object.class);
     * String objXml = jaxbUtil.toXml(obj, "utf-8");
     *
     * @author HHe
     * @date 2019/9/10 16:41
     */
    @Override
    public <T> T requestCWMS2XMLStr(CwmsUrlParamModel cwmsUrlParamModel, Object obj, Class<T> responseType) throws Exception {
        //时间戳
        String date = DateUtil.dateToString(new Date());
        String xml = createObjXml(obj);
        cwmsUrlParamModel.setObjXml(xml);
        //加密生成签名并赋值
        cwmsUrlParamModel.setSign(signMD5(createSign(cwmsUrlParamModel, date)));
        //生成路径
        String url = cretaURL(cwmsUrlParamModel, date);
        URI uri = cretaeURI(url);
        //发起请求
        return restTemplate.postForObject(uri, xml, responseType);
    }

    /**
     * 功能描述: 生成请求URL并发起请求
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/11 9:58
     */
    @Override
    public <T> T createCwmsURL(CwmsUrlParamModel cwmsUrlParamModel, String uuid, Class<T> responseType) throws Exception {
        if (logUtils.isSend(uuid)) {
            if (StringUtils.isBlank(cwmsUrlParamModel.getObjXml())) {
                throw new ServiceException("403", "请求xml不能为空");
            }
            String date = DateUtil.dateToString(new Date());
            cwmsUrlParamModel.setTimestamp(date);
            String sing =signMD5(createSign(cwmsUrlParamModel, date));
            cwmsUrlParamModel.setSign(sing);
            System.out.println("生成签名：" + cwmsUrlParamModel.getSign());
            //生成路径
            String URL = cretaURL(cwmsUrlParamModel, date);
            HttpHeaders header = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("text/xml; charset=UTF-8");
            // 需求需要传参为form-data格式
            header.setContentType(type);
            header.add("Accept", MediaType.APPLICATION_XML.toString());
            //请求体
            HttpEntity<String> formEntity = new HttpEntity<String>(cwmsUrlParamModel.getObjXml(), header);
            return restTemplate.postForObject(cretaeURI(URL), formEntity, responseType);
        }
        return null;
    }

    /**
     * 功能描述: java对象生成XML格式String
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/11 19:22
     */
    @Override
    public String createObjXml(Object object) throws Exception {
        JaxbUtil jaxbUtil = new JaxbUtil(object.getClass());
//        jaxbUtil.initjaxbContext(object.getClass());
        return jaxbUtil.toXml(object, "UTF-8");
    }

    @Override
    public <T> T createStringXml(String string, Class t) throws Exception {
        JaxbUtil jaxbUtil = new JaxbUtil(t);
//        jaxbUtil.initjaxbContext(t);
        return jaxbUtil.fromXml(string);

    }


    //绑定URL
    public URI cretaeURI(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.build(true).toUri();
        return uri;
    }

    /**
     * 功能描述: 按照C-WMS 制定规则生成需要加密字签名字符串
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/17 11:47
     */
    public String createSign(CwmsUrlParamModel cwmsUrlParamModel, String date) {
        if (StringUtils.isBlank(cwmsUrlParamModel.getSecret()))
            throw new ServiceException("403", "请检查传入加密头部信息是否为空 cwmsUrlParamModel.getSecret() null");
        if (StringUtils.isBlank(cwmsUrlParamModel.getObjXml()))
            throw new ServiceException("403", "请检查传入Xml对象是否为空 cwmsUrlParamModel.getObjXml() null");
        String sign = cwmsUrlParamModel.getSecret() + "app_key" + cwmsUrlParamModel.getAppKey() + "customerId" + cwmsUrlParamModel.getCustomerid() +
                "format" + cwmsUrlParamModel.getFormat() + "method" + cwmsUrlParamModel.getMethod() +
                "sign_method" + cwmsUrlParamModel.getSignMethod() + "timestamp" + date +
                "v" + cwmsUrlParamModel.getV() + cwmsUrlParamModel.getObjXml() + cwmsUrlParamModel.getSecret();
        return sign;
    }

    /**
     * 功能描述:生成调用URL
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/17 11:16
     */
    public String cretaURL(CwmsUrlParamModel cwmsUrlParamModel, String date) throws ServiceException, UnsupportedEncodingException {
        if (StringUtils.isBlank(cwmsUrlParamModel.getSign())) {
            throw new ServiceException("403", "签名为空");
        }
        //生成URL
        String url = "method=" + cwmsUrlParamModel.getMethod() + "&timestamp=" + date +
                "&format=" + cwmsUrlParamModel.getFormat() + "&app_key=" + cwmsUrlParamModel.getAppKey() + "&v=" + cwmsUrlParamModel.getV() +
                "&sign=" + cwmsUrlParamModel.getSign() + "&sign_method=" + cwmsUrlParamModel.getSignMethod() + "&customerId=" + cwmsUrlParamModel.getCustomerid();
        //转码
        String uri = CWMSADDR + cwmsUrlParamModel.getRequestMapping() + "?" + URLEncoder.encode(url, "UTF-8");
        log.info("发送生成URL:" + uri);
        return uri;
    }


    /**
     * 功能描述:
     *
     * @param rest   (加密字生产大写符串)
     * @param encode (字符串加密 字符集格式)
     * @return:
     * @auther: CLF
     * @date: 2019/10/17 11:09
     */
    public String signMD5(String rest, String encode) {
        return DigestUtils.md5DigestAsHex(rest.getBytes(Charset.forName(encode))).toUpperCase();
    }

    /**
     * 功能描述:
     *
     * @param rest (加密字生产大写符串) 默认加密格式为  UTF-8
     * @return:
     * @auther: CLF
     * @date: 2019/10/17 11:09
     */
    public static String signMD5(String rest) {

        return DigestUtils.md5DigestAsHex(rest.getBytes(Charset.forName(ENCODING))).toUpperCase();
    }

    // 经过测试  下面转大写 和上面生成字符串后转达大写 加密结果是一致的 到测试时不一致将加密改为下面方式

    /**
     * 功能描述: c-wms 加密
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/17 12:27
     */
    public static byte[] getByte(String message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5Instance = MessageDigest.getInstance("MD5");
        md5Instance.update(message.getBytes(ENCODING));
        return md5Instance.digest();
    }

    /**
     * 转大写
     */
    private static String byte2Hex(byte[] bytes) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int j = bytes.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (byte byte0 : bytes) {
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }


}
