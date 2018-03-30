package sg.edu.nus.iss.pt5.photolearnapp.util;

import android.graphics.ImageFormat;
import android.media.MediaFormat;

import sg.edu.nus.iss.pt5.photolearnapp.model.Item;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;

/**
 * Created by mjeyakaran on 24/3/18.
 */

public class CommonUtils {

    public static boolean isLearningItem(Item item) {
        return item instanceof LearningItem;
    }

    public static boolean isQuizItem(Item item) {
        return item instanceof QuizItem;
    }

    public static String generateRandomImageFileName() {
        return System.currentTimeMillis() + ".JPEG";
    }
}
