package sg.edu.nus.iss.pt5.photolearnapp.model;

/**
 * Created by Liang Entao on 24/3/18.
 */
public class Trainer extends UserRole {
    public Trainer(User user) {
        super(user);
    }

    public Trainer(String id, String name, String email) {
        super(id, name, email);
    }
}
