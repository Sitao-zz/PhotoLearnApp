package sg.edu.nus.iss.pt5.photolearnapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import sg.edu.nus.iss.pt5.photolearnapp.R;
import sg.edu.nus.iss.pt5.photolearnapp.constants.UIType;
import sg.edu.nus.iss.pt5.photolearnapp.layout.TitleFragment;
import sg.edu.nus.iss.pt5.photolearnapp.util.CommonUtils;
import sg.edu.nus.iss.pt5.photolearnapp.util.SecurityContext;

public class LearningTitleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_title);

        if (SecurityContext.getInstance().isParticipant() && CommonUtils.isParticipantViewMode()) {
            setTitle("View Learning Title");
        } else if (SecurityContext.getInstance().isParticipant() && CommonUtils.isParticipantEditMode()) {
            setTitle("Edit Learning Title");
        }

        TitleFragment quizTitleFragment = TitleFragment.newInstance(UIType.LEARNING);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.titleListFrameLayoutID, quizTitleFragment).commit();

    }

}
