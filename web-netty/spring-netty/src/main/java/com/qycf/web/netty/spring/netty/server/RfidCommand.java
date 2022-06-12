package com.qycf.web.netty.spring.netty.server;


/**
 * rfid读卡器命令
 */
public class RfidCommand {

    /**
     * 开启
     */
    public static final String TOGGLE_ON = "on";
    /**
     * 关闭
     */
    public static final String TOGGLE_OFF = "off";

    /**
     * 前缀
     */
    public static final String PREFIX = "464D53000C";


    /**
     * 开启语音播报
     */
    public static final String VOICE_ON = "464D53000BFFFF7100041137F0";
    /**
     * 关闭语音播报
     */
    public static final String VOICE_OFF = "464D53000BFFFF7100041036F1";
    /**
     * 开启蜂鸣器
     */
    public static final String BUZZER_ON = "464D53000BFFFF7000040126E0";
    /**
     * 关闭蜂鸣器
     */
    public static final String BUZZER_OFF = "464D53000BFFFF7000040027E1";
    /**
     * 心跳头
     */
    public static final String HEART_PREFIX = "464D53000BFFFF770004";
    /**
     * 敏感度-硬件
     */
    public static final String SENSIBILITY_HARDWARE = "464D53000CFFFF790005010029E7";
    /**
     * 敏感度-软件头
     */
    public static final String SENSIBILITY_SOFTWARE_PREFIX = "464D53000CFFFF79000501";

    /**
     * 打开继电器动作1
     */
    public static final String RELAY_ACTION_1_ON = "464D53000BFFFF710004315790";

    /**
     * 关闭继电器动作1-恢复
     */
    public static final String RELAY_ACTION_1_OFF = "464D53000BFFFF710004305691";

    /**
     * 打开继电器动作2
     */
    public static final String RELAY_ACTION_2_ON = "464D53000BFFFF7100044127E0";

    /**
     * 关闭继电器动作2-恢复
     */
    public static final String RELAY_ACTION_2_OFF = "464D53000BFFFF7100044026E1";

    /**
     * 语音通道0-- -楼门口未检测到标签，请联系社区管理人员。
     */
    public static final String VOICE_CHANNEL_0 = "464D53000BFFFF7C0004002BE1";

    /**
     * 语音通道1-楼门口-为避免引发安全事故,禁止电动车或电池入楼
     */
    public static final String VOICE_CHANNEL_1 = "464D53000BFFFF7C0004012AE0";

    /**
     * 语音发送 楼门口
     */
    public static final String VOICE_SEND= "464D53000BFFFF7D0004002AE1";

    /**
     * 语音播报  小区门口人员标签播报  播报电瓶车内容  Electric vehicle
     */
    public static final String VOICE_FOR_EV= "464D53000BFFFF7E00040128E0";

    /**
     * 语音播报  小区门口人员标签播报-健康正常-欢迎回家
     */
    public static final String VOICE_FOR_PEOPLE_OK= "464D53000BFFFF7E0004022BE3";

    /**
     * 语音播报  小区门口人员标签播报-健康异常
     */
    public static final String VOICE_FOR_PEOPLE_NO= "464D53000BFFFF7E0004032AE2";




}
