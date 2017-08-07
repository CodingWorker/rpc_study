package com.example.rpc.remote;

import com.example.rpc.Hello;
import com.example.rpc.HelloImpl;

import java.io.IOException;

/**
 * Created by daiya on 2017/8/7.
 */
public class Main {
    public static void main(String[] args)throws IOException{
        //构建rpc服务，并绑定端口
        Server server=new ServerImpl(8090);

        //注册服务
        server.register(Hello.class,HelloImpl.class);

        //启动服务
        server.start();
    }
}
