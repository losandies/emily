package dev.brandon.backend.github;

import dev.brandon.backend.dto.github.GithubCommitResponse;
import dev.brandon.backend.dto.github.RepositoryContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class RepositoryContextService {
    private final GitHubClient gitHubClient;

    public RepositoryContextService(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public RepositoryContext getRepositoryContext(long installationId, String owner, String repo) throws IOException, GeneralSecurityException {
        GithubCommitResponse[] commitResponse = gitHubClient.getRecentCommits(installationId, owner, repo);

        String readme = null;
        try {
            readme = gitHubClient.getReadme(installationId, owner, repo);
        } catch (HttpClientErrorException.NotFound exception) {
            // README unavailable; continue with the retrieved commits.
        }

        return new RepositoryContext(owner, repo, readme, commitResponse);
    }
}