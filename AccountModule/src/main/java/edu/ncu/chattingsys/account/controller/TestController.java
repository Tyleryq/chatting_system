package edu.ncu.chattingsys.account.controller;

import edu.ncu.chattingsys.account.service.UserService;
import edu.ncu.chattingsys.commonsapi.model.responseData.CommonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@Slf4j
@RequestMapping(value = "/acc")
public class TestController {
    @Autowired
    UserService userService;
    @ResponseBody
    @RequestMapping("/test")
    public void test(){
        log.info("test");
    }
}
