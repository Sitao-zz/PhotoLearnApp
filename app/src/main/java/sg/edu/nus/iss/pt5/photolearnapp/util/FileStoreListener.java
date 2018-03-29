package sg.edu.nus.iss.pt5.photolearnapp.util;

import android.support.annotation.NonNull;

/**
 * Created by mjeyakaran on 27/3/18.
 */
public interface FileStoreListener<T> {
    public void onSuccess(T result);

    public void onFailure(Exception exception);
}
