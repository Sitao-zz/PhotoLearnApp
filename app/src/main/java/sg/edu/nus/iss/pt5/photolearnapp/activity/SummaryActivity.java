package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.ACOUNT;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.MODE;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.QCOUNT;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.TITLE_OBJ;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener {

    private QuizTitle quizTitle;
    private int cQuestions;
    private int cAnswers;
    private Button summaryBtn;

    private TextView quizTitleTextView;
    private TextView cQuestionTextView;
    private TextView cCorrectTextView;
    private TextView cIncorrectTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        setTitle("Quiz Summary");

        // Read Intent Parameters
        Bundle extras = getIntent().getExtras();
        quizTitle = (QuizTitle) extras.getSerializable(TITLE_OBJ);
        cQuestions = extras.getInt(QCOUNT);
        cAnswers = extras.getInt(ACOUNT);

        quizTitleTextView = (TextView) findViewById(R.id.quizTitleTextViewID) ;
        quizTitleTextView.setText(quizTitle.getTitle());
        cQuestionTextView = (TextView) findViewById(R.id.cQuestionTextViewID);
        cQuestionTextView.setText(Integer.toString(cQuestions));
        cCorrectTextView = (TextView) findViewById(R.id.cCorrectTextViewID);
        cCorrectTextView.setText(Integer.toString(cAnswers));
        cIncorrectTextView = (TextView) findViewById(R.id.cIncorrectTextViewID);
        cIncorrectTextView.setText(Integer.toString(cQuestions - cAnswers));

        summaryBtn = (Button) findViewById(R.id.summaryBtnID);
        summaryBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.summaryBtnID:
                Intent intent = new Intent(this, SummaryQuizItemActivity.class);
                intent.putExtra(TITLE_OBJ, quizTitle);
                startActivity(intent);
                break;
        }
    }
}
