package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.constants.UIType;
import sg.edu.nus.iss.pt5.photolearnapp.model.Item;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.util.CommonUtils;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.ITEM_OBJ;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.MODE;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.POSITION;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.UI_TYPE;

public class ManageItemActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView photoImageView;
    private EditText descriptionEditText;

    private LinearLayout optLinearLayout;

    private Button cancelBtn;
    private Button addBtn;
    private Button saveBtn;
    private Button deleteBtn;

    private Mode mode;
    private UIType titleUIType;
    private Item item;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_item);

        photoImageView = (ImageView) findViewById(R.id.photoImageViewID);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditTextID);
        optLinearLayout = (LinearLayout) findViewById(R.id.optLayoutID);

        cancelBtn = (Button) findViewById(R.id.cancelBtnID);
        cancelBtn.setOnClickListener(this);

        addBtn = (Button) findViewById(R.id.addBtnID);
        addBtn.setOnClickListener(this);

        deleteBtn = (Button) findViewById(R.id.deleteBtnID);
        deleteBtn.setOnClickListener(this);

        saveBtn = (Button) findViewById(R.id.saveBtnID);
        saveBtn.setOnClickListener(this);

        // Read Intent Parameters
        Bundle extras = getIntent().getExtras();
        mode = (Mode) extras.get(MODE);
        titleUIType = (UIType) extras.get(UI_TYPE);
        item = (Item) extras.get(ITEM_OBJ);
        position = extras.getInt(POSITION);

        setTitle();

        descriptionEditText.setText(item.getDescription());

    }

    private void setTitle() {

        if ((UIType.LEARNING == titleUIType)) {
            optLinearLayout.setVisibility(View.GONE);
            if (Mode.ADD == mode) {
                item = new LearningItem();
                addBtn.setVisibility(View.VISIBLE);
                setTitle("Add New Learning Item");
                startDialog();
            } else if (Mode.EDIT == mode) {
                deleteBtn.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(View.VISIBLE);
                setTitle("Edit Learning Item");
                downloadImage();
            }
        } else {
            optLinearLayout.setVisibility(View.VISIBLE);
            if (Mode.ADD == mode) {
                item = new QuizItem();
                addBtn.setVisibility(View.VISIBLE);
                setTitle("Add New Quiz Item");
                startDialog();
            } else if (Mode.EDIT == mode) {
                deleteBtn.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(View.VISIBLE);
                setTitle("Edit Quiz Item");
                downloadImage();
            }
        }

    }

    private void deleteItem() {

    }

    private void updateModel() {
        item.setDescription(descriptionEditText.getText().toString());
    }

    @Override
    public void onClick(View v) {

        Intent returnIntent;

        switch (v.getId()) {
            case R.id.cancelBtnID:
                finish();
                break;
            case R.id.addBtnID:

                updateModel();

                returnIntent = new Intent();
                returnIntent.putExtra(MODE, mode);
                returnIntent.putExtra(ITEM_OBJ, item);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

                break;
            case R.id.saveBtnID:

                updateModel();

                returnIntent = new Intent();
                returnIntent.putExtra(MODE, mode);
                returnIntent.putExtra(ITEM_OBJ, item);
                returnIntent.putExtra(POSITION, position);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

                break;
            case R.id.deleteBtnID:

                returnIntent = new Intent();
                returnIntent.putExtra(MODE, Mode.DELETE);
                returnIntent.putExtra(ITEM_OBJ, item);
                returnIntent.putExtra(POSITION, position);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

                break;
        }

    }

    private void downloadImage() {

        StorageReference pathReference = storageRef.child(item.getPhotoURL());

        final long ONE_MEGABYTE = 1024 * 1024;
        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);

                photoImageView.setMinimumHeight(dm.heightPixels);
                photoImageView.setMinimumWidth(dm.widthPixels);
                photoImageView.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, AppConstants.RC_GALLERY_PICTURE);
                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (cameraIntent.resolveActivity(ManageItemActivity.this.getPackageManager()) != null) {
                            startActivityForResult(cameraIntent, AppConstants.RC_CAMERA_CAPTURE);
                        }
                    }
                });
        myAlertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.RC_CAMERA_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            uploadToFileStore(imageBitmap);
        } else if (requestCode == AppConstants.RC_GALLERY_PICTURE && resultCode == RESULT_OK) {

            if (data != null) {
                try {
                    Bitmap bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    uploadToFileStore(bm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void uploadToFileStore(Bitmap bitmap) {

        StorageReference imagesRef = storageRef.child(AppConstants.STORAGE_PATH_UPLOADS + CommonUtils.generateRandomImageFileName());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                item.setPhotoURL(taskSnapshot.getMetadata().getPath());
                downloadImage();
            }
        });

    }
}
