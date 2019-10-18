package com.cwc.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author bwh
 * @date 2019/10/18/018 - 17:47
 * @Description
 */
public class SocketClient00 {
    public static void main(String[] args) {
        int port = 1101;
        String host = "127.0.0.1";
        try {
            Socket socket =new Socket(host,port);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("hello no.2".getBytes("UTF-8"));
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
