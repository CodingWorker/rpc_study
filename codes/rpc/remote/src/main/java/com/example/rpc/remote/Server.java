package com.example.rpc.remote;

import java.io.IOException;

/**
 * Created by daiya on 2017/8/7.
 */
public interface Server {
    /**
     * 停止服务
     */
    void stop();

    /**
     * 开启服务
     * @throws IOException
     */
    void start() throws IOException;

    /**
     * 注册服务,参数Wie接口和实现类
     * @param serviceInterface
     * @param impl
     */
    void register(Class serviceInterface, Class impl);

    /**
     * 判断服务是否正在运行
     * @return
     */
    boolean isRunning();

    /**
     * 获取服务端口
     * @return
     */
    int getPort();
}
