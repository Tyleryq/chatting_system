package edu.ncu.chattingsys.team.dao;

import edu.ncu.chattingsys.commonsapi.model.domain.Team;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface TeamDao {
    @Insert("insert into team(t_name,descript,creator_id,create_time) values(#{t_name},#{descript},#{creator_id},#{time})")
    public Integer createTeam(@Param("creator_id") Integer creator_id,@Param("t_name")  String t_name,@Param("descript") String descript,@Param("time") String time);
    @Select("select team_id from team where creator_id=#{uid} and create_time=#{time}")
    public Integer getTNumByUidAndTime(@Param("uid") Integer uid,@Param("time") String time);
    @Insert("insert into team_member(team_id,member_id,join_date) values(#{t_id},#{uid},now())")
    public Integer addTeamMember(@Param("t_id") Integer t_id,@Param("uid") Integer uid);
    @Delete("delete from team_member where team_id=#{t_id} and member_id=#{uid}")
    public Integer deleteTeamMember(@Param("t_id") Integer t_id,@Param("uid") Integer uid);
    @Select("select distinct t.* from team_member tm right join team t on tm.team_id=t.team_id where tm.member_id=#{uid} or t.creator_id=#{uid}")
    public List<Team> selectTeamListByUid(Integer uid);
    @Select("select uname from user where uid=#{uid}")
    public String findNameByUid(Integer uid);
    @Select("select count(*)+1 from team_member where team_id=#{tid}")
    public Integer getTeamNumByTid(Integer tid);
}
