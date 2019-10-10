package com.cwc.test;

/**
 * @author bwh
 * @date 2019/10/10/010 - 14:27
 * @Description
 */
public class ThreadTest implements  Runnable{
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ThreadTest(String msg){
        this.msg = msg;
    }
    public   void hello(String msg){
        synchronized (this) {
            while (true) {
                System.out.println(msg);
            }
        }
    }

    @Override
    public  void run() {
        hello(this.msg);
    }
}
