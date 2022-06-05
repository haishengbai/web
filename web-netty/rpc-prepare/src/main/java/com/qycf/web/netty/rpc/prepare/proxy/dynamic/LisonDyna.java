package com.qycf.web.netty.rpc.prepare.proxy.dynamic;


import com.qycf.web.netty.rpc.prepare.proxy.IGetServant;
import com.qycf.web.netty.rpc.prepare.proxy.Receptionist;

import java.lang.reflect.Proxy;

public class LisonDyna {
    public static void main(String[] args) {

        IGetServant james =(IGetServant)
                Proxy.newProxyInstance(IGetServant.class.getClassLoader(),
                        new Class[]{IGetServant.class},
                        new JamesDyna(new Receptionist()));
        james.choice("御姐范风格");
    }
}
