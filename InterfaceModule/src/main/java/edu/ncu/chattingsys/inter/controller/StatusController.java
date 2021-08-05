package edu.ncu.chattingsys.inter.controller;

import edu.ncu.chattingsys.inter.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
    @Autowired
    private StatusService statusService;
    @RequestMapping(value = "/updateUserStatus")
    public void updateUserStatus(Integer uid){
        statusService.updateUserStatus(uid);
    }
    @RequestMapping(value = "/getUserStatus")
    public Boolean getUserStatus(Integer uid){
        return statusService.getUserStatus(uid);
    }
}
