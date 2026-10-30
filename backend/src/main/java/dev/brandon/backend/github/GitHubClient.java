package dev.brandon.backend.github;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class GitHubClient {
    private GitHubAuthService gitHubAuthService;
    private final RestClient restClient = RestClient.create("https://api.github.com");

    public GitHubClient(GitHubAuthService gitHubAuthService) {
        this.gitHubAuthService = gitHubAuthService;
    }

    public String getAuthenticatedApp() throws IOException, GeneralSecurityException {
        String jwt = gitHubAuthService.generateAppJWT();

        return restClient.get()
                .uri("/app")
                .headers(headers -> headers.setBearerAuth(jwt))
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", "2026-03-10")
                .retrieve()
                .body(String.class);
    }

    public String getInstallations() throws IOException, GeneralSecurityException {
        String jwt = gitHubAuthService.generateAppJWT();

        return restClient.get()
                .uri("/app/installations")
                .headers(headers -> headers.setBearerAuth(jwt))
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", "2026-03-10")
                .retrieve()
                .body(String.class);
    }

    public String createInstallationToken(long installationId) throws IOException, GeneralSecurityException {
        String jwt = gitHubAuthService.generateAppJWT();

        return restClient.post()
                .uri("/app/installations/{installationId}/access_tokens", installationId)
                .headers(headers -> headers.setBearerAuth(jwt))
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", "2026-03-10")
                .retrieve()
                .body(String.class);
    }
}