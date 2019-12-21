package com.boyu.erp.platform.usercenter.TPOS.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BoyuSignUtils {


    /**
     * 把二进制转换成大写的十六进制
     *
     * <p>
     * Description:[方法功能中文描述]
     * </p>
     *
     * @return 返回值说明
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
    /**
     * 功能描述: 将请求的URL 分割
     *
     * @param url (请求的URL)
     * @return:
     * @auther: CLF
     * @date: 2019/11/21 14:19
     */
    public static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> requestParams = null;
        try {
            requestParams = new HashMap<String, String>();
            //解码
            String fullUrl = URLDecoder.decode(url, "UTF-8");
            String[] urls = fullUrl.split("\\?");
            if (urls.length == 2) {
                String[] paramArray = urls[1].split("&");
                for (String param : paramArray) {
                    String[] params = param.split("=");
                    if (params.length == 2) {
                        requestParams.put(params[0], params[1]);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            requestParams = null;
        }
        return requestParams;
    }
    /**
     * 使用加密算法进行加密（目前仅支持md5算法）
     *
     * <p>
     * Description:[方法功能中文描述]
     * </p>
     *
     * @return 返回值说明
     */
    private static byte[] digest(String message) {
        try {
            MessageDigest md5Instance = MessageDigest.getInstance("MD5");
            md5Instance.update(message.getBytes("UTF-8"));
            return md5Instance.digest();
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 根据请求URL分割后参数、请求体、appkey生成签名
     */
    public static String sign(Map<String, String> params, String xml, String appkey) {
        // 1. 第一步，确保参数已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 2. 第二步，把所有参数名和参数值拼接在一起(包含body体)
        String joinedParams = joinRequestParams(params, xml, appkey, keys);
        System.out.println("解密后参数" + joinedParams);
        // 3. 第三步，使用加密算法进行加密（目前仅支持md5算法)
        String signMethod = params.get("sign_method");
        if (!"md5".equalsIgnoreCase(signMethod)) {
            // TODO
            return null;
        }
        byte[] abstractMesaage = digest(joinedParams);
        // 4. 把二进制转换成大写的十六进制
        String sign = byte2Hex(abstractMesaage);
        return sign;
    }


    /**
     * 把所有参数名和参数值拼接在一起(包含body体)
     *
     * <p>
     * Description:[方法功能中文描述]
     * </p>
     *
     * @param secretKey 参数说明
     * @param params    参数
     * @param body      请求体
     * @param sortedKes 排序后参数
     * @return 返回值说明
     */
    private static String joinRequestParams(Map<String, String> params, String body, String secretKey,
                                            String[] sortedKes) {
        StringBuilder sb = new StringBuilder(secretKey); // 前面加上secretKey
        for (String key : sortedKes) {
            if ("sign".equals(key)) {
                continue; // 签名时不计算sign本身
            } else {
                String value = params.get(key);
                if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                    sb.append(key).append(value);
                }
            }
        }
        // 拼接body
        sb.append(body);
        // 最后加上appKey
        sb.append(secretKey);
        return sb.toString();
    }

}
