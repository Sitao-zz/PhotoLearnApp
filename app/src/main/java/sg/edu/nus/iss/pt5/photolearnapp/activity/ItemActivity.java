package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.adapter.ItemPagerAdapter;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.constants.UIType;
import sg.edu.nus.iss.pt5.photolearnapp.dao.DummyDataProvider;
import sg.edu.nus.iss.pt5.photolearnapp.model.Item;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.Title;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.MODE;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.POSITION;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.RC_ADD_ITEM;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.RC_EDIT_ITEM;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {

    private ItemPagerAdapter itemPagerAdapter;
    private ViewPager mViewPager;

    private Title title;
    private UIType uiType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // Read Intent Parameters
        Bundle extras = getIntent().getExtras();
        title = (Title) extras.get(AppConstants.TITLE_OBJ);
        uiType = (title instanceof LearningTitle) ? UIType.LEARNING : UIType.QUIZ;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (uiType == UIType.LEARNING) {
            itemPagerAdapter = new ItemPagerAdapter<LearningItem>(getSupportFragmentManager(), DummyDataProvider.getLearningItemList());
        } else {
            itemPagerAdapter = new ItemPagerAdapter<QuizItem>(getSupportFragmentManager(), DummyDataProvider.getQuizItemList());
        }

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(itemPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addItemFButton);
        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addItemFButton:
                Intent intent = new Intent(this, ManageItemActivity.class);
                intent.putExtra(AppConstants.MODE, Mode.ADD);
                intent.putExtra(AppConstants.UI_TYPE, uiType);
                startActivityForResult(intent, RC_ADD_ITEM);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_ADD_ITEM && resultCode == Activity.RESULT_OK) {

            Bundle extras = data.getExtras();

            Mode mode = (Mode) extras.get(AppConstants.MODE);
            Item item = (Item) extras.get(AppConstants.ITEM_OBJ);

            switch (mode) {
                case ADD:
                    itemPagerAdapter.getItemList().add(item);
                    itemPagerAdapter.notifyDataSetChanged();
                    break;
                case EDIT:
                    break;
                case DELETE:
                    break;
            }

        } else if (requestCode == RC_EDIT_ITEM && resultCode == Activity.RESULT_OK) {

            Bundle extras = data.getExtras();

            Item item = (Item) extras.get(AppConstants.ITEM_OBJ);
            int position = extras.getInt(POSITION);

            Mode mode = (Mode) extras.get(MODE);
            switch (mode) {
                case EDIT:
                    itemPagerAdapter.editItem(position, item);
                    break;
                case DELETE:
                    itemPagerAdapter.deleteItem(position, item);
                    break;
            }
        }
    }

}
