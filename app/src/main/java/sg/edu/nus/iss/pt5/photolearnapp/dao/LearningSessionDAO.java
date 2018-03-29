package sg.edu.nus.iss.pt5.photolearnapp.dao;

import com.google.firebase.database.ValueEventListener;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.Trainer;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class LearningSessionDAO extends BaseEntityDAO<LearningSession> {
    private LearningTitleDAO mChildDao = new LearningTitleDAO();

    public LearningSessionDAO() {
        super("learningSessions", LearningSession.class);
    }

    public void getSessionsByTrainer(Trainer trainer, DAOResultListener<Iterable<LearningSession>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(resultListener);
        mEntityRef.orderByChild("userId").equalTo(trainer.getId()).addValueEventListener(childrenListener);
    }

    @Override
    public void delete(LearningSession obj) {
        this.deleteById(obj.getId());
    }

    @Override
    public void deleteById(String objId) {
        DAOResultListener<Iterable<LearningTitle>> resultListener = new DAOResultListener<Iterable<LearningTitle>>() {
            @Override
            public void OnDAOReturned(Iterable<LearningTitle> titles) {
                mChildDao.delete(titles);
            }
        };
        mChildDao.getTitlesBySessionId(objId, resultListener);
        super.deleteById(objId);
    }

    @Override
    public void delete(Iterable<LearningSession> objects) {
        for (LearningSession obj : objects) {
            this.delete(obj);
        }
    }

    @Override
    public void deleteById(Iterable<String> objIds) {
        for (String objId : objIds) {
            this.deleteById(objId);
        }
    }
}
