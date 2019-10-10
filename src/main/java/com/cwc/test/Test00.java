package com.cwc.test;

/**
 * @author bwh
 * @date 2019/10/10/010 - 14:27
 * @Description
 */
public class Test00 {
    public static void main(String[] args) {
        Thread t1 = new Thread(new ThreadTest("1"));
        Thread t2 = new Thread(new ThreadTest("2"));
        t1.start();
        t2.start();
    }
}
