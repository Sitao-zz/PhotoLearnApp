package sg.edu.nus.iss.pt5.photolearnapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sg.edu.nus.iss.pt5.photolearnapp.constants.UIType;
import sg.edu.nus.iss.pt5.photolearnapp.layout.TitleFragment;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;

/**
 * Created by mjeyakaran on 23/3/18.
 */

public class TitlePagerAdapter extends FragmentStatePagerAdapter {

    private LearningSession learningSession;

    public TitlePagerAdapter(FragmentManager fm, LearningSession learningSession) {
        super(fm);
        this.learningSession = learningSession;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TitleFragment learningTitleFragment = TitleFragment.newInstance(UIType.LEARNING, learningSession);
                return learningTitleFragment;
            case 1:
                TitleFragment quizTitleFragment = TitleFragment.newInstance(UIType.QUIZ, learningSession);
                return quizTitleFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}