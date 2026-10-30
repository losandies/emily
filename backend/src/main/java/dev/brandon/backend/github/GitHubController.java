package dev.brandon.backend.github;

import dev.brandon.backend.dto.GitHubInstallationTokenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class GitHubController {
    private GitHubClient gitHubClient;

    public GitHubController(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
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
}