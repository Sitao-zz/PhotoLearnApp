package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.dao.DummyDataProvider;
import sg.edu.nus.iss.pt5.photolearnapp.util.SwipeCallback;
import sg.edu.nus.iss.pt5.photolearnapp.adapter.LearningSessionListAdapter;
import sg.edu.nus.iss.pt5.photolearnapp.util.SwipeActionHandler;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.ITEM_OBJ;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.MODE;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.POSITION;

public class LearningSessionActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int REQ_CODE = 0001;

    private RecyclerView learningSessionListRecyclerView;
    private LearningSessionListAdapter learningSessionListAdapter;
    private List<LearningSession> learningSessionList;
    private SwipeCallback swipeCallback;

    private SwipeActionHandler swipeActionHandler = new SwipeActionHandler() {
        @Override
        public void onRightClicked(int position) {
            learningSessionListAdapter.removeLearningSession(position);
        }

        @Override
        public void onLeftClicked(int position) {
            Intent intent = new Intent(LearningSessionActivity.this, ManageLearningSessionActivity.class);
            intent.putExtra(AppConstants.MODE, Mode.EDIT);
            intent.putExtra(AppConstants.LEARNING_SESSION_OBJ, learningSessionList.get(position));
            LearningSessionActivity.this.startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_session);

        loadData();
        initUI();

    }

    private void loadData() {
        learningSessionList = DummyDataProvider.getLearningSessionList();
    }

    private void initUI() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        learningSessionListAdapter = new LearningSessionListAdapter(this, learningSessionList);

        learningSessionListRecyclerView = (RecyclerView) findViewById(R.id.learningSessionListRecyclerView);
        learningSessionListRecyclerView.setHasFixedSize(true);
        learningSessionListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        learningSessionListRecyclerView.setAdapter(learningSessionListAdapter);

        swipeCallback = new SwipeCallback(swipeActionHandler);
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeCallback);
        itemTouchhelper.attachToRecyclerView(learningSessionListRecyclerView);

        learningSessionListRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeCallback.onDraw(c);
            }
        });

        FloatingActionButton addLearningSessionBtn = (FloatingActionButton) findViewById(R.id.addLearningSessionFButton);
        addLearningSessionBtn.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.addLearningSessionFButton:
                Intent intent = new Intent(this, ManageLearningSessionActivity.class);
                intent.putExtra(AppConstants.MODE, Mode.ADD);
                startActivityForResult(intent, REQ_CODE);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE && resultCode == Activity.RESULT_OK) {

            Bundle extras = data.getExtras();

            Mode mode = (Mode) extras.get(AppConstants.MODE);
            LearningSession learningSession = (LearningSession) extras.get(AppConstants.LEARNING_SESSION_OBJ);

            switch (mode) {
                case ADD:
                    learningSessionList.add(learningSession);
                    learningSessionListAdapter.notifyDataSetChanged();
                    break;
                case EDIT:
                    break;
                case DELETE:
                    break;
            }

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.switchAsTrainerNavID:
                break;
            case R.id.switchAsParticipantNavID:
                break;
            case R.id.signOutNavID:
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
