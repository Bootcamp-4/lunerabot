package com.lunera.bot.github;

import com.lunera.bot.telegram.Bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GitHubService {

    @Autowired
    private GitHubRepository repo;

    @Autowired
    private Bot bot;

    // This method return obtain the JSON from the URL and print the leaders
    public void getLeader(List<Commit> commits) {
        for (Commit commit : commits) {
            boolean containsTeamData = commit.getModifiedFiles().contains("src/main/resources/teamdata.json");

            if (containsTeamData) {
                Map<String, Integer> teamScore = getTeamScore();
                String message = getMessage(teamScore);
                bot.updateScores(message);
            }
        }
    }

    // This method obtains teams and create a Map<String, Integer> called teamScore with Teams
    // names as key and its points as values
    public Map<String, Integer> getTeamScore() {
        List<Team> teams = repo.getScoresFromJson();
        Map<String, Integer> teamScore = new HashMap<>();

        for (Team team : teams) {
            List<Task> tasks = team.getTasks();
            teamScore.put(team.getName(), pointsCalculator(tasks)); //The method pointsCalculator return the total point of the team.
        }

        return teamScore;
    }

    // This method calculate the points of a team with a list of task as entry
    public static int pointsCalculator(List<Task> tasks) {
        int score = 0;
        for (Task task : tasks) {
            score += task.getScore();
        }
        return score;
    }

    public String getMessage(Map<String, Integer> teamScore) {
        String[] leadersWithScoreAtTheEnd = getLeadersStringWithScore(teamScore).split("/");
        String teams = leadersWithScoreAtTheEnd[0];
        String teamsNames = teams.substring(0, teams.length() - 1).toUpperCase();

        if (teams.split(",").length > 1) {
            return "There is a tie!\n\uD83E\uDD47 |" + teamsNames + "\n\uD83C\uDF96 | " + leadersWithScoreAtTheEnd[1] + " POINTS";
        }

        return "The leader is:\n\uD83E\uDD47 |" + teamsNames + "\n\uD83C\uDF96 | " + leadersWithScoreAtTheEnd[1] + " POINTS";
    }

    public static String getLeadersStringWithScore(Map<String, Integer> teams) {
        String leaders = "";
        int maxScore = -1;
        for (String i : teams.keySet()) {
            if (teams.get(i) == maxScore) {
                leaders += " " + i + ",";
            } else if (teams.get(i) > maxScore) {
                leaders = " " + i + ",";
                maxScore = teams.get(i);
            }
        }
        leaders += "/" + maxScore;
        return leaders;
    }
}
