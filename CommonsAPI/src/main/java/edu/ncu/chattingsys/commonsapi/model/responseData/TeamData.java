package edu.ncu.chattingsys.commonsapi.model.responseData;

import edu.ncu.chattingsys.commonsapi.model.domain.Team;

public class TeamData {
    private Team team;
    private String creator_name;
    private Integer team_num;//群人数

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public Integer getTeam_num() {
        return team_num;
    }

    public void setTeam_num(Integer team_num) {
        this.team_num = team_num;
    }
}
