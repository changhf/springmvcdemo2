package com.changhf.service;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * 私钥+明文密码=加密密码；加密密码+公钥=明文密码
 */
public class DruidTest {
    public static void main(String[] args) throws Exception {
        // 密码明文
        String password = "1q2w3e4r";
        System.out.println("密码[ " + password + " ]的加密信息如下：\n");
        String[] keyPair = ConfigTools.genKeyPair(512);
        // 私钥
        String privateKey = keyPair[0];
        // 公钥
        String publicKey = keyPair[1];
        // 用私钥加密后的密文
        password = ConfigTools.encrypt(privateKey, password);

        System.out.println("privateKey:" + privateKey);
        System.out.println("publicKey:" + publicKey);
        System.out.println("password:" + password);
        String decryptPassword = ConfigTools.decrypt(publicKey, password);
        System.out.println("decryptPassword：" + decryptPassword);

    }
}