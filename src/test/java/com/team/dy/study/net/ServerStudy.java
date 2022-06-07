package com.team.dy.study.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerStudy {
    public static void main(String[] args) throws IOException {
       try( ServerSocket serverSocket = new ServerSocket(8189)){
           Socket accept = serverSocket.accept();
           InputStream inputStream = accept.getInputStream();
           Scanner scanner = new Scanner(inputStream, "GBK");
           OutputStream outputStream = accept.getOutputStream();
           PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, "GBK"), true);
           printWriter.println("你好啊小老弟！欢迎来到小天才问答环节。输入 ‘bye’ 来退出。");
           boolean done = false;
           while(!done && scanner.hasNext()){
               String next = scanner.next();
               printWriter.println("小天才："+next);
               if(next.equals("bye")){
                   done = true;
               }
           }
       }catch (Exception e){

       }finally {

       }
    }
}
