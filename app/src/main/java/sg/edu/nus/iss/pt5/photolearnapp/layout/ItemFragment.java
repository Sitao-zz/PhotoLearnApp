package sg.edu.nus.iss.pt5.photolearnapp.layout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.activity.ManageItemActivity;
import sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants;
import sg.edu.nus.iss.pt5.photolearnapp.constants.Mode;
import sg.edu.nus.iss.pt5.photolearnapp.constants.UIType;
import sg.edu.nus.iss.pt5.photolearnapp.model.Item;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningItem;
import sg.edu.nus.iss.pt5.photolearnapp.model.LearningSession;
import sg.edu.nus.iss.pt5.photolearnapp.model.Title;
import sg.edu.nus.iss.pt5.photolearnapp.util.CommonUtils;
import sg.edu.nus.iss.pt5.photolearnapp.util.FileStoreHelper;
import sg.edu.nus.iss.pt5.photolearnapp.util.FileStoreListener;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;
import sg.edu.nus.iss.pt5.photolearnapp.util.TextToSpeechUtil;

import static sg.edu.nus.iss.pt5.photolearnapp.constants.AppConstants.RC_EDIT_ITEM;

public class ItemFragment extends Fragment implements View.OnClickListener {

    private FileStoreHelper fileStoreHelper = FileStoreHelper.getInstance();

    private LearningSession learningSession;
    private Title title;
    private Item item;
    private int position;

    private ImageView photoImageView;
    private TextView descriptionTextView;
    private Button editBtn;

    private LinearLayout optLayout;

    private ImageButton textToSpeechBtn;
    private TextToSpeechUtil textToSpeechUtil;

    public ItemFragment() {
        // Required empty public constructor
    }

    public static ItemFragment newInstance(int position, Title title, Item item) {

        ItemFragment fragment = new ItemFragment();

        Bundle args = new Bundle();
        //args.putSerializable(AppConstants.LEARNING_SESSION_OBJ, learningSession);
        //args.putSerializable(AppConstants.TITLE_OBJ, title);
        args.putSerializable(AppConstants.POSITION, position);
        args.putSerializable(AppConstants.TITLE_OBJ, title);
        args.putSerializable(AppConstants.ITEM_OBJ, item);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //learningSession = (LearningSession) getArguments().getSerializable(AppConstants.LEARNING_SESSION_OBJ);
            //title = (Title) getArguments().getSerializable(AppConstants.TITLE_OBJ);
            position = getArguments().getInt(AppConstants.POSITION);
            title = (Title) getArguments().getSerializable(AppConstants.TITLE_OBJ);
            item = (Item) getArguments().getSerializable(AppConstants.ITEM_OBJ);
        }

        textToSpeechUtil = new TextToSpeechUtil(this.getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item, container, false);

        photoImageView = (ImageView) view.findViewById(R.id.photoImageViewID);
        descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextViewID);

        textToSpeechBtn = (ImageButton) view.findViewById(R.id.textToSpeachBtnID);
        textToSpeechBtn.setOnClickListener(this);

        initEditItemButton(view);

        descriptionTextView.setText(item.getPhotoDesc());

        optLayout = (LinearLayout) view.findViewById(R.id.optLayoutID);
        optLayout.setVisibility((CommonUtils.isQuizUI(title)) ? View.VISIBLE : View.GONE);

        downloadImage();

        return view;
    }

    private void initEditItemButton(View view) {
        editBtn = (Button) view.findViewById(R.id.editBtnID);
        if ((SecurityContext.getInstance().isTrainer() && CommonUtils.isLearningUI(item))
                || (SecurityContext.getInstance().isParticipant() && CommonUtils.isQuizUI(item))
                || (SecurityContext.getInstance().isParticipant() && CommonUtils.isLearningUI(item) && CommonUtils.isParticipantViewMode())) {
            editBtn.setVisibility(View.GONE);
        } else {
            editBtn.setOnClickListener(this);
        }
    }

    private void downloadImage() {
        fileStoreHelper.downloadImage(item.getPhotoUrl(), new FileStoreListener<Bitmap>() {
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
            case R.id.editBtnID:
                Intent editIntent = new Intent(this.getActivity(), ManageItemActivity.class);
                editIntent.putExtra(AppConstants.MODE, Mode.EDIT);
                editIntent.putExtra(AppConstants.POSITION, position);
                editIntent.putExtra(AppConstants.TITLE_OBJ, title);
                editIntent.putExtra(AppConstants.ITEM_OBJ, item);
                this.getActivity().startActivityForResult(editIntent, RC_EDIT_ITEM);
                break;
        }
    }

    public void onPause() {
        textToSpeechUtil.shutdown();
        super.onPause();
    }

}
