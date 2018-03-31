package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.adapter.ItemPagerAdapter;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.dao.DAOResultListener;
import sg.edu.nus.iss.pt5.photolearnapp.dao.LearningItemDAO;
import sg.edu.nus.iss.pt5.photolearnapp.dao.QuizItemDAO;
import sg.edu.nus.iss.pt5.photolearnapp.model.Item;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.Title;
import sg.edu.nus.iss.pt5.photolearnapp.util.CommonUtils;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.RC_ADD_ITEM;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.RC_EDIT_ITEM;

public class ItemActivity extends BaseActivity implements View.OnClickListener {

    private ItemPagerAdapter itemPagerAdapter;
    private ViewPager mViewPager;

    private Title title;

    private LearningItemDAO learningItemDAO;
    private QuizItemDAO quizItemDAO;

    private List<? extends Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        learningItemDAO = new LearningItemDAO();
        quizItemDAO = new QuizItemDAO();

        // Read Intent Parameters
        Bundle extras = getIntent().getExtras();
        title = (Title) extras.get(AppConstants.TITLE_OBJ);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (CommonUtils.isLearningUI(title)) {
            itemList = new ArrayList<LearningItem>();
            itemPagerAdapter = new ItemPagerAdapter<LearningItem>(getSupportFragmentManager(),title, (List<LearningItem>) itemList);
            toolbar.setTitle(getString(R.string.learning_item));
        } else {
            itemList = new ArrayList<QuizItem>();
            itemPagerAdapter = new ItemPagerAdapter<QuizItem>(getSupportFragmentManager(),title, (List<QuizItem>) itemList);
            toolbar.setTitle(getString(R.string.quiz_item));
        }
        loadData();

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(itemPagerAdapter);

        initAddItemButton();

    }

    private void loadData() {
        if (CommonUtils.isLearningUI(title)) {
            learningItemDAO.getItemsByTitle((LearningTitle) title, new DAOResultListener<Iterable<LearningItem>>() {
                @Override
                public void OnDAOReturned(Iterable<LearningItem> obj) {
                    itemList.clear();
                    itemList.addAll((List) obj);
                    itemPagerAdapter.notifyDataSetChanged();
                }
            });
        } else {
            quizItemDAO.getItemsByTitle((QuizTitle) title, new DAOResultListener<Iterable<QuizItem>>() {
                @Override
                public void OnDAOReturned(Iterable<QuizItem> obj) {
                    itemList.clear();
                    itemList.addAll((List) obj);
                    itemPagerAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void initAddItemButton() {
        FloatingActionButton addItemButton = (FloatingActionButton) findViewById(R.id.addItemFButton);
        if ((SecurityContext.getInstance().isTrainer() && CommonUtils.isLearningUI(title))
                || (SecurityContext.getInstance().isParticipant() && CommonUtils.isQuizUI(title))
                || (SecurityContext.getInstance().isParticipant() && CommonUtils.isParticipantViewMode())) {
            addItemButton.setVisibility(View.GONE);
        } else {
            addItemButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addItemFButton:
                Intent intent = new Intent(this, ManageItemActivity.class);
                intent.putExtra(AppConstants.MODE, Mode.ADD);
                intent.putExtra(AppConstants.TITLE_OBJ, title);
                startActivityForResult(intent, RC_ADD_ITEM);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == RC_ADD_ITEM || requestCode == RC_EDIT_ITEM)
                && resultCode == Activity.RESULT_OK) {
            loadData();
        }
    }

}
