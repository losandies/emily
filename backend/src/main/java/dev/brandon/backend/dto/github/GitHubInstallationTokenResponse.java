package dev.brandon.backend.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubInstallationTokenResponse {
    private  String token;
    @JsonProperty("expires_at")
    private String expiresAt;

    public GitHubInstallationTokenResponse() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }
}