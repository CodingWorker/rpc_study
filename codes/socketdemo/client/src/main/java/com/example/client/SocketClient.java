package com.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.BufferOverflowException;

/**
 * Created by daiya on 2017/8/7.
 */
public class SocketClient {
    private SocketClient(){}
    private static SocketClient socketClient=new SocketClient();

    private BufferedReader br;

    private Socket socket;

    private PrintWriter writer;

    private BufferedReader in;

    private String server;
    private int port;

    public static SocketClient getInstance(){
        return socketClient;
    }

    /**
     * 连接服务器
     */
    public void connect() {
        try {
            //1. 创建客户端socket，指定服务器地址和端口
            this.socket = new Socket(server, port);

            //2. 由socket对象得到输出流，并构造printwriter对象
            this.writer = new PrintWriter(socket.getOutputStream());

            //3. 获取输入流，并读取服务器端的响应数据
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //4. 获取输出流，向服务器端发送消息，向服务器端口发送请求
            this.br = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void linkOn(){
        try{
            String readline = br.readLine();
            while (!readline.equals("end")) {// 若从标准输入读入的字符串为 "end"则停止循环
                writer.println(readline);// 将从系统标准输入读入的字符串输出到Server
                writer.flush();// 刷新输出流，使Server马上收到该字符串
                System.out.println("Client:" + readline);// 在系统标准输出上打印读入的字符串

                System.out.println("Server:" + in.readLine());// 从Server读入一字符串，并打印到标准输出上

                readline = br.readLine(); // 从系统标准输入读入一字符串
            } // 继续循环
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void close(){
        try{
            this.socket.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}


