package edu.ncu.chattingsys.zone.dao;

import edu.ncu.chattingsys.commonsapi.model.domain.Zone;
import edu.ncu.chattingsys.commonsapi.model.responseData.ZoneComment;
import org.apache.ibatis.annotations.*;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

@Mapper
public interface ZoneDao {
    @Insert("insert into zone(uid,content,p_date) values(#{uid},#{content},now())")
    public Integer addZone(@Param("uid") Integer uid,@Param("content") String content);
    @Delete("delete from zone_comment where zone_id=#{zid}")
    public Integer deleteAllCommentByZid(Integer zid);
    @Delete("delete from zone_like where zone_id=#{zid}")
    public Integer deleteAllLikeByZid(Integer zid);
    @Delete("delete from zone where zone_id=#{zid} and uid=#{uid}")
    public Integer deleteZone(@Param("zid") Integer zid,@Param("uid") Integer uid);
    @Insert("insert into zone_like(zone_id,uid) values(#{zid},#{uid})")
    public Integer addLikeZone(@Param("zid") Integer zid,@Param("uid") Integer uid);
    @Select("select uid from zone where zone_id=#{zid}")
    public Integer getUidByZid(Integer zid);
    @Insert("insert into zone_comment(zone_id,uid,content,time) values(#{zid},#{uid},#{content},now())")
    public Integer addComment(@Param("zid") Integer zid,@Param("uid") Integer uid,@Param("content") String content);
    @Update("update friendship set zone_permission=#{p} where uid=#{uid} and f_id=#{fid}")
    public Integer setZonePermission(@Param("uid") Integer uid,@Param("fid") Integer fid,@Param("p") Boolean p);
    @Select("select z.* from zone z where z.uid=#{uid} or z.uid in(select f.f_id from friendship f where uid=#{uid}) order by z.p_date DESC")
    public List<Zone> getFriendsZonesByUid(Integer uid);
    @Select("select uname from user where uid=#{uid}")
    public String getUnameByUid(Integer uid);
    @Select("select u.uname,zc.content from zone_comment zc join user u on zc.uid=u.uid where zc.zone_id=#{zid}")
    public List<ZoneComment> getCommentByZid(Integer zid);
    @Select("select count(*) from zone_like where zone_id=#{zid}")
    public Integer getLikeNumByZid(Integer zid);
    @Select("select u.uname from zone_like zl join user u on zl.uid=u.uid where zl.zone_id=#{zid}")
    public List<String> getLikeNamesByZid(Integer zid);
    @Select("select * from zone where uid=#{fid}")
    public List<Zone> getFriendZonesByFid(Integer fid);
    @Select("select f_id from friendship where uid=#{uid}")
    public List<Integer> getFriendsIdByUid(Integer uid);
    @Select("select zone_permission from friendship where uid=#{fid} and f_id=#{uid}")
    public Boolean getZonePermissionByFid(@Param("uid") Integer uid,@Param("fid") Integer fid);
}
