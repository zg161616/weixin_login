package com.cwc;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URLDecoder;
import java.util.Map;

/**
 * Created by xxwu on 2017/5/8.
 */
public class HttpRequestUtils {

    /**
     * httpPost
     *
     * @param url       路径
     * @param jsonParam 参数
     * @return
     */
    public static String httpPost(String url, String jsonParam) throws IOException {
        return httpPost(url, jsonParam, false,null);
    }

    /**
     * post请求
     *
     * @param url            url地址
     * @param jsonParam      参数
     * @param noNeedResponse 不需要返回结果
     * @return
     */
    public static String httpPost(String url, String jsonParam, boolean noNeedResponse, Map<String,String> _map) throws IOException {
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String jsonResult = null;
        HttpPost method = new HttpPost(url);
        for (String s : _map.keySet()) {
            method.setHeader(s,_map.get(s));
        }
        if (null != jsonParam) {
            //解决中文乱码问题
            StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            method.setEntity(entity);

        }
        HttpResponse result = httpClient.execute(method);
        url = URLDecoder.decode(url, "UTF-8");
        /**请求发送成功，并得到响应**/
        if (result.getStatusLine().getStatusCode() == 200) {
            String str = "";
            /**读取服务器返回过来的json字符串数据**/
            str = EntityUtils.toString(result.getEntity());
            if (noNeedResponse) {
                return null;
            }
            /**把json字符串转换成json对象**/
//                    jsonResult = JsonUtils.parse(str, String.class);
            jsonResult = str;
        }else {
            throw new IOException("post请求提交失败:" + url);
        }
        return jsonResult;
    }


    /**
     * 发送get请求
     *
     * @param url 路径
     * @return
     */
    public static String httpGet(String url) throws IOException{
        //get请求返回结果
        String jsonResult = null;
        DefaultHttpClient client = new DefaultHttpClient();
        //发送get请求
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        /**请求发送成功，并得到响应**/
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            /**读取服务器返回过来的json字符串数据**/
            String strResult = EntityUtils.toString(response.getEntity());
            /**把json字符串转换成json对象**/
//                jsonResult = JsonUtils.parse(strResult, String.class);
            jsonResult = strResult;
            url = URLDecoder.decode(url, "UTF-8");
        } else {
            throw new IOException("get请求提交失败:" + url);
        }
        return jsonResult;
    }


    public static String postXML(String url,String xml){
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;
        try{
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
            client = HttpClients.createDefault();
            StringEntity entityParams = new StringEntity(xml,"utf-8");
            httpPost.setEntity(entityParams);
            client = HttpClients.createDefault();
            resp = client.execute(httpPost);
            String resultMsg = EntityUtils.toString(resp.getEntity(),"utf-8");
            return resultMsg;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(client!=null){
                    client.close();
                }
                if(resp != null){
                    resp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



}
