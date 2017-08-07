package com.example.client;

/**
 * Created by daiya on 2017/8/7.
 */
public class Main {
    public static void main(String[] args){
        SocketClient client=SocketClient.getInstance();
        client.setServer("127.0.0.1");
        client.setPort(1121);
        client.connect();
        client.linkOn();
    }
}
