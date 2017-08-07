package com.example.rpc.remote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by daiya on 2017/8/7.
 */
public class ServerImpl implements Server {
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final HashMap<String, Class> serviceRegistry = new HashMap<>();

    private static boolean isRunning = false;

    private static int port;

    public ServerImpl(int port) {
        this.port = port;
    }

    /**
     * 停止服务
     */
    public void stop() {
        isRunning = false;
        executor.shutdown();
    }

    /**
     * 开启服务
     *
     * @throws IOException
     */
    public void start() throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        System.out.println("start server");
        try {
            while (true) {
                // 1.监听客户端的TCP连接，接到TCP连接后将其封装成task，由线程池执行
                executor.execute(new ServiceTask(server.accept()));
            }
        } finally {
            server.close();
        }
    }

    /**
     * 注册服务
     * @param serviceInterface
     * @param impl
     */
    public void register(Class serviceInterface, Class impl) {
        serviceRegistry.put(serviceInterface.getName(), impl);
    }

    /**
     * 判断服务是否正在运行
     * @return
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 获取服务端口
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     *
     */
    private static class ServiceTask implements Runnable {
        Socket client = null;

        public ServiceTask(Socket client) {
            this.client = client;
        }

        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;

            try {
                // 2.将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果
                input = new ObjectInputStream(client.getInputStream());
                String serviceName = input.readUTF();//获取服务名
                String methodName = input.readUTF();//获取调用方法名

                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();//获取参数类型
                Object[] arguments = (Object[]) input.readObject();//获取参数
                Class serviceClass = serviceRegistry.get(serviceName);//从注册的服务里更具名字获取服务

                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + " not found");
                }

                //get method
                Method method = serviceClass.getMethod(methodName, parameterTypes);

                //get result
                Object result = method.invoke(serviceClass.newInstance(), arguments);

                // 3.将执行结果反序列化，通过socket发送给客户端
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
