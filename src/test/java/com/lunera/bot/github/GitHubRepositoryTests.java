package com.lunera.bot.github;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GitHubRepositoryTests {

    private GitHubRepository repo = new GitHubRepository();

    @Test
    public void GitHubController_WhenGETPayload_MustReturnsAStringWithTheUniqueLeader() {
        String expectedNameOfFirstTeam = "EQUIPO AA";
        List<Team> actualData = repo.getScoresFromJson();

        assertEquals(expectedNameOfFirstTeam, actualData.get(0).getName());
        assertEquals(10, actualData.size());
    }
}
