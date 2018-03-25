package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.constants.UIType;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.Title;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.MODE;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.TITLE_OBJ;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.UI_TYPE;

public class ManageTitleActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText titleNameEditText;

    private Button cancelBtn;
    private Button addBtn;
    private Button saveBtn;

    private Mode mode;
    private UIType titleUIType;
    private Title title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_title);

        titleNameEditText = (EditText) findViewById(R.id.titleNameEditTextID);

        cancelBtn = (Button) findViewById(R.id.cancelBtnID);
        cancelBtn.setOnClickListener(this);

        addBtn = (Button) findViewById(R.id.addBtnID);
        addBtn.setOnClickListener(this);

        saveBtn = (Button) findViewById(R.id.saveBtnID);
        saveBtn.setOnClickListener(this);

        // Read Intent Parameters
        Bundle extras = getIntent().getExtras();
        mode = (Mode) extras.get(MODE);
        titleUIType = (UIType) extras.get(UI_TYPE);
        title = (Title) extras.get(TITLE_OBJ);

        setTitle();

        titleNameEditText.setText(title.getTitle());

    }

    private void setTitle() {

        if ((UIType.LEARNING == titleUIType)) {
            if (Mode.ADD == mode) {
                title = new LearningTitle();
                addBtn.setVisibility(View.VISIBLE);
                setTitle("Add New Learning Title");
            } else if (Mode.EDIT == mode) {
                saveBtn.setVisibility(View.VISIBLE);
                setTitle("Edit Learning Title");
            }
        } else {
            if (Mode.ADD == mode) {
                title = new QuizTitle();
                addBtn.setVisibility(View.VISIBLE);
                setTitle("Add New Quiz Title");
            } else if (Mode.EDIT == mode) {
                saveBtn.setVisibility(View.VISIBLE);
                setTitle("Edit Quiz Title");
            }
        }

    }

    private void updateModel() {
        title.setTitle(titleNameEditText.getText().toString());
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
                title.setDateTime(Calendar.getInstance().getTime());

                returnIntent = new Intent();
                returnIntent.putExtra(MODE, mode);
                returnIntent.putExtra(TITLE_OBJ, title);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
            case R.id.saveBtnID:
                updateModel();
                returnIntent = new Intent();
                returnIntent.putExtra(MODE, mode);
                returnIntent.putExtra(TITLE_OBJ, title);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
        }

    }
}
