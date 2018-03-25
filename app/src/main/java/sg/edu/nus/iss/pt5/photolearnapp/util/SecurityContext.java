package sg.edu.nus.iss.pt5.photolearnapp.util;

import java.io.Serializable;

import sg.edu.nus.iss.pt5.photolearnapp.model.Participant;
import sg.edu.nus.iss.pt5.photolearnapp.model.Trainer;
import sg.edu.nus.iss.pt5.photolearnapp.model.UserRole;

/**
 * Created by mjeyakaran on 25/3/18.
 */

public class SecurityContext implements Serializable {

    private static SecurityContext securityContext;
    private static Object dummy = new Object();

    private UserRole role;

    private SecurityContext() {
    }

    public static SecurityContext getInstance() {

        if (securityContext == null) {
            synchronized (dummy) {
                if (securityContext == null) {
                    securityContext = new SecurityContext();
                }
            }
        }

        return securityContext;
    }


    public boolean isTrainer() {
        return (role instanceof Trainer);
    }

    public boolean isParticipant() {
        return (role instanceof Participant);
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
