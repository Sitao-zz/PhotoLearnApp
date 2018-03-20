package sg.edu.nus.iss.pt5.photolearnapp.dao;

import java.util.InvalidPropertiesFormatException;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningItem;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class LearningItemDAO extends BaseDAO<LearningItem> {
    public LearningItemDAO() {
        super("learningItems", LearningItem.class);
    }

    @Override
    protected String getIdValue(LearningItem item) throws InvalidPropertiesFormatException {
        return (String) getId(item).getValue();
    }
}
