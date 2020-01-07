package com.cwc.test;

import com.jfinal.aop.Singleton;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author bwh
 * @date 2019/10/18/018 - 16:51
 * @Description
 */
public class SocketServer {


    public static void main(String[] args) {
        try {
            int port = 1101;
            ServerSocket server;
            server = new ServerSocket(port);
            server.setSoTimeout(1000*10);
            ExecutorService executorService = Executors.newFixedThreadPool(100);
            while (true) {
                    System.out.println("waiting");
                   final Socket socket = server.accept();
                    Runnable runnable =new Runnable() {
                        @Override
                        public void run() {
                            try {
                                InputStream inputStream = socket.getInputStream();
                                byte[] bytes = new byte[1024];
                                StringBuffer sb = new StringBuffer();
                                int len;
                                File file = new File("D://phycont_upload.txt");
                                if (!file.exists()) {
                                    file.createNewFile();
                                }
                                OutputStream outputStream = new FileOutputStream(file);
                                while ((len = inputStream.read(bytes)) != -1) {
                                    outputStream.write(bytes, 0, len);
                                }
                                inputStream.close();
                                outputStream.close();
                            }catch (IOException e){

                            }
                        }
                    };
                    executorService.submit(runnable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
