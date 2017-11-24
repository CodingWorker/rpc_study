package com.example.server;

import com.mysql.jdbc.ResultSetRow;

import javax.xml.transform.Result;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daiya on 2017/8/7.
 */
public class Main {
    public static void main(String[] args){
//        SocketServer server=SocketServer.getInstance();
//        server.setPort(1121);
//        server.onListen();
//        server.acceptRequest();

        test();


    }

    public static void test(){
        class Result implements Serializable {
            private int code;
            private Map<Integer,List<Integer>> failedIds=null;

            public Result(){this.failedIds=new HashMap<>();}

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public Map<Integer, List<Integer>> getFailedIds() {
                return failedIds;
            }

            public void setFailedIds(Map<Integer, List<Integer>> failedIds) {
                this.failedIds = failedIds;
            }

            @Override
            public String toString(){
                StringBuilder sb=new StringBuilder("{");
                sb.append(String.format("\"code\":%d,",this.code));
                sb.append("\"failed\":[");

                boolean flag1=false;
                for(Map.Entry<Integer,List<Integer>> entry:this.failedIds.entrySet()){
                    if(flag1){
                        sb.append(",");
                    }
                    sb.append(String.format(String.format("{\"deviceId\":%d,\"componentIds\":[",entry.getKey())));
                    flag1=true;

                    boolean flag2=false;
                    for(Integer componentId: entry.getValue()){
                        if(flag2) sb.append(",");
                        sb.append(componentId);
                        flag2=true;
                    }
                    sb.append("]");
                    sb.append("}");
                }
                sb.append("]");
                sb.append("}");

                return sb.toString();
            }
        }
        Result result=new Result();
        Map<Integer,List<Integer>> data=new HashMap<>();

        List<Integer> l1=new ArrayList<>();
        l1.add(1);
        l1.add(2);
        data.put(1,l1);

        List<Integer> l2=new ArrayList<>();
        l2.add(1);
        l2.add(2);
        data.put(2,l2);

        List<Integer> l3=new ArrayList<>();
        l3.add(1);
        l3.add(2);
        data.put(1,l3);


        result.setFailedIds(data);
        System.out.println(result.toString());
    }


}
