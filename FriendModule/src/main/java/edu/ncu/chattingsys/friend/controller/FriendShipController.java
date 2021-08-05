package edu.ncu.chattingsys.friend.controller;

import edu.ncu.chattingsys.commonsapi.model.domain.Message;
import edu.ncu.chattingsys.commonsapi.model.domain.Team;
import edu.ncu.chattingsys.commonsapi.model.responseData.CommonData;
import edu.ncu.chattingsys.commonsapi.model.responseData.FriendInfoData;
import edu.ncu.chattingsys.friend.service.FriendShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendShipController {
    @Autowired
    private FriendShipService friendShipService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/addFriendSend")   //发送加好友请求
    public CommonData addFriend(String uid,String friend_id){
        if (uid==null||friend_id==null) return CommonData.fail("参数为空");
        short t=friendShipService.CanAddFriend(Integer.parseInt(uid),Integer.parseInt(friend_id));
        if (t==1)  return CommonData.fail("该账号不存在");
        else if (t==2)  return CommonData.fail("你已被拉黑");
        else {
            Message message=new Message((short) 3,"",Integer.parseInt(uid),Integer.parseInt(friend_id));
            redisTemplate.opsForList().rightPush(friend_id+"_unreadMsgList",message);
            return CommonData.sucess("已发送加好友请求");
        }
    }

    @RequestMapping(value = "/addFriendReply")   //好友请求答复
    public void reply(Integer uid,Integer adder_id,Character isAgree){
        Message message;
        if (isAgree=='y'){
            friendShipService.addFriend(uid,adder_id);
            message=new Message((short)4,"已同意你的好友申请",uid,adder_id);
        }else
            message=new Message((short)4,"已拒绝你的好友申请",uid,adder_id);
        redisTemplate.opsForList().rightPush(adder_id+"_unreadMsgList",message);
    }

    @RequestMapping(value = "/getFriendList")
    public List<FriendInfoData> getFriendsList(Integer uid){
        return friendShipService.getFriendsList(uid);
    }

    @RequestMapping(value = "/deleteFriend")
    public CommonData deleteFriend(Integer uid,Integer fid){
        if (friendShipService.deleteFriend(uid,fid))    return CommonData.sucess();
        else return CommonData.fail("失败");
    }

    @RequestMapping(value = "/addBlock")
    public void addBlock(Integer uid,Integer b_id){      //拉黑
        friendShipService.addBlock(uid, b_id);
    }

    @RequestMapping(value = "/removeBlock")
    public void removeBlock(Integer uid,Integer bid){
        friendShipService.removeBlock(uid, bid);
    }

    @RequestMapping(value = "/getBlocks")
    public List<Integer> getBlocks(Integer uid){
        return friendShipService.getBlocks(uid);
    }

    @RequestMapping(value = "/setFriendGroup")
    public void setFriendGroup(Integer uid,Integer fid,String group){
        friendShipService.setFriendGroup(uid, fid, group);
    }

    @RequestMapping(value = "/changeZonePermission")
    public void changeZonePermission(Integer uid,Integer fid,Boolean permission){
        friendShipService.changeZonePermission(uid, fid, permission);
    }
}
