package com.example.server;

/**
 * Created by daiya on 2017/8/7.
 */
public class Main {
    public static void main(String[] args){
        SocketServer server=SocketServer.getInstance();
        server.setPort(1121);
        server.onListen();
        server.acceptRequest();
    }
}
