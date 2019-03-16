package com.changhf.utils.http;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author changhuafeng
 * @version V1.0.0
 * @since 2019/3/16
 */
@Slf4j
public class HttpClientUtil {
    public static void main(String[] args) throws Exception {
//        String html = get("http://www.chery.cn/vehicles/tiggo8#page-1");
//        String html = get("http://www.chery.cn/assets/models/360/tiggo8/outer/exterier-white.html");
        String html = post("http://localhost:8080/httpTest");
//        String html = httpPostWithJSON("http://localhost:8080/httpPostWithJson");
        System.out.println(html);
    }

    public static String get(String url) throws IOException {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
        //建造者模式
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity);
        response.close();
        httpClient.close();
        return html;
    }

/**
 * 表单提交
 *
 * @param url
 * @return
 */
public static String post(String url) throws Exception {
    //和上面一样，也可以通过build创建
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost(url);
    CloseableHttpResponse response = null;
    List<NameValuePair> list = Lists.newArrayList();
    list.add(new BasicNameValuePair("name", "张三"));
    list.add(new BasicNameValuePair("pwd", "123321"));
    log.info(JSONObject.toJSONString(list));
    //需要把表单包装到entity中
    StringEntity formEntity = new UrlEncodedFormEntity(list, "UTF-8");
    httpPost.setEntity(formEntity);

    //设置请求和传输超时时间
    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
    httpPost.setConfig(requestConfig);

    response = httpClient.execute(httpPost);
    HttpEntity entity = response.getEntity();

    String result = EntityUtils.toString(entity, "UTF-8");
    response.close();
    httpClient.close();
    return result;
}

    /**
     * json参数提交
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String httpPostWithJSON(String url) throws Exception {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String respContent = null;

        //封装json参数
        //{"endpoint":"abd.cn-beijing.abc.com","reportData":{"metricName":"test","groupId":"101","values":{"value":10.5},"dimensions":{"sampleName1":"value1","sampleName2":"value2"}},"acckey":"something","secret":"something"}
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("endpoint", "abd.cn-beijing.abc.com");
        jsonParam.put("acckey", "something");
        jsonParam.put("secret", "something");

        JSONObject paramVar = new JSONObject();
        paramVar.put("groupId", "101");
        paramVar.put("metricName", "test");
        paramVar.put("groupId", "101");

        JSONObject dimensions = new JSONObject();
        dimensions.put("sampleName1", "value1");
        dimensions.put("sampleName2", "value2");
        paramVar.put("dimensions", dimensions);

        JSONObject values = new JSONObject();
        values.put("value", 10.5);
        paramVar.put("values", values);

        jsonParam.put("reportData", paramVar);
        //解决中文乱码问题
        StringEntity entity = new StringEntity(jsonParam.toString(), "UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        log.info(JSONObject.toJSONString(jsonParam));

        HttpResponse resp = client.execute(httpPost);
        if (resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he, "UTF-8");
        }
        return respContent;
    }



}
