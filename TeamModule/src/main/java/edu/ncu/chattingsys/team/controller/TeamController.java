package edu.ncu.chattingsys.team.controller;

import edu.ncu.chattingsys.commonsapi.model.domain.Team;
import edu.ncu.chattingsys.commonsapi.model.responseData.CommonData;
import edu.ncu.chattingsys.commonsapi.model.responseData.TeamData;
import edu.ncu.chattingsys.team.dao.TeamDao;
import edu.ncu.chattingsys.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/team")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @RequestMapping(value = "/createTeam")
    public CommonData createTeam(Integer uid,String t_name,String descript){
        Integer t_id = teamService.createTeam(uid,t_name,descript);
        if (t_id==null) return CommonData.fail("创建失败");
        return CommonData.sucess("创建成功!群号为:"+t_id);
    }

    @RequestMapping(value = "/joinTeam")
    public void joinTeam(Integer t_id,Integer uid){
        teamService.addTeamMember(t_id,uid);
    }

    @RequestMapping(value = "/quitTeam")
    public void quitTeam(Integer t_id,Integer uid){
        teamService.deleteTeamMember(t_id, uid);
    }

    @RequestMapping(value = "/getTeamList")
    public List<TeamData> getTeamList(Integer uid){return teamService.getTeamList(uid);}
}
