package com.lunerabot.LuneraBot.github;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Commit {
	@JsonProperty("modified")
	private ArrayList<String> modified;

	public Commit() {
	}

	@JsonProperty("modified")
	public List<String> getModifiedFiles() {
		return modified;
	}
}
