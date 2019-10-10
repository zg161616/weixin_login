package com.cwc.test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * @author bwh
 * @date 2019/10/10/010 - 13:40
 * @Description
 */
public class NativeTest {
    public native  void hello();
    static{
        System.loadLibrary("Dll2");
    }
    public static void main(String[] args) {
        new NativeTest().hello();
    }



}
