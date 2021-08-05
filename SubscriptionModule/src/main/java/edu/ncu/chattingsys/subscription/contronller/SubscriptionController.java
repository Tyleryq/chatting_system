package edu.ncu.chattingsys.subscription.contronller;

import edu.ncu.chattingsys.commonsapi.model.domain.Article;
import edu.ncu.chattingsys.commonsapi.model.domain.Subscription;
import edu.ncu.chattingsys.commonsapi.model.responseData.ArticleData;
import edu.ncu.chattingsys.subscription.service.MQSend;
import edu.ncu.chattingsys.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @RequestMapping(value = "/publishArticle")
    public void publishArticle(Integer s_id,String title,String content,String keyword){
        subscriptionService.publishArticle(s_id, title, content, keyword);
    }

    @RequestMapping(value = "/getAllArticlesBySid")
    public List<ArticleData> getAllArticlesBySid(Integer sid){
        return subscriptionService.getAllArticlesBySid(sid);
    }

    @RequestMapping(value = "/subscribe")
    public void subscribe(Integer uid,Integer sid){
        subscriptionService.subscribe(uid, sid);
    }

    @RequestMapping(value = "/offSubscribe")
    public void offSubscribe(Integer uid, Integer sid){
        subscriptionService.offSubscribe(uid, sid);
    }

    @RequestMapping(value = "/getSubscriptionByName")
    public Subscription getSubscriptionByName(String name){
        return subscriptionService.getSubscriptionByName(name);
    }

    @RequestMapping(value = "/getSubscriptionBySid")
    public Subscription getSubscriptionBySid(Integer sid){
        return subscriptionService.getSubscriptionBySid(sid);
    }

    @RequestMapping(value = "/getSubscriptionsByUid")
    public List<Subscription> getSubscriptionsByUid(Integer uid){
        return subscriptionService.getSubscriptionsByUid(uid);
    }
}
