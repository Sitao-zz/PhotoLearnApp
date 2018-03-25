package sg.edu.nus.iss.pt5.photolearnapp.util;

import android.graphics.ImageFormat;
import android.media.MediaFormat;

/**
 * Created by mjeyakaran on 24/3/18.
 */

public class CommonUtils {

    public static String generateRandomImageFileName() {
        return System.currentTimeMillis() + ".JPEG";
    }
}
