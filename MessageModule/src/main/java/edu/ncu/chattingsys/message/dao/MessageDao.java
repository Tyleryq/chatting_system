package edu.ncu.chattingsys.message.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageDao {
    @Select("select uname from user where uid=#{uid}")
    public String getNameByUid(Integer uid);
    @Select("select member_id from team_member where team_id=#{tid} and member_id!=#{uid}")
    public List<Integer> getMembersByTid(@Param("tid") Integer tid,@Param("uid") Integer uid);
    @Select("select creator_id from team where team_id=#{tid}")
    public Integer getCreatorByTid(Integer tid);
    @Select("select s_name from subscription where s_id=#{sid}")
    public String getSNameBySid(Integer sid);
    @Select("select t_name from team where team_id=#{tid}")
    public String getTNameByTid(Integer tid);
}
