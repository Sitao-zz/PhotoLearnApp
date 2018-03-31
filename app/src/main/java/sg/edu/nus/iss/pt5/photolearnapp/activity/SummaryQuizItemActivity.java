package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.adapter.SummaryQuizItemPagerAdapter;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.dao.DAOResultListener;
import sg.edu.nus.iss.pt5.photolearnapp.dao.QuizItemDAO;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;

public class SummaryQuizItemActivity extends BaseActivity implements View.OnClickListener {

    private SummaryQuizItemPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    private ImageButton leftNav;
    private ImageButton rightNav;

    private QuizTitle quizTitle;
    private QuizItemDAO quizItemDAO;
    private List<QuizItem> quizItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_quiz_item);

        quizItemDAO = new QuizItemDAO();

        // Read Intent Parameters
        Bundle extras = getIntent().getExtras();
        quizTitle = (QuizTitle) extras.get(AppConstants.TITLE_OBJ);

        quizItemList = new ArrayList<QuizItem>();
        pagerAdapter = new SummaryQuizItemPagerAdapter(getSupportFragmentManager(), quizItemList);
        setTitle("Quiz Summary");
        loadData();

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.summaryViewPagerID);
        viewPager.setVisibility(View.VISIBLE);
        viewPager.setAdapter(pagerAdapter);

        leftNav = (ImageButton) findViewById(R.id.leftNavImageBtnID);
        leftNav.setOnClickListener(this);
        rightNav = (ImageButton) findViewById(R.id.rightNavImageBtnID);
        rightNav.setOnClickListener(this);

    }

    private void loadData() {
        quizItemDAO.getItemsByTitle((QuizTitle) quizTitle, new DAOResultListener<Iterable<QuizItem>>() {
            @Override
            public void OnDAOReturned(Iterable<QuizItem> obj) {
                quizItemList.clear();
                quizItemList.addAll((List) obj);
                pagerAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftNavImageBtnID:
                int tab01 = viewPager.getCurrentItem();
                if (tab01 > 0) {
                    viewPager.setCurrentItem(--tab01);
                } else if (tab01 == 0) {
                    viewPager.setCurrentItem(tab01);
                }
                break;
            case R.id.rightNavImageBtnID:
                int tab02 = viewPager.getCurrentItem();
                viewPager.setCurrentItem(++tab02);
                break;
        }
    }
}
