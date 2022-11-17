package com.lunerabot.LuneraBot.github;

import com.lunerabot.LuneraBot.service.TelegramBoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    @Autowired
    private GitHubRepository repo;

    @Autowired
    private TelegramBoot bot;

    public void getLeader(List<Commit> commits) {
        for (Commit commit : commits) {
            boolean containsTeamData = commit.getModifiedFiles().contains("src/main/resources/teamdata.json");

            if (containsTeamData) {
                String message = getMessage();
                bot.updateScores(message);
            }
        }
    }

    public String getMessage() {
        String message = "";
        List<Team> teams = repo.getScoresFromJson();
        Map<String, Integer> scores = new HashMap();

        for (Team team : teams) {
            List<Task> tasks = team.getTasks();
            scores.put(team.getName(), pointsCalculator(tasks));
        }

        Map<String, Integer> orderedTeamsByTotalScore = sortByValue(scores);
        Iterator<String> leadersIterator = orderedTeamsByTotalScore.keySet().iterator();
        List<String> leaders = new ArrayList<>();

        while (leadersIterator.hasNext()) {
            leaders.add(leadersIterator.next());
        }

        if (orderedTeamsByTotalScore.size() > 1) {
            message = "There is a tie between ";
            for (String leader : leaders) {
                message += leader + ", ";
            }

            message += "with " + orderedTeamsByTotalScore.get(leaders.get(0)) + " points.";

        } else {
            message += "Leader is " + leaders.get(0) + " with " + orderedTeamsByTotalScore.get(leaders.get(0)) + " points.";
        }

        return message;
    }

    public static int pointsCalculator(List<Task> tasks) {
        int res = 0;
        for (Task task : tasks) {
            res += task.getScore();
        }
        return res;
    }

    public static <K, V extends Comparable<? super V>> Map<String, Integer> sortByValue(Map<String, Integer> map) {
        Map<String, Integer> m = map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        Map<String, Integer> finalResult = new HashMap<>();
        int maxValue = -1;
        for (Map.Entry<String, Integer> entry : m.entrySet()) {
            if (maxValue < 0) {
                maxValue = entry.getValue();
            }
            if (entry.getValue() >= maxValue) {
                finalResult.put(entry.getKey(), entry.getValue());
            } else {
                break;
            }
        }
        return finalResult;
    }
}
