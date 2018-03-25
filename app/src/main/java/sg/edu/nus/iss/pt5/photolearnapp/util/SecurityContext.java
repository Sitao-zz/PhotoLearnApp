package sg.edu.nus.iss.pt5.photolearnapp.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.model.Participant;
import sg.edu.nus.iss.pt5.photolearnapp.model.Trainer;
import sg.edu.nus.iss.pt5.photolearnapp.model.User;

/**
 * Created by mjeyakaran on 25/3/18.
 */

public class SecurityContext implements Serializable {

    private static SecurityContext securityContext;
    private static Object dummy = new Object();

    private User user;

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
        return (user instanceof Trainer);
    }

    public boolean isParticipant() {
        return (user instanceof Participant);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
