package sg.edu.nus.iss.pt5.photolearnapp.model;

import sg.edu.nus.iss.pt5.photolearnapp.dao.IEntity;

/**
 * Created by Liang Entao on 18/3/18.
 */
public class User implements IModel, IEntity {
    @RecordId
    private String id;
    private String name;
    private String email;

    public User() {
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
