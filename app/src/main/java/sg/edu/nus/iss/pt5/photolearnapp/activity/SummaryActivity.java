package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.MODE;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.TITLE_OBJ;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener {

    private QuizTitle quizTitle;
    private Button summaryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Read Intent Parameters
        Bundle extras = getIntent().getExtras();
        quizTitle = (QuizTitle) extras.getSerializable(TITLE_OBJ);

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
