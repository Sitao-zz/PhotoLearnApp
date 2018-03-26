package sg.edu.nus.iss.pt5.photolearnapp.dao;

import com.google.firebase.database.ValueEventListener;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.Participant;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class LearningTitleDAO extends BaseEntityDAO<LearningTitle> {
    public LearningTitleDAO() {
        super("learningTitles", LearningTitle.class);
    }

    public void getTitlesBySession(LearningSession session, DAOResultListener<Iterable<LearningTitle>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(resultListener);
        mEntityRef.orderByChild("sessionId").equalTo(session.getId()).addValueEventListener(childrenListener);
    }

    public void getTitlesByParticipant(Participant participant, DAOResultListener<Iterable<LearningTitle>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(resultListener);
        mEntityRef.orderByChild("userId").equalTo(participant.getId()).addValueEventListener(childrenListener);
    }
}
