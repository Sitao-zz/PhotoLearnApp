package sg.edu.nus.iss.pt5.photolearnapp.model;

import java.io.Serializable;
import java.util.Date;

import sg.edu.nus.iss.pt5.photolearnapp.dao.IEntity;

/**
 * Created by mjeyakaran on 23/3/18.
 */

public abstract class Title implements Serializable, IModel, IEntity {

    @RecordId
    private String id;
    private String userId;
    private String title;
    private String sessionId;
    private Date dateTime;

    public Title() {
    }

    public Title(String id, String userId, String title, String sessionId, Date dateTime) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.sessionId = sessionId;
        this.dateTime = dateTime;
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
