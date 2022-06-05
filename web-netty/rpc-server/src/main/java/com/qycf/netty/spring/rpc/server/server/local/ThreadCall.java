package com.qycf.netty.spring.rpc.server.server.local;


import com.qycf.netty.spring.rpc.server.service.NormalBusi;
import com.qycf.netty.spring.rpc.server.service.SendSms;
import com.qycf.netty.spring.rpc.server.service.StockService;
import com.qycf.netty.spring.rpc.server.service.impl.SendSmsImpl;
import com.qycf.netty.spring.rpc.server.service.impl.StockServiceImpl;
import com.qycf.netty.spring.rpc.server.vo.UserInfo;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：引入线程后的实现
 */
public class ThreadCall {
    public static void main(String[] args) {
       //其他业务工作
        NormalBusi normalBusi = new NormalBusi();
        normalBusi.business();
        new Thread(new StockTask()).start();
        new Thread(new SmsTask()).start();
    }

    private static class StockTask implements Runnable{

        @Override
        public void run() {
            StockService stockService = new StockServiceImpl();
            stockService.addStock("A001",1000);
            stockService.deduceStock("B002",50);
        }
    }

    private static class SmsTask implements Runnable{

        @Override
        public void run() {
            SendSms sendSms = new SendSmsImpl();
            UserInfo userInfo
                    = new UserInfo("Mark","Mark@xiangxue.com");
            System.out.println("Send mail: "+ sendSms.sendMail(userInfo));
        }
    }
}
