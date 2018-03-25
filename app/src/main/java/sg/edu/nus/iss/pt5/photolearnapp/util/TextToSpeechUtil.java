package sg.edu.nus.iss.pt5.photolearnapp.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

import sg.edu.nus.iss.pt5.photolearnapp.layout.ItemFragment;

/**
 * Created by mjeyakaran on 24/3/18.
 */

public class TextToSpeechUtil {

    private TextToSpeech textToSpeech;
    private Context context;

    public TextToSpeechUtil(Context context) {

        this.context = context;

        textToSpeech = new TextToSpeech(this.context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
    }

    public void speak(String toSpeak) {
        Toast.makeText(context, "Text to Speech", Toast.LENGTH_SHORT).show();
        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, Long.toString(System.currentTimeMillis()));
    }

    public void shutdown() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
