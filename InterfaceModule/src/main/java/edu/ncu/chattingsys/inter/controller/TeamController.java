package edu.ncu.chattingsys.inter.controller;

import edu.ncu.chattingsys.commonsapi.model.domain.Team;
import edu.ncu.chattingsys.commonsapi.model.responseData.CommonData;
import edu.ncu.chattingsys.commonsapi.model.responseData.TeamData;
import edu.ncu.chattingsys.inter.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class TeamController {
    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/createTeamPage")
    public String createTeamPage(Integer uid, String token, Model model){
        model.addAttribute("uid",uid);
        model.addAttribute("token",token);
        return "createTeam";
    }
    @RequestMapping(value = "/createTeam")
    @ResponseBody
    public CommonData createTeam(Integer uid, String t_name, String descript){
        return teamService.createTeam(uid, t_name, descript);
    }
    @RequestMapping(value = "/joinTeam")
    @ResponseBody
    public void joinTeam(Integer t_id,Integer uid){
        teamService.joinTeam(t_id, uid);
    }
    @RequestMapping(value = "/quitTeam")
    @ResponseBody
    public void quitTeam(Integer t_id,Integer uid){
        teamService.quitTeam(t_id, uid);
    }

    @RequestMapping(value = "/getTeamList")
    @ResponseBody
    public List<TeamData> getTeamList(Integer uid){
        return teamService.getTeamList(uid);
    }
}
