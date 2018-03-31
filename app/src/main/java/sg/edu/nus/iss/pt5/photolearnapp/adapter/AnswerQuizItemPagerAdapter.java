package sg.edu.nus.iss.pt5.photolearnapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.layout.AnswerQuizItemFragment;
import sg.edu.nus.iss.pt5.photolearnapp.layout.ItemFragment;
import sg.edu.nus.iss.pt5.photolearnapp.layout.SummaryQuizItemFragment;
import sg.edu.nus.iss.pt5.photolearnapp.model.Item;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.Title;

/**
 * Created by mjeyakaran on 23/3/18.
 */

public class AnswerQuizItemPagerAdapter extends FragmentStatePagerAdapter {

    private List<QuizItem> itemList;

    public AnswerQuizItemPagerAdapter(FragmentManager fm, List<QuizItem> itemList) {
        super(fm);
        this.itemList = itemList;
    }

    @Override
    public Fragment getItem(int position) {
        QuizItem quizItem = itemList.get(position);
        return AnswerQuizItemFragment.newInstance(quizItem, (position > itemList.size() - 2));
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