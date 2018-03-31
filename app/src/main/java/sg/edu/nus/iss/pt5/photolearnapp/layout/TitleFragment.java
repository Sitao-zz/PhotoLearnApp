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
import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.activity.AnswerQuizItemActivity;
import sg.edu.nus.iss.pt5.photolearnapp.activity.ItemActivity;
import sg.edu.nus.iss.pt5.photolearnapp.activity.ManageTitleActivity;
import sg.edu.nus.iss.pt5.photolearnapp.adapter.TitleListAdapter;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.constants.UIType;
import sg.edu.nus.iss.pt5.photolearnapp.dao.DAOResultListener;
import sg.edu.nus.iss.pt5.photolearnapp.dao.LearningTitleDAO;
import sg.edu.nus.iss.pt5.photolearnapp.dao.QuizTitleDAO;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.Participant;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizTitle;
import sg.edu.nus.iss.pt5.photolearnapp.model.Title;
import sg.edu.nus.iss.pt5.photolearnapp.util.CommonUtils;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;
import sg.edu.nus.iss.pt5.photolearnapp.util.SwipeActionHandler;
import sg.edu.nus.iss.pt5.photolearnapp.util.SwipeCallback;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.RC_ADD_LT;
import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.RC_EDIT_LT;

public class TitleFragment<T extends Title> extends Fragment implements View.OnClickListener {

    private static final int REQ_CODE = 002;

    private UIType uiType;
    private LearningSession learningSession;
    private List<T> titleList;

    private RecyclerView titleListRecyclerView;
    private TitleListAdapter titleListAdapter;
    private SwipeCallback swipeCallback;

    private LearningTitleDAO learningTitleDAO;
    private QuizTitleDAO quizTitleDAO;

    private SwipeActionHandler swipeActionHandler = new SwipeActionHandler() {
        @Override
        public void onRightClicked(int position) {

            if (CommonUtils.isLearningUI(uiType)) {
                learningTitleDAO.delete((LearningTitle) titleList.get(position));
            } else {
                quizTitleDAO.delete((QuizTitle) titleList.get(position));
            }

            titleListAdapter.removeTitle(position);
        }

        @Override
        public void onLeftClicked(int position) {
            Intent intent = new Intent(TitleFragment.this.getContext(), ManageTitleActivity.class);
            intent.putExtra(AppConstants.MODE, Mode.EDIT);
            intent.putExtra(AppConstants.UI_TYPE, uiType);
            intent.putExtra(AppConstants.LEARNING_SESSION_OBJ, learningSession);
            intent.putExtra(AppConstants.TITLE_OBJ, titleList.get(position));
            getActivity().startActivityForResult(intent, AppConstants.RC_EDIT_LT);
        }
    };

    public TitleFragment() {
        // Required empty public constructor
    }

    public static TitleFragment newInstance(UIType uiType) {
        TitleFragment fragment = new TitleFragment();

        Bundle args = new Bundle();
        args.putSerializable(AppConstants.UI_TYPE, uiType);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        learningTitleDAO = new LearningTitleDAO();
        quizTitleDAO = new QuizTitleDAO();
        titleList = new ArrayList<T>();

        Bundle extras = getActivity().getIntent().getExtras();
        learningSession = (LearningSession) extras.getSerializable(AppConstants.LEARNING_SESSION_OBJ);
        uiType = (UIType) getArguments().getSerializable(AppConstants.UI_TYPE);

        if (SecurityContext.getInstance().isParticipant() && CommonUtils.isParticipantAnswerMode()) {
            titleListAdapter = new TitleListAdapter(getActivity(), titleList);
        } else {
            titleListAdapter = new TitleListAdapter(getActivity(), titleList);
        }

    }

    private void loadData() {
        // Load data
        switch (uiType) {
            case LEARNING:
                if (SecurityContext.getInstance().isParticipant() && CommonUtils.isParticipantEditMode()) {
                    learningTitleDAO.getTitlesBySessionPart(learningSession, (Participant) SecurityContext.getInstance().getRole(), new DAOResultListener<Iterable<LearningTitle>>() {
                        @Override
                        public void OnDAOReturned(Iterable<LearningTitle> obj) {
                            titleList.clear();
                            titleList.addAll((List<T>) obj);
                            titleListAdapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    learningTitleDAO.getTitlesBySession(learningSession, new DAOResultListener<Iterable<LearningTitle>>() {
                        @Override
                        public void OnDAOReturned(Iterable<LearningTitle> obj) {
                            titleList.clear();
                            titleList.addAll((List<T>) obj);
                            titleListAdapter.notifyDataSetChanged();
                        }
                    });
                }

                break;
            case QUIZ:
                quizTitleDAO.getTitlesBySession(learningSession, new DAOResultListener<Iterable<QuizTitle>>() {
                    @Override
                    public void OnDAOReturned(Iterable<QuizTitle> obj) {
                        titleList.clear();
                        titleList.addAll((List<T>) obj);
                        titleListAdapter.notifyDataSetChanged();

                    }
                });
                break;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_title, container, false);

        initTitleListView(view);

        initAddTitleButton(view);

        loadData();

        return view;
    }

    private void initTitleListView(View view) {

        titleListRecyclerView = (RecyclerView) view.findViewById(R.id.titleListRecyclerViewID);
        titleListRecyclerView.setHasFixedSize(true);
        titleListRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        titleListRecyclerView.setAdapter(titleListAdapter);

        // Trainer can only edit title
        if (SecurityContext.getInstance().isTrainer()) {
            swipeCallback = new SwipeCallback(swipeActionHandler);
            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeCallback);
            itemTouchhelper.attachToRecyclerView(titleListRecyclerView);

            titleListRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    swipeCallback.onDraw(c);
                }
            });
        }
    }

    private void initAddTitleButton(View view) {
        FloatingActionButton addTitleBtn = (FloatingActionButton) view.findViewById(R.id.addTitleFButton);
        if ((SecurityContext.getInstance().isTrainer() && UIType.LEARNING == uiType)
                || (SecurityContext.getInstance().isParticipant() && UIType.QUIZ == uiType)
                || (SecurityContext.getInstance().isParticipant() && UIType.LEARNING == uiType && CommonUtils.isParticipantViewMode())) {
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
                intent.putExtra(AppConstants.LEARNING_SESSION_OBJ, learningSession);
                getActivity().startActivityForResult(intent, RC_ADD_LT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == RC_ADD_LT || requestCode == RC_EDIT_LT)
                && resultCode == Activity.RESULT_OK) {
            loadData();
        }
    }
}
