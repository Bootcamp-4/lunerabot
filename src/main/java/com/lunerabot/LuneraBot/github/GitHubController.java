package com.lunerabot.LuneraBot.github;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class GitHubController {

	@PostMapping(path = "/payload")
	public Object getScores(@RequestBody GitHubPushEventDto event) {
		List<Commit> commits = event.getCommits();

		for (Commit commit : commits) {
			boolean containsTeamData = commit.getModifiedFiles().contains("src/main/resources/teamdata.json");

			if (containsTeamData) {
				return true;
			}
		}
		return false;
	}
}
