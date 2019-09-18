package com.cwc;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author bwh
 * @date 2019/9/17/017 - 10:31
 * @Description
 */
public class CheckUtil   {
    private static String token = "7200";
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        String[] arr = {signature,timestamp,nonce};
        sort(arr);
        StringBuffer content = new StringBuffer();
        for (int i=0;i<arr.length;i++){
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try{
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        content = null;
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()):false;
    }

    private static String byteToStr(byte[] byteArray){
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++){
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    private static String byteToHexStr(byte mByte){
        char[] Digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] tempArr =  new char[2];
        tempArr[0] = Digit[(mByte >>> 4)& 0x0f];
        tempArr[1] = Digit[mByte & 0x0f];
        String s = new String(tempArr);
        return s;
    }

    public static void sort(String[] arr){
        for (int i = 0; i < arr.length-1; i++){
            for (int j = i + 1; j < arr.length; j++){
                if (arr[j].compareTo(arr[i])<0){
                    String temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
