package edu.ncu.chattingsys.commonsapi.model.responseData;

import edu.ncu.chattingsys.commonsapi.model.domain.Subscription;
import edu.ncu.chattingsys.commonsapi.model.domain.Team;
import edu.ncu.chattingsys.commonsapi.model.domain.User;

public class SearchResult {
    private User user;
    private Team team;
    private Subscription subscription;
    private String teamCreatorName;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public String getTeamCreatorName() {
        return teamCreatorName;
    }

    public void setTeamCreatorName(String teamCreatorName) {
        this.teamCreatorName = teamCreatorName;
    }
}
