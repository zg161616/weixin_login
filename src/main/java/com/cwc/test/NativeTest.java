package com.cwc.test;


public class NativeTest {
    public native  void hello(String name);
    static{
        System.loadLibrary("NativeTestImpl");
    }

    public static void main(String[] args) {
        new NativeTest().hello("cwc");
    }
}
