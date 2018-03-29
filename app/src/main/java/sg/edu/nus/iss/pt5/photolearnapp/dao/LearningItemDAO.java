package sg.edu.nus.iss.pt5.photolearnapp.dao;

import com.google.firebase.database.ValueEventListener;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class LearningItemDAO extends BaseEntityDAO<LearningItem> {
    public LearningItemDAO() {
        super("learningItems", LearningItem.class);
    }

    public void getItemsByTitle(LearningTitle title, DAOResultListener<Iterable<LearningItem>> resultListener) {
        this.getItemsByTitleId(title.getId(), resultListener);
    }

    public void getItemsByTitleId(String titleId, DAOResultListener<Iterable<LearningItem>> resultListener) {
        ValueEventListener childrenListener = createChildrenEventListener(resultListener);
        mEntityRef.orderByChild("titleId").equalTo(titleId).addValueEventListener(childrenListener);
    }
}
