package com.lunera.bot.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.List;

@Repository
public class GitHubRepository {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Team> getScoresFromJson() {
        try {
            Scores scores =
                    mapper.readValue(new URL("https://raw.githubusercontent.com/Bootcamp-4/lunerabot/main/src/main/resources/teamdata.json"), Scores.class);
            return scores.getTeamData();
        } catch (Exception e) {
            return null;
        }
    }
}
