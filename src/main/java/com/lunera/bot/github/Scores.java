package com.lunera.bot.github;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Scores {
    private List<Team> teamData;

    @JsonCreator
    public Scores(@JsonProperty("teamdata") List<Team> teamData) {
        this.teamData = teamData;
    }

    public List<Team> getTeamData() {
        return teamData;
    }

    public void setTeamData(List<Team> teamData) {
        this.teamData = teamData;
    }
}
