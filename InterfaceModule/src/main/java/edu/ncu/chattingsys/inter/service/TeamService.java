package edu.ncu.chattingsys.inter.service;

import edu.ncu.chattingsys.commonsapi.model.domain.Team;
import edu.ncu.chattingsys.commonsapi.model.responseData.CommonData;
import edu.ncu.chattingsys.commonsapi.model.responseData.TeamData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("TEAM-SERVICE")
public interface TeamService {
    @RequestMapping(value = "/team/createTeam")
    public CommonData createTeam(@RequestParam("uid") Integer uid,@RequestParam("t_name") String t_name,@RequestParam("descript") String descript);
    @RequestMapping(value = "/team/joinTeam")
    public void joinTeam(@RequestParam("t_id") Integer t_id,@RequestParam("uid") Integer uid);
    @RequestMapping(value = "/team/quitTeam")
    public void quitTeam(@RequestParam("t_id") Integer t_id,@RequestParam("uid") Integer uid);
    @RequestMapping(value = "/team/getTeamList")
    public List<TeamData> getTeamList(@RequestParam("uid") Integer uid);
}
