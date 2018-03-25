package sg.edu.nus.iss.pt5.photolearnapp.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by mjeyakaran on 23/3/18.
 */

public abstract class Title implements Serializable{
    private String title;
    private Calendar dateTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }
}
