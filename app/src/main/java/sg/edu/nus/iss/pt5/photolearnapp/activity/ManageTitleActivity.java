package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.UUID;

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

        init();

        titleNameEditText.setText(title.getTitle());

    }

    private void init() {

        if (CommonUtils.isLearningUI(uiType)) {
            if (Mode.ADD == mode) {
                title = new LearningTitle();
                title.setId(UUID.randomUUID().toString());
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
                title.setId(UUID.randomUUID().toString());
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

                if(CommonUtils.isLearningUI(uiType)) {
                    learningTitleDAO.save((LearningTitle) title);
                } else {
                    quizTitleDAO.save((QuizTitle) title);
                }

                returnIntent = new Intent();
                returnIntent.putExtra(MODE, mode);
                returnIntent.putExtra(TITLE_OBJ, title);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;

            case R.id.saveBtnID:
                updateModel();

                if(CommonUtils.isLearningUI(uiType)) {
                    learningTitleDAO.save((LearningTitle) title);
                } else {
                    quizTitleDAO.save((QuizTitle) title);
                }

                returnIntent = new Intent();
                returnIntent.putExtra(MODE, mode);
                returnIntent.putExtra(TITLE_OBJ, title);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
        }

    }
}
