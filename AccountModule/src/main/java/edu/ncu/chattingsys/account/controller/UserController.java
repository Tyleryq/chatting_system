package edu.ncu.chattingsys.account.controller;


import edu.ncu.chattingsys.account.service.UserService;
import edu.ncu.chattingsys.commonsapi.model.domain.Subscription;
import edu.ncu.chattingsys.commonsapi.model.domain.User;
import edu.ncu.chattingsys.commonsapi.model.responseData.CommonData;
import edu.ncu.chattingsys.commonsapi.model.responseData.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping(value = "/account")
public class UserController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/loginCheck")
    public CommonData loginCheck(String uid, String pwd, String account_type){
        log.info("login");
        if (uid==null||uid.isEmpty())  return CommonData.fail("用户名不能为空！");
        Object logFailTimes = redisTemplate.opsForValue().get(uid+"_logFailTimes");
        if (logFailTimes==null||Integer.parseInt(logFailTimes.toString())<10){
            Integer IUid = Integer.parseInt(uid);
            if (userService.checkPwd(IUid,pwd,account_type)){
                String token=uid.hashCode()+"";//为了简便,使用uid的hashCode做token
                redisTemplate.opsForValue().set(uid+"_token",token,7,TimeUnit.DAYS);
//                log.info("key:"+uid+"_token");
//                log.info(redisTemplate.opsForValue().get(uid+"_token").toString());
                return CommonData.sucess(token);
            }
            else {
                if (logFailTimes==null){
                    redisTemplate.opsForValue().set(uid+"_logFailTimes",1,24,TimeUnit.HOURS);
                }else {
                    redisTemplate.opsForValue().set(uid+"_logFailTimes",Integer.parseInt(logFailTimes.toString())+1,24,TimeUnit.HOURS);
                }
                return CommonData.fail("用户名或密码错误！");
            }

        }else {
            return CommonData.fail("该账号已被锁定，请24h后再试！");
        }
    }

    @RequestMapping(value = "/userResetPwd")
    public void userResetPwd(Integer uid,String pwd){
        userService.userResetPwd(uid, pwd);
    }

    @RequestMapping(value = "/userRegister")
    public CommonData userRegister(@RequestBody User user, String spwd){
        if (!user.getPwd().equals(spwd))    return CommonData.fail("两次密码输入不一致!");
        int code = userService.register(user);
        if (code==0){
            Integer uid=userService.findUidByName(user.getUname());
            return CommonData.sucess("注册成功!账号为:"+uid);
        } else if (code==1)
            return CommonData.fail("注册失败,用户名已存在!");
        else
            return CommonData.fail("服务繁忙,稍后再试!");
    }

    @RequestMapping(value = "/SubscriptionRegister")
    public CommonData SubscriptionRegister(@RequestBody Subscription subscription){
        return userService.SubscriptionRegister(subscription);
    }

    @RequestMapping(value = "/search")
    public SearchResult search(Integer id){
        return userService.search(id);
    }

    @RequestMapping(value = "/findUnameByUid")
    public String findUnameByUid(Integer uid){
        return userService.findUnameByUid(uid);
    }

    @RequestMapping(value = "/findSnameBySid")
    public String findSnameBySid(Integer sid){
        return userService.findSnameBySid(sid);
    }

    @RequestMapping(value = "/deleteAcount")
    public void deleteAcount(Integer uid){
        userService.deleteAcount(uid);
    }

    @RequestMapping(value = "/checkNameExist")
    public CommonData checkNameExist(String name){
        Integer uid=userService.findUidByName(name);
        if (uid!=null) return CommonData.fail("用户名已存在!");
        else return CommonData.sucess();
    }
}
