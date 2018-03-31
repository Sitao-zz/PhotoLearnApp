package sg.edu.nus.iss.pt5.photolearnapp.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.activity.AnswerQuizTitleActivity;
import sg.edu.nus.iss.pt5.photolearnapp.activity.LearningTitleActivity;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.dao.DAOResultListener;
import sg.edu.nus.iss.pt5.photolearnapp.dao.LearningSessionDAO;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.Participant;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;

public class ParticipantFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener {

    private Button viewModeBtn;
    private Button editModeBtn;
    private Button answerQuizBtn;

    private LinearLayout sessionDetail;
    private SearchView learningSessionSearchView;
    private LearningSession learningSession;

    private TextView courseDateTextView;
    private TextView courseCodeTextView;
    private TextView courseNameTextView;
    private TextView moduleNumberTextView;
    private TextView moduleNameTextView;

    private LearningSessionDAO learningSessionDAO;

    public ParticipantFragment() {
        // Required empty public constructor
    }

    public static ParticipantFragment newInstance() {
        ParticipantFragment fragment = new ParticipantFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        learningSessionDAO = new LearningSessionDAO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_participant, container, false);

        sessionDetail = (LinearLayout) view.findViewById(R.id.sessionDetailID);
        sessionDetail.setVisibility(View.GONE);

        learningSessionSearchView = (SearchView) view.findViewById(R.id.learningSessionSearchViewID);
        learningSessionSearchView.setQuery("20180301-IoT-M01", false);
        learningSessionSearchView.setQueryHint("Learning Session ID");
        learningSessionSearchView.setOnQueryTextListener(this);

        courseDateTextView = (TextView) view.findViewById(R.id.courseDateTextViewID);
        courseCodeTextView = (TextView) view.findViewById(R.id.courseCodeTextViewID);
        courseNameTextView = (TextView) view.findViewById(R.id.courseNameTextViewID);
        moduleNumberTextView = (TextView) view.findViewById(R.id.moduleNumberTextViewID);
        moduleNameTextView = (TextView) view.findViewById(R.id.moduleNameTextViewID);

        viewModeBtn = (Button) view.findViewById(R.id.viewModeBtnID);
        viewModeBtn.setOnClickListener(this);
        editModeBtn = (Button) view.findViewById(R.id.editModeBtnID);
        editModeBtn.setOnClickListener(this);
        answerQuizBtn = (Button) view.findViewById(R.id.answerQuizBtnID);
        answerQuizBtn.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewModeBtnID:
                Intent viewModeIntent = new Intent(getContext(), LearningTitleActivity.class);
                ((Participant) SecurityContext.getInstance().getRole()).setMode(Mode.VIEW);
                viewModeIntent.putExtra(AppConstants.LEARNING_SESSION_OBJ, learningSession);
                startActivity(viewModeIntent);
                break;
            case R.id.editModeBtnID:
                Intent editModeIntent = new Intent(getContext(), LearningTitleActivity.class);
                ((Participant) SecurityContext.getInstance().getRole()).setMode(Mode.EDIT);
                editModeIntent.putExtra(AppConstants.LEARNING_SESSION_OBJ, learningSession);
                startActivity(editModeIntent);
                break;
            case R.id.answerQuizBtnID:
                Intent answerQuizTitle = new Intent(getContext(), AnswerQuizTitleActivity.class);
                ((Participant) SecurityContext.getInstance().getRole()).setMode(Mode.ANSWER);
                answerQuizTitle.putExtra(AppConstants.LEARNING_SESSION_OBJ, learningSession);
                startActivity(answerQuizTitle);
                break;
        }
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
        courseCodeTextView.setText(learningSession.getCourseCode());
        courseNameTextView.setText(learningSession.getCourseName());
        moduleNumberTextView.setText(learningSession.getModuleNumber());
        moduleNameTextView.setText(learningSession.getModuleName());
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


}
