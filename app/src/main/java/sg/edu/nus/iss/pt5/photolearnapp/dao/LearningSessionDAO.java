package sg.edu.nus.iss.pt5.photolearnapp.dao;

import java.util.InvalidPropertiesFormatException;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class LearningSessionDAO extends BaseDAO<LearningSession> {
    public LearningSessionDAO() {
        super("learningSessions", LearningSession.class);
    }

    @Override
    protected String getIdValue(LearningSession session) throws InvalidPropertiesFormatException {
        return (String) getId(session).getValue();
    }
}
