package com.qycf.web.netty.spring.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.Objects;


@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("Channel active......");
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        log.debug(socketAddress.toString());
        super.handlerAdded(ctx);
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("channelRead start , msg:{}",msg.toString());
        String msgString = msg.toString();
        String head = msgString.substring(0, 6);
        String length = msgString.substring(6, 10);
        String readerSn = msgString.substring(10, 14);
        String son = msgString.substring(14, msgString.length()-4);
        SpringNettyApplicationContext.rfidReaderMap.put(readerSn, ctx);
        ChannelReadExecutor channelReadExecutor = SpringNettyApplicationContext.getChannelReadExecutor();
        if (Objects.isNull(channelReadExecutor)) {
            log.info("channelReadExecutor is null");
        }
        channelReadExecutor.executeEvent(readerSn, son);
    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.debug("Channel removed......");
        super.handlerRemoved(ctx);
    }
}
