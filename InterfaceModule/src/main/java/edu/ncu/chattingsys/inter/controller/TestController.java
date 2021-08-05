package edu.ncu.chattingsys.inter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @RequestMapping("/testPage")
    public String testPage(){
        return "test";
    }
    @ResponseBody
    @RequestMapping("/test")
    public void test(){

    }
}
