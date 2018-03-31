package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.adapter.AnswerQuizItemPagerAdapter;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.dao.DAOResultListener;
import sg.edu.nus.iss.pt5.photolearnapp.dao.QuizItemDAO;
import sg.edu.nus.iss.pt5.photolearnapp.layout.AnswerQuizItemFragment;
import sg.edu.nus.iss.pt5.photolearnapp.model.Participant;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.ACOUNT;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.QCOUNT;

public class AnswerQuizItemActivity extends BaseActivity implements View.OnClickListener, AnswerQuizItemFragment.NavigateListener {

    private AnswerQuizItemPagerAdapter answerQuizItemPagerAdapter;
    private ViewPager viewPager;

    private QuizTitle quizTitle;
    private QuizItemDAO quizItemDAO;
    private List<QuizItem> quizItemList;

    private int correctAnsCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_quiz_item);

        quizItemDAO = new QuizItemDAO();

        // Read Intent Parameters
        Bundle extras = getIntent().getExtras();
        quizTitle = (QuizTitle) extras.get(AppConstants.TITLE_OBJ);

        quizItemList = new ArrayList<QuizItem>();
        answerQuizItemPagerAdapter = new AnswerQuizItemPagerAdapter(getSupportFragmentManager(), quizItemList);
        setTitle("Answer Quiz");
        loadData();

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.answerViewPagerID);
        viewPager.setAdapter(answerQuizItemPagerAdapter);

    }

    private void loadData() {
        quizItemDAO.getItemsByTitle((QuizTitle) quizTitle, new DAOResultListener<Iterable<QuizItem>>() {
            @Override
            public void OnDAOReturned(Iterable<QuizItem> obj) {
                quizItemList.clear();
                quizItemList.addAll((List) obj);
                answerQuizItemPagerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void onNextClick(boolean isCorrectAnswer) {

        if(isCorrectAnswer) {
            correctAnsCount++;
        }

        int currPos = viewPager.getCurrentItem();
        answerQuizItemPagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(currPos + 1);
    }

    @Override
    public void onSubmitClick(boolean isCorrectAnswer) {

        if(isCorrectAnswer) {
            correctAnsCount++;
        }
        
        Intent summaryIntent = new Intent(this, SummaryActivity.class);
        summaryIntent.putExtra(QCOUNT, quizItemList.size());
        summaryIntent.putExtra(ACOUNT, correctAnsCount);
        summaryIntent.putExtra(AppConstants.TITLE_OBJ, quizTitle);
        startActivity(summaryIntent);
    }
}
