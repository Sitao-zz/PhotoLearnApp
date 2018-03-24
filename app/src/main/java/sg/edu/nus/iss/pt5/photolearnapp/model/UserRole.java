package sg.edu.nus.iss.pt5.photolearnapp.model;

/**
 * Created by Liang Entao on 24/3/18.
 */
public class UserRole {
    private User user;

    public UserRole(User user) {
        this.user = user;
    }

    public UserRole(String id, String name, String email) {
        this.user = new User(id, name, email);
    }

    public User getUser() {
        return user;
    }
}
