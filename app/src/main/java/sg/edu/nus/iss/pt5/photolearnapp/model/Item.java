package sg.edu.nus.iss.pt5.photolearnapp.model;

import java.io.Serializable;

/**
 * Created by mjeyakaran on 24/3/18.
 */

public abstract class Item implements Serializable {

    private String photo;
    private String photoURL;
    private String gps;
    private String description;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
