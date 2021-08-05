package edu.ncu.chattingsys.message.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
public class Receiver {
    @Autowired
    private RedisTemplate redisTemplate;
    @StreamListener(Sink.INPUT)
    public void receiveMessage(Message<String> message){
        String receivedString = message.getPayload();
//        System.out.println("received:"+receivedString);
        JSONObject jsonObj=JSONObject.parseObject(receivedString);
        edu.ncu.chattingsys.commonsapi.model.domain.Message receive=JSONObject.toJavaObject(jsonObj, edu.ncu.chattingsys.commonsapi.model.domain.Message.class);
//        System.out.println(receive.getReceiver());
        redisTemplate.opsForList().rightPush(receive.getReceiver()+"_unreadMsgList",receive);
    }

}
