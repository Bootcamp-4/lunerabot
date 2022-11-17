package com.lunerabot.LuneraBot.github;

import com.lunerabot.LuneraBot.service.TelegramBoot;

import org.apache.commons.codec.binary.StringUtils;
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

    //This method return obtain the JSON from the URL and print the leaders.
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

    /*This method obtains teams and create a Map<String, Integer> called teamScore with Teams 
      names as key and its points as values. */
    public Map<String, Integer> getTeamScore() {
        List<Team> teams = repo.getScoresFromJson();
        Map<String, Integer> TeamScore = new HashMap();

        for (Team team : teams) {
            List<Task> tasks = team.getTasks();
            TeamScore.put(team.getName(), pointsCalculator(tasks)); //The method pointsCalculator return the total point of the team.
        }
        
        return TeamScore;
    }
        
        public String getMessage(Map<String, Integer> teamScore) {
        	String message = "";
        String[] leaders = getLeaders(teamScore).split("/");
        String equipos = leaders[0];
        if(equipos.split(",").length>1) {
        	message = "There is a tie between" + equipos.substring(0, equipos.length()-1) + "with " + leaders[1] + " points.";	
        }else {
        	message = "The leader is " + equipos.substring(0, equipos.length()-1) + "with " + leaders[1] + " points.";	
        }
        System.out.println(message);
        return message;
    }

        //This method calculate the points of a team with a list of task as entry.
    public static int pointsCalculator(List<Task> tasks) {
        int res = 0;
        for (Task task : tasks) {
            res += task.getScore();
        }
        return res;
    }
    
    
    public static String getLeaders(Map<String, Integer> teams) {
    	String leaders = "";
    	int maxValue = -1;
    	for(String i: teams.keySet()) {
    		if(teams.get(i) == maxValue) {
    			leaders += " " + i ;
    		}else if(teams.get(i) > maxValue){
    			leaders = " " + i + ",";
    			maxValue = teams.get(i);
    		}
    	}
    		leaders += "/" + maxValue;
    	return leaders;
    }
    

//    public static <K, V extends Comparable<? super V>> Map<String, Integer> sortByValue(Map<String, Integer> map) {
//        Map<String, Integer> m = map.entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue,
//                        (e1, e2) -> e1,
//                        LinkedHashMap::new
//                ));
//        Map<String, Integer> finalResult = new HashMap<>();
//        int maxValue = -1;
//        for (Map.Entry<String, Integer> entry : m.entrySet()) {
//            if (maxValue < 0) {
//                maxValue = entry.getValue();
//            }
//            if (entry.getValue() >= maxValue) {
//                finalResult.put(entry.getKey(), entry.getValue());
//            } else {
//                break;
//            }
//        }
//        return finalResult;
//    }
    
    //GETMESSAGE
//  Map<String, Integer> orderedTeamsByTotalScore = sortByValue(scores);
//  Iterator<String> leadersIterator = orderedTeamsByTotalScore.keySet().iterator();
//  List<String> leaders = new ArrayList<>();
//
//  while (leadersIterator.hasNext()) {
//      leaders.add(leadersIterator.next());
//  }
//
//  if (orderedTeamsByTotalScore.size() > 1) {
//      message = "There is a tie between ";
//      for (String leader : leaders) {
//          message += leader + ", ";
//      }
//
//      message += "with " + orderedTeamsByTotalScore.get(leaders.get(0)) + " points.";
//
//  } else {
//      message += "Leader is " + leaders.get(0) + " with " + orderedTeamsByTotalScore.get(leaders.get(0)) + " points.";
//  }
}
