package sg.edu.nus.iss.pt5.photolearnapp.model;

/**
 * Created by Liang Entao on 18/3/18.
 */
public class LearningItem {
    String id;
    String userId;
    String titleId;
    String photoUrl;
    String photoDesc;
    double latitude;
    double longitude;

    public LearningItem() {
    }

    public LearningItem(String id, String userId, String titleId, String photoUrl, String photoDesc, double latitude, double longitude) {
        this.id = id;
        this.userId = userId;
        this.titleId = titleId;
        this.photoUrl = photoUrl;
        this.photoDesc = photoDesc;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoDesc() {
        return photoDesc;
    }

    public void setPhotoDesc(String photoDesc) {
        this.photoDesc = photoDesc;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
