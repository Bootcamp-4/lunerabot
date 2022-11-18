package com.lunera.bot.github;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class GitHubController {

	@Autowired
	private GitHubService service;

	@PostMapping(path = "/payload")
	public void getLeader(@RequestBody GitHubPushEventDto event) {
		List<Commit> commits = event.getCommits();
		service.getLeader(commits);
	}
}
