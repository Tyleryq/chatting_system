package edu.ncu.chattingsys.inter.service;

import edu.ncu.chattingsys.commonsapi.model.domain.Message;
import edu.ncu.chattingsys.commonsapi.model.responseData.MessageData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("MESSAGE-SERVICE")
public interface MessageService {
    @RequestMapping(value = "/message/send")
    public void send(@RequestBody Message message);
    @RequestMapping(value = "/message/sendFriendMessage")
    public void sendFriendMessage(@RequestParam("sender") Integer sender,@RequestParam("receiver") Integer receiver,@RequestParam("content") String content);
    @RequestMapping(value = "/message/receive")
    public MessageData receive(@RequestParam("receiver_id") Integer receiver_id);
    @RequestMapping("/message/reciveACK")
    public void reciveACK(@RequestParam("message_id") String message_id);
    @RequestMapping(value = "/message/sendTeamMessage")
    public void sendTeamMessage(@RequestParam("sender") Integer sender,@RequestParam("tid") Integer tid,@RequestParam("content") String content);
}
