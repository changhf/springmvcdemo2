/*
 *@Project: GZJK
 *@Author: sen
 *@Date: 2015年6月1日
 *@Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.changhf.utils.http;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.util.StringUtils;

/** 
* @ClassName: Base64Util 
* @Description: TODO
* @author 58336
* @date 2015年6月1日 上午9:50:04 
*/
public class Base64Util {
    /**
     * 解密参数
     * @param behaviorInfo
     * @return
     */
    public static String decodeParam(String deviceId) {
        if (StringUtils.isEmpty(deviceId)) {
            return null;
        }
        try {
            //1、解密
            Decoder decoder = Base64.getDecoder();
            byte[] b = decoder.decode(deviceId);
            return new String(b, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * encode
     * @param deviceId
     * @return
     */
    public static String encode(String deviceId) {
        if (StringUtils.isEmpty(deviceId)) {
            return null;
        }
        try {
            // 1、加密
            Encoder encoder = Base64.getEncoder();
            byte[] b = encoder.encode(deviceId.getBytes());
            return new String(b, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    /*public static final void main(String []args) {
    	String s = "SU1FST1BNUFBNUVEMi1GRkRGLTQzMUItOTI1Ri0zQTczRkM0OEZDMzR8VkVSU0lPTj0yLjIuMXxTTkFNRT3kuK3lm73np7vliqh8REVWSUNFPWlQaG9uZTcsMnxPU1ZF";
    	System.out.println(decodeParam(s));
    	String []ss = decodeParam(s).split("\\|");
    	String version = null;
    	for(int i=0;i<ss.length;i++) {
    		if(ss[i].startsWith("VERSION=")) {
    			version = ss[i];
    		}
    	}
    	System.out.println(version);
    	System.out.println(version.substring(8,11));
    	BigDecimal bigVersion = new BigDecimal(version.substring(8,11)).setScale(2, BigDecimal.ROUND_HALF_UP);
    	if(bigVersion.compareTo(new BigDecimal("2.9")) == 1) {
    		System.out.println("新版本");
    	}else{
    		System.out.println("老版本");
    	}
    	
    	System.out.println(encode("IMEI=A5AA5ED2-FFDF-431B-925F-3A73FC48FC34|VERSION=2.8.0|SNAME=中国移动|DEVICE=iPhone7,2|OSVE"));
    }*/
}
