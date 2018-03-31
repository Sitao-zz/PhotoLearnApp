package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.constants.UIType;
import sg.edu.nus.iss.pt5.photolearnapp.dao.LearningTitleDAO;
import sg.edu.nus.iss.pt5.photolearnapp.dao.QuizTitleDAO;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.Title;
import sg.edu.nus.iss.pt5.photolearnapp.util.CommonUtils;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.LEARNING_SESSION_OBJ;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.MODE;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.TITLE_OBJ;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.UI_TYPE;

public class ManageTitleActivity extends BaseActivity implements View.OnClickListener {

    private EditText titleNameEditText;

    private Button cancelBtn;
    private Button addBtn;
    private Button saveBtn;

    private Mode mode;
    private UIType uiType;
    private Title title;
    private LearningSession learningSession;

    private LearningTitleDAO learningTitleDAO;
    private QuizTitleDAO quizTitleDAO;

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
        uiType = (UIType) extras.get(UI_TYPE);
        learningSession = (LearningSession) extras.get(LEARNING_SESSION_OBJ);
        title = (Title) extras.get(TITLE_OBJ);

        if(CommonUtils.isLearningUI(uiType)) {
            titleNameEditText.setHint("Please enter your learning title");
        }
        else{
            titleNameEditText.setHint("Please enter your quiz title");
        }

        init();

        populateUI();

    }

    private void populateUI() {
        titleNameEditText.setText(title.getTitle());
    }

    private void init() {

        if (CommonUtils.isLearningUI(uiType)) {
            if (Mode.ADD == mode) {
                title = new LearningTitle();
                title.setUserId(SecurityContext.getInstance().getRole().getUser().getId());
                title.setSessionId(learningSession.getId());
                addBtn.setVisibility(View.VISIBLE);
                setTitle("Add New Learning Title");
            } else if (Mode.EDIT == mode) {
                saveBtn.setVisibility(View.VISIBLE);
                setTitle("Edit Learning Title");
            }

            learningTitleDAO = new LearningTitleDAO();

        } else {
            if (Mode.ADD == mode) {
                title = new QuizTitle();
                title.setUserId(SecurityContext.getInstance().getRole().getUser().getId());
                title.setSessionId(learningSession.getId());
                addBtn.setVisibility(View.VISIBLE);
                setTitle("Add New Quiz Title");
            } else if (Mode.EDIT == mode) {
                saveBtn.setVisibility(View.VISIBLE);
                setTitle("Edit Quiz Title");
            }

            quizTitleDAO = new QuizTitleDAO();
        }

    }

    private void populateModel() {
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

                populateModel();
                title.setDateTime(Calendar.getInstance().getTime());

                if(Validate()) {
                    returnIntent = new Intent();
                    SaveTitle(returnIntent);
                }
                else {
                    if(CommonUtils.isLearningUI(uiType)) {
                        Toast.makeText(this, "Adding learning title failed.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Adding quiz title failed.", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.saveBtnID:
                populateModel();

                if(Validate()) {
                    returnIntent = new Intent();
                    SaveTitle(returnIntent);
                }
                else {
                    if(CommonUtils.isLearningUI(uiType)) {
                        Toast.makeText(this, "Edit learning title failed.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Edit quiz title failed.", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }

    }


    // Add/save title form validation
    public boolean Validate()
    {
        boolean valid = true;
        if(title.getTitle().isEmpty()) {
            if(CommonUtils.isLearningUI(uiType)) {
                titleNameEditText.setError("Learning title can't be empty.");
            }
            else{
                titleNameEditText.setError("Quiz title can't be empty.");
            }
            valid = false;
        }

        return  valid;
    }

    //Save session object after validation.
    public void SaveTitle(Intent returnIntent) {
        if(CommonUtils.isLearningUI(uiType)) {
            learningTitleDAO.save((LearningTitle) title);
        } else {
            quizTitleDAO.save((QuizTitle) title);
        }
        returnIntent.putExtra(MODE, mode);
        returnIntent.putExtra(TITLE_OBJ, title);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
