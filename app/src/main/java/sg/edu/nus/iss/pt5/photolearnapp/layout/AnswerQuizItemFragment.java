package sg.edu.nus.iss.pt5.photolearnapp.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.dao.DAOResultListener;
import sg.edu.nus.iss.pt5.photolearnapp.dao.QuizUserAnswerDAO;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.QuizUserAnswer;
import sg.edu.nus.iss.pt5.photolearnapp.util.FileStoreHelper;
import sg.edu.nus.iss.pt5.photolearnapp.util.FileStoreListener;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;
import sg.edu.nus.iss.pt5.photolearnapp.util.TextToSpeechUtil;

public class AnswerQuizItemFragment extends Fragment implements View.OnClickListener {

    private FileStoreHelper fileStoreHelper = FileStoreHelper.getInstance();

    private QuizItem quizItem;
    private QuizUserAnswer quizUserAnswer;
    private QuizUserAnswerDAO quizUserAnswerDAO;
    private boolean isLastItem;

    private ImageView photoImageView;
    private TextView descriptionTextView;
    private TextView textViewLongitude;
    private TextView textViewLatitude;
    private CheckBox optOneCheckBox;
    private CheckBox optTwoCheckBox;
    private CheckBox optThreeCheckBox;
    private CheckBox optFourCheckBox;

    private Button nextBtn;
    private Button submitBtn;

    private ImageButton textToSpeechBtn;
    private TextToSpeechUtil textToSpeechUtil;

    private NavigateListener navigateListener;

    public interface NavigateListener {
        public void onNextClick();

        public void onSubmitClick();
    }


    public AnswerQuizItemFragment() {
        // Required empty public constructor
    }

    public static AnswerQuizItemFragment newInstance(QuizItem quizItem, boolean isLastItem) {

        AnswerQuizItemFragment fragment = new AnswerQuizItemFragment();

        Bundle args = new Bundle();
        args.putSerializable(AppConstants.ITEM_OBJ, quizItem);
        args.putSerializable(AppConstants.IS_LAST, isLastItem);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            quizItem = (QuizItem) getArguments().getSerializable(AppConstants.ITEM_OBJ);
            isLastItem = getArguments().getBoolean(AppConstants.IS_LAST);

        }

        textToSpeechUtil = new TextToSpeechUtil(this.getContext());
        quizUserAnswerDAO = new QuizUserAnswerDAO();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_answer_quiz_item, container, false);

        photoImageView = (ImageView) view.findViewById(R.id.photoImageViewID);
        descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextViewID);

        textViewLongitude = (TextView) view.findViewById(R.id.textViewLongitude);
        textViewLatitude = (TextView) view.findViewById(R.id.textViewLatitude);

        optOneCheckBox = (CheckBox) view.findViewById(R.id.optOneCheckBoxID);
        optTwoCheckBox = (CheckBox) view.findViewById(R.id.optTowCheckBoxID);
        optThreeCheckBox = (CheckBox) view.findViewById(R.id.optThreeCheckBoxID);
        optFourCheckBox = (CheckBox) view.findViewById(R.id.optFourCheckBoxID);

        textToSpeechBtn = (ImageButton) view.findViewById(R.id.textToSpeachBtnID);
        textToSpeechBtn.setOnClickListener(this);

        nextBtn = (Button) view.findViewById(R.id.nextBtnID);
        nextBtn.setVisibility(View.GONE);
        nextBtn.setOnClickListener(this);

        submitBtn = (Button) view.findViewById(R.id.submitBtnID);
        submitBtn.setVisibility(View.GONE);
        submitBtn.setOnClickListener(this);

        if (isLastItem) {
            submitBtn.setVisibility(View.VISIBLE);
        } else {
            nextBtn.setVisibility(View.VISIBLE);
        }

        populateUI();

        downloadImage();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            navigateListener = (NavigateListener) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    private void populateUI() {

        descriptionTextView.setText(quizItem.getPhotoDesc());
        textViewLongitude.setText(Double.toString(quizItem.getLongitude()));
        textViewLatitude.setText(Double.toString(quizItem.getLatitude()));

        optOneCheckBox.setText(quizItem.getOptionOne());
        optTwoCheckBox.setText(quizItem.getOptionTwo());
        optThreeCheckBox.setText(quizItem.getOptionThree());
        optFourCheckBox.setText(quizItem.getOptionFour());

        quizUserAnswerDAO.getAnswersByQuizUser(SecurityContext.getInstance().getRole().getUser(), quizItem, new DAOResultListener<Iterable<QuizUserAnswer>>() {
            @Override
            public void OnDAOReturned(Iterable<QuizUserAnswer> obj) {
                List<QuizUserAnswer> quizUserAnswerList = (List<QuizUserAnswer>) obj;
                if (quizUserAnswerList != null && !quizUserAnswerList.isEmpty()) {
                    quizUserAnswer = quizUserAnswerList.get(0);
                    optOneCheckBox.setChecked(quizUserAnswer.isOptionOne());
                    optTwoCheckBox.setChecked(quizUserAnswer.isOptionTwo());
                    optThreeCheckBox.setChecked(quizUserAnswer.isOptionThree());
                    optFourCheckBox.setChecked(quizUserAnswer.isOptionFour());
                } else {
                    quizUserAnswer = new QuizUserAnswer(SecurityContext.getInstance().getRole().getUser().getId(), quizItem.getId());
                }
            }
        });

    }

    private void populateModel() {
        quizUserAnswer.setOptionOne(optOneCheckBox.isChecked());
        quizUserAnswer.setOptionTwo(optTwoCheckBox.isChecked());
        quizUserAnswer.setOptionThree(optThreeCheckBox.isChecked());
        quizUserAnswer.setOptionFour(optFourCheckBox.isChecked());
    }


    private void downloadImage() {
        fileStoreHelper.downloadImage(quizItem.getPhotoUrl(), new FileStoreListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                photoImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(Exception exception) {
                // TODO
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textToSpeachBtnID:
                String toSpeak = descriptionTextView.getText().toString();
                textToSpeechUtil.speak(toSpeak);
                break;
            case R.id.nextBtnID:
                populateModel();
                quizUserAnswerDAO.save(quizUserAnswer);

                navigateListener.onNextClick();
                break;
            case R.id.submitBtnID:
                populateModel();
                quizUserAnswerDAO.save(quizUserAnswer);

                navigateListener.onSubmitClick();
                break;
        }
    }

    public void onPause() {
        textToSpeechUtil.shutdown();
        super.onPause();
    }

}
