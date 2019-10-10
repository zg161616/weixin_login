package com.cwc;

import org.dom4j.Document;
import org.dom4j.Element;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author bwh
 * @date 2019/9/27/027 - 16:37
 * @Description
 */
public class DomUtil {
    public static <T> T DomToObject(Document document){
        try {
            Element root = document.getRootElement();
            String msgType = upperLetter(root.element("MsgType").getText());
            Class<?> classType =  Class.forName("com.cwc."+msgType + "Message");
            Object o = classType.newInstance();
            List<Element> elements =root.elements();
            for (Element element : elements) {
                PropertyDescriptor pd  = new PropertyDescriptor(lowwerLetter(element.getName()),classType);
                Method wM = pd.getWriteMethod();
                wM.invoke(o, element.getText());
            }
            return (T)o;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String lowwerLetter(String str){
        return str.substring(0,1).toLowerCase()+str.substring(1);
    }
    public static String upperLetter(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

}
