package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
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

public class LearningSessionActivity extends AppCompatActivity implements View.OnClickListener {

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
}
