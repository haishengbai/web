package com.qycf.web.netty.spring.netty.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RfidReaderDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        int len = byteBuf.readableBytes();
        log.info("read msg decoder length {}", len);
        if(len > 0){
            byte[] src = new byte[len];
            byteBuf.readBytes(src);
            if (len > 1){
                if ((src[0] & 0x000000ff) != 0x46
                        || (src[1] & 0x000000ff) != 0x4D
                        || (src[2] & 0x000000ff) != 0x53){
                    log.warn("非rfid读卡器包头");
                    channelHandlerContext.close();
                }
                list.add(bytesToHexString(src));
            }
        }else {
            channelHandlerContext.close();
        }

    }

    public String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2){
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static String toHexString1(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }

    public static String toHexString1(byte b) {
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }
}
