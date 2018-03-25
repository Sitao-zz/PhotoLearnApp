package sg.edu.nus.iss.pt5.photolearnapp.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by mjeyakaran on 18/3/18.
 */

public class LearningSession implements Serializable {

    public static final String DATE_PATTERN = "yyyyMMdd";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.US);

    private String sessionID;
    private Calendar courseDate;
    private String courseCode;
    private String courseName;
    private String moduleNumber;
    private String moduleName;

    /**
     * YYYYMMDD-<Course Code>-M<Module number>
     */
    public void generateSessionID() {

        StringBuilder sessionIDBuilder = new StringBuilder();
        sessionIDBuilder.append(sdf.format(courseDate.getTime()));
        sessionIDBuilder.append("-");
        sessionIDBuilder.append(courseCode);
        sessionIDBuilder.append("-M");
        sessionIDBuilder.append(moduleNumber);

        this.sessionID = sessionIDBuilder.toString();
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public Calendar getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Calendar courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getModuleNumber() {
        return moduleNumber;
    }

    public void setModuleNumber(String moduleNumber) {
        this.moduleNumber = moduleNumber;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
