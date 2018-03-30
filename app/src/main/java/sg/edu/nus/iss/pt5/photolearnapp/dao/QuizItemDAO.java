package sg.edu.nus.iss.pt5.photolearnapp.dao;

import com.google.firebase.database.ValueEventListener;

import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class QuizItemDAO extends BaseEntityDAO<QuizItem> {
    public QuizItemDAO() {
        super("quizItems", QuizItem.class);
    }

    public void getItemsByTitle(QuizTitle title, DAOResultListener<Iterable<QuizItem>> resultListener) {
        this.getItemsByTitleId(title.getId(), resultListener);
    }

    public void getItemsByTitleId(String titleId, DAOResultListener<Iterable<QuizItem>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(resultListener);
        mEntityRef.orderByChild("titleId").equalTo(titleId).addValueEventListener(childrenListener);
    }
}
