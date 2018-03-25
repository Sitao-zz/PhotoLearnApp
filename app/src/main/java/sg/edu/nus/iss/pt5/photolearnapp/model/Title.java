package sg.edu.nus.iss.pt5.photolearnapp.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mjeyakaran on 23/3/18.
 */

public abstract class Title implements Serializable{
    private String title;
    private Date dateTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
