package com.cwc.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author bwh
 * @date 2019/10/18/018 - 16:51
 * @Description
 */
public class SocketServer  {


    public static void main(String[] args) {
        try {
            int port = 1101;
            ServerSocket server;
            server = new ServerSocket(port);
            while(true) {
                System.out.println("waiting");
                Socket socket = server.accept();
                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[1024];
                StringBuffer sb = new StringBuffer();
                int len;
                File file = new File("D://logo_upload.png");
                if(!file.exists()){
                    file.createNewFile();
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while((line=bufferedReader.readLine())!=null){
                    System.out.println(line);
                }
//
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
