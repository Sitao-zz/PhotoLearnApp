package sg.edu.nus.iss.pt5.photolearnapp.model;

/**
 * Created by Liang Entao on 18/3/18.
 */
public class LearningTitle {
    String id;
    String userId;
    String title;
    String sessionId;
    long timestamp;

    public LearningTitle() {
    }

    public LearningTitle(String id, String userId, String title, String sessionId, long timestamp) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.sessionId = sessionId;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
