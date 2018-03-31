package sg.edu.nus.iss.pt5.photolearnapp.layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.activity.ManageLearningSessionActivity;
import sg.edu.nus.iss.pt5.photolearnapp.adapter.LearningSessionListAdapter;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.dao.DAOResultListener;
import sg.edu.nus.iss.pt5.photolearnapp.dao.LearningSessionDAO;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.Trainer;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;
import sg.edu.nus.iss.pt5.photolearnapp.util.SwipeActionHandler;
import sg.edu.nus.iss.pt5.photolearnapp.util.SwipeCallback;

public class TrainerFragment extends Fragment implements View.OnClickListener {

    private RecyclerView learningSessionListRecyclerView;
    private LearningSessionListAdapter learningSessionListAdapter;
    private List<LearningSession> learningSessionList;
    private SwipeCallback swipeCallback;

    private LearningSessionDAO learningSessionDAO;

    private SwipeActionHandler swipeActionHandler = new SwipeActionHandler() {
        @Override
        public void onRightClicked(int position) {
            learningSessionDAO.delete(learningSessionList.get(position));
            learningSessionListAdapter.removeLearningSession(position);
        }

        @Override
        public void onLeftClicked(int position) {
            Intent intent = new Intent(getContext(), ManageLearningSessionActivity.class);
            intent.putExtra(AppConstants.MODE, Mode.EDIT);
            intent.putExtra(AppConstants.LEARNING_SESSION_OBJ, learningSessionList.get(position));
            startActivityForResult(intent, AppConstants.RC_EDIT_LS);
        }
    };


    public TrainerFragment() {
        // Required empty public constructor
    }

    public static TrainerFragment newInstance() {
        TrainerFragment fragment = new TrainerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        learningSessionDAO = new LearningSessionDAO();
        learningSessionList = new ArrayList<LearningSession>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trainer, container, false);

        learningSessionListAdapter = new LearningSessionListAdapter(getActivity(), learningSessionList);

        learningSessionListRecyclerView = (RecyclerView) view.findViewById(R.id.learningSessionListRecyclerView);
        learningSessionListRecyclerView.setHasFixedSize(true);
        learningSessionListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

        FloatingActionButton addLearningSessionBtn = (FloatingActionButton) view.findViewById(R.id.addLearningSessionFButton);
        addLearningSessionBtn.setOnClickListener(this);

        loadData();

        return view;
    }

    private void loadData() {
        learningSessionDAO.getSessionsByTrainer((Trainer) SecurityContext.getInstance().getRole(), new DAOResultListener<Iterable<LearningSession>>() {
            @Override
            public void OnDAOReturned(Iterable<LearningSession> obj) {
                learningSessionList.clear();
                learningSessionList.addAll((Collection<? extends LearningSession>) obj);
                learningSessionListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.addLearningSessionFButton:
                Intent intent = new Intent(getContext(), ManageLearningSessionActivity.class);
                intent.putExtra(AppConstants.MODE, Mode.ADD);
                startActivityForResult(intent, AppConstants.RC_ADD_LS);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == AppConstants.RC_ADD_LS || requestCode == AppConstants.RC_EDIT_LS)
                && resultCode == Activity.RESULT_OK) {
            loadData();
        }

    }


}
