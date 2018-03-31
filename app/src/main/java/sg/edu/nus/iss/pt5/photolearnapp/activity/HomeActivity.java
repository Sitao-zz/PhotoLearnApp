package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.dao.LearningSessionDAO;
import sg.edu.nus.iss.pt5.photolearnapp.layout.ParticipantFragment;
import sg.edu.nus.iss.pt5.photolearnapp.layout.TrainerFragment;
import sg.edu.nus.iss.pt5.photolearnapp.model.Participant;
import sg.edu.nus.iss.pt5.photolearnapp.model.Trainer;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView userNameTextView;
    private TextView userRoleTextView;

    private LinearLayout sessionDetail;
    private SearchView learningSessionSearchView;
    private LearningSession learningSession;

    private TextView courseDateTextView;
    private TextView courseCodeTextView;
    private TextView courseNameTextView;
    private TextView moduleNumberTextView;
    private TextView moduleNameTextView;

    private LearningSessionDAO learningSessionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();

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

            TrainerFragment trainerFragment = TrainerFragment.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.homeFrameLayoutID, trainerFragment).commit();

        } else if (SecurityContext.getInstance().isParticipant()) {

            userRoleTextView.setText("Participant");
            MenuItem switchAsTrainerMenuItem = navigationViewMenu.findItem(R.id.switchAsTrainerNavID).setVisible(false);
            switchAsTrainerMenuItem.setVisible(true);

            ParticipantFragment participantFragment = ParticipantFragment.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.homeFrameLayoutID, participantFragment).commit();

        } else {
            userRoleTextView.setText("Unknown");
        }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

}
