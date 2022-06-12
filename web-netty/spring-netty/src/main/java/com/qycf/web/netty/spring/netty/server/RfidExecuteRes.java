package com.qycf.web.netty.spring.netty.server;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * rfid读卡器命令执行结果
 */
@Getter
@Setter
@Builder
public class RfidExecuteRes {
    /**
     * 执行结果
     */
    private boolean result;
    /**
     * 信息
     */
    private String msg;

}
