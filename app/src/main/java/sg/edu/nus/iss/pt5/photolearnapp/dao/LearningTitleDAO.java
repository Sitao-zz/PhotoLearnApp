package sg.edu.nus.iss.pt5.photolearnapp.dao;

import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class LearningTitleDAO extends BaseEntityDAO<LearningTitle> {
    public LearningTitleDAO() {
        super("learningTitles", LearningTitle.class);
    }
}
