package com.lunera.bot.github;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubPushEventDto {
	@JsonProperty("commits")
	private ArrayList<Commit> commits;

	public GitHubPushEventDto() {
	}

	@JsonProperty("modified")
	public List<Commit> getCommits() {
		return commits;
	}
}
