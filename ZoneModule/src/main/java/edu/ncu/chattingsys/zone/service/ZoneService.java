package edu.ncu.chattingsys.zone.service;

import edu.ncu.chattingsys.commonsapi.model.domain.Message;
import edu.ncu.chattingsys.commonsapi.model.domain.Zone;
import edu.ncu.chattingsys.commonsapi.model.responseData.ZoneComment;
import edu.ncu.chattingsys.commonsapi.model.responseData.ZoneResponseData;
import edu.ncu.chattingsys.zone.dao.ZoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZoneService {
    @Autowired
    private ZoneDao zoneDao;
    @Autowired
    private IMQSend mqSend;

    public void publishZone(Integer uid,String content){
        zoneDao.addZone(uid, content);
        List<Integer> friends=zoneDao.getFriendsIdByUid(uid);
        for (Integer f:friends){//向好友发送朋友圈通知
            Message message=new Message((short)2,"发布了朋友圈,进入空间查看",uid,f);
            mqSend.send(message);
        }
    }
    public void deleteZone(Integer zid,Integer uid){
        zoneDao.deleteAllCommentByZid(zid);
        zoneDao.deleteAllLikeByZid(zid);
        zoneDao.deleteZone(zid,uid);
    }
    public void likeZone(Integer zid,Integer uid){
        zoneDao.addLikeZone(zid, uid);
        Integer receiver=zoneDao.getUidByZid(zid);
        Message message=new Message((short)5,"点赞了你的朋友圈,进入空间查看",uid,receiver);
        mqSend.send(message);   //向被点赞的人发送点赞信息
    }
    public Integer getUidByZid(Integer zid){
        return zoneDao.getUidByZid(zid);
    }
    public void addComment(Integer zid,Integer uid,String content){
        zoneDao.addComment(zid,uid,content);
        Integer receiver=zoneDao.getUidByZid(zid);
        Message message=new Message((short)6,"评论了你的朋友圈,进入空间查看",uid,receiver);
        mqSend.send(message);//向被评论的人发信息
    }
    public void setZonePermission(Integer uid,Integer fid,Boolean p){
        zoneDao.setZonePermission(uid, fid, p);
    }
    public List<ZoneResponseData> getFriendsZonesByUid(Integer uid){
        List<Zone> zones=zoneDao.getFriendsZonesByUid(uid);

        List<ZoneResponseData> zoneResponseDataList=new ArrayList<ZoneResponseData>();
        for (Zone tz:zones){
            ZoneResponseData td=new ZoneResponseData();
            td.setZone(tz);
            td.setUname(zoneDao.getUnameByUid(tz.getUid()));
            List<ZoneComment> zoneComments=zoneDao.getCommentByZid(tz.getZone_id());
            td.setZoneComments(zoneComments);
            td.setLikeNum(zoneDao.getLikeNumByZid(tz.getZone_id()));
            List<String> likes=zoneDao.getLikeNamesByZid(tz.getZone_id());
            td.setLikes(likes);
            zoneResponseDataList.add(td);
        }
        return zoneResponseDataList;
    }

    public List<ZoneResponseData> getFriendZonesByFid(Integer uid,Integer fid){
        Boolean permission=zoneDao.getZonePermissionByFid(uid,fid);
        List<ZoneResponseData> zoneResponseDataList=new ArrayList<ZoneResponseData>();
        if(permission.equals(false)){//检查权限
            Zone z=new Zone();
            z.setContent("你没有权限查看ta的空间");
            ZoneResponseData zr=new ZoneResponseData();
            zr.setZone(z);
            zoneResponseDataList.add(zr);
            return zoneResponseDataList;
        }
        List<Zone> zones=zoneDao.getFriendZonesByFid(fid);


        for (Zone tz:zones){
            ZoneResponseData td=new ZoneResponseData();
            td.setZone(tz);
            td.setUname(zoneDao.getUnameByUid(tz.getUid()));
            List<ZoneComment> zoneComments=zoneDao.getCommentByZid(tz.getZone_id());
            td.setZoneComments(zoneComments);
            td.setLikeNum(zoneDao.getLikeNumByZid(tz.getZone_id()));
            List<String> likes=zoneDao.getLikeNamesByZid(tz.getZone_id());
            td.setLikes(likes);
            zoneResponseDataList.add(td);
        }
        return zoneResponseDataList;

    }
}
