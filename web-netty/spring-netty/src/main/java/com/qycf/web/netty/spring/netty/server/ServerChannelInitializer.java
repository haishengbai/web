package com.qycf.web.netty.spring.netty.server;

import com.qycf.web.netty.spring.netty.server.codec.RfidReaderDecoder;
import com.qycf.web.netty.spring.netty.server.codec.RfidReaderEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel>  {


    @Override
    protected void initChannel(SocketChannel socketChannel) {

        socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535,3,2,-3,0));
        socketChannel.pipeline().addLast("encoder",new RfidReaderEncoder());
        socketChannel.pipeline().addLast("decoder",new RfidReaderDecoder());
        socketChannel.pipeline().addLast(new NettyServerHandler());
    }
}
