package edu.ncu.chattingsys.inter.controller;

import com.alibaba.fastjson.JSONArray;
import edu.ncu.chattingsys.commonsapi.model.domain.Zone;
import edu.ncu.chattingsys.commonsapi.model.responseData.ZoneResponseData;
import edu.ncu.chattingsys.inter.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ZoneController {
    @Autowired
    private ZoneService zoneService;
    @RequestMapping(value = "/publishZone")
    public void publishZone(Integer uid,String content){
        zoneService.publishZone(uid, content);
    }
    @RequestMapping(value = "deleteZone")
    public void deleteZone(Integer uid,Integer zone_id){
        zoneService.deleteZone(uid, zone_id);
    }
    @RequestMapping(value = "/likeZone")
    public void likeZone(Integer zid,Integer uid){
        zoneService.likeZone(zid, uid);
    }
    @RequestMapping(value = "/addComment")
    public void addComment(Integer zid,Integer uid,String content){
        zoneService.addComment(zid, uid, content);
    }
    @RequestMapping(value = "/setZonePermission")
    public void setZonePermission(Integer uid,Integer fid,Boolean p){
        zoneService.setZonePermission(uid, fid, p);
    }
    @RequestMapping(value = "/getFriendsZones")
    public List<ZoneResponseData> getFriendsZonesByUid(Integer uid){
        return zoneService.getFriendsZonesByUid(uid);
    }
    @RequestMapping(value = "/getFriendZonesByFid")
    public List<ZoneResponseData> getFriendZonesByFid(Integer uid,Integer fid){
        return zoneService.getFriendZonesByFid(uid,fid);
    }
}
