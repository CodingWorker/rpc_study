package com.example;

import com.example.rpc.*;

import java.net.InetSocketAddress;

/**
 * Created by daiya on 2017/8/7.
 */
public class Main {
    public static void main(String[] args){
        //调用代理
        Hello helloProxy = RPCClient.getRemoteProxyObj(Hello.class, new InetSocketAddress("localhost", 8090));
        System.out.println(helloProxy.getIP());
    }
}
