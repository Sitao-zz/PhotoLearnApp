package sg.edu.nus.iss.pt5.photolearnapp.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.pt5.photolearnapp.dao.IEntity;

/**
 * Created by mjeyakaran on 18/3/18.
 */

public class LearningSession implements Serializable, IEntity {

    public static final String DATE_PATTERN = "yyyyMMdd";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.US);

    @RecordId
    private String id;
    private String userId;
    private Date courseDate;
    private String courseCode;
    private String courseName;
    private String moduleNumber;
    private String moduleName;

    public LearningSession() {
    }

    public LearningSession(String id, String userId, Date courseDate, String courseCode, String courseName, String moduleNumber, String moduleName) {
        this.id = id;
        this.userId = userId;
        this.courseDate = courseDate;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.moduleNumber = moduleNumber;
        this.moduleName = moduleName;
    }

    /**
     * YYYYMMDD-<Course Code>-M<Module number>
     */
    public void generateSessionID() {

        StringBuilder sessionIDBuilder = new StringBuilder();
        sessionIDBuilder.append(sdf.format(courseDate));
        sessionIDBuilder.append("-");
        sessionIDBuilder.append(courseCode);
        sessionIDBuilder.append("-M");
        sessionIDBuilder.append(moduleNumber);

        this.id = sessionIDBuilder.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Date courseDate) {
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
