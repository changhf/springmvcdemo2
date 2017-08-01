package com.changhf.utils.http;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @Date: 2015年12月17日
 * @Author: wwei
 * @Copyright: 版权所有 (C) 2015 中国移动 杭州研发中心.
 * @Description: DES加密/解密
 * @注意：本内容仅限于中国移动内部传阅，禁止外泄以及用于其他的商业目的
 */
public class DesUtils {

    /** 加密算法,可用 DES,DESede,Blowfish. */
    private final static String ALGORITHM = "DES";
    private final static String DEFAULT_DES_KEY = "hiifit~@";

    public static void main(String[] args) throws Exception {
        String text = "jlkasffdspfk阿斯达";
        String str = DesUtils.encrypt(text);
        System.out.println("str: " + str);
        str = DesUtils.decrypt(str, "hiifit~@");
        System.out.println("str: " + str);
    }

    /**
     * 对数据进行DES加密. 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public final static String decrypt(String data) throws Exception {
        return new String(decrypt(hex2byte(data.getBytes("UTF-8")), DEFAULT_DES_KEY.getBytes("UTF-8")), "UTF-8");
    }

    /**
     * 对用DES加密过的数据进行解密. 
     * @param data
     * @return
     * @throws Exception
     */
    public final static String encrypt(String data) throws Exception {
        return byte2hex(encrypt(data.getBytes("UTF-8"), DEFAULT_DES_KEY.getBytes("UTF-8")));
    }

    /**
     * 对数据进行DES加密. 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public final static String decrypt(String data, String key) throws Exception {
        return new String(decrypt(hex2byte(data.getBytes("UTF-8")), key.getBytes("UTF-8")), "UTF-8");
    }

    /**
     * 对用DES加密过的数据进行解密. 
     * @param data
     * @return
     * @throws Exception
     */
    public final static String encrypt(String data, String key) throws Exception {
        return byte2hex(encrypt(data.getBytes("UTF-8"), key.getBytes("UTF-8")));
    }

    /**
     * 用指定的key对数据进行DES加密. 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源  
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象  
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成  
        // 一个SecretKey对象  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作  
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象  
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密  
        // 正式执行加密操作  
        return cipher.doFinal(data);
    }

    /**
     * 用指定的key对数据进行DES解密. 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源  
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象  
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成  
        // 一个SecretKey对象  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作  
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象  
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密  
        // 正式执行解密操作  
        return cipher.doFinal(data);
    }

    private static byte[] hex2byte(byte[] b) throws UnsupportedEncodingException {
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2, "UTF-8");
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer("");
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }
}
