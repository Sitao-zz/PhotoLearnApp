package sg.edu.nus.iss.pt5.photolearnapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.layout.AnswerQuizItemFragment;
import sg.edu.nus.iss.pt5.photolearnapp.layout.SummaryQuizItemFragment;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;

/**
 * Created by mjeyakaran on 23/3/18.
 */

public class SummaryQuizItemPagerAdapter extends FragmentStatePagerAdapter {

    private List<QuizItem> itemList;

    public SummaryQuizItemPagerAdapter(FragmentManager fm, List<QuizItem> itemList) {
        super(fm);
        this.itemList = itemList;
    }

    @Override
    public Fragment getItem(int position) {
        QuizItem quizItem = itemList.get(position);
        return SummaryQuizItemFragment.newInstance(quizItem, (position > itemList.size() - 2));
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        // it will recreate all Fragments when
        // notifyDataSetChanged is called
        return POSITION_NONE;
    }

}