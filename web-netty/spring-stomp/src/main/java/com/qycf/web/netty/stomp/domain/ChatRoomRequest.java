package com.qycf.web.netty.stomp.domain;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：聊天室的请求实体
 */
public class ChatRoomRequest {
    /*发送者名字*/
    private String name;
    /*聊天内容*/
    private String chatValue;
    /*单聊里接收者的ID*/
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChatValue() {
        return chatValue;
    }

    public void setChatValue(String chatValue) {
        this.chatValue = chatValue;
    }
}
