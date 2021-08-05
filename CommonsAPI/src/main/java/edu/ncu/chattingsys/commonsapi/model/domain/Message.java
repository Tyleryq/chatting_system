package edu.ncu.chattingsys.commonsapi.model.domain;

import java.util.Date;
import java.util.UUID;

public class Message {
    private String uuid;//每个消息都有唯一标识
    private short type;//0:好友消息;1:文章推送;2:动态消息;3:好友请求消息;4:好友请求答复消息;5:动态点赞消息;6:动态评论消息7:群消息
    private String content;//内容
    private Date sendTime;
    private Integer sender;//00000表示系统
    private Integer receiver;
    private Integer t_s_Id;//如果是群消息或公众号消息,此变量表示群/公众号id

    public Message(){}
    public Message(short type, String content,  Integer sender, Integer receiver) {
        this.uuid = UUID.randomUUID().toString();
        this.type = type;
        this.content = content;
        this.sendTime = new Date();
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getT_s_Id() {
        return t_s_Id;
    }

    public void setT_s_Id(Integer t_s_Id) {
        this.t_s_Id = t_s_Id;
    }
}
