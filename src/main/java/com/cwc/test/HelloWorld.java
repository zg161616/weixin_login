package com.cwc.test;

/**
 * @author bwh
 * @date 2019/10/14/014 - 10:02
 * @Description
 */
public class HelloWorld {

    static{
        System.loadLibrary("HelloNative");
    }

    public native void hello();

    public static void main(String[] args) {
        new HelloWorld().hello();
    }
}
