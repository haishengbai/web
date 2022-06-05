package com.qycf.web.netty.rpc.client.client;


import com.qycf.web.netty.rpc.client.client.rpc.RpcClientFrame;
import com.qycf.web.netty.rpc.client.service.SendSms;
import com.qycf.web.netty.rpc.client.service.StockService;
import com.qycf.web.netty.rpc.client.vo.UserInfo;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：rpc的客户端，调用远端服务
 */
public class RpcClient {
    public static void main(String[] args) {

        UserInfo userInfo
                = new UserInfo("Mark","Mark@xiangxue.com");

        SendSms sendSms = RpcClientFrame.getRemoteProxyObj(SendSms.class,
                "127.0.0.1",9189);
        System.out.println("Send mail: "+ sendSms.sendMail(userInfo));

        StockService stockService
                = RpcClientFrame.getRemoteProxyObj(StockService.class,
                "127.0.0.1",9190);
        stockService.addStock("A001",1000);
        stockService.deduceStock("B002",50);

//        StockService stockService = new StockServiceImpl();
//        stockService.addStock("A001",1000);
//        stockService.deduceStock("B002",50);

    }
}
