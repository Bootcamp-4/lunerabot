package com.lunerabot.LuneraBot.github;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class GithubController {

	@PostMapping(path = "/payload")
	public String helloWorld() {
		return "Hello World!";
	}
}
