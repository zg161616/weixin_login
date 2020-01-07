package com.cwc.test;

import com.sun.tools.javac.file.ZipArchive;
import sun.reflect.FieldInfo;

import java.io.*;
import java.net.Socket;

/**
 * @author bwh
 * @date 2019/10/18/018 - 16:51
 * @Description
 */
public class SocketClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 1101;
        try {
            Socket socket = new Socket(host,port);
            OutputStream outputStream = socket.getOutputStream();
            String message = "112";
           BufferedInputStream   bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("D://phycont.txt")));
           byte[] fileByte  = new byte[1024];
           int len;
           while((len=bufferedInputStream.read(fileByte))!=-1){
               outputStream.write(fileByte);
           }
           bufferedInputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
