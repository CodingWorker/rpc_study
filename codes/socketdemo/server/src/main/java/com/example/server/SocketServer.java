package com.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by daiya on 2017/8/7.
 */
public final class SocketServer {
    private static SocketServer socketServer = new SocketServer();

    private ServerSocket serverSocket;

    private Socket socket;

    private PrintWriter writer;

    private BufferedReader in;

    private int port;

    private SocketServer() {
    }

    public static SocketServer getInstance() {
        return socketServer;
    }

    public void onListen() {
        try {
            //1. 绑定到指定端口
            this.serverSocket = new ServerSocket(this.port);

            //2. 监听
            this.socket = this.serverSocket.accept();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void acceptRequest() {
        try {
            //3. 获取输入流，并读取客户端信息
            String line;

            //由Socket对象得到输入流，并构造相应的BufferedReader对象
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            //由Socket对象得到输出流，并构造PrintWriter对象
            this.writer = new PrintWriter(socket.getOutputStream());

            //由系统标准输入设备构造BufferedReader对象
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            //在标准输出上打印从客户端读入的字符串
            System.out.println("Client:" + in.readLine());

            //从标准输入读入一字符串
            line = br.readLine();
            while (!line.equals("end")) {//如果该字符串为 "bye"，则停止循环
                writer.println(line);//向客户端输出该字符串
                writer.flush();//刷新输出流，使Client马上收到该字符串

                System.out.println("SocketServer:" + line);//在系统标准输出上打印读入的字符串

                System.out.println("Client:" + in.readLine());//从Client读入一字符串，并打印到标准输出上

                line = br.readLine();//从系统标准输入读入一字符串
            } //继续循环
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        try {
            this.writer.close();
            this.socket.close();
            this.serverSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
