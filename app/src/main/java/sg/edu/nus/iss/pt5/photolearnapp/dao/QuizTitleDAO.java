package sg.edu.nus.iss.pt5.photolearnapp.dao;

import com.google.firebase.database.ValueEventListener;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.Participant;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class QuizTitleDAO extends BaseEntityDAO<QuizTitle> {
    private QuizItemDAO mChildDao = new QuizItemDAO();

    public QuizTitleDAO() {
        super("quizTitles", QuizTitle.class);
    }

    public void getTitlesBySession(LearningSession session, DAOResultListener<Iterable<QuizTitle>> resultListener) {
        this.getTitlesBySessionId(session.getId(), resultListener);
    }

    public void getTitlesBySessionId(String sessionId, DAOResultListener<Iterable<QuizTitle>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(resultListener);
        mEntityRef.orderByChild("sessionId").equalTo(sessionId).addValueEventListener(childrenListener);
    }

    @Override
    public void delete(QuizTitle obj) {
        this.deleteById(obj.getId());
    }

    @Override
    public void deleteById(String objId) {
        DAOResultListener<Iterable<QuizItem>> resultListener = new DAOResultListener<Iterable<QuizItem>>() {
            @Override
            public void OnDAOReturned(Iterable<QuizItem> items) {
                mChildDao.delete(items);
            }
        };
        mChildDao.getItemsByTitleId(objId, resultListener);
        super.deleteById(objId);
    }

    @Override
    public void delete(Iterable<QuizTitle> objects) {
        for (QuizTitle obj : objects) {
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
