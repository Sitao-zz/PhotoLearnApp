package sg.edu.nus.iss.pt5.photolearnapp.model;

/**
 * Created by Liang Entao on 24/3/18.
 */
public class Participant extends UserRole {
    public Participant(User user) {
        super(user);
    }

    public Participant(String id, String name, String email) {
        super(id, name, email);
    }
}
