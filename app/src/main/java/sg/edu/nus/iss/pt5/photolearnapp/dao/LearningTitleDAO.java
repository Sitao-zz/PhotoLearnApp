package sg.edu.nus.iss.pt5.photolearnapp.dao;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.Participant;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class LearningTitleDAO extends BaseEntityDAO<LearningTitle> {
    private LearningItemDAO mChildDao = new LearningItemDAO();

    public LearningTitleDAO() {
        super("learningTitles", LearningTitle.class);
    }

    public void getTitlesBySession(LearningSession session, DAOResultListener<Iterable<LearningTitle>> resultListener) {
        this.getTitlesBySessionId(session.getId(), resultListener);
    }

    public void getTitlesBySessionId(String sessionId, DAOResultListener<Iterable<LearningTitle>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(resultListener);
        mEntityRef.orderByChild("sessionId").equalTo(sessionId).addValueEventListener(childrenListener);
    }

    public void getTitlesByParticipant(Participant participant, DAOResultListener<Iterable<LearningTitle>> resultListener) {
        this.getTitlesByParticipantId(participant.getId(), resultListener);
    }

    public void getTitlesByParticipantId(String participantId, DAOResultListener<Iterable<LearningTitle>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(resultListener);
        mEntityRef.orderByChild("userId").equalTo(participantId).addValueEventListener(childrenListener);
    }

    public void getTitlesBySessionPart(LearningSession session, Participant participant, DAOResultListener<Iterable<LearningTitle>> resultListener) {
        this.getTitlesBySessionPartId(session.getId(), participant.getId(), resultListener);
    }

    public void getTitlesBySessionPartId(final String sessionId, String participantId, final DAOResultListener<Iterable<LearningTitle>> resultListener) {
        DAOResultListener<Iterable<LearningTitle>> tmpListener = new DAOResultListener<Iterable<LearningTitle>>() {
            @Override
            public void OnDAOReturned(Iterable<LearningTitle> objects) {
                List<LearningTitle> list = new ArrayList<LearningTitle>();
                for (LearningTitle obj : objects) {
                    if (sessionId.equalsIgnoreCase(obj.getSessionId())) {
                        list.add(obj);
                    }
                }
                resultListener.OnDAOReturned(list);
            }
        };
        ValueEventListener childrenListener = createChildrenEventListener(tmpListener);
        mEntityRef.orderByChild("userId").equalTo(participantId).addValueEventListener(childrenListener);
    }

    @Override
    public void delete(LearningTitle obj) {
        this.deleteById(obj.getId());
    }

    @Override
    public void deleteById(String objId) {
        DAOResultListener<Iterable<LearningItem>> resultListener = new DAOResultListener<Iterable<LearningItem>>() {
            @Override
            public void OnDAOReturned(Iterable<LearningItem> items) {
                mChildDao.delete(items);
            }
        };
        mChildDao.getItemsByTitleId(objId, resultListener);
        super.deleteById(objId);
    }

    @Override
    public void delete(Iterable<LearningTitle> objects) {
        for (LearningTitle obj : objects) {
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
