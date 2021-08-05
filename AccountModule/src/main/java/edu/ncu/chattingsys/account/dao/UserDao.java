package edu.ncu.chattingsys.account.dao;

import edu.ncu.chattingsys.commonsapi.model.domain.Subscription;
import edu.ncu.chattingsys.commonsapi.model.domain.Team;
import edu.ncu.chattingsys.commonsapi.model.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Select("select * from user where uid=#{uid}")
    public User findUserById(int uid);
    @Select("select * from team where team_id=#{tid}")
    public Team findTeamByTid(Integer tid);
    @Select("select * from subscription where s_id=#{sid}")
    public Subscription findSubscriptionBySid(Integer sid);
    @Select("select uname from user where uid=#{uid}")
    public String findNameByUid(Integer uid);
    @Select("select uname from user where uname=#{uname}")  //查看用户名是否存在
    public String findNameByName(String uname);
    @Insert("insert into user(uname,pwd,sex,register_date,mail) values(#{uname},#{pwd},#{sex},now(),#{mail})")
    public Integer insertUser(User user);
    @Select("select uid from user where uname=#{name}")
    public Integer findUidByName(String name);
    @Update("update user set pwd=#{pwd} where uid=#{uid}")
    public Integer updateUserPwd(@Param("uid") Integer uid,@Param("pwd")  String pwd);
    @Insert("insert into subscription(s_name,company,company_code,description,pwd,r_date) values(#{s_name},#{company},#{company_code},#{description},#{pwd},now())")
    public Integer insertSubscription(Subscription subscription);
    @Select("select s_id from subscription where s_name=#{name}")
    public Integer getSidByName(String name);
    @Select("select pwd from subscription where s_id=#{sid}")
    public String getSubscriptionPwdById(Integer sid);
    @Select("select s_name from subscription where s_id=#{sid}")
    public String findSnameBySid(Integer sid);
    @Select("select team_id from team where creator_id=#{cid}")
    public List<Integer> getTidsByCreatorId(Integer cid);
    @Delete("delete from block where uid=#{uid} or block_id=#{uid}")
    public Integer deleteBlockByUid(Integer uid);
    @Delete("delete from friendship where uid=#{uid} or f_id=#{uid}")
    public Integer deleteFriendshipByUid(Integer uid);
    @Delete("delete from subscribe_user where uid=#{uid}")
    public Integer deleteSubscribeUserByUid(Integer uid);
    @Delete("delete from team_member where team_id=#{tid}")
    public Integer deleteTeamMemberByTid(Integer tid);
    @Delete("delete from team_member where member_id=#{uid}")
    public Integer deleteTeamMemberByUid(Integer uid);
    @Delete("delete from team where creator_id=#{cid}")
    public Integer deleteTeamByCreatorId(Integer cid);
    @Delete("delete from user where uid=#{uid}")
    public Integer deleteUserByUid(Integer uid);
    @Delete("delete from zone where uid=#{uid}")
    public Integer deleteZoneByUid(Integer uid);
    @Delete("delete from zone_comment where uid=#{uid}")
    public Integer deleteZoneCommentByUid(Integer uid);
    @Delete("delete from zone_like where uid=#{uid}")
    public Integer deleteZoneLikeByUid(Integer uid);
}
