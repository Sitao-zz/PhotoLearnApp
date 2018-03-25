package sg.edu.nus.iss.pt5.photolearnapp.model;

import sg.edu.nus.iss.pt5.photolearnapp.dao.ILookupable;

/**
 * Created by Liang Entao on 24/3/18.
 */
public class UserRole implements ILookupable {
    private User user;

    public UserRole(User user) {
        this.user = user;
    }

    public UserRole(String id, String name, String email) {
        this.user = new User(id, name, email);
    }

    @Override
    public String getId() {
        return user.getId();
    }

    public User getUser() {
        return user;
    }
}
