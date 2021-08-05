package edu.ncu.chattingsys.friend.dao;

import edu.ncu.chattingsys.commonsapi.model.domain.Team;
import edu.ncu.chattingsys.commonsapi.model.domain.User;
import edu.ncu.chattingsys.commonsapi.model.responseData.FriendInfoData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FriendShipDao {
    @Select("select * from user where uid=#{uid}")
    public User findUserById(int uid);
    @Insert("insert into friendship(uid,f_id,add_date) values(#{uid},#{friend_id},now())")
    public Integer insertFriendShip(@Param("uid") Integer uid,@Param("friend_id") Integer friend_id);
    @Select("select block_id from block where uid=#{uid}")
    public List<Integer> selectBidsByUid(@Param("uid") Integer uid);
    @Select("select u.uid,uname,sex,register_date,mail,f.`group`,f.zone_permission from user u join friendship f on u.uid=f.f_id where u.uid in (select f_id from friendship where f.uid=#{uid})")
    public List<FriendInfoData> selectFriendListByUid(Integer uid);
    @Delete("delete from friendship where uid=#{uid} and f_id=#{fid}")
    public Integer deleteFriend(@Param("uid") Integer uid,@Param("fid") Integer fid);
    @Select("select f_id from friendship where uid=#{uid} and f_id=#{fid}")
    public Integer isFriend(@Param("uid") Integer uid,@Param("fid") Integer fid);
    @Select("insert into block(uid,block_id,block_date) values(#{uid},#{bid},now())")
    public Integer addBlock(@Param("uid") Integer uid,@Param("bid") Integer bid);
    @Delete("delete from block where uid=#{uid} and block_id=#{bid}")
    public Integer removeBlock(@Param("uid") Integer uid,@Param("bid") Integer bid);
    @Update("update friendship set `group`=#{group} where uid=#{uid} and f_id=#{fid}")
    public Integer setFriendGroup(@Param("uid") Integer uid,@Param("fid") Integer fid,@Param("group") String group);
    @Update("update friendship set zone_permission=#{permission} where uid=#{uid} and f_id=#{fid}")
    public Integer changeZonePermission(@Param("uid") Integer uid,@Param("fid") Integer fid,@Param("permission") Boolean permission);
    @Select("select block_id from block where uid=#{uid}")
    public List<Integer> getBlocks(Integer uid);

}
