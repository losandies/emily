package dev.brandon.backend.github;

import dev.brandon.backend.dto.GitHubInstallationTokenResponse;
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

    public GitHubInstallationTokenResponse createInstallationToken(long installationId) throws IOException, GeneralSecurityException {
        String jwt = gitHubAuthService.generateAppJWT();

        return restClient.post()
                .uri("/app/installations/{installationId}/access_tokens", installationId)
                .headers(headers -> headers.setBearerAuth(jwt))
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", "2026-03-10")
                .retrieve()
                .body(GitHubInstallationTokenResponse.class);
    }

    public String getRepositories(long installationId) throws IOException, GeneralSecurityException {
        GitHubInstallationTokenResponse response = createInstallationToken(installationId);

        return restClient.get()
                .uri("/installation/repositories")
                .headers(headers -> headers.setBearerAuth(response.getToken()))
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", "2026-03-10")
                .retrieve()
                .body(String.class);
    }

    public String getReadme(long installationId, String owner, String repo) throws IOException, GeneralSecurityException {
        GitHubInstallationTokenResponse response = createInstallationToken(installationId);

        return restClient.get()
                .uri("/repos/{owner}/{repo}/readme", owner, repo )
                .headers(headers -> headers.setBearerAuth(response.getToken()))
                .header("Accept", "application/vnd.github.raw+json")
                .header("X-GitHub-Api-Version", "2026-03-10")
                .retrieve()
                .body(String.class);
    }
}