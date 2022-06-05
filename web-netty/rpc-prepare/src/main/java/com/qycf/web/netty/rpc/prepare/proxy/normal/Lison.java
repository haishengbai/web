package com.qycf.web.netty.rpc.prepare.proxy.normal;


import com.qycf.web.netty.rpc.prepare.proxy.IGetServant;
import com.qycf.web.netty.rpc.prepare.proxy.Receptionist;

/**
 */
public class Lison {

    public static void main(String[] args) {
        IGetServant getServant = new Receptionist();
        getServant.choice("御姐范风格");

        // 静态代理
        IGetServant james = new James();
        james.choice("喜欢的样子");
    }

}
