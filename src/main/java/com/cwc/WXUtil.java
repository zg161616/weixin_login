package com.cwc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.sun.deploy.net.HttpUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bwh
 * @date 2019/9/30/030 - 9:54
 * @Description
 */
public class WXUtil {
    public static final String APPID = "wxc6080cdf86a883ab";
    public static final String SERCRET = "374c6b93c182bca6ad3d72783efa183e";
    public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    public static final String ADD_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";
    public static  String TOKEN  = "";

    public static void getToken(){
       String url =  String.format(ACCESS_TOKEN,APPID,SERCRET);
        try {
            JSONObject o = (JSONObject) JSONObject.parse(HttpRequestUtils.httpGet(url));
            TOKEN = o.getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMedia(String type){
        String s = String.format(ADD_MEDIA, TOKEN, type);
        String filepath = "D://logo.png";
        File file =new File(filepath);
        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\";filelength="+file.length()+"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        String data =fileToStr(file,"utf-8");
        sb.append(data);
        sb.append("\r\n----\r\n");
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","multipart/form-data;");
        String post = HttpKit.post(s, null, sb.toString(),headers);
        return post;
    }

    public static String doPost(String url,String filepath){
        try {
            File file = new File(filepath);
            URL urlConn = new URL(url);
            HttpURLConnection con  = (HttpURLConnection) urlConn.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            String BOUNDARY = "----------" + System.currentTimeMillis();
            con.setRequestProperty("Content-Type", "multipart/form-data");
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] headers = sb.toString().getBytes("utf-8");
            OutputStream out = con.getOutputStream();
            out.write(headers);
            DataInputStream in  = new DataInputStream(new FileInputStream(file));
            int len = 0;
            byte[] buffers = new byte[1024];
            while((len=in.read(buffers))!=-1){
                out.write(buffers,0,len);
            }
            in.close();
            byte[] foot = ("\r\n--" + "" + "--\r\n").getBytes("utf-8");//定义最后数据分隔线
            out.write(foot);

            out.flush();
            out.close();

            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;
            String result = null;
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while((line = reader.readLine())!=null){
                buffer.append(line);
            }
            result = buffer.toString();
            reader.close();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String  fileToStr(File file,String encoding){
        DataInputStream reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new DataInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int n = 0;
            while((n=reader.read(buffer))!=-1){
            reader.read(buffer,0,n);
            sb.append(new String(buffer));
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
