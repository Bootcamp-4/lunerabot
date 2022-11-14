package com.lunerabot.LuneraBot.github;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class GitHubController {

	@PostMapping(path = "/payload")
	public String helloWorld() {
		return "Hello World!";
	}
}
