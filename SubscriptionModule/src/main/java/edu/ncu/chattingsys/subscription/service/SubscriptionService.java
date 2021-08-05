package edu.ncu.chattingsys.subscription.service;

import edu.ncu.chattingsys.commonsapi.model.domain.Article;
import edu.ncu.chattingsys.commonsapi.model.domain.Message;
import edu.ncu.chattingsys.commonsapi.model.domain.Subscription;
import edu.ncu.chattingsys.commonsapi.model.responseData.ArticleData;
import edu.ncu.chattingsys.subscription.dao.SubscriptionDao;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionDao subscriptionDao;
    @Autowired
    private MQSend mqSend;

    public void publishArticle(Integer s_id,String title,String content,String keyword){
        subscriptionDao.addArticle(s_id, title, content, keyword);
        List<Integer> subscribers=subscriptionDao.getSubscribersBySid(s_id);
        for(Integer subscribe:subscribers){//向订阅者推送
            Message message=new Message((short)1,content,s_id,subscribe);
            mqSend.send(message);
        }
    }
    public void subscribe(Integer uid,Integer sid){
        subscriptionDao.subscribe(uid, sid);
    }
    public void offSubscribe(Integer uid, Integer sid){
        subscriptionDao.offSubscribe(uid, sid);
    }
    public Subscription getSubscriptionByName(String name){
        Subscription subscription = subscriptionDao.getSubscriptionByName(name);
        if (subscription!=null) subscription.setPwd(null);
        return subscription;
    }

    public Subscription getSubscriptionBySid(Integer sid){
        Subscription subscription = subscriptionDao.getSubscriptionBySid(sid);
        if (subscription!=null) subscription.setPwd(null);
        return subscription;
    }
    public List<ArticleData> getAllArticlesBySid(Integer sid){
        return subscriptionDao.getAllArticleDatasBySid(sid);
    }

    public List<Subscription> getSubscriptionsByUid(Integer uid){
        return subscriptionDao.getSubscriptionsByUid(uid);
    }
}
