package edu.ncu.chattingsys.inter.service;

import com.alibaba.fastjson.JSONArray;
import edu.ncu.chattingsys.commonsapi.model.domain.Zone;
import edu.ncu.chattingsys.commonsapi.model.responseData.ZoneResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("ZONE-SERVICE")
public interface ZoneService {
    @RequestMapping(value = "/zone/publishZone")
    public void publishZone(@RequestParam("uid") Integer uid, @RequestParam("content") String content);
    @RequestMapping(value = "/zone/deleteZone")
    public void deleteZone(@RequestParam("uid") Integer uid,@RequestParam("zone_id") Integer zone_id);
    @RequestMapping(value = "/zone/likeZone")
    public void likeZone(@RequestParam("zid") Integer zid,@RequestParam("uid") Integer uid);
    @RequestMapping(value = "/zone/addComment")
    public void addComment(@RequestParam("zid") Integer zid,@RequestParam("uid") Integer uid,@RequestParam("content") String content);
    @RequestMapping(value = "/zone/setZonePermission")
    public void setZonePermission(@RequestParam("uid") Integer uid,@RequestParam("fid") Integer fid,@RequestParam("p") Boolean p);
    @RequestMapping(value = "/zone/getFriendsZones")
    public List<ZoneResponseData> getFriendsZonesByUid(@RequestParam("uid") Integer uid);
    @RequestMapping(value = "/zone/getFriendZonesByFid")
    public List<ZoneResponseData> getFriendZonesByFid(@RequestParam("uid") Integer uid,@RequestParam("fid") Integer fid);
}
