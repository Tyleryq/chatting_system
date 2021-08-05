package edu.ncu.chattingsys.inter.controller;

import edu.ncu.chattingsys.commonsapi.model.domain.Message;
import edu.ncu.chattingsys.commonsapi.model.responseData.MessageData;
import edu.ncu.chattingsys.inter.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;
    @RequestMapping(value = "/send")
    public void send(Message message){
        messageService.send(message);
    }
    @RequestMapping(value = "/sendFriendMessage")
    public void sendFriendMessage(Integer sender,Integer receiver,String content){
        messageService.sendFriendMessage(sender,receiver,content);
    }
    @RequestMapping(value = "/receive")
    public MessageData receive(Integer receiver_id){
       return messageService.receive(receiver_id);
    }
    @RequestMapping("/reciveACK")
    public void reciveACK(String message_id){
        messageService.reciveACK(message_id);
    }
    @RequestMapping(value = "/sendTeamMessage")
    public void sendTeamMessage(Integer sender,Integer tid,String content){
        messageService.sendTeamMessage(sender,tid,content);
    }
}
