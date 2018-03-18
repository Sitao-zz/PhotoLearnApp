package sg.edu.nus.iss.pt5.photolearnapp.model;

/**
 * Created by Liang Entao on 18/3/18.
 */
public class LearningSession {
    String id;
    String userId;
    long courseDate;
    String courseCode;
    int moduleNumber;

    public LearningSession() {
    }

    public LearningSession(String id, String userId, long courseDate, String courseCode, int moduleNumber) {
        this.id = id;
        this.userId = userId;
        this.courseDate = courseDate;
        this.courseCode = courseCode;
        this.moduleNumber = moduleNumber;
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

    public long getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(long courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getModuleNumber() {
        return moduleNumber;
    }

    public void setModuleNumber(int moduleNumber) {
        this.moduleNumber = moduleNumber;
    }
}
