package edu.ncu.chattingsys.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/status")
public class StatusController {
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/updateUserStatus")
    public void updateUserStatus(Integer uid){
        redisTemplate.opsForValue().set(uid+"_status",1,5, TimeUnit.SECONDS);
    }
    @RequestMapping(value = "/getUserStatus")
    public Boolean getUserStatus(Integer uid){
        Object o = redisTemplate.opsForValue().get(uid+"_status");
        if (o!=null)    return true;
        else return false;
    }
}
