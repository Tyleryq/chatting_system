package edu.ncu.chattingsys.inter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/zoneListPage")
    public String zoneListPage(Integer fid,Integer uid,String token, Model model){
        model.addAttribute("fid",fid);
        model.addAttribute("uid",uid);
        model.addAttribute("token",token);
        return "zoneList";
    }

    @RequestMapping("/articleListPage")
    public String articleListPage(Integer uid,String token,Integer sid,Model model){
        model.addAttribute("uid",uid);
        model.addAttribute("token",token);
        model.addAttribute("sid",sid);
        return "articleList";
    }
}
