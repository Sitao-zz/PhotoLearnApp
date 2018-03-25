package sg.edu.nus.iss.pt5.photolearnapp.dao;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class LearningSessionDAO extends BaseEntityDAO<LearningSession> {
    public LearningSessionDAO() {
        super("learningSessions", LearningSession.class);
    }
}