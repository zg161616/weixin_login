package com.cwc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cwc.model.User;
import com.cwc.service.UserService;
import com.jfinal.aop.Inject;
import com.jfinal.kit.StrKit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author cwc
 * @date 2019/9/16/016 - 11:09
 * @Description
 */
public class Controller extends com.jfinal.core.Controller {
    @Inject
    UserService userService;
    private String appId = "wx166aa584e2848746";
    private String sercret = "43069a916fda47dbc1ab04f9099bf78d";
    private String redirect_url = "https://cwc.easy.echosite.cn";
    private String scope = "snsapi_userinfo";
    private String openId = "";
    private String session_key = "";
    private String code = "";
    private String js_code = "";
    private String access_token = "";
    private String user_info = "";

//    public void index(){
//        String echostr = get("echostr");
//        String jsonstr = HttpKit.readData(getRequest());
//        Message message = new Message();
//        try {
//            Document document = DocumentHelper.parseText(jsonstr);
//            Element rootElement = document.getRootElement();
//            List<Element> elements = rootElement.elements();
//            Map<String,String> map = new HashMap<>();
//            for (Element element : elements) {
//                map.put(element.getName(),element.getText());
//            }
//            if(map.get("ToUserName")!=null){
//                message.setToUserName(map.get("ToUserName"));
//            }
//            if(map.get("FromUserName")!=null){
//                message.setFromUserName(map.get("FromUserName"));
//            }
//            if(map.get("CreateTime")!=null){
//                message.setCreateTime(map.get("CreateTime"));
//            }
//            if(map.get("MsgType")!=null){
//                message.setMsgType(map.get("MsgType"));
//            }
//            message.setFromUserName(elements.get(1).getText());
//            message.setCreateTime(elements.get(2).getText());
//            message.setMsgType(elements.get(3).getText());
//            message.setContent(elements.get(4).getText());
//            message.setMsgId(elements.get(5).getText());
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        System.out.println(getRequest().getParameterMap());
//        renderText("hello the world");
//    }


    public void index() {
        js_code = get("code");
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+sercret+"&grant_type=authorization_code"+"&js_code="+js_code;
        try {
            Object result = HttpRequestUtils.httpGet(url);
            JSONObject jsonObject = JSON.parseObject(result.toString());
            if(jsonObject.get("errcode")!=null){
                int errcode = jsonObject.getInteger("errcode");
                throw new Exception(errcode+"");
            }
            openId = jsonObject.getString("openid");
            session_key = jsonObject.getString("session_key");
            if(!userService.getUser(openId)){
            User user = new User();
            user.setOpenid(openId);
            user.setSessionKey(session_key);
            user.save();
            }
            renderJson(openId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getUnionId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+sercret+"&grant_type=authorization_code"+"&js_code="+code;
        String openId = "";
        JSONObject object = new JSONObject();
        try {
            Object result = HttpRequestUtils.httpGet(url);
            JSONObject jsonObject = JSON.parseObject(result.toString());
            if(jsonObject.get("errcode")!=null){
                int errcode = jsonObject.getInteger("errcode");
                throw new Exception(errcode+"");
            }
            object = jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }



//    public void getOpenId() {
//        BufferedReader in = null;
//        String result = "";
//        if (StrKit.isBlank(code)) {
//            return;
//        }
//        try {
//            URL url = new URL("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + sercret + "&code=" + code + "&grant_type=authorization_code");
//            URLConnection connection = url.openConnection();
//            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//            in.close();
//            JSONObject jsonObject = (JSONObject)JSONObject.parse(result);
//            access_token = jsonObject.getString("access_token");
//            openId = jsonObject.getString("openid");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void getUserInfo() {
        String result = "";
        try {
            BufferedReader in = null;
            if(StrKit.isBlank(access_token)||StrKit.isBlank(openId)){
                return;
            }
            URL url = new URL("https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN");
            URLConnection connection = url.openConnection();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            in.close();
            user_info = result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(){
        String name = get("name");
        String pwd = get("pwd");
        String code = get("code");
        JSONObject jsonObject = getUnionId(code);
        String openid = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");
        User user = new User();
        user.setOpenid(openid);
        user.setSessionKey(sessionKey);
        user.setName(name);
        user.setPwd(pwd);
        JSONObject  tmp = new JSONObject();
        if(userService.getUser(openid)){
            tmp.put("msg","已经注册过");
            tmp.put("code",1);
            renderJson(tmp);
            return;
        }
        if(user.save()){
            tmp.put("msg","注册成功");
            tmp.put("code",0);
        }
        else{
            tmp.put("msg","注册失败");
            tmp.put("code",1);
        }
        renderJson(tmp);
    }

    public void login(){
        String name = get("name");
        String pwd = get("pwd");
        boolean login = userService.login(name, pwd);
            try {
                getResponse().getWriter().print(login);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
