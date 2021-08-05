package edu.ncu.chattingsys.inter.controller;

import com.alibaba.fastjson.JSONObject;
import edu.ncu.chattingsys.commonsapi.model.domain.Subscription;
import edu.ncu.chattingsys.commonsapi.model.domain.User;
import edu.ncu.chattingsys.commonsapi.model.responseData.CommonData;
import edu.ncu.chattingsys.commonsapi.model.responseData.SearchResult;
import edu.ncu.chattingsys.inter.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }
    @ResponseBody
    @RequestMapping(value = "/loginCheck")
    public CommonData loginCheck(String uid, String pwd, String account_type){
        return accountService.loginCheck(uid,pwd,account_type);
    }


    @RequestMapping(value = "/registerPage")
    public String registerPage(){
        return "register";
    }

    @RequestMapping(value = "/userRegister")
    @ResponseBody
    public CommonData userRegister(User user, String spwd, String mailCode, String sureCode, HttpServletRequest request){
        String verifyCode=(String) request.getSession().getAttribute("verifyCode");
        if (!sureCode.equals(verifyCode))   return CommonData.fail("验证码错误");
        if(!mailCode.equals(request.getSession().getAttribute("mailCode")))     return CommonData.fail("邮箱验证码错误");
        return accountService.userRegister(user,spwd);
    }
    @RequestMapping(value = "/userResetPwd")
    public void userResetPwd(Integer uid,String pwd){
        accountService.userResetPwd(uid, pwd);
    }
    @RequestMapping(value = "/main")
    public String main(Integer uid,Model model){
        model.addAttribute("uid",uid);
        return "main";
    }

    @RequestMapping(value = "/subscriptionRegisterPage")
    public String subscriptionRegisterPage(){
        return "subscriptionRegister";
    }

    @RequestMapping(value = "/SubscriptionRegister")
    @ResponseBody
    public CommonData SubscriptionRegister(Subscription subscription,String spwd,String sureCode,HttpServletRequest request){
        if (!subscription.getPwd().equals(spwd))    return CommonData.fail("两次密码输入不一致!");
        String verifyCode=(String) request.getSession().getAttribute("verifyCode");
        if (!sureCode.equals(verifyCode))   return CommonData.fail("验证码错误");
        return accountService.SubscriptionRegister(subscription);
    }

    @RequestMapping(value = "/subscriptionMain")
    public String subscriptionMain(Integer uid,Model model){
        model.addAttribute("sid",uid);
        return "subscriptionMain";
    }

    @RequestMapping(value = "/search")
    @ResponseBody
    public SearchResult search(Integer id){
        return accountService.search(id);
    }

    @RequestMapping(value = "/findUnameByUid")
    @ResponseBody
    public String findUnameByUid(Integer uid){
        return accountService.findUnameByUid(uid);
    }

    @RequestMapping(value = "/findSnameBySid")
    @ResponseBody
    public String findSnameBySid(@RequestParam("uid") Integer sid){
        return accountService.findSnameBySid(sid);
    }

    @RequestMapping(value = "/deleteAcount")
    @ResponseBody
    public void deleteAcount(Integer uid){
        accountService.deleteAcount(uid);
    }

    @RequestMapping(value = "/checkNameExist")
    @ResponseBody
    public CommonData checkNameExist(String name){
        return accountService.checkNameExist(name);
    }
}
