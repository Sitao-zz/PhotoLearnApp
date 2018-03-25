package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.LEARNING_SESSION_OBJ;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.MODE;

public class ManageLearningSessionActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String DATE_PATTERN = "dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.US);

    private EditText courseDateEditText;
    private EditText courseCodeEditText;
    private EditText courseNameEditText;
    private EditText moduleNumberEditText;
    private EditText moduleNameEditText;

    private Button cancelBtn;
    private Button addBtn;
    private Button saveBtn;

    private Mode mode;
    private LearningSession learningSession;
    private Calendar courseDate;

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            courseDate.set(Calendar.YEAR, year);
            courseDate.set(Calendar.MONTH, monthOfYear);
            courseDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateCourseDate();

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_learning_session);

        courseDateEditText = (EditText) findViewById(R.id.courseDateEditTextID);
        courseCodeEditText = (EditText) findViewById(R.id.courseCodeEditTextID);
        courseNameEditText = (EditText) findViewById(R.id.courseNameEditTextID);
        moduleNumberEditText = (EditText) findViewById(R.id.moduleNumberEditTextID);
        moduleNameEditText = (EditText) findViewById(R.id.moduleNameEditTextID);

        cancelBtn = (Button) findViewById(R.id.cancelBtnID);
        cancelBtn.setOnClickListener(this);

        addBtn = (Button) findViewById(R.id.addBtnID);
        addBtn.setOnClickListener(this);

        saveBtn = (Button) findViewById(R.id.saveBtnID);
        saveBtn.setOnClickListener(this);

        // Read Intent Parameters
        Bundle extras = getIntent().getExtras();
        mode = (Mode) extras.get(MODE);

        if (Mode.ADD == mode) {
            learningSession = new LearningSession();
            learningSession.setCourseDate(Calendar.getInstance());
            addBtn.setVisibility(View.VISIBLE);
            setTitle("Add New Learning Session");
        } else if(Mode.EDIT == mode) {
            learningSession = (LearningSession) extras.get(LEARNING_SESSION_OBJ);
            saveBtn.setVisibility(View.VISIBLE);
            setTitle("Edit Learning Session");
        } else {
            learningSession = (LearningSession) extras.get(LEARNING_SESSION_OBJ);
            setTitle("Learning Session");
        }

        courseDate = learningSession.getCourseDate();
        updateCourseDate();
        courseCodeEditText.setText(learningSession.getCourseCode());
        courseNameEditText.setText(learningSession.getCourseName());
        moduleNumberEditText.setText(learningSession.getModuleNumber());
        moduleNameEditText.setText(learningSession.getModuleName());

        courseDateEditText.setOnClickListener(this);
    }

    private void updateCourseDate() {
        courseDateEditText.setText(sdf.format(courseDate.getTime()));
    }

    private void updateModel() {
        learningSession.setCourseDate(courseDate);
        learningSession.setCourseCode(courseCodeEditText.getText().toString());
        learningSession.setCourseName(courseNameEditText.getText().toString());
        learningSession.setModuleNumber(moduleNumberEditText.getText().toString());
        learningSession.setModuleName(moduleNameEditText.getText().toString());
    }


    @Override
    public void onClick(View v) {

        Intent returnIntent;

        switch (v.getId()) {
            case R.id.courseDateEditTextID :
                new DatePickerDialog(ManageLearningSessionActivity.this, onDateSetListener,
                        courseDate.get(Calendar.YEAR),
                        courseDate.get(Calendar.MONTH),
                        courseDate.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.cancelBtnID :
                finish();
                break;
            case R.id.addBtnID:

                updateModel();
                learningSession.generateSessionID();

                returnIntent = new Intent();
                returnIntent.putExtra(MODE,mode);
                returnIntent.putExtra(LEARNING_SESSION_OBJ,learningSession);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                break;
            case R.id.saveBtnID:
                updateModel();
                returnIntent = new Intent();
                returnIntent.putExtra(MODE,mode);
                returnIntent.putExtra(LEARNING_SESSION_OBJ,learningSession);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                break;
        }
    }
}