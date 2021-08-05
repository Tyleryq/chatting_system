package edu.ncu.chattingsys.inter.service;

import edu.ncu.chattingsys.commonsapi.model.domain.Subscription;
import edu.ncu.chattingsys.commonsapi.model.domain.User;
import edu.ncu.chattingsys.commonsapi.model.responseData.CommonData;
import edu.ncu.chattingsys.commonsapi.model.responseData.SearchResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("ACCOUNT-SERVICE")
public interface AccountService {
    @RequestMapping("/account/loginCheck")
    public CommonData loginCheck(@RequestParam("uid") String uid,@RequestParam("pwd") String pwd,@RequestParam("account_type") String account_type);
    @RequestMapping("/account/userRegister")
    public CommonData userRegister(@RequestBody User user,@RequestParam("spwd") String spwd);
    @RequestMapping(value = "/account/userResetPwd")
    public void userResetPwd(@RequestParam("uid") Integer uid,@RequestParam("pwd") String pwd);
    @RequestMapping(value = "/account/SubscriptionRegister")
    public CommonData SubscriptionRegister(@RequestBody Subscription subscription);
    @RequestMapping(value = "/account/search")
    public SearchResult search(@RequestParam("id") Integer id);
    @RequestMapping(value = "/account/findUnameByUid")
    public String findUnameByUid(@RequestParam("uid") Integer uid);
    @RequestMapping(value = "/account/findSnameBySid")
    public String findSnameBySid(@RequestParam("sid") Integer sid);
    @RequestMapping(value = "/account/deleteAcount")
    public void deleteAcount(@RequestParam("uid") Integer uid);
    @RequestMapping(value = "/account/checkNameExist")
    public CommonData checkNameExist(@RequestParam("name") String name);
}
