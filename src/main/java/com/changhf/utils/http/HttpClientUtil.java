package com.changhf.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author changhuafeng
 * @version V1.0.0
 * @since 2019/3/16
 */
public class HttpClientUtil {
    public static void main(String[] args) {
//        String html = get("http://www.chery.cn/vehicles/tiggo8#page-1");
        String html = get("http://www.chery.cn/assets/models/360/tiggo8/outer/exterier-white.html");
        System.out.println(html);
    }
    public static String get(String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity);
            return html;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
