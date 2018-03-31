package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.adapter.TitlePagerAdapter;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.constants.UIType;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.Participant;
import sg.edu.nus.iss.pt5.photolearnapp.model.Title;
import sg.edu.nus.iss.pt5.photolearnapp.util.CommonUtils;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.MODE;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.RC_ADD_LT;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.RC_EDIT_LT;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.TITLE_OBJ;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.UI_TYPE;

public class TitleActivity extends BaseActivity {

    private TitlePagerAdapter titlePagerAdapter;
    private ViewPager mViewPager;

    private LearningSession learningSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        // Read Intent Parameters
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            learningSession = (LearningSession) extras.get(AppConstants.LEARNING_SESSION_OBJ);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titlePagerAdapter = new TitlePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(titlePagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if(SecurityContext.getInstance().isParticipant()) {
            if(CommonUtils.isParticipantEditMode()) {
                tabLayout.removeTabAt(1);
                titlePagerAdapter.removeTabPage();
            } else if(CommonUtils.isParticipantEditMode()) {
                tabLayout.removeTabAt(2);
                titlePagerAdapter.removeTabPage();
            }
        }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
    }

}
