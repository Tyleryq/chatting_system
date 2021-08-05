package edu.ncu.chattingsys.inter.service;

import edu.ncu.chattingsys.commonsapi.model.domain.Article;
import edu.ncu.chattingsys.commonsapi.model.domain.Subscription;
import edu.ncu.chattingsys.commonsapi.model.responseData.ArticleData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("SUBSCRIPTION-SERVICE")
public interface SubscriptionService {
    @RequestMapping(value = "/subscription/publishArticle")
    public void publishArticle(@RequestParam("s_id") Integer s_id, @RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("keyword") String keyword);
    @RequestMapping(value = "/subscription/getAllArticlesBySid")
    public List<ArticleData> getAllArticlesBySid(@RequestParam("sid") Integer sid);
    @RequestMapping(value = "/subscription/subscribe")
    public void subscribe(@RequestParam("uid") Integer uid,@RequestParam("sid") Integer sid);
    @RequestMapping(value = "/subscription/offSubscribe")
    public void offSubscribe(@RequestParam("uid") Integer uid, @RequestParam("sid") Integer sid);
    @RequestMapping(value = "/subscription/getSubscriptionByName")
    public Subscription getSubscriptionByName(@RequestParam("name") String name);
    @RequestMapping(value = "/subscription/getSubscriptionBySid")
    public Subscription getSubscriptionBySid(@RequestParam("sid") Integer sid);
    @RequestMapping(value = "/subscription/getSubscriptionsByUid")
    public List<Subscription> getSubscriptionsByUid(@RequestParam("uid") Integer uid);
}
