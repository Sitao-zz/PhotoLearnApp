package sg.edu.nus.iss.pt5.photolearnapp.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by Liang Entao on 30/3/18.
 */
public class QuizUserAnswer implements Serializable, IModel {
    public static final String DATETIME_PATTERN = "yyyyMMddHHmmss";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_PATTERN, Locale.US);

    private String id;
    private String userId;
    private String quizItemId;
    private boolean isOptionOne = false;
    private boolean isOptionTwo = false;
    private boolean isOptionThree = false;
    private boolean isOptionFour = false;
    private Date dateTime;

    public QuizUserAnswer() {
    }

    public QuizUserAnswer(String userId, String quizItemId, Iterable<Boolean> answer) {
        this.userId = userId;
        this.quizItemId = quizItemId;
        if(answer !=null) {
            Iterator<Boolean> it = answer.iterator();
            if(it.hasNext()) {
                isOptionOne = it.next();
            }
            if(it.hasNext()) {
                isOptionTwo = it.next();
            }
            if(it.hasNext()) {
                isOptionThree = it.next();
            }
            if(it.hasNext()) {
                isOptionFour = it.next();
            }
        }
        this.dateTime = Calendar.getInstance().getTime();
        this.id = userId + quizItemId + sdf.format(this.dateTime);
    }

    @Override
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

    public String getQuizItemId() {
        return quizItemId;
    }

    public void setQuizItemId(String quizItemId) {
        this.quizItemId = quizItemId;
    }

    public boolean isOptionOne() {
        return isOptionOne;
    }

    public void setOptionOne(boolean optionOne) {
        isOptionOne = optionOne;
    }

    public boolean isOptionTwo() {
        return isOptionTwo;
    }

    public void setOptionTwo(boolean optionTwo) {
        isOptionTwo = optionTwo;
    }

    public boolean isOptionThree() {
        return isOptionThree;
    }

    public void setOptionThree(boolean optionThree) {
        isOptionThree = optionThree;
    }

    public boolean isOptionFour() {
        return isOptionFour;
    }

    public void setOptionFour(boolean optionFour) {
        isOptionFour = optionFour;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
