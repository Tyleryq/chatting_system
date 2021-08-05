package edu.ncu.chattingsys.friend.service;

import edu.ncu.chattingsys.commonsapi.model.domain.Team;
import edu.ncu.chattingsys.friend.dao.FriendShipDao;
import edu.ncu.chattingsys.commonsapi.model.responseData.FriendInfoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendShipService {
    @Autowired
    private FriendShipDao friendShipDao;
    public short CanAddFriend(Integer uid,Integer friend_id){//1:账号不存在,2:你已被拉黑,3:允许添加
        if (friendShipDao.findUserById(friend_id)==null)    return 1;
        for (int t:friendShipDao.selectBidsByUid(friend_id)){
            if (uid.equals(t))  return 2;
        }
        return 3;
    }

    public List<FriendInfoData> getFriendsList(Integer uid){
        return friendShipDao.selectFriendListByUid(uid);
    }

    public boolean deleteFriend(Integer uid,Integer fid){
        Integer t1 = friendShipDao.deleteFriend(uid,fid);
        Integer t2 = friendShipDao.deleteFriend(fid,uid);
        if (t1>0&&t2>0){
            return true;
        }else
            return false;
    }
    public void addFriend(Integer uid,Integer fid){
        friendShipDao.insertFriendShip(uid,fid);
        friendShipDao.insertFriendShip(fid,uid);//相互添加
    }
    public boolean isFriend(Integer uid,Integer fid){
        if (friendShipDao.isFriend(uid,fid)!=null)  return true;
        else return false;
    }
    public void addBlock(Integer uid,Integer bid){
        if (isFriend(uid,bid)){
            deleteFriend(uid,bid);
        }
        friendShipDao.addBlock(uid, bid);
    }
    public void removeBlock(Integer uid,Integer bid){
        friendShipDao.removeBlock(uid, bid);
    }
    public void setFriendGroup(Integer uid,Integer fid,String group){
        friendShipDao.setFriendGroup(uid, fid, group);
    }
    public void changeZonePermission(Integer uid,Integer fid,Boolean permission){
        friendShipDao.changeZonePermission(uid, fid, permission);
    }
    public List<Integer> getBlocks(Integer uid){
        return friendShipDao.getBlocks(uid);
    }
}
