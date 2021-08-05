package edu.ncu.chattingsys.inter.service;

import edu.ncu.chattingsys.commonsapi.model.domain.Team;
import edu.ncu.chattingsys.commonsapi.model.responseData.CommonData;
import edu.ncu.chattingsys.commonsapi.model.responseData.FriendInfoData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("FRIEND-SERVICE")
public interface FriendService {
    @RequestMapping("/friend/getFriendList")
    public List<FriendInfoData> getFriendsList(@RequestParam("uid") Integer uid);
    @RequestMapping("/friend/addFriendSend")
    public CommonData addFriend(@RequestParam("uid") String uid,@RequestParam("friend_id")  String friend_id);
    @RequestMapping("/friend/addFriendReply")
    public void reply(@RequestParam("uid") Integer uid,@RequestParam("adder_id") Integer adder_id,@RequestParam("isAgree") Character isAgree);
    @RequestMapping(value = "/friend/deleteFriend")
    public CommonData deleteFriend(@RequestParam("uid") Integer uid,@RequestParam("fid") Integer fid);
    @RequestMapping(value = "/friend/addBlock")
    public void addBlock(@RequestParam("uid") Integer uid,@RequestParam("b_id") Integer b_id);
    @RequestMapping(value = "/friend/removeBlock")
    public void removeBlock(@RequestParam("uid") Integer uid,@RequestParam("bid") Integer bid);
    @RequestMapping(value = "/friend/setFriendGroup")
    public void setFriendGroup(@RequestParam("uid") Integer uid,@RequestParam("fid") Integer fid,@RequestParam("group") String group);
    @RequestMapping(value = "/friend/changeZonePermission")
    public void changeZonePermission(@RequestParam("uid") Integer uid,@RequestParam("fid") Integer fid,@RequestParam("permission") Boolean permission);
    @RequestMapping(value = "/friend/getBlocks")
    public List<Integer> getBlocks(@RequestParam("uid") Integer uid);
}
