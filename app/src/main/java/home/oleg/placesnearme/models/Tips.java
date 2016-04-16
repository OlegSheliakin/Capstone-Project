package home.oleg.placesnearme.models;

/**
 * Created by Oleg on 13.04.2016.
 */
public class Tips {

    private int id;

    private int createdAt;

    private String text;

    private String type;

    private String canonicalUrl;

    private Likes likes;

    private boolean logView;

    private User user;

    public int getId() { return id; }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public Likes getLikes() {
        return likes;
    }

    public boolean isLogView() {
        return logView;
    }

    public User getUser() {
        return user;
    }
}
