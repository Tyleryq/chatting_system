package edu.ncu.chattingsys.subscription.service;

import com.alibaba.fastjson.JSONObject;
import edu.ncu.chattingsys.commonsapi.model.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

@EnableBinding(Source.class)
public class MQSend {
    @Autowired
    private MessageChannel output;

    public void send(Message message){
        String msgStr = JSONObject.toJSONString(message);
        output.send(MessageBuilder.withPayload(msgStr).build());
    }
    public void send(){}
}
