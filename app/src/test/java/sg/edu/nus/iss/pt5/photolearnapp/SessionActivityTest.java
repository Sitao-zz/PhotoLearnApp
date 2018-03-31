package sg.edu.nus.iss.pt5.photolearnapp;

import sg.edu.nus.iss.pt5.photolearnapp.activity.ManageLearningSessionActivity;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by akeelan on 3/30/2018.
 */

public class SessionActivityTest {

    public final LearningSession learningSession = new LearningSession();

    @Before
    public void setup(){
        learningSession.setCourseCode("IOT1");
        learningSession.setCourseDate(Calendar.getInstance().getTime());
        learningSession.setModuleNumber("0001");
    }

    @Test
    public void generateSessionIDTest()
    {
        learningSession.generateSessionID();
        String actual = learningSession.getId().split("-")[0];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
        String expected = sdf.format(Calendar.getInstance().getTime());
        Assert.assertEquals(expected,actual);
    }
}
