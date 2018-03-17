package sg.edu.nus.iss.pt5.photolearnapp.activity;

import java.util.Date;

public class LearningSession {
    private String LSid;
    private String UserID;
    private Date CourseDate;
    private String CourseCode;
    private int ModuleNumber;

    public LearningSession() {
        super();
    }

    public LearningSession(String LSid, String userID, Date courseDate, String courseCode, int moduleNumber) {
        this.LSid = LSid;
        UserID = userID;
        CourseDate = courseDate;
        CourseCode = courseCode;
        ModuleNumber = moduleNumber;
    }

    public String getLSid() {
        return LSid;
    }

    public void setLSid(String LSid) {
        this.LSid = LSid;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public Date getCourseDate() {
        return CourseDate;
    }

    public void setCourseDate(Date courseDate) {
        CourseDate = courseDate;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public int getModuleNumber() {
        return ModuleNumber;
    }

    public void setModuleNumber(int moduleNumber) {
        ModuleNumber = moduleNumber;
    }
}
