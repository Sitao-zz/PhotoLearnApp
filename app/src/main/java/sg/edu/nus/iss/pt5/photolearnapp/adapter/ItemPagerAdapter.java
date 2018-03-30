package sg.edu.nus.iss.pt5.photolearnapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.dao.DummyDataProvider;
import sg.edu.nus.iss.pt5.photolearnapp.layout.ItemFragment;
import sg.edu.nus.iss.pt5.photolearnapp.model.Item;
import sg.edu.nus.iss.pt5.photolearnapp.model.Title;

/**
 * Created by mjeyakaran on 23/3/18.
 */

public class ItemPagerAdapter<T extends Item> extends FragmentStatePagerAdapter {

    private Title title;
    private List<T> itemList;

    public ItemPagerAdapter(FragmentManager fm, Title title, List<T> itemList) {
        super(fm);
        this.title = title;
        this.itemList = itemList;
    }

    @Override
    public Fragment getItem(int position) {

        T item = itemList.get(position);
        ItemFragment itemFragment = ItemFragment.newInstance(position, title, item);

        return itemFragment;
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

    public List<T> getItemList() {
        return itemList;
    }
}