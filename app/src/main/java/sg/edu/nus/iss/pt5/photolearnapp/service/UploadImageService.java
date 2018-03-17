package sg.edu.nus.iss.pt5.photolearnapp.service;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import sg.edu.nus.iss.pt5.photolearnapp.constants.PhotoLearnAppConstants;

/**
 * Created by akeelan on 3/17/2018.
 */

public class UploadImageService {

    //file path should have extension.
    private String fileExtension;
    //Image bytes
    private byte[] imageBytes;
    private StorageReference storageReference;

    public UploadImageService(String fileExtension, byte[] imageBytes){
        this.fileExtension = fileExtension;
        this.imageBytes = imageBytes;
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public UploadTask UploadImage(){
        //getting the storage reference
        StorageReference sRef = storageReference.child(PhotoLearnAppConstants.STORAGE_PATH_UPLOADS+
                System.currentTimeMillis() + "." + this.fileExtension);
        UploadTask uploadTask = sRef.putBytes(imageBytes);
        return uploadTask;
    }
}
