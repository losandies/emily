package dev.brandon.backend.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class GithubCommitResponse {
    private String sha;
    @JsonProperty("html_url")
    private String htmlUrl;
    private CommitDetails commit;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public CommitDetails getCommit() {
        return commit;
    }

    public void setCommit(CommitDetails commit) {
        this.commit = commit;
    }

    public static class CommitDetails {
        private String message;
        private CommitAuthor author;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public CommitAuthor getAuthor() {
            return author;
        }

        public void setAuthor(CommitAuthor author) {
            this.author = author;
        }
    }

    public static class CommitAuthor {
        private String name;
        private Instant date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Instant getDate() {
            return date;
        }

        public void setDate(Instant date) {
            this.date = date;
        }
    }
}