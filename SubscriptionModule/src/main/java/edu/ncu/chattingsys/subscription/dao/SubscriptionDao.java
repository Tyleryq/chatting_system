package edu.ncu.chattingsys.subscription.dao;

import edu.ncu.chattingsys.commonsapi.model.domain.Article;
import edu.ncu.chattingsys.commonsapi.model.domain.Subscription;
import edu.ncu.chattingsys.commonsapi.model.responseData.ArticleData;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface SubscriptionDao {
    @Insert("insert into article(s_id,title,content,keyword,p_date) values(#{s_id},#{title},#{content},#{keyword},now())")
    public Integer addArticle(@Param("s_id") Integer s_id,@Param("title") String title,@Param("content") String content,@Param("keyword") String keyword);
    @Insert("insert into subscribe_user(uid,s_id,s_date) values(#{uid},#{sid},now())")
    public void subscribe(@Param("uid") Integer uid,@Param("sid") Integer sid);
    @Delete("delete from subscribe_user where uid=#{uid} and s_id=#{sid}")
    public Integer offSubscribe(@Param("uid") Integer uid,@Param("sid")  Integer sid);
    @Select("select * from subscription where s_name=#{name}")
    public Subscription getSubscriptionByName(String name);
    @Select("select * from subscription where s_id=#{sid}")
    public Subscription getSubscriptionBySid(Integer sid);
    @Select("select * from article where s_id=#{sid}")
    public List<Article> getAllArticlesBySid(Integer sid);
    @Select("SELECT a.title,a.content,a.keyword,a.p_date,s.s_name name FROM article a JOIN subscription s ON a.s_id=s.s_id WHERE a.s_id=#{sid}")
    public List<ArticleData> getAllArticleDatasBySid(Integer sid);
    @Select("select s.* from subscription s where s.s_id in(select su.s_id from subscribe_user su where su.uid=#{uid})")
    public List<Subscription> getSubscriptionsByUid(Integer uid);
    @Select("select uid from subscribe_user where s_id=#{sid}")
    public List<Integer> getSubscribersBySid(Integer sid);
}
