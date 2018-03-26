package sg.edu.nus.iss.pt5.photolearnapp.dao;

import com.google.firebase.database.ValueEventListener;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.Trainer;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class LearningSessionDAO extends BaseEntityDAO<LearningSession> {
    public LearningSessionDAO() {
        super("learningSessions", LearningSession.class);
    }

    public void getSessionsByTrainer(Trainer trainer, DAOResultListener<Iterable<LearningSession>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(resultListener);
        mEntityRef.orderByChild("userId").equalTo(trainer.getId()).addValueEventListener(childrenListener);
    }
}
