package com.lunerabot.LuneraBot.github;

import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Iterators;
import com.lunerabot.LuneraBot.service.TelegramBoot;
import com.lunerabot.LuneraBot.utils.JsonReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class GitHubController {

	@Autowired
	private TelegramBoot bot;

	private static JsonReader jsonReader = new JsonReader();

	@PostMapping(path = "/payload")
	public void getScores(@RequestBody GitHubPushEventDto event) {
		List<Commit> commits = event.getCommits();

		for (Commit commit : commits) {
			boolean containsTeamData = commit.getModifiedFiles().contains("src/main/resources/teamdata.json");

			if (containsTeamData) {
				bot.updateScores();
			}
		}
	}

	public static String getScores() {
		String salida = "";
		JSONArray json = jsonReader.getJsonToString().getJSONArray("teamdata");
		Map<String, Integer> scores = new HashMap();

		for (Object o : json) {
			JSONObject jsonLineItem = (JSONObject) o;
			JSONArray actividades = jsonLineItem.getJSONArray("actividades");
			scores.put(jsonLineItem.getString("name"), pointsCalculator(actividades));
		}
		Map<String, Integer> winners = sortByValue(scores);
		Iterator<String> it = winners.keySet().iterator();
		List<String> equipos = new ArrayList<>();
		while(it.hasNext()) {
			equipos.add(it.next());
		}
		if (winners.size() > 1) {
				salida = "There is a tie between ";
				for(String i: equipos) {
				salida += i + ", ";
				}
				salida += "with " + winners.get(equipos.get(0)) + " points.";
				
		}else {
			salida += "Leader is " + equipos.get(0) + " with " + winners.get(equipos.get(0)) + " points.";
		}
		return salida;
	}

	public static int pointsCalculator(JSONArray act) {
		int res = 0;
		for (int i = 0; i < act.length(); i++) {
			JSONObject ps = (JSONObject) act.get(i);
			int points = ps.getInt("puntos");
			res += points;
		}
		return res;
	}

//	public static Map<String, Integer> getLeader(Map<String, Integer> scores) {
//		Map<String, Integer> leaders = new HashMap();
//		int valor = 0;
//		for (String i : scores.keySet()) {
//			if (scores.get(i) > valor) {
//				leaders.clear();
//				leaders.put(i, scores.get(i));
//				valor = scores.get(i);
//			} else if (scores.get(i) == valor) {
//				leaders.put(i, scores.get(i));
//			}
//		}
//		return leaders;
//	}
//	
	
	public static <K, V extends Comparable<? super V>> Map<String, Integer> sortByValue(Map<String, Integer> map) {
	    Map<String,Integer> m =  map.entrySet()
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
	    for(Entry<String, Integer> entry: m.entrySet()){
	        if(maxValue<0){
	            maxValue = entry.getValue();
	        }
	        if(entry.getValue()>=maxValue){
	            finalResult.put(entry.getKey(),entry.getValue());
	        }else{
	            break;
	        }
	    }
	    
	    return finalResult;
	}
		
	
	
}
