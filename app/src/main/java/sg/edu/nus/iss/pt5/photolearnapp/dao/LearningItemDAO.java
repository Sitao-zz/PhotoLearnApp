package sg.edu.nus.iss.pt5.photolearnapp.dao;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningItem;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class LearningItemDAO extends BaseEntityDAO<LearningItem> {
    public LearningItemDAO() {
        super("learningItems", LearningItem.class);
    }
}
