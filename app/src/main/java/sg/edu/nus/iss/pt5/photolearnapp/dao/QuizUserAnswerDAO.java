package sg.edu.nus.iss.pt5.photolearnapp.dao;

import com.google.firebase.database.ValueEventListener;

import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizUserAnswer;
import sg.edu.nus.iss.pt5.photolearnapp.model.User;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class QuizUserAnswerDAO extends BaseEntityDAO<QuizUserAnswer> {
    public QuizUserAnswerDAO() {
        super("quizUserAnswer", QuizUserAnswer.class);
    }

    public void getAnswersByItemId(String titleId, DAOResultListener<Iterable<QuizUserAnswer>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(resultListener);
        mEntityRef.orderByChild("quizItemId").equalTo(titleId).addValueEventListener(childrenListener);
    }

    public void getAnswersByQuizUser(User user, QuizItem quizItem, DAOResultListener<Iterable<QuizUserAnswer>> resultListener) {
        this.getAnswersByQuizUserId(user.getId(), quizItem.getId(), resultListener);
    }

    public void getAnswersByQuizUserId(String userId, String quizItemId, DAOResultListener<Iterable<QuizUserAnswer>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(resultListener);
        String prefix = userId + quizItemId;
        mEntityRef.orderByChild("id").startAt(prefix).endAt(prefix + "\\uf8ff").addValueEventListener(childrenListener);
    }
}
