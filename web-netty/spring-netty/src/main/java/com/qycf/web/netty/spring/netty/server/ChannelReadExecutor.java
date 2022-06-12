package com.qycf.web.netty.spring.netty.server;

import com.qycf.web.netty.spring.netty.utils.ExecutorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ChannelReadExecutor {


    // 三字节卡号的第一个字节 <0x11 是电瓶车，其余的是人员。 16进制的11转换成十进制是17
    private final Integer rfidTypeFlag = 17;

    /**
     * channelReadExecutor 执行 eventLoopGroup任务
     */
    public void executeEvent(String readerSn, String son) {
        log.info("executeEvent : readerSn {}, son {}", readerSn, son);
        ExecutorUtils.executorService.execute(() -> {

            log.info("execute ...");
        });
    }



}
