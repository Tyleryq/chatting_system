package edu.ncu.chattingsys.message.controller;

import edu.ncu.chattingsys.commonsapi.model.domain.Message;
import edu.ncu.chattingsys.commonsapi.model.responseData.MessageData;
import edu.ncu.chattingsys.message.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/send")
    public void send(@RequestBody Message message){  //消息发送
        redisTemplate.opsForList().rightPush(message.getSender()+"_unreadMsgList",message);
    }

    @RequestMapping(value = "/sendFriendMessage")
    public void sendFriendMessage(Integer sender,Integer receiver,String content){//好友消息发送
        Message message=new Message((short) 0,content,sender,receiver);
        redisTemplate.opsForList().rightPush(message.getReceiver()+"_unreadMsgList",message);
    }

    @RequestMapping(value = "/sendTeamMessage")
    public void sendTeamMessage(Integer sender,Integer tid,String content){
//        log.info("tid:"+tid);
        List<Integer> members=messageService.getMembersByTid(tid,sender);
        Integer teamCreator=messageService.getCreatorByTid(tid);
        if(!sender.equals(teamCreator)){
            members.add(teamCreator);
//            log.info(teamCreator+"is added");
        }
        for (Integer member:members){
            Message message=new Message((short)7,content,sender,member);
            message.setT_s_Id(tid);
            redisTemplate.opsForList().rightPush(member+"_unreadMsgList",message);
        }
    }

    @RequestMapping(value = "/receive")
    public MessageData receive(Integer receiver_id){    //消息接收
        long size = redisTemplate.opsForList().size(receiver_id+"_unreadMsgList");
        if (size==0)    return null;
        Message message=(Message) redisTemplate.opsForList().rightPop(receiver_id+"_unreadMsgList");
//        redisTemplate.opsForValue().set(message.getUuid()+"_ack",message);
        MessageData messageData=new MessageData();
        messageData.setMessage(message);
        short msgType=message.getType();
        messageData.setSenderName(messageService.getNameByUid(message.getSender()));
        if(msgType==0||msgType==2||msgType==3||msgType==4||msgType==5||msgType==6){

        } else if(msgType==1)
            messageData.setSenderName(messageService.getSNameBySid(message.getSender()));

        else if(msgType==7)
            messageData.setTeamName(messageService.getTNameByTid(message.getT_s_Id()));
        return messageData;
    }

    @RequestMapping("/reciveACK")
    public void reciveACK(String message_id){   //接收消息确认机制
        redisTemplate.delete(message_id+"_ack");
    }
}
