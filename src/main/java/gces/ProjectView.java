package gces;

public class ProjectView {
    String key;
    String title;
    int noOfVotes;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNoOfVotes() {
        return noOfVotes;
    }

    public void setNoOfVotes(int noOfVotes) {
        this.noOfVotes = noOfVotes;
    }

    public ProjectView(String key, String title, int noOfVotes) {
        this.key = key;
        this.title = title;
        this.noOfVotes = noOfVotes;
    }
}
