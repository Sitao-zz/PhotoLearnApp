package sg.edu.nus.iss.pt5.photolearnapp.model;

import java.util.Date;

/**
 * Created by mjeyakaran on 23/3/18.
 */

public class QuizTitle extends Title {
    public QuizTitle() {
    }

    public QuizTitle(String id, String userId, String title, String sessionId, Date dateTime) {
        super(id, userId, title, sessionId, dateTime);
    }
}
