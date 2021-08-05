package edu.ncu.chattingsys.team.service;

import edu.ncu.chattingsys.commonsapi.model.domain.Team;
import edu.ncu.chattingsys.commonsapi.model.responseData.TeamData;
import edu.ncu.chattingsys.team.dao.TeamDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TeamService {
    @Autowired
    private TeamDao teamDao;
    public Integer createTeam(Integer uid,String t_name,String descript){ //返回群号
        Date dateTime=new Date();
        DateFormat df=DateFormat.getDateTimeInstance();
        String time=df.format(dateTime);
        Integer t = teamDao.createTeam(uid,t_name,descript,time);
        if (t==0){
//            log.info("t is null");
            return null;
        }

//        log.info(time);
        return teamDao.getTNumByUidAndTime(uid,time);
    }
    public void addTeamMember(Integer t_id,Integer uid){
        teamDao.addTeamMember(t_id,uid);
    }
    public void deleteTeamMember(Integer t_id,Integer uid){
        teamDao.deleteTeamMember(t_id, uid);
    }

    public List<TeamData> getTeamList(Integer uid){
//        log.info(uid+"");
        List<Team> teams=teamDao.selectTeamListByUid(uid);
        if (teams==null)    return null;
        List<TeamData> teamDataList=new LinkedList<>();
        for(Team team:teams){
            TeamData teamData=new TeamData();
            teamData.setTeam(team);
            teamData.setCreator_name(teamDao.findNameByUid(team.getCreator_id()));
            teamData.setTeam_num(teamDao.getTeamNumByTid(team.getTeam_id()));
            teamDataList.add(teamData);
        }
//        log.info(teamDataList.size()+"");
//        log.info(teams.size()+"");
        return teamDataList;
    }
}
