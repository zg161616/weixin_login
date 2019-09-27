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
    public static <T> T DomToObject(Document document,Class<T> classType){
        try {
            Object o= classType.newInstance();
            Field[] fields = classType.getDeclaredFields();
            List<Element> elements = document.getRootElement().elements();
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
        }
        return (T)new Object();
    }


    public static String lowwerLetter(String str){
        return str.substring(0,1).toLowerCase()+str.substring(1);
    }

}
