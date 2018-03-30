package sg.edu.nus.iss.pt5.photolearnapp.model;

/**
 * Created by mjeyakaran on 24/3/18.
 */

public class QuizItem extends Item {
    public QuizItem() {
    }

    public QuizItem(String id, String userId, String titleId, String photoUrl, String photoDesc, double latitude, double longitude) {
        super(id, userId, titleId, photoUrl, photoDesc, latitude, longitude);
    }
}
