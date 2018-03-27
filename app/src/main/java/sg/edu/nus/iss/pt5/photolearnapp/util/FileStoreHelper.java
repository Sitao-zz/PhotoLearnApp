package sg.edu.nus.iss.pt5.photolearnapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;

/**
 * Created by akeelan on 3/17/2018.
 */

public class FileStoreHelper {

    private static final long ONE_MEGABYTE = 1024 * 1024;

    private static FileStoreHelper fileStoreHelper;
    private static Object dummy = new Object();

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    private FileStoreHelper() {
    }

    public static FileStoreHelper getInstance() {
        if (fileStoreHelper == null) {
            synchronized (dummy) {
                if (fileStoreHelper == null) {
                    fileStoreHelper = new FileStoreHelper();
                }
            }
        }

        return fileStoreHelper;
    }


    public void downloadImage(final String fileStorePath, final FileStoreListener<Bitmap> fileStoreListener) {

        StorageReference pathReference = storageRef.child(fileStorePath);

        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                fileStoreListener.onSuccess(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                fileStoreListener.onFailure(exception);
            }
        });
    }

    public void uploadImage(Bitmap bitmap, final FileStoreListener<UploadTask.TaskSnapshot> fileStoreListener) {

        StorageReference imagesRef = storageRef.child(AppConstants.STORAGE_PATH_UPLOADS + CommonUtils.generateRandomImageFileName());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                fileStoreListener.onFailure(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileStoreListener.onSuccess(taskSnapshot);
            }
        });

    }
}
