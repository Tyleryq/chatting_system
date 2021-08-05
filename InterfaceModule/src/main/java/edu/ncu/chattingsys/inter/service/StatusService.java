package edu.ncu.chattingsys.inter.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("STATUS-SERVICE")
public interface StatusService {
    @RequestMapping(value = "/status/updateUserStatus")
    public void updateUserStatus(@RequestParam("uid") Integer uid);
    @RequestMapping(value = "/status/getUserStatus")
    public Boolean getUserStatus(@RequestParam("uid") Integer uid);
}
