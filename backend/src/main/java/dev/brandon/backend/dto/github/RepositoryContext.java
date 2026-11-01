package dev.brandon.backend.dto.github;

public class RepositoryContext {
    private String owner;
    private String repo;
    private String readme;
    private GithubCommitResponse[] recentCommits;

    public RepositoryContext(
            String owner,
            String repo,
            String readme,
            GithubCommitResponse[] recentCommits
    ) {
        this.owner = owner;
        this.repo = repo;
        this.readme = readme;
        this.recentCommits = recentCommits;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }

    public GithubCommitResponse[] getRecentCommits() {
        return recentCommits;
    }

    public void setRecentCommits(GithubCommitResponse[] recentCommits) {
        this.recentCommits = recentCommits;
    }
}