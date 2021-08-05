package edu.ncu.chattingsys.inter.controller;

import edu.ncu.chattingsys.commonsapi.model.responseData.CommonData;
import edu.ncu.chattingsys.commonsapi.model.responseData.FriendInfoData;
import edu.ncu.chattingsys.inter.service.FriendService;
import edu.ncu.chattingsys.inter.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private StatusService statusService;
    @RequestMapping(value = "/getFriendList")
    public List<FriendInfoData> getFriendsList(Integer uid){
        List<FriendInfoData> friendInfoDatas=friendService.getFriendsList(uid);
        for(FriendInfoData t:friendInfoDatas){
            Boolean isOnline=statusService.getUserStatus(t.getUid());
            t.setOnline(isOnline);
        }
        return friendInfoDatas;
    }
    @RequestMapping(value = "/addFriendSend")   //发送加好友请求
    public CommonData addFriend(String uid, String friend_id){
        return friendService.addFriend(uid, friend_id);
    }
    @RequestMapping(value = "addFriendReply")   //好友请求答复
    public void reply(Integer uid,Integer adder_id,Character isAgree){
        friendService.reply(uid, adder_id, isAgree);
    }
    @RequestMapping(value = "/deleteFriend")
    public CommonData deleteFriend(Integer uid,Integer fid){
        return friendService.deleteFriend(uid, fid);
    }
    @RequestMapping(value = "/addBlock")
    public void addBlock(Integer uid,Integer b_id){
        friendService.addBlock(uid, b_id);
    }
    @RequestMapping(value = "/removeBlock")
    public void removeBlock(Integer uid,Integer bid){
        friendService.removeBlock(uid, bid);
    }
    @RequestMapping(value = "/setFriendGroup")
    public void setFriendGroup(Integer uid,Integer fid,String group){
        friendService.setFriendGroup(uid, fid, group);
    }
    @RequestMapping(value = "/changeZonePermission")
    public void changeZonePermission(Integer uid,Integer fid,Boolean permission){friendService.changeZonePermission(uid, fid, permission);}
    @RequestMapping(value = "/getBlocks")
    public List<Integer> getBlocks(Integer uid){
        return friendService.getBlocks(uid);
    }
}
