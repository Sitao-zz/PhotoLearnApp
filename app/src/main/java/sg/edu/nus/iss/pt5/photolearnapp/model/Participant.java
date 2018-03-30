package sg.edu.nus.iss.pt5.photolearnapp.model;

import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;

/**
 * Created by Liang Entao on 24/3/18.
 */
public class Participant extends UserRole {

    private Mode mode = Mode.VIEW;

    public Participant(User user) {
        super(user);
    }

    public Participant(String id, String name, String email) {
        super(id, name, email);
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
