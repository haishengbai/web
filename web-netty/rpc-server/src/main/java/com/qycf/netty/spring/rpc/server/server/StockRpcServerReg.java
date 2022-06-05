package com.qycf.netty.spring.rpc.server.server;


import com.qycf.netty.spring.rpc.server.server.rpc.RpcServerFrameReg;
import com.qycf.netty.spring.rpc.server.service.StockService;
import com.qycf.netty.spring.rpc.server.service.impl.StockServiceImpl;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 依娜老师  QQ：2470523467  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：rpc的服务端，提供服务
 */
public class StockRpcServerReg {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                try{
                    RpcServerFrameReg serviceServer = new RpcServerFrameReg(9190);
                    serviceServer.registerSerive(StockService.class, StockServiceImpl.class);
                    serviceServer.startService();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
