package dev.brandon.backend.github;

import dev.brandon.backend.dto.github.GithubCommitResponse;
import dev.brandon.backend.dto.github.RepositoryContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class GitHubController {
    private GitHubClient gitHubClient;
    private RepositoryContextService repositoryContextService;

    public GitHubController(GitHubClient gitHubClient, RepositoryContextService repositoryContextService) {
        this.gitHubClient = gitHubClient;
        this.repositoryContextService = repositoryContextService;
    }

    @GetMapping("/api/github/app")
    public String getClient() throws IOException, GeneralSecurityException {
        return gitHubClient.getAuthenticatedApp();
    }

    @GetMapping("/api/github/app/installations")
    public String getInstallations() throws IOException, GeneralSecurityException {
        return gitHubClient.getInstallations();
    }

//    @PostMapping("/api/github/app/installations/{installationId}/access_tokens")
//    public GitHubInstallationTokenResponse getAccessTokens(@PathVariable long installationId ) throws IOException, GeneralSecurityException{
//        return gitHubClient.createInstallationToken(installationId);
//    }

    @GetMapping("/api/github/installations/{installationId}/repositories")
    public String getRepositories(@PathVariable long installationId) throws IOException, GeneralSecurityException {
        return gitHubClient.getRepositories(installationId);
    }

    @GetMapping("/api/github/installations/{installationId}/repos/{owner}/{repo}/readme")
    public String getReadme(@PathVariable long installationId, @PathVariable String owner, @PathVariable String repo ) throws IOException, GeneralSecurityException {
        return gitHubClient.getReadme(installationId, owner, repo);
    }

    @GetMapping("/api/github/installations/{installationId}/repos/{owner}/{repo}/commits")
    public GithubCommitResponse[] getCommits(@PathVariable long installationId, @PathVariable String owner, @PathVariable  String repo) throws IOException, GeneralSecurityException {
        return gitHubClient.getRecentCommits(installationId, owner, repo);
    }

    @GetMapping("/api/github/installations/{installationId}/repos/{owner}/{repo}/context")
    public RepositoryContext getContext(
            @PathVariable long installationId,
            @PathVariable String owner,
            @PathVariable String repo
    ) throws IOException, GeneralSecurityException {
        return repositoryContextService.getRepositoryContext(
                installationId, owner, repo);
    }
}