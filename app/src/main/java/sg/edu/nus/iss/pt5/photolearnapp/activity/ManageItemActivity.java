package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.constants.UIType;
import sg.edu.nus.iss.pt5.photolearnapp.model.Item;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.util.FileStoreHelper;
import sg.edu.nus.iss.pt5.photolearnapp.util.FileStoreListener;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.ITEM_OBJ;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.MODE;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.POSITION;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.RC_PERMISSION;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.UI_TYPE;

public class ManageItemActivity extends BaseActivity implements View.OnClickListener {

    private FileStoreHelper fileStoreHelper = FileStoreHelper.getInstance();

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

        descriptionEditText.setText(item.getPhotoDesc());

    }

    private void setTitle() {

        if ((UIType.LEARNING == titleUIType)) {
            optLinearLayout.setVisibility(View.GONE);
            if (Mode.ADD == mode) {
                item = new LearningItem();
                addBtn.setVisibility(View.VISIBLE);
                setTitle("Add New Learning Item");
                selectImage();
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
                selectImage();
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
        item.setPhotoDesc(descriptionEditText.getText().toString());
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


    private void selectImage() {

        checkPermissions();

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

    private void downloadImage() {
        fileStoreHelper.downloadImage(item.getPhotoUrl(), new FileStoreListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);

                photoImageView.setMinimumHeight(dm.heightPixels);
                photoImageView.setMinimumWidth(dm.widthPixels);
                photoImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(Exception exception) {
                // TODO
            }
        });
    }

    public void uploadToFileStore(Bitmap bitmap) {

        fileStoreHelper.uploadImage(bitmap, new FileStoreListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                item.setPhotoUrl(taskSnapshot.getMetadata().getPath());
                downloadImage();
            }

            @Override
            public void onFailure(Exception exception) {
                // TODO
            }
        });

    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, RC_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RC_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length < 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // permission denied.
                    finish();
                }
            }
        }
    }
}
