package sg.edu.nus.iss.pt5.photolearnapp.dao;

import java.util.InvalidPropertiesFormatException;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class LearningTitleDAO extends BaseDAO<LearningTitle> {
    public LearningTitleDAO() {
        super("learningTitles", LearningTitle.class);
    }

    @Override
    protected String getIdValue(LearningTitle title) throws InvalidPropertiesFormatException {
        return (String) getId(title).getValue();
    }
}
