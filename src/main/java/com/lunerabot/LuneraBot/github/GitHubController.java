package com.lunerabot.LuneraBot.github;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class GitHubController {

	@PostMapping(path = "/payload")
	public List<Commit> getScores(@RequestBody GitHubPushEventDto event) {
		List<Commit> commits = event.getCommits();
		return commits;
	}
}
