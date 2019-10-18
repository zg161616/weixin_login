package com.cwc.test;


import java.math.BigInteger;

public class NativeTest extends  test{
    public native  String hello(byte[] bytes);
    public native int intTest();
    static{
        System.loadLibrary("NativeTest");
    }

    public  int size(){
        return super.size();
    };
    public static void main(String[] args) {
        int offset = 5;
        int number =65536;
//        System.out.println(String.format("%032d",Long.parseLong(Integer.toBinaryString(number))));
//        System.out.println(Integer.toBinaryString(~(1<<offset)));
//        System.out.println(String.format("%032d",Long.parseLong(Integer.toBinaryString(1<<offset))));
//        System.out.println(String.format("%032d",Long.parseLong(Integer.toBinaryString(number&~(1<<offset)))));
//        System.out.println(String.format("%032d",Long.parseLong(Integer.toBinaryString(~(1<<offset)))));

        System.out.println(String.format("%032d", new BigInteger(Integer.toBinaryString(number))));
        System.out.println(String.format("%032d",new BigInteger(Integer.toBinaryString(~4))));
        System.out.println(~4);
    }
   static void swap( int a , int b)
    {
        a=a^b;

        b=a^b;

        a=a^b;


        System.out.println(a+" "+b);
    }


}
