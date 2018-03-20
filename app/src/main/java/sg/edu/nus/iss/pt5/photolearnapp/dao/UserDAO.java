package sg.edu.nus.iss.pt5.photolearnapp.dao;

import java.util.InvalidPropertiesFormatException;

import sg.edu.nus.iss.pt5.photolearnapp.model.User;

/**
 * Created by Liang Entao on 20/3/18.
 */
public class UserDAO extends BaseDAO<User> {
    public UserDAO() {
        super("users", User.class);
    }

    @Override
    protected String getIdValue(User user) throws InvalidPropertiesFormatException {
        return (String)getId(user).getValue();
    }
}
