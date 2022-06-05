package com.qycf.netty.spring.rpc.server.service.impl;


import com.qycf.netty.spring.rpc.server.service.SendSms;
import com.qycf.netty.spring.rpc.server.vo.UserInfo;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *更多课程咨询 依娜老师  QQ：2470523467  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：短信息发送服务的实现
 */
public class SendSmsImpl implements SendSms {

    @Override
    public boolean sendMail(UserInfo user) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("已发送短信息给："+user.getName()+"到【"+user.getPhone()+"】");
        return true;
    }
}
