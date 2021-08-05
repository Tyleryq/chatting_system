package edu.ncu.chattingsys.inter.controller;

import edu.ncu.chattingsys.commonsapi.model.domain.Article;
import edu.ncu.chattingsys.commonsapi.model.domain.Subscription;
import edu.ncu.chattingsys.commonsapi.model.responseData.ArticleData;
import edu.ncu.chattingsys.inter.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;
    @RequestMapping(value = "/publishArticle")
    @ResponseBody
    public void publishArticle(@RequestParam("uid") Integer s_id,String title,String content,String keyword){
        subscriptionService.publishArticle(s_id, title, content, keyword);
    }
    @RequestMapping(value = "/getAllArticlesBySid")
    @ResponseBody
    public List<ArticleData> getAllArticlesBySid(Integer sid){
        return subscriptionService.getAllArticlesBySid(sid);
    }
    @RequestMapping(value = "/subscribe")
    @ResponseBody
    public void subscribe(Integer uid,Integer sid){
        subscriptionService.subscribe(uid, sid);
    }
    @RequestMapping(value = "/offSubscribe")
    @ResponseBody
    public void offSubscribe(Integer uid, Integer sid){
        subscriptionService.offSubscribe(uid, sid);
    }
    @RequestMapping(value = "/getSubscriptionByName")
    @ResponseBody
    public Subscription getSubscriptionByName(String name){
        return subscriptionService.getSubscriptionByName(name);
    }
    @RequestMapping(value = "/getSubscriptionBySid")
    @ResponseBody
    public Subscription getSubscriptionBySid(Integer sid){
        return subscriptionService.getSubscriptionBySid(sid);
    }
    @RequestMapping(value = "/getSubscriptionsByUid")
    @ResponseBody
    public List<Subscription> getSubscriptionsByUid(Integer uid){
        return subscriptionService.getSubscriptionsByUid(uid);
    }

    @RequestMapping(value = "/writeArticlePage")
    public String writeArticlePage(Integer uid,Model model){
        model.addAttribute("sid",uid);
        return "writeArticle";
    }
}
