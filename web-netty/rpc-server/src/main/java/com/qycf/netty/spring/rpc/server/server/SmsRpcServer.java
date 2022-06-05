package com.qycf.netty.spring.rpc.server.server;


import com.qycf.netty.spring.rpc.server.server.rpc.RpcServerFrame;
import com.qycf.netty.spring.rpc.server.service.SendSms;
import com.qycf.netty.spring.rpc.server.service.impl.SendSmsImpl;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：rpc的服务端，提供服务
 */
public class SmsRpcServer {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                try{
                    RpcServerFrame serviceServer = new RpcServerFrame(9189);
                    serviceServer.registerSerive(SendSms.class.getName(),
                    		SendSmsImpl.class);
                    serviceServer.startService();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
