package com.changhf.plugin.sms;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.changhf.utils.MD5Utils;

/**
 * SmsAiPaiMessageClient
 * @author liMengbiao
 *
 */
public class SmsAiPaiMessageClient extends SmsClient {
    String requestUrl = "http://221.179.176.234:7880/hzSmsInterface/http/sendSms";

    private String sn;// 序列号

    private String pwd;// 密码

    /**
     * SmsAiPaiMessageClient
     * @param sn
     * @param pwd
     */
    public SmsAiPaiMessageClient(String sn, String pwd) {
        this.sn = sn;
        this.pwd = MD5Utils.createEncryption(sn + pwd);
    }

    /**
     * getMD5,不知道这里为什么不直接用Md5Utils
     * @param sourceStr
     * @return
     */
    public String getMD5(String sourceStr) {
        StringBuffer resultStr = new StringBuffer("");
        try {
            byte[] temp = sourceStr.getBytes("utf-8");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(temp);
            byte[] b = md5.digest();
            for (int i = 0; i < b.length; i++) {
                char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
                char[] ob = new char[2];
                ob[0] = digit[(b[i] >>> 4) & 0X0F];
                ob[1] = digit[b[i] & 0X0F];
                resultStr.append(new String(ob));
            }
            return resultStr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * sendSMS
     */
    public String sendSMS(String mobile, String content, String ext, String stime, String rrid, String msgfmt) {
        //创建HttpClientBuilder  
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        String result = null;
        try {
            System.out.println(content);
            String tempCont = URLEncoder.encode(content, "gb2312");
            HttpGet httpGet = new HttpGet(requestUrl + "?sn=" + sn + "&pwd=" + pwd + "&mobile=" + mobile + "&content=" + tempCont + "&ext=" + ext);
            //执行get请求  
            HttpResponse httpResponse;
            httpResponse = closeableHttpClient.execute(httpGet);
            //获取响应消息实体  
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流并释放资源  
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
