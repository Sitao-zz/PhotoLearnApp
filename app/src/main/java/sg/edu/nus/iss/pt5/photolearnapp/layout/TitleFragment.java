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

import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.activity.ManageTitleActivity;
import sg.edu.nus.iss.pt5.photolearnapp.adapter.TitleListAdapter;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.constants.UIType;
import sg.edu.nus.iss.pt5.photolearnapp.dao.DummyDataProvider;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.Title;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;
import sg.edu.nus.iss.pt5.photolearnapp.util.SwipeActionHandler;
import sg.edu.nus.iss.pt5.photolearnapp.util.SwipeCallback;

public class TitleFragment<T extends Title> extends Fragment implements View.OnClickListener {

    private static final int REQ_CODE = 002;

    private UIType uiType;
    private LearningSession learningSession;
    private List<T> titleList;

    private RecyclerView titleListRecyclerView;
    private TitleListAdapter titleListAdapter;
    private SwipeCallback swipeCallback;

    private SwipeActionHandler swipeActionHandler = new SwipeActionHandler() {
        @Override
        public void onRightClicked(int position) {
            titleListAdapter.removeTitle(position);
        }

        @Override
        public void onLeftClicked(int position) {
            Intent intent = new Intent(TitleFragment.this.getContext(), ManageTitleActivity.class);
            intent.putExtra(AppConstants.MODE, Mode.EDIT);
            intent.putExtra(AppConstants.UI_TYPE, uiType);
            intent.putExtra(AppConstants.TITLE_OBJ, titleList.get(position));
            TitleFragment.this.startActivity(intent);
        }
    };

    public TitleFragment() {
        // Required empty public constructor
    }

    public static TitleFragment newInstance(UIType titleUIType, LearningSession learningSession) {

        TitleFragment fragment = new TitleFragment();

        Bundle args = new Bundle();
        args.putSerializable(AppConstants.UI_TYPE, titleUIType);
        args.putSerializable(AppConstants.LEARNING_SESSION_OBJ, learningSession);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            uiType = (UIType) getArguments().getSerializable(AppConstants.UI_TYPE);
            learningSession = (LearningSession) getArguments().getSerializable(AppConstants.LEARNING_SESSION_OBJ);
        }

        // Load data
        switch (uiType) {
            case LEARNING:
                // TODO Load Learning Titles
                titleList = (List<T>) DummyDataProvider.getLearningTitleList();
                break;
            case QUIZ:
                // TODO Load Quiz Titles
                titleList = (List<T>) DummyDataProvider.getQuizTitleList();
                break;
        }

        titleListAdapter = new TitleListAdapter(this.getContext(), titleList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_title, container, false);

        titleListRecyclerView = (RecyclerView) view.findViewById(R.id.titleListRecyclerViewID);
        titleListRecyclerView.setHasFixedSize(true);
        titleListRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        titleListRecyclerView.setAdapter(titleListAdapter);

        swipeCallback = new SwipeCallback(swipeActionHandler);
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeCallback);
        itemTouchhelper.attachToRecyclerView(titleListRecyclerView);

        titleListRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeCallback.onDraw(c);
            }
        });

        initAddTitleButton(view);

        return view;
    }

    private void initAddTitleButton(View view) {
        FloatingActionButton addTitleBtn = (FloatingActionButton) view.findViewById(R.id.addTitleFButton);
        if (SecurityContext.getInstance().isTrainer() && UIType.LEARNING == uiType
                || SecurityContext.getInstance().isParticipant() && UIType.QUIZ == uiType) {
            addTitleBtn.setVisibility(View.GONE);
        } else {
            addTitleBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addTitleFButton:
                Intent intent = new Intent(this.getContext(), ManageTitleActivity.class);
                intent.putExtra(AppConstants.MODE, Mode.ADD);
                intent.putExtra(AppConstants.UI_TYPE, uiType);
                startActivityForResult(intent, REQ_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE && resultCode == Activity.RESULT_OK) {

            Bundle extras = data.getExtras();

            Mode mode = (Mode) extras.get(AppConstants.MODE);
            T title = (T) extras.get(AppConstants.TITLE_OBJ);

            switch (mode) {
                case ADD:
                    titleList.add(title);
                    titleListAdapter.notifyDataSetChanged();
                    break;
                case EDIT:
                    break;
                case DELETE:
                    break;
            }

        }
    }
}
