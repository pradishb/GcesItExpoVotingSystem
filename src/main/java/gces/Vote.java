package gces;

public class Vote {
    private String userKey;
    private String projectKey;

    public String getUserKey() {
        return userKey;
    }

    public Vote(String userKey, String projectKey) {
        this.userKey = userKey;
        this.projectKey = projectKey;
    }

    public void setUserKey(String userKey) {

        this.userKey = userKey;
    }

    public String getprojectKey() {
        return projectKey;
    }

    public void setprojectKey(String projectKey) {
        this.projectKey = projectKey;
    }
}
