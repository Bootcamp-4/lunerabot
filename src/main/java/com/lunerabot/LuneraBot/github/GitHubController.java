package com.lunerabot.LuneraBot.github;

import org.springframework.web.bind.annotation.RestController;

import com.lunerabot.LuneraBot.service.TelegramBoot;
import com.lunerabot.LuneraBot.utils.JsonReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
		JSONArray json = jsonReader.getJsonToString().getJSONArray("teamdata");
		Map<String, Integer> scores = new HashMap();

		for (Object o : json) {
			JSONObject jsonLineItem = (JSONObject) o;
			String key = jsonLineItem.getString("name");
			JSONArray actividades = jsonLineItem.getJSONArray("actividades");
			int teamScore = pointsCalculator(actividades);
			scores.put(key, teamScore);
		}
		Map<String, Integer> winners = getLeader(scores);
		if (winners.size() > 1) {
			return "The winners are ";
		}
		return "The leader is " + winners.get(0);
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

	public static Map<String, Integer> getLeader(Map<String, Integer> scores) {
		Map<String, Integer> leaders = new HashMap();
		int valor = 0;
		for (String i : scores.keySet()) {
			if (scores.get(i) > valor) {
				leaders.clear();
				leaders.put(i, scores.get(i));
				valor = scores.get(i);
			} else if (scores.get(i) == valor) {
				leaders.put(i, scores.get(i));
			}
		}
		return leaders;
	}
}
