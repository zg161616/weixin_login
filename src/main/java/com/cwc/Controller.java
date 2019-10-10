package com.cwc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cwc.model.User;
import com.cwc.service.UserService;
import com.jfinal.aop.Inject;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.MultipartRequest;
import com.jfinal.upload.UploadFile;
import org.apache.http.protocol.HTTP;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.ServletInputStream;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    public void debug(){
        String data  = HttpKit.readData(getRequest());
        Map<String,String> _map = new HashMap<>();
        HttpKit.get(getRequest().getRequestURL().toString(),_map);
        System.out.println();
    }


    public  void index() {
        if(StrKit.isBlank(WXUtil.TOKEN)){
            WXUtil.getToken();
        }
        String data = HttpKit.readData(getRequest());
        Message message = new Message();
        try {
            Document document = DocumentHelper.parseText(data);
            message = DomUtil.DomToObject(document);
            Document reply =  replyMsg(message);
            System.out.println(reply.asXML().substring(39));
            renderJson(reply.asXML().substring(39));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
//
    public Document replyMsg(Message m){
    Document content = DocumentHelper.createDocument();
    Element root = content.addElement("xml");
        switch (m.getMsgType()){
            case Message.MSGTYPE_TEXT:
                m = (TextMessage)m;
                root.addElement("ToUserName").addText(m.getFromUserName());
                root.addElement("FromUserName").addText(m.getToUserName());
                root.addElement("CreateTime").addText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                scanText(((TextMessage) m).getContent(),root);
                break;
            case Message.MSGTYPE_IMAGE:
                m = (ImageMessage)m;
                root.addElement("ToUserName").addText(m.getFromUserName());
                root.addElement("FromUserName").addText(m.getToUserName());
                root.addElement("CreateTime").addText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                root.addElement("MsgType").addText("image");
                Element info = root.addElement("Image");
                info.addElement("MediaId").addText(WXUtil.getMedia("image"));
                break;
        }
        return content;
    }

        public void scanText(String text ,Element root){
            if(text.contains("video")){
                root.addElement("MsgType").addText("video");
               Element info =  root.addElement("Video");
               info.addElement("MediaId").addText(WXUtil.getMedia("video"));
               info.addElement("Title").addText("test");
               info.addElement("Description").addText("test");
            }
            else if(text.contains("music")){
                root.addElement("MsgType").addText("music");
                Element info =  root.addElement("Music");
                info.addElement("Title").addText("");
                info.addElement("Description").addText("");
                info.addElement("MusicUrl").addText("");
                info.addElement("HQMusicUrl").addText("");
                info.addElement("ThumbMediaId").addText(WXUtil.getMedia("image"));
            }
            else if(text.contains("voice")){
                root.addElement("MsgType").addText("voice");
                Element info = root.addElement("Voice");
                info.addElement("MediaId").addText(WXUtil.getMedia("voice"));
            }
            else if(text.contains("articles")){
                root.addElement("MsgType").addText("news");
                root.addElement("ArticleCount").addText("1");
                Element info = root.addElement("Articles");
                Element item = info.addElement("item");
                item.addElement("Title").addText("");
                item.addElement("Description").addText("");
                item.addElement("PicUrl").addText("");
                item.addElement("Url").addText("");
            }
            else{
                root.addElement("MsgType").addText("text");
                root.addElement("Content").addText("hello");
            }


        }
//    public void index() {
//        js_code = get("code");
//        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+sercret+"&grant_type=authorization_code"+"&js_code="+js_code;
//        try {
//            Object result = HttpRequestUtils.httpGet(url);
//            JSONObject jsonObject = JSON.parseObject(result.toString());
//            if(jsonObject.get("errcode")!=null){
//                int errcode = jsonObject.getInteger("errcode");
//                throw new Exception(errcode+"");
//            }
//            openId = jsonObject.getString("openid");
//            session_key = jsonObject.getString("session_key");
//            if(!userService.findUser(openId)){
//            User user = new User();
//            user.setOpenid(openId);
//            user.setSessionKey(session_key);
//            user.save();
//            }
//            renderJson(openId);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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

//    public void getUserInfo() {
//        String result = "";
//        try {
//            BufferedReader in = null;
//            if(StrKit.isBlank(access_token)||StrKit.isBlank(openId)){
//                return;
//            }
//            URL url = new URL("https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN");
//            URLConnection connection = url.openConnection();
//            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//            in.close();
//            user_info = result;
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
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
        tmp.put("session_key",sessionKey);
        User customer = userService.getUser(openid);
        if(customer!=null){
            customer.setSessionKey(sessionKey);
            customer.update();
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
                getResponse().getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void updateSessionKey(){
        String code = get("code");
        JSONObject jsonObject =  getUnionId(code);
        String openid = jsonObject.getString("openid");
        String session_key = jsonObject.getString("session_key");
        User user = userService.getUser(openid);
        if(user==null){
            return;
        }
        user.setSessionKey(session_key);
        try {
            JSONObject object = new JSONObject();
            object.put("update",user.update());
            object.put("session_key",user.getSessionKey());
            getResponse().getWriter().print(object);
            getResponse().getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testFile()
    {
        File file = new File("D://logo.png");
        renderFile(file);
    }

    public void uploadFile(){
        MultipartRequest request =new MultipartRequest(getRequest());
        List<UploadFile> files = request.getFiles();
        try {
            byte[] buffer = new byte[1024*100];
            InputStream inputStream = new FileInputStream(files.get(0).getFile());
            inputStream.read(buffer);
            File file = new File("D://a.png");
            if(!file.exists()){
            file.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(buffer);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

