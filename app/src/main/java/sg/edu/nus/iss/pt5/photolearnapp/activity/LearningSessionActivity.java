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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.dao.DAOResultListener;
import sg.edu.nus.iss.pt5.photolearnapp.dao.DummyDataProvider;
import sg.edu.nus.iss.pt5.photolearnapp.dao.LearningSessionDAO;
import sg.edu.nus.iss.pt5.photolearnapp.model.Participant;
import sg.edu.nus.iss.pt5.photolearnapp.model.Trainer;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;
import sg.edu.nus.iss.pt5.photolearnapp.util.SwipeCallback;
import sg.edu.nus.iss.pt5.photolearnapp.adapter.LearningSessionListAdapter;
import sg.edu.nus.iss.pt5.photolearnapp.util.SwipeActionHandler;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.ITEM_OBJ;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.MODE;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.POSITION;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.RC_TITLE_EDIT_MODE;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.RC_TITLE_READ_MODE;

public class LearningSessionActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private RecyclerView learningSessionListRecyclerView;
    private LearningSessionListAdapter learningSessionListAdapter;
    private List<LearningSession> learningSessionList;
    private SwipeCallback swipeCallback;

    private TextView userNameTextView;
    private TextView userRoleTextView;
    private View trainerContent;
    private View participantContent;

    private Button viewModeBtn;
    private Button editModeBtn;

    private LinearLayout sessionDetail;
    private SearchView learningSessionSearchView;
    private LearningSession learningSession;

    private TextView courseDateTextView;
    private TextView courseCodeTextView;
    private TextView courseNameTextView;
    private TextView moduleNumberTextView;
    private TextView moduleNameTextView;

    private LearningSessionDAO learningSessionDAO;

    private SwipeActionHandler swipeActionHandler = new SwipeActionHandler() {
        @Override
        public void onRightClicked(int position) {
            learningSessionDAO.delete(learningSessionList.get(position));
            learningSessionListAdapter.removeLearningSession(position);
        }

        @Override
        public void onLeftClicked(int position) {
            Intent intent = new Intent(LearningSessionActivity.this, ManageLearningSessionActivity.class);
            intent.putExtra(AppConstants.MODE, Mode.EDIT);
            intent.putExtra(AppConstants.LEARNING_SESSION_OBJ, learningSessionList.get(position));
            LearningSessionActivity.this.startActivityForResult(intent, AppConstants.RC_EDIT_LS);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_session);

        learningSessionDAO = new LearningSessionDAO();
        learningSessionList = new ArrayList<LearningSession>();

        loadData();
        initUI();

    }

    private void loadData() {
        if (SecurityContext.getInstance().isTrainer()) {
            learningSessionDAO.getSessionsByTrainer((Trainer) SecurityContext.getInstance().getRole(), new DAOResultListener<Iterable<LearningSession>>() {
                @Override
                public void OnDAOReturned(Iterable<LearningSession> obj) {
                    learningSessionList.clear();
                    learningSessionList.addAll((Collection<? extends LearningSession>) obj);
                    learningSessionListAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void initUI() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu navigationViewMenu = navigationView.getMenu();
        View headerView = navigationView.getHeaderView(0);

        userNameTextView = (TextView) headerView.findViewById(R.id.userNameTextViewID);
        userNameTextView.setText(SecurityContext.getInstance().getRole().getUser().getName());
        userRoleTextView = (TextView) headerView.findViewById(R.id.userRoleTextViewID);

        if (SecurityContext.getInstance().isTrainer()) {

            userRoleTextView.setText("Trainer");
            MenuItem switchAsParticipantMenuItem = navigationViewMenu.findItem(R.id.switchAsParticipantNavID).setVisible(false);
            switchAsParticipantMenuItem.setVisible(true);

            showTrainerView();

        } else if (SecurityContext.getInstance().isParticipant()) {

            userRoleTextView.setText("Participant");
            MenuItem switchAsTrainerMenuItem = navigationViewMenu.findItem(R.id.switchAsTrainerNavID).setVisible(false);
            switchAsTrainerMenuItem.setVisible(true);

            showParticipantView();

        } else {
            userRoleTextView.setText("Unknown");
        }

    }

    private void showTrainerView() {

        trainerContent = findViewById(R.id.trainerContentID);
        trainerContent.setVisibility(View.VISIBLE);

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

    }

    private void showParticipantView() {

        participantContent = findViewById(R.id.participantContentID);
        participantContent.setVisibility(View.VISIBLE);

        sessionDetail = (LinearLayout) findViewById(R.id.sessionDetailID);
        sessionDetail.setVisibility(View.GONE);

        learningSessionSearchView = (SearchView) findViewById(R.id.learningSessionSearchViewID);
        learningSessionSearchView.setQuery("20180301-IoT-M01",false);
        learningSessionSearchView.setQueryHint("Learning Session ID");
        learningSessionSearchView.setOnQueryTextListener(this);

        courseDateTextView = (TextView) findViewById(R.id.courseDateTextViewID);
        courseCodeTextView = (TextView) findViewById(R.id.courseCodeTextViewID);
        courseNameTextView = (TextView) findViewById(R.id.courseNameTextViewID);
        moduleNumberTextView = (TextView) findViewById(R.id.moduleNumberTextViewID);
        moduleNameTextView = (TextView) findViewById(R.id.moduleNameTextViewID);

        viewModeBtn = (Button) findViewById(R.id.viewModeBtnID);
        viewModeBtn.setOnClickListener(this);
        editModeBtn = (Button) findViewById(R.id.editModeBtnID);
        editModeBtn.setOnClickListener(this);

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
                startActivityForResult(intent, AppConstants.RC_ADD_LS);
                break;
            case R.id.viewModeBtnID:
                Intent viewModeIntent = new Intent(this, TitleActivity.class);
                ((Participant) SecurityContext.getInstance().getRole()).setMode(Mode.VIEW);
                viewModeIntent.putExtra(AppConstants.LEARNING_SESSION_OBJ, learningSession);
                startActivityForResult(viewModeIntent, RC_TITLE_READ_MODE);
                break;
            case R.id.editModeBtnID:
                Intent editModeIntent = new Intent(this, TitleActivity.class);
                ((Participant) SecurityContext.getInstance().getRole()).setMode(Mode.EDIT);
                editModeIntent.putExtra(AppConstants.LEARNING_SESSION_OBJ, learningSession);
                startActivityForResult(editModeIntent, RC_TITLE_EDIT_MODE);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == AppConstants.RC_ADD_LS || requestCode == AppConstants.RC_EDIT_LS)
                && resultCode == Activity.RESULT_OK) {
            loadData();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.switchAsTrainerNavID:
                SecurityContext.getInstance().setRole(new Trainer(SecurityContext.getInstance().getRole().getUser()));
                refreshActivity();
                break;
            case R.id.switchAsParticipantNavID:
                SecurityContext.getInstance().setRole(new Participant(SecurityContext.getInstance().getRole().getUser()));
                refreshActivity();
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

    public void refreshActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        learningSessionDAO.getObject(query, new DAOResultListener<LearningSession>() {
            @Override
            public void OnDAOReturned(LearningSession learningSession) {
                if (learningSession != null) {
                    sessionDetail.setVisibility(View.VISIBLE);
                    updateLearningSessionDetailUI(learningSession);
                } else {
                    sessionDetail.setVisibility(View.GONE);
                }
            }
        });
        return false;
    }

    private void updateLearningSessionDetailUI(LearningSession learningSession) {
        this.learningSession = learningSession;
        SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_DISPLAY_PATTERN);
        courseDateTextView.setText(dateFormat.format(learningSession.getCourseDate()));
        courseCodeTextView .setText(learningSession.getCourseCode());
        courseNameTextView.setText(learningSession.getCourseName());
        moduleNumberTextView.setText(learningSession.getModuleNumber());
        moduleNameTextView.setText(learningSession.getModuleName());
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
