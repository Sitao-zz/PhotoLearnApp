package sg.edu.nus.iss.pt5.photolearnapp.layout;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SummaryQuizItemFragment extends Fragment implements View.OnClickListener {

    private FileStoreHelper fileStoreHelper = FileStoreHelper.getInstance();

    private QuizItem quizItem;
    private QuizUserAnswer quizUserAnswer;
    private QuizUserAnswerDAO quizUserAnswerDAO;
    private boolean isLastItem;

    private ImageView photoImageView;
    private TextView descriptionTextView;
    private CheckBox optOneCheckBox;
    private CheckBox optTwoCheckBox;
    private CheckBox optThreeCheckBox;
    private CheckBox optFourCheckBox;
    private TextView remarksTextView;

    private ImageButton textToSpeechBtn;
    private TextToSpeechUtil textToSpeechUtil;


    public SummaryQuizItemFragment() {
        // Required empty public constructor
    }

    public static SummaryQuizItemFragment newInstance(QuizItem quizItem, boolean isLastItem) {

        SummaryQuizItemFragment fragment = new SummaryQuizItemFragment();

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

        View view = inflater.inflate(R.layout.fragment_summary_quiz_item, container, false);

        photoImageView = (ImageView) view.findViewById(R.id.photoImageViewID);
        descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextViewID);

        optOneCheckBox = (CheckBox) view.findViewById(R.id.optOneCheckBoxID);
        optTwoCheckBox = (CheckBox) view.findViewById(R.id.optTowCheckBoxID);
        optThreeCheckBox = (CheckBox) view.findViewById(R.id.optThreeCheckBoxID);
        optFourCheckBox = (CheckBox) view.findViewById(R.id.optFourCheckBoxID);

        remarksTextView = (TextView) view.findViewById(R.id.remarksTextViewID);

        textToSpeechBtn = (ImageButton) view.findViewById(R.id.textToSpeachBtnID);
        textToSpeechBtn.setOnClickListener(this);

        populateUI();

        downloadImage();

        return view;
    }

    private void populateUI() {

        descriptionTextView.setText(quizItem.getPhotoDesc());

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

                    optOneCheckBox.setTextColor(quizItem.isOptionOneAnswer() == quizUserAnswer.isOptionOne() ? Color.GREEN : Color.RED);
                    optTwoCheckBox.setTextColor(quizItem.isOptionTwoAnswer() == quizUserAnswer.isOptionTwo() ? Color.GREEN : Color.RED);
                    optThreeCheckBox.setTextColor(quizItem.isOptionThreeAnswer() == quizUserAnswer.isOptionThree() ? Color.GREEN : Color.RED);
                    optFourCheckBox.setTextColor(quizItem.isOptionFourAnswer() == quizUserAnswer.isOptionFour() ? Color.GREEN : Color.RED);

                }
            }
        });

        remarksTextView.setText(quizItem.getExplanation());

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
        }
    }

    public void onPause() {
        textToSpeechUtil.shutdown();
        super.onPause();
    }

}
