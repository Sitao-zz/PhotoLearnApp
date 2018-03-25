package sg.edu.nus.iss.pt5.photolearnapp.model;

import sg.edu.nus.iss.pt5.photolearnapp.dao.IEntity;

/**
 * Created by mjeyakaran on 24/3/18.
 */

public class LearningItem extends Item implements IEntity {
    public LearningItem() {
    }

    public LearningItem(String id, String userId, String titleId, String photoUrl, String photoDesc, double latitude, double longitude) {
        super(id, userId, titleId, photoUrl, photoDesc, latitude, longitude);
    }
}
